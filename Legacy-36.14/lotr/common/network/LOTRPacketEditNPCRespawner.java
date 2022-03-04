package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class LOTRPacketEditNPCRespawner implements IMessage {
   private int spawnerID;
   private NBTTagCompound spawnerData;
   public boolean destroy;

   public LOTRPacketEditNPCRespawner() {
   }

   public LOTRPacketEditNPCRespawner(LOTREntityNPCRespawner spawner) {
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

      data.writeBoolean(this.destroy);
   }

   public void fromBytes(ByteBuf data) {
      this.spawnerID = data.readInt();

      try {
         this.spawnerData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var3) {
         FMLLog.severe("Error reading spawner data", new Object[0]);
         var3.printStackTrace();
      }

      this.destroy = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketEditNPCRespawner packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Entity sEntity = world.func_73045_a(packet.spawnerID);
         if (sEntity instanceof LOTREntityNPCRespawner) {
            LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)sEntity;
            if (entityplayer.field_71075_bZ.field_75098_d) {
               spawner.readSpawnerDataFromNBT(packet.spawnerData);
            }

            if (packet.destroy) {
               spawner.onBreak();
            }
         }

         return null;
      }
   }
}
