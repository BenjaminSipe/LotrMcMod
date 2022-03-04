package lotr.common.quest;

import java.util.Random;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public abstract class LOTRMiniQuestKill extends LOTRMiniQuest {
   public int killTarget;
   public int killCount;

   public LOTRMiniQuestKill(LOTRPlayerData pd) {
      super(pd);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.func_74768_a("Target", this.killTarget);
      nbt.func_74768_a("Count", this.killCount);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.killTarget = nbt.func_74762_e("Target");
      this.killCount = nbt.func_74762_e("Count");
   }

   public boolean isValidQuest() {
      return super.isValidQuest() && this.killTarget > 0;
   }

   public String getQuestObjective() {
      return StatCollector.func_74837_a("lotr.miniquest.kill", new Object[]{this.killTarget, this.getKillTargetName()});
   }

   protected abstract String getKillTargetName();

   public String getObjectiveInSpeech() {
      return this.killTarget + " " + this.getKillTargetName();
   }

   public String getProgressedObjectiveInSpeech() {
      return this.killTarget - this.killCount + " " + this.getKillTargetName();
   }

   public String getQuestProgress() {
      return StatCollector.func_74837_a("lotr.miniquest.kill.progress", new Object[]{this.killCount, this.killTarget});
   }

   public String getQuestProgressShorthand() {
      return StatCollector.func_74837_a("lotr.miniquest.progressShort", new Object[]{this.killCount, this.killTarget});
   }

   public float getCompletionFactor() {
      return (float)this.killCount / (float)this.killTarget;
   }

   public ItemStack getQuestIcon() {
      return new ItemStack(Items.field_151040_l);
   }

   public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
      if (this.killCount >= this.killTarget) {
         this.complete(entityplayer, npc);
      } else {
         this.sendProgressSpeechbank(entityplayer, npc);
      }

   }

   public float getAlignmentBonus() {
      return (float)this.killTarget * this.rewardFactor;
   }

   public int getCoinBonus() {
      return Math.round((float)this.killTarget * 2.0F * this.rewardFactor);
   }

   public abstract static class QFKill extends LOTRMiniQuest.QuestFactoryBase {
      private int minTarget;
      private int maxTarget;

      public QFKill(String name) {
         super(name);
      }

      public LOTRMiniQuestKill.QFKill setKillTarget(int min, int max) {
         this.minTarget = min;
         this.maxTarget = max;
         return this;
      }

      public LOTRMiniQuestKill createQuest(LOTREntityNPC npc, Random rand) {
         LOTRMiniQuestKill quest = (LOTRMiniQuestKill)super.createQuest(npc, rand);
         quest.killTarget = MathHelper.func_76136_a(rand, this.minTarget, this.maxTarget);
         return quest;
      }
   }
}
