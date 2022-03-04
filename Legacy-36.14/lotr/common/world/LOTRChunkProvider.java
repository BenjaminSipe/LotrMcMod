package lotr.common.world;

import java.util.List;
import java.util.Random;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRMountains;
import lotr.common.world.map.LOTRRoadGenerator;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.mapgen.LOTRMapGenCaves;
import lotr.common.world.mapgen.LOTRMapGenRavine;
import lotr.common.world.mapgen.dwarvenmine.LOTRMapGenDwarvenMine;
import lotr.common.world.mapgen.tpyr.LOTRMapGenTauredainPyramid;
import lotr.common.world.spawning.LOTRSpawnerAnimals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenStructure;

public class LOTRChunkProvider implements IChunkProvider {
   private World worldObj;
   private Random rand;
   private BiomeGenBase[] biomesForGeneration;
   private LOTRBiomeVariant[] variantsForGeneration;
   private static final double COORDINATE_SCALE = 684.412D;
   private static final double HEIGHT_SCALE = 1.0D;
   private static final double MAIN_NOISE_SCALE_XZ = 400.0D;
   private static final double MAIN_NOISE_SCALE_Y = 5000.0D;
   private static final double DEPTH_NOISE_SCALE = 200.0D;
   private static final double DEPTH_NOISE_EXP = 0.5D;
   private static final double HEIGHT_STRETCH = 6.0D;
   private static final double UPPER_LIMIT_SCALE = 512.0D;
   private static final double LOWER_LIMIT_SCALE = 512.0D;
   private int biomeSampleRadius;
   private int biomeSampleWidth;
   private NoiseGeneratorOctaves noiseGen1;
   private NoiseGeneratorOctaves noiseGen2;
   private NoiseGeneratorOctaves noiseGen3;
   private NoiseGeneratorOctaves noiseGen5;
   private NoiseGeneratorOctaves noiseGen6;
   private NoiseGeneratorOctaves stoneNoiseGen;
   private double[] noise1;
   private double[] noise2;
   private double[] noise3;
   private double[] noise5;
   private double[] noise6;
   private double[] stoneNoise = new double[256];
   private double[] heightNoise;
   private float[] biomeHeightNoise;
   private double[] blockHeightNoiseArray;
   private LOTRMapGenCaves caveGenerator = new LOTRMapGenCaves();
   private MapGenBase ravineGenerator = new LOTRMapGenRavine();
   private MapGenStructure dwarvenMineGenerator = new LOTRMapGenDwarvenMine();
   private MapGenStructure tauredainPyramid = new LOTRMapGenTauredainPyramid();
   public static final int seaLevel = 62;

   public LOTRChunkProvider(World world, long seed) {
      this.worldObj = world;
      this.rand = new Random(seed);
      this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
      this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
      this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
      this.stoneNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
      this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
      this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
      this.biomeSampleRadius = 6;
      this.biomeSampleWidth = 2 * this.biomeSampleRadius + 1;
      this.biomeHeightNoise = new float[this.biomeSampleWidth * this.biomeSampleWidth];

      for(int i = -this.biomeSampleRadius; i <= this.biomeSampleRadius; ++i) {
         for(int k = -this.biomeSampleRadius; k <= this.biomeSampleRadius; ++k) {
            float f = 10.0F / MathHelper.func_76129_c((float)(i * i + k * k) + 0.2F);
            this.biomeHeightNoise[i + this.biomeSampleRadius + (k + this.biomeSampleRadius) * this.biomeSampleWidth] = f;
         }
      }

   }

   private void generateTerrain(int i, int j, Block[] blocks, LOTRChunkProvider.ChunkFlags chunkFlags) {
      LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)this.worldObj.func_72959_q();
      byte byte0 = 4;
      byte byte1 = 32;
      int k = byte0 + 1;
      byte byte3 = 33;
      int l = byte0 + 1;
      this.biomesForGeneration = chunkManager.func_76937_a(this.biomesForGeneration, i * byte0 - this.biomeSampleRadius, j * byte0 - this.biomeSampleRadius, k + this.biomeSampleWidth, l + this.biomeSampleWidth);
      this.variantsForGeneration = chunkManager.getVariantsChunkGen(this.variantsForGeneration, i * byte0 - this.biomeSampleRadius, j * byte0 - this.biomeSampleRadius, k + this.biomeSampleWidth, l + this.biomeSampleWidth, this.biomesForGeneration);
      this.heightNoise = this.initializeHeightNoise(this.heightNoise, i * byte0, 0, j * byte0, k, byte3, l, chunkFlags);
      this.blockHeightNoiseArray = new double[blocks.length];

      for(int i1 = 0; i1 < byte0; ++i1) {
         for(int j1 = 0; j1 < byte0; ++j1) {
            for(int k1 = 0; k1 < byte1; ++k1) {
               double d = 0.125D;
               double d1 = this.heightNoise[((i1 + 0) * l + j1 + 0) * byte3 + k1 + 0];
               double d2 = this.heightNoise[((i1 + 0) * l + j1 + 1) * byte3 + k1 + 0];
               double d3 = this.heightNoise[((i1 + 1) * l + j1 + 0) * byte3 + k1 + 0];
               double d4 = this.heightNoise[((i1 + 1) * l + j1 + 1) * byte3 + k1 + 0];
               double d5 = (this.heightNoise[((i1 + 0) * l + j1 + 0) * byte3 + k1 + 1] - d1) * d;
               double d6 = (this.heightNoise[((i1 + 0) * l + j1 + 1) * byte3 + k1 + 1] - d2) * d;
               double d7 = (this.heightNoise[((i1 + 1) * l + j1 + 0) * byte3 + k1 + 1] - d3) * d;
               double d8 = (this.heightNoise[((i1 + 1) * l + j1 + 1) * byte3 + k1 + 1] - d4) * d;

               for(int l1 = 0; l1 < 8; ++l1) {
                  double d9 = 0.25D;
                  double d10 = d1;
                  double d11 = d2;
                  double d12 = (d3 - d1) * d9;
                  double d13 = (d4 - d2) * d9;

                  for(int i2 = 0; i2 < 4; ++i2) {
                     int j2 = i2 + i1 * 4 << 12 | 0 + j1 * 4 << 8 | k1 * 8 + l1;
                     double d14 = 0.25D;
                     double d15 = (d11 - d10) * d14;

                     for(int k2 = 0; k2 < 4; ++k2) {
                        int blockIndex = j2 + k2 * 256;
                        double blockHeightNoise = d10 + d15 * (double)k2;
                        this.blockHeightNoiseArray[blockIndex] = blockHeightNoise;
                        if (blockHeightNoise > 0.0D) {
                           blocks[blockIndex] = Blocks.field_150348_b;
                        } else if (k1 * 8 + l1 <= 62) {
                           blocks[blockIndex] = Blocks.field_150355_j;
                        } else {
                           blocks[blockIndex] = Blocks.field_150350_a;
                        }
                     }

                     d10 += d12;
                     d11 += d13;
                  }

                  d1 += d5;
                  d2 += d6;
                  d3 += d7;
                  d4 += d8;
               }
            }
         }
      }

   }

   private void replaceBlocksForBiome(int i, int k, Block[] blocks, byte[] metadata, BiomeGenBase[] biomeArray, LOTRBiomeVariant[] variantArray, LOTRChunkProvider.ChunkFlags chunkFlags) {
      double d = 0.03125D;
      this.stoneNoise = this.stoneNoiseGen.func_76304_a(this.stoneNoise, i * 16, k * 16, 0, 16, 16, 1, d * 2.0D, d * 2.0D, d * 2.0D);
      int ySize = blocks.length / 256;

      for(int i1 = 0; i1 < 16; ++i1) {
         for(int k1 = 0; k1 < 16; ++k1) {
            int x = i * 16 + i1;
            int z = k * 16 + k1;
            int xzIndex = i1 * 16 + k1;
            int xzIndexBiome = i1 + k1 * 16;
            LOTRBiome biome = (LOTRBiome)biomeArray[xzIndexBiome];
            LOTRBiomeVariant variant = variantArray[xzIndexBiome];
            int height = 0;

            int lavaHeight;
            for(int j = ySize - 1; j >= 0; --j) {
               lavaHeight = xzIndex * ySize + j;
               Block block = blocks[lavaHeight];
               if (block.func_149662_c()) {
                  height = j;
                  break;
               }
            }

            biome.generateBiomeTerrain(this.worldObj, this.rand, blocks, metadata, x, z, this.stoneNoise[xzIndex], height, variant);
            if (LOTRFixedStructures.hasMapFeatures(this.worldObj)) {
               boolean road = LOTRRoadGenerator.generateRoad(this.worldObj, this.rand, x, z, biome, blocks, metadata, this.blockHeightNoiseArray);
               chunkFlags.roadFlags[xzIndex] = road;
               lavaHeight = LOTRMountains.getLavaHeight(x, z);
               if (lavaHeight > 0) {
                  for(int j = lavaHeight; j >= 0; --j) {
                     int index = xzIndex * ySize + j;
                     Block block = blocks[index];
                     if (block.func_149662_c()) {
                        break;
                     }

                     blocks[index] = Blocks.field_150353_l;
                     metadata[index] = 0;
                  }
               }
            }
         }
      }

   }

   public Chunk func_73158_c(int i, int k) {
      return this.func_73154_d(i, k);
   }

   public Chunk func_73154_d(int i, int k) {
      this.rand.setSeed((long)i * 341873128712L + (long)k * 132897987541L);
      LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)this.worldObj.func_72959_q();
      Block[] blocks = new Block[65536];
      byte[] metadata = new byte[65536];
      LOTRChunkProvider.ChunkFlags chunkFlags = new LOTRChunkProvider.ChunkFlags();
      this.generateTerrain(i, k, blocks, chunkFlags);
      this.biomesForGeneration = chunkManager.func_76933_b(this.biomesForGeneration, i * 16, k * 16, 16, 16);
      this.variantsForGeneration = chunkManager.getBiomeVariants(this.variantsForGeneration, i * 16, k * 16, 16, 16);
      this.replaceBlocksForBiome(i, k, blocks, metadata, this.biomesForGeneration, this.variantsForGeneration, chunkFlags);
      this.caveGenerator.chunkFlags = chunkFlags;
      this.caveGenerator.func_151539_a(this, this.worldObj, i, k, blocks);
      this.ravineGenerator.func_151539_a(this, this.worldObj, i, k, blocks);
      this.dwarvenMineGenerator.func_151539_a(this, this.worldObj, i, k, blocks);
      this.tauredainPyramid.func_151539_a(this, this.worldObj, i, k, blocks);
      Chunk chunk = new Chunk(this.worldObj, i, k);
      ExtendedBlockStorage[] blockStorage = chunk.func_76587_i();

      int k1;
      int j1;
      for(int i1 = 0; i1 < 16; ++i1) {
         for(k1 = 0; k1 < 16; ++k1) {
            for(j1 = 0; j1 < 256; ++j1) {
               int blockIndex = i1 << 12 | k1 << 8 | j1;
               Block block = blocks[blockIndex];
               if (block != null && block != Blocks.field_150350_a) {
                  byte meta = metadata[blockIndex];
                  int j2 = j1 >> 4;
                  if (blockStorage[j2] == null) {
                     blockStorage[j2] = new ExtendedBlockStorage(j2 << 4, true);
                  }

                  blockStorage[j2].func_150818_a(i1, j1 & 15, k1, block);
                  blockStorage[j2].func_76654_b(i1, j1 & 15, k1, meta & 15);
               }
            }
         }
      }

      byte[] biomes = chunk.func_76605_m();

      for(k1 = 0; k1 < biomes.length; ++k1) {
         biomes[k1] = (byte)this.biomesForGeneration[k1].field_76756_M;
      }

      byte[] variants = new byte[256];

      for(j1 = 0; j1 < variants.length; ++j1) {
         variants[j1] = (byte)this.variantsForGeneration[j1].variantID;
      }

      LOTRBiomeVariantStorage.setChunkBiomeVariants(this.worldObj, chunk, variants);
      chunk.func_76603_b();
      LOTRFixedStructures.nanoTimeElapsed = 0L;
      return chunk;
   }

   private double[] initializeHeightNoise(double[] noise, int i, int j, int k, int xSize, int ySize, int zSize, LOTRChunkProvider.ChunkFlags chunkFlags) {
      if (noise == null) {
         noise = new double[xSize * ySize * zSize];
      }

      double xzNoiseScale = 400.0D;
      double heightStretch = 6.0D;
      int noiseCentralIndex = (xSize - 1) / 2 + this.biomeSampleRadius + ((zSize - 1) / 2 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
      LOTRBiome noiseCentralBiome = (LOTRBiome)this.biomesForGeneration[noiseCentralIndex];
      if (noiseCentralBiome.biomeTerrain.hasXZScale()) {
         xzNoiseScale = noiseCentralBiome.biomeTerrain.getXZScale();
      }

      if (noiseCentralBiome.biomeTerrain.hasHeightStretchFactor()) {
         heightStretch *= noiseCentralBiome.biomeTerrain.getHeightStretchFactor();
      }

      this.noise5 = this.noiseGen5.func_76305_a(this.noise5, i, k, xSize, zSize, 1.121D, 1.121D, 0.5D);
      this.noise6 = this.noiseGen6.func_76305_a(this.noise6, i, k, xSize, zSize, 200.0D, 200.0D, 0.5D);
      this.noise3 = this.noiseGen3.func_76304_a(this.noise3, i, j, k, xSize, ySize, zSize, 684.412D / xzNoiseScale, 2.0E-4D, 684.412D / xzNoiseScale);
      this.noise1 = this.noiseGen1.func_76304_a(this.noise1, i, j, k, xSize, ySize, zSize, 684.412D, 1.0D, 684.412D);
      this.noise2 = this.noiseGen2.func_76304_a(this.noise2, i, j, k, xSize, ySize, zSize, 684.412D, 1.0D, 684.412D);
      int noiseIndexXZ = 0;
      int noiseIndex = 0;

      for(int i1 = 0; i1 < xSize; ++i1) {
         for(int k1 = 0; k1 < zSize; ++k1) {
            int xPos = i + i1 << 2;
            int zPos = k + k1 << 2;
            xPos += 2;
            zPos += 2;
            float totalBaseHeight = 0.0F;
            float totalHeightVariation = 0.0F;
            float totalHeightNoise = 0.0F;
            float totalVariantHillFactor = 0.0F;
            float totalFlatBiomeHeight = 0.0F;
            int biomeCount = 0;
            int centreBiomeIndex = i1 + this.biomeSampleRadius + (k1 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
            BiomeGenBase centreBiome = this.biomesForGeneration[centreBiomeIndex];
            LOTRBiomeVariant centreVariant = this.variantsForGeneration[centreBiomeIndex];
            float centreHeight = centreBiome.field_76748_D + centreVariant.getHeightBoostAt(xPos, zPos);
            if (centreVariant.absoluteHeight) {
               centreHeight = centreVariant.getHeightBoostAt(xPos, zPos);
            }

            int j1;
            float baseHeight;
            for(int i2 = -this.biomeSampleRadius; i2 <= this.biomeSampleRadius; ++i2) {
               for(int k2 = -this.biomeSampleRadius; k2 <= this.biomeSampleRadius; ++k2) {
                  int biomeIndex = i1 + i2 + this.biomeSampleRadius + (k1 + k2 + this.biomeSampleRadius) * (xSize + this.biomeSampleWidth);
                  BiomeGenBase biome = this.biomesForGeneration[biomeIndex];
                  LOTRBiomeVariant variant = this.variantsForGeneration[biomeIndex];
                  int xPosHere = xPos + (i2 << 2);
                  j1 = zPos + (k2 << 2);
                  baseHeight = biome.field_76748_D + variant.getHeightBoostAt(xPosHere, j1);
                  float heightVariation = biome.field_76749_E * variant.hillFactor;
                  if (variant.absoluteHeight) {
                     baseHeight = variant.getHeightBoostAt(xPosHere, j1);
                     heightVariation = variant.hillFactor;
                  }

                  float hillFactor = variant.hillFactor;
                  float baseHeightPlus = baseHeight + 2.0F;
                  if (baseHeightPlus == 0.0F) {
                     baseHeightPlus = 0.001F;
                  }

                  float heightNoise = this.biomeHeightNoise[i2 + this.biomeSampleRadius + (k2 + this.biomeSampleRadius) * this.biomeSampleWidth] / baseHeightPlus / 2.0F;
                  heightNoise = Math.abs(heightNoise);
                  if (baseHeight > centreHeight) {
                     heightNoise /= 2.0F;
                  }

                  totalBaseHeight += baseHeight * heightNoise;
                  totalHeightVariation += heightVariation * heightNoise;
                  totalHeightNoise += heightNoise;
                  totalVariantHillFactor += hillFactor;
                  float flatBiomeHeight = biome.field_76748_D;
                  boolean isWater = ((LOTRBiome)biome).isWateryBiome();
                  if (variant.absoluteHeight && variant.absoluteHeightLevel < 0.0F) {
                     isWater = true;
                  }

                  if (isWater) {
                     flatBiomeHeight = baseHeight;
                  }

                  totalFlatBiomeHeight += flatBiomeHeight;
                  ++biomeCount;
               }
            }

            float avgBaseHeight = totalBaseHeight / totalHeightNoise;
            float avgHeightVariation = totalHeightVariation / totalHeightNoise;
            float avgFlatBiomeHeight = totalFlatBiomeHeight / (float)biomeCount;
            float avgVariantHillFactor = totalVariantHillFactor / (float)biomeCount;
            if (LOTRFixedStructures.hasMapFeatures(this.worldObj)) {
               float roadNear = LOTRRoads.isRoadNear(xPos, zPos, 32);
               if (roadNear >= 0.0F) {
                  avgBaseHeight = avgFlatBiomeHeight + (avgBaseHeight - avgFlatBiomeHeight) * roadNear;
                  avgHeightVariation *= roadNear;
               }

               float mountain = LOTRMountains.getTotalHeightBoost(xPos, zPos);
               if (mountain > 0.005F) {
                  avgBaseHeight += mountain;
                  float mtnV = 0.2F;
                  baseHeight = avgHeightVariation - mtnV;
                  avgHeightVariation = mtnV + baseHeight / (1.0F + mountain);
               }
            }

            if (centreBiome instanceof LOTRBiome) {
               LOTRBiome lb = (LOTRBiome)centreBiome;
               lb.decorator.checkForVillages(this.worldObj, xPos, zPos, chunkFlags);
               if (chunkFlags.isFlatVillage) {
                  avgBaseHeight = avgFlatBiomeHeight;
                  avgHeightVariation = 0.0F;
               }
            }

            avgBaseHeight = (avgBaseHeight * 4.0F - 1.0F) / 8.0F;
            if (avgHeightVariation == 0.0F) {
               avgHeightVariation = 0.001F;
            }

            double heightNoise = this.noise6[noiseIndexXZ] / 8000.0D;
            if (heightNoise < 0.0D) {
               heightNoise = -heightNoise * 0.3D;
            }

            heightNoise = heightNoise * 3.0D - 2.0D;
            if (heightNoise < 0.0D) {
               heightNoise /= 2.0D;
               if (heightNoise < -1.0D) {
                  heightNoise = -1.0D;
               }

               heightNoise /= 1.4D;
               heightNoise /= 2.0D;
            } else {
               if (heightNoise > 1.0D) {
                  heightNoise = 1.0D;
               }

               heightNoise /= 8.0D;
            }

            ++noiseIndexXZ;

            for(j1 = 0; j1 < ySize; ++j1) {
               double baseHeight = (double)avgBaseHeight;
               double heightVariation = (double)avgHeightVariation;
               baseHeight += heightNoise * 0.2D * (double)avgVariantHillFactor;
               baseHeight = baseHeight * (double)ySize / 16.0D;
               double var28 = (double)ySize / 2.0D + baseHeight * 4.0D;
               double totalNoise = 0.0D;
               double var32 = ((double)j1 - var28) * heightStretch * 128.0D / 256.0D / heightVariation;
               if (var32 < 0.0D) {
                  var32 *= 4.0D;
               }

               double var34 = this.noise1[noiseIndex] / 512.0D;
               double var36 = this.noise2[noiseIndex] / 512.0D;
               double var38 = (this.noise3[noiseIndex] / 10.0D + 1.0D) / 2.0D * (double)avgVariantHillFactor;
               if (var38 < 0.0D) {
                  totalNoise = var34;
               } else if (var38 > 1.0D) {
                  totalNoise = var36;
               } else {
                  totalNoise = var34 + (var36 - var34) * var38;
               }

               totalNoise -= var32;
               if (j1 > ySize - 4) {
                  double var40 = (double)((float)(j1 - (ySize - 4)) / 3.0F);
                  totalNoise = totalNoise * (1.0D - var40) + -10.0D * var40;
               }

               noise[noiseIndex] = totalNoise;
               ++noiseIndex;
            }
         }
      }

      return noise;
   }

   public boolean func_73149_a(int i, int j) {
      return true;
   }

   public void func_73153_a(IChunkProvider ichunkprovider, int i, int j) {
      BlockSand.field_149832_M = true;
      int k = i * 16;
      int l = j * 16;
      LOTRBiome biome = (LOTRBiome)this.worldObj.func_72807_a(k + 16, l + 16);
      LOTRBiomeVariant variant = ((LOTRWorldChunkManager)this.worldObj.func_72959_q()).getBiomeVariantAt(k + 16, l + 16);
      this.rand.setSeed(this.worldObj.func_72905_C());
      long l1 = this.rand.nextLong() / 2L * 2L + 1L;
      long l2 = this.rand.nextLong() / 2L * 2L + 1L;
      this.rand.setSeed((long)i * l1 + (long)j * l2 ^ this.worldObj.func_72905_C());
      this.dwarvenMineGenerator.func_75051_a(this.worldObj, this.rand, i, j);
      this.tauredainPyramid.func_75051_a(this.worldObj, this.rand, i, j);
      int i1;
      int k1;
      int j1;
      if (this.rand.nextInt(4) == 0) {
         i1 = k + this.rand.nextInt(16) + 8;
         k1 = this.rand.nextInt(128);
         j1 = l + this.rand.nextInt(16) + 8;
         if (k1 < 60) {
            (new WorldGenLakes(Blocks.field_150355_j)).func_76484_a(this.worldObj, this.rand, i1, k1, j1);
         }
      }

      if (this.rand.nextInt(8) == 0) {
         i1 = k + this.rand.nextInt(16) + 8;
         k1 = this.rand.nextInt(this.rand.nextInt(120) + 8);
         j1 = l + this.rand.nextInt(16) + 8;
         if (k1 < 60) {
            (new WorldGenLakes(Blocks.field_150353_l)).func_76484_a(this.worldObj, this.rand, i1, k1, j1);
         }
      }

      biome.func_76728_a(this.worldObj, this.rand, k, l);
      if (biome.getChanceToSpawnAnimals() <= 1.0F) {
         if (this.rand.nextFloat() < biome.getChanceToSpawnAnimals()) {
            LOTRSpawnerAnimals.worldGenSpawnAnimals(this.worldObj, biome, variant, k + 8, l + 8, this.rand);
         }
      } else {
         i1 = MathHelper.func_76128_c((double)biome.getChanceToSpawnAnimals());

         for(k1 = 0; k1 < i1; ++k1) {
            LOTRSpawnerAnimals.worldGenSpawnAnimals(this.worldObj, biome, variant, k + 8, l + 8, this.rand);
         }
      }

      k += 8;
      l += 8;

      for(i1 = 0; i1 < 16; ++i1) {
         for(k1 = 0; k1 < 16; ++k1) {
            j1 = this.worldObj.func_72874_g(k + i1, l + k1);
            if (this.worldObj.func_72884_u(i1 + k, j1 - 1, k1 + l)) {
               this.worldObj.func_147465_d(i1 + k, j1 - 1, k1 + l, Blocks.field_150432_aD, 0, 2);
            }

            if (this.worldObj.func_147478_e(i1 + k, j1, k1 + l, true)) {
               this.worldObj.func_147465_d(i1 + k, j1, k1 + l, Blocks.field_150431_aC, 0, 2);
            }
         }
      }

      BlockSand.field_149832_M = false;
   }

   public boolean func_73151_a(boolean flag, IProgressUpdate update) {
      return true;
   }

   public void func_104112_b() {
   }

   public boolean func_73156_b() {
      return false;
   }

   public boolean func_73157_c() {
      return true;
   }

   public String func_73148_d() {
      return "MiddleEarthLevelSource";
   }

   public List func_73155_a(EnumCreatureType creatureType, int i, int j, int k) {
      BiomeGenBase biome = this.worldObj.func_72807_a(i, k);
      return biome == null ? null : biome.func_76747_a(creatureType);
   }

   public ChunkPosition func_147416_a(World world, String type, int i, int j, int k) {
      return null;
   }

   public int func_73152_e() {
      return 0;
   }

   public void func_82695_e(int i, int k) {
      this.dwarvenMineGenerator.func_151539_a(this, this.worldObj, i, k, (Block[])null);
      this.tauredainPyramid.func_151539_a(this, this.worldObj, i, k, (Block[])null);
   }

   public static class ChunkFlags {
      public boolean isVillage;
      public boolean isFlatVillage;
      public boolean[] roadFlags;

      private ChunkFlags() {
         this.isVillage = false;
         this.isFlatVillage = false;
         this.roadFlags = new boolean[256];
      }

      // $FF: synthetic method
      ChunkFlags(Object x0) {
         this();
      }
   }
}
