package lotr.common.world.biome;

import java.util.List;
import java.util.Random;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.biome.surface.UnderwaterNoiseMixer;
import lotr.common.world.gen.feature.LatitudeBasedFeatureConfig;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.world.gen.PerlinNoiseGenerator;

public class SeaBiome extends LOTRBiomeBase {
   private final PerlinNoiseGenerator iceNoiseGen;
   private SeaClimateWaterSpawns coldWaterSpawns;
   private SeaClimateWaterSpawns normalWaterSpawns;
   private SeaClimateWaterSpawns tropicalWaterSpawns;
   private final Random waterSpawningRand;

   public SeaBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.OCEAN).func_205421_a(-1.5F).func_205420_b(0.1F).func_205414_c(0.7F).func_205417_d(0.9F), major);
   }

   protected SeaBiome(Builder builder, boolean major) {
      super(builder, major);
      this.iceNoiseGen = makeSingleLayerPerlinNoise(5231241491057810726L);
      this.coldWaterSpawns = new SeaClimateWaterSpawns();
      this.normalWaterSpawns = new SeaClimateWaterSpawns();
      this.tropicalWaterSpawns = new SeaClimateWaterSpawns();
      this.waterSpawningRand = new Random();
   }

   protected void setupBiomeAmbience(net.minecraft.world.biome.BiomeAmbience.Builder builder) {
      super.setupBiomeAmbience(builder);
      builder.func_242537_a(LOTRGrassColorModifiers.SEA);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setUnderwaterNoiseMixer(UnderwaterNoiseMixer.SEA_LATITUDE);
   }

   protected void addFeatures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addFeatures(builder);
      this.addIcebergs(builder);
   }

   protected void addIcebergs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addIcebergs(builder);
      LOTRBiomeFeatures.addBlueIcePatches(builder);
   }

   protected void addCarvers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addSeaCarvers(builder);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addExtraSalt(builder, 8, 4, 64);
      LOTRBiomeFeatures.addSaltInSand(builder, 8, 1, 56, 80);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithLatitudeConfig(this, builder, LatitudeBasedFeatureConfig.LatitudeConfiguration.ofInverted(LatitudeBasedFeatureConfig.LatitudeValuesType.ICE).min(0.75F), 1, 0.1F, LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.birch(), 1000, LOTRBiomeFeatures.birchFancy(), 100, LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 50, LOTRBiomeFeatures.apple(), 30, LOTRBiomeFeatures.pear(), 30);
      LOTRBiomeFeatures.addTreesWithLatitudeConfig(this, builder, LatitudeBasedFeatureConfig.LatitudeConfiguration.of(LatitudeBasedFeatureConfig.LatitudeValuesType.ICE), 0, 0.25F, LOTRBiomeFeatures.spruce(), 600, LOTRBiomeFeatures.spruceThin(), 400, LOTRBiomeFeatures.spruceDead(), 2000, LOTRBiomeFeatures.fir(), 400);
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addDefaultFlowers(builder, 2);
      LOTRBiomeFeatures.addSeagrass(builder, 48, 0.4F);
      LOTRBiomeFeatures.addExtraUnderwaterSeagrass(builder);
      LOTRBiomeFeatures.addKelp(builder);
      LOTRBiomeFeatures.addCoral(builder);
      LOTRBiomeFeatures.addSeaPickles(builder);
      LOTRBiomeFeatures.addSponges(builder);
   }

   public List getSpawnsAtLocation(EntityClassification creatureType, BlockPos pos) {
      if (creatureType != EntityClassification.WATER_CREATURE && creatureType != EntityClassification.WATER_AMBIENT) {
         return super.getSpawnsAtLocation(creatureType, pos);
      } else {
         int z = pos.func_177952_p();
         double iceProgressF = (double)MapSettingsManager.serverInstance().getCurrentLoadedMap().getWaterLatitudes().getIceCoverageForLatitude(z);
         double coralProgressF = (double)MapSettingsManager.serverInstance().getCurrentLoadedMap().getWaterLatitudes().getCoralForLatitude(z);
         if (iceProgressF > 0.0D && (double)this.waterSpawningRand.nextFloat() < iceProgressF) {
            return this.coldWaterSpawns.getSpawns(creatureType);
         } else {
            return coralProgressF > 0.0D && (double)this.waterSpawningRand.nextFloat() < coralProgressF ? this.tropicalWaterSpawns.getSpawns(creatureType) : this.normalWaterSpawns.getSpawns(creatureType);
         }
      }
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addAmbientCreatures(builder);
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200737_ac, 6, 4, 4));
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200786_Z, 2, 1, 2));
      this.coldWaterSpawns.add(new Spawners(EntityType.field_200749_ao, 3, 1, 4));
      this.coldWaterSpawns.add(new Spawners(EntityType.field_203780_j, 15, 3, 6));
      this.coldWaterSpawns.add(new Spawners(EntityType.field_203778_ae, 15, 1, 5));
      this.normalWaterSpawns.add(new Spawners(EntityType.field_200749_ao, 1, 1, 4));
      this.normalWaterSpawns.add(new Spawners(EntityType.field_203780_j, 10, 3, 6));
      this.normalWaterSpawns.add(new Spawners(EntityType.field_205137_n, 1, 1, 2));
      this.tropicalWaterSpawns.add(new Spawners(EntityType.field_200749_ao, 10, 4, 4));
      this.tropicalWaterSpawns.add(new Spawners(EntityType.field_203779_Z, 15, 1, 3));
      this.tropicalWaterSpawns.add(new Spawners(EntityType.field_204262_at, 25, 8, 8));
      this.tropicalWaterSpawns.add(new Spawners(EntityType.field_205137_n, 2, 1, 2));
   }

   public Biome getRiver(IWorld world) {
      return null;
   }

   public boolean doesWaterFreeze(boolean defaultDoesSnowGenerate, IWorldReader world, BlockPos pos, boolean mustBeAtEdge) {
      if (this.isSeaFrozen(world, pos) && pos.func_177956_o() >= 0 && pos.func_177956_o() < world.func_230315_m_().func_241513_m_() && world.func_226658_a_(LightType.BLOCK, pos) < 10) {
         BlockState state = world.func_180495_p(pos);
         FluidState fluid = world.func_204610_c(pos);
         if (fluid.func_206886_c() == Fluids.field_204546_a && state.func_177230_c() instanceof FlowingFluidBlock) {
            if (!mustBeAtEdge) {
               return true;
            }

            boolean surrounded = world.func_201671_F(pos.func_177976_e()) && world.func_201671_F(pos.func_177974_f()) && world.func_201671_F(pos.func_177978_c()) && world.func_201671_F(pos.func_177968_d());
            if (!surrounded) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean doesSnowGenerate(boolean defaultDoesSnowGenerate, IWorldReader world, BlockPos pos) {
      if (this.isSeaFrozen(world, pos) && pos.func_177956_o() >= 0 && pos.func_177956_o() < world.func_230315_m_().func_241513_m_() && world.func_226658_a_(LightType.BLOCK, pos) < 10) {
         BlockState state = world.func_180495_p(pos);
         if (state.func_196958_f() && Blocks.field_150433_aE.func_176223_P().func_196955_c(world, pos)) {
            return true;
         }
      }

      return false;
   }

   public boolean isSeaFrozen(IWorldReader world, BlockPos pos) {
      int x = pos.func_177958_n();
      int z = pos.func_177952_p();
      float iceProgressF = getIceLatitudeLevel(world, z);
      if (iceProgressF <= 0.0F) {
         return false;
      } else if (iceProgressF >= 1.0F) {
         return true;
      } else {
         double noise1 = this.iceNoiseGen.func_215464_a((double)x * 0.1D, (double)z * 0.1D, false);
         double noise2 = this.iceNoiseGen.func_215464_a((double)x * 0.03D, (double)z * 0.03D, false);
         double noiseAvg = (noise1 + noise2) / 2.0D;
         double noiseNorm = (noiseAvg + 1.0D) / 2.0D;
         return noiseNorm < (double)iceProgressF;
      }
   }

   public static float getIceLatitudeLevel(IWorldReader world, int z) {
      return MapSettingsManager.sidedInstance(world).getCurrentLoadedMap().getWaterLatitudes().getIceCoverageForLatitude(z);
   }

   public static float getAdjustedTemperatureForGrassAndFoliage(IWorldReader world, Biome biome, int z) {
      float iceF = getIceLatitudeLevel(world, z);
      float adjustedTemp = MathHelper.func_219799_g(iceF, biome.func_242445_k(), 0.0F);
      return MathHelper.func_76131_a(adjustedTemp, 0.0F, 1.0F);
   }

   public float getTemperatureForSnowWeatherRendering(IWorld world, BlockPos pos) {
      return getIceLatitudeLevel(world, pos.func_177952_p()) > 0.25F ? 0.0F : super.getTemperatureForSnowWeatherRendering(world, pos);
   }

   public static class WhiteBeach extends SeaBiome.Beach {
      public WhiteBeach(boolean major) {
         super(major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         config.setTop(((Block)LOTRBlocks.WHITE_SAND.get()).func_176223_P());
         config.setFiller(((Block)LOTRBlocks.WHITE_SAND.get()).func_176223_P());
         config.setUnderwater(((Block)LOTRBlocks.WHITE_SAND.get()).func_176223_P());
      }

      protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addWhiteSandSediments(builder);
      }
   }

   public static class Beach extends SeaBiome {
      public Beach(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.BEACH).func_205421_a(-0.1F).func_205420_b(0.03F).func_205414_c(0.8F).func_205417_d(0.7F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         config.setTop(Blocks.field_150354_m.func_176223_P());
         config.setFiller(Blocks.field_150354_m.func_176223_P());
         config.setUnderwater(Blocks.field_150354_m.func_176223_P());
      }

      protected void addIcebergs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addVegetation(builder);
         LOTRBiomeFeatures.addDriftwood(builder, 12);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
         builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_203099_aq, 10, 2, 5));
      }

      public Biome getRiver(IWorld world) {
         return this.getNormalRiver(world);
      }
   }

   public static class WesternIsles extends SeaBiome.Island {
      public WesternIsles(boolean major) {
         super(major);
      }

      protected void addIcebergs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      }

      protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addAndesite(builder);
      }
   }

   public static class Island extends SeaBiome {
      public Island(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.OCEAN).func_205421_a(0.0F).func_205420_b(0.3F).func_205414_c(0.7F).func_205417_d(0.9F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.09D).threshold(0.7D).state(Blocks.field_150351_n).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.4D, 0.09D).threshold(0.3D).state(Blocks.field_150348_b)));
      }

      protected void addIcebergs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      }

      protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 1, 2, 3);
      }
   }
}
