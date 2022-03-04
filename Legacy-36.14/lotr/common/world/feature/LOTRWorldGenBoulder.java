package lotr.common.world.feature;

import java.util.Random;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenBoulder extends WorldGenerator {
   private Block id;
   private int meta;
   private int minWidth;
   private int maxWidth;
   private int heightCheck = 3;

   public LOTRWorldGenBoulder(Block i, int j, int k, int l) {
      super(false);
      this.id = i;
      this.meta = j;
      this.minWidth = k;
      this.maxWidth = l;
   }

   public LOTRWorldGenBoulder setHeightCheck(int i) {
      this.heightCheck = i;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      world.func_72807_a(i, k);
      if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k)) {
         return false;
      } else {
         int boulderWidth = MathHelper.func_76136_a(random, this.minWidth, this.maxWidth);
         int highestHeight = j;
         int lowestHeight = j;

         int spheres;
         int l;
         int posX;
         for(spheres = i - boulderWidth; spheres <= i + boulderWidth; ++spheres) {
            for(l = k - boulderWidth; l <= k + boulderWidth; ++l) {
               posX = world.func_72976_f(spheres, l);
               if (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, spheres, posX - 1, l)) {
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
            spheres = 1 + random.nextInt(boulderWidth + 1);

            for(l = 0; l < spheres; ++l) {
               posX = i + MathHelper.func_76136_a(random, -boulderWidth, boulderWidth);
               int posZ = k + MathHelper.func_76136_a(random, -boulderWidth, boulderWidth);
               int posY = world.func_72825_h(posX, posZ);
               int sphereWidth = MathHelper.func_76136_a(random, this.minWidth, this.maxWidth);

               for(int i1 = posX - sphereWidth; i1 <= posX + sphereWidth; ++i1) {
                  for(int j1 = posY - sphereWidth; j1 <= posY + sphereWidth; ++j1) {
                     for(int k1 = posZ - sphereWidth; k1 <= posZ + sphereWidth; ++k1) {
                        int i2 = i1 - posX;
                        int j2 = j1 - posY;
                        int k2 = k1 - posZ;
                        int dist = i2 * i2 + j2 * j2 + k2 * k2;
                        if (dist < sphereWidth * sphereWidth || dist < (sphereWidth + 1) * (sphereWidth + 1) && random.nextInt(3) == 0) {
                           int j3;
                           for(j3 = j1; j3 >= 0 && !world.func_147439_a(i1, j3 - 1, k1).func_149662_c(); --j3) {
                           }

                           this.func_150516_a(world, i1, j3, k1, this.id, this.meta);
                           world.func_147439_a(i1, j3 - 1, k1).onPlantGrow(world, i1, j3 - 1, k1, i1, j3 - 1, k1);
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
