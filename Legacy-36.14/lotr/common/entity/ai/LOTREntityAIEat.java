package lotr.common.entity.ai;

import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class LOTREntityAIEat extends LOTREntityAIConsumeBase {
   public LOTREntityAIEat(LOTREntityNPC entity, LOTRFoods foods, int chance) {
      super(entity, foods, chance);
   }

   protected ItemStack createConsumable() {
      return this.foodPool.getRandomFood(this.rand);
   }

   protected void updateConsumeTick(int tick) {
      if (tick % 4 == 0) {
         this.theEntity.spawnFoodParticles();
         this.theEntity.func_85030_a("random.eat", 0.5F + 0.5F * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      }

   }

   protected void consume() {
      ItemStack itemstack = this.theEntity.func_70694_bm();
      Item item = itemstack.func_77973_b();
      if (item instanceof ItemFood) {
         ItemFood food = (ItemFood)item;
         this.theEntity.func_70691_i((float)food.func_150905_g(itemstack));
      }

   }
}
