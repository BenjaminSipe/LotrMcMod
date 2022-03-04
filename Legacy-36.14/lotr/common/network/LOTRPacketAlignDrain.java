package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketAlignDrain implements IMessage {
   private int numFactions;

   public LOTRPacketAlignDrain() {
   }

   public LOTRPacketAlignDrain(int num) {
      this.numFactions = num;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.numFactions);
   }

   public void fromBytes(ByteBuf data) {
      this.numFactions = data.readByte();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketAlignDrain packet, MessageContext context) {
         LOTRMod.proxy.displayAlignDrain(packet.numFactions);
         return null;
      }
   }
}
