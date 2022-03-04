package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class DagorladBiome extends LOTRBiomeBase {
   public DagorladBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.PLAINS).func_205421_a(0.1F).func_205420_b(0.1F).func_205414_c(0.8F).func_205417_d(0.1F), major);
      this.biomeColors.setGrass(9208427);
      this.biomeColors.setSky(6181446);
      this.biomeColors.setClouds(3355443);
      this.biomeColors.setFog(6710886);
      this.biomeColors.setWater(2498845);
   }

   protected void setupBiomeAmbience(net.minecraft.world.biome.BiomeAmbience.Builder builder) {
      super.setupBiomeAmbience(builder);
      builder.func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239813_am_, 0.01F));
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setUnderwater(((Block)LOTRBlocks.MORDOR_GRAVEL.get()).func_176223_P());
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.6D, 0.09D).threshold(0.3D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.6D, 0.09D).threshold(0.15D).state(Blocks.field_150351_n).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.6D, 0.09D).threshold(0.1D).state(LOTRBlocks.MORDOR_GRAVEL), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(4).scales(0.6D, 0.09D).threshold(0.2D).state(LOTRBlocks.MORDOR_DIRT)));
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 40, 3);
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.MORDOR_DIRT.get()).func_176223_P(), 1, 4, 24, 2);
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.MORDOR_GRAVEL.get()).func_176223_P(), 1, 4, 24, 2);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesIncrease(this, builder, 0, 0.0125F, 3, LOTRBiomeFeatures.charred(), 1);
      LOTRBiomeFeatures.addGrass(this, builder, 1, GrassBlends.BASIC);
      LOTRBiomeFeatures.addMordorGrass(builder, 1);
      LOTRBiomeFeatures.addDeadBushAtSurfaceChance(builder, 2);
   }

   protected void addPumpkins(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   protected ExtendedWeatherType getBiomeExtendedWeather() {
      return ExtendedWeatherType.ASHFALL;
   }

   public Biome getRiver(IWorld world) {
      return null;
   }
}
