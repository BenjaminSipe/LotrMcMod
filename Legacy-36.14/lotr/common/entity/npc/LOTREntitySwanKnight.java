package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntitySwanKnight extends LOTREntityDolAmrothSoldier {
   public LOTREntitySwanKnight(World world) {
      super(world);
      this.addTargetTasks(true);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(4) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_DOL_AMROTH;
   }

   public EntityAIBase createGondorAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
      horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDolAmroth));
      return horse;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(horseAttackSpeed).func_111128_a(2.0D);
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.75D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextInt(4) == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearDolAmroth));
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDolAmroth));
      }

      if (this.field_70146_Z.nextInt(3) == 0) {
         this.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceDolAmroth));
      } else {
         this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDolAmroth));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDolAmroth));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDolAmroth));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetDolAmroth));
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

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killSwanKnight;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "gondor/swanKnight/hired" : "gondor/swanKnight/friendly";
      } else {
         return "gondor/swanKnight/hostile";
      }
   }
}
