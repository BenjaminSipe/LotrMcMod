package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;

public class LOTRPacketSelectTitle implements IMessage {
   private LOTRTitle.PlayerTitle playerTitle;

   public LOTRPacketSelectTitle() {
   }

   public LOTRPacketSelectTitle(LOTRTitle.PlayerTitle t) {
      this.playerTitle = t;
   }

   public void toBytes(ByteBuf data) {
      if (this.playerTitle == null) {
         data.writeShort(-1);
         data.writeByte(-1);
      } else {
         data.writeShort(this.playerTitle.getTitle().titleID);
         data.writeByte(this.playerTitle.getColor().func_96298_a());
      }

   }

   public void fromBytes(ByteBuf data) {
      int titleID = data.readShort();
      LOTRTitle title = LOTRTitle.forID(titleID);
      int colorID = data.readByte();
      EnumChatFormatting color = LOTRTitle.PlayerTitle.colorForID(colorID);
      if (title != null && color != null) {
         this.playerTitle = new LOTRTitle.PlayerTitle(title, color);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketSelectTitle packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         LOTRTitle.PlayerTitle title = packet.playerTitle;
         if (title == null) {
            pd.setPlayerTitle((LOTRTitle.PlayerTitle)null);
         } else if (title.getTitle().canPlayerUse(entityplayer)) {
            pd.setPlayerTitle(title);
         }

         return null;
      }
   }
}
