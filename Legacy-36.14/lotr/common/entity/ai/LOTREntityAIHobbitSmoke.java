package lotr.common.entity.ai;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import lotr.common.item.LOTRItemHobbitPipe;
import net.minecraft.item.ItemStack;

public class LOTREntityAIHobbitSmoke extends LOTREntityAIConsumeBase {
   public LOTREntityAIHobbitSmoke(LOTREntityNPC entity, int chance) {
      super(entity, (LOTRFoods)null, chance);
   }

   protected ItemStack createConsumable() {
      return new ItemStack(LOTRMod.hobbitPipe);
   }

   protected void updateConsumeTick(int tick) {
   }

   protected void consume() {
      LOTREntitySmokeRing smoke = new LOTREntitySmokeRing(this.theEntity.field_70170_p, this.theEntity);
      int color = 0;
      ItemStack itemstack = this.theEntity.func_70694_bm();
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemHobbitPipe) {
         color = LOTRItemHobbitPipe.getSmokeColor(itemstack);
      }

      smoke.setSmokeColour(color);
      this.theEntity.field_70170_p.func_72838_d(smoke);
      this.theEntity.func_85030_a("lotr:item.puff", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      this.theEntity.func_70691_i(2.0F);
   }
}
