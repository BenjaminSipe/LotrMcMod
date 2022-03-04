package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerCoinExchange;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketCoinExchange implements IMessage {
   private int button;

   public LOTRPacketCoinExchange() {
   }

   public LOTRPacketCoinExchange(int i) {
      this.button = i;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.button);
   }

   public void fromBytes(ByteBuf data) {
      this.button = data.readByte();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketCoinExchange packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         Container container = entityplayer.field_71070_bA;
         if (container != null && container instanceof LOTRContainerCoinExchange) {
            LOTRContainerCoinExchange coinExchange = (LOTRContainerCoinExchange)container;
            coinExchange.handleExchangePacket(packet.button);
         }

         return null;
      }
   }
}
