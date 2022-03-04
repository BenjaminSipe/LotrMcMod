package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.data.FastTravelDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.world.map.CustomWaypoint;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketUpdateCustomWaypoint {
   private final int waypointId;
   private final String name;
   private final String lore;
   private final boolean isPublic;

   public SPacketUpdateCustomWaypoint(CustomWaypoint waypoint) {
      this(waypoint.getCustomId(), waypoint.getRawName(), waypoint.getRawLore(), waypoint.isPublic());
   }

   private SPacketUpdateCustomWaypoint(int waypointId, String name, String lore, boolean isPublic) {
      this.waypointId = waypointId;
      this.name = name;
      this.lore = lore;
      this.isPublic = isPublic;
   }

   public static void encode(SPacketUpdateCustomWaypoint packet, PacketBuffer buf) {
      buf.func_150787_b(packet.waypointId);
      buf.func_180714_a(packet.name);
      buf.func_180714_a(packet.lore);
      buf.writeBoolean(packet.isPublic);
   }

   public static SPacketUpdateCustomWaypoint decode(PacketBuffer buf) {
      int waypointId = buf.func_150792_a();
      String name = buf.func_218666_n();
      String lore = buf.func_218666_n();
      boolean isPublic = buf.readBoolean();
      return new SPacketUpdateCustomWaypoint(waypointId, name, lore, isPublic);
   }

   public static void handle(SPacketUpdateCustomWaypoint packet, Supplier context) {
      PlayerEntity player = LOTRMod.PROXY.getClientPlayer();
      FastTravelDataModule ftData = LOTRLevelData.clientInstance().getData(player).getFastTravelData();
      int waypointId = packet.waypointId;
      CustomWaypoint waypoint = ftData.getCustomWaypointById(waypointId);
      if (waypoint != null) {
         ftData.updateCustomWaypoint(player.field_70170_p, waypoint, packet.name, packet.lore, packet.isPublic);
      } else {
         LOTRLog.warn("Received custom waypoint update from server, but no custom waypoint for ID %d exists", waypointId);
      }

      ((Context)context.get()).setPacketHandled(true);
   }
}
