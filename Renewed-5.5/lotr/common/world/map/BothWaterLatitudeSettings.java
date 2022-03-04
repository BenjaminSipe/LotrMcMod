package lotr.common.world.map;

import com.google.gson.JsonObject;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BothWaterLatitudeSettings {
   public static final ResourceLocation WATER_SETTINGS_PATH = new ResourceLocation("lotr", "map/water_latitude.json");
   private final WaterLatitudeSettings northernWaterLatitudes;
   private final WaterLatitudeSettings southernWaterLatitudes;

   private BothWaterLatitudeSettings(WaterLatitudeSettings north, WaterLatitudeSettings south) {
      this.northernWaterLatitudes = north;
      this.southernWaterLatitudes = south;
   }

   public static BothWaterLatitudeSettings read(MapSettings map, JsonObject rootJson) {
      WaterLatitudeSettings north = WaterLatitudeSettings.read(map, rootJson.getAsJsonObject("northern"));
      WaterLatitudeSettings south = WaterLatitudeSettings.read(map, rootJson.getAsJsonObject("southern"));
      return new BothWaterLatitudeSettings(north, south);
   }

   protected static BothWaterLatitudeSettings read(MapSettings map, PacketBuffer buf) {
      WaterLatitudeSettings north = WaterLatitudeSettings.read(map, buf);
      WaterLatitudeSettings south = WaterLatitudeSettings.read(map, buf);
      return new BothWaterLatitudeSettings(north, south);
   }

   protected void write(PacketBuffer buf) {
      this.northernWaterLatitudes.write(buf);
      this.southernWaterLatitudes.write(buf);
   }

   public WaterLatitudeSettings getNorthern() {
      return this.northernWaterLatitudes;
   }

   public WaterLatitudeSettings getSouthern() {
      return this.southernWaterLatitudes;
   }

   public float getWaterTemperatureForLatitude(int z) {
      return this.getFromNorthOrSouth(z, WaterLatitudeSettings::getWarmWaterZ_world, WaterLatitudeSettings::getWaterTemperatureForLatitude);
   }

   public float getIceCoverageForLatitude(int z) {
      return this.getFromNorthOrSouth(z, WaterLatitudeSettings::getIceStartZ_world, WaterLatitudeSettings::getIceCoverageForLatitude);
   }

   public float getSandCoverageForLatitude(int z) {
      return this.getFromNorthOrSouth(z, WaterLatitudeSettings::getSandyFullZ_world, WaterLatitudeSettings::getSandCoverageForLatitude);
   }

   public float getCoralForLatitude(int z) {
      return this.getFromNorthOrSouth(z, WaterLatitudeSettings::getCoralFullZ_world, WaterLatitudeSettings::getCoralForLatitude);
   }

   private float getFromNorthOrSouth(int z, Function southSettingsNorthernmostPoint, BiFunction settingsLatitudeGetter) {
      return z >= (Integer)southSettingsNorthernmostPoint.apply(this.southernWaterLatitudes) ? (Float)settingsLatitudeGetter.apply(this.southernWaterLatitudes, z) : (Float)settingsLatitudeGetter.apply(this.northernWaterLatitudes, z);
   }

   public static boolean isNorthOfSouthernIceSheet(IWorld world, BlockPos pos) {
      return pos.func_177952_p() < MapSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedMap().getWaterLatitudes().getSouthern().getIceStartZ_world();
   }
}
