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

public class LOTRWorldGenPalm extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private boolean hasDates = false;
   private int minHeight = 5;
   private int maxHeight = 8;

   public LOTRWorldGenPalm(boolean flag, Block b, int m, Block b1, int m1) {
      super(flag);
      this.woodBlock = b;
      this.woodMeta = m;
      this.leafBlock = b1;
      this.leafMeta = m1;
   }

   public LOTRWorldGenPalm setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public LOTRWorldGenPalm setDates() {
      this.hasDates = true;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      if (j >= 1 && j + height + 2 <= 256) {
         if (!this.isReplaceable(world, i, j, k)) {
            return false;
         } else {
            Block below = world.func_147439_a(i, j - 1, k);
            if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
               return false;
            } else {
               for(int l = 1; l < height + 2; ++l) {
                  for(int i1 = i - 1; i1 <= i + 1; ++i1) {
                     for(int k1 = k - 1; k1 <= k + 1; ++k1) {
                        if (!this.isReplaceable(world, i1, j + l, k1)) {
                           return false;
                        }
                     }
                  }
               }

               float trunkAngle = 6.2831855F * random.nextFloat();
               float trunkSin = MathHelper.func_76126_a(trunkAngle);
               float trunkCos = MathHelper.func_76134_b(trunkAngle);
               int trunkX = i;
               int trunkZ = k;
               int trunkSwitches = 0;
               int trunkSwitchesMax = MathHelper.func_76136_a(random, 0, 3);

               int l;
               for(l = 0; l < height; ++l) {
                  this.func_150516_a(world, trunkX, j + l, trunkZ, this.woodBlock, this.woodMeta);
                  if (this.hasDates && l == height - 3) {
                     for(int d = 0; d < 4; ++d) {
                        ForgeDirection dir = ForgeDirection.getOrientation(d + 2);
                        this.func_150516_a(world, trunkX + dir.getOpposite().offsetX, j + l, trunkZ + dir.getOpposite().offsetZ, LOTRMod.dateBlock, d);
                     }
                  }

                  if (l > height / 3 && l < height - 1 && trunkSwitches < trunkSwitchesMax && random.nextBoolean()) {
                     ++trunkSwitches;
                     if ((double)Math.abs(trunkCos) >= MathHelper.func_82716_a(random, 0.25D, 0.5D)) {
                        trunkX = (int)((float)trunkX + Math.signum(trunkCos));
                     }

                     if ((double)Math.abs(trunkSin) >= MathHelper.func_82716_a(random, 0.25D, 0.5D)) {
                        trunkZ = (int)((float)trunkZ + Math.signum(trunkSin));
                     }
                  }
               }

               l = 0;

               while(l < 360) {
                  l += 15 + random.nextInt(15);
                  float angleR = (float)Math.toRadians((double)l);
                  float sin = MathHelper.func_76126_a(angleR);
                  float cos = MathHelper.func_76134_b(angleR);
                  float angleY = random.nextFloat() * (float)Math.toRadians(30.0D);
                  float cosY = MathHelper.func_76134_b(angleY);
                  float sinY = MathHelper.func_76126_a(angleY);
                  int i1 = trunkX;
                  int j1 = j + height - 1;
                  int k1 = trunkZ;
                  int branchLength = 5;

                  for(int l = 1; l <= branchLength; ++l) {
                     if (Math.floor((double)(sinY * (float)l)) != Math.floor((double)(sinY * (float)(l - 1)))) {
                        j1 = (int)((float)j1 + Math.signum(sinY));
                     } else {
                        double dCos = Math.floor((double)Math.abs(cos * (float)l)) - Math.floor((double)Math.abs(cos * (float)(l - 1)));
                        double dSin = Math.floor((double)Math.abs(sin * (float)l)) - Math.floor((double)Math.abs(sin * (float)(l - 1)));
                        dCos = Math.abs(dCos);
                        dSin = Math.abs(dSin);
                        boolean cosOrSin = dCos == dSin ? random.nextBoolean() : dCos > dSin;
                        if (cosOrSin) {
                           i1 = (int)((float)i1 + Math.signum(cos));
                        } else {
                           k1 = (int)((float)k1 + Math.signum(sin));
                        }
                     }

                     boolean wood = l == 1;
                     Block block = world.func_147439_a(i1, j1, k1);
                     boolean replacingWood = block.isWood(world, i1, j1, k1);
                     if (!block.isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1) && !replacingWood) {
                        break;
                     }

                     if (wood) {
                        this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta);
                     } else if (!replacingWood) {
                        this.func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                     }

                     if (l >= 5) {
                        break;
                     }
                  }
               }

               world.func_147439_a(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
               return true;
            }
         }
      } else {
         return false;
      }
   }
}
