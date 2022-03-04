package lotr.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class LOTREntityAIAvoidWithChance extends EntityAIAvoidEntity {
   private EntityCreature theEntity;
   private float chance;
   private String soundEffect;

   public LOTREntityAIAvoidWithChance(EntityCreature entity, Class avoidClass, float f, double d, double d1, float c) {
      this(entity, avoidClass, f, d, d1, c, (String)null);
   }

   public LOTREntityAIAvoidWithChance(EntityCreature entity, Class avoidClass, float f, double d, double d1, float c, String s) {
      super(entity, avoidClass, f, d, d1);
      this.theEntity = entity;
      this.chance = c;
      this.soundEffect = s;
   }

   public boolean func_75250_a() {
      return this.theEntity.func_70681_au().nextFloat() < this.chance && super.func_75250_a();
   }

   public void func_75249_e() {
      super.func_75249_e();
      if (this.soundEffect != null) {
         this.theEntity.func_85030_a(this.soundEffect, 0.5F, (this.theEntity.func_70681_au().nextFloat() - this.theEntity.func_70681_au().nextFloat()) * 0.2F + 1.0F);
      }

   }
}
