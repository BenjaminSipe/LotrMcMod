package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRMod;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.world.map.Waypoint;
import lotr.common.world.map.WaypointNetworkType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketFastTravel {
   private final Waypoint waypoint;
   private final int startX;
   private final int startZ;

   public SPacketFastTravel(Waypoint wp, int x, int z) {
      this.waypoint = wp;
      this.startX = x;
      this.startZ = z;
   }

   public static void encode(SPacketFastTravel packet, PacketBuffer buf) {
      WaypointNetworkType.writeIdentification(buf, packet.waypoint);
      buf.func_150787_b(packet.startX);
      buf.func_150787_b(packet.startZ);
   }

   public static SPacketFastTravel decode(PacketBuffer buf) {
      PlayerEntity player = LOTRMod.PROXY.getClientPlayer();
      LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(player);
      Waypoint waypoint = WaypointNetworkType.readFromIdentification(buf, pd);
      int startX = buf.func_150792_a();
      int startZ = buf.func_150792_a();
      return new SPacketFastTravel(waypoint, startX, startZ);
   }

   public static void handle(SPacketFastTravel packet, Supplier context) {
      LOTRMod.PROXY.displayFastTravelScreen(packet.waypoint, packet.startX, packet.startZ);
      ((Context)context.get()).setPacketHandled(true);
   }
}
