package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.item.VesselDrinkItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NPCDrinkGoal extends NPCConsumeGoal {
   private final NPCFoodPool drinkPool;

   public NPCDrinkGoal(NPCEntity entity, NPCFoodPool foods, int chance) {
      super(entity, chance);
      this.drinkPool = foods;
   }

   protected ItemStack createConsumable() {
      ItemStack drink = this.drinkPool.getRandomFood(this.rand);
      Item item = drink.func_77973_b();
      if (item instanceof VesselDrinkItem && ((VesselDrinkItem)item).hasPotencies) {
         VesselDrinkItem.setPotency(drink, VesselDrinkItem.Potency.randomForNPC(this.rand));
      }

      return drink;
   }

   protected boolean shouldConsume() {
      return this.theEntity.isDrunk() && this.rand.nextInt(100) == 0 ? true : super.shouldConsume();
   }

   protected int getConsumeTime() {
      int time = super.getConsumeTime();
      if (this.theEntity.isDrunk()) {
         time *= 1 + this.rand.nextInt(4);
      }

      return time;
   }

   protected void updateConsumeTick(int tick) {
      if (tick % 4 == 0) {
         this.playDrinkSound(this.theEntity.func_184614_ca());
      }

   }

   protected void consume() {
      ItemStack itemstack = this.theEntity.func_184614_ca();
      this.playDrinkSound(itemstack);
      Item item = itemstack.func_77973_b();
      if (item instanceof VesselDrinkItem) {
         VesselDrinkItem drink = (VesselDrinkItem)item;
         drink.func_77654_b(itemstack, this.theEntity.field_70170_p, this.theEntity);
      }

   }

   private void playDrinkSound(ItemStack itemstack) {
      this.theEntity.func_184185_a(this.theEntity.getNPCDrinkSound(itemstack), 0.5F, this.rand.nextFloat() * 0.1F + 0.9F);
   }
}
