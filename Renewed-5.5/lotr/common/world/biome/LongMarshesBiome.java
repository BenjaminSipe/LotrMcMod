package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class LongMarshesBiome extends LOTRBiomeBase {
   private static final int LONG_MARSHES_WATER_COLOR = 8167049;

   public LongMarshesBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.SWAMP).func_205421_a(-0.22F).func_205420_b(0.0F).func_205414_c(0.5F).func_205417_d(0.9F), 8167049, major);
      this.biomeColors.setSky(13230818).setFog(12112325).setFoggy(true).setWater(8167049);
   }

   public float getStrengthOfAddedDepthNoise() {
      return 0.15F;
   }

   public float getBiomeScaleSignificanceForChunkGen() {
      return 0.96F;
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setMarsh(true);
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addQuagmire(builder, 2);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 100, LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakShrub(), 3000, LOTRBiomeFeatures.oakDead(), 500, LOTRBiomeFeatures.spruce(), 500, LOTRBiomeFeatures.spruceDead(), 500, LOTRBiomeFeatures.fir(), 200, LOTRBiomeFeatures.pine(), 200};
      LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 1, 0.5F, 63, weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 3, 0.5F, 64, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 12, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addSwampFlowers(builder, 2);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
      LOTRBiomeFeatures.addWaterLilies(builder, 4);
      LOTRBiomeFeatures.addSwampSeagrass(builder);
      LOTRBiomeFeatures.addFallenLogs(builder, 1);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addReeds(builder);
      LOTRBiomeFeatures.addMoreSwampReeds(builder);
      LOTRBiomeFeatures.addSwampRushes(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   public Biome getRiver(IWorld world) {
      return null;
   }
}
