package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRGuiMessageTypes;
import lotr.common.LOTRMod;

public class LOTRPacketMessage implements IMessage {
   private LOTRGuiMessageTypes message;

   public LOTRPacketMessage() {
   }

   public LOTRPacketMessage(LOTRGuiMessageTypes m) {
      this.message = m;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.message.ordinal());
   }

   public void fromBytes(ByteBuf data) {
      int messageID = data.readByte();
      if (messageID >= 0 && messageID < LOTRGuiMessageTypes.values().length) {
         this.message = LOTRGuiMessageTypes.values()[messageID];
      } else {
         FMLLog.severe("Failed to display LOTR message: There is no message with ID " + messageID, new Object[0]);
         this.message = null;
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMessage packet, MessageContext context) {
         if (packet.message != null) {
            LOTRMod.proxy.displayMessage(packet.message);
         }

         return null;
      }
   }
}
