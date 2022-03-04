package lotr.common.world.biome.surface;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Random;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.FrozenOceanSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class MiddleEarthSurfaceBuilder extends SurfaceBuilder {
   private final FrozenOceanSurfaceBuilder frozenOcean;
   private final PerlinNoiseGenerator icebergBorderNoise;

   public MiddleEarthSurfaceBuilder(Codec codec) {
      super(codec);
      this.frozenOcean = new FrozenOceanSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_);
      this.icebergBorderNoise = LOTRBiomeBase.makeSingleLayerPerlinNoise(5231241491057810726L);
   }

   private boolean isFrozenIcebergTerrain(IChunk chunk, Biome biome, int x, int z) {
      if (LOTRBiomes.areBiomesEqual(biome, LOTRBiomes.SEA.getInitialisedBiome(), chunk.getWorldForge())) {
         MapSettings map = MapSettingsManager.serverInstance().getCurrentLoadedMap();
         int zBlurRange = 32;
         float icebergThreshold = 0.5F;
         int minAdjustedZ = z - zBlurRange;
         if (map.getWaterLatitudes().getIceCoverageForLatitude(minAdjustedZ) > icebergThreshold) {
            double xBlurScale = 1.0D / (double)zBlurRange;
            int adjustedZ = z + (int)(this.icebergBorderNoise.func_215464_a((double)x * xBlurScale, 0.0D, false) * (double)zBlurRange);
            return map.getWaterLatitudes().getIceCoverageForLatitude(adjustedZ) > icebergThreshold;
         }
      }

      return false;
   }

   public void buildSurface(Random rand, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, MiddleEarthSurfaceConfig config) {
      if (this.isFrozenIcebergTerrain(chunk, biome, x, z)) {
         this.frozenOcean.func_205610_a_(rand, chunk, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.field_215425_v);
      } else {
         int chunkX = x & 15;
         int chunkZ = z & 15;
         Mutable movingPos = new Mutable();
         BlockState top = config.getSurfaceNoiseReplacement(x, z, config.func_204108_a(), true, rand);
         BlockState filler = config.getSurfaceNoiseReplacement(x, z, config.func_204109_b(), false, rand);
         BlockState underwater = config.getUnderwaterNoiseReplacement(x, z, config.getUnderwaterMaterial(), rand);
         int digDepth = -1;
         int soilDepth = (int)(noise * 0.25D + config.getFillerDepth() + rand.nextDouble() * 0.25D);
         if (soilDepth < 0) {
            soilDepth = 0;
         }

         int determinedTopTerrainY = -1;
         float pdzRand;
         int y;
         int y;
         if (config.hasRockyTerrain()) {
            int topBlock = startHeight - 1;
            movingPos.func_181079_c(chunkX, topBlock, chunkZ);
            if (topBlock >= 90 && chunk.func_180495_p(movingPos) == defaultBlock) {
               float hFactor = (float)(topBlock - 90) / 10.0F;
               pdzRand = 0.6F - hFactor * 0.1F;
               pdzRand = Math.max(pdzRand, 0.0F);
               double rockyNoise = MiddleEarthSurfaceConfig.getNoise1(x, z, 0.3D, 0.03D);
               if (rockyNoise > (double)pdzRand) {
                  if (rand.nextFloat() < 0.2F) {
                     top = Blocks.field_150351_n.func_176223_P();
                  } else {
                     top = Blocks.field_150348_b.func_176223_P();
                  }

                  y = topBlock;
                  if (rand.nextInt(20) == 0) {
                     ++topBlock;
                  }

                  for(y = topBlock; y >= y; --y) {
                     movingPos.func_181079_c(chunkX, y, chunkZ);
                     chunk.func_177436_a(movingPos, top, false);
                  }
               }
            }
         }

         double randNoise;
         if (config.hasPodzol() && startHeight - 1 <= config.getMaxPodzolHeight() && !config.isMarsh()) {
            float podzolMinThreshold = 0.0F;
            boolean podzolHere = false;
            if (top.func_177230_c() == Blocks.field_196658_i) {
               pdzRand = config.getTreeDensityForPodzol();
               if (pdzRand >= 1.5F) {
                  float threshold = 0.8F;
                  threshold -= pdzRand * 0.15F;
                  threshold = Math.max(threshold, podzolMinThreshold);
                  randNoise = MiddleEarthSurfaceConfig.getNoise2(x, z, 0.05D);
                  if (randNoise > (double)threshold) {
                     podzolHere = true;
                  }
               }
            }

            if (podzolHere) {
               pdzRand = rand.nextFloat();
               if (pdzRand < 0.45F) {
                  top = Blocks.field_196661_l.func_176223_P();
               } else if (pdzRand < 0.6F) {
                  top = Blocks.field_196660_k.func_176223_P();
               } else if (pdzRand < 0.605F) {
                  top = Blocks.field_150351_n.func_176223_P();
               }
            }
         }

         if (config.isMarsh()) {
            double marshNoiseScale1 = 0.25D;
            double marshNoiseScale2 = 0.05D;
            randNoise = (MiddleEarthSurfaceConfig.MARSH_NOISE.func_215464_a((double)x * marshNoiseScale1, (double)z * marshNoiseScale1, false) + MiddleEarthSurfaceConfig.MARSH_NOISE.func_215464_a((double)x * marshNoiseScale2, (double)z * marshNoiseScale2, false)) / 2.0D;
            if (randNoise > -0.1D) {
               for(y = startHeight; y >= 0; --y) {
                  movingPos.func_181079_c(chunkX, y, chunkZ);
                  BlockState currentState = chunk.func_180495_p(movingPos);
                  if (!currentState.func_196958_f()) {
                     if (y == seaLevel - 1 && !currentState.func_185904_a().func_76224_d()) {
                        chunk.func_177436_a(movingPos, Blocks.field_150355_j.func_176223_P(), false);
                     }
                     break;
                  }
               }
            }
         }

         BlockState topToUse = top;
         BlockState fillerToUse = filler;
         Iterator subSoilLayers = config.getSubSoilLayers();

         int y;
         for(y = startHeight; y >= 0; --y) {
            movingPos.func_181079_c(chunkX, y, chunkZ);
            BlockState currentState = chunk.func_180495_p(movingPos);
            if (currentState.func_196958_f()) {
               digDepth = -1;
            } else if (currentState.func_177230_c() == defaultBlock.func_177230_c()) {
               if (digDepth == -1) {
                  if (soilDepth < 0) {
                     topToUse = Blocks.field_150350_a.func_176223_P();
                     fillerToUse = defaultBlock;
                  } else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                     topToUse = top;
                     fillerToUse = filler;
                  }

                  if (y < seaLevel && (topToUse == null || topToUse.func_196958_f())) {
                     if (biome.func_225486_c(movingPos.func_181079_c(x, y, z)) < 0.15F) {
                        topToUse = Blocks.field_150432_aD.func_176223_P();
                     } else {
                        topToUse = defaultFluid;
                     }

                     movingPos.func_181079_c(chunkX, y, chunkZ);
                  }

                  digDepth = soilDepth;
                  if (y >= seaLevel - 1) {
                     chunk.func_177436_a(movingPos, topToUse, false);
                  } else if (y < seaLevel - 7 - soilDepth) {
                     topToUse = Blocks.field_150350_a.func_176223_P();
                     fillerToUse = defaultBlock;
                     chunk.func_177436_a(movingPos, underwater, false);
                  } else {
                     chunk.func_177436_a(movingPos, fillerToUse, false);
                  }

                  if (determinedTopTerrainY == -1) {
                     determinedTopTerrainY = y;
                  }
               } else if (digDepth > 0) {
                  --digDepth;
                  chunk.func_177436_a(movingPos, fillerToUse, false);
               } else if (digDepth == 0) {
                  if (subSoilLayers.hasNext()) {
                     MiddleEarthSurfaceConfig.SubSoilLayer subSoilLayer = (MiddleEarthSurfaceConfig.SubSoilLayer)subSoilLayers.next();
                     digDepth = subSoilLayer.getDepth(rand);
                     fillerToUse = subSoilLayer.getMaterial();
                  } else if (soilDepth > 1) {
                     if (fillerToUse.func_177230_c() == Blocks.field_150354_m) {
                        digDepth = rand.nextInt(4) + Math.max(0, y - seaLevel);
                        fillerToUse = Blocks.field_150322_A.func_176223_P();
                     } else if (fillerToUse.func_177230_c() == Blocks.field_196611_F) {
                        digDepth = rand.nextInt(4) + Math.max(0, y - seaLevel);
                        fillerToUse = Blocks.field_180395_cM.func_176223_P();
                     } else if (fillerToUse.func_177230_c() == LOTRBlocks.WHITE_SAND.get()) {
                        digDepth = rand.nextInt(4) + Math.max(0, y - seaLevel);
                        fillerToUse = ((Block)LOTRBlocks.WHITE_SANDSTONE.get()).func_176223_P();
                     }
                  }

                  if (digDepth > 0) {
                     --digDepth;
                     chunk.func_177436_a(movingPos, fillerToUse, false);
                  }
               }
            }
         }

         if (config.hasMountainTerrain()) {
            y = (int)(noise * 6.0D + 2.0D + rand.nextDouble() * 1.0D);
            boolean passedTopBlock = false;

            for(y = determinedTopTerrainY; y >= 0; --y) {
               movingPos.func_181079_c(chunkX, y, chunkZ);
               BlockState currentState = chunk.func_180495_p(movingPos);
               if (!currentState.func_204520_s().func_206888_e()) {
                  break;
               }

               if (!currentState.func_196958_f()) {
                  boolean isTop = !passedTopBlock;
                  BlockState mountainBlock = config.getMountainTerrain(x, z, y, currentState, defaultBlock, isTop, y);
                  if (mountainBlock != currentState) {
                     chunk.func_177436_a(movingPos, mountainBlock, false);
                  }

                  if (isTop && !passedTopBlock) {
                     passedTopBlock = true;
                  }
               }
            }
         }

      }
   }

   public void func_205548_a(long seed) {
      this.frozenOcean.func_205548_a(seed);
   }
}
