package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class RivendellBiome extends LOTRBiomeBase {
   public RivendellBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.12F).func_205420_b(0.6F).func_205414_c(0.7F).func_205417_d(1.0F), major);
   }

   protected RivendellBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setWater(6933979);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addEdhelvirOre(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addBoulders(builder);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 0, 1, 12, 6);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 24, 3);
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.CHALK.get()).func_176223_P(), 0, 1, 60, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 2000, LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 20, LOTRBiomeFeatures.birch(), 5000, LOTRBiomeFeatures.birchFancy(), 1000, LOTRBiomeFeatures.birchBees(), 50, LOTRBiomeFeatures.birchFancyBees(), 10, LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2, LOTRBiomeFeatures.larch(), 1500, LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.aspenLarge(), 500, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1};
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.25F, TreeCluster.of(12, 16), weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreelineIncrease(this, builder, 2, 0.2F, 2, 85, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 5);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200796_j, 40, 4, 4));
      this.addHorsesDonkeys(builder);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.RIVENDELL_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HIGH_ELVEN_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_180404_aQ.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HIGH_ELVEN_TORCH.get()).func_176223_P(), 1));
   }

   public Biome getRiver(IWorld world) {
      return null;
   }

   public static class Hills extends RivendellBiome {
      public Hills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(3.7F).func_205420_b(1.0F).func_205414_c(0.6F).func_205417_d(0.9F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setFillerDepth(0.0D);
         config.addSubSoilLayer(((Block)LOTRBlocks.DIRTY_CHALK.get()).func_176223_P(), 1);
         config.addSubSoilLayer(((Block)LOTRBlocks.CHALK.get()).func_176223_P(), 5);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.pine(), 5000, LOTRBiomeFeatures.pineShrub(), 3000, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.larch(), 2000, LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.aspenLarge(), 500, LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakFancy(), 500, LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 5, LOTRBiomeFeatures.birch(), 1000, LOTRBiomeFeatures.birchFancy(), 500, LOTRBiomeFeatures.birchBees(), 10, LOTRBiomeFeatures.birchFancyBees(), 5};
         LOTRBiomeFeatures.addTreesIncrease(this, builder, 7, 0.3F, 3, weightedTrees);
         LOTRBiomeFeatures.addGrass(this, builder, 5, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
         LOTRBiomeFeatures.addMountainsFlowers(builder, 2);
      }

      protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addLiquidSprings(builder);

         for(int i = 0; i < 2; ++i) {
            LOTRBiomeFeatures.addWaterSprings(builder, 80);
         }

      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
      }

      protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      }
   }
}
