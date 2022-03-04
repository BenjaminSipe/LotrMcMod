package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketEnvironmentOverlay implements IMessage {
   private LOTRPacketEnvironmentOverlay.Overlay overlay;

   public LOTRPacketEnvironmentOverlay() {
   }

   public LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay o) {
      this.overlay = o;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.overlay.ordinal());
   }

   public void fromBytes(ByteBuf data) {
      int overlayID = data.readByte();
      this.overlay = LOTRPacketEnvironmentOverlay.Overlay.values()[overlayID];
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketEnvironmentOverlay packet, MessageContext context) {
         if (packet.overlay == LOTRPacketEnvironmentOverlay.Overlay.FROST) {
            LOTRMod.proxy.showFrostOverlay();
         } else if (packet.overlay == LOTRPacketEnvironmentOverlay.Overlay.BURN) {
            LOTRMod.proxy.showBurnOverlay();
         }

         return null;
      }
   }

   public static enum Overlay {
      FROST,
      BURN;
   }
}
