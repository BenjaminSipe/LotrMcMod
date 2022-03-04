package lotr.common.quest;

import java.util.Random;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class LOTRMiniQuestCollect extends LOTRMiniQuestCollectBase {
   public ItemStack collectItem;

   public LOTRMiniQuestCollect(LOTRPlayerData pd) {
      super(pd);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      if (this.collectItem != null) {
         NBTTagCompound itemData = new NBTTagCompound();
         this.collectItem.func_77955_b(itemData);
         nbt.func_74782_a("Item", itemData);
      }

   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      if (nbt.func_74764_b("Item")) {
         NBTTagCompound itemData = nbt.func_74775_l("Item");
         this.collectItem = ItemStack.func_77949_a(itemData);
      }

   }

   public boolean isValidQuest() {
      return super.isValidQuest() && this.collectItem != null;
   }

   public String getQuestObjective() {
      return StatCollector.func_74837_a("lotr.miniquest.collect", new Object[]{this.collectTarget, this.collectItem.func_82833_r()});
   }

   public String getObjectiveInSpeech() {
      return this.collectTarget + " " + this.collectItem.func_82833_r();
   }

   public String getProgressedObjectiveInSpeech() {
      return this.collectTarget - this.amountGiven + " " + this.collectItem.func_82833_r();
   }

   public ItemStack getQuestIcon() {
      return this.collectItem;
   }

   protected boolean isQuestItem(ItemStack itemstack) {
      if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
         return false;
      } else if (LOTRItemMug.isItemFullDrink(this.collectItem)) {
         ItemStack collectDrink = LOTRItemMug.getEquivalentDrink(this.collectItem);
         ItemStack offerDrink = LOTRItemMug.getEquivalentDrink(itemstack);
         return collectDrink.func_77973_b() == offerDrink.func_77973_b();
      } else {
         return itemstack.func_77973_b() == this.collectItem.func_77973_b() && (this.collectItem.func_77960_j() == 32767 || itemstack.func_77960_j() == this.collectItem.func_77960_j());
      }
   }

   public static class QFCollect extends LOTRMiniQuest.QuestFactoryBase {
      private ItemStack collectItem;
      private int minTarget;
      private int maxTarget;

      public QFCollect(String name) {
         super(name);
      }

      public LOTRMiniQuestCollect.QFCollect setCollectItem(ItemStack itemstack, int min, int max) {
         this.collectItem = itemstack;
         if (this.collectItem.func_77984_f()) {
            this.collectItem.func_77964_b(32767);
         }

         this.minTarget = min;
         this.maxTarget = max;
         return this;
      }

      public Class getQuestClass() {
         return LOTRMiniQuestCollect.class;
      }

      public LOTRMiniQuestCollect createQuest(LOTREntityNPC npc, Random rand) {
         LOTRMiniQuestCollect quest = (LOTRMiniQuestCollect)super.createQuest(npc, rand);
         quest.collectItem = this.collectItem.func_77946_l();
         quest.collectTarget = MathHelper.func_76136_a(rand, this.minTarget, this.maxTarget);
         return quest;
      }
   }
}
