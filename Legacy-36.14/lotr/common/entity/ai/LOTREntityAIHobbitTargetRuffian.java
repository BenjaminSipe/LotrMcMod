package lotr.common.entity.ai;

import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityAIHobbitTargetRuffian extends LOTREntityAINearestAttackableTargetBasic {
   public LOTREntityAIHobbitTargetRuffian(EntityCreature entity, Class targetClass, int chance, boolean flag) {
      super(entity, targetClass, chance, flag);
   }

   public LOTREntityAIHobbitTargetRuffian(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
      super(entity, targetClass, chance, flag, selector);
   }

   protected boolean func_75296_a(EntityLivingBase entity, boolean flag) {
      if (super.func_75296_a(entity, flag)) {
         BiomeGenBase biome = this.field_75299_d.field_70170_p.func_72807_a(MathHelper.func_76128_c(this.field_75299_d.field_70165_t), MathHelper.func_76128_c(this.field_75299_d.field_70161_v));
         if (biome instanceof LOTRBiomeGenShire) {
            return true;
         }
      }

      return false;
   }
}
