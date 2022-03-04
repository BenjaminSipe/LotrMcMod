package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketIsOpResponse implements IMessage {
   private boolean isOp;

   public LOTRPacketIsOpResponse() {
   }

   public LOTRPacketIsOpResponse(boolean flag) {
      this.isOp = flag;
   }

   public void toBytes(ByteBuf data) {
      data.writeBoolean(this.isOp);
   }

   public void fromBytes(ByteBuf data) {
      this.isOp = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketIsOpResponse packet, MessageContext context) {
         LOTRMod.proxy.setMapIsOp(packet.isOp);
         return null;
      }
   }
}
