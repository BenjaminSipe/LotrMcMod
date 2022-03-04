package lotr.common.world.biome;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class DorEnErnilBiome extends LOTRBiomeBase {
   public DorEnErnilBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.04F).func_205420_b(0.28F).func_205414_c(0.9F).func_205417_d(0.9F), major);
   }

   protected DorEnErnilBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.addSubSoilLayer(((Block)LOTRBlocks.GONDOR_ROCK.get()).func_176223_P(), 8, 10);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addGranite(builder);
      LOTRBiomeFeatures.addGondorRockPatches(builder);
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addWhiteSandSediments(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.GONDOR_ROCK.get()).func_176223_P(), 1, 2, 200, 3);
   }

   protected final Object[] dorEnErnilTrees() {
      return new Object[]{LOTRBiomeFeatures.birch(), 2000, LOTRBiomeFeatures.birchFancy(), 2000, LOTRBiomeFeatures.birchBees(), 20, LOTRBiomeFeatures.birchFancyBees(), 20, LOTRBiomeFeatures.oak(), 800, LOTRBiomeFeatures.oakTall(), 800, LOTRBiomeFeatures.oakFancy(), 800, LOTRBiomeFeatures.oakBees(), 8, LOTRBiomeFeatures.oakTallBees(), 8, LOTRBiomeFeatures.oakFancyBees(), 8, LOTRBiomeFeatures.cedar(), 1000, LOTRBiomeFeatures.cypress(), 500, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.03F, TreeCluster.of(10, 30), this.dorEnErnilTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 4);
      LOTRBiomeFeatures.addAthelasChance(builder);
      LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addReeds(builder);
      LOTRBiomeFeatures.addSugarCane(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder, 6);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.DOL_AMROTH_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DOL_AMROTH_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DOL_AMROTH_BRICK_WALL.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_222432_lU.func_176223_P(), 1));
   }

   public LOTRBiomeBase getShore() {
      return LOTRBiomes.WHITE_BEACH.getInitialisedBiomeWrapper();
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.DOL_AMROTH;
   }

   public static class Hills extends DorEnErnilBiome {
      public Hills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.7F).func_205420_b(0.7F).func_205414_c(0.7F).func_205417_d(0.9F), major);
      }

      protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addStoneVariants(builder);
         LOTRBiomeFeatures.addDiorite(builder);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.65F, TreeCluster.of(10, 10), this.dorEnErnilTrees());
         LOTRBiomeFeatures.addGrass(this, builder, 5, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
         LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
         LOTRBiomeFeatures.addAthelasChance(builder);
         LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
      }

      public Biome getRiver(IWorld world) {
         return null;
      }
   }
}
