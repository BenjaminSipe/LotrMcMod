package lotr.common;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.IGuiHandler;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lotr.client.gui.LOTRGuiAlloyForge;
import lotr.client.gui.LOTRGuiAnvil;
import lotr.client.gui.LOTRGuiArmorStand;
import lotr.client.gui.LOTRGuiBarrel;
import lotr.client.gui.LOTRGuiBeacon;
import lotr.client.gui.LOTRGuiBookshelf;
import lotr.client.gui.LOTRGuiBrandingIron;
import lotr.client.gui.LOTRGuiChestWithPouch;
import lotr.client.gui.LOTRGuiCoinExchange;
import lotr.client.gui.LOTRGuiCraftingTable;
import lotr.client.gui.LOTRGuiDaleCracker;
import lotr.client.gui.LOTRGuiEditSign;
import lotr.client.gui.LOTRGuiGollum;
import lotr.client.gui.LOTRGuiHiredFarmerInventory;
import lotr.client.gui.LOTRGuiHiredInteract;
import lotr.client.gui.LOTRGuiHiredWarriorInventory;
import lotr.client.gui.LOTRGuiHobbitOven;
import lotr.client.gui.LOTRGuiHornSelect;
import lotr.client.gui.LOTRGuiMap;
import lotr.client.gui.LOTRGuiMenu;
import lotr.client.gui.LOTRGuiMercenaryHire;
import lotr.client.gui.LOTRGuiMercenaryInteract;
import lotr.client.gui.LOTRGuiMillstone;
import lotr.client.gui.LOTRGuiMobSpawner;
import lotr.client.gui.LOTRGuiMountInventory;
import lotr.client.gui.LOTRGuiNPCMountInventory;
import lotr.client.gui.LOTRGuiNPCRespawner;
import lotr.client.gui.LOTRGuiPouch;
import lotr.client.gui.LOTRGuiRedBook;
import lotr.client.gui.LOTRGuiSquadronItem;
import lotr.client.gui.LOTRGuiTrade;
import lotr.client.gui.LOTRGuiTradeInteract;
import lotr.client.gui.LOTRGuiTradeUnitTradeInteract;
import lotr.client.gui.LOTRGuiUnitTrade;
import lotr.client.gui.LOTRGuiUnitTradeInteract;
import lotr.client.gui.LOTRGuiUnsmeltery;
import lotr.common.block.LOTRBlockFlowerPot;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.entity.npc.LOTRMercenary;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.entity.npc.LOTRUnitTradeable;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRFaction;
import lotr.common.inventory.LOTRContainerAlloyForge;
import lotr.common.inventory.LOTRContainerAnvil;
import lotr.common.inventory.LOTRContainerArmorStand;
import lotr.common.inventory.LOTRContainerBarrel;
import lotr.common.inventory.LOTRContainerBookshelf;
import lotr.common.inventory.LOTRContainerChestWithPouch;
import lotr.common.inventory.LOTRContainerCoinExchange;
import lotr.common.inventory.LOTRContainerCraftingTable;
import lotr.common.inventory.LOTRContainerDaleCracker;
import lotr.common.inventory.LOTRContainerGollum;
import lotr.common.inventory.LOTRContainerHiredFarmerInventory;
import lotr.common.inventory.LOTRContainerHiredWarriorInventory;
import lotr.common.inventory.LOTRContainerHobbitOven;
import lotr.common.inventory.LOTRContainerMillstone;
import lotr.common.inventory.LOTRContainerMountInventory;
import lotr.common.inventory.LOTRContainerNPCMountInventory;
import lotr.common.inventory.LOTRContainerPouch;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.inventory.LOTRContainerUnitTrade;
import lotr.common.inventory.LOTRContainerUnsmeltery;
import lotr.common.item.LOTRItemDaleCracker;
import lotr.common.item.LOTRItemPouch;
import lotr.common.network.LOTRPacketClientsideGUI;
import lotr.common.network.LOTRPacketFellowshipAcceptInviteResult;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.tileentity.LOTRTileEntityAlloyForgeBase;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import lotr.common.tileentity.LOTRTileEntityBookshelf;
import lotr.common.tileentity.LOTRTileEntityChest;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import lotr.common.tileentity.LOTRTileEntityMillstone;
import lotr.common.tileentity.LOTRTileEntitySign;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import lotr.common.world.map.LOTRAbstractWaypoint;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class LOTRCommonProxy implements IGuiHandler {
   public static final int GUI_ID_HOBBIT_OVEN = 0;
   public static final int GUI_ID_MORGUL_TABLE = 1;
   public static final int GUI_ID_ELVEN_TABLE = 2;
   public static final int GUI_ID_TRADE = 3;
   public static final int GUI_ID_DWARVEN_TABLE = 4;
   public static final int GUI_ID_ALLOY_FORGE = 5;
   public static final int GUI_ID_MOB_SPAWNER = 6;
   public static final int GUI_ID_UNIT_TRADE = 7;
   public static final int GUI_ID_URUK_TABLE = 8;
   public static final int GUI_ID_HORN_SELECT = 9;
   public static final int GUI_ID_GOLLUM = 10;
   public static final int GUI_ID_LOTR = 11;
   public static final int GUI_ID_WOOD_ELVEN_TABLE = 12;
   public static final int GUI_ID_GONDORIAN_TABLE = 13;
   public static final int GUI_ID_ROHIRRIC_TABLE = 14;
   public static final int GUI_ID_POUCH = 15;
   public static final int GUI_ID_BARREL = 16;
   public static final int GUI_ID_ARMOR_STAND = 17;
   public static final int GUI_ID_DUNLENDING_TABLE = 18;
   public static final int GUI_ID_TRADE_INTERACT = 19;
   public static final int GUI_ID_UNIT_TRADE_INTERACT = 20;
   public static final int GUI_ID_HIRED_INTERACT = 21;
   public static final int GUI_ID_HIRED_FARMER_INVENTORY = 22;
   public static final int GUI_ID_ANGMAR_TABLE = 23;
   public static final int GUI_ID_TRADE_UNIT_TRADE_INTERACT = 24;
   public static final int GUI_ID_NEAR_HARAD_TABLE = 25;
   public static final int GUI_ID_HIGH_ELVEN_TABLE = 26;
   public static final int GUI_ID_BLUE_DWARVEN_TABLE = 27;
   public static final int GUI_ID_RANGER_TABLE = 28;
   public static final int GUI_ID_MOUNT_INV = 29;
   public static final int GUI_ID_DOL_GULDUR_TABLE = 30;
   public static final int GUI_ID_GUNDABAD_TABLE = 31;
   public static final int GUI_ID_RED_BOOK = 32;
   public static final int GUI_ID_SQUADRON_ITEM = 33;
   public static final int GUI_ID_HALF_TROLL_TABLE = 34;
   public static final int GUI_ID_COIN_EXCHANGE = 35;
   public static final int GUI_ID_DOL_AMROTH_TABLE = 36;
   public static final int GUI_ID_MOREDAIN_TABLE = 37;
   public static final int GUI_ID_UNSMELTERY = 38;
   public static final int GUI_ID_TAUREDAIN_TABLE = 39;
   public static final int GUI_ID_DART_TRAP = 40;
   public static final int GUI_ID_CHEST = 41;
   public static final int GUI_ID_DALE_TABLE = 42;
   public static final int GUI_ID_DORWINION_TABLE = 43;
   public static final int GUI_ID_HOBBIT_TABLE = 44;
   public static final int GUI_ID_RESPAWNER = 45;
   public static final int GUI_ID_HIRED_WARRIOR_INVENTORY = 46;
   public static final int GUI_ID_EDIT_SIGN = 47;
   public static final int GUI_ID_DALE_CRACKER = 48;
   public static final int GUI_ID_RHUN_TABLE = 49;
   public static final int GUI_ID_BEACON = 50;
   public static final int GUI_ID_RIVENDELL_TABLE = 51;
   public static final int GUI_ID_MILLSTONE = 52;
   public static final int GUI_ID_ANVIL = 53;
   public static final int GUI_ID_ANVIL_TRADER = 54;
   public static final int GUI_ID_BOOKSHELF = 55;
   public static final int GUI_ID_UMBAR_TABLE = 56;
   public static final int GUI_ID_GULF_TABLE = 57;
   public static final int GUI_ID_MERCENARY_INTERACT = 58;
   public static final int GUI_ID_MERCENARY_TRADE = 59;
   public static final int GUI_ID_CONQUEST_GRID = 60;
   public static final int GUI_ID_BRANDING_IRON = 61;
   public static final int GUI_ID_BREE_TABLE = 62;
   public static final int GUI_ID_CHEST_WITH_POUCH = 63;
   public static final int GUI_ID_MINECART_CHEST_WITH_POUCH = 64;
   private static final int SLOT_PACK_BITSHIFT = 16;

   public void onPreload() {
   }

   public void onLoad() {
   }

   public void onPostload() {
   }

   public void testReflection(World world) {
      LOTRReflection.testAll(world);
   }

   public static int packGuiIDWithSlot(int guiID, int slotNo) {
      return guiID | slotNo << 16;
   }

   public static boolean testForSlotPackedGuiID(int fullID, int guiID) {
      return (fullID & '\uffff') == guiID;
   }

   public static int unpackSlot(int fullID) {
      return fullID >> 16;
   }

   public Object getServerGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
      TileEntity bookshelf;
      if (ID == 0) {
         bookshelf = world.func_147438_o(i, j, k);
         if (bookshelf instanceof LOTRTileEntityHobbitOven) {
            return new LOTRContainerHobbitOven(entityplayer.field_71071_by, (LOTRTileEntityHobbitOven)bookshelf);
         }
      }

      if (ID == 1) {
         return new LOTRContainerCraftingTable.Morgul(entityplayer.field_71071_by, world, i, j, k);
      } else if (ID == 2) {
         return new LOTRContainerCraftingTable.Elven(entityplayer.field_71071_by, world, i, j, k);
      } else {
         Entity entity;
         if (ID == 3) {
            entity = world.func_73045_a(i);
            if (entity instanceof LOTRTradeable) {
               return new LOTRContainerTrade(entityplayer.field_71071_by, (LOTRTradeable)entity, world);
            }
         }

         if (ID == 4) {
            return new LOTRContainerCraftingTable.Dwarven(entityplayer.field_71071_by, world, i, j, k);
         } else {
            if (ID == 5) {
               bookshelf = world.func_147438_o(i, j, k);
               if (bookshelf instanceof LOTRTileEntityAlloyForgeBase) {
                  return new LOTRContainerAlloyForge(entityplayer.field_71071_by, (LOTRTileEntityAlloyForgeBase)bookshelf);
               }
            }

            if (ID == 7) {
               entity = world.func_73045_a(i);
               if (entity instanceof LOTRUnitTradeable) {
                  return new LOTRContainerUnitTrade(entityplayer, (LOTRUnitTradeable)entity, world);
               }
            }

            if (ID == 8) {
               return new LOTRContainerCraftingTable.Uruk(entityplayer.field_71071_by, world, i, j, k);
            } else {
               if (ID == 10) {
                  entity = world.func_73045_a(i);
                  if (entity instanceof LOTREntityGollum) {
                     return new LOTRContainerGollum(entityplayer.field_71071_by, (LOTREntityGollum)entity);
                  }
               }

               if (ID == 12) {
                  return new LOTRContainerCraftingTable.WoodElven(entityplayer.field_71071_by, world, i, j, k);
               } else if (ID == 13) {
                  return new LOTRContainerCraftingTable.Gondorian(entityplayer.field_71071_by, world, i, j, k);
               } else if (ID == 14) {
                  return new LOTRContainerCraftingTable.Rohirric(entityplayer.field_71071_by, world, i, j, k);
               } else if (ID == 15 && LOTRItemPouch.isHoldingPouch(entityplayer, i)) {
                  return new LOTRContainerPouch(entityplayer, i);
               } else {
                  if (ID == 16) {
                     bookshelf = world.func_147438_o(i, j, k);
                     if (bookshelf instanceof LOTRTileEntityBarrel) {
                        return new LOTRContainerBarrel(entityplayer.field_71071_by, (LOTRTileEntityBarrel)bookshelf);
                     }
                  }

                  if (ID == 17) {
                     bookshelf = world.func_147438_o(i, j, k);
                     if (bookshelf instanceof LOTRTileEntityArmorStand) {
                        return new LOTRContainerArmorStand(entityplayer.field_71071_by, (LOTRTileEntityArmorStand)bookshelf);
                     }
                  }

                  if (ID == 18) {
                     return new LOTRContainerCraftingTable.Dunlending(entityplayer.field_71071_by, world, i, j, k);
                  } else {
                     LOTREntityNPC npc;
                     if (ID == 22) {
                        entity = world.func_73045_a(i);
                        if (entity instanceof LOTREntityNPC) {
                           npc = (LOTREntityNPC)entity;
                           if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER) {
                              return new LOTRContainerHiredFarmerInventory(entityplayer.field_71071_by, npc);
                           }
                        }
                     }

                     if (ID == 23) {
                        return new LOTRContainerCraftingTable.Angmar(entityplayer.field_71071_by, world, i, j, k);
                     } else if (ID == 25) {
                        return new LOTRContainerCraftingTable.NearHarad(entityplayer.field_71071_by, world, i, j, k);
                     } else if (ID == 26) {
                        return new LOTRContainerCraftingTable.HighElven(entityplayer.field_71071_by, world, i, j, k);
                     } else if (ID == 27) {
                        return new LOTRContainerCraftingTable.BlueDwarven(entityplayer.field_71071_by, world, i, j, k);
                     } else if (ID == 28) {
                        return new LOTRContainerCraftingTable.Ranger(entityplayer.field_71071_by, world, i, j, k);
                     } else {
                        if (ID == 29) {
                           entity = world.func_73045_a(i);
                           if (entity instanceof LOTREntityHorse) {
                              LOTREntityHorse horse = (LOTREntityHorse)entity;
                              return new LOTRContainerMountInventory(entityplayer.field_71071_by, LOTRReflection.getHorseInv(horse), horse);
                           }

                           if (entity instanceof LOTREntityNPCRideable) {
                              LOTREntityNPCRideable npc = (LOTREntityNPCRideable)entity;
                              if (npc.getMountInventory() != null) {
                                 return new LOTRContainerNPCMountInventory(entityplayer.field_71071_by, npc.getMountInventory(), npc);
                              }
                           }
                        }

                        if (ID == 30) {
                           return new LOTRContainerCraftingTable.DolGuldur(entityplayer.field_71071_by, world, i, j, k);
                        } else if (ID == 31) {
                           return new LOTRContainerCraftingTable.Gundabad(entityplayer.field_71071_by, world, i, j, k);
                        } else if (ID == 34) {
                           return new LOTRContainerCraftingTable.HalfTroll(entityplayer.field_71071_by, world, i, j, k);
                        } else {
                           if (ID == 35) {
                              entity = world.func_73045_a(i);
                              if (entity instanceof LOTREntityNPC) {
                                 npc = (LOTREntityNPC)entity;
                                 return new LOTRContainerCoinExchange(entityplayer, npc);
                              }
                           }

                           if (ID == 36) {
                              return new LOTRContainerCraftingTable.DolAmroth(entityplayer.field_71071_by, world, i, j, k);
                           } else if (ID == 37) {
                              return new LOTRContainerCraftingTable.Moredain(entityplayer.field_71071_by, world, i, j, k);
                           } else {
                              if (ID == 38) {
                                 bookshelf = world.func_147438_o(i, j, k);
                                 if (bookshelf instanceof LOTRTileEntityUnsmeltery) {
                                    return new LOTRContainerUnsmeltery(entityplayer.field_71071_by, (LOTRTileEntityUnsmeltery)bookshelf);
                                 }
                              }

                              if (ID == 39) {
                                 return new LOTRContainerCraftingTable.Tauredain(entityplayer.field_71071_by, world, i, j, k);
                              } else {
                                 if (ID == 40) {
                                    bookshelf = world.func_147438_o(i, j, k);
                                    if (bookshelf instanceof LOTRTileEntityDartTrap) {
                                       return new ContainerDispenser(entityplayer.field_71071_by, (LOTRTileEntityDartTrap)bookshelf);
                                    }
                                 }

                                 if (ID == 41) {
                                    bookshelf = world.func_147438_o(i, j, k);
                                    if (bookshelf instanceof LOTRTileEntityChest) {
                                       return new ContainerChest(entityplayer.field_71071_by, (LOTRTileEntityChest)bookshelf);
                                    }
                                 }

                                 if (ID == 42) {
                                    return new LOTRContainerCraftingTable.Dale(entityplayer.field_71071_by, world, i, j, k);
                                 } else if (ID == 43) {
                                    return new LOTRContainerCraftingTable.Dorwinion(entityplayer.field_71071_by, world, i, j, k);
                                 } else if (ID == 44) {
                                    return new LOTRContainerCraftingTable.Hobbit(entityplayer.field_71071_by, world, i, j, k);
                                 } else {
                                    if (ID == 46) {
                                       entity = world.func_73045_a(i);
                                       if (entity instanceof LOTREntityNPC) {
                                          npc = (LOTREntityNPC)entity;
                                          if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
                                             return new LOTRContainerHiredWarriorInventory(entityplayer.field_71071_by, npc);
                                          }
                                       }
                                    }

                                    if (ID == 48 && entityplayer.field_71071_by.func_70448_g() != null && entityplayer.field_71071_by.func_70448_g().func_77973_b() instanceof LOTRItemDaleCracker) {
                                       return new LOTRContainerDaleCracker(entityplayer);
                                    } else if (ID == 49) {
                                       return new LOTRContainerCraftingTable.Rhun(entityplayer.field_71071_by, world, i, j, k);
                                    } else {
                                       if (ID == 50) {
                                          bookshelf = world.func_147438_o(i, j, k);
                                          if (bookshelf instanceof LOTRTileEntityBeacon) {
                                             ((LOTRTileEntityBeacon)bookshelf).addEditingPlayer(entityplayer);
                                          }
                                       }

                                       if (ID == 51) {
                                          return new LOTRContainerCraftingTable.Rivendell(entityplayer.field_71071_by, world, i, j, k);
                                       } else {
                                          if (ID == 52) {
                                             bookshelf = world.func_147438_o(i, j, k);
                                             if (bookshelf instanceof LOTRTileEntityMillstone) {
                                                return new LOTRContainerMillstone(entityplayer.field_71071_by, (LOTRTileEntityMillstone)bookshelf);
                                             }
                                          }

                                          if (ID == 53) {
                                             return new LOTRContainerAnvil(entityplayer, i, j, k);
                                          } else {
                                             if (ID == 54) {
                                                entity = world.func_73045_a(i);
                                                if (entity instanceof LOTREntityNPC) {
                                                   return new LOTRContainerAnvil(entityplayer, (LOTREntityNPC)entity);
                                                }
                                             }

                                             if (ID == 55) {
                                                bookshelf = world.func_147438_o(i, j, k);
                                                if (bookshelf instanceof LOTRTileEntityBookshelf) {
                                                   return new LOTRContainerBookshelf(entityplayer.field_71071_by, (LOTRTileEntityBookshelf)bookshelf);
                                                }
                                             }

                                             if (ID == 56) {
                                                return new LOTRContainerCraftingTable.Umbar(entityplayer.field_71071_by, world, i, j, k);
                                             } else if (ID == 57) {
                                                return new LOTRContainerCraftingTable.Gulf(entityplayer.field_71071_by, world, i, j, k);
                                             } else {
                                                if (ID == 59) {
                                                   entity = world.func_73045_a(i);
                                                   if (entity instanceof LOTRMercenary) {
                                                      return new LOTRContainerUnitTrade(entityplayer, (LOTRMercenary)entity, world);
                                                   }
                                                }

                                                if (ID == 62) {
                                                   return new LOTRContainerCraftingTable.Bree(entityplayer.field_71071_by, world, i, j, k);
                                                } else {
                                                   int slot;
                                                   if (testForSlotPackedGuiID(ID, 63)) {
                                                      slot = unpackSlot(ID);
                                                      if (LOTRItemPouch.isHoldingPouch(entityplayer, slot)) {
                                                         IInventory chest = LOTRItemPouch.getChestInvAt(entityplayer, world, i, j, k);
                                                         if (chest != null) {
                                                            return new LOTRContainerChestWithPouch(entityplayer, slot, chest);
                                                         }
                                                      }
                                                   }

                                                   if (testForSlotPackedGuiID(ID, 64)) {
                                                      slot = unpackSlot(ID);
                                                      if (LOTRItemPouch.isHoldingPouch(entityplayer, slot)) {
                                                         Entity minecart = world.func_73045_a(i);
                                                         if (minecart instanceof EntityMinecartContainer) {
                                                            return new LOTRContainerChestWithPouch(entityplayer, slot, (EntityMinecartContainer)minecart);
                                                         }
                                                      }
                                                   }

                                                   return null;
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public Object getClientGuiElement(int ID, EntityPlayer entityplayer, World world, int i, int j, int k) {
      TileEntity bookshelf;
      if (ID == 0) {
         bookshelf = world.func_147438_o(i, j, k);
         if (bookshelf instanceof LOTRTileEntityHobbitOven) {
            return new LOTRGuiHobbitOven(entityplayer.field_71071_by, (LOTRTileEntityHobbitOven)bookshelf);
         }
      }

      if (ID == 1) {
         return new LOTRGuiCraftingTable.Morgul(entityplayer.field_71071_by, world, i, j, k);
      } else if (ID == 2) {
         return new LOTRGuiCraftingTable.Elven(entityplayer.field_71071_by, world, i, j, k);
      } else {
         Entity entity;
         if (ID == 3) {
            entity = world.func_73045_a(i);
            if (entity instanceof LOTRTradeable) {
               return new LOTRGuiTrade(entityplayer.field_71071_by, (LOTRTradeable)entity, world);
            }
         }

         if (ID == 4) {
            return new LOTRGuiCraftingTable.Dwarven(entityplayer.field_71071_by, world, i, j, k);
         } else {
            if (ID == 5) {
               bookshelf = world.func_147438_o(i, j, k);
               if (bookshelf instanceof LOTRTileEntityAlloyForgeBase) {
                  return new LOTRGuiAlloyForge(entityplayer.field_71071_by, (LOTRTileEntityAlloyForgeBase)bookshelf);
               }
            }

            if (ID == 6) {
               return new LOTRGuiMobSpawner(world, i, j, k);
            } else {
               if (ID == 7) {
                  entity = world.func_73045_a(i);
                  if (entity instanceof LOTRUnitTradeable) {
                     return new LOTRGuiUnitTrade(entityplayer, (LOTRUnitTradeable)entity, world);
                  }
               }

               if (ID == 8) {
                  return new LOTRGuiCraftingTable.Uruk(entityplayer.field_71071_by, world, i, j, k);
               } else if (ID == 9) {
                  return new LOTRGuiHornSelect();
               } else {
                  if (ID == 10) {
                     entity = world.func_73045_a(i);
                     if (entity instanceof LOTREntityGollum) {
                        return new LOTRGuiGollum(entityplayer.field_71071_by, (LOTREntityGollum)entity);
                     }
                  }

                  if (ID == 11) {
                     return LOTRGuiMenu.openMenu(entityplayer);
                  } else if (ID == 12) {
                     return new LOTRGuiCraftingTable.WoodElven(entityplayer.field_71071_by, world, i, j, k);
                  } else if (ID == 13) {
                     return new LOTRGuiCraftingTable.Gondorian(entityplayer.field_71071_by, world, i, j, k);
                  } else if (ID == 14) {
                     return new LOTRGuiCraftingTable.Rohirric(entityplayer.field_71071_by, world, i, j, k);
                  } else if (ID == 15) {
                     return new LOTRGuiPouch(entityplayer, i);
                  } else {
                     if (ID == 16) {
                        bookshelf = world.func_147438_o(i, j, k);
                        if (bookshelf instanceof LOTRTileEntityBarrel) {
                           return new LOTRGuiBarrel(entityplayer.field_71071_by, (LOTRTileEntityBarrel)bookshelf);
                        }
                     }

                     if (ID == 17) {
                        bookshelf = world.func_147438_o(i, j, k);
                        if (bookshelf instanceof LOTRTileEntityArmorStand) {
                           return new LOTRGuiArmorStand(entityplayer.field_71071_by, (LOTRTileEntityArmorStand)bookshelf);
                        }
                     }

                     if (ID == 18) {
                        return new LOTRGuiCraftingTable.Dunlending(entityplayer.field_71071_by, world, i, j, k);
                     } else {
                        if (ID == 19) {
                           entity = world.func_73045_a(i);
                           if (entity instanceof LOTRTradeable) {
                              return new LOTRGuiTradeInteract((LOTREntityNPC)entity);
                           }
                        }

                        if (ID == 20) {
                           entity = world.func_73045_a(i);
                           if (entity instanceof LOTRUnitTradeable) {
                              return new LOTRGuiUnitTradeInteract((LOTREntityNPC)entity);
                           }
                        }

                        if (ID == 21) {
                           entity = world.func_73045_a(i);
                           if (entity instanceof LOTREntityNPC) {
                              return new LOTRGuiHiredInteract((LOTREntityNPC)entity);
                           }
                        }

                        LOTREntityNPC npc;
                        if (ID == 22) {
                           entity = world.func_73045_a(i);
                           if (entity instanceof LOTREntityNPC) {
                              npc = (LOTREntityNPC)entity;
                              if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER) {
                                 return new LOTRGuiHiredFarmerInventory(entityplayer.field_71071_by, npc);
                              }
                           }
                        }

                        if (ID == 23) {
                           return new LOTRGuiCraftingTable.Angmar(entityplayer.field_71071_by, world, i, j, k);
                        } else {
                           if (ID == 24) {
                              entity = world.func_73045_a(i);
                              if (entity instanceof LOTRTradeable) {
                                 return new LOTRGuiTradeUnitTradeInteract((LOTREntityNPC)entity);
                              }
                           }

                           if (ID == 25) {
                              return new LOTRGuiCraftingTable.NearHarad(entityplayer.field_71071_by, world, i, j, k);
                           } else if (ID == 26) {
                              return new LOTRGuiCraftingTable.HighElven(entityplayer.field_71071_by, world, i, j, k);
                           } else if (ID == 27) {
                              return new LOTRGuiCraftingTable.BlueDwarven(entityplayer.field_71071_by, world, i, j, k);
                           } else if (ID == 28) {
                              return new LOTRGuiCraftingTable.Ranger(entityplayer.field_71071_by, world, i, j, k);
                           } else {
                              if (ID == 29) {
                                 entity = world.func_73045_a(i);
                                 if (entity instanceof LOTREntityHorse) {
                                    LOTREntityHorse horse = (LOTREntityHorse)entity;
                                    return new LOTRGuiMountInventory(entityplayer.field_71071_by, new AnimalChest(horse.func_70005_c_(), j), horse);
                                 }

                                 if (entity instanceof LOTREntityNPCRideable) {
                                    LOTREntityNPCRideable npc = (LOTREntityNPCRideable)entity;
                                    if (npc.getMountInventory() != null) {
                                       return new LOTRGuiNPCMountInventory(entityplayer.field_71071_by, new AnimalChest(npc.func_70005_c_(), j), npc);
                                    }
                                 }
                              }

                              if (ID == 30) {
                                 return new LOTRGuiCraftingTable.DolGuldur(entityplayer.field_71071_by, world, i, j, k);
                              } else if (ID == 31) {
                                 return new LOTRGuiCraftingTable.Gundabad(entityplayer.field_71071_by, world, i, j, k);
                              } else if (ID == 32) {
                                 return new LOTRGuiRedBook();
                              } else if (ID == 33) {
                                 return new LOTRGuiSquadronItem();
                              } else if (ID == 34) {
                                 return new LOTRGuiCraftingTable.HalfTroll(entityplayer.field_71071_by, world, i, j, k);
                              } else {
                                 if (ID == 35) {
                                    entity = world.func_73045_a(i);
                                    if (entity instanceof LOTREntityNPC) {
                                       npc = (LOTREntityNPC)entity;
                                       return new LOTRGuiCoinExchange(entityplayer, npc);
                                    }
                                 }

                                 if (ID == 36) {
                                    return new LOTRGuiCraftingTable.DolAmroth(entityplayer.field_71071_by, world, i, j, k);
                                 } else if (ID == 37) {
                                    return new LOTRGuiCraftingTable.Moredain(entityplayer.field_71071_by, world, i, j, k);
                                 } else {
                                    if (ID == 38) {
                                       bookshelf = world.func_147438_o(i, j, k);
                                       if (bookshelf instanceof LOTRTileEntityUnsmeltery) {
                                          return new LOTRGuiUnsmeltery(entityplayer.field_71071_by, (LOTRTileEntityUnsmeltery)bookshelf);
                                       }
                                    }

                                    if (ID == 39) {
                                       return new LOTRGuiCraftingTable.Tauredain(entityplayer.field_71071_by, world, i, j, k);
                                    } else {
                                       if (ID == 40) {
                                          bookshelf = world.func_147438_o(i, j, k);
                                          if (bookshelf instanceof LOTRTileEntityDartTrap) {
                                             return new GuiDispenser(entityplayer.field_71071_by, (LOTRTileEntityDartTrap)bookshelf);
                                          }
                                       }

                                       if (ID == 41) {
                                          bookshelf = world.func_147438_o(i, j, k);
                                          if (bookshelf instanceof LOTRTileEntityChest) {
                                             return new GuiChest(entityplayer.field_71071_by, (LOTRTileEntityChest)bookshelf);
                                          }
                                       }

                                       if (ID == 42) {
                                          return new LOTRGuiCraftingTable.Dale(entityplayer.field_71071_by, world, i, j, k);
                                       } else if (ID == 43) {
                                          return new LOTRGuiCraftingTable.Dorwinion(entityplayer.field_71071_by, world, i, j, k);
                                       } else if (ID == 44) {
                                          return new LOTRGuiCraftingTable.Hobbit(entityplayer.field_71071_by, world, i, j, k);
                                       } else {
                                          if (ID == 45) {
                                             entity = world.func_73045_a(i);
                                             if (entity instanceof LOTREntityNPCRespawner) {
                                                return new LOTRGuiNPCRespawner((LOTREntityNPCRespawner)entity);
                                             }
                                          }

                                          if (ID == 46) {
                                             entity = world.func_73045_a(i);
                                             if (entity instanceof LOTREntityNPC) {
                                                npc = (LOTREntityNPC)entity;
                                                if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
                                                   return new LOTRGuiHiredWarriorInventory(entityplayer.field_71071_by, npc);
                                                }
                                             }
                                          }

                                          if (ID == 47) {
                                             Block block = world.func_147439_a(i, j, k);
                                             int meta = world.func_72805_g(i, j, k);
                                             LOTRTileEntitySign fake = (LOTRTileEntitySign)block.createTileEntity(world, meta);
                                             fake.func_145834_a(world);
                                             fake.field_145851_c = i;
                                             fake.field_145848_d = j;
                                             fake.field_145849_e = k;
                                             fake.isFakeGuiSign = true;
                                             return new LOTRGuiEditSign(fake);
                                          } else if (ID == 48 && entityplayer.field_71071_by.func_70448_g() != null && entityplayer.field_71071_by.func_70448_g().func_77973_b() instanceof LOTRItemDaleCracker) {
                                             return new LOTRGuiDaleCracker(entityplayer);
                                          } else if (ID == 49) {
                                             return new LOTRGuiCraftingTable.Rhun(entityplayer.field_71071_by, world, i, j, k);
                                          } else {
                                             if (ID == 50) {
                                                bookshelf = world.func_147438_o(i, j, k);
                                                if (bookshelf instanceof LOTRTileEntityBeacon) {
                                                   return new LOTRGuiBeacon((LOTRTileEntityBeacon)bookshelf);
                                                }
                                             }

                                             if (ID == 51) {
                                                return new LOTRGuiCraftingTable.Rivendell(entityplayer.field_71071_by, world, i, j, k);
                                             } else {
                                                if (ID == 52) {
                                                   bookshelf = world.func_147438_o(i, j, k);
                                                   if (bookshelf instanceof LOTRTileEntityMillstone) {
                                                      return new LOTRGuiMillstone(entityplayer.field_71071_by, (LOTRTileEntityMillstone)bookshelf);
                                                   }
                                                }

                                                if (ID == 53) {
                                                   return new LOTRGuiAnvil(entityplayer, i, j, k);
                                                } else {
                                                   if (ID == 54) {
                                                      entity = world.func_73045_a(i);
                                                      if (entity instanceof LOTREntityNPC) {
                                                         return new LOTRGuiAnvil(entityplayer, (LOTREntityNPC)entity);
                                                      }
                                                   }

                                                   if (ID == 55) {
                                                      if (world.func_147439_a(i, j, k) == Blocks.field_150342_X) {
                                                         world.func_147465_d(i, j, k, LOTRMod.bookshelfStorage, 0, 3);
                                                      }

                                                      bookshelf = world.func_147438_o(i, j, k);
                                                      if (bookshelf instanceof LOTRTileEntityBookshelf) {
                                                         return new LOTRGuiBookshelf(entityplayer.field_71071_by, (LOTRTileEntityBookshelf)bookshelf);
                                                      }
                                                   }

                                                   if (ID == 56) {
                                                      return new LOTRGuiCraftingTable.Umbar(entityplayer.field_71071_by, world, i, j, k);
                                                   } else if (ID == 57) {
                                                      return new LOTRGuiCraftingTable.Gulf(entityplayer.field_71071_by, world, i, j, k);
                                                   } else {
                                                      if (ID == 58) {
                                                         entity = world.func_73045_a(i);
                                                         if (entity instanceof LOTRMercenary) {
                                                            return new LOTRGuiMercenaryInteract((LOTREntityNPC)entity);
                                                         }
                                                      }

                                                      if (ID == 59) {
                                                         entity = world.func_73045_a(i);
                                                         if (entity instanceof LOTRMercenary) {
                                                            return new LOTRGuiMercenaryHire(entityplayer, (LOTRMercenary)entity, world);
                                                         }
                                                      }

                                                      if (ID == 60) {
                                                         return (new LOTRGuiMap()).setConquestGrid();
                                                      } else if (ID == 61) {
                                                         return new LOTRGuiBrandingIron();
                                                      } else if (ID == 62) {
                                                         return new LOTRGuiCraftingTable.Bree(entityplayer.field_71071_by, world, i, j, k);
                                                      } else {
                                                         int slot;
                                                         if (testForSlotPackedGuiID(ID, 63)) {
                                                            slot = unpackSlot(ID);
                                                            IInventory chest = LOTRItemPouch.getChestInvAt(entityplayer, world, i, j, k);
                                                            if (chest != null) {
                                                               return new LOTRGuiChestWithPouch(entityplayer, slot, chest);
                                                            }
                                                         }

                                                         if (testForSlotPackedGuiID(ID, 64)) {
                                                            slot = unpackSlot(ID);
                                                            Entity minecart = world.func_73045_a(i);
                                                            if (minecart instanceof EntityMinecartContainer) {
                                                               return new LOTRGuiChestWithPouch(entityplayer, slot, (EntityMinecartContainer)minecart);
                                                            }
                                                         }

                                                         return null;
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void sendClientsideGUI(EntityPlayerMP entityplayer, int guiID, int x, int y, int z) {
      LOTRPacketClientsideGUI packet = new LOTRPacketClientsideGUI(guiID, x, y, z);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   public boolean isClient() {
      return false;
   }

   public boolean isSingleplayer() {
      return false;
   }

   public World getClientWorld() {
      return null;
   }

   public EntityPlayer getClientPlayer() {
      return null;
   }

   public boolean isPaused() {
      return false;
   }

   public void setClientDifficulty(EnumDifficulty difficulty) {
   }

   public void setWaypointModes(boolean showWP, boolean showCWP, boolean showHiddenSWP) {
   }

   public void spawnAlignmentBonus(LOTRFaction faction, float prevMainAlignment, LOTRAlignmentBonusMap factionBonusMap, String name, boolean isKill, boolean isHiredKill, float conquestBonus, double posX, double posY, double posZ) {
   }

   public void displayAlignDrain(int numFactions) {
   }

   public void queueAchievement(LOTRAchievement achievement) {
   }

   public void queueFellowshipNotification(IChatComponent message) {
   }

   public void displayFellowshipAcceptInvitationResult(UUID fellowshipID, String name, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult result) {
   }

   public void queueConquestNotification(LOTRFaction fac, float conq, boolean isCleansing) {
   }

   public void displayMessage(LOTRGuiMessageTypes message) {
   }

   public void openHiredNPCGui(LOTREntityNPC npc) {
   }

   public void setMapIsOp(boolean isOp) {
   }

   public void displayFTScreen(LOTRAbstractWaypoint waypoint, int startX, int startZ) {
   }

   public void showFrostOverlay() {
   }

   public void showBurnOverlay() {
   }

   public void clearMapPlayerLocations() {
   }

   public void addMapPlayerLocation(GameProfile player, double posX, double posZ) {
   }

   public void setMapCWPProtectionMessage(IChatComponent message) {
   }

   public void displayBannerGui(LOTREntityBanner banner) {
   }

   public void validateBannerUsername(LOTREntityBanner banner, int slot, String prevText, boolean valid) {
   }

   public void clientReceiveSpeech(LOTREntityNPC npc, String speech) {
   }

   public void displayNewDate() {
   }

   public void displayMiniquestOffer(LOTRMiniQuest quest, LOTREntityNPC npc) {
   }

   public void setTrackedQuest(LOTRMiniQuest quest) {
   }

   public void displayAlignmentSee(String username, Map alignments) {
   }

   public void displayAlignmentChoice() {
   }

   public void cancelItemHighlight() {
   }

   public void receiveConquestGrid(LOTRFaction conqFac, List allZones) {
   }

   public void handleInvasionWatch(int invasionEntityID, boolean overrideAlreadyWatched) {
   }

   public void setInPortal(EntityPlayer entityplayer) {
      if (!LOTRTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
         LOTRTickHandlerServer.playersInPortals.put(entityplayer, 0);
      }

   }

   public void setInElvenPortal(EntityPlayer entityplayer) {
      if (!LOTRTickHandlerServer.playersInElvenPortals.containsKey(entityplayer)) {
         LOTRTickHandlerServer.playersInElvenPortals.put(entityplayer, 0);
      }

   }

   public void setInMorgulPortal(EntityPlayer entityplayer) {
      if (!LOTRTickHandlerServer.playersInMorgulPortals.containsKey(entityplayer)) {
         LOTRTickHandlerServer.playersInMorgulPortals.put(entityplayer, 0);
      }

   }

   public void setInUtumnoReturnPortal(EntityPlayer entityplayer) {
   }

   public void setUtumnoReturnPortalCoords(EntityPlayer entityplayer, int x, int z) {
   }

   public void spawnParticle(String type, double d, double d1, double d2, double d3, double d4, double d5) {
   }

   public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
      world.func_147465_d(i, j, k, LOTRMod.flowerPot, 0, 3);
      LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
   }

   public void fillMugFromCauldron(World world, int i, int j, int k, int side, ItemStack itemstack) {
      world.func_72921_c(i, j, k, world.func_72805_g(i, j, k) - 1, 3);
   }

   public void usePouchOnChest(EntityPlayer entityplayer, World world, int i, int j, int k, int side, ItemStack itemstack, int pouchSlot) {
      entityplayer.openGui(LOTRMod.instance, packGuiIDWithSlot(63, pouchSlot), world, i, j, k);
   }

   public void renderCustomPotionEffect(int x, int y, PotionEffect effect, Minecraft mc) {
   }

   public int getBeaconRenderID() {
      return 0;
   }

   public int getBarrelRenderID() {
      return 0;
   }

   public int getOrcBombRenderID() {
      return 0;
   }

   public int getDoubleTorchRenderID() {
      return 0;
   }

   public int getMobSpawnerRenderID() {
      return 0;
   }

   public int getPlateRenderID() {
      return 0;
   }

   public int getStalactiteRenderID() {
      return 0;
   }

   public int getFlowerPotRenderID() {
      return 0;
   }

   public int getCloverRenderID() {
      return 0;
   }

   public int getEntJarRenderID() {
      return 0;
   }

   public int getTrollTotemRenderID() {
      return 0;
   }

   public int getFenceRenderID() {
      return 0;
   }

   public int getGrassRenderID() {
      return 0;
   }

   public int getFallenLeavesRenderID() {
      return 0;
   }

   public int getCommandTableRenderID() {
      return 0;
   }

   public int getButterflyJarRenderID() {
      return 0;
   }

   public int getUnsmelteryRenderID() {
      return 0;
   }

   public int getChestRenderID() {
      return 0;
   }

   public int getReedsRenderID() {
      return 0;
   }

   public int getWasteRenderID() {
      return 0;
   }

   public int getBeamRenderID() {
      return 0;
   }

   public int getVCauldronRenderID() {
      return 0;
   }

   public int getGrapevineRenderID() {
      return 0;
   }

   public int getThatchFloorRenderID() {
      return 0;
   }

   public int getTreasureRenderID() {
      return 0;
   }

   public int getFlowerRenderID() {
      return 0;
   }

   public int getDoublePlantRenderID() {
      return 0;
   }

   public int getBirdCageRenderID() {
      return 0;
   }

   public int getRhunFireJarRenderID() {
      return 0;
   }

   public int getCoralRenderID() {
      return 0;
   }

   public int getDoorRenderID() {
      return 0;
   }

   public int getRopeRenderID() {
      return 0;
   }

   public int getOrcChainRenderID() {
      return 0;
   }

   public int getGuldurilRenderID() {
      return 0;
   }

   public int getOrcPlatingRenderID() {
      return 0;
   }

   public int getTrapdoorRenderID() {
      return 0;
   }
}
