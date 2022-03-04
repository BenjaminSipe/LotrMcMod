package lotr.common.world.biome;

import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class WilderlandBiome extends LOTRBiomeBase {
   public WilderlandBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.4F).func_205414_c(0.9F).func_205417_d(0.4F), major);
   }

   protected WilderlandBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 24, 4);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.05F, TreeCluster.of(8, 20), LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 1, LOTRBiomeFeatures.oakDead(), 5000, LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.spruceDead(), 1000);
      LOTRBiomeFeatures.addGrass(this, builder, 14, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 8, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addRhunPlainsFlowers(builder, 3);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder, 2);
      this.addBears(builder);
   }

   public static class Northern extends WilderlandBiome {
      private static final float SNOWY_TEMP = 0.15F;

      public Northern(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.5F).func_205414_c(0.5F).func_205417_d(0.6F), major);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.05F, TreeCluster.of(8, 40), LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 10, LOTRBiomeFeatures.oakDead(), 5000, LOTRBiomeFeatures.spruce(), 3000, LOTRBiomeFeatures.spruceDead(), 1000, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.pine(), 2000);
         LOTRBiomeFeatures.addGrass(this, builder, 7, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_STANDARD);
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
