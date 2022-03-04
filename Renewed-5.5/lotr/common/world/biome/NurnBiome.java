package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
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

public class NurnBiome extends LOTRBiomeBase {
   private static final int NURN_WATER_COLOR = 4413266;

   public NurnBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.1F).func_205420_b(0.2F).func_205414_c(0.8F).func_205417_d(0.5F), major);
   }

   protected NurnBiome(Builder builder, boolean major) {
      super(builder, 4413266, major);
      this.biomeColors.setGrass(10068025).setFoliage(7504951).setSky(10404589).setClouds(9342083).setWater(4413266);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setFillerDepth(2.0D);
      config.addSubSoilLayer(((Block)LOTRBlocks.MORDOR_DIRT.get()).func_176223_P(), 3);
      config.addSubSoilLayer(((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P(), 1000);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.4D, 0.08D).threshold(0.4D).state(Blocks.field_196660_k).topOnly()));
   }

   protected void addDirtGravel(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addMordorDirtGravel(builder);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addMordorOres(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P(), 1, 3, 40, 4);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 4, 60, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.25F, TreeCluster.of(6, 30), LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakDesert(), 5000, LOTRBiomeFeatures.oakDead(), 2000, LOTRBiomeFeatures.cedar(), 1000, LOTRBiomeFeatures.charred(), 2000);
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.EXTRA_WHEATGRASS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_WITH_EXTRA_WHEATGRASS);
      LOTRBiomeFeatures.addDefaultFlowers(builder, 1);
      LOTRBiomeFeatures.addMordorGrass(builder, 2);
      LOTRBiomeFeatures.addMordorThorns(builder, 200);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addReedsWithDriedChance(builder, 0.6F);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.MORDOR_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_BRICK_WALL.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ORC_TORCH.get()).func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.NURN_PATH;
   }

   public static class Marshes extends NurnBiome {
      public Marshes(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.SWAMP).func_205421_a(-0.22F).func_205420_b(0.0F).func_205414_c(0.7F).func_205417_d(0.8F), major);
         this.biomeColors.setGrass(8291139);
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
         Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 100, LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakDead(), 1500, LOTRBiomeFeatures.oakShrub(), 6000};
         LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 2, 0.5F, 63, weightedTrees);
         LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 3, 0.5F, 64, weightedTrees);
         LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.MUTED_WITH_FERNS);
         LOTRBiomeFeatures.addDoubleGrass(builder, 10, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
         LOTRBiomeFeatures.addSwampFlowers(builder, 2);
         LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 4);
         LOTRBiomeFeatures.addSwampSeagrass(builder);
         LOTRBiomeFeatures.addFallenLogs(builder, 2);
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

   public static class Sea extends NurnBiome {
      public Sea(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.OCEAN).func_205421_a(-1.0F).func_205420_b(0.3F).func_205414_c(0.8F).func_205417_d(0.5F), major);
      }

      public Biome getRiver(IWorld world) {
         return null;
      }
   }
}
