package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDolGuldurOrc extends LOTREntityOrc {
   public LOTREntityDolGuldurOrc(World world) {
      super(world);
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(8);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDolGuldur));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDolGuldur));
      } else if (i == 2) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDolGuldurPoisoned));
      } else if (i == 3) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDolGuldur));
      } else if (i == 4) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerDolGuldur));
      } else if (i == 5) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeDolGuldur));
      } else if (i == 6) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeDolGuldur));
      } else if (i == 7) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDolGuldur));
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDolGuldur));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDolGuldur));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDolGuldur));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDolGuldur));
      if (this.field_70146_Z.nextInt(5) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetDolGuldur));
      }

      if (!this.field_70170_p.field_72995_K && this.spawnRidingHorse && !(this instanceof LOTRBannerBearer)) {
         LOTREntityMirkwoodSpider spider = new LOTREntityMirkwoodSpider(this.field_70170_p);
         spider.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
         if (this.field_70170_p.func_147461_a(spider.field_70121_D).isEmpty()) {
            spider.func_110161_a((IEntityLivingData)null);
            spider.isNPCPersistent = this.isNPCPersistent;
            this.field_70170_p.func_72838_d(spider);
            this.func_70078_a(spider);
         }
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.DOL_GULDUR;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killDolGuldurOrc;
   }

   protected void dropOrcItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.DOL_GULDUR_TENT, 1, 2 + i);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "dolGuldur/orc/hired";
         } else {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F ? "dolGuldur/orc/friendly" : "dolGuldur/orc/neutral";
         }
      } else {
         return "dolGuldur/orc/hostile";
      }
   }

   protected String getOrcSkirmishSpeech() {
      return "dolGuldur/orc/skirmish";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.DOL_GULDUR.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.DOL_GULDUR;
   }
}
