package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketHiredUnitDismiss implements IMessage {
   private int entityID;
   private int action;

   public LOTRPacketHiredUnitDismiss() {
   }

   public LOTRPacketHiredUnitDismiss(int id, int a) {
      this.entityID = id;
      this.action = a;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);
      data.writeByte(this.action);
   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();
      this.action = data.readByte();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketHiredUnitDismiss packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Entity npc = world.func_73045_a(packet.entityID);
         if (npc != null && npc instanceof LOTREntityNPC) {
            LOTREntityNPC hiredNPC = (LOTREntityNPC)npc;
            if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
               int action = packet.action;
               boolean closeScreen = false;
               if (action == 0) {
                  hiredNPC.hiredNPCInfo.dismissUnit(false);
                  Entity mount = hiredNPC.field_70154_o;
                  Entity rider = hiredNPC.field_70153_n;
                  LOTREntityNPC riderNPC;
                  if (mount instanceof LOTREntityNPC) {
                     riderNPC = (LOTREntityNPC)mount;
                     if (riderNPC.hiredNPCInfo.isActive && riderNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                        riderNPC.hiredNPCInfo.dismissUnit(false);
                     }
                  }

                  if (rider instanceof LOTREntityNPC) {
                     riderNPC = (LOTREntityNPC)rider;
                     if (riderNPC.hiredNPCInfo.isActive && riderNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                        riderNPC.hiredNPCInfo.dismissUnit(false);
                     }
                  }

                  closeScreen = true;
               } else if (action == 1) {
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
