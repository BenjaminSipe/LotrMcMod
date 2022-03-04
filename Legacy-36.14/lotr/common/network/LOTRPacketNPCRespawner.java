package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class LOTRPacketNPCRespawner implements IMessage {
   private int spawnerID;
   private NBTTagCompound spawnerData;

   public LOTRPacketNPCRespawner() {
   }

   public LOTRPacketNPCRespawner(LOTREntityNPCRespawner spawner) {
      this.spawnerID = spawner.func_145782_y();
      this.spawnerData = new NBTTagCompound();
      spawner.writeSpawnerDataToNBT(this.spawnerData);
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.spawnerID);

      try {
         (new PacketBuffer(data)).func_150786_a(this.spawnerData);
      } catch (IOException var3) {
         FMLLog.severe("Error writing spawner data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public void fromBytes(ByteBuf data) {
      this.spawnerID = data.readInt();

      try {
         this.spawnerData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var3) {
         FMLLog.severe("Error reading spawner data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketNPCRespawner packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         Entity entity = world.func_73045_a(packet.spawnerID);
         if (entity instanceof LOTREntityNPCRespawner) {
            LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)entity;
            spawner.readSpawnerDataFromNBT(packet.spawnerData);
            entityplayer.openGui(LOTRMod.instance, 45, world, entity.func_145782_y(), 0, 0);
         }

         return null;
      }
   }
}
