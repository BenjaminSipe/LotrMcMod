package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentDurability extends LOTREnchantment {
   public final float durabilityFactor;

   public LOTREnchantmentDurability(String s, float f) {
      super(s, LOTREnchantmentType.BREAKABLE);
      this.durabilityFactor = f;
      this.setValueModifier(this.durabilityFactor);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.durable.desc", new Object[]{this.formatMultiplicative(this.durabilityFactor)});
   }

   public boolean isBeneficial() {
      return this.durabilityFactor >= 1.0F;
   }
}
