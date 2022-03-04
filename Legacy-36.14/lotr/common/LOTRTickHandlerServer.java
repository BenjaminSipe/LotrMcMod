package lotr.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import lotr.common.block.LOTRBlockPortal;
import lotr.common.entity.item.LOTREntityPortal;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionBounties;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.item.LOTRItemStructureSpawner;
import lotr.common.world.LOTRTeleporter;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.LOTRWorldInfo;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.LOTRWorldProviderUtumno;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnerNPCs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

public class LOTRTickHandlerServer {
   public static HashMap playersInPortals = new HashMap();
   public static HashMap playersInElvenPortals = new HashMap();
   public static HashMap playersInMorgulPortals = new HashMap();
   private int fireworkDisplay;

   public LOTRTickHandlerServer() {
      FMLCommonHandler.instance().bus().register(this);
   }

   @SubscribeEvent
   public void onWorldTick(WorldTickEvent event) {
      World world = event.world;
      if (!world.field_72995_K) {
         int l;
         if (event.phase == Phase.START && world == DimensionManager.getWorld(0)) {
            World overworld = world;
            if (LOTRLevelData.needsLoad) {
               LOTRLevelData.load();
            }

            if (LOTRTime.needsLoad) {
               LOTRTime.load();
            }

            if (LOTRFellowshipData.needsLoad) {
               LOTRFellowshipData.loadAll();
            }

            if (LOTRFactionBounties.needsLoad) {
               LOTRFactionBounties.loadAll();
            }

            if (LOTRFactionRelations.needsLoad) {
               LOTRFactionRelations.load();
            }

            if (LOTRConquestGrid.needsLoad) {
               LOTRConquestGrid.loadAllZones();
            }

            WorldServer[] var4 = MinecraftServer.func_71276_C().field_71305_c;
            l = var4.length;

            for(int var6 = 0; var6 < l; ++var6) {
               WorldServer dimWorld = var4[var6];
               if (dimWorld.field_73011_w instanceof LOTRWorldProvider) {
                  WorldInfo prevWorldInfo = dimWorld.func_72912_H();
                  if (prevWorldInfo.getClass() != LOTRWorldInfo.class) {
                     WorldInfo newWorldInfo = new LOTRWorldInfo(overworld.func_72912_H());
                     newWorldInfo.func_76062_a(prevWorldInfo.func_76065_j());
                     LOTRReflection.setWorldInfo(dimWorld, newWorldInfo);
                     FMLLog.info("LOTR: Successfully replaced world info in %s", new Object[]{LOTRDimension.getCurrentDimensionWithFallback(dimWorld).dimensionName});
                  }
               }
            }

            LOTRBannerProtection.updateWarningCooldowns();
            LOTRInterModComms.update();
         }

         if (event.phase == Phase.END) {
            int launches;
            if (world == DimensionManager.getWorld(0)) {
               if (LOTRLevelData.anyDataNeedsSave()) {
                  LOTRLevelData.save();
               }

               if (LOTRFellowshipData.anyDataNeedsSave()) {
                  LOTRFellowshipData.saveAll();
               }

               LOTRFactionBounties.updateAll();
               if (LOTRFactionBounties.anyDataNeedsSave()) {
                  LOTRFactionBounties.saveAll();
               }

               if (LOTRFactionRelations.needsSave()) {
                  LOTRFactionRelations.save();
               }

               if (LOTRConquestGrid.anyChangedZones()) {
                  LOTRConquestGrid.saveChangedZones();
               }

               if (world.func_82737_E() % 600L == 0L) {
                  LOTRLevelData.save();
                  LOTRFellowshipData.saveAll();
                  LOTRFellowshipData.saveAndClearUnusedFellowships();
                  LOTRFactionBounties.saveAll();
                  LOTRFactionRelations.save();
               }

               launches = LOTRConfig.playerDataClearingInterval;
               launches = Math.max(launches, 600);
               if (world.func_82737_E() % (long)launches == 0L) {
                  LOTRLevelData.saveAndClearUnusedPlayerData();
               }

               if (LOTRItemStructureSpawner.lastStructureSpawnTick > 0) {
                  --LOTRItemStructureSpawner.lastStructureSpawnTick;
               }

               LOTRTime.update();
               LOTRGreyWandererTracker.updateCooldowns();
            }

            if (world == DimensionManager.getWorld(LOTRDimension.MIDDLE_EARTH.dimensionID)) {
               LOTRDate.update(world);
               if (LOTRMod.canSpawnMobs(world)) {
                  LOTRSpawnerNPCs.performSpawning(world);
                  LOTREventSpawner.performSpawning(world);
               }

               LOTRConquestGrid.updateZones(world);
               if (!world.field_73010_i.isEmpty()) {
                  if (LOTRMod.isNewYearsDay()) {
                     if (this.fireworkDisplay == 0 && world.field_73012_v.nextInt(4000) == 0) {
                        this.fireworkDisplay = 100 + world.field_73012_v.nextInt(300);
                     }

                     if (this.fireworkDisplay > 0) {
                        --this.fireworkDisplay;
                        if (world.field_73012_v.nextInt(50) == 0) {
                           launches = 1 + world.field_73012_v.nextInt(7 + world.field_73010_i.size() / 2);
                           int range = 64;

                           for(l = 0; l < launches; ++l) {
                              EntityPlayer entityplayer = (EntityPlayer)world.field_73010_i.get(world.field_73012_v.nextInt(world.field_73010_i.size()));
                              int i = MathHelper.func_76128_c(entityplayer.field_70165_t) + MathHelper.func_76136_a(world.field_73012_v, -range, range);
                              int k = MathHelper.func_76128_c(entityplayer.field_70161_v) + MathHelper.func_76136_a(world.field_73012_v, -range, range);
                              int fireworks = 1 + world.field_73012_v.nextInt(4);

                              for(int l1 = 0; l1 < fireworks; ++l1) {
                                 int groupRange = 8;
                                 int i1 = i - world.field_73012_v.nextInt(groupRange) + world.field_73012_v.nextInt(groupRange);
                                 int k1 = k - world.field_73012_v.nextInt(groupRange) + world.field_73012_v.nextInt(groupRange);
                                 if (world.func_72899_e(i1, 0, k1)) {
                                    int j1 = world.func_72976_f(i1, k1);
                                    if (world.func_147439_a(i1, j1 - 1, k1).func_149721_r()) {
                                       ItemStack itemstack = new ItemStack(Items.field_151152_bP);
                                       NBTTagCompound itemData = new NBTTagCompound();
                                       NBTTagCompound fireworkData = new NBTTagCompound();
                                       NBTTagList explosionsList = new NBTTagList();
                                       int explosions = 1 + world.field_73012_v.nextInt(3);

                                       int flight;
                                       for(flight = 0; flight < explosions; ++flight) {
                                          NBTTagCompound explosionData = new NBTTagCompound();
                                          if (world.field_73012_v.nextBoolean()) {
                                             explosionData.func_74757_a("Flicker", true);
                                          }

                                          if (world.field_73012_v.nextBoolean()) {
                                             explosionData.func_74757_a("Trail", true);
                                          }

                                          int[] colors = new int[1 + world.field_73012_v.nextInt(3)];

                                          int effectType;
                                          for(effectType = 0; effectType < colors.length; ++effectType) {
                                             colors[effectType] = ItemDye.field_150922_c[world.field_73012_v.nextInt(ItemDye.field_150922_c.length)];
                                          }

                                          explosionData.func_74783_a("Colors", colors);
                                          effectType = world.field_73012_v.nextInt(5);
                                          if (effectType == 3) {
                                             effectType = 0;
                                          }

                                          explosionData.func_74774_a("Type", (byte)effectType);
                                          explosionsList.func_74742_a(explosionData);
                                       }

                                       fireworkData.func_74782_a("Explosions", explosionsList);
                                       flight = 1 + world.field_73012_v.nextInt(3);
                                       fireworkData.func_74774_a("Flight", (byte)flight);
                                       itemData.func_74782_a("Fireworks", fireworkData);
                                       itemstack.func_77982_d(itemData);
                                       EntityFireworkRocket firework = new EntityFireworkRocket(world, (double)i1 + 0.5D, (double)j1 + 0.5D, (double)k1 + 0.5D, itemstack);
                                       world.func_72838_d(firework);
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (world.func_82737_E() % 20L == 0L) {
                     for(launches = 0; launches < world.field_73010_i.size(); ++launches) {
                        EntityPlayer entityplayer = (EntityPlayer)world.field_73010_i.get(launches);
                        LOTRLevelData.sendPlayerLocationsToPlayer(entityplayer, world);
                     }
                  }
               }
            }

            if (world == DimensionManager.getWorld(LOTRDimension.UTUMNO.dimensionID) && !world.field_73010_i.isEmpty() && LOTRMod.canSpawnMobs(world)) {
               LOTRSpawnerNPCs.performSpawning(world);
            }

            if (world.field_73011_w instanceof LOTRWorldProvider && world.func_82737_E() % 100L == 0L) {
               LOTRBiomeVariantStorage.performCleanup((WorldServer)world);
            }
         }

      }
   }

   @SubscribeEvent
   public void onPlayerTick(PlayerTickEvent event) {
      EntityPlayer player = event.player;
      World world = player.field_70170_p;
      if (world != null && !world.field_72995_K) {
         if (player != null && player instanceof EntityPlayerMP) {
            EntityPlayerMP entityplayer = (EntityPlayerMP)player;
            if (event.phase == Phase.START && entityplayer.field_71135_a != null && !(entityplayer.field_71135_a instanceof LOTRNetHandlerPlayServer)) {
               entityplayer.field_71135_a = new LOTRNetHandlerPlayServer(MinecraftServer.func_71276_C(), entityplayer.field_71135_a.field_147371_a, entityplayer);
            }

            if (event.phase == Phase.END) {
               LOTRLevelData.getData((EntityPlayer)entityplayer).onUpdate(entityplayer, (WorldServer)world);
               NetHandlerPlayServer netHandler = entityplayer.field_71135_a;
               if (netHandler instanceof LOTRNetHandlerPlayServer) {
                  ((LOTRNetHandlerPlayServer)netHandler).update();
               }

               ItemStack heldItem = entityplayer.func_70694_bm();
               if (heldItem != null && (heldItem.func_77973_b() instanceof ItemWritableBook || heldItem.func_77973_b() instanceof ItemEditableBook)) {
                  entityplayer.func_143004_u();
               }

               List portals;
               Iterator var8;
               Object obj;
               EntityItem item;
               if (entityplayer.field_71093_bK == 0 && LOTRLevelData.madePortal == 0) {
                  portals = world.func_72872_a(EntityItem.class, entityplayer.field_70121_D.func_72314_b(16.0D, 16.0D, 16.0D));
                  var8 = portals.iterator();

                  while(var8.hasNext()) {
                     obj = var8.next();
                     item = (EntityItem)obj;
                     if (LOTRLevelData.madePortal == 0 && item.func_92059_d() != null && item.func_92059_d().func_77973_b() == LOTRMod.goldRing && item.func_70027_ad()) {
                        LOTRLevelData.setMadePortal(1);
                        LOTRLevelData.markOverworldPortalLocation(MathHelper.func_76128_c(item.field_70165_t), MathHelper.func_76128_c(item.field_70163_u), MathHelper.func_76128_c(item.field_70161_v));
                        item.func_70106_y();
                        world.func_72876_a(entityplayer, item.field_70165_t, item.field_70163_u + 3.0D, item.field_70161_v, 3.0F, true);
                        Entity portal = new LOTREntityPortal(world);
                        portal.func_70012_b(item.field_70165_t, item.field_70163_u + 3.0D, item.field_70161_v, 0.0F, 0.0F);
                        world.func_72838_d(portal);
                     }
                  }
               }

               int j;
               int j1;
               int i;
               if (entityplayer.field_71093_bK == 0 || entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                  portals = world.func_72872_a(EntityItem.class, entityplayer.field_70121_D.func_72314_b(16.0D, 16.0D, 16.0D));
                  var8 = portals.iterator();

                  label248:
                  while(true) {
                     boolean foundPortalLocation;
                     int[] portalLocation;
                     int i1;
                     int k1;
                     do {
                        ItemStack itemstack;
                        do {
                           do {
                              do {
                                 if (!var8.hasNext()) {
                                    break label248;
                                 }

                                 obj = var8.next();
                                 item = (EntityItem)obj;
                              } while(item.func_92059_d() == null);

                              i = MathHelper.func_76128_c(item.field_70165_t);
                              j = MathHelper.func_76128_c(item.field_70163_u);
                              j1 = MathHelper.func_76128_c(item.field_70161_v);
                              itemstack = item.func_92059_d();
                              if ((LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.LOTHLORIEN) >= 1.0F || LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.HIGH_ELF) >= 1.0F) && (itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.elanor) || itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.niphredil))) {
                                 foundPortalLocation = false;
                                 portalLocation = new int[3];

                                 for(i1 = i - 2; !foundPortalLocation && i1 <= i + 2; ++i1) {
                                    for(k1 = j1 - 2; !foundPortalLocation && k1 <= j1 + 2; ++k1) {
                                       if (((LOTRBlockPortal)LOTRMod.elvenPortal).isValidPortalLocation(world, i1, j, k1, false)) {
                                          foundPortalLocation = true;
                                          portalLocation = new int[]{i1, j, k1};
                                       }
                                    }
                                 }

                                 if (foundPortalLocation) {
                                    item.func_70106_y();

                                    for(i1 = -1; i1 <= 1; ++i1) {
                                       for(k1 = -1; k1 <= 1; ++k1) {
                                          world.func_147465_d(portalLocation[0] + i1, portalLocation[1], portalLocation[2] + k1, LOTRMod.elvenPortal, 0, 2);
                                       }
                                    }
                                 }
                              }
                           } while(LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.MORDOR) < 1.0F && LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.ANGMAR) < 1.0F && LOTRLevelData.getData((EntityPlayer)entityplayer).getAlignment(LOTRFaction.DOL_GULDUR) < 1.0F);
                        } while(!LOTRMod.isOreNameEqual(itemstack, "bone"));

                        foundPortalLocation = false;
                        portalLocation = new int[3];

                        for(i1 = i - 2; !foundPortalLocation && i1 <= i + 2; ++i1) {
                           for(k1 = j1 - 2; !foundPortalLocation && k1 <= j1 + 2; ++k1) {
                              if (((LOTRBlockPortal)LOTRMod.morgulPortal).isValidPortalLocation(world, i1, j, k1, false)) {
                                 foundPortalLocation = true;
                                 portalLocation = new int[]{i1, j, k1};
                              }
                           }
                        }
                     } while(!foundPortalLocation);

                     item.func_70106_y();

                     for(i1 = -1; i1 <= 1; ++i1) {
                        for(k1 = -1; k1 <= 1; ++k1) {
                           world.func_147465_d(portalLocation[0] + i1, portalLocation[1], portalLocation[2] + k1, LOTRMod.morgulPortal, 0, 2);
                        }
                     }
                  }
               }

               int i;
               if ((entityplayer.field_71093_bK == 0 || entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) && playersInPortals.containsKey(entityplayer)) {
                  portals = world.func_72872_a(LOTREntityPortal.class, entityplayer.field_70121_D.func_72314_b(8.0D, 8.0D, 8.0D));
                  boolean inPortal = false;

                  for(i = 0; i < portals.size(); ++i) {
                     LOTREntityPortal portal = (LOTREntityPortal)portals.get(i);
                     if (portal.field_70121_D.func_72326_a(entityplayer.field_70121_D)) {
                        inPortal = true;
                        break;
                     }
                  }

                  if (inPortal) {
                     i = (Integer)playersInPortals.get(entityplayer);
                     ++i;
                     playersInPortals.put(entityplayer, i);
                     if (i >= 100) {
                        int dimension = 0;
                        if (entityplayer.field_71093_bK == 0) {
                           dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
                        } else if (entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                           dimension = 0;
                        }

                        if (world instanceof WorldServer) {
                           MinecraftServer.func_71276_C().func_71203_ab().transferPlayerToDimension(entityplayer, dimension, new LOTRTeleporter(DimensionManager.getWorld(dimension), true));
                        }

                        playersInPortals.remove(entityplayer);
                     }
                  } else {
                     playersInPortals.remove(entityplayer);
                  }
               }

               this.updatePlayerInPortal(entityplayer, playersInElvenPortals, (LOTRBlockPortal)LOTRMod.elvenPortal);
               this.updatePlayerInPortal(entityplayer, playersInMorgulPortals, (LOTRBlockPortal)LOTRMod.morgulPortal);
               if (entityplayer.field_71093_bK == LOTRDimension.UTUMNO.dimensionID) {
                  int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
                  int j = MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b);
                  i = MathHelper.func_76128_c(entityplayer.field_70161_v);
                  byte range = 32;

                  for(i = 0; i < 60; ++i) {
                     j = i + world.field_73012_v.nextInt(range) - world.field_73012_v.nextInt(range);
                     j1 = j + world.field_73012_v.nextInt(range) - world.field_73012_v.nextInt(range);
                     int k1 = i + world.field_73012_v.nextInt(range) - world.field_73012_v.nextInt(range);
                     Block block;
                     int meta;
                     if (LOTRUtumnoLevel.forY(j1) == LOTRUtumnoLevel.ICE) {
                        block = world.func_147439_a(j, j1, k1);
                        meta = world.func_72805_g(j, j1, k1);
                        if (block.func_149688_o() == Material.field_151586_h && meta == 0) {
                           world.func_147465_d(j, j1, k1, Blocks.field_150432_aD, 0, 3);
                        }
                     }

                     if (LOTRUtumnoLevel.forY(j1) == LOTRUtumnoLevel.FIRE) {
                        block = world.func_147439_a(j, j1, k1);
                        meta = world.func_72805_g(j, j1, k1);
                        if (block.func_149688_o() == Material.field_151586_h && meta == 0) {
                           world.func_147465_d(j, j1, k1, Blocks.field_150350_a, 0, 3);
                           LOTRWorldProviderUtumno.doEvaporateFX(world, j, j1, k1);
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private void updatePlayerInPortal(EntityPlayerMP entityplayer, HashMap players, LOTRBlockPortal portalBlock) {
      if ((entityplayer.field_71093_bK == 0 || entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) && players.containsKey(entityplayer)) {
         boolean inPortal = entityplayer.field_70170_p.func_147439_a(MathHelper.func_76128_c(entityplayer.field_70165_t), MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b), MathHelper.func_76128_c(entityplayer.field_70161_v)) == portalBlock;
         if (inPortal) {
            int i = (Integer)players.get(entityplayer);
            ++i;
            players.put(entityplayer, i);
            if (i >= entityplayer.func_82145_z()) {
               int dimension = 0;
               if (entityplayer.field_71093_bK == 0) {
                  dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
               } else if (entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                  dimension = 0;
               }

               WorldServer newWorld = MinecraftServer.func_71276_C().func_71218_a(dimension);
               MinecraftServer.func_71276_C().func_71203_ab().transferPlayerToDimension(entityplayer, dimension, portalBlock.getPortalTeleporter(newWorld));
               players.remove(entityplayer);
            }
         } else {
            players.remove(entityplayer);
         }
      }

   }
}
