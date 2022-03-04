package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import lotr.common.entity.npc.LOTREntityTree;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketMallornEntSummon implements IMessage {
   private int mallornEntID;
   private int summonedID;

   public LOTRPacketMallornEntSummon() {
   }

   public LOTRPacketMallornEntSummon(int id1, int id2) {
      this.mallornEntID = id1;
      this.summonedID = id1;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.mallornEntID);
      data.writeInt(this.summonedID);
   }

   public void fromBytes(ByteBuf data) {
      this.mallornEntID = data.readInt();
      this.summonedID = data.readInt();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMallornEntSummon packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.mallornEntID);
         if (entity instanceof LOTREntityMallornEnt) {
            LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
            Entity summoned = world.func_73045_a(packet.summonedID);
            if (summoned instanceof LOTREntityTree) {
               ent.spawnEntSummonParticles((LOTREntityTree)summoned);
            }
         }

         return null;
      }
   }
}
