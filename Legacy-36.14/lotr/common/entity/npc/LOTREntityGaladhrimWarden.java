package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityGaladhrimWarden extends LOTREntityGaladhrimElf {
   private int sneakCooldown = 0;
   private EntityLivingBase prevElfTarget;

   public LOTREntityGaladhrimWarden(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, this.rangedAttackAI);
   }

   protected EntityAIBase createElfRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 25, 35, 24.0F);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public boolean isElfSneaking() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setElfSneaking(boolean flag) {
      this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(flag ? 1 : 0)));
      if (flag) {
         this.sneakCooldown = 20;
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerElven));
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mallornBow));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsHithlain));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsHithlain));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyHithlain));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetHithlain));
      }

      return data;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         if (this.isElfSneaking()) {
            if (this.func_70638_az() == null) {
               if (this.sneakCooldown > 0) {
                  --this.sneakCooldown;
               } else {
                  this.setElfSneaking(false);
               }
            } else {
               this.sneakCooldown = 20;
            }
         } else {
            this.sneakCooldown = 0;
         }
      }

   }

   public void setAttackTarget(EntityLivingBase target, boolean speak) {
      super.setAttackTarget(target, speak);
      if (target != null && target != this.prevElfTarget) {
         this.prevElfTarget = target;
         if (!this.field_70170_p.field_72995_K && !this.isElfSneaking()) {
            this.setElfSneaking(true);
         }
      }

   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag && !this.field_70170_p.field_72995_K && this.isElfSneaking()) {
         this.setElfSneaking(false);
      }

      return flag;
   }

   public void func_71038_i() {
      super.func_71038_i();
      if (!this.field_70170_p.field_72995_K && this.isElfSneaking()) {
         this.setElfSneaking(false);
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "galadhrim/elf/hired" : "galadhrim/warrior/friendly";
      } else {
         return "galadhrim/warrior/hostile";
      }
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
      if (!this.isElfSneaking()) {
         super.func_145780_a(i, j, k, block);
      }

   }
}
