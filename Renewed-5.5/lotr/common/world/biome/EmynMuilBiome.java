package lotr.common.world.biome;

import com.google.common.collect.ImmutableList;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class EmynMuilBiome extends LOTRBiomeBase {
   public EmynMuilBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.DESERT).func_205421_a(0.2F).func_205420_b(0.8F).func_205414_c(0.5F).func_205417_d(0.9F), major);
      this.biomeColors.setGrass(9539937);
      this.biomeColors.setSky(10000788);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setTop(Blocks.field_150348_b.func_176223_P());
      config.setFiller(Blocks.field_150348_b.func_176223_P());
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 4, 1, 4, 3);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 5, 8, 1, 4, 6);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_196650_c.func_176223_P(), 1, 4, 1, 4, 3);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_196650_c.func_176223_P(), 5, 8, 1, 4, 6);
      LOTRBiomeFeatures.addTerrainSharpener(builder, ImmutableList.of(Blocks.field_150348_b.func_176223_P(), Blocks.field_196650_c.func_176223_P()), 1, 3, 10);
      LOTRBiomeFeatures.addGrassPatches(builder, ImmutableList.of(Blocks.field_150348_b.func_176223_P(), Blocks.field_196650_c.func_176223_P()), 1, 5, 4, 5, 5);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 1, 0.5F, LOTRBiomeFeatures.oakDesert(), 200, LOTRBiomeFeatures.oakDead(), 800);
      LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addMountainsFlowers(builder, 1);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }
}
