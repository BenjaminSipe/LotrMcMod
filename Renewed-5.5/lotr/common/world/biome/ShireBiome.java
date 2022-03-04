package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.util.LOTRUtil;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
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

public class ShireBiome extends LOTRBiomeBase {
   public ShireBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.15F).func_205420_b(0.3F).func_205414_c(0.8F).func_205417_d(0.7F), major);
   }

   protected ShireBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setGrass(8111137);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected final Object[] shireTrees() {
      return new Object[]{LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 4000, LOTRBiomeFeatures.oakBees(), 100, LOTRBiomeFeatures.oakFancyBees(), 40, LOTRBiomeFeatures.oakParty(), 200, LOTRBiomeFeatures.birch(), 250, LOTRBiomeFeatures.birchFancy(), 100, LOTRBiomeFeatures.birchBees(), 2, LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.pearBees(), 1, LOTRBiomeFeatures.cherry(), 20, LOTRBiomeFeatures.cherryBees(), 1};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.2F, TreeCluster.of(10, 6), this.shireTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.SHIRE);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 3);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
      LOTRBiomeFeatures.addSunflowers(builder, 6);
      LOTRBiomeFeatures.addWildPipeweedChance(builder, 6);
   }

   protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addWaterSprings(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_220356_B, 5, 2, 4));
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.HOBBIT_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.PINE_PLANKS.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.PINE_FENCE.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_222432_lU.func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.PATH.withStandardHedge();
   }

   public static class Marshes extends ShireBiome {
      public Marshes(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.SWAMP).func_205421_a(-0.22F).func_205420_b(0.0F).func_205414_c(0.8F).func_205417_d(1.2F), major);
         this.biomeColors.resetGrass();
      }

      public float getStrengthOfAddedDepthNoise() {
         return 0.15F;
      }

      public float getBiomeScaleSignificanceForChunkGen() {
         return 0.96F;
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setMarsh(true);
      }

      protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addQuagmire(builder, 1);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 100, LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakShrub(), 2500};
         LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 2, 0.5F, 63, weightedTrees);
         LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 3, 0.5F, 64, weightedTrees);
         LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.MUTED_WITH_FERNS);
         LOTRBiomeFeatures.addDoubleGrass(builder, 8, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
         LOTRBiomeFeatures.addSwampFlowers(builder, 4);
         LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
         LOTRBiomeFeatures.addWaterLiliesWithRareFlowers(builder, 4);
         LOTRBiomeFeatures.addSwampSeagrass(builder);
         LOTRBiomeFeatures.addFallenLogs(builder, 2);
         LOTRBiomeFeatures.addWildPipeweedChance(builder, 3);
      }

      protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addReeds(builder);
         LOTRBiomeFeatures.addMoreSwampReeds(builder);
         LOTRBiomeFeatures.addSwampRushes(builder);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      }

      protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      }

      public Biome getRiver(IWorld world) {
         return null;
      }
   }

   public static class WhiteDowns extends ShireBiome {
      public WhiteDowns(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.7F).func_205420_b(0.6F).func_205414_c(0.6F).func_205417_d(0.8F), major);
         this.biomeColors.resetGrass();
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setFillerDepth(0.0D);
         config.addSubSoilLayer(((Block)LOTRBlocks.DIRTY_CHALK.get()).func_176223_P(), 1);
         config.addSubSoilLayer(((Block)LOTRBlocks.CHALK.get()).func_176223_P(), 4);
      }

      protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addStoneVariants(builder);
         LOTRBiomeFeatures.addDiorite(builder);
      }

      protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addBoulders(builder);
         LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 80, 3);
         LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.CHALK.get()).func_176223_P(), 1, 1, 16, 3);
         LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.CHALK.get()).func_176223_P(), 2, 3, 32, 2);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTrees(this, builder, 0, 0.08F, this.shireTrees());
         LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.SHIRE_MOORS);
         LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MOORS);
         LOTRBiomeFeatures.addPlainsFlowers(builder, 2, LOTRBlocks.SHIRE_HEATHER.get(), 20);
         LOTRBiomeFeatures.addWildPipeweedChance(builder, 12);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
      }

      public RoadBlockProvider getRoadBlockProvider() {
         return RoadBlockProvider.CHALK_PATH;
      }
   }

   public static class Moors extends ShireBiome {
      public Moors(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.6F).func_205420_b(0.6F).func_205414_c(0.6F).func_205417_d(1.0F), major);
         this.biomeColors.resetGrass();
      }

      protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addBoulders(builder);
         LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 8, 4);
         LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 3, 5, 40, 3);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTrees(this, builder, 0, 0.1F, LOTRUtil.combineVarargs(this.shireTrees(), LOTRBiomeFeatures.oakFancy(), 80000, LOTRBiomeFeatures.oakFancyBees(), 800));
         LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.SHIRE_MOORS);
         LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MOORS);
         LOTRBiomeFeatures.addPlainsFlowers(builder, 10, LOTRBlocks.SHIRE_HEATHER.get(), 100);
         LOTRBiomeFeatures.addWildPipeweedChance(builder, 12);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
      }
   }
}
