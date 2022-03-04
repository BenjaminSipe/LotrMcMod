package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketMountOpenInv implements IMessage {
   public void toBytes(ByteBuf data) {
   }

   public void fromBytes(ByteBuf data) {
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMountOpenInv packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         if (entityplayer.field_70154_o instanceof LOTREntityNPCRideable) {
            ((LOTREntityNPCRideable)entityplayer.field_70154_o).openGUI(entityplayer);
         }

         return null;
      }
   }
}
