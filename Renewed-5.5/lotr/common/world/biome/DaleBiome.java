package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class DaleBiome extends LOTRBiomeBase {
   public DaleBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.1F).func_205420_b(0.4F).func_205414_c(0.6F).func_205417_d(0.7F), major);
   }

   protected DaleBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 80, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakDead(), 500, LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 10, LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.spruceDead(), 500, LOTRBiomeFeatures.maple(), 500, LOTRBiomeFeatures.mapleBees(), 5, LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.pineDead(), 200, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1};
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.05F, TreeCluster.of(8, 16), weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreelineIncrease(this, builder, 2, 0.3F, 3, 82, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder, 1);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.DALE_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DALE_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_222459_lw.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_222432_lU.func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.DALE;
   }

   public static class Northern extends DaleBiome {
      private static final float SNOWY_TEMP = 0.15F;

      public Northern(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.5F).func_205414_c(0.5F).func_205417_d(0.7F), major);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 100, LOTRBiomeFeatures.beechDead(), 500, LOTRBiomeFeatures.beechBees(), 10, LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.spruceDead(), 500, LOTRBiomeFeatures.maple(), 500, LOTRBiomeFeatures.mapleBees(), 5, LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.pine(), 500, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1};
         LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.05F, TreeCluster.of(8, 40), weightedTrees);
         LOTRBiomeFeatures.addTreesAboveTreelineIncrease(this, builder, 2, 0.3F, 2, 82, weightedTrees);
         LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
         LOTRBiomeFeatures.addBorealFlowers(builder, 2);
      }

      public float getTemperatureRaw(float defaultTemperatureAtPos, BlockPos pos) {
         return defaultTemperatureAtPos - this.getLocalSnowiness(pos) * (this.getActualBiome().func_242445_k() - 0.15F);
      }

      private float getLocalSnowiness(BlockPos pos) {
         int x = pos.func_177958_n();
         int z = pos.func_177952_p();
         double d1 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.002D, (double)z * 0.002D, false);
         double d2 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.05D, (double)z * 0.05D, false);
         double d3 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.3D, (double)z * 0.3D, false);
         d1 *= 0.6D;
         d2 *= 0.2D;
         d3 *= 0.2D;
         float biased = (float)Math.max(d1 + d2 + d3, 0.0D) + 0.5F;
         return MathHelper.func_76131_a(biased, 0.0F, 1.0F);
      }
   }
}
