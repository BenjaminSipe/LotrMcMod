package lotr.common.world.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenKanuka extends WorldGenAbstractTree {
   private int minHeight;
   private int maxHeight;
   private int trunkWidth;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenKanuka(boolean flag) {
      this(flag, 5, 12, 0);
   }

   public LOTRWorldGenKanuka(boolean flag, int i, int j, int k) {
      super(flag);
      this.woodBlock = LOTRMod.wood9;
      this.woodMeta = 1;
      this.leafBlock = LOTRMod.leaves9;
      this.leafMeta = 1;
      this.minHeight = i;
      this.maxHeight = j;
      this.trunkWidth = k;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      float trunkAngleY = (float)Math.toRadians((double)(90.0F - MathHelper.func_151240_a(random, 0.0F, 35.0F)));
      float trunkAngle = random.nextFloat() * 3.1415927F * 2.0F;
      float trunkYCos = MathHelper.func_76134_b(trunkAngleY);
      float trunkYSin = MathHelper.func_76126_a(trunkAngleY);
      float trunkCos = MathHelper.func_76134_b(trunkAngle) * trunkYCos;
      float trunkSin = MathHelper.func_76126_a(trunkAngle) * trunkYCos;
      boolean flag = true;
      if (j >= 1 && j + height + 3 <= 256) {
         int pass;
         int trunkX;
         int trunkZ;
         int trunkY;
         for(pass = j; pass <= j + height + 3; ++pass) {
            trunkX = this.trunkWidth + 1;
            if (pass == j) {
               trunkX = this.trunkWidth;
            }

            if (pass >= j + height + 2) {
               trunkX = this.trunkWidth + 2;
            }

            for(trunkZ = i - trunkX; trunkZ <= i + trunkX && flag; ++trunkZ) {
               for(trunkY = k - trunkX; trunkY <= k + trunkX && flag; ++trunkY) {
                  if (pass >= 0 && pass < 256) {
                     if (!this.isReplaceable(world, trunkZ, pass, trunkY)) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         for(pass = i - this.trunkWidth; pass <= i + this.trunkWidth && flag; ++pass) {
            for(trunkX = k - this.trunkWidth; trunkX <= k + this.trunkWidth && flag; ++trunkX) {
               Block block = world.func_147439_a(pass, j - 1, trunkX);
               boolean isSoil = block.canSustainPlant(world, pass, j - 1, trunkX, ForgeDirection.UP, (BlockSapling)Blocks.field_150345_g);
               if (!isSoil) {
                  flag = false;
               }
            }
         }

         if (!flag) {
            return false;
         } else {
            for(pass = 0; pass <= 1; ++pass) {
               if (pass == 1) {
                  for(trunkX = i - this.trunkWidth; trunkX <= i + this.trunkWidth; ++trunkX) {
                     for(trunkZ = k - this.trunkWidth; trunkZ <= k + this.trunkWidth; ++trunkZ) {
                        world.func_147439_a(trunkX, j - 1, trunkZ).onPlantGrow(world, trunkX, j - 1, trunkZ, trunkX, j, trunkZ);
                     }
                  }
               }

               trunkX = i;
               trunkZ = k;
               trunkY = j;
               List trunkCoords = new ArrayList();
               int trunkHeight = height;

               int l;
               int j1;
               int i1;
               for(l = 0; l < trunkHeight; ++l) {
                  if (l > 0) {
                     if (Math.floor((double)(trunkCos * (float)l)) != Math.floor((double)(trunkCos * (float)(l + 1)))) {
                        trunkX = (int)((float)trunkX + Math.signum(trunkCos));
                     }

                     if (Math.floor((double)(trunkSin * (float)l)) != Math.floor((double)(trunkSin * (float)(l + 1)))) {
                        trunkZ = (int)((float)trunkZ + Math.signum(trunkSin));
                     }

                     if (Math.floor((double)(trunkYSin * (float)l)) != Math.floor((double)(trunkYSin * (float)(l + 1)))) {
                        trunkY = (int)((float)trunkY + Math.signum(trunkYSin));
                     }
                  }

                  j1 = trunkY;

                  for(i1 = trunkX - this.trunkWidth; i1 <= trunkX + this.trunkWidth; ++i1) {
                     for(int k1 = trunkZ - this.trunkWidth; k1 <= trunkZ + this.trunkWidth; ++k1) {
                        if (pass == 0 && !this.isReplaceable(world, i1, j1, k1)) {
                           return false;
                        }

                        if (pass == 1) {
                           this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 12);
                           trunkCoords.add(new int[]{i1, j1, k1});
                        }
                     }
                  }
               }

               if (pass == 1) {
                  l = (int)((double)height * 0.67D);

                  int i1;
                  int j1;
                  int k1;
                  for(j1 = 0; j1 < 360; this.growLeafCanopy(world, random, i1, j1, k1)) {
                     i1 = MathHelper.func_76136_a(random, 70, 150);
                     i1 -= this.trunkWidth * 10;
                     i1 = Math.max(i1, 20);
                     j1 += i1;
                     float angle = (float)Math.toRadians((double)j1);
                     float cos = MathHelper.func_76134_b(angle);
                     float sin = MathHelper.func_76126_a(angle);
                     float angleY = (float)Math.toRadians((double)(70.0F - random.nextFloat() * 20.0F));
                     float cosY = MathHelper.func_76134_b(angleY);
                     float sinY = MathHelper.func_76126_a(angleY);
                     cos *= cosY;
                     sin *= cosY;
                     int length = l + MathHelper.func_76136_a(random, -3, 3);
                     length = Math.max(length, 3);
                     length += this.trunkWidth;
                     int trunkIndex = MathHelper.func_76136_a(random, (int)((double)(trunkCoords.size() - 1) * 0.5D), trunkCoords.size() - 1);
                     int[] oneTrunkCoord = (int[])trunkCoords.get(trunkIndex);
                     i1 = oneTrunkCoord[0];
                     j1 = oneTrunkCoord[1];
                     k1 = oneTrunkCoord[2];

                     for(int l = 0; l < length; ++l) {
                        if (Math.floor((double)(cos * (float)l)) != Math.floor((double)(cos * (float)(l + 1)))) {
                           i1 = (int)((float)i1 + Math.signum(cos));
                        }

                        if (Math.floor((double)(sin * (float)l)) != Math.floor((double)(sin * (float)(l + 1)))) {
                           k1 = (int)((float)k1 + Math.signum(sin));
                        }

                        if (Math.floor((double)(sinY * (float)l)) != Math.floor((double)(sinY * (float)(l + 1)))) {
                           j1 = (int)((float)j1 + Math.signum(sinY));
                        }

                        Block block = world.func_147439_a(i1, j1, k1);
                        if (!block.isReplaceable(world, i1, j1, k1) && !block.isWood(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
                           break;
                        }

                        this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 12);
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void growLeafCanopy(World world, Random random, int i, int j, int k) {
      int leafHeight = 2;
      int maxRange = 1 + random.nextInt(3);

      for(int j1 = 0; j1 < leafHeight; ++j1) {
         int j2 = j + j1;
         int leafRange = maxRange - j1;

         for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
            for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
               int i2 = Math.abs(i1 - i);
               int k2 = Math.abs(k1 - k);
               int dist = i2 + k2;
               if (dist <= leafRange) {
                  Block block = world.func_147439_a(i1, j2, k1);
                  if (block.isReplaceable(world, i1, j2, k1) || block.isLeaves(world, i1, j2, k1)) {
                     this.func_150516_a(world, i1, j2, k1, this.leafBlock, this.leafMeta);
                  }
               }
            }
         }
      }

      Block block = world.func_147439_a(i, j, k);
      if (block.isReplaceable(world, i, j, k) || block.isLeaves(world, i, j, k)) {
         this.func_150516_a(world, i, j, k, this.woodBlock, this.woodMeta | 12);
      }

   }
}
