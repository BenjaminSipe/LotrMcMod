package lotr.common.world.structure2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockArmorStand;
import lotr.common.block.LOTRBlockBarrel;
import lotr.common.block.LOTRBlockChest;
import lotr.common.block.LOTRBlockFlowerPot;
import lotr.common.block.LOTRBlockForgeBase;
import lotr.common.block.LOTRBlockGate;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import lotr.common.block.LOTRBlockHobbitOven;
import lotr.common.block.LOTRBlockKebabStand;
import lotr.common.block.LOTRBlockMug;
import lotr.common.block.LOTRBlockSpawnerChest;
import lotr.common.block.LOTRBlockWeaponRack;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.item.LOTREntityBannerWall;
import lotr.common.entity.item.LOTREntityRugBase;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import lotr.common.tileentity.LOTRTileEntityPlate;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import lotr.common.util.LOTRLog;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.world.structure.LOTRStructures;
import lotr.common.world.structure2.scan.LOTRStructureScan;
import lotr.common.world.village.LOTRVillageGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.FlowerEntry;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRWorldGenStructureBase2 extends WorldGenerator {
   public boolean restrictions = true;
   protected boolean notifyChanges;
   public EntityPlayer usingPlayer = null;
   public boolean shouldFindSurface = false;
   public LOTRVillageGen.AbstractInstance villageInstance;
   public LOTRStructureTimelapse.ThreadTimelapse threadTimelapse;
   protected int originX;
   protected int originY;
   protected int originZ;
   private int rotationMode;
   private StructureBoundingBox sbb;
   private LOTRStructureScan currentStrScan;
   private Map scanAliases = new HashMap();
   private Map scanAliasChances = new HashMap();

   public LOTRWorldGenStructureBase2(boolean flag) {
      super(flag);
      this.notifyChanges = flag;
   }

   public final boolean func_76484_a(World world, Random random, int i, int j, int k) {
      return this.generateWithSetRotation(world, random, i, j, k, random.nextInt(4));
   }

   public abstract boolean generateWithSetRotation(World var1, Random var2, int var3, int var4, int var5, int var6);

   protected void setupRandomBlocks(Random random) {
   }

   public int usingPlayerRotation() {
      return LOTRStructures.getRotationFromPlayer(this.usingPlayer);
   }

   public int getRotationMode() {
      return this.rotationMode;
   }

   protected void setOriginAndRotation(World world, int i, int j, int k, int rotation, int shift) {
      this.setOriginAndRotation(world, i, j, k, rotation, shift, 0);
   }

   protected void setOriginAndRotation(World world, int i, int j, int k, int rotation, int shift, int shiftX) {
      --j;
      this.rotationMode = rotation;
      switch(this.getRotationMode()) {
      case 0:
         k += shift;
         i += shiftX;
         break;
      case 1:
         i -= shift;
         k += shiftX;
         break;
      case 2:
         k -= shift;
         i -= shiftX;
         break;
      case 3:
         i += shift;
         k -= shiftX;
      }

      this.originX = i;
      this.originY = j;
      this.originZ = k;
      if (this.shouldFindSurface) {
         this.shouldFindSurface = false;
         this.findSurface(world, -shiftX, -shift);
      }

   }

   protected void findSurface(World world, int i, int k) {
      for(int j = 8; this.getY(j) >= 0; --j) {
         if (this.isSurface(world, i, j, k)) {
            this.originY = this.getY(j);
            break;
         }
      }

   }

   public void setStructureBB(StructureBoundingBox box) {
      this.sbb = box;
   }

   public boolean hasSBB() {
      return this.sbb != null;
   }

   private boolean isInSBB(int i, int j, int k) {
      return this.sbb == null ? true : this.sbb.func_78890_b(i, j, k);
   }

   protected void setBlockAndMetadata(World world, int i, int j, int k, Block block, int meta) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         meta = this.rotateMeta(block, meta);
         super.func_150516_a(world, i, j, k, block, meta);
         if (meta != 0 && (block instanceof BlockChest || block instanceof LOTRBlockChest || block instanceof LOTRBlockSpawnerChest || block instanceof BlockFurnace || block instanceof LOTRBlockHobbitOven || block instanceof LOTRBlockForgeBase)) {
            world.func_72921_c(i, j, k, meta, this.notifyChanges ? 3 : 2);
         }

         if (block != Blocks.field_150350_a && this.threadTimelapse != null) {
            this.threadTimelapse.onBlockSet();
         }

      }
   }

   protected int rotateMeta(Block block, int meta) {
      int i;
      int j;
      if (block instanceof BlockRotatedPillar) {
         i = meta & 3;
         j = meta & 12;
         if (j == 0) {
            return meta;
         } else if (this.rotationMode != 0 && this.rotationMode != 2) {
            if (j == 4) {
               j = 8;
            } else if (j == 8) {
               j = 4;
            }

            return j | i;
         } else {
            return meta;
         }
      } else {
         int l;
         if (block instanceof BlockStairs) {
            i = meta & 3;
            j = meta & 4;

            for(l = 0; l < this.rotationMode; ++l) {
               if (i == 2) {
                  i = 1;
               } else if (i == 1) {
                  i = 3;
               } else if (i == 3) {
                  i = 0;
               } else if (i == 0) {
                  i = 2;
               }
            }

            return j | i;
         } else if (!(block instanceof LOTRBlockMug) && !(block instanceof BlockTripWireHook) && !(block instanceof BlockAnvil)) {
            if (block instanceof LOTRBlockArmorStand) {
               i = meta & 3;
               j = meta & 4;

               for(l = 0; l < this.rotationMode; ++l) {
                  i = Direction.field_71577_f[i];
               }

               return j | i;
            } else if (block instanceof LOTRBlockWeaponRack) {
               i = meta & 3;
               j = meta & 4;

               for(l = 0; l < this.rotationMode; ++l) {
                  i = Direction.field_71577_f[i];
               }

               return j | i;
            } else if (block != Blocks.field_150444_as && !(block instanceof BlockLadder) && !(block instanceof BlockFurnace) && !(block instanceof BlockChest) && !(block instanceof LOTRBlockChest) && !(block instanceof LOTRBlockBarrel) && !(block instanceof LOTRBlockHobbitOven) && !(block instanceof LOTRBlockForgeBase) && !(block instanceof LOTRBlockKebabStand)) {
               if (block == Blocks.field_150472_an) {
                  i = meta + this.rotationMode * 4;
                  i &= 15;
                  return i;
               } else {
                  boolean east;
                  if (block instanceof BlockBed) {
                     i = meta;
                     east = meta >= 8;
                     if (east) {
                        i = meta - 8;
                     }

                     for(l = 0; l < this.rotationMode; ++l) {
                        i = Direction.field_71577_f[i];
                     }

                     if (east) {
                        i += 8;
                     }

                     return i;
                  } else if (block instanceof BlockTorch) {
                     if (meta == 5) {
                        return 5;
                     } else {
                        i = meta;

                        for(j = 0; j < this.rotationMode; ++j) {
                           if (i == 4) {
                              i = 1;
                           } else if (i == 1) {
                              i = 3;
                           } else if (i == 3) {
                              i = 2;
                           } else if (i == 2) {
                              i = 4;
                           }
                        }

                        return i;
                     }
                  } else if (block instanceof BlockDoor) {
                     if ((meta & 8) != 0) {
                        return meta;
                     } else {
                        i = meta & 3;
                        j = meta & 4;

                        for(l = 0; l < this.rotationMode; ++l) {
                           i = Direction.field_71577_f[i];
                        }

                        return j | i;
                     }
                  } else if (block instanceof BlockTrapDoor) {
                     i = meta & 3;
                     j = meta & 4;
                     l = meta & 8;

                     for(int l = 0; l < this.rotationMode; ++l) {
                        if (i == 0) {
                           i = 3;
                        } else if (i == 1) {
                           i = 2;
                        } else if (i == 2) {
                           i = 0;
                        } else if (i == 3) {
                           i = 1;
                        }
                     }

                     return l | j | i;
                  } else if (block instanceof BlockFenceGate) {
                     i = meta & 3;
                     j = meta & 4;

                     for(l = 0; l < this.rotationMode; ++l) {
                        i = Direction.field_71577_f[i];
                     }

                     return j | i;
                  } else if (block instanceof BlockPumpkin) {
                     i = meta;

                     for(j = 0; j < this.rotationMode; ++j) {
                        i = Direction.field_71577_f[i];
                     }

                     return i;
                  } else if (block instanceof BlockSkull) {
                     if (meta < 2) {
                        return meta;
                     } else {
                        i = Direction.field_71579_d[meta];

                        for(j = 0; j < this.rotationMode; ++j) {
                           i = Direction.field_71577_f[i];
                        }

                        return Direction.field_71582_c[i];
                     }
                  } else if (block instanceof LOTRBlockGate) {
                     i = meta & 7;
                     j = meta & 8;
                     if (i != 0 && i != 1) {
                        for(l = 0; l < this.rotationMode; ++l) {
                           i = Direction.field_71579_d[i];
                           i = Direction.field_71577_f[i];
                           i = Direction.field_71582_c[i];
                        }
                     }

                     return j | i;
                  } else if (block instanceof BlockLever) {
                     i = meta & 7;
                     j = meta & 8;
                     if (i != 0 && i != 7) {
                        if (i == 5 || i == 6) {
                           for(l = 0; l < this.rotationMode; ++l) {
                              i = i == 5 ? 6 : 5;
                           }
                        } else {
                           for(l = 0; l < this.rotationMode; ++l) {
                              if (i == 4) {
                                 i = 1;
                              } else if (i == 1) {
                                 i = 3;
                              } else if (i == 3) {
                                 i = 2;
                              } else if (i == 2) {
                                 i = 4;
                              }
                           }
                        }
                     } else {
                        for(l = 0; l < this.rotationMode; ++l) {
                           i = i == 0 ? 7 : 0;
                        }
                     }

                     return j | i;
                  } else if (block instanceof BlockButton) {
                     i = meta;
                     j = meta & 8;

                     for(l = 0; l < this.rotationMode; ++l) {
                        if (i == 4) {
                           i = 1;
                        } else if (i == 1) {
                           i = 3;
                        } else if (i == 3) {
                           i = 2;
                        } else if (i == 2) {
                           i = 4;
                        }
                     }

                     return j | i;
                  } else if (!(block instanceof BlockVine)) {
                     return meta;
                  } else {
                     boolean west = (meta & 2) != 0;
                     east = (meta & 8) != 0;
                     boolean north = (meta & 4) != 0;
                     boolean south = (meta & 1) != 0;

                     for(int l = 0; l < this.rotationMode; ++l) {
                        boolean newEast = north;
                        boolean newSouth = east;
                        boolean newWest = south;
                        north = west;
                        east = newEast;
                        south = newSouth;
                        west = newWest;
                     }

                     return (west ? 2 : 0) | (east ? 8 : 0) | (north ? 4 : 0) | (south ? 1 : 0);
                  }
               }
            } else if (meta == 0 && (block instanceof BlockFurnace || block instanceof BlockChest || block instanceof LOTRBlockChest || block instanceof LOTRBlockHobbitOven || block instanceof LOTRBlockForgeBase)) {
               return meta;
            } else {
               i = meta;

               for(j = 0; j < this.rotationMode; ++j) {
                  i = Direction.field_71579_d[i];
                  i = Direction.field_71577_f[i];
                  i = Direction.field_71582_c[i];
               }

               return i;
            }
         } else {
            i = meta;

            for(j = 0; j < this.rotationMode; ++j) {
               i = Direction.field_71577_f[i];
            }

            return i;
         }
      }
   }

   protected Block getBlock(World world, int i, int j, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      return !this.isInSBB(i, j, k) ? Blocks.field_150350_a : world.func_147439_a(i, j, k);
   }

   protected int getMeta(World world, int i, int j, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      return !this.isInSBB(i, j, k) ? 0 : world.func_72805_g(i, j, k);
   }

   protected int getTopBlock(World world, int i, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      return !this.isInSBB(i, 0, k) ? 0 : world.func_72825_h(i, k) - this.originY;
   }

   protected BiomeGenBase getBiome(World world, int i, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      return !this.isInSBB(i, 0, k) ? null : world.func_72807_a(i, k);
   }

   protected boolean isAir(World world, int i, int j, int k) {
      return this.getBlock(world, i, j, k).func_149688_o() == Material.field_151579_a;
   }

   protected boolean isOpaque(World world, int i, int j, int k) {
      return this.getBlock(world, i, j, k).func_149662_c();
   }

   protected boolean isReplaceable(World world, int i, int j, int k) {
      return this.getBlock(world, i, j, k).isReplaceable(world, this.getX(i, k), this.getY(j), this.getZ(i, k));
   }

   protected boolean isSideSolid(World world, int i, int j, int k, ForgeDirection side) {
      return this.getBlock(world, i, j, k).isSideSolid(world, this.getX(i, k), this.getY(j), this.getZ(i, k), side);
   }

   protected TileEntity getTileEntity(World world, int i, int j, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      return !this.isInSBB(i, j, k) ? null : world.func_147438_o(i, j, k);
   }

   protected void placeChest(World world, Random random, int i, int j, int k, int meta, LOTRChestContents contents) {
      this.placeChest(world, random, i, j, k, meta, contents, -1);
   }

   protected void placeChest(World world, Random random, int i, int j, int k, int meta, LOTRChestContents contents, int amount) {
      this.placeChest(world, random, i, j, k, Blocks.field_150486_ae, meta, contents, amount);
   }

   protected void placeChest(World world, Random random, int i, int j, int k, Block chest, int meta, LOTRChestContents contents) {
      this.placeChest(world, random, i, j, k, chest, meta, contents, -1);
   }

   protected void placeChest(World world, Random random, int i, int j, int k, Block chest, int meta, LOTRChestContents contents, int amount) {
      this.setBlockAndMetadata(world, i, j, k, chest, meta);
      this.fillChest(world, random, i, j, k, contents, amount);
   }

   protected void fillChest(World world, Random random, int i, int j, int k, LOTRChestContents contents, int amount) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         LOTRChestContents.fillChest(world, random, i, j, k, contents, amount);
      }
   }

   protected void putInventoryInChest(World world, int i, int j, int k, IInventory inv) {
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof IInventory) {
         IInventory blockInv = (IInventory)tileentity;

         for(int l = 0; l < blockInv.func_70302_i_() && l < inv.func_70302_i_(); ++l) {
            blockInv.func_70299_a(l, inv.func_70301_a(l));
         }
      }

   }

   protected void placeOrcTorch(World world, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j, k, LOTRMod.orcTorch, 0);
      this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.orcTorch, 1);
   }

   protected void placeMobSpawner(World world, int i, int j, int k, Class entityClass) {
      this.setBlockAndMetadata(world, i, j, k, LOTRMod.mobSpawner, 0);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof LOTRTileEntityMobSpawner) {
         ((LOTRTileEntityMobSpawner)tileentity).setEntityClassID(LOTREntities.getEntityIDFromClass(entityClass));
      }

   }

   protected void placeSpawnerChest(World world, int i, int j, int k, Block block, int meta, Class entityClass) {
      this.placeSpawnerChest(world, (Random)null, i, j, k, block, meta, entityClass, (LOTRChestContents)null);
   }

   protected void placeSpawnerChest(World world, Random random, int i, int j, int k, Block block, int meta, Class entityClass, LOTRChestContents contents) {
      this.placeSpawnerChest(world, random, i, j, k, block, meta, entityClass, contents, -1);
   }

   protected void placeSpawnerChest(World world, Random random, int i, int j, int k, Block block, int meta, Class entityClass, LOTRChestContents contents, int amount) {
      this.setBlockAndMetadata(world, i, j, k, block, meta);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof LOTRTileEntitySpawnerChest) {
         ((LOTRTileEntitySpawnerChest)tileentity).setMobID(entityClass);
      }

      if (contents != null) {
         this.fillChest(world, random, i, j, k, contents, amount);
      }

   }

   protected void placePlate(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
      this.placePlate_list(world, random, i, j, k, plateBlock, foodList, false);
   }

   protected void placePlateWithCertainty(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
      this.placePlate_list(world, random, i, j, k, plateBlock, foodList, true);
   }

   protected void placePlate_list(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList, boolean certain) {
      ItemStack food = foodList.getRandomFoodForPlate(random);
      if (random.nextInt(4) == 0) {
         food.field_77994_a += 1 + random.nextInt(3);
      }

      this.placePlate_item(world, random, i, j, k, plateBlock, food, certain);
   }

   protected void placePlate_item(World world, Random random, int i, int j, int k, Block plateBlock, ItemStack foodItem, boolean certain) {
      this.placePlate_do(world, random, i, j, k, plateBlock, foodItem, certain);
   }

   private void placePlate_do(World world, Random random, int i, int j, int k, Block plateBlock, ItemStack foodItem, boolean certain) {
      if (certain || !random.nextBoolean()) {
         this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
         if (certain || random.nextBoolean()) {
            TileEntity tileentity = this.getTileEntity(world, i, j, k);
            if (tileentity instanceof LOTRTileEntityPlate) {
               LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
               plate.setFoodItem(foodItem);
            }
         }

      }
   }

   protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
      this.placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
   }

   protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, ItemStack drink) {
      this.setBlockAndMetadata(world, i, j, k, LOTRMod.barrel, meta);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof LOTRTileEntityBarrel) {
         LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)tileentity;
         barrel.barrelMode = 2;
         drink = drink.func_77946_l();
         LOTRItemMug.setStrengthMeta(drink, MathHelper.func_76136_a(random, 1, 3));
         LOTRItemMug.setVessel(drink, LOTRItemMug.Vessel.MUG, true);
         drink.field_77994_a = MathHelper.func_76136_a(random, LOTRBrewingRecipes.BARREL_CAPACITY / 2, LOTRBrewingRecipes.BARREL_CAPACITY);
         barrel.func_70299_a(9, drink);
      }

   }

   protected void placeMug(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
      this.placeMug(world, random, i, j, k, meta, foodList.getRandomPlaceableDrink(random), foodList);
   }

   protected void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, LOTRFoods foodList) {
      this.placeMug(world, random, i, j, k, meta, drink, foodList.getPlaceableDrinkVessels());
   }

   protected void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, LOTRItemMug.Vessel[] vesselTypes) {
      LOTRItemMug.Vessel vessel = vesselTypes[random.nextInt(vesselTypes.length)];
      this.setBlockAndMetadata(world, i, j, k, vessel.getBlock(), meta);
      if (random.nextInt(3) != 0) {
         int i1 = i;
         i = this.getX(i, k);
         k = this.getZ(i1, k);
         j = this.getY(j);
         if (!this.isInSBB(i, j, k)) {
            return;
         }

         drink = drink.func_77946_l();
         drink.field_77994_a = 1;
         if (drink.func_77973_b() instanceof LOTRItemMug && ((LOTRItemMug)drink.func_77973_b()).isBrewable) {
            LOTRItemMug.setStrengthMeta(drink, MathHelper.func_76136_a(random, 1, 3));
         }

         LOTRItemMug.setVessel(drink, vessel, true);
         LOTRBlockMug.setMugItem(world, i, j, k, drink, vessel);
      }

   }

   protected void placeKebabStand(World world, Random random, int i, int j, int k, Block block, int meta) {
      this.setBlockAndMetadata(world, i, j, k, block, meta);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof LOTRTileEntityKebabStand) {
         LOTRTileEntityKebabStand stand = (LOTRTileEntityKebabStand)tileentity;
         int kebab = MathHelper.func_76136_a(random, 1, 8);
         stand.generateCookedKebab(kebab);
      }

   }

   protected void plantFlower(World world, Random random, int i, int j, int k) {
      ItemStack itemstack = this.getRandomFlower(world, random);
      this.setBlockAndMetadata(world, i, j, k, Block.func_149634_a(itemstack.func_77973_b()), itemstack.func_77960_j());
   }

   protected void placeRandomFlowerPot(World world, Random random, int i, int j, int k) {
      this.placeFlowerPot(world, i, j, k, this.getRandomFlower(world, random));
   }

   protected void placeFlowerPot(World world, int i, int j, int k, ItemStack itemstack) {
      boolean vanilla = itemstack == null || itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_150434_aF);
      if (vanilla) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150457_bL, 0);
      } else {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.flowerPot, 0);
      }

      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         if (itemstack != null) {
            if (vanilla) {
               TileEntity te = world.func_147438_o(i, j, k);
               if (te instanceof TileEntityFlowerPot) {
                  TileEntityFlowerPot pot = (TileEntityFlowerPot)te;
                  pot.func_145964_a(itemstack.func_77973_b(), itemstack.func_77960_j());
                  pot.func_70296_d();
               }
            } else {
               LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
            }
         }

      }
   }

   protected ItemStack getRandomFlower(World world, Random random) {
      int i = 0;
      int j = 0;
      int k = 0;
      BiomeGenBase biome = this.getBiome(world, i, k);
      if (biome instanceof LOTRBiome) {
         FlowerEntry fe = ((LOTRBiome)biome).getRandomFlower(world, random, this.getX(i, k), this.getY(j), this.getZ(i, k));
         return new ItemStack(fe.block, 1, fe.metadata);
      } else {
         return random.nextBoolean() ? new ItemStack(Blocks.field_150327_N, 0) : new ItemStack(Blocks.field_150328_O, 0);
      }
   }

   protected ItemStack getRandomTallGrass(World world, Random random) {
      BiomeGenBase biome = this.getBiome(world, 0, 0);
      if (biome instanceof LOTRBiome) {
         LOTRBiome.GrassBlockAndMeta gbm = ((LOTRBiome)biome).getRandomGrass(random);
         return new ItemStack(gbm.block, 1, gbm.meta);
      } else {
         return new ItemStack(Blocks.field_150329_H, 1, 1);
      }
   }

   protected void plantTallGrass(World world, Random random, int i, int j, int k) {
      ItemStack itemstack = this.getRandomTallGrass(world, random);
      this.setBlockAndMetadata(world, i, j, k, Block.func_149634_a(itemstack.func_77973_b()), itemstack.func_77960_j());
   }

   protected void spawnItemFrame(World world, int i, int j, int k, int direction, ItemStack itemstack) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         for(int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.field_71577_f[direction];
         }

         EntityItemFrame frame = new EntityItemFrame(world, i, j, k, direction);
         frame.func_82334_a(itemstack);
         world.func_72838_d(frame);
      }
   }

   protected void placeArmorStand(World world, int i, int j, int k, int direction, ItemStack[] armor) {
      this.setBlockAndMetadata(world, i, j, k, LOTRMod.armorStand, direction);
      this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.armorStand, direction | 4);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof LOTRTileEntityArmorStand) {
         LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand)tileentity;
         if (armor != null) {
            for(int l = 0; l < armor.length; ++l) {
               ItemStack armorPart = armor[l];
               if (armorPart == null) {
                  armorStand.func_70299_a(l, (ItemStack)null);
               } else {
                  armorStand.func_70299_a(l, armor[l].func_77946_l());
               }
            }
         }
      }

   }

   protected void placeWeaponRack(World world, int i, int j, int k, int meta, ItemStack weapon) {
      this.setBlockAndMetadata(world, i, j, k, LOTRMod.weaponRack, meta);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof LOTRTileEntityWeaponRack) {
         LOTRTileEntityWeaponRack weaponRack = (LOTRTileEntityWeaponRack)tileentity;
         if (weapon != null) {
            weaponRack.setWeaponItem(weapon.func_77946_l());
         }
      }

   }

   protected void placeBanner(World world, int i, int j, int k, LOTRItemBanner.BannerType bt, int direction) {
      this.placeBanner(world, i, j, k, bt, direction, false, 0);
   }

   protected void placeBanner(World world, int i, int j, int k, LOTRItemBanner.BannerType bt, int direction, boolean protection, int r) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         for(int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.field_71577_f[direction];
         }

         LOTREntityBanner banner = new LOTREntityBanner(world);
         banner.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, (float)direction * 90.0F, 0.0F);
         banner.setBannerType(bt);
         if (protection) {
            banner.setStructureProtection(true);
            banner.setSelfProtection(false);
         }

         if (r > 0) {
            if (r > 64) {
               throw new RuntimeException("WARNING: Banner protection range " + r + " is too large!");
            }

            banner.setCustomRange(r);
         }

         world.func_72838_d(banner);
      }
   }

   protected void placeWallBanner(World world, int i, int j, int k, LOTRItemBanner.BannerType bt, int direction) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         for(int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.field_71577_f[direction];
         }

         LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, direction);
         banner.setBannerType(bt);
         world.func_72838_d(banner);
      }
   }

   protected void setGrassToDirt(World world, int i, int j, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         world.func_147439_a(i, j, k).onPlantGrow(world, i, j, k, i, j, k);
      }
   }

   protected void setBiomeTop(World world, int i, int j, int k) {
      BiomeGenBase biome = this.getBiome(world, i, k);
      Block topBlock = biome.field_76752_A;
      int topMeta = 0;
      if (biome instanceof LOTRBiome) {
         topMeta = ((LOTRBiome)biome).topBlockMeta;
      }

      this.setBlockAndMetadata(world, i, j, k, topBlock, topMeta);
   }

   protected void setBiomeFiller(World world, int i, int j, int k) {
      BiomeGenBase biome = this.getBiome(world, i, k);
      Block fillerBlock = biome.field_76753_B;
      int fillerMeta = 0;
      if (biome instanceof LOTRBiome) {
         fillerMeta = ((LOTRBiome)biome).fillerBlockMeta;
      }

      this.setBlockAndMetadata(world, i, j, k, fillerBlock, fillerMeta);
   }

   protected void setAir(World world, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j, k, Blocks.field_150350_a, 0);
   }

   protected void placeSkull(World world, Random random, int i, int j, int k) {
      this.placeSkull(world, i, j, k, random.nextInt(16));
   }

   protected void placeSkull(World world, int i, int j, int k, int dir) {
      this.setBlockAndMetadata(world, i, j, k, Blocks.field_150465_bP, 1);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof TileEntitySkull) {
         TileEntitySkull skull = (TileEntitySkull)tileentity;
         dir += this.rotationMode * 4;
         dir %= 16;
         skull.func_145903_a(dir);
      }

   }

   protected void placeSign(World world, int i, int j, int k, Block block, int meta, String[] text) {
      this.setBlockAndMetadata(world, i, j, k, block, meta);
      TileEntity te = this.getTileEntity(world, i, j, k);
      if (te instanceof TileEntitySign) {
         TileEntitySign sign = (TileEntitySign)te;

         for(int l = 0; l < sign.field_145915_a.length; ++l) {
            sign.field_145915_a[l] = text[l];
         }
      }

   }

   protected void placeAnimalJar(World world, int i, int j, int k, Block block, int meta, EntityLiving creature) {
      this.setBlockAndMetadata(world, i, j, k, block, meta);
      TileEntity te = this.getTileEntity(world, i, j, k);
      if (te instanceof LOTRTileEntityAnimalJar) {
         LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)te;
         NBTTagCompound nbt = new NBTTagCompound();
         if (creature != null) {
            int i1 = this.getX(i, k);
            int j1 = this.getY(j);
            int k1 = this.getZ(i, k);
            creature.func_70107_b((double)i1 + 0.5D, (double)j1, (double)k1 + 0.5D);
            creature.func_110161_a((IEntityLivingData)null);
            if (creature.func_70039_c(nbt)) {
               jar.setEntityData(nbt);
            }
         }
      }

   }

   protected void placeIthildinDoor(World world, int i, int j, int k, Block block, int meta, LOTRBlockGateDwarvenIthildin.DoorSize doorSize) {
      int i1 = this.getX(i, k);
      int j1 = this.getY(j);
      int k1 = this.getZ(i, k);
      int xzFactorX = meta == 2 ? -1 : (meta == 3 ? 1 : 0);
      int xzFactorZ = meta == 4 ? 1 : (meta == 5 ? -1 : 0);

      for(int y = 0; y < doorSize.height; ++y) {
         for(int xz = 0; xz < doorSize.width; ++xz) {
            int i2 = i + xz * xzFactorX;
            int j2 = j + y;
            int k2 = k + xz * xzFactorZ;
            this.setBlockAndMetadata(world, i2, j2, k2, block, meta);
            LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)this.getTileEntity(world, i2, j2, k2);
            if (door != null) {
               door.setDoorSizeAndPos(doorSize, xz, y);
               door.setDoorBasePos(i1, j1, k1);
            }
         }
      }

   }

   protected void spawnNPCAndSetHome(EntityCreature entity, World world, int i, int j, int k, int homeDistance) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         entity.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
         entity.func_110161_a((IEntityLivingData)null);
         if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).setPersistentAndTraderShouldRespawn();
         }

         world.func_72838_d(entity);
         entity.func_110171_b(i, j, k, homeDistance);
      }
   }

   protected void leashEntityTo(EntityCreature entity, World world, int i, int j, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, i, j, k);
         entity.func_110162_b(leash, true);
      }
   }

   protected void placeNPCRespawner(LOTREntityNPCRespawner entity, World world, int i, int j, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         entity.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
         world.func_72838_d(entity);
      }
   }

   protected void placeRug(LOTREntityRugBase rug, World world, int i, int j, int k, float rotation) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (this.isInSBB(i, j, k)) {
         float f = rotation;
         switch(this.rotationMode) {
         case 0:
            f = rotation + 0.0F;
            break;
         case 1:
            f = rotation + 270.0F;
            break;
         case 2:
            f = rotation + 180.0F;
            break;
         case 3:
            f = rotation + 90.0F;
         }

         f %= 360.0F;
         rug.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, f, 0.0F);
         world.func_72838_d(rug);
      }
   }

   protected boolean generateSubstructure(LOTRWorldGenStructureBase2 str, World world, Random random, int i, int j, int k, int r) {
      return this.generateSubstructureWithRestrictionFlag(str, world, random, i, j, k, r, this.restrictions);
   }

   protected boolean generateSubstructureWithRestrictionFlag(LOTRWorldGenStructureBase2 str, World world, Random random, int i, int j, int k, int r, boolean isRestrict) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      r += this.rotationMode;
      r %= 4;
      str.restrictions = isRestrict;
      str.usingPlayer = this.usingPlayer;
      str.villageInstance = this.villageInstance;
      str.threadTimelapse = this.threadTimelapse;
      str.setStructureBB(this.sbb);
      return str.generateWithSetRotation(world, random, i, j, k, r);
   }

   protected void loadStrScan(String name) {
      this.currentStrScan = LOTRStructureScan.getScanByName(name);
      if (this.currentStrScan == null) {
         LOTRLog.logger.error("LOTR: Structure Scan for name " + name + " does not exist!!!");
      }

      this.scanAliases.clear();
   }

   protected void associateBlockAlias(String alias, Block block) {
      this.addBlockAliasOption(alias, 1, block);
   }

   protected void addBlockAliasOption(String alias, int weight, Block block) {
      this.addBlockMetaAliasOption(alias, weight, block, -1);
   }

   protected void associateBlockMetaAlias(String alias, Block block, int meta) {
      this.addBlockMetaAliasOption(alias, 1, block, meta);
   }

   protected void addBlockMetaAliasOption(String alias, int weight, Block block, int meta) {
      LOTRWorldGenStructureBase2.BlockAliasPool pool = (LOTRWorldGenStructureBase2.BlockAliasPool)this.scanAliases.get(alias);
      if (pool == null) {
         pool = new LOTRWorldGenStructureBase2.BlockAliasPool(alias);
         this.scanAliases.put(alias, pool);
      }

      pool.addEntry(1, block, meta);
   }

   protected void setBlockAliasChance(String alias, float chance) {
      this.scanAliasChances.put(alias, chance);
   }

   protected void clearScanAlias(String alias) {
      this.scanAliases.remove(alias);
      this.scanAliasChances.remove(alias);
   }

   protected void generateStrScan(World world, Random random, int i, int j, int k) {
      for(int pass = 0; pass <= 1; ++pass) {
         LOTRStructureScan.ScanStepBase step = null;

         try {
            Iterator it = this.currentStrScan.scanSteps.iterator();

            while(it.hasNext()) {
               step = (LOTRStructureScan.ScanStepBase)it.next();
               int i1 = i - step.x;
               int j1 = j + step.y;
               int k1 = k + step.z;
               LOTRWorldGenStructureBase2.BlockAliasPool.BlockMetaEntry scanBlockMeta = this.getScanStepBlockAndMeta(step, random);
               if (scanBlockMeta != null) {
                  Block block = scanBlockMeta.block;
                  int meta = scanBlockMeta.meta;
                  boolean inThisPass = false;
                  if (!block.func_149688_o().func_76218_k() && block != Blocks.field_150350_a) {
                     inThisPass = pass == 1;
                  } else {
                     inThisPass = pass == 0;
                  }

                  if (inThisPass) {
                     if (step.findLowest) {
                        while(this.getY(j1) > 0 && !this.getBlock(world, i1, j1 - 1, k1).func_149688_o().func_76230_c()) {
                           --j1;
                        }
                     }

                     if (step instanceof LOTRStructureScan.ScanStepSkull) {
                        this.placeSkull(world, random, i1, j1, k1);
                     } else {
                        this.setBlockAndMetadata(world, i1, j1, k1, block, meta);
                        if ((step.findLowest || j1 <= 1) && block.func_149662_c()) {
                           this.setGrassToDirt(world, i1, j1 - 1, k1);
                        }

                        if (step.fillDown) {
                           for(int j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
                              LOTRWorldGenStructureBase2.BlockAliasPool.BlockMetaEntry fillBlockMeta = this.getScanStepBlockAndMeta(step, random);
                              if (fillBlockMeta != null) {
                                 Block fillBlock = fillBlockMeta.block;
                                 int fillMeta = fillBlockMeta.meta;
                                 this.setBlockAndMetadata(world, i1, j2, k1, fillBlock, fillMeta);
                                 if (fillBlock.func_149662_c()) {
                                    this.setGrassToDirt(world, i1, j2 - 1, k1);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (Exception var20) {
            String msg = String.format("lotr: Error generating structure from Structure Scan %s, line %d", this.currentStrScan.scanName, step.lineNumber);
            throw new RuntimeException(msg, var20);
         }
      }

      this.currentStrScan = null;
      this.scanAliases.clear();
   }

   private LOTRWorldGenStructureBase2.BlockAliasPool.BlockMetaEntry getScanStepBlockAndMeta(LOTRStructureScan.ScanStepBase step, Random random) {
      Block aliasBlock = null;
      int aliasMeta = -1;
      if (step.hasAlias()) {
         String alias = step.getAlias();
         LOTRWorldGenStructureBase2.BlockAliasPool pool = (LOTRWorldGenStructureBase2.BlockAliasPool)this.scanAliases.get(alias);
         if (pool == null) {
            throw new IllegalArgumentException("No block associated to alias " + alias + " !");
         }

         LOTRWorldGenStructureBase2.BlockAliasPool.BlockMetaEntry e = pool.getEntry(random);
         aliasBlock = e.block;
         aliasMeta = e.meta;
         if (this.scanAliasChances.containsKey(alias)) {
            float chance = (Float)this.scanAliasChances.get(alias);
            if (random.nextFloat() >= chance) {
               return null;
            }
         }
      }

      return new LOTRWorldGenStructureBase2.BlockAliasPool.BlockMetaEntry(1, step.getBlock(aliasBlock), step.getMeta(aliasMeta));
   }

   protected int getX(int x, int z) {
      switch(this.rotationMode) {
      case 0:
         return this.originX - x;
      case 1:
         return this.originX - z;
      case 2:
         return this.originX + x;
      case 3:
         return this.originX + z;
      default:
         return this.originX;
      }
   }

   protected int getZ(int x, int z) {
      switch(this.rotationMode) {
      case 0:
         return this.originZ + z;
      case 1:
         return this.originZ - x;
      case 2:
         return this.originZ - z;
      case 3:
         return this.originZ + x;
      default:
         return this.originZ;
      }
   }

   protected int getY(int y) {
      return this.originY + y;
   }

   protected final boolean isSurface(World world, int i, int j, int k) {
      int i1 = i;
      i = this.getX(i, k);
      k = this.getZ(i1, k);
      j = this.getY(j);
      if (isSurfaceStatic(world, i, j, k)) {
         return true;
      } else {
         return this.villageInstance != null && this.villageInstance.isVillageSpecificSurface(world, i, j, k);
      }
   }

   public static boolean isSurfaceStatic(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      BiomeGenBase biome = world.func_72807_a(i, k);
      if (block instanceof BlockSlab && !block.func_149662_c()) {
         return isSurfaceStatic(world, i, j - 1, k);
      } else {
         Block above = world.func_147439_a(i, j + 1, k);
         if (above.func_149688_o().func_76224_d()) {
            return false;
         } else if (block != biome.field_76752_A && block != biome.field_76753_B) {
            if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150351_n && block != LOTRMod.dirtPath) {
               if (block != LOTRMod.mudGrass && block != LOTRMod.mud) {
                  if (block != Blocks.field_150354_m && block != LOTRMod.whiteSand) {
                     if (block != LOTRMod.mordorDirt && block != LOTRMod.mordorGravel) {
                        return (block == Blocks.field_150348_b || block instanceof BlockOre) && !above.func_149662_c();
                     } else {
                        return true;
                     }
                  } else {
                     return true;
                  }
               } else {
                  return true;
               }
            } else {
               return true;
            }
         } else {
            return true;
         }
      }
   }

   private static class BlockAliasPool {
      public final String alias;
      private List entries = new ArrayList();
      private int totalWeight;

      public BlockAliasPool(String a) {
         this.alias = a;
      }

      public void addEntry(int w, Block b, int m) {
         if (b == null) {
            throw new IllegalArgumentException("Cannot associate NULL to a strscan block alias! Alias = " + this.alias);
         } else {
            this.entries.add(new LOTRWorldGenStructureBase2.BlockAliasPool.BlockMetaEntry(w, b, m));
            this.totalWeight = WeightedRandom.func_76272_a(this.entries);
         }
      }

      public LOTRWorldGenStructureBase2.BlockAliasPool.BlockMetaEntry getEntry(Random random) {
         return (LOTRWorldGenStructureBase2.BlockAliasPool.BlockMetaEntry)WeightedRandom.func_76273_a(random, this.entries, this.totalWeight);
      }

      private static class BlockMetaEntry extends net.minecraft.util.WeightedRandom.Item {
         public final Block block;
         public final int meta;

         public BlockMetaEntry(int w, Block b, int m) {
            super(w);
            this.block = b;
            this.meta = m;
         }
      }
   }
}
