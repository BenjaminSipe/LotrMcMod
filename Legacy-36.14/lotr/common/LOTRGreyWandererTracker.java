package lotr.common;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.entity.npc.LOTREntityGandalf;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class LOTRGreyWandererTracker {
   private static Map activeGreyWanderers = new HashMap();
   private static final int greyWandererCooldown_MAX = 3600;
   private static final int spawnInterval = 2400;
   private static int spawnCooldown;

   public static void save(NBTTagCompound levelData) {
      NBTTagList greyWandererTags = new NBTTagList();
      Iterator var2 = activeGreyWanderers.entrySet().iterator();

      while(var2.hasNext()) {
         Entry e = (Entry)var2.next();
         UUID id = (UUID)e.getKey();
         int cd = (Integer)e.getValue();
         NBTTagCompound nbt = new NBTTagCompound();
         nbt.func_74778_a("ID", id.toString());
         nbt.func_74768_a("CD", cd);
         greyWandererTags.func_74742_a(nbt);
      }

      levelData.func_74782_a("GreyWanderers", greyWandererTags);
      levelData.func_74768_a("GWSpawnTick", spawnCooldown);
   }

   public static void load(NBTTagCompound levelData) {
      activeGreyWanderers.clear();
      NBTTagList greyWandererTags = levelData.func_150295_c("GreyWanderers", 10);

      for(int i = 0; i < greyWandererTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = greyWandererTags.func_150305_b(i);

         try {
            UUID id = UUID.fromString(nbt.func_74779_i("ID"));
            int cd = nbt.func_74762_e("CD");
            activeGreyWanderers.put(id, cd);
         } catch (Exception var6) {
            FMLLog.severe("Error loading LOTR data: invalid Grey Wanderer", new Object[0]);
            var6.printStackTrace();
         }
      }

      if (levelData.func_74764_b("GWSpawnTick")) {
         spawnCooldown = levelData.func_74762_e("GWSpawnTick");
      } else {
         spawnCooldown = 2400;
      }

   }

   private static void markDirty() {
      LOTRLevelData.markDirty();
   }

   public static boolean isWandererActive(UUID id) {
      return activeGreyWanderers.containsKey(id) && (Integer)activeGreyWanderers.get(id) > 0;
   }

   public static void addNewWanderer(UUID id) {
      activeGreyWanderers.put(id, 3600);
      markDirty();
   }

   public static void setWandererActive(UUID id) {
      if (activeGreyWanderers.containsKey(id)) {
         activeGreyWanderers.put(id, 3600);
         markDirty();
      }

   }

   public static void updateCooldowns() {
      Set removes = new HashSet();
      Iterator var1 = activeGreyWanderers.keySet().iterator();

      UUID id;
      while(var1.hasNext()) {
         id = (UUID)var1.next();
         int cd = (Integer)activeGreyWanderers.get(id);
         --cd;
         activeGreyWanderers.put(id, cd);
         if (cd <= 0) {
            removes.add(id);
         }
      }

      if (!removes.isEmpty()) {
         var1 = removes.iterator();

         while(var1.hasNext()) {
            id = (UUID)var1.next();
            activeGreyWanderers.remove(id);
         }

         markDirty();
      }

   }

   public static void performSpawning(World world) {
      if (activeGreyWanderers.isEmpty()) {
         if (!world.field_73010_i.isEmpty()) {
            --spawnCooldown;
            if (spawnCooldown <= 0) {
               spawnCooldown = 2400;
               List players = new ArrayList(world.field_73010_i);
               Collections.shuffle(players);
               Random rand = world.field_73012_v;
               boolean spawned = false;
               Iterator var4 = players.iterator();

               while(true) {
                  EntityPlayer entityplayer;
                  do {
                     if (!var4.hasNext()) {
                        return;
                     }

                     Object obj = var4.next();
                     entityplayer = (EntityPlayer)obj;
                  } while(LOTRLevelData.getData(entityplayer).hasAnyGWQuest());

                  for(int attempts = 0; attempts < 32; ++attempts) {
                     float angle = rand.nextFloat() * 3.1415927F * 2.0F;
                     int r = MathHelper.func_76136_a(rand, 4, 16);
                     int i = MathHelper.func_76128_c(entityplayer.field_70165_t + (double)((float)r * MathHelper.func_76134_b(angle)));
                     int k = MathHelper.func_76128_c(entityplayer.field_70161_v + (double)((float)r * MathHelper.func_76126_a(angle)));
                     int j = world.func_72976_f(i, k);
                     if (j > 62 && world.func_147439_a(i, j - 1, k).func_149662_c() && !world.func_147439_a(i, j, k).func_149721_r() && !world.func_147439_a(i, j + 1, k).func_149721_r()) {
                        LOTREntityGandalf wanderer = new LOTREntityGandalf(world);
                        wanderer.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
                        wanderer.liftSpawnRestrictions = true;
                        wanderer.liftBannerRestrictions = true;
                        Result canSpawn = ForgeEventFactory.canEntitySpawn(wanderer, world, (float)wanderer.field_70165_t, (float)wanderer.field_70163_u, (float)wanderer.field_70161_v);
                        if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT && wanderer.func_70601_bi()) {
                           wanderer.liftSpawnRestrictions = false;
                           wanderer.liftBannerRestrictions = false;
                           world.func_72838_d(wanderer);
                           wanderer.func_110161_a((IEntityLivingData)null);
                           addNewWanderer(wanderer.func_110124_au());
                           wanderer.arriveAt(entityplayer);
                           spawned = true;
                           return;
                        }
                     }
                  }
               }
            }
         }

      }
   }
}
