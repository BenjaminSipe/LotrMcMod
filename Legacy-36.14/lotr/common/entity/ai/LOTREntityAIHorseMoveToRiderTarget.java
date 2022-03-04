package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.Vec3;

public class LOTREntityAIHorseMoveToRiderTarget extends EntityAIBase {
   private LOTRNPCMount theHorse;
   private EntityCreature livingHorse;
   private double speed;
   private PathEntity entityPathEntity;
   private int pathCheckTimer;

   public LOTREntityAIHorseMoveToRiderTarget(LOTRNPCMount horse) {
      this.theHorse = horse;
      this.livingHorse = (EntityCreature)this.theHorse;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (!this.theHorse.getBelongsToNPC()) {
         return false;
      } else {
         Entity rider = this.livingHorse.field_70153_n;
         if (rider != null && rider.func_70089_S() && rider instanceof LOTREntityNPC) {
            EntityLivingBase riderTarget = ((LOTREntityNPC)rider).func_70638_az();
            if (riderTarget != null && riderTarget.func_70089_S()) {
               this.entityPathEntity = this.livingHorse.func_70661_as().func_75494_a(riderTarget);
               return this.entityPathEntity != null;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public boolean func_75253_b() {
      if (this.livingHorse.field_70153_n != null && this.livingHorse.field_70153_n.func_70089_S() && this.livingHorse.field_70153_n instanceof LOTREntityNPC) {
         LOTREntityNPC rider = (LOTREntityNPC)this.livingHorse.field_70153_n;
         EntityLivingBase riderTarget = rider.func_70638_az();
         return riderTarget != null && riderTarget.func_70089_S() && !this.livingHorse.func_70661_as().func_75500_f();
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.speed = ((LOTREntityNPC)this.livingHorse.field_70153_n).func_110148_a(LOTREntityNPC.horseAttackSpeed).func_111126_e();
      this.livingHorse.func_70661_as().func_75484_a(this.entityPathEntity, this.speed);
      this.pathCheckTimer = 0;
   }

   public void func_75251_c() {
      this.livingHorse.func_70661_as().func_75499_g();
   }

   public void func_75246_d() {
      LOTREntityNPC rider = (LOTREntityNPC)this.livingHorse.field_70153_n;
      EntityLivingBase riderTarget = rider.func_70638_az();
      boolean aimingBow = rider.isAimingRanged() && this.livingHorse.func_70635_at().func_75522_a(riderTarget);
      if (!aimingBow) {
         this.livingHorse.func_70671_ap().func_75651_a(riderTarget, 30.0F, 30.0F);
         rider.field_70177_z = this.livingHorse.field_70177_z;
         rider.field_70759_as = this.livingHorse.field_70759_as;
      }

      if (--this.pathCheckTimer <= 0) {
         this.pathCheckTimer = 4 + this.livingHorse.func_70681_au().nextInt(7);
         this.livingHorse.func_70661_as().func_75497_a(riderTarget, this.speed);
      }

      if (aimingBow) {
         if (rider.func_70068_e(riderTarget) < 25.0D) {
            Vec3 vec = LOTREntityAIRangedAttack.findPositionAwayFrom(rider, riderTarget, 8, 16);
            if (vec != null) {
               this.livingHorse.func_70661_as().func_75492_a(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c, this.speed);
            }
         } else {
            this.livingHorse.func_70661_as().func_75499_g();
         }
      }

   }
}
