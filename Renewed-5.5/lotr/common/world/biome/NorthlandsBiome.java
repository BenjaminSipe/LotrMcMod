package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class NorthlandsBiome extends LOTRBiomeBase {
   public NorthlandsBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.SNOW).func_205419_a(Category.ICY).func_205421_a(0.1F).func_205420_b(0.2F).func_205414_c(0.1F).func_205417_d(0.3F), major);
   }

   protected NorthlandsBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void setupBiomeAmbience(net.minecraft.world.biome.BiomeAmbience.Builder builder) {
      super.setupBiomeAmbience(builder);
      builder.func_242537_a(LOTRGrassColorModifiers.NORTHLANDS);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.07D).threshold(0.4D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.07D).threshold(0.6D).state(Blocks.field_150348_b)));
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 80, 1);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 80, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.04F, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruce()), 600, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceThin()), 400, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceDead()), 1000, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.pine()), 500, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.pineDead()), 500, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.fir()), 1000, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.maple()), 100, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.beech()), 100);
      LOTRBiomeFeatures.addGrass(this, builder, 4, GrassBlends.MOORS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MOORS);
      LOTRBiomeFeatures.addBorealFlowers(builder, 2);
      LOTRBiomeFeatures.addTundraBushesChance(builder, 2, (new WeightedBlockStateProvider()).func_227407_a_((BlockState)Blocks.field_196645_X.func_176223_P().func_206870_a(LeavesBlock.field_208495_b, true), 3).func_227407_a_((BlockState)((Block)LOTRBlocks.MAPLE_LEAVES.get()).func_176223_P().func_206870_a(LeavesBlock.field_208495_b, true), 3).func_227407_a_((BlockState)((Block)LOTRBlocks.BEECH_LEAVES.get()).func_176223_P().func_206870_a(LeavesBlock.field_208495_b, true), 3), 16);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addWolves(builder, 2);
      this.addDeer(builder, 1);
      this.addElk(builder, 2);
      this.addBears(builder, 3);
      this.addFoxes(builder, 2);
   }

   public boolean doesSnowGenerate(boolean defaultDoesSnowGenerate, IWorldReader world, BlockPos pos) {
      return defaultDoesSnowGenerate && (LOTRBiomeWrapper.isSnowBlockBelow(world, pos) || this.isTundraSnowy(pos));
   }

   protected boolean isTundraSnowy(BlockPos pos) {
      return this.getSnowVarietyNoise(pos) > 0.8D;
   }

   protected final double getSnowVarietyNoise(BlockPos pos) {
      int x = pos.func_177958_n();
      int z = pos.func_177952_p();
      double d1 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.002D, (double)z * 0.002D, false);
      double d2 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.05D, (double)z * 0.05D, false);
      double d3 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.3D, (double)z * 0.3D, false);
      d2 *= 0.3D;
      d3 *= 0.3D;
      return d1 + d2 + d3;
   }

   public static class DenseSnowyForest extends NorthlandsBiome.SnowyForest {
      public DenseSnowyForest(boolean major) {
         super(major);
      }

      protected boolean isDenseForest() {
         return true;
      }
   }

   public static class SnowyForest extends NorthlandsBiome.Forest {
      public SnowyForest(boolean major) {
         super((new Builder()).func_205415_a(RainType.SNOW).func_205419_a(Category.FOREST).func_205421_a(0.1F).func_205420_b(0.5F).func_205414_c(0.05F).func_205417_d(0.4F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.07D).threshold(0.4D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.07D).threshold(0.6D).state(Blocks.field_150348_b), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.3D, 0.07D).threshold(-0.3D).state(Blocks.field_196604_cC).topOnly()));
      }

      protected boolean isTundraSnowy(BlockPos pos) {
         return this.getSnowVarietyNoise(pos) > -0.4D;
      }
   }

   public static class SnowyNorthlands extends NorthlandsBiome {
      public SnowyNorthlands(boolean major) {
         super((new Builder()).func_205415_a(RainType.SNOW).func_205419_a(Category.ICY).func_205421_a(0.1F).func_205420_b(0.2F).func_205414_c(0.05F).func_205417_d(0.2F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.07D).threshold(0.4D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.07D).threshold(0.6D).state(Blocks.field_150348_b), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.3D, 0.07D).threshold(-0.6D).state(Blocks.field_196604_cC).topOnly()));
      }

      protected boolean isTundraSnowy(BlockPos pos) {
         return true;
      }
   }

   public static class DenseForest extends NorthlandsBiome.Forest {
      public DenseForest(boolean major) {
         super(major);
      }

      protected boolean isDenseForest() {
         return true;
      }
   }

   public static class Forest extends NorthlandsBiome {
      public Forest(boolean major) {
         this((new Builder()).func_205415_a(RainType.SNOW).func_205419_a(Category.FOREST).func_205421_a(0.1F).func_205420_b(0.5F).func_205414_c(0.1F).func_205417_d(0.7F), major);
      }

      protected Forest(Builder builder, boolean major) {
         super(builder, major);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         if (this.isDenseForest()) {
            LOTRBiomeFeatures.addTrees(this, builder, 5, 0.5F, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruce()), 200, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceThin()), 100, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceMega()), 2000, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceThinMega()), 200, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceDead()), 200, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.pine()), 700, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.pineDead()), 200, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.fir()), 500, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceShrub()), 500);
         } else {
            LOTRBiomeFeatures.addTrees(this, builder, 2, 0.8F, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruce()), 2000, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceThin()), 1000, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceDead()), 500, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.pine()), 2000, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.pineDead()), 400, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.fir()), 2000, LOTRBiomeFeatures.snowWrapTree(LOTRBiomeFeatures.spruceShrub()), 600);
         }

         LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.MOORS_WITH_FERNS);
         LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_MOORS_WITH_FERNS);
         LOTRBiomeFeatures.addBorealFlowers(builder, 2);
         LOTRBiomeFeatures.addSparseFoxBerryBushes(builder);
      }

      protected boolean isDenseForest() {
         return false;
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
      }
   }
}
