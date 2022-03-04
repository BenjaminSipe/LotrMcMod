package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketNPCIsOfferingQuest implements IMessage {
   private int entityID;
   public boolean offering;
   public int offerColor;

   public LOTRPacketNPCIsOfferingQuest() {
   }

   public LOTRPacketNPCIsOfferingQuest(int id, boolean flag, int color) {
      this.entityID = id;
      this.offering = flag;
      this.offerColor = color;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);
      data.writeBoolean(this.offering);
      data.writeInt(this.offerColor);
   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();
      this.offering = data.readBoolean();
      this.offerColor = data.readInt();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketNPCIsOfferingQuest packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).questInfo.receiveData(packet);
         }

         return null;
      }
   }
}
