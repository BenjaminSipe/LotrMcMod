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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MaggotyBreadItem extends Item {
   public MaggotyBreadItem(Properties properties) {
      super(properties);
   }

   public ItemStack func_77654_b(ItemStack stack, World world, LivingEntity entity) {
      ItemStack result = super.func_77654_b(stack, world, entity);
      if (!world.field_72995_K) {
         float alignProp = FoodAlignmentHelper.getHighestAlignmentProportion(entity, 250.0F, FoodAlignmentHelper.EVIL_CREATURE_FACTION_TYPES);
         float chance = (1.0F - alignProp) * 0.8F;
         Random rand = entity.func_70681_au();
         if (rand.nextFloat() < chance) {
            int dur = MathHelper.func_76136_a(rand, 20, 40);
            entity.func_195064_c(new EffectInstance(Effects.field_76438_s, LOTRUtil.secondsToTicks(dur)));
         }
      }

      return result;
   }
}
