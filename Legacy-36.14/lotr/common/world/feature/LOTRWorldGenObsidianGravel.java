package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenObsidianGravel extends WorldGenerator {
   private Block genBlock;
   private int genMeta;

   public LOTRWorldGenObsidianGravel() {
      this.genBlock = LOTRMod.obsidianGravel;
      this.genMeta = 0;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      BiomeGenBase biome = world.func_72807_a(i, k);
      Block below = world.func_147439_a(i, j - 1, k);
      if (below != biome.field_76752_A && below != biome.field_76753_B && below != Blocks.field_150348_b) {
         return false;
      } else {
         int numBlocks = MathHelper.func_76136_a(random, 6, 16);
         float angle = random.nextFloat() * 3.1415927F;
         float sin = MathHelper.func_76126_a(angle);
         float cos = MathHelper.func_76126_a(angle);
         float div = 8.0F;
         double xMin = (double)((float)i - sin * (float)numBlocks / div);
         double xMax = (double)((float)i + sin * (float)numBlocks / div);
         double zMin = (double)((float)k - cos * (float)numBlocks / div);
         double zMax = (double)((float)k + cos * (float)numBlocks / div);
         double yMin = (double)(j + random.nextInt(3) - 2);
         double yMax = (double)(j + random.nextInt(3) - 2);

         for(int l = 0; l <= numBlocks; ++l) {
            float lerp = (float)l / (float)numBlocks;
            double xLerp = xMin + (xMax - xMin) * (double)lerp;
            double yLerp = yMin + (yMax - yMin) * (double)lerp;
            double zLerp = zMin + (zMax - zMin) * (double)lerp;
            double d9 = random.nextDouble() * (double)numBlocks / 16.0D;
            double d10 = (double)(MathHelper.func_76126_a((float)l * 3.1415927F / (float)numBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.func_76126_a((float)l * 3.1415927F / (float)numBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.func_76128_c(xLerp - d10 / 2.0D);
            int j1 = MathHelper.func_76128_c(yLerp - d11 / 2.0D);
            int k1 = MathHelper.func_76128_c(zLerp - d10 / 2.0D);
            int l1 = MathHelper.func_76128_c(xLerp + d10 / 2.0D);
            int i2 = MathHelper.func_76128_c(yLerp + d11 / 2.0D);
            int j2 = MathHelper.func_76128_c(zLerp + d10 / 2.0D);

            for(int k2 = i1; k2 <= l1; ++k2) {
               double d12 = ((double)k2 + 0.5D - xLerp) / (d10 / 2.0D);
               if (d12 * d12 < 1.0D) {
                  for(int l2 = j1; l2 <= i2; ++l2) {
                     double d13 = ((double)l2 + 0.5D - yLerp) / (d11 / 2.0D);
                     if (d12 * d12 + d13 * d13 < 1.0D) {
                        for(int i3 = k1; i3 <= j2; ++i3) {
                           double d14 = ((double)i3 + 0.5D - zLerp) / (d10 / 2.0D);
                           if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && this.canReplace(world, k2, l2, i3)) {
                              world.func_147465_d(k2, l2, i3, this.genBlock, this.genMeta, 2);
                           }
                        }
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean canReplace(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      BiomeGenBase biome = world.func_72807_a(i, k);
      return block == biome.field_76752_A || block == biome.field_76753_B || block == Blocks.field_150348_b || block.isReplaceable(world, i, j, k);
   }
}
