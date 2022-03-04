package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class HarondorBiome extends LOTRBiomeBase {
   public HarondorBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.3F).func_205414_c(1.0F).func_205417_d(0.6F), major);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.09D).threshold(0.15D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.4D, 0.09D, 0.002D).weights(1, 1, 2).threshold(0.35D).state(Blocks.field_150354_m)));
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addLapisOre(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 16, 4);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oakDesert(), 10000, LOTRBiomeFeatures.oakDesertBees(), 10, LOTRBiomeFeatures.cedar(), 2500};
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.1F, weightedTrees);
      LOTRBiomeFeatures.addTreesIncrease(this, builder, 0, 0.0625F, 3, weightedTrees);
      LOTRBiomeFeatures.addTreesIncrease(this, builder, 0, 0.0625F, 7, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.WITH_ARID);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_WITH_ARID);
      LOTRBiomeFeatures.addDeadBushes(builder, 1);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 4);
      LOTRBiomeFeatures.addCactiFreq(builder, 1);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addReeds(builder);
      LOTRBiomeFeatures.addSugarCane(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addCaracals(builder, 1);
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.GONDOR.withRepair(0.6F);
   }
}
