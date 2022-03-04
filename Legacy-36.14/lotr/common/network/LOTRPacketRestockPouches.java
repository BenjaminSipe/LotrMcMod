package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.item.LOTRItemPouch;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketRestockPouches implements IMessage {
   public void toBytes(ByteBuf data) {
   }

   public void fromBytes(ByteBuf data) {
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketRestockPouches packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         if (LOTRItemPouch.restockPouches(entityplayer)) {
            entityplayer.field_71070_bA.func_75142_b();
            entityplayer.field_70170_p.func_72956_a(entityplayer, "mob.horse.leather", 0.5F, 1.0F);
         }

         return null;
      }
   }
}
