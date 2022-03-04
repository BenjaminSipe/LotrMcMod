package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.BowItem;

public class NPCRangedAttackGoal extends Goal {
   private final MobEntity entity;
   private final Random rand;
   private final double moveSpeed;
   private int attackCooldown;
   private final float maxAttackDistanceSq;
   private int attackTime = -1;
   private static final int ATTACK_CHARGE_TIME = 20;
   private int seeTime;
   private boolean strafingClockwise;
   private boolean strafingBackwards;
   private int strafingTime = -1;

   public NPCRangedAttackGoal(MobEntity entity, double moveSpeed, int attackCooldown, float maxAttackDistance) {
      this.entity = entity;
      this.rand = entity.func_70681_au();
      this.moveSpeed = moveSpeed;
      this.attackCooldown = attackCooldown;
      this.maxAttackDistanceSq = maxAttackDistance * maxAttackDistance;
      this.func_220684_a(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public void setAttackCooldown(int cooldown) {
      this.attackCooldown = cooldown;
   }

   protected Predicate testRangedWeapon() {
      return (item) -> {
         return item instanceof BowItem;
      };
   }

   public boolean func_75250_a() {
      return this.entity.func_70638_az() == null ? false : this.isBowInMainhand();
   }

   protected boolean isBowInMainhand() {
      return this.entity.func_233634_a_(this.testRangedWeapon());
   }

   public boolean func_75253_b() {
      return (this.func_75250_a() || !this.entity.func_70661_as().func_75500_f()) && this.isBowInMainhand();
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.entity.func_213395_q(true);
   }

   public void func_75251_c() {
      super.func_75251_c();
      this.entity.func_213395_q(false);
      this.seeTime = 0;
      this.attackTime = -1;
      this.entity.func_184602_cy();
      this.entity.func_70661_as().func_75499_g();
   }

   public void func_75246_d() {
      LivingEntity target = this.entity.func_70638_az();
      if (target != null) {
         double dSq = this.entity.func_70092_e(target.func_226277_ct_(), target.func_226278_cu_(), target.func_226281_cx_());
         boolean canSee = this.entity.func_70635_at().func_75522_a(target);
         boolean wasSeeing = this.seeTime > 0;
         if (canSee != wasSeeing) {
            this.seeTime = 0;
         }

         if (canSee) {
            ++this.seeTime;
         } else {
            --this.seeTime;
         }

         if (dSq <= (double)this.maxAttackDistanceSq && this.seeTime >= 20) {
            this.entity.func_70661_as().func_75499_g();
            ++this.strafingTime;
         } else {
            this.entity.func_70661_as().func_75497_a(target, this.moveSpeed);
            this.strafingTime = -1;
         }

         if (this.strafingTime >= 20) {
            if ((double)this.rand.nextFloat() < 0.3D) {
               this.strafingClockwise = !this.strafingClockwise;
            }

            if ((double)this.rand.nextFloat() < 0.3D) {
               this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
         }

         if (this.strafingTime > -1) {
            if (dSq > (double)(this.maxAttackDistanceSq * 0.75F)) {
               this.strafingBackwards = false;
            } else if (dSq < (double)(this.maxAttackDistanceSq * 0.25F)) {
               this.strafingBackwards = true;
            }

            this.entity.func_70605_aq().func_188488_a(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            this.entity.func_70625_a(target, 30.0F, 30.0F);
         } else {
            this.entity.func_70671_ap().func_75651_a(target, 30.0F, 30.0F);
         }

         if (this.entity.func_184587_cr()) {
            if (!canSee && this.seeTime < -60) {
               this.entity.func_184602_cy();
            } else if (canSee) {
               int useCount = this.entity.func_184612_cw();
               if (useCount >= 20) {
                  this.entity.func_184602_cy();
                  ((IRangedAttackMob)this.entity).func_82196_d(target, BowItem.func_185059_b(useCount));
                  this.attackTime = this.attackCooldown;
               }
            }
         } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
            this.entity.func_184598_c(ProjectileHelper.getWeaponHoldingHand(this.entity, this.testRangedWeapon()));
         }
      }

   }
}
