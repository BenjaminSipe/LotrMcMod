package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenPine extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int minHeight;
   private int maxHeight;

   public LOTRWorldGenPine(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood5;
      this.woodMeta = 0;
      this.leafBlock = LOTRMod.leaves5;
      this.leafMeta = 0;
      this.minHeight = 12;
      this.maxHeight = 24;
   }

   public LOTRWorldGenPine setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      boolean flag = true;
      int leafHeight;
      int k1;
      if (j >= 1 && height + 1 <= 256) {
         for(int j1 = j; j1 <= j + height + 1; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= j + height - 1) {
               range = 2;
            }

            for(leafHeight = i - range; leafHeight <= i + range && flag; ++leafHeight) {
               for(k1 = k - range; k1 <= k + range && flag; ++k1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, leafHeight, j1, k1)) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }
      } else {
         flag = false;
      }

      Block below = world.func_147439_a(i, j - 1, k);
      boolean isSoil = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g);
      if (!isSoil) {
         flag = false;
      }

      if (!flag) {
         return false;
      } else {
         below.onPlantGrow(world, i, j - 1, k, i, j, k);
         this.func_150516_a(world, i, j + height, k, this.leafBlock, this.leafMeta);
         this.generateLeafLayer(world, random, i, j + height - 1, k, 1);
         leafHeight = j + height - 3;
         k1 = j + (int)((float)height * 0.5F);

         int lastDir;
         while(leafHeight > k1) {
            lastDir = random.nextInt(3);
            if (lastDir == 0) {
               this.generateLeafLayer(world, random, i, leafHeight, k, 1);
               leafHeight -= 2;
            } else if (lastDir == 1) {
               --leafHeight;
               this.generateLeafLayer(world, random, i, leafHeight + 1, k, 1);
               this.generateLeafLayer(world, random, i, leafHeight, k, 2);
               this.generateLeafLayer(world, random, i, leafHeight - 1, k, 1);
               leafHeight -= 3;
            } else if (lastDir == 2) {
               --leafHeight;
               this.generateLeafLayer(world, random, i, leafHeight + 1, k, 2);
               this.generateLeafLayer(world, random, i, leafHeight, k, 3);
               this.generateLeafLayer(world, random, i, leafHeight - 1, k, 2);
               leafHeight -= 3;
            }
         }

         this.generateLeafLayer(world, random, i, leafHeight, k, 1);
         lastDir = -1;

         for(int j1 = j; j1 < j + height; ++j1) {
            this.func_150516_a(world, i, j1, k, this.woodBlock, this.woodMeta);
            if (j1 >= j + 3 && j1 < k1 && random.nextInt(3) == 0) {
               int dir = random.nextInt(4);
               if (dir != lastDir) {
                  lastDir = dir;
                  int length = 1;

                  for(int l = 1; l <= length; ++l) {
                     int i1 = i + Direction.field_71583_a[dir] * l;
                     int k1 = k + Direction.field_71581_b[dir] * l;
                     if (!this.isReplaceable(world, i1, j1, k1)) {
                        break;
                     }

                     if (dir != 0 && dir != 2) {
                        this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 4);
                     } else {
                        this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 8);
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   private void generateLeafLayer(World world, Random random, int i, int j, int k, int range) {
      for(int i1 = i - range; i1 <= i + range; ++i1) {
         for(int k1 = k - range; k1 <= k + range; ++k1) {
            int i2 = Math.abs(i1 - i);
            int k2 = Math.abs(k1 - k);
            if (i2 + k2 <= range) {
               Block block = world.func_147439_a(i1, j, k1);
               if (block.isReplaceable(world, i1, j, k1) || block.isLeaves(world, i1, j, k1)) {
                  this.func_150516_a(world, i1, j, k1, this.leafBlock, this.leafMeta);
               }
            }
         }
      }

   }
}
