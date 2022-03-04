package lotr.common.entity.npc;

import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

public interface IBandit {
   LOTREntityNPC getBanditAsNPC();

   int getMaxThefts();

   LOTRInventoryNPC getBanditInventory();

   boolean canTargetPlayerForTheft(EntityPlayer var1);

   String getTheftSpeechBank(EntityPlayer var1);

   IChatComponent getTheftChatMsg(EntityPlayer var1);

   public static class Helper {
      public static LOTRInventoryNPC createInv(IBandit bandit) {
         return new LOTRInventoryNPC("BanditInventory", bandit.getBanditAsNPC(), bandit.getMaxThefts());
      }

      public static boolean canStealFromPlayerInv(IBandit bandit, EntityPlayer entityplayer) {
         for(int slot = 0; slot < entityplayer.field_71071_by.field_70462_a.length; ++slot) {
            if (slot != entityplayer.field_71071_by.field_70461_c && entityplayer.field_71071_by.func_70301_a(slot) != null) {
               return true;
            }
         }

         return false;
      }
   }
}
