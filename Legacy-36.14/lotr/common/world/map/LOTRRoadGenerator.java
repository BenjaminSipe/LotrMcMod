package lotr.common.world.map;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.FlowerEntry;

public class LOTRRoadGenerator {
   public static final int ROAD_DEPTH = 4;

   public static boolean generateRoad(World world, Random rand, int i, int k, LOTRBiome biome, Block[] blocks, byte[] metadata, double[] heightNoise) {
      int chunkX = i & 15;
      int chunkZ = k & 15;
      int xzIndex = chunkX * 16 + chunkZ;
      int ySize = blocks.length / 256;
      LOTRRoadType roadType = biome.getRoadBlock();
      LOTRRoadType.BridgeType bridgeType = biome.getBridgeBlock();
      int roadTop;
      int bridgeBase;
      if (LOTRRoads.isRoadAt(i, k)) {
         roadTop = 0;
         bridgeBase = 0;
         boolean bridge = false;
         boolean bridgeSlab = false;

         int j;
         int index;
         int index;
         int indexLower;
         for(j = ySize - 1; j > 0; --j) {
            index = xzIndex * ySize + j;
            Block block = blocks[index];
            if (block.func_149662_c()) {
               roadTop = j;
               bridge = false;
               break;
            }

            if (block.func_149688_o().func_76224_d()) {
               roadTop = j + 1;
               bridgeBase = roadTop;
               int maxBridgeTop = j + 6;
               float bridgeHeight = 0.0F;

               for(index = j - 1; index > 0; --index) {
                  indexLower = xzIndex * ySize + index;
                  Block blockLower = blocks[indexLower];
                  if (!blockLower.func_149688_o().func_76224_d()) {
                     break;
                  }

                  bridgeHeight += 0.5F;
               }

               index = (int)Math.floor((double)bridgeHeight);
               roadTop += index;
               roadTop = Math.min(roadTop, maxBridgeTop);
               if (roadTop >= maxBridgeTop) {
                  bridgeSlab = true;
               } else {
                  float bridgeHeightR = bridgeHeight - (float)index;
                  if (bridgeHeightR < 0.5F) {
                     bridgeSlab = true;
                  }
               }

               bridge = true;
               break;
            }
         }

         boolean isSlab;
         if (bridge) {
            LOTRRoadType.RoadBlock bridgeBlock = bridgeType.getBlock(rand, false);
            LOTRRoadType.RoadBlock bridgeBlockSlab = bridgeType.getBlock(rand, true);
            LOTRRoadType.RoadBlock bridgeEdge = bridgeType.getEdge(rand);
            LOTRRoadType.RoadBlock bridgeFence = bridgeType.getFence(rand);
            isSlab = isFenceAt(i, k);
            index = xzIndex * ySize + roadTop;
            if (isSlab) {
               boolean pillar = isPillarAt(i, k);
               int pillarIndex;
               int j;
               if (pillar) {
                  for(j = roadTop + 4; j > 0; --j) {
                     pillarIndex = xzIndex * ySize + j;
                     Block block = blocks[pillarIndex];
                     if (block.func_149662_c()) {
                        break;
                     }

                     if (j >= roadTop + 4) {
                        blocks[pillarIndex] = bridgeFence.block;
                        metadata[pillarIndex] = (byte)bridgeFence.meta;
                     } else if (j >= roadTop + 3) {
                        blocks[pillarIndex] = bridgeBlock.block;
                        metadata[pillarIndex] = (byte)bridgeBlock.meta;
                     } else {
                        blocks[pillarIndex] = bridgeEdge.block;
                        metadata[pillarIndex] = (byte)bridgeEdge.meta;
                     }
                  }
               } else {
                  blocks[index] = bridgeEdge.block;
                  metadata[index] = (byte)bridgeEdge.meta;
                  j = index + 1;
                  blocks[j] = bridgeFence.block;
                  metadata[j] = (byte)bridgeFence.meta;
                  if (roadTop > bridgeBase) {
                     pillarIndex = index - 1;
                     blocks[pillarIndex] = bridgeEdge.block;
                     metadata[pillarIndex] = (byte)bridgeEdge.meta;
                  }

                  pillarIndex = bridgeBase + 2;
                  if (roadTop - 1 > pillarIndex) {
                     int indexSupport = xzIndex * ySize + pillarIndex;
                     blocks[indexSupport] = bridgeFence.block;
                     metadata[indexSupport] = (byte)bridgeFence.meta;
                  }
               }
            } else {
               if (bridgeSlab) {
                  blocks[index] = bridgeBlockSlab.block;
                  metadata[index] = (byte)bridgeBlockSlab.meta;
               } else {
                  blocks[index] = bridgeBlock.block;
                  metadata[index] = (byte)bridgeBlock.meta;
               }

               if (roadTop > bridgeBase) {
                  indexLower = index - 1;
                  blocks[indexLower] = bridgeBlock.block;
                  metadata[indexLower] = (byte)bridgeBlock.meta;
               }
            }
         } else {
            for(j = roadTop; j > roadTop - 4 && j > 0; --j) {
               index = xzIndex * ySize + j;
               float repair = roadType.getRepair();
               if (rand.nextFloat() < repair) {
                  boolean isTop = j == roadTop;
                  isSlab = false;
                  if (isTop && j >= 63) {
                     double avgNoise = (heightNoise[index] + heightNoise[index + 1]) / 2.0D;
                     isSlab = avgNoise < 0.0D;
                  }

                  LOTRRoadType.RoadBlock roadblock = roadType.getBlock(rand, biome, isTop, isSlab);
                  blocks[index] = roadblock.block;
                  metadata[index] = (byte)roadblock.meta;
               }
            }
         }

         return true;
      } else if (!roadType.hasFlowers()) {
         return false;
      } else {
         roadTop = 0;

         int i1;
         for(bridgeBase = ySize - 1; bridgeBase > 0; --bridgeBase) {
            i1 = xzIndex * ySize + bridgeBase;
            Block block = blocks[i1];
            if (block.func_149662_c()) {
               roadTop = bridgeBase;
               break;
            }
         }

         boolean adjRoad = false;

         int k1;
         label190:
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               if ((i1 != 0 || k1 != 0) && LOTRRoads.isRoadAt(i + i1, k + k1)) {
                  adjRoad = true;
                  break label190;
               }
            }
         }

         if (adjRoad) {
            i1 = xzIndex * ySize + roadTop + 1;
            FlowerEntry flower = biome.getRandomFlower(world, rand, i, roadTop, k);
            blocks[i1] = flower.block;
            metadata[i1] = (byte)flower.metadata;
         } else {
            adjRoad = false;

            label172:
            for(i1 = -3; i1 <= 3; ++i1) {
               for(k1 = -3; k1 <= 3; ++k1) {
                  if ((Math.abs(i1) > 2 || Math.abs(k1) > 2) && LOTRRoads.isRoadAt(i + i1, k + k1)) {
                     adjRoad = true;
                     break label172;
                  }
               }
            }

            if (adjRoad) {
               i1 = xzIndex * ySize + roadTop + 1;
               blocks[i1] = Blocks.field_150362_t;
               metadata[i1] = 4;
            }
         }

         return true;
      }
   }

   private static boolean isFenceAt(int i, int k) {
      for(int i1 = -1; i1 <= 1; ++i1) {
         for(int k1 = -1; k1 <= 1; ++k1) {
            if ((i1 != 0 || k1 != 0) && !LOTRRoads.isRoadAt(i + i1, k + k1)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean isPillarAt(int i, int k) {
      int pRange = 8;
      int xmod = IntMath.mod(i, pRange);
      int zmod = IntMath.mod(k, pRange);
      if (IntMath.mod(xmod + zmod, pRange) == 0) {
         return !isBridgeEdgePillar(i + 1, k - 1) && !isBridgeEdgePillar(i + 1, k + 1);
      } else {
         return false;
      }
   }

   private static boolean isBridgeEdgePillar(int i, int k) {
      return LOTRRoads.isRoadAt(i, k) && isFenceAt(i, k) && isPillarAt(i, k);
   }
}
