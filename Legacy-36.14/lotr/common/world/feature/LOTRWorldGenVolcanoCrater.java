package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenVolcanoCrater extends WorldGenerator {
   private int minWidth = 5;
   private int maxWidth = 15;
   private int heightCheck = 8;

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      world.func_72807_a(i, k);
      if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k) && world.func_147439_a(i, j - 1, k) != Blocks.field_150348_b) {
         return false;
      } else {
         int craterWidth = MathHelper.func_76136_a(random, this.minWidth, this.maxWidth);
         int highestHeight = j;
         int lowestHeight = j;

         int l;
         int posX;
         int posZ;
         for(int i1 = i - craterWidth; i1 <= i + craterWidth; ++i1) {
            for(l = k - craterWidth; l <= k + craterWidth; ++l) {
               posX = world.func_72976_f(i1, l);
               posZ = posX - 1;
               if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i1, posZ, l) && world.func_147439_a(i1, posZ, l) != Blocks.field_150348_b) {
                  return false;
               }

               if (posX > highestHeight) {
                  highestHeight = posX;
               }

               if (posX < lowestHeight) {
                  lowestHeight = posX;
               }
            }
         }

         if (highestHeight - lowestHeight > this.heightCheck) {
            return false;
         } else {
            int spheres = 1;

            for(l = 0; l < spheres; ++l) {
               posX = i;
               posZ = k;
               int posY = world.func_72825_h(i, k);
               int sphereWidth = MathHelper.func_76136_a(random, this.minWidth, this.maxWidth);

               for(int i1 = i - sphereWidth; i1 <= posX + sphereWidth; ++i1) {
                  for(int k1 = posZ - sphereWidth; k1 <= posZ + sphereWidth; ++k1) {
                     int i2 = i1 - posX;
                     int k2 = k1 - posZ;
                     int xzDistSq = i2 * i2 + k2 * k2;
                     if (xzDistSq < sphereWidth * sphereWidth || xzDistSq < (sphereWidth + 1) * (sphereWidth + 1) && random.nextInt(3) == 0) {
                        int jTop = world.func_72825_h(i1, k1);

                        int depthHere;
                        for(depthHere = jTop; depthHere > posY; --depthHere) {
                           this.func_150516_a(world, i1, depthHere, k1, Blocks.field_150350_a, 0);
                        }

                        depthHere = (int)(((double)sphereWidth - Math.sqrt((double)xzDistSq)) * 0.7D) + random.nextInt(2);

                        int j1;
                        for(j1 = posY - depthHere - 1; j1 >= posY - (depthHere + this.heightCheck + 2 + random.nextInt(2)) && !world.func_147439_a(i1, j1, k1).func_149662_c(); --j1) {
                           this.func_150516_a(world, i1, j1, k1, Blocks.field_150348_b, 0);
                        }

                        for(j1 = posY; j1 >= posY - depthHere; --j1) {
                           int jDepth = posY - j1;
                           if (jDepth > 6) {
                              this.func_150516_a(world, i1, j1, k1, Blocks.field_150353_l, 0);
                              if (!world.func_147439_a(i1, j1 - 1, k1).func_149662_c()) {
                                 this.func_150516_a(world, i1, j1 - 1, k1, Blocks.field_150343_Z, 0);
                              }
                           } else {
                              this.func_150516_a(world, i1, j1, k1, Blocks.field_150350_a, 0);
                           }

                           if (jDepth > 4) {
                              this.func_150516_a(world, i1, j1 - 1, k1, Blocks.field_150343_Z, 0);
                           } else if (jDepth > 2) {
                              if (random.nextInt(4) == 0) {
                                 this.func_150516_a(world, i1, j1 - 1, k1, Blocks.field_150351_n, 0);
                                 this.func_150516_a(world, i1, j1 - 2, k1, Blocks.field_150348_b, 0);
                              } else {
                                 this.func_150516_a(world, i1, j1 - 1, k1, LOTRMod.obsidianGravel, 0);
                                 this.func_150516_a(world, i1, j1 - 2, k1, Blocks.field_150343_Z, 0);
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
   }
}
