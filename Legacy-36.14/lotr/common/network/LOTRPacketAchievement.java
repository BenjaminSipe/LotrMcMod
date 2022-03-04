package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketAchievement implements IMessage {
   private LOTRAchievement achievement;
   private boolean display;

   public LOTRPacketAchievement() {
   }

   public LOTRPacketAchievement(LOTRAchievement ach, boolean disp) {
      this.achievement = ach;
      this.display = disp;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.achievement.category.ordinal());
      data.writeShort(this.achievement.ID);
      data.writeBoolean(this.display);
   }

   public void fromBytes(ByteBuf data) {
      int catID = data.readByte();
      int achID = data.readShort();
      LOTRAchievement.Category cat = LOTRAchievement.Category.values()[catID];
      this.achievement = LOTRAchievement.achievementForCategoryAndID(cat, achID);
      this.display = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketAchievement packet, MessageContext context) {
         LOTRAchievement achievement = packet.achievement;
         if (achievement != null) {
            if (!LOTRMod.proxy.isSingleplayer()) {
               EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
               LOTRLevelData.getData(entityplayer).addAchievement(achievement);
            }

            if (packet.display) {
               LOTRMod.proxy.queueAchievement(achievement);
            }
         }

         return null;
      }
   }
}
