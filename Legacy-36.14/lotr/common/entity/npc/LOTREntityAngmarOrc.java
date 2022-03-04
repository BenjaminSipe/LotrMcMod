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
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarOrc extends LOTREntityOrc {
   public LOTREntityAngmarOrc(World world) {
      super(world);
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(10);
      if (i != 0 && i != 1 && i != 2) {
         if (i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeAngmar));
         } else if (i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerAngmar));
         } else if (i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerAngmarPoisoned));
         } else if (i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerAngmar));
         } else if (i == 7) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeAngmar));
         } else if (i == 8) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeAngmar));
         } else if (i == 9) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmAngmar));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordAngmar));
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearAngmar));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsAngmar));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsAngmar));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyAngmar));
      if (this.field_70146_Z.nextInt(5) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetAngmar));
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.ANGMAR;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killAngmarOrc;
   }

   protected void dropOrcItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.ANGMAR_TENT, 1, 2 + i);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "angmar/orc/hired";
         } else {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F ? "angmar/orc/friendly" : "angmar/orc/neutral";
         }
      } else {
         return "angmar/orc/hostile";
      }
   }

   protected String getOrcSkirmishSpeech() {
      return "angmar/orc/skirmish";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.ANGMAR.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.ANGMAR;
   }
}
