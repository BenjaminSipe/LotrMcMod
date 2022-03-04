package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRPacketIsOpRequest implements IMessage {
   public void toBytes(ByteBuf data) {
   }

   public void fromBytes(ByteBuf data) {
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketIsOpRequest packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         boolean isOp = MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(entityplayer.func_146103_bH());
         LOTRPacketIsOpResponse packetResponse = new LOTRPacketIsOpResponse(isOp);
         LOTRPacketHandler.networkWrapper.sendTo(packetResponse, entityplayer);
         return null;
      }
   }
}
