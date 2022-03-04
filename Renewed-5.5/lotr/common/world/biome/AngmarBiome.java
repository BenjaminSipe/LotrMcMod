package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class AngmarBiome extends LOTRBiomeBase {
   public AngmarBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.6F).func_205414_c(0.2F).func_205417_d(0.3F), major);
   }

   protected AngmarBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setGrass(7896151).setSky(5654333).setClouds(3815994).setFog(3815994);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.07D).threshold(0.25D).state(Blocks.field_150348_b)));
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addStoneOrcishOres(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 6, 1);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.spruceThin(), 1000, LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.spruceDead(), 1000, LOTRBiomeFeatures.charred(), 1000, LOTRBiomeFeatures.fir(), 1000, LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.pineDead(), 500};
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.25F, weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 4, 0.1F, 80, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 4, GrassBlends.MUTED);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MUTED);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addWolves(builder, 2);
      this.addBears(builder, 1);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.ANGMAR_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ANGMAR_BRICK.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_ANGMAR_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_ANGMAR_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.CRACKED_STONE_BRICK_WALL.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ORC_TORCH.get()).func_176223_P(), 1));
   }

   public static class Mountains extends AngmarBiome {
      public Mountains(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(1.6F).func_205420_b(1.5F).func_205414_c(0.25F).func_205417_d(0.3F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(130).state(Blocks.field_196604_cC).topOnly(), MountainTerrainProvider.MountainLayer.layerBuilder().above(110).useStone()));
      }

      protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         super.addStoneVariants(builder);
         LOTRBiomeFeatures.addDeepDiorite(builder);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      }

      public Biome getRiver(IWorld world) {
         return null;
      }
   }
}
