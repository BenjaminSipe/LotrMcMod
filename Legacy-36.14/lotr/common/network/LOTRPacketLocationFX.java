package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.Random;
import lotr.client.fx.LOTREntitySwordCommandMarker;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRPacketLocationFX implements IMessage {
   private LOTRPacketLocationFX.Type type;
   private double posX;
   private double posY;
   private double posZ;

   public LOTRPacketLocationFX() {
   }

   public LOTRPacketLocationFX(LOTRPacketLocationFX.Type t, double x, double y, double z) {
      this.type = t;
      this.posX = x;
      this.posY = y;
      this.posZ = z;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.type.ordinal());
      data.writeDouble(this.posX);
      data.writeDouble(this.posY);
      data.writeDouble(this.posZ);
   }

   public void fromBytes(ByteBuf data) {
      int typeID = data.readByte();
      this.type = LOTRPacketLocationFX.Type.values()[typeID];
      this.posX = data.readDouble();
      this.posY = data.readDouble();
      this.posZ = data.readDouble();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketLocationFX packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         double x = packet.posX;
         double y = packet.posY;
         double z = packet.posZ;
         Random rand = world.field_73012_v;
         if (packet.type == LOTRPacketLocationFX.Type.SWORD_COMMAND) {
            LOTREntitySwordCommandMarker marker = new LOTREntitySwordCommandMarker(world, x, y + 6.0D, z);
            world.func_72838_d(marker);
         }

         return null;
      }
   }

   public static enum Type {
      SWORD_COMMAND;
   }
}
