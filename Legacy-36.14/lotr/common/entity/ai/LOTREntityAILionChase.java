package lotr.common.entity.ai;

import java.util.ArrayList;
import java.util.List;
import lotr.common.entity.animal.LOTREntityLionBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.Vec3;

public class LOTREntityAILionChase extends EntityAIBase {
   private LOTREntityLionBase theLion;
   private EntityCreature targetEntity;
   private double speed;
   private int chaseTimer;
   private int lionRePathDelay;
   private int targetRePathDelay;

   public LOTREntityAILionChase(LOTREntityLionBase lion, double d) {
      this.theLion = lion;
      this.speed = d;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (!this.theLion.func_70631_g_() && !this.theLion.func_70880_s()) {
         if (this.theLion.func_70681_au().nextInt(800) != 0) {
            return false;
         } else {
            List entities = this.theLion.field_70170_p.func_72872_a(EntityAnimal.class, this.theLion.field_70121_D.func_72314_b(12.0D, 12.0D, 12.0D));
            List validTargets = new ArrayList();

            for(int i = 0; i < entities.size(); ++i) {
               EntityAnimal entity = (EntityAnimal)entities.get(i);
               if (entity.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111264_e) == null) {
                  validTargets.add(entity);
               }
            }

            if (validTargets.isEmpty()) {
               return false;
            } else {
               this.targetEntity = (EntityAnimal)validTargets.get(this.theLion.func_70681_au().nextInt(validTargets.size()));
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.chaseTimer = 300 + this.theLion.func_70681_au().nextInt(400);
   }

   public void func_75246_d() {
      --this.chaseTimer;
      this.theLion.func_70671_ap().func_75651_a(this.targetEntity, 30.0F, 30.0F);
      --this.lionRePathDelay;
      if (this.lionRePathDelay <= 0) {
         this.lionRePathDelay = 10;
         this.theLion.func_70661_as().func_75497_a(this.targetEntity, this.speed);
      }

      if (this.targetEntity.func_70661_as().func_75500_f()) {
         Vec3 vec3 = RandomPositionGenerator.func_75461_b(this.targetEntity, 16, 7, Vec3.func_72443_a(this.theLion.field_70165_t, this.theLion.field_70163_u, this.theLion.field_70161_v));
         if (vec3 != null) {
            this.targetEntity.func_70661_as().func_75492_a(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c, 2.0D);
         }
      }

   }

   public boolean func_75253_b() {
      return this.targetEntity != null && this.targetEntity.func_70089_S() && this.chaseTimer > 0 && this.theLion.func_70068_e(this.targetEntity) < 256.0D;
   }

   public void func_75251_c() {
      this.chaseTimer = 0;
      this.lionRePathDelay = 0;
      this.targetRePathDelay = 0;
   }
}
