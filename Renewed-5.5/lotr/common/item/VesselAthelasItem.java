package lotr.common.item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VesselAthelasItem extends VesselDrinkItem {
   public VesselAthelasItem(int food, float sat, EffectInstance... effs) {
      super(0.0F, food, sat, true, 0.0F, Arrays.asList(effs));
   }

   public ItemStack func_77654_b(ItemStack stack, World world, LivingEntity entity) {
      ItemStack result = super.func_77654_b(stack, world, entity);
      if (!world.field_72995_K) {
         Set toRemove = new HashSet();
         Iterator var6 = entity.func_70651_bq().iterator();

         while(var6.hasNext()) {
            EffectInstance ei = (EffectInstance)var6.next();
            Effect effect = ei.func_188419_a();
            if (effect.func_220303_e() == EffectType.HARMFUL) {
               toRemove.add(effect);
            }
         }

         var6 = toRemove.iterator();

         while(var6.hasNext()) {
            Effect effect = (Effect)var6.next();
            entity.func_195063_d(effect);
         }
      }

      return result;
   }

   @OnlyIn(Dist.CLIENT)
   protected void addPreEffectsTooltip(ItemStack stack, World world, List tooltip, ITooltipFlag flag) {
      super.addPreEffectsTooltip(stack, world, tooltip, flag);
      ITextComponent displayCure = (new TranslationTextComponent("item.lotr.drink.cures_harmful")).func_240699_a_(TextFormatting.YELLOW);
      tooltip.add(displayCure);
   }
}
