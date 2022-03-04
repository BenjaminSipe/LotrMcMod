package lotr.common.world.map;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import lotr.common.data.LOTRPlayerData;
import net.minecraft.network.PacketBuffer;

public class WaypointNetworkType {
   private static final Map TYPES = new HashMap();
   public static final WaypointNetworkType MAP = registerType(MapWaypoint::writeIdentification, MapWaypoint::readFromIdentification);
   public static final WaypointNetworkType CUSTOM = registerType(CustomWaypoint::writeIdentification, CustomWaypoint::readFromIdentification);
   public static final WaypointNetworkType ADOPTED_CUSTOM = registerType(AdoptedCustomWaypoint::writeIdentification, AdoptedCustomWaypoint::readFromIdentification);
   private final int typeId;
   private final BiConsumer identificationWriter;
   private final BiFunction identificationReader;

   private static final WaypointNetworkType registerType(BiConsumer writer, BiFunction reader) {
      int nextTypeId = TYPES.size();
      if (nextTypeId > 255) {
         throw new IllegalStateException("No more waypoint type IDs available!");
      } else {
         WaypointNetworkType type = new WaypointNetworkType(nextTypeId, writer, reader);
         TYPES.put(nextTypeId, type);
         return type;
      }
   }

   private WaypointNetworkType(int id, BiConsumer writer, BiFunction reader) {
      this.typeId = id;
      this.identificationWriter = writer;
      this.identificationReader = reader;
   }

   public static void writeIdentification(PacketBuffer buf, Waypoint waypoint) {
      WaypointNetworkType type = waypoint.getNetworkType();
      buf.writeByte(waypoint.getNetworkType().typeId);
      type.identificationWriter.accept(buf, waypoint);
   }

   public static Waypoint readFromIdentification(PacketBuffer buf, LOTRPlayerData pd) {
      int typeId = buf.readByte() & 255;
      WaypointNetworkType type = (WaypointNetworkType)TYPES.get(typeId);
      if (type == null) {
         throw new IllegalStateException("Networking error - packet received unknown waypoint type ID " + typeId);
      } else {
         return (Waypoint)type.identificationReader.apply(buf, pd);
      }
   }
}
