package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRMod;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketAlignmentDrain {
   private final int numFactions;

   public SPacketAlignmentDrain(int num) {
      this.numFactions = num;
   }

   public static void encode(SPacketAlignmentDrain packet, PacketBuffer buf) {
      buf.func_150787_b(packet.numFactions);
   }

   public static SPacketAlignmentDrain decode(PacketBuffer buf) {
      int numFactions = buf.func_150792_a();
      return new SPacketAlignmentDrain(numFactions);
   }

   public static void handle(SPacketAlignmentDrain packet, Supplier context) {
      LOTRMod.PROXY.displayAlignmentDrain(packet.numFactions);
      ((Context)context.get()).setPacketHandled(true);
   }
}
