package lotr.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class LOTREntityAIFlee extends EntityAIBase {
   private EntityCreature theEntity;
   private double speed;
   private double attackerX;
   private double attackerY;
   private double attackerZ;
   private int timer;
   private boolean firstPath;

   public LOTREntityAIFlee(EntityCreature entity, double d) {
      this.theEntity = entity;
      this.speed = d;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      EntityLivingBase attacker = this.theEntity.func_70643_av();
      if (attacker == null) {
         return false;
      } else {
         this.attackerX = attacker.field_70165_t;
         this.attackerY = attacker.field_70163_u;
         this.attackerZ = attacker.field_70161_v;
         return true;
      }
   }

   public void func_75249_e() {
      this.timer = 60 + this.theEntity.func_70681_au().nextInt(50);
   }

   public boolean func_75253_b() {
      return this.timer > 0;
   }

   public void func_75246_d() {
      --this.timer;
      if (!this.firstPath || this.theEntity.func_70661_as().func_75500_f()) {
         Vec3 vec3 = RandomPositionGenerator.func_75461_b(this.theEntity, 16, 7, Vec3.func_72443_a(this.attackerX, this.attackerY, this.attackerZ));
         if (vec3 != null && this.theEntity.func_70661_as().func_75492_a(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c, this.speed)) {
            this.theEntity.func_70604_c((EntityLivingBase)null);
            this.firstPath = true;
         }
      }

   }

   public void func_75251_c() {
      this.theEntity.func_70661_as().func_75499_g();
      this.timer = 0;
      this.firstPath = false;
   }
}
