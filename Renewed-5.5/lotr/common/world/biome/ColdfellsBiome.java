package lotr.common.world.biome;

import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class ColdfellsBiome extends LOTRBiomeBase {
   public ColdfellsBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(0.4F).func_205420_b(0.8F).func_205414_c(0.2F).func_205417_d(0.8F), major);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 4, 8, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 1, 0.5F, LOTRBiomeFeatures.fir(), 5000, LOTRBiomeFeatures.pine(), 5000, LOTRBiomeFeatures.pineDead(), 500, LOTRBiomeFeatures.spruce(), 4000, LOTRBiomeFeatures.spruceThin(), 2000, LOTRBiomeFeatures.spruceDead(), 600, LOTRBiomeFeatures.oak(), 2000, LOTRBiomeFeatures.oakFancy(), 300, LOTRBiomeFeatures.oakBees(), 2, LOTRBiomeFeatures.oakFancyBees(), 1, LOTRBiomeFeatures.larch(), 3000, LOTRBiomeFeatures.maple(), 500, LOTRBiomeFeatures.mapleFancy(), 50, LOTRBiomeFeatures.mapleBees(), 1, LOTRBiomeFeatures.mapleFancyBees(), 1);
      LOTRBiomeFeatures.addGrass(this, builder, 5, GrassBlends.MOORS);
      LOTRBiomeFeatures.addMountainsFlowers(builder, 2);
      LOTRBiomeFeatures.addAthelasChance(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addWolves(builder, 2);
      this.addElk(builder, 1);
      this.addBears(builder, 2);
   }
}
