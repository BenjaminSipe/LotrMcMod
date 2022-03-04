package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.data.PlayerMessageType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketPlayerMessage {
   private final PlayerMessageType messageType;
   private final boolean isCommandSent;
   private final String customText;

   public SPacketPlayerMessage(PlayerMessageType message, boolean command, String custom) {
      this.messageType = message;
      this.isCommandSent = command;
      this.customText = custom;
   }

   public static void encode(SPacketPlayerMessage packet, PacketBuffer buf) {
      buf.func_150787_b(packet.messageType.networkID);
      buf.writeBoolean(packet.isCommandSent);
      boolean hasCustomText = packet.customText != null;
      buf.writeBoolean(hasCustomText);
      if (hasCustomText) {
         buf.func_180714_a(packet.customText);
      }

   }

   public static SPacketPlayerMessage decode(PacketBuffer buf) {
      int typeId = buf.func_150792_a();
      PlayerMessageType messageType = PlayerMessageType.forNetworkID(typeId);
      if (messageType == null) {
         LOTRLog.error("Failed to display player message: There is no message with ID %d", typeId);
      }

      boolean isCommandSent = buf.readBoolean();
      String customText = null;
      boolean hasCustomText = buf.readBoolean();
      if (hasCustomText) {
         customText = buf.func_218666_n();
         if (messageType != null && messageType != PlayerMessageType.CUSTOM) {
            LOTRLog.error("Player message type %s doesn't support custom text", messageType.getSaveName());
            customText = null;
         }
      }

      return new SPacketPlayerMessage(messageType, isCommandSent, customText);
   }

   public static void handle(SPacketPlayerMessage packet, Supplier context) {
      PlayerMessageType messageType = packet.messageType;
      if (messageType != null) {
         LOTRMod.PROXY.displayMessageType(messageType, packet.isCommandSent, packet.customText);
      }

      ((Context)context.get()).setPacketHandled(true);
   }
}
