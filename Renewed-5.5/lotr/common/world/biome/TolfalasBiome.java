package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class TolfalasBiome extends LOTRBiomeBase {
   public TolfalasBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.OCEAN).func_205421_a(0.3F).func_205420_b(1.0F).func_205414_c(0.8F).func_205417_d(0.7F), major);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.09D).threshold(0.65D).state(Blocks.field_150351_n).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.4D, 0.09D).threshold(0.2D).state(Blocks.field_150348_b).topOnly()));
      config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(90).useStone()));
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 4, 10, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 1, 0.4F, LOTRBiomeFeatures.oak(), 100, LOTRBiomeFeatures.oakDesert(), 500, LOTRBiomeFeatures.oakDead(), 2000);
      LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addDefaultFlowers(builder, 1);
      LOTRBiomeFeatures.addAthelasChance(builder);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addReeds(builder);
      LOTRBiomeFeatures.addSugarCane(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
   }
}
