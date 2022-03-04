package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.inventory.LOTRContainerBarrel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class LOTRPacketBrewingButton implements IMessage {
   public void toBytes(ByteBuf data) {
   }

   public void fromBytes(ByteBuf data) {
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBrewingButton packet, MessageContext context) {
         EntityPlayer entityplayer = context.getServerHandler().field_147369_b;
         Container container = entityplayer.field_71070_bA;
         if (container != null && container instanceof LOTRContainerBarrel) {
            ((LOTRContainerBarrel)container).theBarrel.handleBrewingButtonPress();
            LOTRLevelData.getData((EntityPlayer)entityplayer).addAchievement(LOTRAchievement.brewDrinkInBarrel);
         }

         return null;
      }
   }
}
