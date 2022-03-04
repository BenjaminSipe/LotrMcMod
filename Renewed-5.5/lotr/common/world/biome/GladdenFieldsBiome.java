package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class GladdenFieldsBiome extends LOTRBiomeBase {
   public GladdenFieldsBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.SWAMP).func_205421_a(-0.22F).func_205420_b(0.0F).func_205414_c(0.6F).func_205417_d(1.2F), major);
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
      LOTRBiomeFeatures.addQuagmire(builder, 1);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 100, LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakShrub(), 4000, LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 100, LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.larch(), 500};
      LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 2, 0.5F, 63, weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 3, 0.5F, 64, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 8, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addSwampFlowers(builder, 2);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 7, LOTRBlocks.YELLOW_IRIS.get(), 40);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
      LOTRBiomeFeatures.addWaterLiliesWithFlowers(builder, 5);
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
