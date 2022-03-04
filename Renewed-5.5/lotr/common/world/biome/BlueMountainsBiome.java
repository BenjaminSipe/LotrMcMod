package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
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

public class BlueMountainsBiome extends LOTRBiomeBase {
   public BlueMountainsBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(1.0F).func_205420_b(2.2F).func_205414_c(0.22F).func_205417_d(0.8F), major);
   }

   protected BlueMountainsBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setSky(7506425);
   }

   protected boolean isFoothills() {
      return false;
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      if (!this.isFoothills()) {
         config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(110).state(Blocks.field_196604_cC).topOnly(), MountainTerrainProvider.MountainLayer.layerBuilder().above(90).state(LOTRBlocks.BLUE_ROCK).excludeStone()));
      }

   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addAndesite(builder);
      LOTRBiomeFeatures.addDeepDiorite(builder);
      LOTRBiomeFeatures.addBlueRockPatches(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addExtraCoal(builder, 8, 10, 128);
      LOTRBiomeFeatures.addExtraIron(builder, 4, 10, 96);
      LOTRBiomeFeatures.addGlowstoneOre(builder);
   }

   protected void addCobwebs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.4F, TreeCluster.of(10, 12), LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.spruceDead(), 50, LOTRBiomeFeatures.birch(), 4000, LOTRBiomeFeatures.pine(), 5000, LOTRBiomeFeatures.pineDead(), 50, LOTRBiomeFeatures.fir(), 5000);
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.MUTED);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MUTED);
      LOTRBiomeFeatures.addMountainsFlowers(builder, 1, LOTRBlocks.DWARFWORT.get(), 1);
   }

   protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      if (!this.isFoothills()) {
         LOTRBiomeFeatures.addWaterLavaSpringsReducedAboveground(builder, 80, 0.15F);
      } else {
         super.addLiquidSprings(builder);
      }

   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.BLUE_MOUNTAINS_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.BLUE_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.BLUE_BRICK_WALL.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_222432_lU.func_176223_P(), 1));
   }

   public Biome getRiver(IWorld world) {
      return this.isFoothills() ? super.getRiver(world) : null;
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.DWARVEN;
   }

   public static class Foothills extends BlueMountainsBiome {
      public Foothills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(0.5F).func_205420_b(0.9F).func_205414_c(0.5F).func_205417_d(0.8F), major);
      }

      protected boolean isFoothills() {
         return true;
      }
   }
}
