package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class EasternDesolationBiome extends MordorBiome {
   public EasternDesolationBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.NONE).func_205419_a(Category.DESERT).func_205421_a(0.2F).func_205420_b(0.2F).func_205414_c(0.8F).func_205417_d(0.3F), major);
      this.biomeColors.setGrass(8880748);
      this.biomeColors.setSky(9538431);
      this.biomeColors.resetClouds().setCloudCoverage(0.6F);
      this.biomeColors.resetFog();
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      super.setupSurface(config);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.06D).threshold(0.3D).state(Blocks.field_196660_k), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.06D).threshold(0.25D).state(LOTRBlocks.MORDOR_DIRT), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(3).scales(0.3D, 0.06D).threshold(0.35D).state(LOTRBlocks.MORDOR_GRAVEL)));
   }

   protected void addBasalt(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   public ExtendedWeatherType getBiomeExtendedWeather() {
      return ExtendedWeatherType.NONE;
   }
}
