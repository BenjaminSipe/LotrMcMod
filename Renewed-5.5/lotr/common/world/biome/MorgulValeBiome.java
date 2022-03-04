package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class MorgulValeBiome extends BaseMordorBiome {
   private static final int MORGUL_WATER_COLOR = 3563598;

   public MorgulValeBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.PLAINS).func_205421_a(0.1F).func_205420_b(0.1F).func_205414_c(0.8F).func_205417_d(0.4F), 3563598, major);
      this.biomeColors.setGrass(6054733).setFoliage(4475954).setSky(7835270).setClouds(5860197).setFog(6318950);
   }

   protected void setupBiomeAmbience(net.minecraft.world.biome.BiomeAmbience.Builder builder) {
      super.setupBiomeAmbience(builder);
      builder.func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239813_am_, 0.01F));
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      super.setupSurface(config);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.06D).threshold(0.35D).state(LOTRBlocks.MORDOR_DIRT), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.06D).threshold(0.35D).state(LOTRBlocks.MORDOR_GRAVEL), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.3D, 0.06D).threshold(0.55D).state(LOTRBlocks.MORDOR_ROCK)));
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addExtraMordorGulduril(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P(), 0, 1, 48, 3);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 0, 1, 48, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.2F, LOTRBiomeFeatures.oak(), 2000, LOTRBiomeFeatures.oakDesert(), 5000, LOTRBiomeFeatures.oakDead(), 5000, LOTRBiomeFeatures.charred(), 5000);
      LOTRBiomeFeatures.addGrass(this, builder, 3, GrassBlends.MUTED);
      LOTRBiomeFeatures.addFlowers(builder, 1, LOTRBlocks.MORGUL_FLOWER.get(), 20);
      LOTRBiomeFeatures.addExtraMorgulFlowersByWater(builder, 4);
      LOTRBiomeFeatures.addMordorMoss(builder, 40);
      LOTRBiomeFeatures.addMordorGrass(builder, 4);
      LOTRBiomeFeatures.addMordorThorns(builder, 40);
      LOTRBiomeFeatures.addMorgulShrooms(builder, 32);
   }
}
