package lotr.common.entity.ai;

import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LOTREntityAIBreeEat extends LOTREntityAIEat {
   public LOTREntityAIBreeEat(LOTREntityNPC entity, LOTRFoods foods, int chance) {
      super(entity, foods, chance);
   }

   protected ItemStack createConsumable() {
      return this.theEntity.getNPCName().equals("Peter Jackson") ? new ItemStack(Items.field_151172_bF) : super.createConsumable();
   }
}
