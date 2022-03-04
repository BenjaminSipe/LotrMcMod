package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketNPCFX implements IMessage {
   private int entityID;
   private LOTRPacketNPCFX.FXType type;

   public LOTRPacketNPCFX() {
   }

   public LOTRPacketNPCFX(int i, LOTRPacketNPCFX.FXType t) {
      this.entityID = i;
      this.type = t;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);
      data.writeByte(this.type.ordinal());
   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();
      int typeID = data.readByte();
      this.type = LOTRPacketNPCFX.FXType.values()[typeID];
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketNPCFX packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC)entity;
            if (packet.type == LOTRPacketNPCFX.FXType.HEARTS) {
               npc.spawnHearts();
            } else if (packet.type == LOTRPacketNPCFX.FXType.EATING) {
               npc.spawnFoodParticles();
            } else if (packet.type == LOTRPacketNPCFX.FXType.SMOKE) {
               npc.spawnSmokes();
            }
         }

         return null;
      }
   }

   public static enum FXType {
      HEARTS,
      EATING,
      SMOKE;
   }
}
