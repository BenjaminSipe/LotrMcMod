package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class AnfalasBiome extends BaseGondorBiome {
   public AnfalasBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.08F).func_205420_b(0.15F).func_205414_c(0.9F).func_205417_d(0.9F), major);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      super.setupSurface(config);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.008D).zScales(2.333D, 8.0D).weights(4, 10).threshold(0.571D).state(Blocks.field_150354_m)));
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.05F, TreeCluster.of(8, 25), LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 10, LOTRBiomeFeatures.birch(), 1000, LOTRBiomeFeatures.birchFancy(), 500, LOTRBiomeFeatures.birchBees(), 10, LOTRBiomeFeatures.birchFancyBees(), 5, LOTRBiomeFeatures.cypress(), 4000, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1);
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
      LOTRBiomeFeatures.addAthelasChance(builder);
      LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
   }
}
