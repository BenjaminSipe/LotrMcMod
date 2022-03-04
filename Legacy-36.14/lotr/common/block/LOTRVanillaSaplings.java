package lotr.common.block;

import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRVanillaSaplings {
   public static void growTree(World world, int i, int j, int k, Random random) {
      Block block = world.func_147439_a(i, j, k);
      int meta = world.func_72805_g(i, j, k) & 7;
      WorldGenerator treeGen = null;
      int trunkNeg = 0;
      int trunkPos = 0;
      int xOffset = 0;
      int zOffset = 0;
      int[] partyTree;
      if (meta == 0) {
         partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, block, meta);
         if (partyTree != null) {
            treeGen = LOTRTreeType.OAK_PARTY.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = partyTree[0];
            zOffset = partyTree[1];
         }

         if (treeGen == null) {
            if (random.nextInt(10) == 0) {
               treeGen = LOTRTreeType.OAK_LARGE.create(true, random);
            } else {
               treeGen = LOTRTreeType.OAK.create(true, random);
            }

            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
         }
      }

      int i1;
      int i1;
      if (meta == 1) {
         for(i1 = 0; i1 >= -1; --i1) {
            for(i1 = 0; i1 >= -1; --i1) {
               if (isSameSapling(world, i + i1, j, k + i1, meta) && isSameSapling(world, i + i1 + 1, j, k + i1, meta) && isSameSapling(world, i + i1, j, k + i1 + 1, meta) && isSameSapling(world, i + i1 + 1, j, k + i1 + 1, meta)) {
                  if (random.nextBoolean()) {
                     treeGen = LOTRTreeType.SPRUCE_MEGA.create(true, random);
                  } else {
                     treeGen = LOTRTreeType.SPRUCE_MEGA_THIN.create(true, random);
                  }

                  trunkNeg = 0;
                  trunkPos = 1;
                  xOffset = i1;
                  zOffset = i1;
                  break;
               }
            }

            if (treeGen != null) {
               break;
            }
         }

         if (treeGen == null) {
            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
            treeGen = LOTRTreeType.SPRUCE.create(true, random);
         }
      }

      if (meta == 2) {
         partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, block, meta);
         if (partyTree != null) {
            treeGen = LOTRTreeType.BIRCH_PARTY.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = partyTree[0];
            zOffset = partyTree[1];
         }

         if (treeGen == null) {
            if (random.nextInt(10) == 0) {
               treeGen = LOTRTreeType.BIRCH_LARGE.create(true, random);
            } else {
               treeGen = LOTRTreeType.BIRCH.create(true, random);
            }

            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
         }
      }

      if (meta == 3) {
         for(i1 = 0; i1 >= -1; --i1) {
            for(i1 = 0; i1 >= -1; --i1) {
               if (isSameSapling(world, i + i1, j, k + i1, meta) && isSameSapling(world, i + i1 + 1, j, k + i1, meta) && isSameSapling(world, i + i1, j, k + i1 + 1, meta) && isSameSapling(world, i + i1 + 1, j, k + i1 + 1, meta)) {
                  treeGen = LOTRTreeType.JUNGLE_LARGE.create(true, random);
                  trunkNeg = 0;
                  trunkPos = 1;
                  xOffset = i1;
                  zOffset = i1;
                  break;
               }
            }

            if (treeGen != null) {
               break;
            }
         }

         if (treeGen == null) {
            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
            treeGen = LOTRTreeType.JUNGLE.create(true, random);
         }
      }

      if (meta == 4) {
         treeGen = LOTRTreeType.ACACIA.create(true, random);
      }

      if (meta == 5) {
         partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, block, meta);
         if (partyTree != null) {
            treeGen = LOTRTreeType.DARK_OAK_PARTY.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = partyTree[0];
            zOffset = partyTree[1];
         }

         if (treeGen == null) {
            for(i1 = 0; i1 >= -1; --i1) {
               for(int k1 = 0; k1 >= -1; --k1) {
                  if (isSameSapling(world, i + i1, j, k + k1, meta) && isSameSapling(world, i + i1 + 1, j, k + k1, meta) && isSameSapling(world, i + i1, j, k + k1 + 1, meta) && isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
                     treeGen = LOTRTreeType.DARK_OAK.create(true, random);
                     trunkNeg = 0;
                     trunkPos = 1;
                     xOffset = i1;
                     zOffset = k1;
                     break;
                  }
               }

               if (treeGen != null) {
                  break;
               }
            }
         }

         if (treeGen == null) {
            return;
         }
      }

      for(i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
         for(i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
            world.func_147465_d(i + xOffset + i1, j, k + zOffset + i1, Blocks.field_150350_a, 0, 4);
         }
      }

      if (treeGen != null && !treeGen.func_76484_a(world, random, i + xOffset, j, k + zOffset)) {
         for(i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
            for(i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
               world.func_147465_d(i + xOffset + i1, j, k + zOffset + i1, Blocks.field_150345_g, meta, 4);
            }
         }
      }

   }

   private static boolean isSameSapling(World world, int i, int j, int k, int meta) {
      return LOTRBlockSaplingBase.isSameSapling(world, i, j, k, Blocks.field_150345_g, meta);
   }
}
