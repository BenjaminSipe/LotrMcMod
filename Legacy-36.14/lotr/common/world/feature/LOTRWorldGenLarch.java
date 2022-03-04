package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockSaplingBase;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenLarch extends WorldGenAbstractTree {
   public LOTRWorldGenLarch(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = random.nextInt(9) + 8;
      int trunkBaseHeight = 2 + random.nextInt(2);
      int leafStart = height - trunkBaseHeight;
      int leafWidth = 2 + random.nextInt(2);
      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         boolean isSoil;
         int maxLeafRange;
         int leafRange;
         for(int j1 = j; j1 <= j + 1 + height && flag; ++j1) {
            isSoil = true;
            int range = false;
            if (j1 - j < trunkBaseHeight) {
               leafRange = 0;
            } else {
               leafRange = leafWidth;
            }

            for(maxLeafRange = i - leafRange; maxLeafRange <= i + leafRange && flag; ++maxLeafRange) {
               for(int k1 = k - leafRange; k1 <= k + leafRange && flag; ++k1) {
                  if (j1 >= 0 && j1 < 256) {
                     Block block = world.func_147439_a(maxLeafRange, j1, k1);
                     if (!block.isAir(world, maxLeafRange, j1, k1) && !block.isLeaves(world, maxLeafRange, j1, k1)) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         if (!flag) {
            return false;
         } else {
            Block soil = world.func_147439_a(i, j - 1, k);
            isSoil = soil.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (LOTRBlockSaplingBase)LOTRMod.sapling3);
            if (isSoil && j < 256 - height - 1) {
               soil.onPlantGrow(world, i, j - 1, k, i, j, k);
               leafRange = random.nextInt(2);
               maxLeafRange = 1;
               int minLeafRange = 0;

               int j1;
               int trunkTop;
               for(trunkTop = 0; trunkTop <= leafStart; ++trunkTop) {
                  j1 = j + height - trunkTop;

                  for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
                     int i2 = i1 - i;

                     for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
                        int k2 = k1 - k;
                        if ((Math.abs(i2) != leafRange || Math.abs(k2) != leafRange || leafRange <= 0) && world.func_147439_a(i1, j1, k1).canBeReplacedByLeaves(world, i1, j1, k1)) {
                           this.func_150516_a(world, i1, j1, k1, LOTRMod.leaves3, 1);
                        }
                     }
                  }

                  if (leafRange >= maxLeafRange) {
                     leafRange = minLeafRange;
                     minLeafRange = 1;
                     ++maxLeafRange;
                     if (maxLeafRange > leafWidth) {
                        maxLeafRange = leafWidth;
                     }
                  } else {
                     ++leafRange;
                  }
               }

               trunkTop = random.nextInt(3);

               for(j1 = 0; j1 < height - trunkTop; ++j1) {
                  Block block2 = world.func_147439_a(i, j + j1, k);
                  if (block2.isAir(world, i, j + j1, k) || block2.isLeaves(world, i, j + j1, k)) {
                     this.func_150516_a(world, i, j + j1, k, LOTRMod.wood3, 1);
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }
}
