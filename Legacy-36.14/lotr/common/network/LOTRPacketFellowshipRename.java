package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipRename extends LOTRPacketFellowshipDo {
   private String newName;

   public LOTRPacketFellowshipRename() {
   }

   public LOTRPacketFellowshipRename(LOTRFellowshipClient fs, String name) {
      super(fs);
      this.newName = name;
   }

   public void toBytes(ByteBuf data) {
      super.toBytes(data);
      byte[] nameBytes = this.newName.getBytes(Charsets.UTF_8);
      data.writeByte(nameBytes.length);
      data.writeBytes(nameBytes);
   }

   public void fromBytes(ByteBuf data) {
      super.fromBytes(data);
      int nameLength = data.readByte();
      ByteBuf nameBytes = data.readBytes(nameLength);
      this.newName = nameBytes.toString(Charsets.UTF_8);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFellowshipRename packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRFellowship fellowship = packet.getActiveFellowship();
         if (fellowship != null) {
            LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            playerData.renameFellowship(fellowship, packet.newName);
         }

         return null;
      }
   }
}
