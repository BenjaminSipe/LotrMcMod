package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketNPCSquadron implements IMessage {
   private int npcID;
   private String squadron;

   public LOTRPacketNPCSquadron() {
   }

   public LOTRPacketNPCSquadron(LOTREntityNPC npc, String s) {
      this.npcID = npc.func_145782_y();
      this.squadron = s;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.npcID);
      if (StringUtils.func_151246_b(this.squadron)) {
         data.writeInt(-1);
      } else {
         byte[] sqBytes = this.squadron.getBytes(Charsets.UTF_8);
         data.writeInt(sqBytes.length);
         data.writeBytes(sqBytes);
      }

   }

   public void fromBytes(ByteBuf data) {
      this.npcID = data.readInt();
      int length = data.readInt();
      if (length > -1) {
         this.squadron = data.readBytes(length).toString(Charsets.UTF_8);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketNPCSquadron packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Entity npc = world.func_73045_a(packet.npcID);
         if (npc != null && npc instanceof LOTREntityNPC) {
            LOTREntityNPC hiredNPC = (LOTREntityNPC)npc;
            if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
               String squadron = packet.squadron;
               if (!StringUtils.func_151246_b(squadron)) {
                  squadron = LOTRSquadrons.checkAcceptableLength(squadron);
                  hiredNPC.hiredNPCInfo.setSquadron(squadron);
               } else {
                  hiredNPC.hiredNPCInfo.setSquadron("");
               }
            }
         }

         return null;
      }
   }
}
