package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class AndrastBiome extends LOTRBiomeBase {
   public AndrastBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.4F).func_205414_c(0.7F).func_205417_d(0.9F), major);
      this.biomeColors.setGrass(10202470);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      super.setupSurface(config);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.07D).threshold(0.55D).state(Blocks.field_150348_b), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.07D).threshold(0.3D).state(Blocks.field_196660_k).topOnly()));
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 4, 16, 4);
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.GONDOR_ROCK.get()).func_176223_P(), 1, 4, 16, 4);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.4F, TreeCluster.of(10, 24), LOTRBiomeFeatures.oak(), 4000, LOTRBiomeFeatures.oakFancy(), 2000, LOTRBiomeFeatures.oakBees(), 4, LOTRBiomeFeatures.oakFancyBees(), 2, LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 200, LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechBees(), 1, LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.larch(), 2000, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1, LOTRBiomeFeatures.oakShrub(), 15000);
      LOTRBiomeFeatures.addGrass(this, builder, 12, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 3);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder);
      this.addBears(builder);
   }
}
