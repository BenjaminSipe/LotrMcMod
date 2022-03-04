package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketFamilyInfo implements IMessage {
   private int entityID;
   public int age;
   public boolean isMale;
   public String name;
   public boolean isDrunk;

   public LOTRPacketFamilyInfo() {
   }

   public LOTRPacketFamilyInfo(int id, int a, boolean m, String s, boolean drunk) {
      this.entityID = id;
      this.age = a;
      this.isMale = m;
      this.name = s;
      this.isDrunk = drunk;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);
      data.writeInt(this.age);
      data.writeBoolean(this.isMale);
      if (this.name == null) {
         data.writeShort(-1);
      } else {
         byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
         data.writeShort(nameBytes.length);
         data.writeBytes(nameBytes);
      }

      data.writeBoolean(this.isDrunk);
   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();
      this.age = data.readInt();
      this.isMale = data.readBoolean();
      int nameLength = data.readShort();
      if (nameLength > -1) {
         this.name = data.readBytes(nameLength).toString(Charsets.UTF_8);
      }

      this.isDrunk = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFamilyInfo packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).familyInfo.receiveData(packet);
         }

         return null;
      }
   }
}
