package lotr.common.world.biome;

import lotr.common.init.LOTRBiomes;
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

public class LindonBiome extends LOTRBiomeBase {
   public LindonBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.15F).func_205420_b(0.25F).func_205414_c(0.7F).func_205417_d(0.9F), major);
   }

   protected LindonBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setSky(9411050).setFog(15264767);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addAndesite(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addEdhelvirOre(builder);
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addWhiteSandSediments(builder);
   }

   protected final Object[] lindonTrees() {
      return new Object[]{LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakFancy(), 250, LOTRBiomeFeatures.oakBees(), 1, LOTRBiomeFeatures.oakFancyBees(), 1, LOTRBiomeFeatures.birch(), 10000, LOTRBiomeFeatures.birchFancy(), 2000, LOTRBiomeFeatures.birchBees(), 10, LOTRBiomeFeatures.birchFancyBees(), 2, LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 250, LOTRBiomeFeatures.beechBees(), 1, LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.aspen(), 2000, LOTRBiomeFeatures.aspenLarge(), 1000, LOTRBiomeFeatures.apple(), 20, LOTRBiomeFeatures.pear(), 20, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.05F, TreeCluster.of(10, 10), this.lindonTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 3);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.LINDON_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HIGH_ELVEN_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_180404_aQ.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HIGH_ELVEN_TORCH.get()).func_176223_P(), 1));
   }

   public LOTRBiomeBase getShore() {
      return LOTRBiomes.WHITE_BEACH.getInitialisedBiomeWrapper();
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.HIGH_ELVEN;
   }

   public static class Woodlands extends LindonBiome {
      public Woodlands(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.5F).func_205414_c(0.7F).func_205417_d(1.0F), major);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTrees(this, builder, 6, 0.05F, this.lindonTrees());
         LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_STANDARD);
         LOTRBiomeFeatures.addForestFlowers(builder, 4);
         LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
         this.addDeer(builder, 3);
      }
   }
}
