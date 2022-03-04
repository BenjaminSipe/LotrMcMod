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

public class LOTREntityMordorOrc extends LOTREntityOrc {
   public LOTREntityMordorOrc(World world) {
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
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeOrc));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerOrc));
      } else if (i == 2) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerOrcPoisoned));
      } else if (i == 3) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarOrc));
      } else if (i == 4) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerOrc));
      } else if (i == 5) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeOrc));
      } else if (i == 6) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeOrc));
      } else if (i == 7) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmOrc));
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearOrc));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsOrc));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsOrc));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyOrc));
      if (this.field_70146_Z.nextInt(5) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetOrc));
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.MORDOR;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killMordorOrc;
   }

   protected void dropOrcItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.ORC_TENT, 1, 2 + i);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "mordor/orc/hired";
         } else {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F ? "mordor/orc/friendly" : "mordor/orc/neutral";
         }
      } else {
         return "mordor/orc/hostile";
      }
   }

   protected String getOrcSkirmishSpeech() {
      return "mordor/orc/skirmish";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.MORDOR.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.MORDOR;
   }
}
