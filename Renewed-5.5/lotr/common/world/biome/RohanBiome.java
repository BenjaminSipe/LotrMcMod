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

public class RohanBiome extends LOTRBiomeBase {
   public RohanBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.15F).func_205414_c(0.8F).func_205417_d(0.8F), major);
   }

   protected RohanBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.addSubSoilLayer(((Block)LOTRBlocks.ROHAN_ROCK.get()).func_176223_P(), 8, 10);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addGranite(builder);
      LOTRBiomeFeatures.addRohanRockPatches(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.ROHAN_ROCK.get()).func_176223_P(), 1, 4, 64, 3);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 4, 64, 3);
   }

   protected final Object[] rohanTrees() {
      return new Object[]{LOTRBiomeFeatures.oak(), 4000, LOTRBiomeFeatures.oakFancy(), 8000, LOTRBiomeFeatures.oakBees(), 40, LOTRBiomeFeatures.oakFancyBees(), 80, LOTRBiomeFeatures.birch(), 200, LOTRBiomeFeatures.birchFancy(), 100, LOTRBiomeFeatures.birchBees(), 2, LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechFancy(), 100, LOTRBiomeFeatures.beechBees(), 2, LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.pine(), 200, LOTRBiomeFeatures.apple(), 20, LOTRBiomeFeatures.pear(), 20, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.01F, TreeCluster.of(12, 80), this.rohanTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 15, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 5, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 4);
      LOTRBiomeFeatures.addSimbelmyneChance(builder, 60);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder, 6);
      this.addBears(builder);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.ROHAN_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ROHAN_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150463_bK.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150478_aa.func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.ROHAN;
   }

   public static class Wold extends RohanBiome {
      public Wold(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.4F).func_205420_b(0.3F).func_205414_c(0.7F).func_205417_d(0.6F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         config.setFillerDepth(1.0D);
         config.addSubSoilLayer(((Block)LOTRBlocks.DIRTY_CHALK.get()).func_176223_P(), 1);
         config.addSubSoilLayer(((Block)LOTRBlocks.CHALK.get()).func_176223_P(), 2);
         config.addSubSoilLayer(((Block)LOTRBlocks.ROHAN_ROCK.get()).func_176223_P(), 4, 6);
         config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.005D).threshold(0.3D).state(Blocks.field_196660_k).topOnly()));
      }

      protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addGranite(builder);
         LOTRBiomeFeatures.addDiorite(builder);
         LOTRBiomeFeatures.addRohanRockPatches(builder);
      }

      protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.ROHAN_ROCK.get()).func_176223_P(), 1, 3, 120, 3);
         LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 120, 3);
         LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.ROHAN_ROCK.get()).func_176223_P(), 0, 0, 6, 8);
         LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 0, 0, 6, 8);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         Object[] weightedTrees = LOTRUtil.combineVarargs(this.rohanTrees(), LOTRBiomeFeatures.oakDead(), 4000, LOTRBiomeFeatures.beechDead(), 4000);
         LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.008F, TreeCluster.of(8, 100), weightedTrees);
         LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
         LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
      }
   }
}
