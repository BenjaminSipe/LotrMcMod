package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRPacketUtumnoKill implements IMessage {
   private int entityID;
   private int blockX;
   private int blockY;
   private int blockZ;

   public LOTRPacketUtumnoKill() {
   }

   public LOTRPacketUtumnoKill(int id, int i, int j, int k) {
      this.entityID = id;
      this.blockX = i;
      this.blockY = j;
      this.blockZ = k;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);
      data.writeInt(this.blockX);
      data.writeInt(this.blockY);
      data.writeInt(this.blockZ);
   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();
      this.blockX = data.readInt();
      this.blockY = data.readInt();
      this.blockZ = data.readInt();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketUtumnoKill packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity != null) {
            int i1 = packet.blockX;
            int j1 = packet.blockY;
            int k1 = packet.blockZ;
            Block block = world.func_147439_a(i1, j1, k1);
            block.func_149719_a(world, i1, j1, k1);
            double blockTop = block.func_149669_A();

            int l;
            double d;
            double d1;
            double d2;
            double d3;
            double d4;
            double d5;
            for(l = 0; l < 8; ++l) {
               d = (double)i1 + MathHelper.func_82716_a(world.field_73012_v, 0.25D, 0.75D);
               d1 = (double)j1 + 0.1D;
               d2 = (double)k1 + MathHelper.func_82716_a(world.field_73012_v, 0.25D, 0.75D);
               d3 = 0.0D;
               d4 = MathHelper.func_82716_a(world.field_73012_v, 0.1D, 0.2D);
               d5 = 0.0D;
               LOTRMod.proxy.spawnParticle("utumnoKill", d, d1, d2, d3, d4, d5);
            }

            for(l = 0; l < 12; ++l) {
               d = entity.field_70165_t + world.field_73012_v.nextGaussian() * 0.8D;
               d1 = entity.field_70121_D.field_72338_b + (double)entity.field_70131_O * 0.7D + world.field_73012_v.nextGaussian() * 0.2D;
               d2 = entity.field_70161_v + world.field_73012_v.nextGaussian() * 0.8D;
               d3 = (double)i1 + 0.5D - d;
               d4 = (double)j1 + blockTop - d1;
               d5 = (double)k1 + 0.5D - d2;
               double v = 0.05D;
               d3 *= v;
               d4 *= v;
               d5 *= v;
               LOTRMod.proxy.spawnParticle("utumnoKill", d, d1, d2, d3, d4, d5);
            }
         }

         return null;
      }
   }
}
