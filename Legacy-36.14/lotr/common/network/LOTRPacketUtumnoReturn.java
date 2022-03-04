package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class LOTRPacketUtumnoReturn implements IMessage {
   private double posX;
   private double posZ;

   public LOTRPacketUtumnoReturn() {
   }

   public LOTRPacketUtumnoReturn(double x, double z) {
      this.posX = x;
      this.posZ = z;
   }

   public void toBytes(ByteBuf data) {
      data.writeDouble(this.posX);
      data.writeDouble(this.posZ);
   }

   public void fromBytes(ByteBuf data) {
      this.posX = data.readDouble();
      this.posZ = data.readDouble();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketUtumnoReturn packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         entityplayer.func_70107_b(packet.posX, entityplayer.field_70163_u, packet.posZ);
         LOTRMod.proxy.setUtumnoReturnPortalCoords(entityplayer, MathHelper.func_76128_c(packet.posX), MathHelper.func_76128_c(packet.posZ));
         return null;
      }
   }
}
