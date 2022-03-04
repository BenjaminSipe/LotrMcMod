package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketHiredInfo implements IMessage {
   private int entityID;
   public boolean isHired;
   public UUID hiringPlayer;
   public LOTRHiredNPCInfo.Task task;
   public String squadron;
   public int xpLvl;

   public LOTRPacketHiredInfo() {
   }

   public LOTRPacketHiredInfo(int i, UUID player, LOTRHiredNPCInfo.Task t, String sq, int lvl) {
      this.entityID = i;
      this.hiringPlayer = player;
      this.isHired = this.hiringPlayer != null;
      this.task = t;
      this.squadron = sq;
      this.xpLvl = lvl;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.entityID);
      data.writeBoolean(this.isHired);
      if (this.isHired) {
         data.writeLong(this.hiringPlayer.getMostSignificantBits());
         data.writeLong(this.hiringPlayer.getLeastSignificantBits());
      }

      data.writeByte(this.task.ordinal());
      if (StringUtils.func_151246_b(this.squadron)) {
         data.writeShort(-1);
      } else {
         byte[] sqBytes = this.squadron.getBytes(Charsets.UTF_8);
         data.writeShort(sqBytes.length);
         data.writeBytes(sqBytes);
      }

      data.writeShort(this.xpLvl);
   }

   public void fromBytes(ByteBuf data) {
      this.entityID = data.readInt();
      this.isHired = data.readBoolean();
      if (this.isHired) {
         this.hiringPlayer = new UUID(data.readLong(), data.readLong());
      } else {
         this.hiringPlayer = null;
      }

      this.task = LOTRHiredNPCInfo.Task.forID(data.readByte());
      int sqLength = data.readShort();
      if (sqLength > -1) {
         this.squadron = data.readBytes(sqLength).toString(Charsets.UTF_8);
      }

      this.xpLvl = data.readShort();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketHiredInfo packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         Entity entity = world.func_73045_a(packet.entityID);
         if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).hiredNPCInfo.receiveBasicData(packet);
         }

         return null;
      }
   }
}
