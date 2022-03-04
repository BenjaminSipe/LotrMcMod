package lotr.common.world.biome;

import java.util.List;
import java.util.Random;
import lotr.common.world.map.BridgeBlockProvider;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.RainType;

public interface LOTRBiomeWrapper {
   ResourceLocation getBiomeRegistryName();

   Biome getActualBiome();

   default double getHorizontalNoiseScale() {
      return 400.0D;
   }

   default float getStrengthOfAddedDepthNoise() {
      return 1.0F;
   }

   default float getBiomeScaleSignificanceForChunkGen() {
      return 0.9F;
   }

   default BlockState getGrassForBonemeal(Random rand, BlockPos plantPos) {
      return Blocks.field_150349_c.func_176223_P();
   }

   List getSpawnsAtLocation(EntityClassification var1, BlockPos var2);

   default boolean hasSkyFeatures() {
      return true;
   }

   default void onGeographicalWaterColorUpdate(int waterColor, Biome biomeObjectInClientRegistry) {
   }

   default Vector3d alterCloudColor(Vector3d clouds) {
      return clouds;
   }

   default float getCloudCoverage() {
      return 1.0F;
   }

   default boolean isFoggy() {
      return false;
   }

   default boolean hasMountainsMist() {
      return false;
   }

   RainType getPrecipitationVisually();

   float getTemperatureForSnowWeatherRendering(IWorld var1, BlockPos var2);

   default ExtendedWeatherType getExtendedWeatherVisually() {
      return ExtendedWeatherType.NONE;
   }

   default boolean doesSnowGenerate(boolean defaultDoesSnowGenerate, IWorldReader world, BlockPos pos) {
      return defaultDoesSnowGenerate;
   }

   static boolean isSnowBlockBelow(IWorldReader world, BlockPos pos) {
      return world.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_196604_cC;
   }

   default boolean doesWaterFreeze(boolean defaultDoesWaterFreeze, IWorldReader world, BlockPos pos, boolean mustBeAtEdge) {
      return defaultDoesWaterFreeze;
   }

   default float getTemperatureRaw(float defaultTemperatureAtPos, BlockPos pos) {
      return defaultTemperatureAtPos;
   }

   boolean isRiver();

   Biome getRiver(IWorld var1);

   LOTRBiomeBase getShore();

   default RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.PATH;
   }

   default BridgeBlockProvider getBridgeBlockProvider() {
      return BridgeBlockProvider.OAK;
   }

   boolean isSurfaceBlockForNPCSpawn(BlockState var1);

   default boolean hasBreakMallornResponse() {
      return false;
   }
}
