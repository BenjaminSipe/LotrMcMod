package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFTBounceServer implements IMessage {
   public void toBytes(ByteBuf data) {
   }

   public void fromBytes(ByteBuf data) {
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFTBounceServer packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRLevelData.getData((EntityPlayer)entityplayer).receiveFTBouncePacket();
         return null;
      }
   }
}
