package lotr.common.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import lotr.common.config.LOTRConfig;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.item.RingPortalEntity;
import lotr.common.init.LOTRDimensions;
import lotr.common.time.LOTRDate;
import lotr.common.time.LOTRTime;
import lotr.common.world.RingPortalTeleporter;
import lotr.common.world.spawning.RenewedNPCSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class LOTRTickHandlerServer {
   private Map ringPortalTransfers = new HashMap();
   private Map ringPortalPlayerTicks = new HashMap();
   public static final int RING_PORTAL_PLAYER_TIME = 100;

   public LOTRTickHandlerServer() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onWorldTick(WorldTickEvent event) {
      if (event.side == LogicalSide.SERVER) {
         ServerWorld world = (ServerWorld)event.world;
         Phase phase = event.phase;
         LOTRLevelData levelData = LOTRLevelData.serverInstance();
         if (phase == Phase.START && LOTRDimensions.isDimension((World)world, World.field_234918_g_)) {
            if (levelData.needsLoad()) {
               levelData.load(world);
            }

            if (LOTRTime.needsLoad()) {
               LOTRTime.load(world);
            }
         }

         if (phase == Phase.END) {
            if (LOTRDimensions.isDimension((World)world, World.field_234918_g_)) {
               if (levelData.anyDataNeedsSave()) {
                  levelData.save(world);
               }

               if (world.func_82737_E() % 600L == 0L) {
                  levelData.save(world);
               }

               if (world.func_82737_E() % (long)(Integer)LOTRConfig.SERVER.playerDataClearingInterval.get() == 0L) {
                  levelData.saveAndClearUnusedPlayerData(world);
               }
            } else if (LOTRDimensions.isDimension((World)world, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
               LOTRTime.updateTime(world);
               LOTRDate.updateDate(world);
               RenewedNPCSpawner.getForWorld(world).runSpawning(world);
               if (world.func_82737_E() % 20L == 0L) {
                  Iterator var5 = world.func_217369_A().iterator();

                  while(var5.hasNext()) {
                     PlayerEntity player = (PlayerEntity)var5.next();
                     levelData.sendPlayerLocationsToPlayer(player, world);
                  }
               }
            }

            Set removes = new HashSet();
            Iterator var14 = this.ringPortalTransfers.entrySet().iterator();

            while(true) {
               while(var14.hasNext()) {
                  Entry entry = (Entry)var14.next();
                  Entity e = (Entity)entry.getKey();
                  RingPortalEntity portal = (RingPortalEntity)entry.getValue();
                  if (e.func_70089_S() && e.field_70170_p != null) {
                     if (e.field_70170_p == world) {
                        boolean inPortal = checkInRingPortal(e);
                        if (e instanceof PlayerEntity) {
                           PlayerEntity player = (PlayerEntity)e;
                           if (inPortal) {
                              int i = (Integer)this.ringPortalPlayerTicks.getOrDefault(player, 0);
                              ++i;
                              this.ringPortalPlayerTicks.put(player, i);
                              if (i >= 100) {
                                 RingPortalTeleporter.transferEntity(world, player, Optional.of(portal), true);
                                 this.ringPortalPlayerTicks.remove(player);
                                 removes.add(player);
                              }
                           } else {
                              this.ringPortalPlayerTicks.remove(player);
                           }
                        } else {
                           if (inPortal) {
                              RingPortalTeleporter.transferEntity(world, e, Optional.of(portal), true);
                           }

                           removes.add(e);
                        }
                     }
                  } else {
                     removes.add(e);
                  }
               }

               Map var10001 = this.ringPortalTransfers;
               removes.forEach(var10001::remove);
               break;
            }
         }
      }

   }

   @SubscribeEvent
   public void onPlayerTick(PlayerTickEvent event) {
      if (event.side == LogicalSide.SERVER) {
         ServerPlayerEntity player = (ServerPlayerEntity)event.player;
         ServerWorld world = player.func_71121_q();
         Phase phase = event.phase;
         if (phase == Phase.END) {
            LOTRLevelData.serverInstance().getData(player).onUpdate(player, world);
         }
      }

   }

   @SubscribeEvent
   public void onWorldUnload(Unload event) {
      IWorld world = event.getWorld();
      if (world instanceof ServerWorld) {
         ServerWorld sWorld = (ServerWorld)world;
         boolean isCompleteGameUnload = LOTRDimensions.isDimension((World)sWorld, World.field_234918_g_);
         if (isCompleteGameUnload) {
            this.ringPortalTransfers.clear();
            this.ringPortalPlayerTicks.clear();
         }
      }

   }

   public void prepareRingPortal(Entity entity, RingPortalEntity portal) {
      if (!this.ringPortalTransfers.containsKey(entity)) {
         this.ringPortalTransfers.put(entity, portal);
      }

   }

   public static boolean checkInRingPortal(Entity entity) {
      if (!(entity instanceof RingPortalEntity) && !entity.func_184218_aH()) {
         double searchRange = 8.0D;
         List portals = entity.field_70170_p.func_217357_a(RingPortalEntity.class, entity.func_174813_aQ().func_72321_a(searchRange, searchRange, searchRange));
         boolean inPortal = false;
         Iterator var5 = portals.iterator();

         RingPortalEntity portal;
         do {
            if (!var5.hasNext()) {
               return false;
            }

            portal = (RingPortalEntity)var5.next();
         } while(!portal.func_174813_aQ().func_72326_a(entity.func_174813_aQ()));

         return true;
      } else {
         return false;
      }
   }
}
