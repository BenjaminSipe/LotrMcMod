package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketClientMQEvent implements IMessage {
   private LOTRPacketClientMQEvent.ClientMQEvent type;

   public LOTRPacketClientMQEvent() {
   }

   public LOTRPacketClientMQEvent(LOTRPacketClientMQEvent.ClientMQEvent t) {
      this.type = t;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.type.ordinal());
   }

   public void fromBytes(ByteBuf data) {
      int typeID = data.readByte();
      if (typeID >= 0 && typeID < LOTRPacketClientMQEvent.ClientMQEvent.values().length) {
         this.type = LOTRPacketClientMQEvent.ClientMQEvent.values()[typeID];
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketClientMQEvent packet, MessageContext context) {
         EntityPlayer entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         if (packet.type == LOTRPacketClientMQEvent.ClientMQEvent.MAP) {
            pd.distributeMQEvent(new LOTRMiniQuestEvent.ViewMap());
         } else if (packet.type == LOTRPacketClientMQEvent.ClientMQEvent.FACTIONS) {
            pd.distributeMQEvent(new LOTRMiniQuestEvent.ViewFactions());
         }

         return null;
      }
   }

   public static enum ClientMQEvent {
      MAP,
      FACTIONS;
   }
}
