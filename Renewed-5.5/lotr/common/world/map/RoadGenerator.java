package lotr.common.world.map;

import com.google.common.math.IntMath;
import java.util.Random;
import java.util.function.UnaryOperator;
import lotr.common.world.biome.LOTRBiomeWrapper;
import lotr.common.world.gen.feature.LOTRFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.chunk.IChunk;

public class RoadGenerator {
   public static final int ROAD_DEPTH = 4;

   public boolean generateRoad(IWorld world, IChunk chunk, Random rand, LOTRBiomeWrapper biomeWrapper, BlockPos topPos, int seaLevel) {
      MapSettings mapSettings = MapSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedMap();
      RoadBlockProvider roadProvider = biomeWrapper.getRoadBlockProvider();
      BridgeBlockProvider bridgeProvider = biomeWrapper.getBridgeBlockProvider();
      int x = topPos.func_177958_n();
      int z = topPos.func_177952_p();
      if (!mapSettings.getRoadPointCache().isRoadAt(x, z)) {
         return false;
      } else {
         int roadTop = 0;
         int bridgeBase = 0;
         boolean bridge = false;
         boolean bridgeSlab = false;
         BlockState topState = chunk.func_180495_p(topPos);
         Mutable edgePos;
         int ySupport;
         if (topState.func_200015_d(chunk, topPos)) {
            bridge = false;
            roadTop = topPos.func_177956_o();
         } else if (topState.func_185904_a().func_76224_d()) {
            roadTop = topPos.func_177956_o() + 1;
            bridgeBase = roadTop;
            int maxBridgeTop = topPos.func_177956_o() + 6;
            float bridgeHeight = 0.0F;

            for(edgePos = (new Mutable()).func_189533_g(topPos); edgePos.func_177956_o() > 0; bridgeHeight += 0.5F) {
               edgePos.func_189536_c(Direction.DOWN);
               BlockState belowState = chunk.func_180495_p(edgePos);
               if (!belowState.func_185904_a().func_76224_d()) {
                  break;
               }
            }

            ySupport = (int)Math.floor((double)bridgeHeight);
            roadTop += ySupport;
            roadTop = Math.min(roadTop, maxBridgeTop);
            if (roadTop >= maxBridgeTop) {
               bridgeSlab = true;
            } else {
               float bridgeHeightR = bridgeHeight - (float)ySupport;
               if (bridgeHeightR < 0.5F) {
                  bridgeSlab = true;
               }
            }

            bridge = true;
         }

         boolean pillar;
         Mutable roadPos;
         BlockState block;
         if (bridge) {
            boolean fence = this.isFenceAt(mapSettings, x, z);
            if (fence) {
               pillar = this.isPillarAt(mapSettings, x, z);
               if (pillar) {
                  int pillarTop = roadTop + 4;

                  for(roadPos = new Mutable(x, pillarTop, z); roadPos.func_177956_o() > 0; roadPos.func_189536_c(Direction.DOWN)) {
                     block = chunk.func_180495_p(roadPos);
                     if (block.func_200015_d(chunk, roadPos)) {
                        break;
                     }

                     if (roadPos.func_177956_o() >= pillarTop) {
                        this.setBlock(chunk, roadPos, bridgeProvider.getFenceBlock(rand, roadPos));
                     } else if (roadPos.func_177956_o() >= pillarTop - 1) {
                        this.setBlock(chunk, roadPos, bridgeProvider.getMainBlock(rand, roadPos));
                     } else {
                        this.setBlockAndUpdateAdjacent(chunk, world, roadPos, bridgeProvider.getBeamBlock(rand, roadPos));
                     }
                  }
               } else {
                  edgePos = new Mutable(x, roadTop, z);
                  this.setBlock(chunk, edgePos, bridgeProvider.getBeamBlock(rand, edgePos));
                  edgePos.func_189536_c(Direction.UP);
                  this.setBlockAndUpdateAdjacent(chunk, world, edgePos, bridgeProvider.getFenceBlock(rand, edgePos));
                  if (roadTop > bridgeBase) {
                     edgePos.func_185336_p(roadTop - 1);
                     this.setBlockAndUpdateAdjacent(chunk, world, edgePos, bridgeProvider.getFenceBlock(rand, edgePos));
                  }

                  ySupport = bridgeBase + 2;
                  if (roadTop - 1 > ySupport) {
                     edgePos.func_185336_p(ySupport);
                     this.setBlockAndUpdateAdjacent(chunk, world, edgePos, bridgeProvider.getFenceBlock(rand, edgePos));
                  }
               }
            } else {
               Mutable bridgePos = new Mutable(x, roadTop, z);
               if (bridgeSlab) {
                  this.setBlock(chunk, bridgePos, bridgeProvider.getMainSlabBlock(rand, bridgePos));
                  if (roadTop > bridgeBase) {
                     bridgePos.func_189536_c(Direction.DOWN);
                     this.setBlock(chunk, bridgePos, bridgeProvider.getMainSlabBlockInverted(rand, bridgePos));
                  }
               } else {
                  this.setBlock(chunk, bridgePos, bridgeProvider.getMainBlock(rand, bridgePos));
               }
            }
         } else {
            float repair = roadProvider.getRepair();
            pillar = this.isRoadEdge(mapSettings, x, z);
            boolean isHedge = roadProvider.hasHedge() && pillar;
            roadPos = new Mutable(x, roadTop, z);
            if (isHedge) {
               roadPos.func_189536_c(Direction.UP);
               if (rand.nextFloat() < roadProvider.getHedgeDensity()) {
                  block = roadProvider.getHedgeBlock(rand, roadPos);
                  this.setBlock(chunk, roadPos, block);
                  this.setGrassToDirtBelowIfPlacedBlockSolid(chunk, roadPos, block);
               }
            } else {
               boolean isDistinctEdge = roadProvider.hasDistinctEdge() && pillar;
               boolean elevatedEdge = isDistinctEdge && rand.nextFloat() < repair;

               for(boolean isTop = true; roadPos.func_177956_o() > roadTop - 4 && roadPos.func_177956_o() > 0; isTop = false) {
                  float repairHere = repair;
                  if (isDistinctEdge && isTop) {
                     repairHere = repair * repair;
                  }

                  if (rand.nextFloat() < repairHere) {
                     boolean isSlab = false;
                     if (isTop && roadPos.func_177956_o() >= seaLevel + 1) {
                        isSlab = this.determineIsSlab(chunk, roadPos);
                     }

                     if (isTop && elevatedEdge) {
                        UnaryOperator moveUpByHalfBlock = (inputIsSlab) -> {
                           if (inputIsSlab) {
                              inputIsSlab = false;
                           } else {
                              inputIsSlab = true;
                              roadPos.func_189536_c(Direction.UP);
                           }

                           return inputIsSlab;
                        };
                        isSlab = (Boolean)moveUpByHalfBlock.apply(isSlab);
                        if (rand.nextInt(18) == 0) {
                           isSlab = (Boolean)moveUpByHalfBlock.apply(isSlab);
                        }
                     }

                     RoadBlockProvider roadToUse = isDistinctEdge ? roadProvider.getEdgeProvider() : roadProvider;
                     BlockState roadBlockState = isSlab ? roadToUse.getTopSlabBlock(rand, roadPos) : (isTop ? roadToUse.getTopBlock(rand, roadPos) : roadToUse.getFillerBlock(rand, roadPos));
                     if (roadToUse.requiresPostProcessing()) {
                        this.setBlockAndUpdateAdjacent(chunk, world, roadPos, roadBlockState);
                     } else {
                        this.setBlock(chunk, roadPos, roadBlockState);
                     }

                     if (roadPos.func_177956_o() > roadTop) {
                        this.setGrassToDirtBelowIfPlacedBlockSolid(chunk, roadPos, roadBlockState);
                     }
                  }

                  roadPos.func_189536_c(Direction.DOWN);
               }
            }
         }

         return true;
      }
   }

   private boolean determineIsSlab(IChunk chunk, Mutable roadPos) {
      ChunkPos pos = chunk.func_76632_l();
      int chunkXStart = pos.func_180334_c();
      int chunkXEnd = pos.func_180332_e();
      int chunkZStart = pos.func_180333_d();
      int chunkZEnd = pos.func_180330_f();
      int x = roadPos.func_177958_n();
      int z = roadPos.func_177952_p();
      int y = roadPos.func_177956_o();
      int totalChecked = 0;
      int nearbySolid = 0;
      Mutable checkPos = new Mutable();

      for(int checkX = x - 1; checkX <= x + 1; ++checkX) {
         for(int checkZ = z - 1; checkZ <= z + 1; ++checkZ) {
            if ((checkX != x || checkZ != z) && checkX >= chunkXStart && checkX <= chunkXEnd && checkZ >= chunkZStart && checkZ <= chunkZEnd) {
               checkPos.func_181079_c(checkX, y, checkZ);
               BlockState checkState = chunk.func_180495_p(checkPos);
               if (checkState.func_200015_d(chunk, checkPos) || checkState.func_177230_c() instanceof SlabBlock) {
                  ++nearbySolid;
               }

               ++totalChecked;
            }
         }
      }

      if (totalChecked == 0) {
         return false;
      } else {
         float avgSolid = (float)nearbySolid / (float)totalChecked;
         return (double)avgSolid < 1.0D;
      }
   }

   private void setBlock(IChunk chunk, BlockPos pos, BlockState state) {
      chunk.func_177436_a(pos, state, false);
   }

   private void setBlockAndUpdateAdjacent(IChunk chunk, IWorld world, BlockPos pos, BlockState state) {
      this.setBlock(chunk, pos, state);
      chunk.func_201594_d(pos);
   }

   private void setGrassToDirtBelowIfPlacedBlockSolid(IChunk chunk, BlockPos pos, BlockState placedState) {
      if (placedState.func_224755_d(chunk, pos, Direction.DOWN)) {
         LOTRFeatures.setGrassToDirtBelowDuringChunkGen(chunk, pos);
      }

   }

   private boolean isRoadEdge(MapSettings mapSettings, int i, int k) {
      for(int i1 = -1; i1 <= 1; ++i1) {
         for(int k1 = -1; k1 <= 1; ++k1) {
            if ((i1 != 0 || k1 != 0) && !mapSettings.getRoadPointCache().isRoadAt(i + i1, k + k1)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isFenceAt(MapSettings mapSettings, int i, int k) {
      return this.isRoadEdge(mapSettings, i, k);
   }

   private boolean isPillarAt(MapSettings mapSettings, int i, int k) {
      int pRange = 8;
      int xmod = IntMath.mod(i, pRange);
      int zmod = IntMath.mod(k, pRange);
      if (IntMath.mod(xmod + zmod, pRange) == 0) {
         return !this.isBridgeEdgePillar(mapSettings, i + 1, k - 1) && !this.isBridgeEdgePillar(mapSettings, i + 1, k + 1);
      } else {
         return false;
      }
   }

   private boolean isBridgeEdgePillar(MapSettings mapSettings, int i, int k) {
      return mapSettings.getRoadPointCache().isRoadAt(i, k) && this.isFenceAt(mapSettings, i, k) && this.isPillarAt(mapSettings, i, k);
   }
}
