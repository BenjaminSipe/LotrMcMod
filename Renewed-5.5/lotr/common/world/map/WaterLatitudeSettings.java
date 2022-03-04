package lotr.common.world.map;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;

public class WaterLatitudeSettings {
   private final MapSettings mapSettings;
   private final float coldWaterZ;
   private final float warmWaterZ;
   private final int coldWaterZ_world;
   private final int warmWaterZ_world;
   private final float iceStartZ;
   private final float iceFullZ;
   private final int iceStartZ_world;
   private final int iceFullZ_world;
   private final float sandyStartZ;
   private final float sandyFullZ;
   private final int sandyStartZ_world;
   private final int sandyFullZ_world;
   private final float coralStartZ;
   private final float coralFullZ;
   private final int coralStartZ_world;
   private final int coralFullZ_world;

   private WaterLatitudeSettings(MapSettings map, float coldWaterZ, float warmWaterZ, float iceStartZ, float iceFullZ, float sandyStartZ, float sandyFullZ, float coralStartZ, float coralFullZ) {
      this.mapSettings = map;
      this.coldWaterZ = coldWaterZ;
      this.warmWaterZ = warmWaterZ;
      this.coldWaterZ_world = map.mapToWorldZ((double)coldWaterZ);
      this.warmWaterZ_world = map.mapToWorldZ((double)warmWaterZ);
      this.iceStartZ = iceStartZ;
      this.iceFullZ = iceFullZ;
      this.iceStartZ_world = map.mapToWorldZ((double)iceStartZ);
      this.iceFullZ_world = map.mapToWorldZ((double)iceFullZ);
      this.sandyStartZ = sandyStartZ;
      this.sandyFullZ = sandyFullZ;
      this.sandyStartZ_world = map.mapToWorldZ((double)sandyStartZ);
      this.sandyFullZ_world = map.mapToWorldZ((double)sandyFullZ);
      this.coralStartZ = coralStartZ;
      this.coralFullZ = coralFullZ;
      this.coralStartZ_world = map.mapToWorldZ((double)coralStartZ);
      this.coralFullZ_world = map.mapToWorldZ((double)coralFullZ);
   }

   protected static WaterLatitudeSettings read(MapSettings map, JsonObject json) {
      float coldWater = json.get("cold_water_z").getAsFloat();
      float warmWater = json.get("warm_water_z").getAsFloat();
      float iceStart = json.get("ice_start_z").getAsFloat();
      float iceFull = json.get("ice_full_z").getAsFloat();
      float sandyStart = json.get("sandy_start_z").getAsFloat();
      float sandyFull = json.get("sandy_full_z").getAsFloat();
      float coralStart = json.get("coral_start_z").getAsFloat();
      float coralFull = json.get("coral_full_z").getAsFloat();
      return new WaterLatitudeSettings(map, coldWater, warmWater, iceStart, iceFull, sandyStart, sandyFull, coralStart, coralFull);
   }

   protected static WaterLatitudeSettings read(MapSettings map, PacketBuffer buf) {
      float coldWater = buf.readFloat();
      float warmWater = buf.readFloat();
      float iceStart = buf.readFloat();
      float iceFull = buf.readFloat();
      float sandyStart = buf.readFloat();
      float sandyFull = buf.readFloat();
      float coralStart = buf.readFloat();
      float coralFull = buf.readFloat();
      return new WaterLatitudeSettings(map, coldWater, warmWater, iceStart, iceFull, sandyStart, sandyFull, coralStart, coralFull);
   }

   protected void write(PacketBuffer buf) {
      buf.writeFloat(this.coldWaterZ);
      buf.writeFloat(this.warmWaterZ);
      buf.writeFloat(this.iceStartZ);
      buf.writeFloat(this.iceFullZ);
      buf.writeFloat(this.sandyStartZ);
      buf.writeFloat(this.sandyFullZ);
      buf.writeFloat(this.coralStartZ);
      buf.writeFloat(this.coralFullZ);
   }

   public int getColdWaterZ_world() {
      return this.coldWaterZ_world;
   }

   public int getWarmWaterZ_world() {
      return this.warmWaterZ_world;
   }

   public int getIceStartZ_world() {
      return this.iceStartZ_world;
   }

   public int getIceFullZ_world() {
      return this.iceFullZ_world;
   }

   public int getSandyStartZ_world() {
      return this.sandyStartZ_world;
   }

   public int getSandyFullZ_world() {
      return this.sandyFullZ_world;
   }

   public int getCoralStartZ_world() {
      return this.coralStartZ_world;
   }

   public int getCoralFullZ_world() {
      return this.coralFullZ_world;
   }

   private float getStartToFullProgress(int curZ, int startZ, int fullZ) {
      int latitudeTransitionLength = Math.abs(fullZ - startZ);
      int latitudeProgress = Integer.signum(fullZ - startZ) * (curZ - startZ);
      float progressF = (float)latitudeProgress / (float)latitudeTransitionLength;
      return MathHelper.func_76131_a(progressF, 0.0F, 1.0F);
   }

   public float getWaterTemperatureForLatitude(int z) {
      return this.getStartToFullProgress(z, this.coldWaterZ_world, this.warmWaterZ_world);
   }

   public float getIceCoverageForLatitude(int z) {
      return this.getStartToFullProgress(z, this.iceStartZ_world, this.iceFullZ_world);
   }

   public float getSandCoverageForLatitude(int z) {
      return this.getStartToFullProgress(z, this.sandyStartZ_world, this.sandyFullZ_world);
   }

   public float getCoralForLatitude(int z) {
      return this.getStartToFullProgress(z, this.coralStartZ_world, this.coralFullZ_world);
   }
}
