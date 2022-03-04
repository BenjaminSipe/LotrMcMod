package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentSilkTouch extends LOTREnchantment {
   public LOTREnchantmentSilkTouch(String s) {
      super(s, LOTREnchantmentType.TOOL);
      this.setValueModifier(3.0F);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant." + this.enchantName + ".desc", new Object[0]);
   }

   public boolean isBeneficial() {
      return true;
   }

   public boolean isCompatibleWith(LOTREnchantment other) {
      return super.isCompatibleWith(other) && !(other instanceof LOTREnchantmentLooting);
   }
}
