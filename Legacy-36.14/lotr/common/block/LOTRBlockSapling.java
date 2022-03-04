package lotr.common.block;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockSapling extends LOTRBlockSaplingBase {
   public LOTRBlockSapling() {
      this.setSaplingNames(new String[]{"shirePine", "mallorn", "mirkOak", "mirkOakRed"});
   }

   public void growTree(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k) & 7;
      WorldGenerator treeGen = null;
      int trunkNeg = 0;
      int trunkPos = 0;
      int xOffset = 0;
      int zOffset = 0;
      boolean cross = false;
      if (meta == 0) {
         treeGen = LOTRTreeType.SHIRE_PINE.create(true, random);
      }

      int i1;
      int k1;
      int[] boughTree;
      if (meta == 1) {
         if (world.func_147439_a(i, j - 1, k) == LOTRMod.quenditeGrass) {
            label151:
            for(i1 = -4; i1 <= 4; ++i1) {
               for(k1 = -4; k1 <= 4; ++k1) {
                  boolean canGenerate = true;

                  label144:
                  for(int i2 = -2; i2 <= 2; ++i2) {
                     for(int k2 = -2; k2 <= 2; ++k2) {
                        int i3 = i + i1 + i2;
                        int k3 = k + k1 + k2;
                        if (!this.isSameSapling(world, i3, j, k3, meta) || world.func_147439_a(i3, j - 1, k3) != LOTRMod.quenditeGrass) {
                           canGenerate = false;
                           break label144;
                        }
                     }
                  }

                  if (canGenerate) {
                     treeGen = LOTRTreeType.MALLORN_EXTREME_SAPLING.create(true, random);
                     trunkPos = 2;
                     trunkNeg = 2;
                     xOffset = i1;
                     zOffset = k1;
                     break label151;
                  }
               }
            }
         }

         if (treeGen == null) {
            boughTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
            if (boughTree != null) {
               treeGen = LOTRTreeType.MALLORN_PARTY.create(true, random);
               trunkPos = 1;
               trunkNeg = 1;
               xOffset = boughTree[0];
               zOffset = boughTree[1];
            }
         }

         if (treeGen == null) {
            boughTree = LOTRBlockSaplingBase.findCrossShape(world, i, j, k, this, meta);
            if (boughTree != null) {
               treeGen = LOTRTreeType.MALLORN_BOUGHS.create(true, random);
               trunkPos = 1;
               trunkNeg = 1;
               xOffset = boughTree[0];
               zOffset = boughTree[1];
               cross = true;
            }
         }

         if (treeGen == null) {
            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
            treeGen = LOTRTreeType.MALLORN.create(true, random);
         }
      }

      if (meta == 2) {
         boughTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
         if (boughTree != null) {
            treeGen = LOTRTreeType.MIRK_OAK_LARGE.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = boughTree[0];
            zOffset = boughTree[1];
         }

         if (treeGen == null) {
            treeGen = LOTRTreeType.MIRK_OAK.create(true, random);
            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
         }
      }

      if (meta == 3) {
         boughTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
         if (boughTree != null) {
            treeGen = LOTRTreeType.RED_OAK_LARGE.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = boughTree[0];
            zOffset = boughTree[1];
         }

         if (treeGen == null) {
            treeGen = LOTRTreeType.RED_OAK.create(true, random);
            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
         }
      }

      for(i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
         for(k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
            if (!cross || Math.abs(i1) == 0 || Math.abs(k1) == 0) {
               world.func_147465_d(i + xOffset + i1, j, k + zOffset + k1, Blocks.field_150350_a, 0, 4);
            }
         }
      }

      if (treeGen != null && !treeGen.func_76484_a(world, random, i + xOffset, j, k + zOffset)) {
         for(i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
            for(k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
               if (!cross || Math.abs(i1) == 0 || Math.abs(k1) == 0) {
                  world.func_147465_d(i + xOffset + i1, j, k + zOffset + k1, this, meta, 4);
               }
            }
         }
      }

   }

   public boolean func_149705_a(World world, int i, int j, int k, int side, ItemStack item) {
      if (super.func_149705_a(world, i, j, k, side, item)) {
         return true;
      } else {
         return item.func_77960_j() == 1 && world.func_147439_a(i, j - 1, k) == LOTRMod.quenditeGrass;
      }
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      if (super.func_149718_j(world, i, j, k)) {
         return true;
      } else {
         int meta = world.func_72805_g(i, j, k) & 7;
         return meta == 1 && world.func_147439_a(i, j - 1, k) == LOTRMod.quenditeGrass;
      }
   }
}
