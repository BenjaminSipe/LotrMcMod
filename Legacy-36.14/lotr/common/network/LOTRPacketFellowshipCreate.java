package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipCreate implements IMessage {
   private String fellowshipName;

   public LOTRPacketFellowshipCreate() {
   }

   public LOTRPacketFellowshipCreate(String name) {
      this.fellowshipName = name;
   }

   public void toBytes(ByteBuf data) {
      byte[] nameBytes = this.fellowshipName.getBytes(Charsets.UTF_8);
      data.writeByte(nameBytes.length);
      data.writeBytes(nameBytes);
   }

   public void fromBytes(ByteBuf data) {
      int nameLength = data.readByte();
      ByteBuf nameBytes = data.readBytes(nameLength);
      this.fellowshipName = nameBytes.toString(Charsets.UTF_8);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowshipCreate packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
         playerData.createFellowship(packet.fellowshipName, true);
         return null;
      }
   }
}
