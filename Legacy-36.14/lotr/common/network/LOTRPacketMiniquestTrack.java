package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketMiniquestTrack implements IMessage {
   private UUID questID;

   public LOTRPacketMiniquestTrack() {
   }

   public LOTRPacketMiniquestTrack(LOTRMiniQuest quest) {
      this.questID = quest == null ? null : quest.questUUID;
   }

   public void toBytes(ByteBuf data) {
      boolean hasQuest = this.questID != null;
      data.writeBoolean(hasQuest);
      if (hasQuest) {
         data.writeLong(this.questID.getMostSignificantBits());
         data.writeLong(this.questID.getLeastSignificantBits());
      }

   }

   public void fromBytes(ByteBuf data) {
      boolean hasQuest = data.readBoolean();
      if (hasQuest) {
         this.questID = new UUID(data.readLong(), data.readLong());
      } else {
         this.questID = null;
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMiniquestTrack packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         if (packet.questID == null) {
            pd.setTrackingMiniQuestID((UUID)null);
         } else {
            pd.setTrackingMiniQuestID(packet.questID);
         }

         return null;
      }
   }
}
