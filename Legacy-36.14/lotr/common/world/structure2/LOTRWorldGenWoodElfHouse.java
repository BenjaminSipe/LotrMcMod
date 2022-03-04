package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWoodElfHouse extends LOTRWorldGenStructureBase2 {
   private WorldGenerator treeGen = (new LOTRWorldGenMirkOak(true, 3, 4, 0, false)).setGreenOak().disableRestrictions().disableRoots();
   protected Block plank1Block;
   protected int plank1Meta;
   protected Block wood1Block;
   protected int wood1Meta;
   protected Block fence1Block;
   protected int fence1Meta;
   protected Block doorBlock;
   protected Block plank2Block;
   protected int plank2Meta;
   protected Block fence2Block;
   protected int fence2Meta;
   protected Block stair2Block;
   protected Block barsBlock;
   protected Block plateBlock;

   public LOTRWorldGenWoodElfHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      int minHeight;
      int maxHeight;
      int i1;
      int carpetType;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -6; i1 <= 6; ++i1) {
            for(carpetType = -6; carpetType <= 6; ++carpetType) {
               j1 = this.getTopBlock(world, i1, carpetType);
               Block block = this.getBlock(world, i1, j1 - 1, carpetType);
               if (block != Blocks.field_150349_c) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 3) {
                  return false;
               }
            }
         }
      }

      minHeight = random.nextInt(2);
      if (minHeight == 0) {
         this.plank1Block = LOTRMod.planks2;
         this.plank1Meta = 13;
         this.wood1Block = LOTRMod.wood7;
         this.wood1Meta = 1;
         this.fence1Block = LOTRMod.fence2;
         this.fence1Meta = 13;
         this.doorBlock = LOTRMod.doorGreenOak;
      } else {
         this.plank1Block = LOTRMod.planks;
         this.plank1Meta = 9;
         this.wood1Block = LOTRMod.wood2;
         this.wood1Meta = 1;
         this.fence1Block = LOTRMod.fence;
         this.fence1Meta = 9;
         this.doorBlock = LOTRMod.doorBeech;
      }

      maxHeight = random.nextInt(2);
      if (maxHeight == 0) {
         this.plank2Block = LOTRMod.planks2;
         this.plank2Meta = 13;
         this.fence2Block = LOTRMod.fence2;
         this.fence2Meta = 13;
         this.stair2Block = LOTRMod.stairsGreenOak;
      } else {
         this.plank2Block = LOTRMod.planks;
         this.plank2Meta = 9;
         this.fence2Block = LOTRMod.fence;
         this.fence2Meta = 9;
         this.stair2Block = LOTRMod.stairsBeech;
      }

      this.barsBlock = LOTRMod.woodElfWoodBars;
      this.plateBlock = LOTRMod.woodPlateBlock;

      for(i1 = -6; i1 <= 6; ++i1) {
         for(carpetType = -6; carpetType <= 6; ++carpetType) {
            for(j1 = 1; j1 <= 7; ++j1) {
               this.setAir(world, i1, j1, carpetType);
            }

            for(j1 = 0; (j1 == 0 || !this.isOpaque(world, i1, j1, carpetType)) && this.getY(j1) >= 0; --j1) {
               if (this.getBlock(world, i1, j1 + 1, carpetType).func_149662_c()) {
                  this.setBlockAndMetadata(world, i1, j1, carpetType, Blocks.field_150346_d, 0);
               } else {
                  this.setBlockAndMetadata(world, i1, j1, carpetType, Blocks.field_150349_c, 0);
               }

               this.setGrassToDirt(world, i1, j1 - 1, carpetType);
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(carpetType = -4; carpetType <= 4; ++carpetType) {
            this.setBlockAndMetadata(world, i1, 0, carpetType, LOTRMod.brick3, 5);
         }
      }

      int stairHeight;
      int i1;
      for(i1 = -4; i1 <= 4; ++i1) {
         for(carpetType = -4; carpetType <= 4; ++carpetType) {
            j1 = Math.abs(i1);
            i1 = Math.abs(carpetType);

            for(stairHeight = 1; stairHeight <= 3; ++stairHeight) {
               if (j1 == 4 && i1 == 4) {
                  this.setBlockAndMetadata(world, i1, stairHeight, carpetType, this.wood1Block, this.wood1Meta);
               } else if (j1 != 4 && i1 != 4) {
                  this.setAir(world, i1, stairHeight, carpetType);
               } else {
                  this.setBlockAndMetadata(world, i1, stairHeight, carpetType, this.plank1Block, this.plank1Meta);
               }
            }

            if (j1 == 4 || i1 == 4) {
               this.setBlockAndMetadata(world, i1, 4, carpetType, this.plank2Block, this.plank2Meta);
            }
         }
      }

      int[] var17;
      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -5, this.stair2Block, 6);
         this.setBlockAndMetadata(world, i1, 4, 5, this.stair2Block, 7);
         var17 = new int[]{-5, 5};
         j1 = var17.length;

         for(i1 = 0; i1 < j1; ++i1) {
            stairHeight = var17[i1];
            if (!this.getBlock(world, i1, 1, stairHeight).func_149662_c()) {
               this.setBlockAndMetadata(world, i1, 1, stairHeight, LOTRMod.leaves7, 5);
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -5, 4, i1, this.stair2Block, 5);
         this.setBlockAndMetadata(world, 5, 4, i1, this.stair2Block, 4);
         var17 = new int[]{-5, 5};
         j1 = var17.length;

         for(i1 = 0; i1 < j1; ++i1) {
            stairHeight = var17[i1];
            if (!this.getBlock(world, stairHeight, 1, i1).func_149662_c()) {
               this.setBlockAndMetadata(world, stairHeight, 1, i1, LOTRMod.leaves7, 5);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -3, this.stair2Block, 7);
         this.setBlockAndMetadata(world, i1, 4, 3, this.stair2Block, 6);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -3, 4, i1, this.stair2Block, 4);
         this.setBlockAndMetadata(world, 3, 4, i1, this.stair2Block, 5);
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(carpetType = -5; carpetType <= 5; ++carpetType) {
            j1 = Math.abs(i1);
            i1 = Math.abs(carpetType);
            if (j1 == 5 || i1 == 5) {
               this.setBlockAndMetadata(world, i1, 5, carpetType, this.fence1Block, this.fence1Meta);
            }

            if (j1 == 5 && i1 == 5) {
               this.setBlockAndMetadata(world, i1, 6, carpetType, LOTRMod.woodElvenTorch, 5);
            }

            if (j1 == 5 && i1 == 0 || i1 == 5 && j1 == 0) {
               this.setBlockAndMetadata(world, i1, 6, carpetType, this.fence1Block, this.fence1Meta);
               this.setBlockAndMetadata(world, i1, 7, carpetType, LOTRMod.woodElvenTorch, 5);
            }
         }
      }

      this.setBlockAndMetadata(world, -3, 2, -1, LOTRMod.woodElvenTorch, 2);
      this.setBlockAndMetadata(world, -3, 2, 1, LOTRMod.woodElvenTorch, 2);
      this.setBlockAndMetadata(world, 3, 2, -1, LOTRMod.woodElvenTorch, 1);
      this.setBlockAndMetadata(world, 3, 2, 1, LOTRMod.woodElvenTorch, 1);
      this.setBlockAndMetadata(world, -1, 2, -3, LOTRMod.woodElvenTorch, 3);
      this.setBlockAndMetadata(world, 1, 2, -3, LOTRMod.woodElvenTorch, 3);
      this.setBlockAndMetadata(world, -1, 2, 3, LOTRMod.woodElvenTorch, 4);
      this.setBlockAndMetadata(world, 1, 2, 3, LOTRMod.woodElvenTorch, 4);
      int[] carpets = new int[]{12, 13, 14, 15};
      carpetType = carpets[random.nextInt(carpets.length)];

      int j1;
      for(j1 = -4; j1 <= 4; ++j1) {
         for(i1 = -4; i1 <= 4; ++i1) {
            stairHeight = Math.abs(j1);
            j1 = Math.abs(i1);
            this.setBlockAndMetadata(world, j1, -5, i1, LOTRMod.brick3, 5);

            for(int j1 = -4; j1 <= -1; ++j1) {
               if (stairHeight != 4 && j1 != 4) {
                  this.setAir(world, j1, j1, i1);
               } else if (j1 >= -3 && j1 <= -2) {
                  this.setBlockAndMetadata(world, j1, j1, i1, Blocks.field_150417_aV, 0);
               } else {
                  this.setBlockAndMetadata(world, j1, j1, i1, this.plank1Block, this.plank1Meta);
               }
            }

            if (stairHeight <= 2 && j1 <= 2) {
               this.setBlockAndMetadata(world, j1, -4, i1, Blocks.field_150404_cg, carpetType);
            }
         }
      }

      for(j1 = -3; j1 <= -2; ++j1) {
         this.setBlockAndMetadata(world, -2, j1, -4, this.wood1Block, this.wood1Meta);
         this.setBlockAndMetadata(world, -1, j1, -4, Blocks.field_150342_X, 0);
         this.setBlockAndMetadata(world, 0, j1, -4, Blocks.field_150342_X, 0);
         this.setBlockAndMetadata(world, 1, j1, -4, Blocks.field_150342_X, 0);
         this.setBlockAndMetadata(world, 1, j1, -4, this.wood1Block, this.wood1Meta);
      }

      for(j1 = 2; j1 <= 3; ++j1) {
         for(i1 = -2; i1 <= 2; ++i1) {
            this.setAir(world, i1, 0, j1);
            stairHeight = i1 - -2;

            for(j1 = -4; j1 < -4 + stairHeight; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, j1, LOTRMod.brick3, 5);
            }

            this.setBlockAndMetadata(world, i1, -4 + stairHeight, j1, LOTRMod.stairsWoodElvenBrick, 1);
         }

         for(i1 = -4; i1 <= -1; ++i1) {
            this.setBlockAndMetadata(world, 3, i1, j1, LOTRMod.brick3, 5);
         }
      }

      this.setBlockAndMetadata(world, -3, -2, -3, LOTRMod.woodElvenTorch, 3);
      this.setBlockAndMetadata(world, 3, -2, -3, LOTRMod.woodElvenTorch, 3);
      this.setBlockAndMetadata(world, -3, -2, 3, LOTRMod.woodElvenTorch, 4);
      this.setBlockAndMetadata(world, 3, -2, 1, LOTRMod.woodElvenTorch, 4);
      this.setBlockAndMetadata(world, 3, -4, 0, LOTRMod.woodElvenBed, 0);
      this.setBlockAndMetadata(world, 3, -4, 1, LOTRMod.woodElvenBed, 8);

      for(j1 = -3; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, j1, -1, -3, this.barsBlock, 0);
      }

      for(j1 = -2; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, -3, -1, j1, this.barsBlock, 0);
      }

      for(j1 = -2; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, 3, -1, j1, this.barsBlock, 0);
      }

      for(j1 = 1; j1 <= 3; ++j1) {
         for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, j1, -4, this.wood1Block, this.wood1Meta);
         }
      }

      this.setBlockAndMetadata(world, 0, 0, -2, LOTRMod.brick2, 14);
      this.setBlockAndMetadata(world, -2, 0, 0, LOTRMod.brick2, 14);
      this.setBlockAndMetadata(world, 2, 0, 0, LOTRMod.brick2, 14);
      this.setBlockAndMetadata(world, 3, 0, 3, LOTRMod.brick2, 14);
      this.setAir(world, 0, 1, -5);
      this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -1, 2, -5, LOTRMod.woodElvenTorch, 4);
      this.setBlockAndMetadata(world, 1, 2, -5, LOTRMod.woodElvenTorch, 4);
      this.setBlockAndMetadata(world, -3, 2, -4, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -2, 2, -4, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 2, 2, -4, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 3, 2, -4, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 3, 1, -3, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, 2, 1, -3, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 3, 1, -2, LOTRMod.woodElvenTable, 0);
      this.setBlockAndMetadata(world, -3, 1, -3, this.plank2Block, this.plank2Meta);
      this.placeMug(world, random, -3, 2, -3, random.nextInt(4), LOTRFoods.WOOD_ELF_DRINK);
      this.setBlockAndMetadata(world, -2, 1, -3, this.plank2Block, this.plank2Meta);
      this.placePlate(world, random, -2, 2, -3, this.plateBlock, LOTRFoods.ELF);
      this.placeChest(world, random, -3, 1, -2, 0, LOTRChestContents.WOOD_ELF_HOUSE);
      this.placeWoodElfItemFrame(world, -4, 2, 0, 1, random);
      this.placeWoodElfItemFrame(world, 4, 2, 0, 3, random);

      for(j1 = 1; j1 <= 4; ++j1) {
         this.setBlockAndMetadata(world, 3, j1, 3, Blocks.field_150468_ap, 2);
      }

      for(j1 = -4; j1 <= 4; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, LOTRMod.wood7, 1);
      }

      this.treeGen.func_76484_a(world, random, this.getX(0, 0), this.getY(5), this.getZ(0, 0));
      LOTREntityWoodElf elf = new LOTREntityWoodElf(world);
      this.spawnNPCAndSetHome(elf, world, 1, 1, 1, 8);
      return true;
   }

   private void placeWoodElfItemFrame(World world, int i, int j, int k, int direction, Random random) {
      ItemStack item = null;
      int l = random.nextInt(3);
      switch(l) {
      case 0:
         item = new ItemStack(LOTRMod.mirkwoodBow);
         break;
      case 1:
         item = new ItemStack(Items.field_151032_g);
         break;
      case 2:
         item = new ItemStack(LOTRMod.sapling7, 1, 1);
         break;
      case 3:
         item = new ItemStack(Blocks.field_150328_O);
         break;
      case 4:
         item = new ItemStack(Blocks.field_150327_N);
         break;
      case 5:
         item = new ItemStack(Items.field_151122_aG);
      }

      this.spawnItemFrame(world, i, j, k, direction, item);
   }
}
