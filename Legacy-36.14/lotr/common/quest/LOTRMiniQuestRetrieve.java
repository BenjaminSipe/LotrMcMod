package lotr.common.quest;

import java.util.Random;
import java.util.UUID;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRMiniQuestRetrieve extends LOTRMiniQuestCollect {
   public Class killEntityType;
   public boolean hasDropped = false;

   public LOTRMiniQuestRetrieve(LOTRPlayerData pd) {
      super(pd);
   }

   public static UUID getRetrieveQuestID(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("LOTRRetrieveID")) {
         String id = itemstack.func_77978_p().func_74779_i("LOTRRetrieveID");
         return UUID.fromString(id);
      } else {
         return null;
      }
   }

   public static void setRetrieveQuest(ItemStack itemstack, LOTRMiniQuest quest) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74778_a("LOTRRetrieveID", quest.questUUID.toString());
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.func_74778_a("KillClass", LOTREntities.getStringFromClass(this.killEntityType));
      nbt.func_74757_a("HasDropped", this.hasDropped);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.killEntityType = LOTREntities.getClassFromString(nbt.func_74779_i("KillClass"));
      this.hasDropped = nbt.func_74767_n("HasDropped");
   }

   public boolean isValidQuest() {
      return super.isValidQuest() && this.killEntityType != null && EntityLivingBase.class.isAssignableFrom(this.killEntityType);
   }

   public String getQuestObjective() {
      return this.collectTarget == 1 ? StatCollector.func_74837_a("lotr.miniquest.retrieve1", new Object[]{this.collectItem.func_82833_r()}) : StatCollector.func_74837_a("lotr.miniquest.retrieveMany", new Object[]{this.collectTarget, this.collectItem.func_82833_r()});
   }

   public String getProgressedObjectiveInSpeech() {
      return this.collectTarget == 1 ? this.collectItem.func_82833_r() : this.collectTarget + " " + this.collectItem.func_82833_r();
   }

   protected boolean isQuestItem(ItemStack itemstack) {
      if (super.isQuestItem(itemstack)) {
         UUID retrieveQuestID = getRetrieveQuestID(itemstack);
         return retrieveQuestID.equals(this.questUUID);
      } else {
         return false;
      }
   }

   public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
      if (!this.hasDropped && this.killEntityType.isAssignableFrom(entity.getClass())) {
         ItemStack itemstack = this.collectItem.func_77946_l();
         setRetrieveQuest(itemstack, this);
         this.hasDropped = true;
         this.updateQuest();
      }

   }

   public static class QFRetrieve extends LOTRMiniQuestCollect.QFCollect {
      private Class entityType;

      public QFRetrieve(String name) {
         super(name);
      }

      public LOTRMiniQuestRetrieve.QFRetrieve setKillEntity(Class entityClass) {
         this.entityType = entityClass;
         return this;
      }

      public Class getQuestClass() {
         return LOTRMiniQuestRetrieve.class;
      }

      public LOTRMiniQuestRetrieve createQuest(LOTREntityNPC npc, Random rand) {
         LOTRMiniQuestRetrieve quest = (LOTRMiniQuestRetrieve)super.createQuest(npc, rand);
         quest.killEntityType = this.entityType;
         return quest;
      }
   }
}
