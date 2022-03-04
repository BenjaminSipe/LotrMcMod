package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class NindalfBiome extends LOTRBiomeBase {
   private static final int NINDALF_WATER_COLOR = 8362365;

   public NindalfBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.SWAMP).func_205421_a(-0.22F).func_205420_b(0.0F).func_205414_c(0.5F).func_205417_d(1.0F), 8362365, major);
      this.biomeColors.setGrass(6519879).setFoliage(6976836).setSky(9740163).setClouds(8687218).setFog(7500388).setWater(8362365);
   }

   public float getStrengthOfAddedDepthNoise() {
      return 0.15F;
   }

   public float getBiomeScaleSignificanceForChunkGen() {
      return 0.96F;
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setMarsh(true);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.07D).threshold(0.455D).state(Blocks.field_196660_k).topOnly()));
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addQuagmire(builder, 2);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 24, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 200, LOTRBiomeFeatures.oakSwamp(), 600, LOTRBiomeFeatures.oakShrub(), 10000, LOTRBiomeFeatures.oakDead(), 500, LOTRBiomeFeatures.spruce(), 1500, LOTRBiomeFeatures.spruceDead(), 1000};
      LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 1, 0.5F, 63, weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 2, 0.5F, 64, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 12, GrassBlends.MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 10, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addSwampFlowers(builder, 1);
      LOTRBiomeFeatures.addDeadBushAtSurfaceChance(builder, 4);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
      LOTRBiomeFeatures.addWaterLilies(builder, 3);
      LOTRBiomeFeatures.addSwampSeagrass(builder);
      LOTRBiomeFeatures.addFallenLogs(builder, 2);
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
