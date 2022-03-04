package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.util.LOTRUtil;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class UmbarBiome extends LOTRBiomeBase {
   public UmbarBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.1F).func_205420_b(0.2F).func_205414_c(0.9F).func_205417_d(0.6F), major);
   }

   protected UmbarBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setGrass(11914805);
      this.biomeColors.setFog(16248281);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.25D, 0.07D, 0.002D).threshold(0.2D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.25D, 0.07D, 0.002D).threshold(0.3667D).state(Blocks.field_150354_m)));
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addLapisOre(builder);
   }

   protected final Object[] umbarTrees() {
      return new Object[]{LOTRBiomeFeatures.oakDesert(), 10000, LOTRBiomeFeatures.oakDesertBees(), 10, LOTRBiomeFeatures.cedar(), 3000, LOTRBiomeFeatures.cypress(), 5000};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.15F, TreeCluster.of(10, 30), this.umbarTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addHaradFlowers(builder, 3);
      LOTRBiomeFeatures.addHaradDoubleFlowers(builder, 1);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addLessCommonReeds(builder);
      LOTRBiomeFeatures.addPapyrus(builder);
      LOTRBiomeFeatures.addSugarCane(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder, 1);
      this.addCaracals(builder, 3);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.UMBAR_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.UMBAR_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.NUMENOREAN_BRICK_WALL.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150478_aa.func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.UMBAR;
   }

   public static class Hills extends UmbarBiome {
      public Hills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(1.2F).func_205420_b(0.8F).func_205414_c(0.8F).func_205417_d(0.6F), major);
         this.biomeColors.resetGrass();
      }

      protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addBoulders(builder);
         LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 1, 32, 3);
      }
   }

   public static class Forest extends UmbarBiome {
      public Forest(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.3F).func_205414_c(0.8F).func_205417_d(1.0F), major);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTrees(this, builder, 5, 0.5F, LOTRUtil.combineVarargs(this.umbarTrees(), LOTRBiomeFeatures.cedar(), 3000, LOTRBiomeFeatures.cedarLarge(), 500, LOTRBiomeFeatures.cypress(), 2000));
         LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
         LOTRBiomeFeatures.addHaradFlowers(builder, 4);
         LOTRBiomeFeatures.addHaradDoubleFlowers(builder, 2);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
      }
   }
}
