package lotr.common.world.mapgen;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRChunkProvider;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;

public class LOTRMapGenCaves extends MapGenBase {
   public LOTRChunkProvider.ChunkFlags chunkFlags;

   protected void generateLargeCaveNode(long seed, int par3, int par4, Block[] blockArray, double par6, double par8, double par10, boolean cutSurface) {
      this.generateCaveNode(seed, par3, par4, blockArray, par6, par8, par10, 1.0F + this.field_75038_b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D, cutSurface);
   }

   protected void generateCaveNode(long seed, int par3, int par4, Block[] blockArray, double par6, double par8, double par10, float par12, float angle, float par14, int par15, int par16, double par17, boolean cutSurface) {
      double var19 = (double)(par3 * 16 + 8);
      double var21 = (double)(par4 * 16 + 8);
      float var23 = 0.0F;
      float var24 = 0.0F;
      Random caveRand = new Random(seed);
      if (par16 <= 0) {
         int var26 = this.field_75040_a * 16 - 16;
         par16 = var26 - caveRand.nextInt(var26 / 4);
      }

      boolean var54 = false;
      if (par15 == -1) {
         par15 = par16 / 2;
         var54 = true;
      }

      int var27 = caveRand.nextInt(par16 / 2) + par16 / 4;

      for(boolean var28 = caveRand.nextInt(6) == 0; par15 < par16; ++par15) {
         double var29 = 1.5D + (double)(MathHelper.func_76126_a((float)par15 * 3.1415927F / (float)par16) * par12 * 1.0F);
         double var31 = var29 * par17;
         float var33 = MathHelper.func_76134_b(par14);
         float var34 = MathHelper.func_76126_a(par14);
         par6 += (double)(MathHelper.func_76134_b(angle) * var33);
         par8 += (double)var34;
         par10 += (double)(MathHelper.func_76126_a(angle) * var33);
         if (var28) {
            par14 *= 0.92F;
         } else {
            par14 *= 0.7F;
         }

         par14 += var24 * 0.1F;
         angle += var23 * 0.1F;
         var24 *= 0.9F;
         var23 *= 0.75F;
         var24 += (caveRand.nextFloat() - caveRand.nextFloat()) * caveRand.nextFloat() * 2.0F;
         var23 += (caveRand.nextFloat() - caveRand.nextFloat()) * caveRand.nextFloat() * 4.0F;
         if (!var54 && par15 == var27 && par12 > 1.0F && par16 > 0) {
            this.generateCaveNode(caveRand.nextLong(), par3, par4, blockArray, par6, par8, par10, caveRand.nextFloat() * 0.5F + 0.5F, angle - 1.5707964F, par14 / 3.0F, par15, par16, 1.0D, cutSurface);
            this.generateCaveNode(caveRand.nextLong(), par3, par4, blockArray, par6, par8, par10, caveRand.nextFloat() * 0.5F + 0.5F, angle + 1.5707964F, par14 / 3.0F, par15, par16, 1.0D, cutSurface);
            return;
         }

         if (var54 || caveRand.nextInt(4) != 0) {
            double var35 = par6 - var19;
            double var37 = par10 - var21;
            double var39 = (double)(par16 - par15);
            double var41 = (double)(par12 + 2.0F + 16.0F);
            if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41) {
               return;
            }

            if (par6 >= var19 - 16.0D - var29 * 2.0D && par10 >= var21 - 16.0D - var29 * 2.0D && par6 <= var19 + 16.0D + var29 * 2.0D && par10 <= var21 + 16.0D + var29 * 2.0D) {
               int var55 = MathHelper.func_76128_c(par6 - var29) - par3 * 16 - 1;
               int var36 = MathHelper.func_76128_c(par6 + var29) - par3 * 16 + 1;
               int var57 = MathHelper.func_76128_c(par8 - var31) - 1;
               int var38 = MathHelper.func_76128_c(par8 + var31) + 1;
               int var56 = MathHelper.func_76128_c(par10 - var29) - par4 * 16 - 1;
               int var40 = MathHelper.func_76128_c(par10 + var29) - par4 * 16 + 1;
               var55 = Math.max(var55, 0);
               var36 = Math.min(var36, 16);
               var57 = Math.max(var57, 1);
               var38 = Math.min(var38, 248);
               var56 = Math.max(var56, 0);
               var40 = Math.min(var40, 16);
               boolean anyWater = false;

               int var42;
               int var45;
               for(var42 = var55; !anyWater && var42 < var36; ++var42) {
                  for(int var43 = var56; !anyWater && var43 < var40; ++var43) {
                     for(int var44 = var38 + 1; !anyWater && var44 >= var57 - 1; --var44) {
                        var45 = (var42 * 16 + var43) * 256 + var44;
                        if (var44 >= 0 && var44 < 256) {
                           if (blockArray[var45] == Blocks.field_150358_i || blockArray[var45] == Blocks.field_150355_j) {
                              anyWater = true;
                           }

                           if (var44 != var57 - 1 && var42 != var55 && var42 != var36 - 1 && var43 != var56 && var43 != var40 - 1) {
                              var44 = var57;
                           }
                        }
                     }
                  }
               }

               if (!anyWater) {
                  for(var42 = var55; var42 < var36; ++var42) {
                     double var59 = ((double)(var42 + par3 * 16) + 0.5D - par6) / var29;

                     for(var45 = var56; var45 < var40; ++var45) {
                        double var46 = ((double)(var45 + par4 * 16) + 0.5D - par10) / var29;
                        int xzIndex = var42 * 16 + var45;
                        int blockIndex = xzIndex * 256 + var57 + 1;
                        if (var59 * var59 + var46 * var46 < 1.0D) {
                           for(int var50 = var57; var50 <= var38 - 1; ++var50) {
                              double var51 = ((double)var50 + 0.5D - par8) / var31;
                              if (var51 > -0.7D && var59 * var59 + var51 * var51 + var46 * var46 < 1.0D) {
                                 LOTRBiome biome = (LOTRBiome)this.field_75039_c.func_72807_a(var42 + par3 * 16, var45 + par4 * 16);
                                 this.digBlock(blockArray, blockIndex, xzIndex, var42, var50, var45, par3, par4, biome, cutSurface);
                              }

                              ++blockIndex;
                           }
                        }
                     }
                  }

                  if (var54) {
                     break;
                  }
               }
            }
         }
      }

   }

   protected void digBlock(Block[] blockArray, int index, int xzIndex, int i, int j, int k, int chunkX, int chunkZ, LOTRBiome biome, boolean cutSurface) {
      Block block = blockArray[index];
      boolean isTop = false;
      boolean belowVillageOrRoad = false;
      int topCheckDepth = 1;
      byte roadDepth;
      if (j >= 59 - topCheckDepth) {
         isTop = true;
         roadDepth = 5;

         for(int j1 = topCheckDepth + 1; j1 <= topCheckDepth + roadDepth && j + j1 <= 255; ++j1) {
            if (blockArray[index + j1].func_149662_c()) {
               isTop = false;
               break;
            }
         }
      }

      int j1;
      byte grassCheckMax;
      if (this.chunkFlags.isVillage || this.chunkFlags.roadFlags[xzIndex]) {
         roadDepth = 4;
         if (j >= 59 - roadDepth) {
            belowVillageOrRoad = true;
            grassCheckMax = 5;

            for(j1 = roadDepth + 1; j1 <= roadDepth + grassCheckMax && j + j1 <= 255; ++j1) {
               if (blockArray[index + j1].func_149662_c()) {
                  belowVillageOrRoad = false;
                  break;
               }
            }
         }
      }

      boolean dig = isTerrainBlock(block, biome) || block.func_149688_o().func_76224_d();
      if (belowVillageOrRoad) {
         dig = false;
      }

      if (isTop && (!cutSurface || this.chunkFlags.isVillage)) {
         dig = false;
      }

      if (dig) {
         if (j < 10) {
            blockArray[index] = Blocks.field_150353_l;
         } else {
            blockArray[index] = Blocks.field_150350_a;
            if (isTop) {
               grassCheckMax = 5;

               for(j1 = 1; j1 <= grassCheckMax && j - j1 > 0; ++j1) {
                  if (blockArray[index - j1] == biome.field_76753_B) {
                     blockArray[index - j1] = biome.field_76752_A;
                     break;
                  }
               }
            }
         }
      }

   }

   protected int caveRarity() {
      return 10;
   }

   protected int getCaveGenerationHeight() {
      return this.field_75038_b.nextInt(this.field_75038_b.nextInt(120) + 8);
   }

   protected void func_151538_a(World world, int i, int k, int chunkX, int chunkZ, Block[] blocks) {
      int caves = this.field_75038_b.nextInt(this.field_75038_b.nextInt(this.field_75038_b.nextInt(40) + 1) + 1);
      if (this.field_75038_b.nextInt(this.caveRarity()) != 0) {
         caves = 0;
      }

      for(int l = 0; l < caves; ++l) {
         int i1 = i * 16 + this.field_75038_b.nextInt(16);
         int j1 = this.getCaveGenerationHeight();
         int k1 = k * 16 + this.field_75038_b.nextInt(16);
         boolean cutSurface = this.field_75038_b.nextInt(5) == 0;
         int nodes = 1;
         if (this.field_75038_b.nextInt(4) == 0) {
            this.generateLargeCaveNode(this.field_75038_b.nextLong(), chunkX, chunkZ, blocks, (double)i1, (double)j1, (double)k1, cutSurface);
            nodes += this.field_75038_b.nextInt(4);
         }

         for(int n = 0; n < nodes; ++n) {
            float angle = this.field_75038_b.nextFloat() * 3.1415927F * 2.0F;
            float var18 = (this.field_75038_b.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float size = this.field_75038_b.nextFloat() * 2.0F + this.field_75038_b.nextFloat();
            if (this.field_75038_b.nextInt(10) == 0) {
               size *= this.field_75038_b.nextFloat() * this.field_75038_b.nextFloat() * 3.0F + 1.0F;
            }

            this.generateCaveNode(this.field_75038_b.nextLong(), chunkX, chunkZ, blocks, (double)i1, (double)j1, (double)k1, size, angle, var18, 0, 0, 1.0D, cutSurface);
         }
      }

   }

   public static boolean isTerrainBlock(Block block, BiomeGenBase biome) {
      if (block != biome.field_76752_A && block != biome.field_76753_B) {
         if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150354_m && block != LOTRMod.whiteSand && block != Blocks.field_150351_n && block != LOTRMod.mudGrass && block != LOTRMod.mud) {
            if (block == LOTRMod.dirtPath) {
               return true;
            } else if (block != Blocks.field_150348_b && block != LOTRMod.rock && block != Blocks.field_150322_A && block != LOTRMod.redSandstone && block != LOTRMod.whiteSandstone) {
               return block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel;
            } else {
               return true;
            }
         } else {
            return true;
         }
      } else {
         return true;
      }
   }
}
