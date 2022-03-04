package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipRespondInvite extends LOTRPacketFellowshipDo {
   private boolean accept;

   public LOTRPacketFellowshipRespondInvite() {
   }

   public LOTRPacketFellowshipRespondInvite(LOTRFellowshipClient fs, boolean accept) {
      super(fs);
      this.accept = accept;
   }

   public void toBytes(ByteBuf data) {
      super.toBytes(data);
      data.writeBoolean(this.accept);
   }

   public void fromBytes(ByteBuf data) {
      super.fromBytes(data);
      this.accept = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowshipRespondInvite packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
         LOTRFellowship fellowship = packet.getActiveOrDisbandedFellowship();
         if (fellowship != null) {
            if (packet.accept) {
               playerData.acceptFellowshipInvite(fellowship, true);
            } else {
               playerData.rejectFellowshipInvite(fellowship);
            }
         } else {
            LOTRPacketFellowshipAcceptInviteResult resultPacket = new LOTRPacketFellowshipAcceptInviteResult(LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.NONEXISTENT);
            LOTRPacketHandler.networkWrapper.sendTo(resultPacket, entityplayer);
         }

         return null;
      }
   }
}
