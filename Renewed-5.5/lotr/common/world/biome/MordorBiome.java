package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
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

public class MordorBiome extends BaseMordorBiome {
   private static final int MORDOR_WATER_COLOR = 3884089;

   public MordorBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.DESERT).func_205421_a(0.3F).func_205420_b(0.5F).func_205414_c(1.5F).func_205417_d(0.0F), major);
   }

   protected MordorBiome(Builder builder, boolean major) {
      super(builder, 3884089, major);
      this.biomeColors.setGrass(7496538).setFoliage(5987138).setSky(6313301).setClouds(6705223).setFog(6701621);
   }

   protected void setupBiomeAmbience(net.minecraft.world.biome.BiomeAmbience.Builder builder) {
      super.setupBiomeAmbience(builder);
      builder.func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239813_am_, 0.01F));
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      super.setupSurface(config);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.06D).threshold(0.1D).state(LOTRBlocks.MORDOR_ROCK), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.06D).threshold(0.5D).state(Blocks.field_196660_k), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.3D, 0.06D).threshold(0.2D).state(LOTRBlocks.MORDOR_DIRT), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(4).scales(0.3D, 0.06D).threshold(0.25D).state(LOTRBlocks.MORDOR_GRAVEL)));
   }

   protected void addFeatures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addFeatures(builder);
      this.addBasalt(builder);
   }

   protected void addBasalt(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addMordorBasalt(builder, 16, 40);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P(), 1, 3, 60, 3);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 60, 3);
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P(), 0, 1, 12, 3);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 0, 1, 24, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesBelowTreelineIncrease(this, builder, 0, 0.025F, 5, 100, LOTRBiomeFeatures.charred(), 100, LOTRBiomeFeatures.oakDead(), 50, LOTRBiomeFeatures.oakDesert(), 20);
      LOTRBiomeFeatures.addGrass(this, builder, 2, GrassBlends.MUTED_WITHOUT_THISTLES);
      LOTRBiomeFeatures.addMordorMoss(builder, 20);
      LOTRBiomeFeatures.addMordorGrass(builder, 4);
      LOTRBiomeFeatures.addMordorThorns(builder, 40);
      LOTRBiomeFeatures.addMorgulShrooms(builder, 32);
   }

   public static class Mountains extends MordorBiome {
      public Mountains(boolean major) {
         super((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.EXTREME_HILLS).func_205421_a(2.0F).func_205420_b(2.0F).func_205414_c(1.5F).func_205417_d(0.0F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setRockyTerrain(false);
         config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.06D).threshold(0.0D).state(LOTRBlocks.MORDOR_ROCK), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.06D).threshold(0.65D).state(Blocks.field_196660_k), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.3D, 0.06D).threshold(0.65D).state(LOTRBlocks.MORDOR_DIRT), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(4).scales(0.3D, 0.06D).threshold(0.1D).state(LOTRBlocks.MORDOR_GRAVEL)));
         config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(100).state(LOTRBlocks.MORDOR_ROCK)));
      }

      protected void addBasalt(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      }

      public Biome getRiver(IWorld world) {
         return null;
      }
   }
}
