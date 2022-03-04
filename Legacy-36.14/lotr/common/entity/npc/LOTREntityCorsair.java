package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityCorsair extends LOTREntityUmbarian {
   private EntityAIBase rangedAttackAI = this.createHaradrimRangedAttackAI();
   private EntityAIBase meleeAttackAI;
   private static ItemStack[] weaponsCorsair;

   public LOTREntityCorsair(World world) {
      super(world);
      this.addTargetTasks(true);
      this.spawnRidingHorse = false;
      this.npcShield = LOTRShields.ALIGNMENT_CORSAIR;
   }

   protected EntityAIBase createHaradrimAttackAI() {
      return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6D, true);
   }

   protected EntityAIBase createHaradrimRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.5D, 30, 40, 16.0F);
   }

   protected LOTRFoods getHaradrimFoods() {
      return LOTRFoods.CORSAIR;
   }

   protected LOTRFoods getHaradrimDrinks() {
      return LOTRFoods.CORSAIR_DRINK;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.5D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weaponsCorsair.length);
      this.npcItemsInv.setMeleeWeapon(weaponsCorsair[i].func_77946_l());
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearCorsair));
      }

      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.nearHaradBow));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsCorsair));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsCorsair));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyCorsair));
      if (this.field_70146_Z.nextInt(2) == 0) {
         this.func_70062_b(4, (ItemStack)null);
      } else {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetCorsair));
      }

      return data;
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      }

      if (mode == LOTREntityNPC.AttackMode.MELEE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

      if (mode == LOTREntityNPC.AttackMode.RANGED) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getRangedWeapon());
      }

   }

   public boolean lootsExtraCoins() {
      return true;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      this.dropNPCArrows(i);
   }

   protected void dropHaradrimItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.dropChestContents(LOTRChestContents.CORSAIR, 1, 2 + i);
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "nearHarad/umbar/corsair/hired" : "nearHarad/umbar/corsair/friendly";
      } else {
         return "nearHarad/umbar/corsair/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.CORSAIR.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.CORSAIR;
   }

   static {
      weaponsCorsair = new ItemStack[]{new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.daggerCorsair), new ItemStack(LOTRMod.daggerCorsairPoisoned), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.battleaxeCorsair), new ItemStack(LOTRMod.battleaxeCorsair)};
   }
}
