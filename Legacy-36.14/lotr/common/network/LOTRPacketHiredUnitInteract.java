package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketHiredUnitInteract implements IMessage {
   private int entityID;
   private int entityAction;

   public LOTRPacketHiredUnitInteract() {
   }

   public LOTRPacketHiredUnitInteract(int id, int a) {
      this.entityID = id;
      this.entityAction = a;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);
      data.writeByte(this.entityAction);
   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();
      this.entityAction = data.readByte();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketHiredUnitInteract packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Entity npc = world.func_73045_a(packet.entityID);
         if (npc != null && npc instanceof LOTREntityNPC) {
            LOTREntityNPC hiredNPC = (LOTREntityNPC)npc;
            if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
               int action = packet.entityAction;
               boolean closeScreen = false;
               if (action == 0) {
                  hiredNPC.npcTalkTick = hiredNPC.getNPCTalkInterval();
                  closeScreen = hiredNPC.speakTo(entityplayer);
               } else if (action == 1) {
                  hiredNPC.hiredNPCInfo.sendClientPacket(true);
               } else if (action == 2) {
               }

               if (closeScreen) {
                  entityplayer.func_71053_j();
               }
            }
         }

         return null;
      }
   }
}
