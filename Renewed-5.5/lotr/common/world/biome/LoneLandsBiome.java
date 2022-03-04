package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class LoneLandsBiome extends LOTRBiomeBase {
   public LoneLandsBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.15F).func_205420_b(0.4F).func_205414_c(0.6F).func_205417_d(0.5F), major);
   }

   protected LoneLandsBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setGrass(12565603);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 5, 32, 3);
   }

   protected final Object[] loneLandsTrees() {
      return new Object[]{LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 3000, LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 3, LOTRBiomeFeatures.spruce(), 3000, LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 500, LOTRBiomeFeatures.beechBees(), 1, LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.birch(), 100, LOTRBiomeFeatures.birchFancy(), 50, LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.maple(), 50, LOTRBiomeFeatures.mapleFancy(), 5, LOTRBiomeFeatures.mapleBees(), 1, LOTRBiomeFeatures.mapleFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100, LOTRBiomeFeatures.apple(), 10, LOTRBiomeFeatures.pear(), 10, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.2F, TreeCluster.of(12, 20), this.loneLandsTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 3);
      LOTRBiomeFeatures.addAthelasChance(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder);
      this.addBears(builder, 2);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, 2, ((Block)LOTRBlocks.RANGER_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ARNOR_BRICK.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_ARNOR_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_ARNOR_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_180407_aO.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150478_aa.func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.ARNOR.withRepair(0.6F);
   }

   public static class Hills extends LoneLandsBiome {
      public Hills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.7F).func_205420_b(1.0F).func_205414_c(0.6F).func_205417_d(0.7F), major);
         this.biomeColors.setGrass(11713120);
      }

      protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addStoneVariants(builder);
         LOTRBiomeFeatures.addAndesite(builder);
         LOTRBiomeFeatures.addDiorite(builder);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.1F, TreeCluster.of(8, 30), this.loneLandsTrees());
         LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.MOORS);
         LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_MOORS);
         LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
         LOTRBiomeFeatures.addAthelasChance(builder);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
         this.addWolves(builder, 2);
      }
   }
}
