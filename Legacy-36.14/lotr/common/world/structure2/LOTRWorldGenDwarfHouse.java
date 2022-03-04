package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenDwarfHouse extends LOTRWorldGenStructureBase2 {
   protected Block stoneBlock;
   protected int stoneMeta;
   protected Block fillerBlock;
   protected int fillerMeta;
   protected Block topBlock;
   protected int topMeta;
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickStairBlock;
   protected Block brick2Block;
   protected int brick2Meta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block chandelierBlock;
   protected int chandelierMeta;
   protected Block tableBlock;
   protected Block barsBlock;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block plankStairBlock;
   protected Block carpetBlock;
   protected int carpetMeta;
   protected Block plateBlock;
   protected LOTRChestContents larderContents;
   protected LOTRChestContents personalContents;
   protected LOTRFoods plateFoods;
   protected LOTRFoods drinkFoods;

   public LOTRWorldGenDwarfHouse(boolean flag) {
      super(flag);
   }

   protected LOTREntityDwarf createDwarf(World world) {
      return new LOTREntityDwarf(world);
   }

   protected void setupRandomBlocks(Random random) {
      this.stoneBlock = Blocks.field_150348_b;
      this.stoneMeta = 0;
      this.fillerBlock = Blocks.field_150346_d;
      this.fillerMeta = 0;
      this.topBlock = Blocks.field_150349_c;
      this.topMeta = 0;
      this.brickBlock = LOTRMod.brick;
      this.brickMeta = 6;
      this.brickStairBlock = LOTRMod.stairsDwarvenBrick;
      this.brick2Block = Blocks.field_150417_aV;
      this.brick2Meta = 0;
      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 0;
      this.chandelierBlock = LOTRMod.chandelier;
      this.chandelierMeta = 8;
      this.tableBlock = LOTRMod.dwarvenTable;
      this.barsBlock = LOTRMod.dwarfBars;
      int randomWood = random.nextInt(4);
      if (randomWood == 0) {
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 1;
         this.plankSlabBlock = Blocks.field_150376_bx;
         this.plankSlabMeta = 1;
         this.plankStairBlock = Blocks.field_150485_bF;
      } else if (randomWood == 1) {
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 13;
         this.plankSlabBlock = LOTRMod.woodSlabSingle2;
         this.plankSlabMeta = 5;
         this.plankStairBlock = LOTRMod.stairsLarch;
      } else if (randomWood == 2) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 4;
         this.plankSlabBlock = LOTRMod.woodSlabSingle3;
         this.plankSlabMeta = 4;
         this.plankStairBlock = LOTRMod.stairsPine;
      } else if (randomWood == 3) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 3;
         this.plankSlabBlock = LOTRMod.woodSlabSingle3;
         this.plankSlabMeta = 3;
         this.plankStairBlock = LOTRMod.stairsFir;
      }

      this.carpetBlock = Blocks.field_150404_cg;
      int randomCarpet = random.nextInt(3);
      if (randomCarpet == 0) {
         this.carpetMeta = 7;
      } else if (randomCarpet == 1) {
         this.carpetMeta = 12;
      } else if (randomCarpet == 2) {
         this.carpetMeta = 15;
      }

      if (random.nextBoolean()) {
         this.plateBlock = LOTRMod.ceramicPlateBlock;
      } else {
         this.plateBlock = LOTRMod.woodPlateBlock;
      }

      this.larderContents = LOTRChestContents.DWARF_HOUSE_LARDER;
      this.personalContents = LOTRChestContents.DWARVEN_TOWER;
      this.plateFoods = LOTRFoods.DWARF;
      this.drinkFoods = LOTRFoods.DWARF_DRINK;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int maxChildren;
      int k2;
      int i3;
      if (this.restrictions && this.usingPlayer == null) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         int xzRange = 5;
         int yRange = 4;

         for(maxChildren = -xzRange; maxChildren <= xzRange; ++maxChildren) {
            for(k2 = -yRange; k2 <= yRange; ++k2) {
               for(i3 = -xzRange; i3 <= xzRange; ++i3) {
                  if (this.isAir(world, maxChildren, k2, i3)) {
                     return false;
                  }
               }
            }
         }
      } else {
         this.setOriginAndRotation(world, i, j, k, rotation, 8);
      }

      this.setupRandomBlocks(random);
      int k1;
      int k1;
      if (this.restrictions) {
         for(k1 = -1; k1 <= 1; ++k1) {
            for(k1 = 1; k1 <= 2; ++k1) {
               boolean foundAir = false;

               for(k2 = -8; k2 >= -14; --k2) {
                  if (this.isAir(world, k1, k1, k2)) {
                     foundAir = true;
                     break;
                  }
               }

               if (!foundAir) {
                  return false;
               }
            }
         }

         for(k1 = -1; k1 <= 1; ++k1) {
            for(k1 = 1; k1 <= 2; ++k1) {
               for(maxChildren = -8; maxChildren >= -14 && !this.isAir(world, k1, k1, maxChildren); --maxChildren) {
                  this.setAir(world, k1, k1, maxChildren);
                  if (k1 == 1) {
                     this.setBlockAndMetadata(world, k1, k1 - 1, maxChildren, this.stoneBlock, this.stoneMeta);
                  }
               }
            }
         }
      }

      int k3;
      int j1;
      int j1;
      for(k1 = -7; k1 <= 7; ++k1) {
         for(k1 = -7; k1 <= 7; ++k1) {
            maxChildren = Math.abs(k1);
            k2 = Math.abs(k1);
            i3 = (int)Math.round(Math.sqrt((double)(maxChildren * maxChildren + k2 * k2)));
            k3 = 13 - i3;
            k3 = Math.min(k3, 7);

            for(j1 = k3; (j1 >= -5 || !this.isOpaque(world, k1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               if (!this.isOpaque(world, k1, j1, k1)) {
                  Block block = null;
                  int meta = true;
                  if (j1 >= k3 - 4) {
                     if (this.isOpaque(world, k1, j1 + 1, k1)) {
                        block = this.fillerBlock;
                        j1 = this.fillerMeta;
                     } else {
                        block = this.topBlock;
                        j1 = this.topMeta;
                     }
                  } else {
                     block = this.stoneBlock;
                     j1 = this.stoneMeta;
                  }

                  if (block != null) {
                     this.setBlockAndMetadata(world, k1, j1, k1, block, j1);
                     this.setGrassToDirt(world, k1, j1 - 1, k1);
                  }
               }
            }
         }
      }

      for(k1 = 1; k1 <= 3; ++k1) {
         k1 = 5 - k1;
         if (k1 >= 3) {
            --k1;
         }

         for(maxChildren = -k1; maxChildren <= k1; ++maxChildren) {
            this.setBlockAndMetadata(world, maxChildren, k1, -7, this.stoneBlock, this.stoneMeta);
         }
      }

      for(k1 = -11; k1 <= 11; ++k1) {
         for(k1 = -11; k1 <= 11; ++k1) {
            maxChildren = Math.abs(k1);
            k2 = Math.abs(k1);
            if (maxChildren > 7 || k2 > 7) {
               i3 = Math.min(maxChildren, k2);
               k3 = Math.max(maxChildren, k2);
               j1 = k3 - 8;
               int[] var22 = new int[]{4, 7, 9};
               j1 = var22.length;

               int meta;
               for(int var16 = 0; var16 < j1; ++var16) {
                  meta = var22[var16];
                  if (i3 >= meta) {
                     j1 += i3 - meta;
                  }
               }

               int top = 0 - (i3 + j1) / 2;

               for(j1 = top; !this.isOpaque(world, k1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  Block block = null;
                  int meta = true;
                  if (j1 >= top - 4) {
                     if (this.isOpaque(world, k1, j1 + 1, k1)) {
                        block = this.fillerBlock;
                        meta = this.fillerMeta;
                     } else {
                        block = this.topBlock;
                        meta = this.topMeta;
                     }
                  } else {
                     block = this.stoneBlock;
                     meta = this.stoneMeta;
                  }

                  if (block != null) {
                     this.setBlockAndMetadata(world, k1, j1, k1, block, meta);
                     this.setGrassToDirt(world, k1, j1 - 1, k1);
                  }
               }
            }
         }
      }

      for(k1 = -6; k1 <= 6; ++k1) {
         for(k1 = -6; k1 <= 6; ++k1) {
            for(maxChildren = -4; maxChildren <= 4; ++maxChildren) {
               if (Math.abs(k1) != 6 && Math.abs(k1) != 6) {
                  if (maxChildren != 0 && Math.abs(maxChildren) != 4) {
                     this.setAir(world, k1, maxChildren, k1);
                  } else {
                     this.setBlockAndMetadata(world, k1, maxChildren, k1, this.brick2Block, this.brick2Meta);
                  }
               } else if (maxChildren == 2) {
                  this.setBlockAndMetadata(world, k1, maxChildren, k1, this.plankBlock, this.plankMeta);
               } else {
                  this.setBlockAndMetadata(world, k1, maxChildren, k1, this.brick2Block, this.brick2Meta);
               }
            }
         }
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         if (k1 != 0) {
            this.setBlockAndMetadata(world, -5, k1, -5, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, -5, k1, 5, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 5, k1, -5, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 5, k1, 5, this.pillarBlock, this.pillarMeta);
         }
      }

      this.setBlockAndMetadata(world, -4, 2, -5, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -5, 2, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -4, 2, 5, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -5, 2, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 4, 2, -5, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 5, 2, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 4, 2, 5, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 5, 2, 4, Blocks.field_150478_aa, 4);

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, k1, 3, -5, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 3, 5, this.brickStairBlock, 6);
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, -5, 3, k1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 5, 3, k1, this.brickStairBlock, 5);
      }

      for(k1 = 1; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, -1, k1, -6, this.pillarBlock, this.pillarMeta);
         this.setAir(world, 0, k1, -6);
         this.setBlockAndMetadata(world, 1, k1, -6, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, -1, k1, -7, this.stoneBlock, this.stoneMeta);
         this.setAir(world, 0, k1, -7);
         this.setBlockAndMetadata(world, 1, k1, -7, this.stoneBlock, this.stoneMeta);
      }

      this.placeIthildinDoor(world, 0, 1, -7, LOTRMod.dwarvenDoorIthildin, 3, LOTRBlockGateDwarvenIthildin.DoorSize._1x2);

      for(k1 = -4; k1 <= -3; ++k1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, k1, 1, k1, this.carpetBlock, this.carpetMeta);
         }
      }

      for(k1 = -1; k1 <= 3; ++k1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            if (Math.abs(k1) != 1 || k1 != -1 && k1 != 3) {
               this.setBlockAndMetadata(world, k1, 1, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            } else {
               this.setBlockAndMetadata(world, k1, 1, k1, this.plankBlock, this.plankMeta);
            }

            if (random.nextInt(3) == 0) {
               this.placeMug(world, random, k1, 2, k1, random.nextInt(4), this.drinkFoods);
            } else {
               this.placePlate(world, random, k1, 2, k1, this.plateBlock, this.plateFoods);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 3, 0, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, 0, 3, 2, this.chandelierBlock, this.chandelierMeta);

      for(k1 = 0; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, -3, 1, k1, this.plankStairBlock, 0);
         this.setBlockAndMetadata(world, 3, 1, k1, this.plankStairBlock, 1);
      }

      for(k1 = 4; k1 <= 6; ++k1) {
         for(k1 = 1; k1 <= 4; ++k1) {
            for(maxChildren = -2; maxChildren <= 2; ++maxChildren) {
               this.setBlockAndMetadata(world, maxChildren, k1, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(k1 = 1; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, -2, k1, 4, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, 2, k1, 4, this.pillarBlock, this.pillarMeta);
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, k1, 2, 4, this.barsBlock, 0);
         this.setBlockAndMetadata(world, k1, 3, 4, this.barsBlock, 0);
         this.setBlockAndMetadata(world, k1, 1, 5, LOTRMod.hearth, 0);
         this.setBlockAndMetadata(world, k1, 2, 5, Blocks.field_150480_ab, 0);
         this.setAir(world, k1, 3, 5);
      }

      for(k1 = -2; k1 <= 1; ++k1) {
         this.setAir(world, -5, 0, k1);
         this.setAir(world, 5, 0, k1);
         k1 = 1 - k1;

         for(maxChildren = -3; maxChildren < -3 + k1; ++maxChildren) {
            this.setBlockAndMetadata(world, -5, maxChildren, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 5, maxChildren, k1, this.brickBlock, this.brickMeta);
         }

         this.setBlockAndMetadata(world, -5, -3 + k1, k1, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, 5, -3 + k1, k1, this.brickStairBlock, 3);
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         for(k1 = -3; k1 <= -1; ++k1) {
            for(maxChildren = -1; maxChildren <= 1; ++maxChildren) {
               this.setBlockAndMetadata(world, maxChildren, k1, k1, this.plankBlock, this.plankMeta);
            }
         }
      }

      for(k1 = -3; k1 <= -1; ++k1) {
         this.setBlockAndMetadata(world, -2, k1, -5, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, -2, k1, 5, this.pillarBlock, this.pillarMeta);
      }

      this.setBlockAndMetadata(world, -5, -2, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, -2, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, -2, -4, Blocks.field_150478_aa, 3);

      for(k1 = -4; k1 <= 4; ++k1) {
         if (IntMath.mod(k1, 2) == 1) {
            this.setBlockAndMetadata(world, -2, -3, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            if (random.nextBoolean()) {
               this.placePlateWithCertainty(world, random, -2, -2, k1, this.plateBlock, this.plateFoods);
            } else {
               this.placeMug(world, random, -2, -2, k1, 1, this.drinkFoods);
            }
         } else {
            this.setBlockAndMetadata(world, -2, -3, k1, this.plankBlock, this.plankMeta);
         }

         this.setBlockAndMetadata(world, -2, -1, k1, this.brickStairBlock, 5);
      }

      for(k1 = -4; k1 <= -3; ++k1) {
         this.setBlockAndMetadata(world, k1, -3, -5, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, k1, -2, -6, this.plankBlock, this.plankMeta);
         this.placeBarrel(world, random, k1, -2, -5, 3, this.drinkFoods);
         this.setBlockAndMetadata(world, k1, -1, -5, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, k1, -3, 5, Blocks.field_150460_al, 2);
         this.setBlockAndMetadata(world, k1, -2, 6, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, k1, -1, 5, this.brickStairBlock, 6);
      }

      for(k1 = -4; k1 <= -3; ++k1) {
         this.setBlockAndMetadata(world, -5, -3, k1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -6, -2, k1, this.plankBlock, this.plankMeta);
         this.placeChest(world, random, -5, -2, k1, 4, this.larderContents);
         this.setBlockAndMetadata(world, -5, -1, k1, this.brickStairBlock, 4);
      }

      this.setBlockAndMetadata(world, -2, -3, 2, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -2, -3, 0, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -2, -3, -2, this.tableBlock, 0);

      for(k1 = -3; k1 <= -1; ++k1) {
         this.setBlockAndMetadata(world, 2, k1, -5, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, 2, k1, 5, this.pillarBlock, this.pillarMeta);
      }

      this.setBlockAndMetadata(world, 5, -2, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, -2, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 5, -2, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, -2, -4, Blocks.field_150478_aa, 3);

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, 2, -3, k1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 2, -1, k1, this.brickStairBlock, 4);
      }

      for(k1 = 3; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, k1, -3, -5, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, k1, -2, -6, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, k1, -1, -5, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, k1, -3, 5, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, k1, -2, 6, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, k1, -1, 5, this.brickStairBlock, 6);

         for(k1 = -2; k1 <= 0; ++k1) {
            this.setBlockAndMetadata(world, k1, -3, k1, this.carpetBlock, this.carpetMeta);
         }

         this.setBlockAndMetadata(world, k1, -3, -3, LOTRMod.dwarvenBed, 2);
         this.setBlockAndMetadata(world, k1, -3, -4, LOTRMod.dwarvenBed, 10);
         this.placeChest(world, random, k1, -2, -5, 3, this.personalContents, MathHelper.func_76136_a(random, 2, 4));
      }

      for(k1 = -4; k1 <= -3; ++k1) {
         this.setBlockAndMetadata(world, 5, -3, k1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 6, -2, k1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 5, -1, k1, this.brickStairBlock, 5);
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         ItemStack item;
         if (k1 == 0) {
            item = this.getRandomWeaponItem(random);
            this.placeWeaponRack(world, 2, -2, k1, 5, item);
         } else if (IntMath.mod(k1, 2) == 0) {
            item = random.nextBoolean() ? this.getRandomWeaponItem(random) : this.getRandomOtherItem(random);
            this.spawnItemFrame(world, 1, -2, k1, 1, item);
         }
      }

      LOTREntityDwarf dwarfMale = this.createDwarf(world);
      dwarfMale.familyInfo.setMale(true);
      dwarfMale.familyInfo.setName(LOTRNames.getDwarfName(random, dwarfMale.familyInfo.isMale()));
      this.spawnNPCAndSetHome(dwarfMale, world, 0, 2, 0, 8);
      LOTREntityDwarf dwarfFemale = this.createDwarf(world);
      dwarfFemale.familyInfo.setMale(false);
      dwarfFemale.familyInfo.setName(LOTRNames.getDwarfName(random, dwarfFemale.familyInfo.isMale()));
      this.spawnNPCAndSetHome(dwarfFemale, world, 0, 2, 0, 8);
      maxChildren = dwarfMale.familyInfo.getRandomMaxChildren();
      dwarfMale.func_70062_b(4, new ItemStack(LOTRMod.dwarvenRing));
      dwarfMale.familyInfo.spouseUniqueID = dwarfFemale.func_110124_au();
      dwarfMale.familyInfo.setMaxBreedingDelay();
      dwarfMale.familyInfo.maxChildren = maxChildren;
      dwarfFemale.func_70062_b(4, new ItemStack(LOTRMod.dwarvenRing));
      dwarfFemale.familyInfo.spouseUniqueID = dwarfMale.func_110124_au();
      dwarfFemale.familyInfo.setMaxBreedingDelay();
      dwarfFemale.familyInfo.maxChildren = maxChildren;
      return true;
   }

   protected ItemStack getRandomWeaponItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.swordDwarven), new ItemStack(LOTRMod.daggerDwarven), new ItemStack(LOTRMod.hammerDwarven), new ItemStack(LOTRMod.battleaxeDwarven), new ItemStack(LOTRMod.pickaxeDwarven), new ItemStack(LOTRMod.mattockDwarven), new ItemStack(LOTRMod.throwingAxeDwarven), new ItemStack(LOTRMod.pikeDwarven), new ItemStack(LOTRMod.swordDale), new ItemStack(LOTRMod.daggerDale), new ItemStack(LOTRMod.pikeDale), new ItemStack(LOTRMod.spearDale), new ItemStack(LOTRMod.battleaxeDale)};
      return items[random.nextInt(items.length)].func_77946_l();
   }

   protected ItemStack getRandomOtherItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.helmetDwarven), new ItemStack(LOTRMod.bodyDwarven), new ItemStack(LOTRMod.legsDwarven), new ItemStack(LOTRMod.bootsDwarven), new ItemStack(LOTRMod.helmetDale), new ItemStack(LOTRMod.bodyDale), new ItemStack(LOTRMod.legsDale), new ItemStack(LOTRMod.bootsDale), new ItemStack(LOTRMod.dwarfSteel), new ItemStack(LOTRMod.bronze), new ItemStack(Items.field_151042_j), new ItemStack(LOTRMod.silver), new ItemStack(LOTRMod.silverNugget), new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151074_bl)};
      return items[random.nextInt(items.length)].func_77946_l();
   }
}
