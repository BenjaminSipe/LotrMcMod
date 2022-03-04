package lotr.common.quest;

import java.util.Random;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRMiniQuestKillEntity extends LOTRMiniQuestKill {
   public Class entityType;

   public LOTRMiniQuestKillEntity(LOTRPlayerData pd) {
      super(pd);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.func_74778_a("KillClass", LOTREntities.getStringFromClass(this.entityType));
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.entityType = LOTREntities.getClassFromString(nbt.func_74779_i("KillClass"));
   }

   public boolean isValidQuest() {
      return super.isValidQuest() && this.entityType != null && EntityLivingBase.class.isAssignableFrom(this.entityType);
   }

   protected String getKillTargetName() {
      String entityName = LOTREntities.getStringFromClass(this.entityType);
      return StatCollector.func_74838_a("entity." + entityName + ".name");
   }

   public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
      if (this.killCount < this.killTarget && this.entityType.isAssignableFrom(entity.getClass())) {
         ++this.killCount;
         this.updateQuest();
      }

   }

   public static class QFKillEntity extends LOTRMiniQuestKill.QFKill {
      private Class entityType;

      public QFKillEntity(String name) {
         super(name);
      }

      public LOTRMiniQuestKillEntity.QFKillEntity setKillEntity(Class entityClass, int min, int max) {
         this.entityType = entityClass;
         this.setKillTarget(min, max);
         return this;
      }

      public Class getQuestClass() {
         return LOTRMiniQuestKillEntity.class;
      }

      public LOTRMiniQuestKillEntity createQuest(LOTREntityNPC npc, Random rand) {
         LOTRMiniQuestKillEntity quest = (LOTRMiniQuestKillEntity)super.createQuest(npc, rand);
         quest.entityType = this.entityType;
         return quest;
      }
   }
}
