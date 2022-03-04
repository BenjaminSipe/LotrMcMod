package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketPledgeSet implements IMessage {
   private LOTRFaction pledgeFac;

   public LOTRPacketPledgeSet() {
   }

   public LOTRPacketPledgeSet(LOTRFaction f) {
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
      public IMessage onMessage(LOTRPacketPledgeSet packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         LOTRFaction fac = packet.pledgeFac;
         if (fac == null) {
            pd.revokePledgeFaction(entityplayer, true);
         } else if (pd.canPledgeTo(fac) && pd.canMakeNewPledge()) {
            pd.setPledgeFaction(fac);
         }

         return null;
      }
   }
}
