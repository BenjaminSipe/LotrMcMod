package lotr.common.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.world.biome.LOTRBiomeWrapper;
import lotr.common.world.biome.provider.MiddleEarthBiomeProvider;
import lotr.common.world.map.MapSettingsManager;
import lotr.common.world.map.RoadGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MiddleEarthChunkGenerator extends ChunkGenerator {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BiomeProvider.field_235202_a_.fieldOf("biome_source").forGetter((chunkGen) -> {
         return chunkGen.field_222542_c;
      }), Codec.LONG.fieldOf("seed").stable().forGetter((chunkGen) -> {
         return chunkGen.seed;
      }), DimensionSettings.field_236098_b_.fieldOf("settings").forGetter((chunkGen) -> {
         return chunkGen.dimensionSettings;
      }), Codec.BOOL.optionalFieldOf("instant_middle_earth").forGetter((chunkGen) -> {
         return chunkGen.isInstantMiddleEarth;
      })).apply(instance, instance.stable(MiddleEarthChunkGenerator::new));
   });
   private static final int CUBIC_SAMPLE_SIZE = 24;
   private static final float[] CUBIC_SAMPLE = (float[])Util.func_200696_a(new float[13824], (array) -> {
      for(int i = 0; i < 24; ++i) {
         for(int j = 0; j < 24; ++j) {
            for(int k = 0; k < 24; ++k) {
               array[i * 576 + j * 24 + k] = (float)func_222554_b(j - 12, k - 12, i - 12);
            }
         }
      }

   });
   private static final int BIOME_SAMPLE_RADIUS = 6;
   private static final int BIOME_SAMPLE_WIDTH = 13;
   private static final float[] BIOME_SAMPLING_SIGNIFICANCE = (float[])Util.func_200696_a(new float[169], (array) -> {
      for(int z = -6; z <= 6; ++z) {
         for(int x = -6; x <= 6; ++x) {
            float f = 10.0F / MathHelper.func_76129_c((float)(z * z + x * x) + 0.2F);
            array[z + 6 + (x + 6) * 13] = f;
         }
      }

   });
   private static final BlockState AIR;
   private final BlockState defaultBlock;
   private final BlockState defaultFluid;
   private final int verticalNoiseGranularity;
   private final int horizontalNoiseGranularity;
   private final int noiseSizeX;
   private final int noiseSizeY;
   private final int noiseSizeZ;
   private final SharedSeedRandom randomSeed;
   private final OctavesNoiseGenerator field_222568_o;
   private final OctavesNoiseGenerator field_222569_p;
   private final OctavesNoiseGenerator field_222570_q;
   private final INoiseGenerator surfaceDepthNoise;
   private final OctavesNoiseGenerator depthNoise;
   private final long seed;
   private final Supplier dimensionSettings;
   private final boolean isAmplified;
   private final int chunkGenHeight;
   private final RoadGenerator roadGenerator;
   private static final int ROAD_TERRAIN_FLATTEN_RANGE = 64;
   private static final float ROAD_TERRAIN_FLATTEN_FACTOR = 0.85F;
   private Optional isInstantMiddleEarth;

   public MiddleEarthChunkGenerator(BiomeProvider biomeProvider, long seed, Supplier dimSettings, Optional isInstantMiddleEarth) {
      this(biomeProvider, biomeProvider, seed, dimSettings, isInstantMiddleEarth);
   }

   private MiddleEarthChunkGenerator(BiomeProvider biomeProvider, BiomeProvider secondBreakfastBiomeProvider, long seed, Supplier dimSettings, Optional isInstantMiddleEarth) {
      super(biomeProvider, secondBreakfastBiomeProvider, ((DimensionSettings)dimSettings.get()).func_236108_a_(), seed);
      this.roadGenerator = new RoadGenerator();
      this.seed = seed;
      DimensionSettings dimensionSettings = (DimensionSettings)dimSettings.get();
      this.dimensionSettings = dimSettings;
      NoiseSettings noiseSettings = dimensionSettings.func_236113_b_();
      this.chunkGenHeight = noiseSettings.func_236169_a_();
      this.verticalNoiseGranularity = noiseSettings.func_236175_f_() * 4;
      this.horizontalNoiseGranularity = noiseSettings.func_236174_e_() * 4;
      this.defaultBlock = dimensionSettings.func_236115_c_();
      this.defaultFluid = dimensionSettings.func_236116_d_();
      this.noiseSizeX = 16 / this.horizontalNoiseGranularity;
      this.noiseSizeY = noiseSettings.func_236169_a_() / this.verticalNoiseGranularity;
      this.noiseSizeZ = 16 / this.horizontalNoiseGranularity;
      this.randomSeed = new SharedSeedRandom(seed);
      this.field_222568_o = new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-15, 0));
      this.field_222569_p = new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-15, 0));
      this.field_222570_q = new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-7, 0));
      this.surfaceDepthNoise = (INoiseGenerator)(noiseSettings.func_236178_i_() ? new PerlinNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-3, 0)) : new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-3, 0)));
      this.randomSeed.func_202423_a(2620);
      this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-15, 0));
      this.isAmplified = noiseSettings.func_236181_l_();
      this.isInstantMiddleEarth = isInstantMiddleEarth;
   }

   public void hackySetWorldTypeInstantMiddleEarth(boolean flag) {
      if (this.isInstantMiddleEarth.isPresent()) {
         throw new IllegalStateException("Already set isInstantMiddleEarth to " + this.isInstantMiddleEarth.get() + "!");
      } else {
         this.isInstantMiddleEarth = Optional.of(flag);
      }
   }

   public void hackySetWorldTypeClassicBiomes(boolean flag) {
      if (this.field_222542_c instanceof MiddleEarthBiomeProvider) {
         ((MiddleEarthBiomeProvider)this.field_222542_c).hackySetWorldTypeClassicBiomes(flag);
      }

   }

   public boolean isInstantMiddleEarth() {
      return (Boolean)this.isInstantMiddleEarth.orElse(false);
   }

   public boolean isClassicBiomes() {
      return this.field_222542_c instanceof MiddleEarthBiomeProvider && ((MiddleEarthBiomeProvider)this.field_222542_c).isClassicBiomes();
   }

   protected Codec func_230347_a_() {
      return CODEC;
   }

   @OnlyIn(Dist.CLIENT)
   public ChunkGenerator func_230349_a_(long otherSeed) {
      return new MiddleEarthChunkGenerator(this.field_222542_c.func_230320_a_(otherSeed), otherSeed, this.dimensionSettings, Optional.of(this.isInstantMiddleEarth()));
   }

   private double[] func_222547_b(int x, int z) {
      double[] noise = new double[this.noiseSizeY + 1];
      this.fillNoiseColumn(noise, x, z);
      return noise;
   }

   private void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ) {
      double coordScaleXZ = 484.412D;
      double coordScaleY = 1.0D;
      Biome biome = this.field_222542_c.func_225526_b_(noiseX, this.func_230356_f_(), noiseZ);
      double xzNoiseScale = LOTRBiomes.getWrapperFor(biome, LOTRBiomes.getServerBiomeContextWorld()).getHorizontalNoiseScale();
      double yNoiseScale = 5000.0D;
      double scaledNoiseXZ = coordScaleXZ / xzNoiseScale;
      double scaledNoiseY = coordScaleY / yNoiseScale;
      int i = -10;
      int j = 3;
      this.calcNoiseColumn(noiseColumn, noiseX, noiseZ, coordScaleXZ, coordScaleY, scaledNoiseXZ, scaledNoiseY, i, j);
   }

   protected void calcNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ, double p_222546_4_, double p_222546_6_, double p_222546_8_, double p_222546_10_, int p_222546_12_, int p_222546_13_) {
      double[] depthAndScale = this.getBiomeNoiseColumn(noiseX, noiseZ);
      double avgDepth = depthAndScale[0];
      double avgScale = depthAndScale[1];
      double d2 = (double)(this.noiseSizeY - 4);
      double d3 = 0.0D;

      for(int i = 0; i < this.noiseSizeY; ++i) {
         double d4 = this.func_222552_a(noiseX, i, noiseZ, p_222546_4_, p_222546_6_, p_222546_8_, p_222546_10_);
         d4 -= this.func_222545_a(avgDepth, avgScale, i);
         if ((double)i > d2) {
            d4 = MathHelper.func_151238_b(d4, (double)p_222546_13_, ((double)i - d2) / (double)p_222546_12_);
         } else if ((double)i < d3) {
            d4 = MathHelper.func_151238_b(d4, -30.0D, (d3 - (double)i) / (d3 - 1.0D));
         }

         noiseColumn[i] = d4;
      }

   }

   private double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {
      int blockX = noiseX * this.noiseSizeX + this.noiseSizeX / 2;
      int blockZ = noiseZ * this.noiseSizeZ + this.noiseSizeZ / 2;
      int seaLevel = this.func_230356_f_();
      double[] depthAndScale = new double[2];
      float totalScale = 0.0F;
      float totalDepth = 0.0F;
      float totalDepthWithoutVariants = 0.0F;
      float totalAddedDepthNoiseStrength = 0.0F;
      float totalModifiedSignificance = 0.0F;
      float totalSignificance = 0.0F;
      float centralDepth = this.field_222542_c.func_225526_b_(noiseX, seaLevel, noiseZ).func_185355_j();
      ServerWorld world = LOTRBiomes.getServerBiomeContextWorld();

      float depth;
      float addedDepthNoiseStrength;
      for(int k = -6; k <= 6; ++k) {
         for(int l = -6; l <= 6; ++l) {
            Biome biome = this.field_222542_c.func_225526_b_(noiseX + k, seaLevel, noiseZ + l);
            depth = biome.func_185355_j();
            LOTRBiomeWrapper biomeWrapper = LOTRBiomes.getWrapperFor(biome, world);
            addedDepthNoiseStrength = biomeWrapper.getStrengthOfAddedDepthNoise();
            float scaleSignificance = biomeWrapper.getBiomeScaleSignificanceForChunkGen();
            float scale = biome.func_185360_m() * scaleSignificance + (1.0F - scaleSignificance);
            if (scale == 0.0F) {
               scale = 1.0E-7F;
            }

            if (this.isAmplified && depth > 0.0F) {
               depth = 1.0F + depth * 2.0F;
               scale = 1.0F + scale * 4.0F;
            }

            int arrayIndex = k + 6 + (l + 6) * 13;
            float significance = BIOME_SAMPLING_SIGNIFICANCE[arrayIndex];
            float modifiedSignificance = significance / (depth + 2.0F);
            if (biome.func_185355_j() > centralDepth) {
               modifiedSignificance /= 2.0F;
            }

            if (biomeWrapper.isRiver()) {
               float affectingFactor = 5.0F;
               if (centralDepth < 0.0F) {
                  affectingFactor *= 2.0F;
               }

               modifiedSignificance *= affectingFactor;
            }

            totalScale += scale * modifiedSignificance;
            totalDepth += depth * modifiedSignificance;
            totalDepthWithoutVariants += depth * modifiedSignificance;
            totalModifiedSignificance += modifiedSignificance;
            totalAddedDepthNoiseStrength += addedDepthNoiseStrength * significance;
            totalSignificance += significance;
         }
      }

      float avgScale = totalScale / totalModifiedSignificance;
      float avgDepth = totalDepth / totalModifiedSignificance;
      float avgDepthWithoutVariants = totalDepthWithoutVariants / totalModifiedSignificance;
      depth = totalAddedDepthNoiseStrength / totalSignificance;
      float lerpFactor;
      if (LOTRWorldTypes.hasMapFeatures(world)) {
         lerpFactor = MapSettingsManager.serverInstance().getCurrentLoadedMap().getRoadPointCache().getRoadCentreCloseness(blockX, blockZ, 64);
         if (lerpFactor >= 0.0F) {
            addedDepthNoiseStrength = Math.min(lerpFactor + 0.15F, 1.0F) * 0.85F;
            avgDepth += (avgDepthWithoutVariants - avgDepth) * addedDepthNoiseStrength;
            avgScale *= 1.0F - addedDepthNoiseStrength;
         }
      }

      if (centralDepth < 0.0F && avgDepth >= 0.0F) {
         lerpFactor = 0.5F;
         avgDepth = MathHelper.func_219799_g(lerpFactor, avgDepth, centralDepth / 2.0F);
      }

      double depthNoiseAdd = ((DimensionSettings)this.dimensionSettings.get()).func_236113_b_().func_236179_j_() ? this.getNoiseDepthAt(noiseX, noiseZ) : 0.0D;
      depthNoiseAdd = (depthNoiseAdd * 8.0D + 1.0D) / 4.0D;
      avgDepth = (float)((double)avgDepth + depthNoiseAdd * (double)depth);
      avgDepth = (avgDepth * 4.0F - 1.0F) / 8.0F;
      depthAndScale[0] = (double)avgDepth;
      depthAndScale[1] = (double)avgScale;
      return depthAndScale;
   }

   private double func_222545_a(double baseHeight, double heightVariation, int yIndex) {
      double heightStretch = 12.0D;
      double d0 = 8.5D;
      double d1 = ((double)yIndex - (8.5D + baseHeight * 8.5D / 8.0D * 4.0D)) * heightStretch * 128.0D / 256.0D / heightVariation;
      if (d1 < 0.0D) {
         d1 *= 4.0D;
      }

      return d1;
   }

   private double getNoiseDepthAt(int noiseX, int noiseZ) {
      double d0 = this.depthNoise.func_215462_a((double)(noiseX * 200), 10.0D, (double)(noiseZ * 200), 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
      double d1;
      if (d0 < 0.0D) {
         d1 = -d0 * 0.3D;
      } else {
         d1 = d0;
      }

      double d2 = d1 * 3.0D - 2.0D;
      return d2 < 0.0D ? d2 / 28.0D : Math.min(d2, 1.0D) / 40.0D;
   }

   private double func_222552_a(int p_222552_1_, int p_222552_2_, int p_222552_3_, double p_222552_4_, double p_222552_6_, double p_222552_8_, double p_222552_10_) {
      double d0 = 0.0D;
      double d1 = 0.0D;
      double d2 = 0.0D;
      boolean flag = true;
      double d3 = 1.0D;

      for(int i = 0; i < 16; ++i) {
         double d4 = OctavesNoiseGenerator.func_215461_a((double)p_222552_1_ * p_222552_4_ * d3);
         double d5 = OctavesNoiseGenerator.func_215461_a((double)p_222552_2_ * p_222552_6_ * d3);
         double d6 = OctavesNoiseGenerator.func_215461_a((double)p_222552_3_ * p_222552_4_ * d3);
         double d7 = p_222552_6_ * d3;
         ImprovedNoiseGenerator improvednoisegenerator = this.field_222568_o.func_215463_a(i);
         if (improvednoisegenerator != null) {
            d0 += improvednoisegenerator.func_215456_a(d4, d5, d6, d7, (double)p_222552_2_ * d7) / d3;
         }

         ImprovedNoiseGenerator improvednoisegenerator1 = this.field_222569_p.func_215463_a(i);
         if (improvednoisegenerator1 != null) {
            d1 += improvednoisegenerator1.func_215456_a(d4, d5, d6, d7, (double)p_222552_2_ * d7) / d3;
         }

         if (i < 8) {
            ImprovedNoiseGenerator improvednoisegenerator2 = this.field_222570_q.func_215463_a(i);
            if (improvednoisegenerator2 != null) {
               d2 += improvednoisegenerator2.func_215456_a(OctavesNoiseGenerator.func_215461_a((double)p_222552_1_ * p_222552_8_ * d3), OctavesNoiseGenerator.func_215461_a((double)p_222552_2_ * p_222552_10_ * d3), OctavesNoiseGenerator.func_215461_a((double)p_222552_3_ * p_222552_8_ * d3), p_222552_10_ * d3, (double)p_222552_2_ * p_222552_10_ * d3) / d3;
            }
         }

         d3 /= 2.0D;
      }

      return MathHelper.func_151238_b(d0 / 512.0D, d1 / 512.0D, (d2 / 10.0D + 1.0D) / 2.0D);
   }

   public int func_222529_a(int x, int z, Type heightmapType) {
      return this.makeBasicTerrain(x, z, (BlockState[])null, heightmapType.func_222684_d());
   }

   public IBlockReader func_230348_a_(int x, int z) {
      BlockState[] states = new BlockState[this.noiseSizeY * this.verticalNoiseGranularity];
      this.makeBasicTerrain(x, z, states, (Predicate)null);
      return new Blockreader(states);
   }

   private int makeBasicTerrain(int x, int z, @Nullable BlockState[] stateArray, @Nullable Predicate heightmapTest) {
      int i = Math.floorDiv(x, this.horizontalNoiseGranularity);
      int j = Math.floorDiv(z, this.horizontalNoiseGranularity);
      int k = Math.floorMod(x, this.horizontalNoiseGranularity);
      int l = Math.floorMod(z, this.horizontalNoiseGranularity);
      double d0 = (double)k / (double)this.horizontalNoiseGranularity;
      double d1 = (double)l / (double)this.horizontalNoiseGranularity;
      double[][] adouble = new double[][]{this.func_222547_b(i, j), this.func_222547_b(i, j + 1), this.func_222547_b(i + 1, j), this.func_222547_b(i + 1, j + 1)};

      for(int i1 = this.noiseSizeY - 1; i1 >= 0; --i1) {
         double d2 = adouble[0][i1];
         double d3 = adouble[1][i1];
         double d4 = adouble[2][i1];
         double d5 = adouble[3][i1];
         double d6 = adouble[0][i1 + 1];
         double d7 = adouble[1][i1 + 1];
         double d8 = adouble[2][i1 + 1];
         double d9 = adouble[3][i1 + 1];

         for(int j1 = this.verticalNoiseGranularity - 1; j1 >= 0; --j1) {
            double d10 = (double)j1 / (double)this.verticalNoiseGranularity;
            double d11 = MathHelper.func_219807_a(d10, d0, d1, d2, d6, d4, d8, d3, d7, d5, d9);
            int k1 = i1 * this.verticalNoiseGranularity + j1;
            BlockState blockstate = this.getDefaultStateForHeight(d11, k1);
            if (stateArray != null) {
               stateArray[k1] = blockstate;
            }

            if (heightmapTest != null && heightmapTest.test(blockstate)) {
               return k1 + 1;
            }
         }
      }

      return 0;
   }

   protected BlockState getDefaultStateForHeight(double terrainNoise, int height) {
      if (terrainNoise > 0.0D) {
         return this.defaultBlock;
      } else {
         return height < this.func_230356_f_() ? this.defaultFluid : AIR;
      }
   }

   public void func_225551_a_(WorldGenRegion region, IChunk chunk) {
      ChunkPos chunkPos = chunk.func_76632_l();
      int chunkX = chunkPos.field_77276_a;
      int chunkZ = chunkPos.field_77275_b;
      SharedSeedRandom rand = new SharedSeedRandom();
      rand.func_202422_a(chunkX, chunkZ);
      int xStart = chunkPos.func_180334_c();
      int zStart = chunkPos.func_180333_d();
      double noiseScale = 0.0625D;
      Mutable movingPos = new Mutable();

      for(int x = 0; x < 16; ++x) {
         for(int z = 0; z < 16; ++z) {
            int posX = xStart + x;
            int posZ = zStart + z;
            int topY = chunk.func_201576_a(Type.WORLD_SURFACE_WG, x, z) + 1;
            movingPos.func_181079_c(xStart + x, topY, zStart + z);
            double noise = this.surfaceDepthNoise.func_215460_a((double)posX * noiseScale, (double)posZ * noiseScale, noiseScale, (double)x * noiseScale) * 15.0D;
            Biome biome = region.func_226691_t_(movingPos);
            biome.func_206854_a(rand, chunk, posX, posZ, topY, noise, this.defaultBlock, this.defaultFluid, this.func_230356_f_(), region.func_72905_C());
            ServerWorld world = region.func_201672_e();
            if (LOTRWorldTypes.hasMapFeatures(world)) {
               this.roadGenerator.generateRoad(region, chunk, rand, LOTRBiomes.getWrapperFor(biome, world), movingPos.func_177977_b(), this.func_230356_f_());
            }
         }
      }

      this.makeBedrock(chunk, rand);
   }

   private void makeBedrock(IChunk chunkIn, Random rand) {
      Mutable movingPos = new Mutable();
      int i = chunkIn.func_76632_l().func_180334_c();
      int j = chunkIn.func_76632_l().func_180333_d();
      DimensionSettings dimensionsettings = (DimensionSettings)this.dimensionSettings.get();
      int k = dimensionsettings.func_236118_f_();
      int l = this.chunkGenHeight - 1 - dimensionsettings.func_236117_e_();
      int i1 = true;
      boolean flag = l + 4 >= 0 && l < this.chunkGenHeight;
      boolean flag1 = k + 4 >= 0 && k < this.chunkGenHeight;
      if (flag || flag1) {
         Iterator var12 = BlockPos.func_191531_b(i, 0, j, i + 15, 0, j + 15).iterator();

         while(true) {
            BlockPos blockpos;
            int k1;
            do {
               if (!var12.hasNext()) {
                  return;
               }

               blockpos = (BlockPos)var12.next();
               if (flag) {
                  for(k1 = 0; k1 < 5; ++k1) {
                     if (k1 <= rand.nextInt(5)) {
                        chunkIn.func_177436_a(movingPos.func_181079_c(blockpos.func_177958_n(), l - k1, blockpos.func_177952_p()), Blocks.field_150357_h.func_176223_P(), false);
                     }
                  }
               }
            } while(!flag1);

            for(k1 = 4; k1 >= 0; --k1) {
               if (k1 <= rand.nextInt(5)) {
                  chunkIn.func_177436_a(movingPos.func_181079_c(blockpos.func_177958_n(), k + k1, blockpos.func_177952_p()), Blocks.field_150357_h.func_176223_P(), false);
               }
            }
         }
      }
   }

   public void func_230352_b_(IWorld world, StructureManager strManager, IChunk chunk) {
      ObjectList objectlist = new ObjectArrayList(10);
      ObjectList objectlist1 = new ObjectArrayList(32);
      ChunkPos chunkPos = chunk.func_76632_l();
      int chunkX = chunkPos.field_77276_a;
      int chunkZ = chunkPos.field_77275_b;
      int blockX = chunkX << 4;
      int blockZ = chunkZ << 4;
      Iterator var11 = Structure.field_236384_t_.iterator();

      while(var11.hasNext()) {
         Structure structure = (Structure)var11.next();
         strManager.func_235011_a_(SectionPos.func_218156_a(chunkPos, 0), structure).forEach((p_236089_5_) -> {
            Iterator var6 = p_236089_5_.func_186161_c().iterator();

            while(true) {
               while(true) {
                  StructurePiece structurepiece1;
                  do {
                     if (!var6.hasNext()) {
                        return;
                     }

                     structurepiece1 = (StructurePiece)var6.next();
                  } while(!structurepiece1.func_214810_a(chunkPos, 12));

                  if (structurepiece1 instanceof AbstractVillagePiece) {
                     AbstractVillagePiece abstractvillagepiece = (AbstractVillagePiece)structurepiece1;
                     PlacementBehaviour jigsawpattern$placementbehaviour = abstractvillagepiece.func_214826_b().func_214854_c();
                     if (jigsawpattern$placementbehaviour == PlacementBehaviour.RIGID) {
                        objectlist.add(abstractvillagepiece);
                     }

                     Iterator var10 = abstractvillagepiece.func_214829_e().iterator();

                     while(var10.hasNext()) {
                        JigsawJunction jigsawjunction1 = (JigsawJunction)var10.next();
                        int l5 = jigsawjunction1.func_214895_a();
                        int i6 = jigsawjunction1.func_214893_c();
                        if (l5 > blockX - 12 && i6 > blockZ - 12 && l5 < blockX + 15 + 12 && i6 < blockZ + 15 + 12) {
                           objectlist1.add(jigsawjunction1);
                        }
                     }
                  } else {
                     objectlist.add(structurepiece1);
                  }
               }
            }
         });
      }

      double[][][] adouble = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

      for(int i5 = 0; i5 < this.noiseSizeZ + 1; ++i5) {
         adouble[0][i5] = new double[this.noiseSizeY + 1];
         this.fillNoiseColumn(adouble[0][i5], chunkX * this.noiseSizeX, chunkZ * this.noiseSizeZ + i5);
         adouble[1][i5] = new double[this.noiseSizeY + 1];
      }

      ChunkPrimer chunkprimer = (ChunkPrimer)chunk;
      Heightmap heightmap = chunkprimer.func_217303_b(Type.OCEAN_FLOOR_WG);
      Heightmap heightmap1 = chunkprimer.func_217303_b(Type.WORLD_SURFACE_WG);
      Mutable blockpos$mutable = new Mutable();
      ObjectListIterator objectlistiterator = objectlist.iterator();
      ObjectListIterator objectlistiterator1 = objectlist1.iterator();

      for(int i1 = 0; i1 < this.noiseSizeX; ++i1) {
         int j5;
         for(j5 = 0; j5 < this.noiseSizeZ + 1; ++j5) {
            this.fillNoiseColumn(adouble[1][j5], chunkX * this.noiseSizeX + i1 + 1, chunkZ * this.noiseSizeZ + j5);
         }

         for(j5 = 0; j5 < this.noiseSizeZ; ++j5) {
            ChunkSection chunksection = chunkprimer.func_217332_a(15);
            chunksection.func_222635_a();

            for(int k1 = this.noiseSizeY - 1; k1 >= 0; --k1) {
               double d0 = adouble[0][j5][k1];
               double d1 = adouble[0][j5 + 1][k1];
               double d2 = adouble[1][j5][k1];
               double d3 = adouble[1][j5 + 1][k1];
               double d4 = adouble[0][j5][k1 + 1];
               double d5 = adouble[0][j5 + 1][k1 + 1];
               double d6 = adouble[1][j5][k1 + 1];
               double d7 = adouble[1][j5 + 1][k1 + 1];

               for(int l1 = this.verticalNoiseGranularity - 1; l1 >= 0; --l1) {
                  int i2 = k1 * this.verticalNoiseGranularity + l1;
                  int j2 = i2 & 15;
                  int k2 = i2 >> 4;
                  if (chunksection.func_222632_g() >> 4 != k2) {
                     chunksection.func_222637_b();
                     chunksection = chunkprimer.func_217332_a(k2);
                     chunksection.func_222635_a();
                  }

                  double d8 = (double)l1 / (double)this.verticalNoiseGranularity;
                  double d9 = MathHelper.func_219803_d(d8, d0, d4);
                  double d10 = MathHelper.func_219803_d(d8, d2, d6);
                  double d11 = MathHelper.func_219803_d(d8, d1, d5);
                  double d12 = MathHelper.func_219803_d(d8, d3, d7);

                  for(int l2 = 0; l2 < this.horizontalNoiseGranularity; ++l2) {
                     int i3 = blockX + i1 * this.horizontalNoiseGranularity + l2;
                     int j3 = i3 & 15;
                     double d13 = (double)l2 / (double)this.horizontalNoiseGranularity;
                     double d14 = MathHelper.func_219803_d(d13, d9, d10);
                     double d15 = MathHelper.func_219803_d(d13, d11, d12);

                     for(int k3 = 0; k3 < this.horizontalNoiseGranularity; ++k3) {
                        int l3 = blockZ + j5 * this.horizontalNoiseGranularity + k3;
                        int i4 = l3 & 15;
                        double d16 = (double)k3 / (double)this.horizontalNoiseGranularity;
                        double d17 = MathHelper.func_219803_d(d16, d14, d15);
                        double d18 = MathHelper.func_151237_a(d17 / 200.0D, -1.0D, 1.0D);

                        int j4;
                        int k4;
                        int l4;
                        for(d18 = d18 / 2.0D - d18 * d18 * d18 / 24.0D; objectlistiterator.hasNext(); d18 += func_222556_a(j4, k4, l4) * 0.8D) {
                           StructurePiece structurepiece = (StructurePiece)objectlistiterator.next();
                           MutableBoundingBox mutableboundingbox = structurepiece.func_74874_b();
                           j4 = Math.max(0, Math.max(mutableboundingbox.field_78897_a - i3, i3 - mutableboundingbox.field_78893_d));
                           k4 = i2 - (mutableboundingbox.field_78895_b + (structurepiece instanceof AbstractVillagePiece ? ((AbstractVillagePiece)structurepiece).func_214830_d() : 0));
                           l4 = Math.max(0, Math.max(mutableboundingbox.field_78896_c - l3, l3 - mutableboundingbox.field_78892_f));
                        }

                        objectlistiterator.back(objectlist.size());

                        while(objectlistiterator1.hasNext()) {
                           JigsawJunction jigsawjunction = (JigsawJunction)objectlistiterator1.next();
                           int k5 = i3 - jigsawjunction.func_214895_a();
                           j4 = i2 - jigsawjunction.func_214896_b();
                           k4 = l3 - jigsawjunction.func_214893_c();
                           d18 += func_222556_a(k5, j4, k4) * 0.4D;
                        }

                        objectlistiterator1.back(objectlist1.size());
                        BlockState blockstate = this.getDefaultStateForHeight(d18, i2);
                        if (blockstate != AIR) {
                           blockpos$mutable.func_181079_c(i3, i2, l3);
                           if (blockstate.getLightValue(chunkprimer, blockpos$mutable) != 0) {
                              chunkprimer.func_201637_h(blockpos$mutable);
                           }

                           chunksection.func_177484_a(j3, j2, i4, blockstate, false);
                           heightmap.func_202270_a(j3, i2, i4, blockstate);
                           heightmap1.func_202270_a(j3, i2, i4, blockstate);
                        }
                     }
                  }
               }
            }

            chunksection.func_222637_b();
         }

         double[][] adouble1 = adouble[0];
         adouble[0] = adouble[1];
         adouble[1] = adouble1;
      }

   }

   private static double func_222556_a(int p_222556_0_, int p_222556_1_, int p_222556_2_) {
      int sample = 24;
      int hSample = sample / 2;
      int i = p_222556_0_ + hSample;
      int j = p_222556_1_ + hSample;
      int k = p_222556_2_ + hSample;
      if (i >= 0 && i < sample) {
         if (j >= 0 && j < sample) {
            return k >= 0 && k < sample ? (double)CUBIC_SAMPLE[k * sample * sample + i * sample + j] : 0.0D;
         } else {
            return 0.0D;
         }
      } else {
         return 0.0D;
      }
   }

   private static double func_222554_b(int x, int y, int z) {
      double hSq = (double)(x * x + z * z);
      double v = (double)y + 0.5D;
      double vSq = v * v;
      double d3 = Math.pow(2.718281828459045D, -(vSq / 16.0D + hSq / 16.0D));
      double d4 = -v * MathHelper.func_181161_i(vSq / 2.0D + hSq / 2.0D) / 2.0D;
      return d4 * d3;
   }

   public int func_230355_e_() {
      return this.chunkGenHeight;
   }

   public int func_230356_f_() {
      return ((DimensionSettings)this.dimensionSettings.get()).func_236119_g_();
   }

   public List func_230353_a_(Biome biome, StructureManager strManager, EntityClassification creatureType, BlockPos pos) {
      return LOTRBiomes.getWrapperFor(biome, LOTRBiomes.getServerBiomeContextWorld()).getSpawnsAtLocation(creatureType, pos);
   }

   public void func_230354_a_(WorldGenRegion region) {
      if (!((DimensionSettings)this.dimensionSettings.get()).func_236120_h_()) {
         int i = region.func_201679_a();
         int j = region.func_201680_b();
         Biome biome = region.func_226691_t_((new ChunkPos(i, j)).func_206849_h());
         SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
         sharedseedrandom.func_202424_a(region.func_72905_C(), i << 4, j << 4);
         WorldEntitySpawner.func_77191_a(region, biome, i, j, sharedseedrandom);
      }

   }

   static {
      AIR = Blocks.field_150350_a.func_176223_P();
   }
}
