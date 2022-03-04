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

public class LOTRPacketFellowshipLeave extends LOTRPacketFellowshipDo {
   public LOTRPacketFellowshipLeave() {
   }

   public LOTRPacketFellowshipLeave(LOTRFellowshipClient fs) {
      super(fs);
   }

   public void toBytes(ByteBuf data) {
      super.toBytes(data);
   }

   public void fromBytes(ByteBuf data) {
      super.fromBytes(data);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowshipLeave packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRFellowship fellowship = packet.getActiveFellowship();
         if (fellowship != null) {
            LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            playerData.leaveFellowship(fellowship);
         }

         return null;
      }
   }
}
