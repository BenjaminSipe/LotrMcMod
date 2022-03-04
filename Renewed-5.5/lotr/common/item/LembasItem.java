package lotr.common.item;

import lotr.common.fac.FoodAlignmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LembasItem extends Item {
   public LembasItem(Properties properties) {
      super(properties);
   }

   public static boolean canConsumeElvishFoodSafely(LivingEntity entity) {
      return !FoodAlignmentHelper.isPledgedOrEntityAlignedToAny(entity, FoodAlignmentHelper.EVIL_CREATURE_FACTION_TYPES);
   }

   public static void damageEntityHostileToElvishFood(LivingEntity entity) {
      if (!entity.field_70170_p.field_72995_K) {
         entity.func_70097_a(DamageSource.field_76376_m, 2.0F);
      }

   }

   public ItemStack func_77654_b(ItemStack stack, World world, LivingEntity entity) {
      if (!canConsumeElvishFoodSafely(entity)) {
         stack = FoodAlignmentHelper.onFoodEatenWithoutRestore(stack, world, entity);
         damageEntityHostileToElvishFood(entity);
         return stack;
      } else {
         return super.func_77654_b(stack, world, entity);
      }
   }
}
