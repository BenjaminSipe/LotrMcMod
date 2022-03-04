package lotr.common.world.biome;

import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class AnduinValeBiome extends LOTRBiomeBase {
   public AnduinValeBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.05F).func_205420_b(0.05F).func_205414_c(0.7F).func_205417_d(1.0F), major);
   }

   protected AnduinValeBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 5, 16, 3);
   }

   protected final Object[] anduinTrees() {
      return new Object[]{LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakBees(), 100, LOTRBiomeFeatures.oakFancyBees(), 10, LOTRBiomeFeatures.birch(), 1500, LOTRBiomeFeatures.birchFancy(), 150, LOTRBiomeFeatures.birchBees(), 15, LOTRBiomeFeatures.birchFancyBees(), 2, LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.larch(), 1500, LOTRBiomeFeatures.pine(), 1000, LOTRBiomeFeatures.apple(), 10, LOTRBiomeFeatures.appleBees(), 10, LOTRBiomeFeatures.pear(), 10, LOTRBiomeFeatures.pearBees(), 10};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.25F, TreeCluster.of(10, 20), this.anduinTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 5);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 2);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder);
      this.addBears(builder);
   }
}
