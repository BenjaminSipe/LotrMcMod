package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohirrimWarrior extends LOTREntityRohanMan {
   public LOTREntityRohirrimWarrior(World world) {
      super(world);
      this.addTargetTasks(true);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(3) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_ROHAN;
   }

   public EntityAIBase createRohanAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.45D, false);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.75D);
      this.func_110148_a(horseAttackSpeed).func_111128_a(2.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRohan));
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
      }

      if (this.field_70146_Z.nextInt(4) == 0) {
         this.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceRohan));
      } else {
         this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
      }

      if (this.field_70146_Z.nextInt(4) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRohan));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsRohan));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsRohan));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyRohan));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetRohan));
      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         if (mounted) {
            this.func_70062_b(0, this.npcItemsInv.getIdleItemMounted());
         } else {
            this.func_70062_b(0, this.npcItemsInv.getIdleItem());
         }
      } else if (mounted) {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeaponMounted());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "rohan/warrior/hired" : "rohan/warrior/friendly";
      } else {
         return "rohan/warrior/hostile";
      }
   }
}
