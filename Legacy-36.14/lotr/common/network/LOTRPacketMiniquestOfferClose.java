package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketMiniquestOfferClose implements IMessage {
   private int npcID;
   private boolean accept;

   public LOTRPacketMiniquestOfferClose() {
   }

   public LOTRPacketMiniquestOfferClose(int id, boolean flag) {
      this.npcID = id;
      this.accept = flag;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.npcID);
      data.writeBoolean(this.accept);
   }

   public void fromBytes(ByteBuf data) {
      this.npcID = data.readInt();
      this.accept = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMiniquestOfferClose packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Entity npcEntity = world.func_73045_a(packet.npcID);
         if (npcEntity instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC)npcEntity;
            npc.questInfo.receiveOfferResponse(entityplayer, packet.accept);
         }

         return null;
      }
   }
}
