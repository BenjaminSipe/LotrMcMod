package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedHouse extends LOTRWorldGenStructureBase2 {
   protected Block woodBlock;
   protected int woodMeta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block stairBlock;
   protected Block stoneBlock;
   protected int stoneMeta;
   protected Block stoneVariantBlock;
   protected int stoneVariantMeta;

   public LOTRWorldGenRuinedHouse(boolean flag) {
      super(flag);
      this.woodBlock = Blocks.field_150364_r;
      this.woodMeta = 0;
      this.plankBlock = Blocks.field_150344_f;
      this.plankMeta = 0;
      this.fenceBlock = Blocks.field_150422_aJ;
      this.fenceMeta = 0;
      this.stairBlock = Blocks.field_150476_ad;
      this.stoneBlock = Blocks.field_150347_e;
      this.stoneMeta = 0;
      this.stoneVariantBlock = Blocks.field_150341_Y;
      this.stoneVariantMeta = 0;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int width = 4 + random.nextInt(3);
      this.setOriginAndRotation(world, i, j, k, rotation, width + 1);
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         i1 = 1;
         k1 = 1;

         for(j1 = -width; j1 <= width; ++j1) {
            for(int k1 = -width; k1 <= width; ++k1) {
               int j1 = this.getTopBlock(world, j1, k1);
               Block block = this.getBlock(world, j1, j1 - 1, k1);
               if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b) {
                  return false;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (j1 < i1) {
                  i1 = j1;
               }
            }
         }

         if (Math.abs(k1 - i1) > 5) {
            return false;
         }
      }

      for(i1 = -width; i1 <= width; ++i1) {
         for(k1 = -width; k1 <= width; ++k1) {
            for(j1 = 0; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            for(j1 = 0; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
               this.placeRandomGroundBlock(world, random, i1, j1, k1);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }
         }
      }

      for(i1 = -width; i1 <= width; ++i1) {
         this.placeRandomWallOrStone(world, random, -width, 1, -i1);
         this.placeRandomWallOrStone(world, random, width, 1, i1);
         this.placeRandomWall(world, random, -width, 2, i1, true);
         this.placeRandomWall(world, random, width, 2, i1, true);
         this.placeRandomWall(world, random, -width, 3, i1, true);
         this.placeRandomWall(world, random, width, 3, i1, true);
      }

      for(i1 = -width; i1 <= width; ++i1) {
         this.placeRandomWallOrStone(world, random, i1, 1, width);
         if (random.nextInt(3) == 0) {
            this.placeRandomWallOrStone(world, random, i1, 2, width - 1);
         }

         this.placeRandomWall(world, random, i1, 2, width, false);
         this.placeRandomWall(world, random, i1, 3, width, false);
      }

      for(i1 = -width + 1; i1 <= -1 && random.nextInt(4) != 0; ++i1) {
         this.placeRandomWallOrStone(world, random, i1, 1, -width);
      }

      for(i1 = width - 1; i1 >= 1 && random.nextInt(4) != 0; --i1) {
         this.placeRandomWallOrStone(world, random, i1, 1, -width);
      }

      this.setBlockAndMetadata(world, -width + 1, 2, -width, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, width - 1, 2, -width, this.fenceBlock, this.fenceMeta);
      this.placeWoodPillar(world, random, -width, 1, -width);
      this.placeWoodPillar(world, random, width, 1, -width);
      this.placeWoodPillar(world, random, -width, 1, width);
      this.placeWoodPillar(world, random, width, 1, width);
      if (random.nextBoolean()) {
         this.setBlockAndMetadata(world, width - 1, 1, -width + 1, this.stoneBlock, this.stoneMeta);
         this.setBlockAndMetadata(world, width - 1, 1, -width + 2, Blocks.field_150460_al, 0);
      } else {
         this.setBlockAndMetadata(world, -width + 1, 1, -width + 1, this.stoneBlock, this.stoneMeta);
         this.setBlockAndMetadata(world, -width + 1, 1, -width + 2, Blocks.field_150460_al, 0);
      }

      if (random.nextBoolean()) {
         this.placeChest(world, random, width - 1, 1, width - 2, 0, LOTRChestContents.RUINED_HOUSE);
      } else {
         this.placeChest(world, random, -width + 1, 1, width - 2, 0, LOTRChestContents.RUINED_HOUSE);
      }

      return true;
   }

   private void placeRandomGroundBlock(World world, Random random, int i, int j, int k) {
      int l = random.nextInt(4);
      if (l == 0) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150346_d, 1);
      } else if (l == 1) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150351_n, 0);
      } else if (l == 2) {
         this.setBlockAndMetadata(world, i, j, k, this.stoneBlock, this.stoneMeta);
      } else if (l == 3) {
         this.setBlockAndMetadata(world, i, j, k, this.stoneVariantBlock, this.stoneVariantMeta);
      }

   }

   private void placeRandomWallOrStone(World world, Random random, int i, int j, int k) {
      if (random.nextInt(12) != 0) {
         if (!this.isAir(world, i, j - 1, k)) {
            int l = random.nextInt(4);
            if (l == 0) {
               this.setBlockAndMetadata(world, i, j, k, this.fenceBlock, this.fenceMeta);
            } else if (l == 1) {
               this.setBlockAndMetadata(world, i, j, k, this.plankBlock, this.plankMeta);
            } else if (l == 2) {
               this.setBlockAndMetadata(world, i, j, k, this.stoneBlock, this.stoneMeta);
            } else if (l == 3) {
               this.setBlockAndMetadata(world, i, j, k, this.stoneVariantBlock, this.stoneVariantMeta);
            }

         }
      }
   }

   private void placeRandomWall(World world, Random random, int i, int j, int k, boolean northToSouth) {
      if (random.nextInt(12) != 0) {
         if (!this.isAir(world, i, j - 1, k)) {
            int l = random.nextInt(4);
            if (l == 0) {
               this.setBlockAndMetadata(world, i, j, k, this.fenceBlock, this.fenceMeta);
            } else if (l == 1) {
               this.setBlockAndMetadata(world, i, j, k, this.plankBlock, this.plankMeta);
            } else if (l == 2) {
               this.setBlockAndMetadata(world, i, j, k, this.woodBlock, this.woodMeta | (northToSouth ? 8 : 4));
            } else if (l == 3) {
               int upsideDown = random.nextBoolean() ? 4 : 0;
               if (northToSouth) {
                  this.setBlockAndMetadata(world, i, j, k, this.stairBlock, random.nextInt(2) | upsideDown);
               } else {
                  this.setBlockAndMetadata(world, i, j, k, this.stairBlock, 2 + random.nextInt(2) | upsideDown);
               }
            }

         }
      }
   }

   private void placeWoodPillar(World world, Random random, int i, int j, int k) {
      for(int j1 = j; j1 <= j + 4; ++j1) {
         this.setBlockAndMetadata(world, i, j1, k, this.woodBlock, this.woodMeta);
         if (random.nextInt(4) == 0 && j1 >= j + 2) {
            break;
         }
      }

   }
}
