package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class BrownLandsBiome extends LOTRBiomeBase {
   public BrownLandsBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.2F).func_205414_c(0.8F).func_205417_d(0.2F), major);
      this.biomeColors.setGrass(11373417);
      this.biomeColors.setSky(8878434);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.7D, 0.08D).threshold(0.05D).state(Blocks.field_196660_k).topOnly()));
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 8, 4);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.1F, LOTRBiomeFeatures.oakDead(), 10, LOTRBiomeFeatures.spruceDead(), 3);
      LOTRBiomeFeatures.addGrass(this, builder, 2, GrassBlends.BASIC);
      LOTRBiomeFeatures.addDeadBushAtSurfaceChance(builder, 8);
   }

   protected void addPumpkins(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }
}
