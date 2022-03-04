package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import net.minecraft.block.Block;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class GorgorothBiome extends BaseMordorBiome {
   private static final int GORGOROTH_WATER_COLOR = 2498845;

   public GorgorothBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.DESERT).func_205421_a(0.7F).func_205420_b(0.4F).func_205414_c(1.5F).func_205417_d(0.0F), 2498845, major);
      this.biomeColors.setGrass(5980459).setFoliage(5987138).setSky(6700595).setClouds(4924185).setFog(3154711);
   }

   protected void setupBiomeAmbience(net.minecraft.world.biome.BiomeAmbience.Builder builder) {
      super.setupBiomeAmbience(builder);
      builder.func_235244_a_(new ParticleEffectAmbience(ParticleTypes.field_239813_am_, 0.025F));
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      super.setupSurface(config);
      config.setTop(((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P());
      config.setFiller(((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P());
      config.resetFillerDepthAndSubSoilLayers();
      config.addSubSoilLayer(((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P(), 1000);
      config.setRockyTerrain(false);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.08D).threshold(0.25D).state(LOTRBlocks.MORDOR_DIRT), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.4D, 0.08D).threshold(0.4D).state(LOTRBlocks.MORDOR_GRAVEL)));
   }

   protected void addFeatures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addFeatures(builder);
      LOTRBiomeFeatures.addMordorBasalt(builder, 1, 6);
   }

   protected void addCarvers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCarversExtraCanyons(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, ((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P(), 2, 6, 30, 4);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesIncrease(this, builder, 0, 0.0125F, 7, LOTRBiomeFeatures.charred(), 1);
      LOTRBiomeFeatures.addMordorMoss(builder, 20);
      LOTRBiomeFeatures.addMordorGrass(builder, 2);
      LOTRBiomeFeatures.addMordorThorns(builder, 40);
      LOTRBiomeFeatures.addMorgulShrooms(builder, 32);
   }

   protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addWaterSprings(builder);
      LOTRBiomeFeatures.addLavaSprings(builder, 50);
   }
}
