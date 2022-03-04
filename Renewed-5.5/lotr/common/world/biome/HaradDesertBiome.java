package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class HaradDesertBiome extends LOTRBiomeBase {
   public HaradDesertBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.DESERT).func_205421_a(0.2F).func_205420_b(0.1F).func_205414_c(1.5F).func_205417_d(0.1F), major);
   }

   protected HaradDesertBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setCloudCoverage(0.2F);
      this.biomeColors.setFog(16180681);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setTop(Blocks.field_150354_m.func_176223_P());
      config.setFiller(Blocks.field_150354_m.func_176223_P());
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addLapisOre(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 240, 3);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150322_A.func_176223_P(), 1, 3, 240, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oakDead(), 8000, LOTRBiomeFeatures.oakDesert(), 2000};
      LOTRBiomeFeatures.addTreesIncrease(this, builder, 0, 5.0E-4F, 1, weightedTrees);
      LOTRBiomeFeatures.addTreesIncrease(this, builder, 0, 5.0E-4F, 3, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 1, GrassBlends.WITH_ARID);
      LOTRBiomeFeatures.addHaradFlowers(builder, 0);
      LOTRBiomeFeatures.addCactiAtSurfaceChance(builder, 50);
      LOTRBiomeFeatures.addDeadBushAtSurfaceChance(builder, 16);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addLessCommonReeds(builder);
      LOTRBiomeFeatures.addMoreCommonPapyrus(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   protected ExtendedWeatherType getBiomeExtendedWeather() {
      return ExtendedWeatherType.SANDSTORM;
   }

   public Biome getRiver(IWorld world) {
      return null;
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.HARAD.withRepair(0.5F);
   }

   public static class Hills extends HaradDesertBiome {
      public Hills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.DESERT).func_205421_a(0.5F).func_205420_b(0.8F).func_205414_c(1.2F).func_205417_d(0.3F), major);
         this.biomeColors.setCloudCoverage(0.4F);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.09D).threshold(0.1D).state(Blocks.field_150322_A), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.4D, 0.09D).threshold(0.3D).state(Blocks.field_150348_b)));
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oakDead(), 8000, LOTRBiomeFeatures.oakDesert(), 2000};
         LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.01F, TreeCluster.of(6, 100), weightedTrees);
         LOTRBiomeFeatures.addGrass(this, builder, 1, GrassBlends.WITH_ARID);
         LOTRBiomeFeatures.addHaradFlowers(builder, 0);
         LOTRBiomeFeatures.addCactiAtSurfaceChance(builder, 200);
         LOTRBiomeFeatures.addDeadBushAtSurfaceChance(builder, 16);
      }

      protected ExtendedWeatherType getBiomeExtendedWeather() {
         return ExtendedWeatherType.NONE;
      }
   }

   public static class HalfDesert extends HaradDesertBiome {
      public HalfDesert(boolean major) {
         super((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.DESERT).func_205421_a(0.2F).func_205420_b(0.1F).func_205414_c(1.5F).func_205417_d(0.3F), major);
         this.biomeColors.setCloudCoverage(0.6F);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.08D).threshold(0.15D).state(Blocks.field_196660_k).topOnly()));
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oakDead(), 5000, LOTRBiomeFeatures.oakDesert(), 5000};
         LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.05F, TreeCluster.of(8, 100), weightedTrees);
         LOTRBiomeFeatures.addGrass(this, builder, 5, GrassBlends.WITH_ARID);
         LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_WITH_ARID);
         LOTRBiomeFeatures.addHaradFlowers(builder, 0);
         LOTRBiomeFeatures.addCactiAtSurfaceChance(builder, 200);
         LOTRBiomeFeatures.addDeadBushAtSurfaceChance(builder, 16);
      }

      public Biome getRiver(IWorld world) {
         return this.getNormalRiver(world);
      }
   }
}
