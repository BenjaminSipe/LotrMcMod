package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityInvasionSpawner;

public class LOTRPacketInvasionWatch implements IMessage {
   private int invasionEntityID;
   private boolean overrideAlreadyWatched;

   public LOTRPacketInvasionWatch() {
   }

   public LOTRPacketInvasionWatch(LOTREntityInvasionSpawner invasion, boolean override) {
      this.invasionEntityID = invasion.func_145782_y();
      this.overrideAlreadyWatched = override;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.invasionEntityID);
      data.writeBoolean(this.overrideAlreadyWatched);
   }

   public void fromBytes(ByteBuf data) {
      this.invasionEntityID = data.readInt();
      this.overrideAlreadyWatched = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketInvasionWatch packet, MessageContext context) {
         LOTRMod.proxy.handleInvasionWatch(packet.invasionEntityID, packet.overrideAlreadyWatched);
         return null;
      }
   }
}
