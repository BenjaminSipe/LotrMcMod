package lotr.common.quest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public abstract class LOTRMiniQuestCollectBase extends LOTRMiniQuest {
   public int collectTarget;
   public int amountGiven;

   public LOTRMiniQuestCollectBase(LOTRPlayerData pd) {
      super(pd);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.func_74768_a("Target", this.collectTarget);
      nbt.func_74768_a("Given", this.amountGiven);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.collectTarget = nbt.func_74762_e("Target");
      this.amountGiven = nbt.func_74762_e("Given");
   }

   public boolean isValidQuest() {
      return super.isValidQuest() && this.collectTarget > 0;
   }

   public String getQuestProgress() {
      return StatCollector.func_74837_a("lotr.miniquest.collect.progress", new Object[]{this.amountGiven, this.collectTarget});
   }

   public String getQuestProgressShorthand() {
      return StatCollector.func_74837_a("lotr.miniquest.progressShort", new Object[]{this.amountGiven, this.collectTarget});
   }

   public float getCompletionFactor() {
      return (float)this.amountGiven / (float)this.collectTarget;
   }

   public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
      int prevAmountGiven = this.amountGiven;
      List slotNumbers = new ArrayList();
      slotNumbers.add(entityplayer.field_71071_by.field_70461_c);

      for(int slot = 0; slot < entityplayer.field_71071_by.field_70462_a.length; ++slot) {
         if (!slotNumbers.contains(slot)) {
            slotNumbers.add(slot);
         }
      }

      Iterator var9 = slotNumbers.iterator();

      while(var9.hasNext()) {
         int slot = (Integer)var9.next();
         ItemStack itemstack = entityplayer.field_71071_by.field_70462_a[slot];
         if (itemstack != null && this.isQuestItem(itemstack)) {
            int amountRemaining = this.collectTarget - this.amountGiven;
            if (itemstack.field_77994_a >= amountRemaining) {
               itemstack.field_77994_a -= amountRemaining;
               if (itemstack.field_77994_a <= 0) {
                  itemstack = null;
               }

               entityplayer.field_71071_by.func_70299_a(slot, itemstack);
               this.amountGiven += amountRemaining;
            } else {
               this.amountGiven += itemstack.field_77994_a;
               entityplayer.field_71071_by.func_70299_a(slot, (ItemStack)null);
            }
         }

         if (this.amountGiven >= this.collectTarget) {
            this.complete(entityplayer, npc);
            break;
         }
      }

      if (this.amountGiven > prevAmountGiven && !this.isCompleted()) {
         this.updateQuest();
      }

      if (!this.isCompleted()) {
         this.sendProgressSpeechbank(entityplayer, npc);
      }

   }

   protected abstract boolean isQuestItem(ItemStack var1);

   public float getAlignmentBonus() {
      float f = (float)this.collectTarget;
      f *= this.rewardFactor;
      return Math.max(f, 1.0F);
   }

   public int getCoinBonus() {
      return Math.round(this.getAlignmentBonus() * 2.0F);
   }
}
