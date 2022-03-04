package lotr.common.world.feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenWillow extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int minHeight;
   private int maxHeight;
   private boolean needsWater;

   public LOTRWorldGenWillow(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood6;
      this.woodMeta = 1;
      this.leafBlock = LOTRMod.leaves6;
      this.leafMeta = 1;
      this.minHeight = 8;
      this.maxHeight = 13;
      this.needsWater = false;
   }

   public LOTRWorldGenWillow setNeedsWater() {
      this.needsWater = true;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      boolean flag = true;
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

            for(int i1 = i - range; i1 <= i + range && flag; ++i1) {
               for(k1 = k - range; k1 <= k + range && flag; ++k1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, i1, j1, k1)) {
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

      int i1;
      int k1;
      int i2;
      int k2;
      if (this.needsWater) {
         boolean water = false;
         int attempts = 4;

         for(i1 = 0; i1 < attempts; ++i1) {
            k1 = i + MathHelper.func_76136_a(random, -12, 12);
            i2 = j + MathHelper.func_76136_a(random, -8, 4);
            k2 = k + MathHelper.func_76136_a(random, -12, 12);
            if (world.func_147439_a(k1, i2, k2).func_149688_o() == Material.field_151586_h) {
               water = true;
               break;
            }
         }

         if (!water) {
            return false;
         }
      }

      if (!flag) {
         return false;
      } else {
         below.onPlantGrow(world, i, j - 1, k, i, j, k);
         List vineGrows = new ArrayList();
         k1 = 0;

         int rootX;
         int rootY;
         int rootZ;
         int roots;
         while(k1 < 360) {
            k1 += 30 + random.nextInt(30);
            float angleR = (float)Math.toRadians((double)k1);
            float sin = MathHelper.func_76126_a(angleR);
            float cos = MathHelper.func_76134_b(angleR);
            k2 = j + height - 3 - random.nextInt(3);
            rootX = 2 + random.nextInt(4);
            rootY = i;
            rootZ = k2;
            roots = k;

            for(int l = 0; l < rootX; ++l) {
               if (l > 0 && (l % 4 == 0 || random.nextInt(3) == 0)) {
                  ++rootZ;
               }

               if (random.nextFloat() < Math.abs(cos)) {
                  rootY = (int)((float)rootY + Math.signum(cos));
               }

               if (random.nextFloat() < Math.abs(sin)) {
                  roots = (int)((float)roots + Math.signum(sin));
               }

               this.func_150516_a(world, rootY, rootZ, roots, this.woodBlock, this.woodMeta);
            }

            this.spawnLeafCluster(world, random, rootY, rootZ, roots);
            vineGrows.add(new ChunkCoordinates(rootY, rootZ, roots));
         }

         for(i1 = 0; i1 < height; ++i1) {
            this.func_150516_a(world, i, j + i1, k, this.woodBlock, this.woodMeta);
            if (i1 == height - 1) {
               this.spawnLeafCluster(world, random, i, j + i1, k);
               vineGrows.add(new ChunkCoordinates(i, j + i1, k));
            }
         }

         for(i1 = i - 1; i1 <= i + 1; ++i1) {
            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               i2 = i1 - i;
               k2 = k1 - k;
               if (Math.abs(i2) != Math.abs(k2)) {
                  rootX = i1;
                  rootY = j + 1 + random.nextInt(2);
                  rootZ = k1;
                  roots = 0;

                  while(world.func_147439_a(rootX, rootY, k1).isReplaceable(world, rootX, rootY, rootZ)) {
                     this.func_150516_a(world, rootX, rootY, rootZ, this.woodBlock, this.woodMeta | 12);
                     world.func_147439_a(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
                     --rootY;
                     ++roots;
                     if (roots > 4 + random.nextInt(3)) {
                        break;
                     }
                  }
               }
            }
         }

         Iterator var29 = vineGrows.iterator();

         while(var29.hasNext()) {
            ChunkCoordinates coords = (ChunkCoordinates)var29.next();
            this.spawnVineCluster(world, random, coords.field_71574_a, coords.field_71572_b, coords.field_71573_c);
         }

         return true;
      }
   }

   private void spawnLeafCluster(World world, Random random, int i, int j, int k) {
      int leafRange = 3;
      int leafRangeSq = leafRange * leafRange;
      int leafRangeSqLess = (int)(((double)leafRange - 0.5D) * ((double)leafRange - 0.5D));

      for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
         for(int j1 = j - leafRange; j1 <= j + leafRange; ++j1) {
            for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
               int i2 = i1 - i;
               int j2 = j1 - j;
               int k2 = k1 - k;
               int dist = i2 * i2 + j2 * j2 + k2 * k2;
               int taxicab = Math.abs(i2) + Math.abs(j2) + Math.abs(k2);
               if ((dist < leafRangeSqLess || dist < leafRangeSq && random.nextInt(3) == 0) && taxicab <= 4) {
                  Block block = world.func_147439_a(i1, j1, k1);
                  if (block.isReplaceable(world, i1, j1, k1) || block.isLeaves(world, i1, j1, k1)) {
                     this.func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                  }
               }
            }
         }
      }

   }

   private void spawnVineCluster(World world, Random random, int i, int j, int k) {
      int leafRange = 3;
      int leafRangeSq = leafRange * leafRange;

      for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
         for(int j1 = j - leafRange; j1 <= j + leafRange; ++j1) {
            for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
               int i2 = i1 - i;
               int j2 = j1 - j;
               int k2 = k1 - k;
               int dist = i2 * i2 + j2 * j2 + k2 * k2;
               if (dist < leafRangeSq) {
                  Block block = world.func_147439_a(i1, j1, k1);
                  int meta = world.func_72805_g(i1, j1, k1);
                  if (block == this.leafBlock && meta == this.leafMeta) {
                     int vineChance = 2;
                     if (random.nextInt(vineChance) == 0 && world.func_147439_a(i1 - 1, j1, k1).isAir(world, i1 - 1, j1, k1)) {
                        this.growVines(world, random, i1 - 1, j1, k1, 8);
                     }

                     if (random.nextInt(vineChance) == 0 && world.func_147439_a(i1 + 1, j1, k1).isAir(world, i1 + 1, j1, k1)) {
                        this.growVines(world, random, i1 + 1, j1, k1, 2);
                     }

                     if (random.nextInt(vineChance) == 0 && world.func_147439_a(i1, j1, k1 - 1).isAir(world, i1, j1, k1 - 1)) {
                        this.growVines(world, random, i1, j1, k1 - 1, 1);
                     }

                     if (random.nextInt(vineChance) == 0 && world.func_147439_a(i1, j1, k1 + 1).isAir(world, i1, j1, k1 + 1)) {
                        this.growVines(world, random, i1, j1, k1 + 1, 4);
                     }
                  }
               }
            }
         }
      }

   }

   private void growVines(World world, Random random, int i, int j, int k, int meta) {
      this.func_150516_a(world, i, j, k, LOTRMod.willowVines, meta);
      int vines = 0;

      while(true) {
         --j;
         if (!world.func_147439_a(i, j, k).isAir(world, i, j, k) || vines >= 2 + random.nextInt(4)) {
            return;
         }

         this.func_150516_a(world, i, j, k, LOTRMod.willowVines, meta);
         ++vines;
      }
   }
}
