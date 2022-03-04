package lotr.common.world.biome;

import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class LossarnachBiome extends BaseGondorBiome {
   public LossarnachBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.1F).func_205420_b(0.2F).func_205414_c(0.9F).func_205417_d(1.0F), major);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.4F, TreeCluster.of(10, 30), LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakBees(), 500, LOTRBiomeFeatures.oakFancyBees(), 100, LOTRBiomeFeatures.darkOak(), 4000, LOTRBiomeFeatures.birch(), 3000, LOTRBiomeFeatures.birchFancy(), 1000, LOTRBiomeFeatures.birchBees(), 300, LOTRBiomeFeatures.birchFancyBees(), 100, LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 100, LOTRBiomeFeatures.beechBees(), 50, LOTRBiomeFeatures.beechFancyBees(), 10, LOTRBiomeFeatures.maple(), 500, LOTRBiomeFeatures.mapleFancy(), 100, LOTRBiomeFeatures.mapleBees(), 50, LOTRBiomeFeatures.mapleFancyBees(), 10, LOTRBiomeFeatures.apple(), 400, LOTRBiomeFeatures.pear(), 400, LOTRBiomeFeatures.appleBees(), 40, LOTRBiomeFeatures.pearBees(), 40, LOTRBiomeFeatures.oakShrub(), 6000);
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 10);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 3);
      LOTRBiomeFeatures.addAthelasChance(builder);
      LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
   }
}
