package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarHillmanHouse extends LOTRWorldGenStructureBase2 {
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

   public LOTRWorldGenAngmarHillmanHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      int k1;
      int l;
      int j1;
      int k1;
      int i1;
      if (this.restrictions) {
         k1 = 0;
         l = 0;

         for(j1 = -4; j1 <= 4; ++j1) {
            for(k1 = -6; k1 <= 6; ++k1) {
               i1 = this.getTopBlock(world, j1, k1);
               Block block = this.getBlock(world, j1, i1 - 1, k1);
               if (block != Blocks.field_150349_c && block != Blocks.field_150348_b) {
                  return false;
               }

               if (i1 < k1) {
                  k1 = i1;
               }

               if (i1 > l) {
                  l = i1;
               }

               if (l - k1 > 4) {
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

      for(k1 = -4; k1 <= 4; ++k1) {
         for(l = -6; l <= 6; ++l) {
            for(j1 = 1; j1 <= 7; ++j1) {
               this.setAir(world, k1, j1, l);
            }

            for(j1 = 0; (j1 == 0 || !this.isOpaque(world, k1, j1, l)) && this.getY(j1) >= 0; --j1) {
               if (this.getBlock(world, k1, j1 + 1, l).func_149662_c()) {
                  this.setBlockAndMetadata(world, k1, j1, l, Blocks.field_150346_d, 0);
               } else {
                  this.setBlockAndMetadata(world, k1, j1, l, Blocks.field_150349_c, 0);
               }

               this.setGrassToDirt(world, k1, j1 - 1, l);
            }
         }
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         for(l = -5; l <= 5; ++l) {
            this.setBlockAndMetadata(world, k1, 0, l, this.floorBlock, this.floorMeta);
            if (random.nextInt(2) == 0) {
               this.setBlockAndMetadata(world, k1, 1, l, LOTRMod.thatchFloor, 0);
            }
         }
      }

      for(k1 = 1; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, -3, k1, -5, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 3, k1, -5, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, -3, k1, 5, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 3, k1, 5, this.woodBlock, this.woodMeta);
      }

      int[] var13 = new int[]{1, 4};
      l = var13.length;

      for(j1 = 0; j1 < l; ++j1) {
         k1 = var13[j1];

         for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, k1, -5, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, i1, k1, 5, this.woodBlock, this.woodMeta | 4);
         }

         for(i1 = -4; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, -3, k1, i1, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, 3, k1, i1, this.woodBlock, this.woodMeta | 8);
         }
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, -3, 2, k1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, 3, 2, k1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, -3, 3, k1, this.stairBlock, 5);
         this.setBlockAndMetadata(world, 3, 3, k1, this.stairBlock, 4);
      }

      var13 = new int[]{-3, 3};
      l = var13.length;

      for(j1 = 0; j1 < l; ++j1) {
         k1 = var13[j1];
         this.setBlockAndMetadata(world, k1, 2, 0, this.slabBlock, this.slabMeta);
         this.setBlockAndMetadata(world, k1, 3, 0, this.slabBlock, this.slabMeta | 8);
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         for(l = 0; l <= 3; ++l) {
            this.setBlockAndMetadata(world, -4 + l, 4 + l, k1, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 4 - l, 4 + l, k1, this.stairBlock, 0);
         }

         this.setBlockAndMetadata(world, 0, 7, k1, this.plankBlock, this.plankMeta);
      }

      var13 = new int[]{-5, 5};
      l = var13.length;

      for(j1 = 0; j1 < l; ++j1) {
         k1 = var13[j1];

         for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, i1, 5, k1, this.woodBlock, this.woodMeta | 4);
         }

         for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 6, k1, this.woodBlock, this.woodMeta | 4);
         }
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, k1, 2, 5, this.stairBlock, 3);
         this.setBlockAndMetadata(world, k1, 3, 5, this.stairBlock, 7);
      }

      this.setBlockAndMetadata(world, 0, 3, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 3, 6, this.stairBlock, 7);

      for(k1 = 4; k1 <= 8; ++k1) {
         this.setBlockAndMetadata(world, 0, k1, 6, this.woodBlock, this.woodMeta);
      }

      this.setBlockAndMetadata(world, 0, 8, 5, this.stairBlock, 2);
      this.setBlockAndMetadata(world, 0, 9, 6, this.stairBlock, 2);
      this.setBlockAndMetadata(world, 0, 1, -5, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -5, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 3, -5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -2, 2, -5, this.stairBlock, 2);
      this.setBlockAndMetadata(world, -2, 3, -5, this.stairBlock, 6);
      this.setBlockAndMetadata(world, -1, 2, -5, this.stairBlock, 1);
      this.setBlockAndMetadata(world, -1, 3, -5, this.stairBlock, 5);
      this.setBlockAndMetadata(world, 1, 2, -5, this.stairBlock, 0);
      this.setBlockAndMetadata(world, 1, 3, -5, this.stairBlock, 4);
      this.setBlockAndMetadata(world, 2, 2, -5, this.stairBlock, 2);
      this.setBlockAndMetadata(world, 2, 3, -5, this.stairBlock, 6);
      this.setBlockAndMetadata(world, 0, 3, -6, this.stairBlock, 6);

      for(k1 = 4; k1 <= 8; ++k1) {
         this.setBlockAndMetadata(world, 0, k1, -6, this.woodBlock, this.woodMeta);
      }

      this.setBlockAndMetadata(world, 0, 8, -5, this.stairBlock, 3);
      this.setBlockAndMetadata(world, 0, 9, -6, this.stairBlock, 3);
      this.setBlockAndMetadata(world, -1, 5, -6, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 1, 5, -6, Blocks.field_150478_aa, 2);

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, -2, 1, k1, this.slabBlock, this.slabMeta | 8);
         this.setBlockAndMetadata(world, 2, 1, k1, this.slabBlock, this.slabMeta | 8);
      }

      this.setBlockAndMetadata(world, -2, 3, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 3, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 3, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 3, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.strawBed, 0);
      this.setBlockAndMetadata(world, 0, 1, 4, LOTRMod.strawBed, 8);
      this.setBlockAndMetadata(world, -1, 1, 4, Blocks.field_150462_ai, 0);
      this.placeChest(world, random, 1, 1, 4, 2, LOTRChestContents.ANGMAR_HILLMAN_HOUSE);
      this.placeWallBanner(world, 0, 4, 5, LOTRItemBanner.BannerType.RHUDAUR, 2);
      this.setBlockAndMetadata(world, -1, 3, 4, Blocks.field_150465_bP, 2);
      this.setBlockAndMetadata(world, 1, 3, 4, Blocks.field_150465_bP, 2);
      LOTREntityAngmarHillman hillman = new LOTREntityAngmarHillman(world);
      this.spawnNPCAndSetHome(hillman, world, 0, 1, 0, 8);
      return true;
   }
}
