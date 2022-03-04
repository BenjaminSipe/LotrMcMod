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

public class DeadMarshesBiome extends LOTRBiomeBase {
   private static final int DEAD_MARSHES_WATER_COLOR = 4014639;

   public DeadMarshesBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.SWAMP).func_205421_a(-0.24F).func_205420_b(0.0F).func_205414_c(0.4F).func_205417_d(1.0F), 4014639, major);
      this.biomeColors.setGrass(8024152).setFoliage(7041093).setSky(8684390).setClouds(7368024).setFog(6315334).setWater(4014639);
   }

   public float getStrengthOfAddedDepthNoise() {
      return 0.15F;
   }

   public float getBiomeScaleSignificanceForChunkGen() {
      return 1.0F;
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setMarsh(true);
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.07D).threshold(0.15D).state(Blocks.field_196660_k).topOnly()));
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addQuagmire(builder, 1);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oakDead(), 1000};
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.25F, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addSwampFlowers(builder, 0);
      LOTRBiomeFeatures.addDeadBushAtSurfaceChance(builder, 1);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
      LOTRBiomeFeatures.addSwampSeagrass(builder);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addReedsWithDriedChance(builder, 1.0F);
      LOTRBiomeFeatures.addSwampRushes(builder);
   }

   protected void addPumpkins(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   public Biome getRiver(IWorld world) {
      return null;
   }
}
