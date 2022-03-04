package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenFir extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int minHeight;
   private int maxHeight;

   public LOTRWorldGenFir(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood4;
      this.woodMeta = 3;
      this.leafBlock = LOTRMod.leaves4;
      this.leafMeta = 3;
      this.minHeight = 6;
      this.maxHeight = 13;
   }

   public LOTRWorldGenFir setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      boolean flag = true;
      int leafLevel;
      if (j >= 1 && height + 2 <= 256) {
         for(int j1 = j; j1 <= j + height + 2; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= j + height - 1) {
               range = 2;
            }

            for(leafLevel = i - range; leafLevel <= i + range && flag; ++leafLevel) {
               for(int k1 = k - range; k1 <= k + range && flag; ++k1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, leafLevel, j1, k1)) {
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
         leafLevel = j + height + 2;
         int leafLayers = 3;

         int j1;
         for(j1 = 0; j1 <= leafLayers * 2; ++j1) {
            int leafRange = j1 / 2;

            for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
               for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
                  Block block = world.func_147439_a(i1, leafLevel, k1);
                  int i2 = Math.abs(i1 - i);
                  int k2 = Math.abs(k1 - k);
                  if (i2 + k2 <= leafRange && (block.isReplaceable(world, i1, leafLevel, k1) || block.isLeaves(world, i1, leafLevel, k1))) {
                     this.func_150516_a(world, i1, leafLevel, k1, this.leafBlock, this.leafMeta);
                  }
               }
            }

            --leafLevel;
         }

         for(j1 = 0; j1 < height; ++j1) {
            this.func_150516_a(world, i, j + j1, k, this.woodBlock, this.woodMeta);
         }

         return true;
      }
   }
}
