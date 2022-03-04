package lotr.common.world.map;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class NorthernLightsSettings {
   public static final ResourceLocation NORTHERN_LIGHTS_SETTINGS_PATH = new ResourceLocation("lotr", "map/northern_lights.json");
   private final MapSettings mapSettings;
   private final float fullNorth;
   private final float startSouth;
   private final float furthestPossibleSouth;
   private final int fullNorth_world;
   private final int startSouth_world;
   private final int furthestPossibleSouth_world;

   private NorthernLightsSettings(MapSettings map, float fullNorth, float startSouth, float furthestPossibleSouth) {
      this.mapSettings = map;
      this.fullNorth = fullNorth;
      this.startSouth = startSouth;
      this.furthestPossibleSouth = furthestPossibleSouth;
      this.fullNorth_world = map.mapToWorldZ((double)fullNorth);
      this.startSouth_world = map.mapToWorldZ((double)startSouth);
      this.furthestPossibleSouth_world = map.mapToWorldZ((double)furthestPossibleSouth);
   }

   public static NorthernLightsSettings read(MapSettings map, JsonObject json) {
      float fullNorth = json.get("full_north").getAsFloat();
      float startSouth = json.get("start_south").getAsFloat();
      float furthestPossibleSouth = json.get("furthest_possible_south").getAsFloat();
      return new NorthernLightsSettings(map, fullNorth, startSouth, furthestPossibleSouth);
   }

   public static NorthernLightsSettings read(MapSettings map, PacketBuffer buf) {
      float fullNorth = buf.readFloat();
      float startSouth = buf.readFloat();
      float furthestPossibleSouth = buf.readFloat();
      return new NorthernLightsSettings(map, fullNorth, startSouth, furthestPossibleSouth);
   }

   public void write(PacketBuffer buf) {
      buf.writeFloat(this.fullNorth);
      buf.writeFloat(this.startSouth);
      buf.writeFloat(this.furthestPossibleSouth);
   }

   public int getFullNorth_world() {
      return this.fullNorth_world;
   }

   public int getStartSouth_world() {
      return this.startSouth_world;
   }

   public int getFurthestPossibleSouth_world() {
      return this.furthestPossibleSouth_world;
   }
}
