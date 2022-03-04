package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRMod;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import net.minecraft.network.PacketBuffer;

public class SPacketToggle extends SidedTogglePacket {
   public SPacketToggle(SidedTogglePacket.ToggleType type, boolean fieldValue) {
      super(type, fieldValue);
   }

   public static void encode(SPacketToggle packet, PacketBuffer buf) {
      SidedTogglePacket.encode(packet, buf);
   }

   public static SPacketToggle decode(PacketBuffer buf) {
      return (SPacketToggle)SidedTogglePacket.decode(buf, SPacketToggle::new);
   }

   protected LOTRPlayerData getSidedPlayerData(Supplier context) {
      return LOTRLevelData.clientInstance().getData(LOTRMod.PROXY.getClientPlayer());
   }
}
