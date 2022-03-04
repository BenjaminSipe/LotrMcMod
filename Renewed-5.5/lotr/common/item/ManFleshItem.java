package lotr.common.item;

import java.util.Random;
import lotr.common.fac.FoodAlignmentHelper;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ManFleshItem extends Item {
   public ManFleshItem(Properties properties) {
      super(properties);
   }

   public ItemStack func_77654_b(ItemStack stack, World world, LivingEntity entity) {
      float safety = FoodAlignmentHelper.getHighestAlignmentProportion(entity, 250.0F, FoodAlignmentHelper.EVIL_CREATURE_FACTION_TYPES);
      Random rand = entity.func_70681_au();
      if (rand.nextFloat() < safety) {
         return super.func_77654_b(stack, world, entity);
      } else {
         stack = FoodAlignmentHelper.onFoodEatenWithoutRestore(stack, world, entity);
         if (!world.field_72995_K) {
            entity.func_195064_c(new EffectInstance(Effects.field_76438_s, LOTRUtil.secondsToTicks(30)));
            entity.func_195064_c(new EffectInstance(Effects.field_76436_u, LOTRUtil.secondsToTicks(5)));
         }

         return stack;
      }
   }

   public static boolean isManFleshAligned(LivingEntity entity) {
      return FoodAlignmentHelper.hasAnyPositiveAlignment(entity, FoodAlignmentHelper.EVIL_CREATURE_FACTION_TYPES);
   }
}
