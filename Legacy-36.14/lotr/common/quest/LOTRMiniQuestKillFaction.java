package lotr.common.quest;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRMiniQuestKillFaction extends LOTRMiniQuestKill {
   public LOTRFaction killFaction;

   public LOTRMiniQuestKillFaction(LOTRPlayerData pd) {
      super(pd);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.func_74778_a("KillFaction", this.killFaction.codeName());
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.killFaction = LOTRFaction.forName(nbt.func_74779_i("KillFaction"));
   }

   public boolean isValidQuest() {
      return super.isValidQuest() && this.killFaction != null;
   }

   protected String getKillTargetName() {
      return this.killFaction.factionEntityName();
   }

   public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
      if (this.killCount < this.killTarget && LOTRMod.getNPCFaction(entity) == this.killFaction) {
         ++this.killCount;
         this.updateQuest();
      }

   }

   public static class QFKillFaction extends LOTRMiniQuestKill.QFKill {
      private LOTRFaction killFaction;

      public QFKillFaction(String name) {
         super(name);
      }

      public LOTRMiniQuestKillFaction.QFKillFaction setKillFaction(LOTRFaction faction, int min, int max) {
         this.killFaction = faction;
         this.setKillTarget(min, max);
         return this;
      }

      public Class getQuestClass() {
         return LOTRMiniQuestKillFaction.class;
      }

      public LOTRMiniQuestKillFaction createQuest(LOTREntityNPC npc, Random rand) {
         LOTRMiniQuestKillFaction quest = (LOTRMiniQuestKillFaction)super.createQuest(npc, rand);
         quest.killFaction = this.killFaction;
         return quest;
      }
   }
}
