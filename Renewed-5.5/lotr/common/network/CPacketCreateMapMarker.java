package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.MapMarkerDataModule;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.MapMarker;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class CPacketCreateMapMarker {
   private final int worldX;
   private final int worldZ;
   private final String name;

   public CPacketCreateMapMarker(int worldX, int worldZ, String name) {
      this.worldX = worldX;
      this.worldZ = worldZ;
      this.name = name;
   }

   public static void encode(CPacketCreateMapMarker packet, PacketBuffer buf) {
      buf.func_150787_b(packet.worldX);
      buf.func_150787_b(packet.worldZ);
      buf.func_180714_a(packet.name);
   }

   public static CPacketCreateMapMarker decode(PacketBuffer buf) {
      int worldX = buf.func_150792_a();
      int worldZ = buf.func_150792_a();
      String name = buf.func_150789_c(32);
      return new CPacketCreateMapMarker(worldX, worldZ, name);
   }

   public static void handle(CPacketCreateMapMarker packet, Supplier context) {
      ServerPlayerEntity player = ((Context)context.get()).getSender();
      MapMarkerDataModule markerData = LOTRLevelData.serverInstance().getData(player).getMapMarkerData();
      if (!markerData.canCreateNewMarker()) {
         LOTRLog.warn("Player %s tried to create a new map marker but already has %d", UsernameHelper.getRawUsername(player), markerData.getMarkers().size());
      } else {
         int worldX = packet.worldX;
         int worldZ = packet.worldZ;
         if (!MapMarker.isValidMapMarkerPosition(player.field_70170_p, worldX, worldZ)) {
            LOTRLog.warn("Player %s tried to place a map marker at invalid coordinates (%d, %d)", UsernameHelper.getRawUsername(player), worldX, worldZ);
         } else {
            markerData.createNewMarker(worldX, worldZ, packet.name);
         }

         ((Context)context.get()).setPacketHandled(true);
      }
   }
}
