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

public class SouthronCoastsBiome extends LOTRBiomeBase {
   public SouthronCoastsBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.1F).func_205414_c(1.2F).func_205417_d(0.7F), major);
   }

   protected SouthronCoastsBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setGrass(11914805);
      this.biomeColors.setFog(16248281);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.25D, 0.07D, 0.002D).threshold(0.13D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.25D, 0.07D, 0.002D).threshold(0.3D).state(Blocks.field_150354_m), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.25D, 0.07D, 0.002D).threshold(0.53D).state(Blocks.field_196611_F)));
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addLapisOre(builder);
   }

   protected final Object[] southronCoastsTrees() {
      return new Object[]{LOTRBiomeFeatures.cedar(), 8000, LOTRBiomeFeatures.oakDesert(), 5000, LOTRBiomeFeatures.oakDesertBees(), 50, LOTRBiomeFeatures.cypress(), 4000};
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.2F, TreeCluster.of(8, 24), this.southronCoastsTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addDeadBushes(builder, 1);
      LOTRBiomeFeatures.addHaradFlowers(builder, 3);
      LOTRBiomeFeatures.addHaradDoubleFlowers(builder, 1);
      LOTRBiomeFeatures.addCactiFreq(builder, 1);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addLessCommonReeds(builder);
      LOTRBiomeFeatures.addPapyrus(builder);
      LOTRBiomeFeatures.addSugarCane(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder, 3);
      this.addCaracals(builder, 3);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.HARAD_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HARAD_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.CEDAR_FENCE.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.BRONZE_LANTERN.get()).func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.HARAD_PATH;
   }

   public static class Forest extends SouthronCoastsBiome {
      public Forest(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.4F).func_205414_c(1.0F).func_205417_d(1.0F), major);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTrees(this, builder, 6, 0.5F, LOTRUtil.combineVarargs(this.southronCoastsTrees(), LOTRBiomeFeatures.cedar(), 6000, LOTRBiomeFeatures.cedarLarge(), 1500));
         LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
         LOTRBiomeFeatures.addDeadBushes(builder, 1);
         LOTRBiomeFeatures.addHaradFlowers(builder, 4);
         LOTRBiomeFeatures.addHaradDoubleFlowers(builder, 2);
         LOTRBiomeFeatures.addCactiFreq(builder, 1);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
      }
   }
}
