package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class LOTRPacketHornSelect implements IMessage {
   private int hornType;

   public LOTRPacketHornSelect() {
   }

   public LOTRPacketHornSelect(int h) {
      this.hornType = h;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.hornType);
   }

   public void fromBytes(ByteBuf data) {
      this.hornType = data.readByte();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketHornSelect packet, MessageContext context) {
         EntityPlayer entityplayer = context.getServerHandler().field_147369_b;
         ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
         if (itemstack != null && itemstack.func_77973_b() == LOTRMod.commandHorn && itemstack.func_77960_j() == 0) {
            itemstack.func_77964_b(packet.hornType);
         }

         return null;
      }
   }
}
