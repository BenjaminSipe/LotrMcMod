package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.common.entity.npc.LOTREntityAngmarHillmanChieftain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarHillmanChieftainHouse extends LOTRWorldGenStructureBase2 {
   private Block woodBlock;
   private int woodMeta;
   private Block plankBlock;
   private int plankMeta;
   private Block slabBlock;
   private int slabMeta;
   private Block stairBlock;
   private Block fenceBlock;
   private int fenceMeta;
   private Block doorBlock;
   private Block floorBlock;
   private int floorMeta;

   public LOTRWorldGenAngmarHillmanChieftainHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      int j1;
      int i1;
      int k1;
      int i1;
      int j1;
      if (this.restrictions) {
         j1 = 0;
         i1 = 0;

         for(k1 = -5; k1 <= 5; ++k1) {
            for(i1 = -6; i1 <= 6; ++i1) {
               j1 = this.getTopBlock(world, k1, i1);
               Block block = this.getBlock(world, k1, j1 - 1, i1);
               if (block != Blocks.field_150349_c && block != Blocks.field_150348_b) {
                  return false;
               }

               if (j1 < j1) {
                  j1 = j1;
               }

               if (j1 > i1) {
                  i1 = j1;
               }

               if (i1 - j1 > 4) {
                  return false;
               }
            }
         }
      }

      this.woodBlock = Blocks.field_150364_r;
      this.woodMeta = 1;
      this.plankBlock = Blocks.field_150344_f;
      this.plankMeta = 1;
      this.slabBlock = Blocks.field_150376_bx;
      this.slabMeta = 1;
      this.stairBlock = Blocks.field_150485_bF;
      this.fenceBlock = Blocks.field_150422_aJ;
      this.fenceMeta = 0;
      this.doorBlock = LOTRMod.doorSpruce;
      this.floorBlock = Blocks.field_150406_ce;
      this.floorMeta = 15;

      for(j1 = -5; j1 <= 5; ++j1) {
         for(i1 = -6; i1 <= 6; ++i1) {
            for(k1 = 1; k1 <= 10; ++k1) {
               this.setAir(world, j1, k1, i1);
            }

            for(k1 = 0; (k1 == 0 || !this.isOpaque(world, j1, k1, i1)) && this.getY(k1) >= 0; --k1) {
               if (this.getBlock(world, j1, k1 + 1, i1).func_149662_c()) {
                  this.setBlockAndMetadata(world, j1, k1, i1, Blocks.field_150346_d, 0);
               } else {
                  this.setBlockAndMetadata(world, j1, k1, i1, Blocks.field_150349_c, 0);
               }

               this.setGrassToDirt(world, j1, k1 - 1, i1);
            }
         }
      }

      for(j1 = -4; j1 <= 4; ++j1) {
         for(i1 = -5; i1 <= 5; ++i1) {
            this.setBlockAndMetadata(world, j1, 0, i1, this.floorBlock, this.floorMeta);
            if (random.nextInt(2) == 0) {
               this.setBlockAndMetadata(world, j1, 1, i1, LOTRMod.thatchFloor, 0);
            }
         }
      }

      int[] var13 = new int[]{-4, 4};
      i1 = var13.length;

      for(k1 = 0; k1 < i1; ++k1) {
         i1 = var13[k1];

         for(j1 = -4; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, i1, 1, j1, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, i1, 4, j1, this.woodBlock, this.woodMeta | 8);
         }

         for(j1 = 1; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, -5, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, i1, j1, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, i1, j1, 5, this.woodBlock, this.woodMeta);
         }
      }

      var13 = new int[]{-3, 3};
      i1 = var13.length;

      for(k1 = 0; k1 < i1; ++k1) {
         i1 = var13[k1];

         for(j1 = -4; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, i1, 1, j1, this.plankBlock, this.plankMeta);
         }

         for(j1 = 2; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, -4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, j1, -1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, j1, 1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, j1, 4, this.plankBlock, this.plankMeta);
         }

         this.setBlockAndMetadata(world, i1, 3, -3, this.stairBlock, 7);
         this.setBlockAndMetadata(world, i1, 3, -2, this.stairBlock, 6);
         this.setBlockAndMetadata(world, i1, 3, 2, this.stairBlock, 7);
         this.setBlockAndMetadata(world, i1, 3, 3, this.stairBlock, 6);

         for(j1 = 1; j1 <= 5; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, 0, this.woodBlock, this.woodMeta);
         }

         this.setBlockAndMetadata(world, i1, 1, -5, this.woodBlock, this.woodMeta | 4);
         this.setBlockAndMetadata(world, i1, 2, -5, this.stairBlock, 2);
         this.setBlockAndMetadata(world, i1, 3, -5, this.stairBlock, 6);
         this.setBlockAndMetadata(world, i1, 4, -5, this.slabBlock, this.slabMeta);
      }

      var13 = new int[]{-2, 2};
      i1 = var13.length;

      for(k1 = 0; k1 < i1; ++k1) {
         i1 = var13[k1];

         for(j1 = 1; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, -4, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, j1, -5, this.woodBlock, this.woodMeta);
         }

         this.setBlockAndMetadata(world, i1, 4, -5, this.slabBlock, this.slabMeta);
         this.setBlockAndMetadata(world, i1, 2, -6, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, i1, 3, -6, Blocks.field_150465_bP, 2);
      }

      for(j1 = 1; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, -1, j1, -4, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 1, j1, -4, this.woodBlock, this.woodMeta);
      }

      this.setBlockAndMetadata(world, -1, 2, -5, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 2, -5, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -1, 3, -5, this.stairBlock, 4);
      this.setBlockAndMetadata(world, -1, 4, -5, this.stairBlock, 1);
      this.setBlockAndMetadata(world, 1, 3, -5, this.stairBlock, 5);
      this.setBlockAndMetadata(world, 1, 4, -5, this.stairBlock, 0);
      this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 3, -4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 3, -5, this.slabBlock, this.slabMeta | 8);

      for(j1 = -3; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, j1, 4, -4, this.woodBlock, this.woodMeta | 4);
         this.setBlockAndMetadata(world, j1, 5, -5, this.stairBlock, 6);
      }

      this.setBlockAndMetadata(world, -2, 5, -4, Blocks.field_150465_bP, 3);
      this.setBlockAndMetadata(world, 2, 5, -4, Blocks.field_150465_bP, 3);

      for(j1 = -2; j1 <= 2; ++j1) {
         this.setBlockAndMetadata(world, j1, 6, -5, this.woodBlock, this.woodMeta | 4);
      }

      for(j1 = -1; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, j1, 7, -5, this.woodBlock, this.woodMeta | 4);
      }

      for(j1 = 4; j1 <= 9; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, -5, this.woodBlock, this.woodMeta);
      }

      this.setBlockAndMetadata(world, 0, 9, -4, this.stairBlock, 7);
      this.setBlockAndMetadata(world, 0, 6, -6, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 5, -4, Blocks.field_150478_aa, 3);
      this.placeWallBanner(world, 0, 5, -5, LOTRItemBanner.BannerType.ANGMAR, 2);
      this.placeWallBanner(world, -2, 5, -5, LOTRItemBanner.BannerType.RHUDAUR, 2);
      this.placeWallBanner(world, 2, 5, -5, LOTRItemBanner.BannerType.RHUDAUR, 2);

      for(j1 = -3; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, j1, 1, 5, this.woodBlock, this.woodMeta | 4);
         this.setBlockAndMetadata(world, j1, 2, 5, this.stairBlock, 3);
         this.setBlockAndMetadata(world, j1, 3, 5, this.stairBlock, 7);
         this.setBlockAndMetadata(world, j1, 4, 5, this.woodBlock, this.woodMeta | 4);
      }

      this.setBlockAndMetadata(world, -3, 5, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -2, 5, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -1, 5, 5, this.slabBlock, this.slabMeta | 8);
      this.setBlockAndMetadata(world, 0, 5, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 1, 5, 5, this.slabBlock, this.slabMeta | 8);
      this.setBlockAndMetadata(world, 2, 5, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 3, 5, 5, this.plankBlock, this.plankMeta);

      for(j1 = -2; j1 <= 2; ++j1) {
         for(i1 = 6; i1 <= 7; ++i1) {
            this.setBlockAndMetadata(world, j1, i1, 5, this.plankBlock, this.plankMeta);
         }
      }

      var13 = new int[]{-2, 2};
      i1 = var13.length;

      for(k1 = 0; k1 < i1; ++k1) {
         i1 = var13[k1];

         for(j1 = 1; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, 4, this.plankBlock, this.plankMeta);
         }

         this.setBlockAndMetadata(world, i1, 5, 4, this.fenceBlock, this.fenceMeta);
      }

      for(j1 = 4; j1 <= 5; ++j1) {
         this.setBlockAndMetadata(world, -3, j1, 4, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 3, j1, 4, this.plankBlock, this.plankMeta);
      }

      for(j1 = 7; j1 <= 9; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, 5, this.woodBlock, this.woodMeta);
      }

      this.setBlockAndMetadata(world, 0, 9, 4, this.stairBlock, 6);
      this.setBlockAndMetadata(world, 0, 5, 4, Blocks.field_150478_aa, 4);
      this.placeWallBanner(world, 0, 4, 5, LOTRItemBanner.BannerType.ANGMAR, 2);
      this.setBlockAndMetadata(world, -1, 4, 4, Blocks.field_150465_bP, 2);
      this.setBlockAndMetadata(world, 1, 4, 4, Blocks.field_150465_bP, 2);
      this.setBlockAndMetadata(world, 0, 3, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 3, 6, this.stairBlock, 7);
      this.setBlockAndMetadata(world, 0, 4, 6, this.woodBlock, this.woodMeta);
      this.setBlockAndMetadata(world, 0, 5, 6, this.woodBlock, this.woodMeta);
      this.setBlockAndMetadata(world, 0, 6, 6, this.stairBlock, 3);
      this.setBlockAndMetadata(world, -2, 5, 0, Blocks.field_150478_aa, 2);
      this.placeWallBanner(world, -3, 3, 0, LOTRItemBanner.BannerType.RHUDAUR, 1);
      this.setBlockAndMetadata(world, 2, 5, 0, Blocks.field_150478_aa, 1);
      this.placeWallBanner(world, 3, 3, 0, LOTRItemBanner.BannerType.RHUDAUR, 3);

      for(j1 = -3; j1 <= -1; ++j1) {
         this.setBlockAndMetadata(world, -3, 4, j1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, 3, 4, j1, this.stairBlock, 1);
      }

      for(j1 = -4; j1 <= -1; ++j1) {
         this.setBlockAndMetadata(world, -3, 5, j1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, 3, 5, j1, this.stairBlock, 5);
      }

      for(j1 = 1; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, -3, 4, j1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, 3, 4, j1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, -3, 5, j1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, 3, 5, j1, this.stairBlock, 5);
      }

      for(j1 = -6; j1 <= 6; ++j1) {
         this.setBlockAndMetadata(world, -5, 4, j1, this.slabBlock, this.slabMeta | 8);
         this.setBlockAndMetadata(world, -4, 5, j1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, -3, 6, j1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, -2, 7, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -2, 8, j1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, -1, 9, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -1, 10, j1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, 0, 10, j1, this.woodBlock, this.woodMeta | 8);
         this.setBlockAndMetadata(world, 1, 10, j1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, 1, 9, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 2, 8, j1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, 2, 7, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 3, 6, j1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, 4, 5, j1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, 5, 4, j1, this.slabBlock, this.slabMeta | 8);
      }

      var13 = new int[]{-6, 6};
      i1 = var13.length;

      for(k1 = 0; k1 < i1; ++k1) {
         i1 = var13[k1];
         this.setBlockAndMetadata(world, -4, 4, i1, this.slabBlock, this.slabMeta | 8);
         this.setBlockAndMetadata(world, -3, 5, i1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, -2, 6, i1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, -1, 7, i1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, -1, 8, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 1, 8, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 1, 7, i1, this.stairBlock, 5);
         this.setBlockAndMetadata(world, 2, 6, i1, this.stairBlock, 5);
         this.setBlockAndMetadata(world, 3, 5, i1, this.stairBlock, 5);
         this.setBlockAndMetadata(world, 4, 4, i1, this.slabBlock, this.slabMeta | 8);
      }

      this.setBlockAndMetadata(world, 0, 11, -6, this.stairBlock, 3);
      this.setBlockAndMetadata(world, 0, 11, -7, this.stairBlock, 6);
      this.setBlockAndMetadata(world, 0, 12, -7, this.stairBlock, 3);
      this.setBlockAndMetadata(world, 0, 11, 6, this.stairBlock, 2);
      this.setBlockAndMetadata(world, 0, 11, 7, this.stairBlock, 7);
      this.setBlockAndMetadata(world, 0, 12, 7, this.stairBlock, 2);

      for(j1 = -1; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, -1, 10, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 1, 10, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -1, 11, j1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, 1, 11, j1, this.stairBlock, 0);
      }

      this.setBlockAndMetadata(world, 0, 11, -1, this.stairBlock, 2);
      this.setBlockAndMetadata(world, 0, 11, 1, this.stairBlock, 3);
      this.setAir(world, 0, 10, 0);

      for(j1 = 0; j1 <= 2; ++j1) {
         i1 = 4 + j1 * 2;
         this.setBlockAndMetadata(world, -4 + j1, i1, 0, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, -4 + j1, i1 + 1, 0, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, -4 + j1, i1 + 2, 0, this.stairBlock, 1);
         this.setBlockAndMetadata(world, 4 - j1, i1, 0, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 4 - j1, i1 + 1, 0, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 4 - j1, i1 + 2, 0, this.stairBlock, 0);
      }

      for(j1 = -4; j1 <= 4; ++j1) {
         this.setBlockAndMetadata(world, -2, 6, j1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, 2, 6, j1, this.stairBlock, 5);
      }

      for(j1 = -3; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, -1, 8, j1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, 1, 8, j1, this.stairBlock, 5);
      }

      var13 = new int[]{-1, 1};
      i1 = var13.length;

      for(k1 = 0; k1 < i1; ++k1) {
         i1 = var13[k1];
         this.setBlockAndMetadata(world, i1, 8, -5, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 8, -4, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 8, 4, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 8, 5, this.plankBlock, this.plankMeta);
      }

      this.setBlockAndMetadata(world, -1, 7, -4, this.stairBlock, 4);
      this.setBlockAndMetadata(world, 1, 7, -4, this.stairBlock, 5);
      this.setBlockAndMetadata(world, -1, 7, 4, this.stairBlock, 4);
      this.setBlockAndMetadata(world, 1, 7, 4, this.stairBlock, 5);

      for(j1 = 0; j1 >= -5; --j1) {
         for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
               if (i1 == 0 && k1 == 0) {
                  this.setAir(world, i1, j1, k1);
               } else {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, -6, 0, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 0, -5, 0, Blocks.field_150480_ab, 0);
      this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.bronzeBars, 0);
      this.setAir(world, 0, 1, 0);
      this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.strawBed, 0);
      this.setBlockAndMetadata(world, 0, 1, 4, LOTRMod.strawBed, 8);

      for(j1 = 1; j1 <= 2; ++j1) {
         this.setBlockAndMetadata(world, -1, j1, 4, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 1, j1, 4, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -2, 1, 3, LOTRMod.angmarTable, 0);
      this.setBlockAndMetadata(world, -2, 1, 2, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 2, 1, 3, Blocks.field_150460_al, 5);
      this.placeChest(world, random, 2, 1, 2, 5, LOTRChestContents.ANGMAR_HILLMAN_HOUSE);
      LOTREntityAngmarHillman chieftain = new LOTREntityAngmarHillmanChieftain(world);
      this.spawnNPCAndSetHome(chieftain, world, 0, 1, 0, 8);
      return true;
   }
}
