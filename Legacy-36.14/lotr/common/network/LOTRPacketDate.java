package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import lotr.common.LOTRDate;
import lotr.common.LOTRMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketDate implements IMessage {
   public NBTTagCompound dateData;
   private boolean doUpdate;

   public LOTRPacketDate() {
   }

   public LOTRPacketDate(NBTTagCompound nbt, boolean flag) {
      this.dateData = nbt;
      this.doUpdate = flag;
   }

   public void toBytes(ByteBuf data) {
      try {
         (new PacketBuffer(data)).func_150786_a(this.dateData);
      } catch (IOException var3) {
         FMLLog.severe("Error writing LOTR date", new Object[0]);
         var3.printStackTrace();
      }

      data.writeBoolean(this.doUpdate);
   }

   public void fromBytes(ByteBuf data) {
      try {
         this.dateData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var3) {
         FMLLog.severe("Error reading LOTR date", new Object[0]);
         var3.printStackTrace();
      }

      this.doUpdate = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketDate packet, MessageContext context) {
         LOTRDate.loadDates(packet.dateData);
         if (packet.doUpdate) {
            LOTRMod.proxy.displayNewDate();
         }

         return null;
      }
   }
}
