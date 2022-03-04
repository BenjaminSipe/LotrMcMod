package lotr.common.world.mapgen;

import java.util.Random;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenRavine;

public class LOTRMapGenRavine extends MapGenRavine {
   private float[] ravineNoise = new float[1024];

   protected void func_151538_a(World world, int i, int k, int chunkX, int chunkZ, Block[] blocks) {
      BiomeGenBase biome = this.field_75039_c.func_72807_a(chunkX * 16, chunkZ * 16);
      if (this.field_75038_b.nextBoolean()) {
         super.func_151538_a(world, i, k, chunkX, chunkZ, blocks);
      } else if (biome instanceof LOTRBiomeGenMordor && ((LOTRBiomeGenMordor)biome).isGorgoroth()) {
         for(int l = 0; l < 20; ++l) {
            super.func_151538_a(world, i, k, chunkX, chunkZ, blocks);
         }
      }

   }

   protected void func_151540_a(long seed, int chunkX, int chunkZ, Block[] blocks, double d, double d1, double d2, float f, float ravineAngle, float f2, int intPar1, int intPar2, double increase) {
      Random random = new Random(seed);
      double chunkCentreX = (double)(chunkX * 16 + 8);
      double chunkCentreZ = (double)(chunkZ * 16 + 8);
      float f3 = 0.0F;
      float f4 = 0.0F;
      if (intPar2 <= 0) {
         int j1 = this.field_75040_a * 16 - 16;
         intPar2 = j1 - random.nextInt(j1 / 4);
      }

      boolean flag = false;
      if (intPar1 == -1) {
         intPar1 = intPar2 / 2;
         flag = true;
      }

      float f5 = 1.0F;

      for(int k1 = 0; k1 < 256; ++k1) {
         if (k1 == 0 || random.nextInt(3) == 0) {
            f5 = 1.0F + random.nextFloat() * random.nextFloat() * 1.0F;
         }

         this.ravineNoise[k1] = f5 * f5;
      }

      for(; intPar1 < intPar2; ++intPar1) {
         double d6 = 1.5D + (double)(MathHelper.func_76126_a((float)intPar1 * 3.1415927F / (float)intPar2) * f * 1.0F);
         double d7 = d6 * increase;
         d6 *= (double)random.nextFloat() * 0.25D + 0.75D;
         d7 *= (double)random.nextFloat() * 0.25D + 0.75D;
         float f6 = MathHelper.func_76134_b(f2);
         float f7 = MathHelper.func_76126_a(f2);
         d += (double)(MathHelper.func_76134_b(ravineAngle) * f6);
         d1 += (double)f7;
         d2 += (double)(MathHelper.func_76126_a(ravineAngle) * f6);
         f2 *= 0.7F;
         f2 += f4 * 0.05F;
         ravineAngle += f3 * 0.05F;
         f4 *= 0.8F;
         f3 *= 0.5F;
         f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (flag || random.nextInt(4) != 0) {
            double d8 = d - chunkCentreX;
            double d9 = d2 - chunkCentreZ;
            double d10 = (double)(intPar2 - intPar1);
            double d11 = (double)(f + 2.0F + 16.0F);
            if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11) {
               return;
            }

            if (d >= chunkCentreX - 16.0D - d6 * 2.0D && d2 >= chunkCentreZ - 16.0D - d6 * 2.0D && d <= chunkCentreX + 16.0D + d6 * 2.0D && d2 <= chunkCentreZ + 16.0D + d6 * 2.0D) {
               int xMin = MathHelper.func_76128_c(d - d6) - chunkX * 16 - 1;
               int xMax = MathHelper.func_76128_c(d + d6) - chunkX * 16 + 1;
               int yMin = MathHelper.func_76128_c(d1 - d7) - 1;
               int yMax = MathHelper.func_76128_c(d1 + d7) + 1;
               int zMin = MathHelper.func_76128_c(d2 - d6) - chunkZ * 16 - 1;
               int zMax = MathHelper.func_76128_c(d2 + d6) - chunkZ * 16 + 1;
               xMin = Math.max(xMin, 0);
               xMax = Math.min(xMax, 16);
               yMin = Math.max(yMin, 1);
               yMax = Math.min(yMax, 120);
               zMin = Math.max(zMin, 0);
               zMax = Math.min(zMax, 16);
               boolean isWater = false;

               int i1;
               int k1;
               label123:
               for(i1 = xMin; i1 < xMax; ++i1) {
                  for(int k1 = zMin; k1 < zMax; ++k1) {
                     for(int j1 = yMax + 1; j1 >= yMin - 1; --j1) {
                        k1 = (i1 * 16 + k1) * 256 + j1;
                        if (j1 >= 0 && j1 < 256) {
                           if (this.isOceanBlock(blocks, k1, i1, j1, k1, chunkX, chunkZ)) {
                              isWater = true;
                           }

                           if (j1 != yMin - 1 && i1 != xMin && i1 != xMax - 1 && k1 != zMin && k1 != zMax - 1) {
                              j1 = yMin;
                           }

                           if (isWater) {
                              break label123;
                           }
                        }
                     }
                  }
               }

               if (!isWater) {
                  for(i1 = xMin; i1 < xMax; ++i1) {
                     double d12 = ((double)(i1 + chunkX * 16) + 0.5D - d) / d6;

                     for(k1 = zMin; k1 < zMax; ++k1) {
                        double d13 = ((double)(k1 + chunkZ * 16) + 0.5D - d2) / d6;
                        int blockIndex = (i1 * 16 + k1) * 256 + yMax;
                        boolean topBlock = false;
                        if (d12 * d12 + d13 * d13 < 1.0D) {
                           for(int j1 = yMax - 1; j1 >= yMin; --j1) {
                              double d14 = ((double)j1 + 0.5D - d1) / d7;
                              if ((d12 * d12 + d13 * d13) * (double)this.ravineNoise[j1] + d14 * d14 / 6.0D < 1.0D) {
                                 if (this.isTopBlock(blocks, blockIndex, i1, j1, k1, chunkX, chunkZ)) {
                                    topBlock = true;
                                 }

                                 this.digBlock(blocks, blockIndex, i1, j1, k1, chunkX, chunkZ, topBlock);
                              }

                              --blockIndex;
                           }
                        }
                     }
                  }

                  if (flag) {
                     break;
                  }
               }
            }
         }
      }

   }

   private boolean isTopBlock(Block[] data, int index, int i, int j, int k, int chunkX, int chunkZ) {
      BiomeGenBase biome = this.field_75039_c.func_72807_a(i + chunkX * 16, k + chunkZ * 16);
      return data[index] == biome.field_76752_A;
   }

   protected void digBlock(Block[] data, int index, int i, int j, int k, int chunkX, int chunkZ, boolean topBlock) {
      BiomeGenBase biome = this.field_75039_c.func_72807_a(i + chunkX * 16, k + chunkZ * 16);
      Block top = biome.field_76752_A;
      Block filler = biome.field_76753_B;
      Block block = data[index];
      if (LOTRMapGenCaves.isTerrainBlock(block, biome)) {
         if (j < 10) {
            data[index] = Blocks.field_150356_k;
         } else if (biome instanceof LOTRBiomeGenMordor && ((LOTRBiomeGenMordor)biome).isGorgoroth() && j < 60) {
            data[index] = Blocks.field_150356_k;
         } else {
            data[index] = Blocks.field_150350_a;
            if (topBlock && data[index - 1] == filler) {
               data[index - 1] = top;
            }
         }
      }

   }
}
