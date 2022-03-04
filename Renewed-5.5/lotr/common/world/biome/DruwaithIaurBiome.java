package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class DruwaithIaurBiome extends LOTRBiomeBase {
   public DruwaithIaurBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.4F).func_205414_c(0.7F).func_205417_d(0.8F), major);
      this.biomeColors.setGrass(7115073).setSky(12441581).setFog(11390171);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.06D).threshold(0.65D).state(Blocks.field_150348_b), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.4D, 0.06D).threshold(0.35D).state(Blocks.field_196660_k).topOnly()));
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addBoulders(builder);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 4, 30, 4);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 1, 0.1F, TreeCluster.of(10, 24), LOTRBiomeFeatures.oak(), 4000, LOTRBiomeFeatures.oakFancy(), 2000, LOTRBiomeFeatures.oakBees(), 40, LOTRBiomeFeatures.oakFancyBees(), 20, LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 200, LOTRBiomeFeatures.birchBees(), 5, LOTRBiomeFeatures.birchFancyBees(), 2, LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2, LOTRBiomeFeatures.darkOak(), 5000, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.pineDead(), 50, LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.larch(), 2000, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 5, LOTRBiomeFeatures.pearBees(), 5, LOTRBiomeFeatures.oakShrub(), 4000);
      LOTRBiomeFeatures.addGrass(this, builder, 14, GrassBlends.WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_WITH_FERNS);
      LOTRBiomeFeatures.addForestFlowers(builder, 3);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
      LOTRBiomeFeatures.addFoxBerryBushes(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addWolves(builder);
      this.addDeer(builder);
      this.addBears(builder);
      this.addFoxes(builder);
   }
}
