package lotr.common.entity.ai;

import java.util.List;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;

public class LOTREntityAINearestAttackableTargetHuorn extends LOTREntityAINearestAttackableTargetBasic {
   public LOTREntityAINearestAttackableTargetHuorn(EntityCreature entity, Class targetClass, int chance, boolean flag) {
      super(entity, targetClass, chance, flag);
   }

   public LOTREntityAINearestAttackableTargetHuorn(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
      super(entity, targetClass, chance, flag, selector);
   }

   public boolean func_75250_a() {
      int chance = 400;
      List nearbyHuorns = this.field_75299_d.field_70170_p.func_72872_a(LOTREntityHuornBase.class, this.field_75299_d.field_70121_D.func_72314_b(24.0D, 8.0D, 24.0D));

      for(int i = 0; i < nearbyHuorns.size(); ++i) {
         LOTREntityHuornBase huorn = (LOTREntityHuornBase)nearbyHuorns.get(i);
         if (huorn.func_70638_az() != null) {
            chance /= 2;
         }
      }

      if (chance < 20) {
         chance = 20;
      }

      if (this.field_75299_d.func_70681_au().nextInt(chance) != 0) {
         return false;
      } else {
         return super.func_75250_a();
      }
   }
}
