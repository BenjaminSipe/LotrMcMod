package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketMiniquestRemove implements IMessage {
   private UUID questUUID;
   private boolean wasCompleted;
   private boolean addToCompleted;

   public LOTRPacketMiniquestRemove() {
   }

   public LOTRPacketMiniquestRemove(LOTRMiniQuest quest, boolean wc, boolean atc) {
      this.questUUID = quest.questUUID;
      this.wasCompleted = wc;
      this.addToCompleted = atc;
   }

   public void toBytes(ByteBuf data) {
      data.writeLong(this.questUUID.getMostSignificantBits());
      data.writeLong(this.questUUID.getLeastSignificantBits());
      data.writeBoolean(this.wasCompleted);
      data.writeBoolean(this.addToCompleted);
   }

   public void fromBytes(ByteBuf data) {
      this.questUUID = new UUID(data.readLong(), data.readLong());
      this.wasCompleted = data.readBoolean();
      this.addToCompleted = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMiniquestRemove packet, MessageContext context) {
         if (!LOTRMod.proxy.isSingleplayer()) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRMiniQuest removeQuest = pd.getMiniQuestForID(packet.questUUID, packet.wasCompleted);
            if (removeQuest != null) {
               if (packet.addToCompleted) {
                  pd.completeMiniQuest(removeQuest);
               } else {
                  pd.removeMiniQuest(removeQuest, packet.wasCompleted);
               }
            } else {
               FMLLog.warning("Tried to remove a LOTR miniquest that doesn't exist", new Object[0]);
            }
         }

         return null;
      }
   }
}
