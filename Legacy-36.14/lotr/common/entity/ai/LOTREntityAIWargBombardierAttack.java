package lotr.common.entity.ai;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityWargBombardier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class LOTREntityAIWargBombardierAttack extends EntityAIBase {
   private World worldObj;
   private LOTREntityWargBombardier theWarg;
   private EntityLivingBase entityTarget;
   private double moveSpeed;
   private PathEntity entityPathEntity;
   private int randomMoveTick;

   public LOTREntityAIWargBombardierAttack(LOTREntityWargBombardier entity, double speed) {
      this.theWarg = entity;
      this.worldObj = entity.field_70170_p;
      this.moveSpeed = speed;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      EntityLivingBase entity = this.theWarg.func_70638_az();
      if (entity == null) {
         return false;
      } else {
         this.entityTarget = entity;
         this.entityPathEntity = this.theWarg.func_70661_as().func_75494_a(this.entityTarget);
         return this.entityPathEntity != null;
      }
   }

   public boolean func_75253_b() {
      EntityLivingBase entity = this.theWarg.func_70638_az();
      return entity != null && this.entityTarget.func_70089_S();
   }

   public void func_75249_e() {
      this.theWarg.func_70661_as().func_75484_a(this.entityPathEntity, this.moveSpeed);
      this.randomMoveTick = 0;
   }

   public void func_75251_c() {
      this.entityTarget = null;
      this.theWarg.func_70661_as().func_75499_g();
      this.theWarg.setBombFuse(35);
   }

   public void func_75246_d() {
      this.theWarg.func_70671_ap().func_75651_a(this.entityTarget, 30.0F, 30.0F);
      if (this.theWarg.func_70635_at().func_75522_a(this.entityTarget) && --this.randomMoveTick <= 0) {
         this.randomMoveTick = 4 + this.theWarg.func_70681_au().nextInt(7);
         this.theWarg.func_70661_as().func_75497_a(this.entityTarget, this.moveSpeed);
      }

      int i;
      if (this.theWarg.func_70092_e(this.entityTarget.field_70165_t, this.entityTarget.field_70121_D.field_72338_b, this.entityTarget.field_70161_v) <= 16.0D) {
         if (this.theWarg.getBombFuse() > 20) {
            for(i = this.theWarg.getBombFuse(); i > 20; i -= 10) {
            }

            this.theWarg.setBombFuse(i);
         } else if (this.theWarg.getBombFuse() > 0) {
            this.theWarg.setBombFuse(this.theWarg.getBombFuse() - 1);
         } else {
            this.worldObj.func_72876_a(this.theWarg, this.theWarg.field_70165_t, this.theWarg.field_70163_u, this.theWarg.field_70161_v, (float)(this.theWarg.getBombStrengthLevel() + 1) * 4.0F, LOTRMod.canGrief(this.worldObj));
            this.theWarg.func_70106_y();
         }
      } else if (this.theWarg.getBombFuse() <= 20) {
         for(i = this.theWarg.getBombFuse(); i <= 20; i += 10) {
         }

         this.theWarg.setBombFuse(i);
      } else {
         this.theWarg.setBombFuse(this.theWarg.getBombFuse() - 1);
      }

   }
}
