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

public class LOTRPacketPledge implements IMessage {
   private LOTRFaction pledgeFac;

   public LOTRPacketPledge() {
   }

   public LOTRPacketPledge(LOTRFaction f) {
      this.pledgeFac = f;
   }

   public void toBytes(ByteBuf data) {
      int facID;
      if (this.pledgeFac == null) {
         facID = -1;
      } else {
         facID = this.pledgeFac.ordinal();
      }

      data.writeByte(facID);
   }

   public void fromBytes(ByteBuf data) {
      int facID = data.readByte();
      if (facID == -1) {
         this.pledgeFac = null;
      } else {
         this.pledgeFac = LOTRFaction.forID(facID);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketPledge packet, MessageContext context) {
         if (!LOTRMod.proxy.isSingleplayer()) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            pd.setPledgeFaction(packet.pledgeFac);
         }

         return null;
      }
   }
}
