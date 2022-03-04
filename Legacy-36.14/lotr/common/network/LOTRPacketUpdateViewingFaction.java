package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketUpdateViewingFaction implements IMessage {
   private LOTRFaction viewingFaction;

   public LOTRPacketUpdateViewingFaction() {
   }

   public LOTRPacketUpdateViewingFaction(LOTRFaction f) {
      this.viewingFaction = f;
   }

   public void toBytes(ByteBuf data) {
      int facID = this.viewingFaction.ordinal();
      data.writeByte(facID);
   }

   public void fromBytes(ByteBuf data) {
      int facID = data.readByte();
      this.viewingFaction = LOTRFaction.forID(facID);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketUpdateViewingFaction packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         pd.setViewingFaction(packet.viewingFaction);
         return null;
      }
   }
}
