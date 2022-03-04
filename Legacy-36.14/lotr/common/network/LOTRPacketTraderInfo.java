package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import lotr.common.LOTRMod;
import lotr.common.inventory.LOTRContainerTrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketTraderInfo implements IMessage {
   public NBTTagCompound traderData;

   public LOTRPacketTraderInfo() {
   }

   public LOTRPacketTraderInfo(NBTTagCompound nbt) {
      this.traderData = nbt;
   }

   public void toBytes(ByteBuf data) {
      try {
         (new PacketBuffer(data)).func_150786_a(this.traderData);
      } catch (IOException var3) {
         FMLLog.severe("Error writing trader data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public void fromBytes(ByteBuf data) {
      try {
         this.traderData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var3) {
         FMLLog.severe("Error reading trader data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketTraderInfo packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         Container container = entityplayer.field_71070_bA;
         if (container instanceof LOTRContainerTrade) {
            LOTRContainerTrade containerTrade = (LOTRContainerTrade)container;
            containerTrade.theTraderNPC.traderNPCInfo.receiveClientPacket(packet);
         }

         return null;
      }
   }
}
