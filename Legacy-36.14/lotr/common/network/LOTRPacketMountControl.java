package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRNetHandlerPlayServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class LOTRPacketMountControl implements IMessage {
   public double posX;
   public double posY;
   public double posZ;
   public float rotationYaw;
   public float rotationPitch;

   public LOTRPacketMountControl() {
   }

   public LOTRPacketMountControl(Entity entity) {
      this.posX = entity.field_70165_t;
      this.posY = entity.field_70163_u;
      this.posZ = entity.field_70161_v;
      this.rotationYaw = entity.field_70177_z;
      this.rotationPitch = entity.field_70125_A;
   }

   public void toBytes(ByteBuf data) {
      data.writeDouble(this.posX);
      data.writeDouble(this.posY);
      data.writeDouble(this.posZ);
      data.writeFloat(this.rotationYaw);
      data.writeFloat(this.rotationPitch);
   }

   public void fromBytes(ByteBuf data) {
      this.posX = data.readDouble();
      this.posY = data.readDouble();
      this.posZ = data.readDouble();
      this.rotationYaw = data.readFloat();
      this.rotationPitch = data.readFloat();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMountControl packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         NetHandlerPlayServer handler = entityplayer.field_71135_a;
         if (handler instanceof LOTRNetHandlerPlayServer) {
            ((LOTRNetHandlerPlayServer)handler).processMountControl(packet);
         }

         return null;
      }
   }
}
