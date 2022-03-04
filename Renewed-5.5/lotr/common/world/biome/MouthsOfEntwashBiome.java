package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class MouthsOfEntwashBiome extends LOTRBiomeBase {
   public MouthsOfEntwashBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.SWAMP).func_205421_a(-0.22F).func_205420_b(0.0F).func_205414_c(0.6F).func_205417_d(1.0F), major);
   }

   public float getStrengthOfAddedDepthNoise() {
      return 0.15F;
   }

   public float getBiomeScaleSignificanceForChunkGen() {
      return 0.96F;
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setMarsh(true);
      config.addSubSoilLayer(((Block)LOTRBlocks.ROHAN_ROCK.get()).func_176223_P(), 4, 6);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addGranite(builder);
      LOTRBiomeFeatures.addRohanRockPatches(builder);
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addQuagmire(builder, 2);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.oak(), 200, LOTRBiomeFeatures.oakFancy(), 200, LOTRBiomeFeatures.oakSwamp(), 500, LOTRBiomeFeatures.oakShrub(), 4000};
      LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 2, 0.5F, 63, weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 3, 0.5F, 64, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 10, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addSwampFlowers(builder, 3);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
      LOTRBiomeFeatures.addWaterLiliesWithRareFlowers(builder, 2);
      LOTRBiomeFeatures.addSwampSeagrass(builder);
      LOTRBiomeFeatures.addFallenLogs(builder, 2);
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
