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

public class BreelandBiome extends LOTRBiomeBase {
   public BreelandBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.1F).func_205420_b(0.2F).func_205414_c(0.8F).func_205417_d(0.7F), major);
   }

   protected BreelandBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected final Object[] breelandTrees() {
      return new Object[]{LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 3000, LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 3, LOTRBiomeFeatures.beech(), 3000, LOTRBiomeFeatures.beechFancy(), 750, LOTRBiomeFeatures.beechBees(), 3, LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.maple(), 2000, LOTRBiomeFeatures.mapleFancy(), 500, LOTRBiomeFeatures.mapleBees(), 2, LOTRBiomeFeatures.mapleFancyBees(), 1, LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 200, LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100, LOTRBiomeFeatures.apple(), 15, LOTRBiomeFeatures.pear(), 15, LOTRBiomeFeatures.appleBees(), 15, LOTRBiomeFeatures.pearBees(), 15};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.05F, TreeCluster.of(8, 20), this.breelandTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
      LOTRBiomeFeatures.addAthelasChance(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder, 2);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.BREE_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DRYSTONE.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.BEECH_FENCE.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_222432_lU.func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.DRYSTONE.withStandardHedge();
   }
}
