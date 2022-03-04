package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerPouch;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketRenamePouch implements IMessage {
   private String name;

   public LOTRPacketRenamePouch() {
   }

   public LOTRPacketRenamePouch(String s) {
      this.name = s;
   }

   public void toBytes(ByteBuf data) {
      byte[] nameData = this.name.getBytes(Charsets.UTF_8);
      data.writeShort(nameData.length);
      data.writeBytes(nameData);
   }

   public void fromBytes(ByteBuf data) {
      int length = data.readShort();
      this.name = data.readBytes(length).toString(Charsets.UTF_8);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketRenamePouch packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         Container container = entityplayer.field_71070_bA;
         if (container != null && container instanceof LOTRContainerPouch) {
            LOTRContainerPouch pouch = (LOTRContainerPouch)container;
            pouch.renamePouch(packet.name);
         }

         return null;
      }
   }
}
