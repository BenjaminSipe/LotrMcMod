package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRMod;
import lotr.common.data.LOTRLevelData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketRingPortalPos {
   private final BlockPos portalPos;

   public SPacketRingPortalPos(BlockPos pos) {
      this.portalPos = pos;
   }

   public static void encode(SPacketRingPortalPos packet, PacketBuffer buf) {
      buf.func_179255_a(packet.portalPos);
   }

   public static SPacketRingPortalPos decode(PacketBuffer buf) {
      BlockPos portalPos = buf.func_179259_c();
      SPacketRingPortalPos packet = new SPacketRingPortalPos(portalPos);
      return packet;
   }

   public static void handle(SPacketRingPortalPos packet, Supplier context) {
      World clientWorld = LOTRMod.PROXY.getClientWorld();
      LOTRLevelData.clientInstance().markMiddleEarthPortalLocation(clientWorld, packet.portalPos);
      ((Context)context.get()).setPacketHandled(true);
   }
}
