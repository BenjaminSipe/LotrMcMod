package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.MapMarkerDataModule;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.MapMarker;
import lotr.common.world.map.MapMarkerIcon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class CPacketUpdateMapMarker {
   private final int markerId;
   private final String name;
   private final MapMarkerIcon icon;

   private CPacketUpdateMapMarker(int markerId, String name, MapMarkerIcon icon) {
      this.markerId = markerId;
      this.name = name;
      this.icon = icon;
   }

   public CPacketUpdateMapMarker(MapMarker marker) {
      this(marker.getId(), marker.getName(), marker.getIcon());
   }

   public static void encode(CPacketUpdateMapMarker packet, PacketBuffer buf) {
      buf.func_150787_b(packet.markerId);
      buf.func_180714_a(packet.name);
      buf.func_150787_b(packet.icon.networkId);
   }

   public static CPacketUpdateMapMarker decode(PacketBuffer buf) {
      int markerId = buf.func_150792_a();
      String name = buf.func_150789_c(32);
      MapMarkerIcon icon = MapMarkerIcon.forNetworkIdOrDefault(buf.func_150792_a());
      return new CPacketUpdateMapMarker(markerId, name, icon);
   }

   public static void handle(CPacketUpdateMapMarker packet, Supplier context) {
      ServerPlayerEntity player = ((Context)context.get()).getSender();
      MapMarkerDataModule markerData = LOTRLevelData.serverInstance().getData(player).getMapMarkerData();
      int markerId = packet.markerId;
      MapMarker marker = markerData.getMarkerById(markerId);
      if (marker != null) {
         markerData.updateMarker(marker, packet.name, packet.icon);
      } else {
         LOTRLog.warn("Player %s tried to update map marker, but no marker for ID %d exists", UsernameHelper.getRawUsername(player), markerId);
      }

      ((Context)context.get()).setPacketHandled(true);
   }
}
