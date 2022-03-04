package lotr.common.entity.npc;

import lotr.common.LOTRCapes;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityRanger extends LOTREntityDunedain {
   public EntityAIBase rangedAttackAI = this.createDunedainRangedAI();
   public EntityAIBase meleeAttackAI;
   private int sneakCooldown = 0;
   private EntityLivingBase prevRangerTarget;

   public LOTREntityRanger(World world) {
      super(world);
      this.addTargetTasks(true);
      this.npcCape = LOTRCapes.RANGER;
   }

   protected EntityAIBase createDunedainAttackAI() {
      return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.5D, true);
   }

   protected EntityAIBase createDunedainRangedAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 20, 40, 20.0F);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public boolean isRangerSneaking() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setRangerSneaking(boolean flag) {
      this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(flag ? 1 : 0)));
      if (flag) {
         this.sneakCooldown = 20;
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.5D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
      this.npcItemsInv.setRangedWeapon(new ItemStack(Items.field_151031_f));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsRanger));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsRanger));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyRanger));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetRanger));
      return data;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70154_o == null) {
            if (this.isRangerSneaking()) {
               if (this.func_70638_az() == null) {
                  if (this.sneakCooldown > 0) {
                     --this.sneakCooldown;
                  } else {
                     this.setRangerSneaking(false);
                  }
               } else {
                  this.sneakCooldown = 20;
               }
            } else {
               this.sneakCooldown = 0;
            }
         } else if (this.isRangerSneaking()) {
            this.setRangerSneaking(false);
         }
      }

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

   public void setAttackTarget(EntityLivingBase target, boolean speak) {
      super.setAttackTarget(target, speak);
      if (target != null && target != this.prevRangerTarget) {
         this.prevRangerTarget = target;
         if (!this.field_70170_p.field_72995_K && !this.isRangerSneaking() && this.field_70154_o == null) {
            this.setRangerSneaking(true);
         }
      }

   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag && !this.field_70170_p.field_72995_K && this.isRangerSneaking()) {
         this.setRangerSneaking(false);
      }

      return flag;
   }

   public void func_71038_i() {
      super.func_71038_i();
      if (!this.field_70170_p.field_72995_K && this.isRangerSneaking()) {
         this.setRangerSneaking(false);
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      this.dropNPCArrows(i);
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
      if (!this.isRangerSneaking()) {
         super.func_145780_a(i, j, k, block);
      }

   }
}
