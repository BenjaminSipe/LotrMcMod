package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerDaleCracker;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketSealCracker implements IMessage {
   public void toBytes(ByteBuf data) {
   }

   public void fromBytes(ByteBuf data) {
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketSealCracker packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         Container container = entityplayer.field_71070_bA;
         if (container instanceof LOTRContainerDaleCracker) {
            LOTRContainerDaleCracker cracker = (LOTRContainerDaleCracker)container;
            cracker.receiveSealingPacket(entityplayer);
         }

         return null;
      }
   }
}
