package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.MapMarkerDataModule;
import lotr.common.world.map.MapMarker;
import lotr.common.world.map.MapMarkerIcon;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketUpdateMapMarker {
   private final int markerId;
   private final String name;
   private final MapMarkerIcon icon;

   private SPacketUpdateMapMarker(int markerId, String name, MapMarkerIcon icon) {
      this.markerId = markerId;
      this.name = name;
      this.icon = icon;
   }

   public SPacketUpdateMapMarker(MapMarker marker) {
      this(marker.getId(), marker.getName(), marker.getIcon());
   }

   public static void encode(SPacketUpdateMapMarker packet, PacketBuffer buf) {
      buf.func_150787_b(packet.markerId);
      buf.func_180714_a(packet.name);
      buf.func_150787_b(packet.icon.networkId);
   }

   public static SPacketUpdateMapMarker decode(PacketBuffer buf) {
      int markerId = buf.func_150792_a();
      String name = buf.func_150789_c(32);
      MapMarkerIcon icon = MapMarkerIcon.forNetworkIdOrDefault(buf.func_150792_a());
      return new SPacketUpdateMapMarker(markerId, name, icon);
   }

   public static void handle(SPacketUpdateMapMarker packet, Supplier context) {
      PlayerEntity player = LOTRMod.PROXY.getClientPlayer();
      MapMarkerDataModule markerData = LOTRLevelData.clientInstance().getData(player).getMapMarkerData();
      int markerId = packet.markerId;
      MapMarker marker = markerData.getMarkerById(markerId);
      if (marker != null) {
         markerData.updateMarker(marker, packet.name, packet.icon);
      } else {
         LOTRLog.warn("Received map marker update from server, but no marker for ID %d exists", markerId);
      }

      ((Context)context.get()).setPacketHandled(true);
   }
}
