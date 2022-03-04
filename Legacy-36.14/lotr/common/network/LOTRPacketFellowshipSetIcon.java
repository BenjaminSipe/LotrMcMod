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
import net.minecraft.item.ItemStack;

public class LOTRPacketFellowshipSetIcon extends LOTRPacketFellowshipDo {
   public LOTRPacketFellowshipSetIcon() {
   }

   public LOTRPacketFellowshipSetIcon(LOTRFellowshipClient fs) {
      super(fs);
   }

   public void toBytes(ByteBuf data) {
      super.toBytes(data);
   }

   public void fromBytes(ByteBuf data) {
      super.fromBytes(data);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowshipSetIcon packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRFellowship fellowship = packet.getActiveFellowship();
         if (fellowship != null) {
            LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            ItemStack itemstack = entityplayer.func_70694_bm();
            if (itemstack != null) {
               ItemStack newStack = itemstack.func_77946_l();
               newStack.field_77994_a = 1;
               playerData.setFellowshipIcon(fellowship, newStack);
            } else {
               playerData.setFellowshipIcon(fellowship, (ItemStack)null);
            }
         }

         return null;
      }
   }
}
