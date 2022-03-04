package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRPacketWeaponFX implements IMessage {
   private LOTRPacketWeaponFX.Type type;
   private int entityID;

   public LOTRPacketWeaponFX() {
   }

   public LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type t, Entity entity) {
      this.type = t;
      this.entityID = entity.func_145782_y();
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.type.ordinal());
      data.writeInt(this.entityID);
   }

   public void fromBytes(ByteBuf data) {
      int typeID = data.readByte();
      this.type = LOTRPacketWeaponFX.Type.values()[typeID];
      this.entityID = data.readInt();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketWeaponFX packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity != null) {
            double x = entity.field_70165_t;
            double y = entity.field_70121_D.field_72338_b;
            double z = entity.field_70161_v;
            Random rand = world.field_73012_v;
            int i;
            float angle;
            double dist;
            double d;
            double d1;
            if (packet.type == LOTRPacketWeaponFX.Type.MACE_SAURON) {
               for(i = 0; i < 360; i += 2) {
                  angle = (float)Math.toRadians((double)i);
                  dist = 1.5D;
                  d = dist * (double)MathHelper.func_76126_a(angle);
                  d1 = dist * (double)MathHelper.func_76134_b(angle);
                  world.func_72869_a("smoke", x + d, y + 0.1D, z + d1, d * 0.2D, 0.0D, d1 * 0.2D);
               }
            } else if (packet.type == LOTRPacketWeaponFX.Type.STAFF_GANDALF_WHITE) {
               for(i = 0; i < 360; i += 2) {
                  angle = (float)Math.toRadians((double)i);
                  dist = 1.5D;
                  d = dist * (double)MathHelper.func_76126_a(angle);
                  d1 = dist * (double)MathHelper.func_76134_b(angle);
                  LOTRMod.proxy.spawnParticle("blueFlame", x + d, y + 0.1D, z + d1, d * 0.2D, 0.0D, d1 * 0.2D);
               }
            } else if (packet.type == LOTRPacketWeaponFX.Type.FIREBALL_GANDALF_WHITE) {
               LOTRMod.proxy.spawnParticle("gandalfFireball", x, y, z, 0.0D, 0.0D, 0.0D);
            } else {
               double d1;
               float angleXZ;
               float angleY;
               float speed;
               double d3;
               double d4;
               double d5;
               if (packet.type == LOTRPacketWeaponFX.Type.INFERNAL) {
                  for(i = 0; i < 20; ++i) {
                     d1 = y + (double)(entity.field_70131_O * 0.7F);
                     angleXZ = rand.nextFloat() * 3.1415927F * 2.0F;
                     angleY = rand.nextFloat() * 3.1415927F * 2.0F;
                     speed = MathHelper.func_151240_a(rand, 0.1F, 0.15F);
                     d3 = (double)(MathHelper.func_76134_b(angleXZ) * MathHelper.func_76134_b(angleY) * speed);
                     d4 = (double)(MathHelper.func_76126_a(angleY) * speed);
                     d5 = (double)(MathHelper.func_76126_a(angleXZ) * MathHelper.func_76134_b(angleY) * speed);
                     d4 += 0.15000000596046448D;
                     d3 += entity.field_70165_t - entity.field_70142_S;
                     d4 += entity.field_70163_u - entity.field_70137_T;
                     d5 += entity.field_70161_v - entity.field_70136_U;
                     world.func_72869_a("flame", x, d1, z, d3, d4, d5);
                  }
               } else if (packet.type == LOTRPacketWeaponFX.Type.CHILLING) {
                  for(i = 0; i < 40; ++i) {
                     d1 = y + (double)(entity.field_70131_O * 0.7F);
                     angleXZ = rand.nextFloat() * 3.1415927F * 2.0F;
                     angleY = rand.nextFloat() * 3.1415927F * 2.0F;
                     speed = MathHelper.func_151240_a(rand, 0.1F, 0.2F);
                     d3 = (double)(MathHelper.func_76134_b(angleXZ) * MathHelper.func_76134_b(angleY) * speed);
                     d4 = (double)(MathHelper.func_76126_a(angleY) * speed);
                     d5 = (double)(MathHelper.func_76126_a(angleXZ) * MathHelper.func_76134_b(angleY) * speed);
                     d3 += entity.field_70165_t - entity.field_70142_S;
                     d4 += entity.field_70163_u - entity.field_70137_T;
                     d5 += entity.field_70161_v - entity.field_70136_U;
                     LOTRMod.proxy.spawnParticle("chill", x, d1, z, d3, d4, d5);
                  }
               }
            }
         }

         return null;
      }
   }

   public static enum Type {
      MACE_SAURON,
      STAFF_GANDALF_WHITE,
      FIREBALL_GANDALF_WHITE,
      INFERNAL,
      CHILLING;
   }
}
