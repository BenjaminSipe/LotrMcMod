package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class LOTRPacketMallornEntHeal implements IMessage {
   private int entityID;
   public NBTTagCompound healingData;

   public LOTRPacketMallornEntHeal() {
   }

   public LOTRPacketMallornEntHeal(int id, NBTTagCompound nbt) {
      this.entityID = id;
      this.healingData = nbt;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);

      try {
         (new PacketBuffer(data)).func_150786_a(this.healingData);
      } catch (IOException var3) {
         FMLLog.severe("Error writing MEnt healing data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();

      try {
         this.healingData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var3) {
         FMLLog.severe("Error reading MEnt healing data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMallornEntHeal packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity instanceof LOTREntityMallornEnt) {
            LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
            ent.receiveClientHealing(packet.healingData);
         }

         return null;
      }
   }
}
