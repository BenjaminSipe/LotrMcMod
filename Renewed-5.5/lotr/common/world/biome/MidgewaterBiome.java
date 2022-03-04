package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class MidgewaterBiome extends LOTRBiomeBase {
   private static final int MIDGEWATER_WATER_COLOR = 5855807;

   public MidgewaterBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.SWAMP).func_205421_a(-0.22F).func_205420_b(0.0F).func_205414_c(0.6F).func_205417_d(1.0F), 5855807, major);
      this.biomeColors.setGrass(7962434).setFoliage(8154931).setSky(13560554).setFog(14211254).setFoggy(true).setWater(5855807);
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

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addQuagmire(builder, 1);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 100, LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakShrub(), 2500, LOTRBiomeFeatures.oakDead(), 500};
      LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 2, 0.5F, 63, weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 3, 0.5F, 64, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addSwampFlowers(builder, 1);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
      LOTRBiomeFeatures.addWaterLilies(builder, 5);
      LOTRBiomeFeatures.addSwampSeagrass(builder);
      LOTRBiomeFeatures.addFallenLogs(builder, 3);
      LOTRBiomeFeatures.addAthelasChance(builder);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addReeds(builder);
      LOTRBiomeFeatures.addSwampRushes(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   public Biome getRiver(IWorld world) {
      return null;
   }
}
