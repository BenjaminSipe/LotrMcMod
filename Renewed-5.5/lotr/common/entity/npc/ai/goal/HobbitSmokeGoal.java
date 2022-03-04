package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.LOTRItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class HobbitSmokeGoal extends NPCConsumeGoal {
   public HobbitSmokeGoal(NPCEntity entity, int chance) {
      super(entity, chance);
   }

   protected ItemStack createConsumable() {
      return new ItemStack((IItemProvider)LOTRItems.SMOKING_PIPE.get());
   }

   protected void updateConsumeTick(int tick) {
   }

   protected void consume() {
      ItemStack itemstack = this.getHeldConsumingItem();
      itemstack.func_77950_b(this.theEntity.field_70170_p, this.theEntity);
      this.theEntity.func_70691_i(2.0F);
   }
}
