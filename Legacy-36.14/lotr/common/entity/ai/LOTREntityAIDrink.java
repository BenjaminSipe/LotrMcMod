package lotr.common.entity.ai;

import java.util.List;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.item.LOTRItemMug;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class LOTREntityAIDrink extends LOTREntityAIConsumeBase {
   public LOTREntityAIDrink(LOTREntityNPC entity, LOTRFoods foods, int chance) {
      super(entity, foods, chance);
   }

   protected ItemStack createConsumable() {
      ItemStack drink = this.foodPool.getRandomFood(this.rand);
      Item item = drink.func_77973_b();
      if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
         LOTRItemMug.setStrengthMeta(drink, 1 + this.rand.nextInt(3));
      }

      return drink;
   }

   protected boolean shouldConsume() {
      return this.theEntity.isDrunkard() && this.rand.nextInt(100) == 0 ? true : super.shouldConsume();
   }

   protected int getConsumeTime() {
      int time = super.getConsumeTime();
      if (this.theEntity.isDrunkard()) {
         time *= 1 + this.rand.nextInt(4);
      }

      return time;
   }

   protected void updateConsumeTick(int tick) {
      if (tick % 4 == 0) {
         this.theEntity.func_85030_a("random.drink", 0.5F, this.rand.nextFloat() * 0.1F + 0.9F);
      }

   }

   protected void consume() {
      ItemStack itemstack = this.theEntity.func_70694_bm();
      Item item = itemstack.func_77973_b();
      if (item instanceof LOTRItemMug) {
         LOTRItemMug drink = (LOTRItemMug)item;
         drink.applyToNPC(this.theEntity, itemstack);
         if (drink.alcoholicity > 0.0F && this.theEntity.canGetDrunk() && !this.theEntity.isDrunkard() && this.rand.nextInt(3) == 0) {
            double range = 12.0D;
            IEntitySelector selectNonEnemyBartenders = new IEntitySelector() {
               public boolean func_82704_a(Entity entity) {
                  return entity.func_70089_S() && !LOTRMod.getNPCFaction(entity).isBadRelation(LOTREntityAIDrink.this.theEntity.getFaction());
               }
            };
            List nearbyBartenders = this.theEntity.field_70170_p.func_82733_a(LOTRTradeable.Bartender.class, this.theEntity.field_70121_D.func_72314_b(range, range, range), selectNonEnemyBartenders);
            if (!nearbyBartenders.isEmpty()) {
               int drunkTime = MathHelper.func_76136_a(this.rand, 30, 1500);
               drunkTime *= 20;
               this.theEntity.familyInfo.setDrunkTime(drunkTime);
            }
         }
      }

   }
}
