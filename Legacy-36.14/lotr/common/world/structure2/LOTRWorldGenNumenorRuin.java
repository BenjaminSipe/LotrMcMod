package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenNumenorRuin extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenNumenorRuin(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int width = 3 + random.nextInt(3);
      this.setOriginAndRotation(world, i, j, k, rotation, width + 1);

      int i1;
      int k1;
      int i1;
      for(i1 = -width; i1 <= width; ++i1) {
         for(k1 = -width; k1 <= width; ++k1) {
            if (Math.abs(i1) != width && Math.abs(k1) != width) {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150349_c, 0);

               for(i1 = -1; !this.isOpaque(world, i1, i1, k1) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, i1, i1, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, i1 - 1, k1);
               }
            } else {
               for(i1 = 0; !this.isOpaque(world, i1, i1, k1) && this.getY(i1) >= 0; --i1) {
                  this.placeRandomBrick(world, random, i1, i1, k1);
                  this.setGrassToDirt(world, i1, i1 - 1, k1);
               }
            }
         }
      }

      if (random.nextBoolean()) {
         LOTRTreeType.OAK_LARGE.create(this.notifyChanges, random).func_76484_a(world, random, this.originX, this.originY + 1, this.originZ);
      } else {
         LOTRTreeType.BEECH_LARGE.create(this.notifyChanges, random).func_76484_a(world, random, this.originX, this.originY + 1, this.originZ);
      }

      int k1;
      for(i1 = -width; i1 <= width; ++i1) {
         for(k1 = -width; k1 <= width; ++k1) {
            if (Math.abs(i1) == width || Math.abs(k1) == width) {
               i1 = width * 2 + random.nextInt(8);

               for(k1 = 1; k1 < i1; ++k1) {
                  this.placeRandomBrick(world, random, i1, k1, k1);
               }
            }
         }
      }

      this.setAir(world, 0, 1, -width);
      this.setAir(world, 0, 2, -width);
      i1 = 10 + random.nextInt(20);

      for(k1 = 0; k1 < i1; ++k1) {
         i1 = -width * 2 + random.nextInt(width * 2 + 1);
         k1 = -width * 2 + random.nextInt(width * 2 + 1);
         int j1 = this.getTopBlock(world, i1, k1);
         Block block = this.getBlock(world, i1, j1 - 1, k1);
         if (block == Blocks.field_150349_c || block == Blocks.field_150346_d || block == Blocks.field_150348_b) {
            int l1 = random.nextInt(3);
            if (l1 == 0) {
               this.setBlockAndMetadata(world, i1, j1 - 1, k1, Blocks.field_150351_n, 0);
            } else if (l1 == 1) {
               this.placeRandomBrick(world, random, i1, j1 - 1, k1);
            } else if (l1 == 2) {
               int height = 1 + random.nextInt(3);

               for(int j2 = j1; j2 < j1 + height && !this.isOpaque(world, i1, j2, k1); ++j2) {
                  this.placeRandomBrick(world, random, i1, j2, k1);
               }
            }
         }
      }

      return true;
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      int l = random.nextInt(5);
      if (l == 0) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 0);
      } else if (l == 1) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 1);
      } else if (l == 2) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 2);
      } else if (l == 3) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150347_e, 0);
      } else if (l == 4) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150341_Y, 0);
      }

   }
}
