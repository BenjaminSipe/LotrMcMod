package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public abstract class BaseMordorBiome extends LOTRBiomeBase {
   protected BaseMordorBiome(Builder builder, int water, boolean major) {
      super(builder, water, major);
      this.biomeColors.setWater(water);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setFillerDepth(2.0D);
      config.addSubSoilLayer(((Block)LOTRBlocks.MORDOR_DIRT.get()).func_176223_P(), 3);
      config.setUnderwater(((Block)LOTRBlocks.MORDOR_GRAVEL.get()).func_176223_P());
      config.addSubSoilLayer(((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P(), 1000);
   }

   protected void addDirtGravel(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addMordorDirtGravel(builder);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addMordorOres(builder);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addReedsWithDriedChance(builder, 1.0F);
   }

   protected void addPumpkins(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.MORDOR_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_BRICK_WALL.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ORC_TORCH.get()).func_176223_P(), 1));
   }

   public boolean hasSkyFeatures() {
      return false;
   }

   protected ExtendedWeatherType getBiomeExtendedWeather() {
      return ExtendedWeatherType.ASHFALL;
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.MORDOR_PATH;
   }
}
