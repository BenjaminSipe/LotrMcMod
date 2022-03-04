package lotr.common.entity.ai;

import java.util.List;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class LOTREntityAIBalrogCharge extends LOTREntityAIAttackOnCollide {
   private LOTREntityBalrog theBalrog;
   private float chargeDist;
   private int frustrationTime;
   private boolean hitChargeTarget = false;
   private int chargingTick;

   public LOTREntityAIBalrogCharge(LOTREntityBalrog balrog, double speed, float dist, int fr) {
      super(balrog, speed, false);
      this.theBalrog = balrog;
      this.chargeDist = dist;
      this.frustrationTime = fr;
   }

   public boolean func_75250_a() {
      if (this.theBalrog.isBalrogCharging()) {
         return false;
      } else {
         boolean flag = super.func_75250_a();
         if (flag) {
            if (this.theBalrog.chargeFrustration >= this.frustrationTime) {
               return true;
            } else {
               double dist = this.theBalrog.func_70068_e(this.attackTarget);
               return dist >= (double)(this.chargeDist * this.chargeDist);
            }
         } else {
            return false;
         }
      }
   }

   public boolean func_75253_b() {
      if (!this.theBalrog.func_70089_S()) {
         return false;
      } else {
         this.attackTarget = this.theBalrog.func_70638_az();
         if (this.attackTarget != null && this.attackTarget.func_70089_S()) {
            return this.chargingTick > 0 && !this.hitChargeTarget;
         } else {
            return false;
         }
      }
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.theBalrog.setBalrogCharging(true);
      this.hitChargeTarget = false;
      this.chargingTick = 200;
   }

   public void func_75251_c() {
      super.func_75251_c();
      this.theBalrog.setBalrogCharging(false);
      this.hitChargeTarget = false;
      this.chargingTick = 0;
   }

   public void func_75246_d() {
      this.updateLookAndPathing();
      if (this.chargingTick > 0) {
         --this.chargingTick;
      }

      AxisAlignedBB targetBB = this.theBalrog.field_70121_D.func_72314_b(0.5D, 0.0D, 0.5D);
      double motionExtent = 2.0D;
      float angleRad = (float)Math.atan2(this.theBalrog.field_70179_y, this.theBalrog.field_70159_w);
      targetBB = targetBB.func_72325_c((double)MathHelper.func_76134_b(angleRad) * motionExtent, 0.0D, (double)MathHelper.func_76126_a(angleRad) * motionExtent);
      List hitTargets = this.worldObj.func_72839_b(this.theBalrog, targetBB);
      boolean hitAnyEntities = false;

      for(int i = 0; i < hitTargets.size(); ++i) {
         Entity obj = (Entity)hitTargets.get(i);
         if (obj instanceof EntityLivingBase) {
            EntityLivingBase hitEntity = (EntityLivingBase)obj;
            if (hitEntity != this.theBalrog.field_70153_n) {
               float attackStr = (float)this.theBalrog.func_110148_a(LOTREntityBalrog.balrogChargeDamage).func_111126_e();
               boolean flag = hitEntity.func_70097_a(DamageSource.func_76358_a(this.theBalrog), attackStr);
               if (flag) {
                  float knock = 2.5F;
                  float knockY = 0.5F;
                  hitEntity.func_70024_g((double)(-MathHelper.func_76126_a((float)Math.toRadians((double)this.theBalrog.field_70177_z)) * knock), (double)knockY, (double)(MathHelper.func_76134_b((float)Math.toRadians((double)this.theBalrog.field_70177_z)) * knock));
                  hitEntity.func_70015_d(6);
                  hitAnyEntities = true;
                  if (hitEntity == this.attackTarget) {
                     this.hitChargeTarget = true;
                  }
               }
            }
         }
      }

      if (hitAnyEntities) {
      }

   }
}
