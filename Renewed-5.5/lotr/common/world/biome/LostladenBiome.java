package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class LostladenBiome extends LOTRBiomeBase {
   public LostladenBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.DESERT).func_205421_a(0.2F).func_205420_b(0.1F).func_205414_c(0.9F).func_205417_d(0.2F), major);
      this.biomeColors.setSky(15592678);
      this.biomeColors.setCloudCoverage(0.7F);
      this.biomeColors.setFog(15393237);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.09D).threshold(0.15D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.4D, 0.09D).threshold(0.05D).states(Blocks.field_150354_m, 4, Blocks.field_150322_A, 1), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.4D, 0.09D).threshold(0.05D).states(Blocks.field_150348_b, 4, Blocks.field_150351_n, 1)));
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addLapisOre(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 48, 3);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150322_A.func_176223_P(), 1, 3, 48, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oakDead(), 200, LOTRBiomeFeatures.oakDesert(), 1000};
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.02F, TreeCluster.of(8, 200), weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 3, GrassBlends.WITH_ARID);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_WITH_ARID);
      LOTRBiomeFeatures.addDefaultFlowers(builder, 0);
      LOTRBiomeFeatures.addCactiAtSurfaceChance(builder, 200);
      LOTRBiomeFeatures.addDeadBushAtSurfaceChance(builder, 8);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   protected ExtendedWeatherType getBiomeExtendedWeather() {
      return ExtendedWeatherType.SANDSTORM;
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.HARAD.withRepair(0.3F);
   }
}
