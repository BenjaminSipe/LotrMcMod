package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentToolSpeed extends LOTREnchantment {
   public final float speedFactor;

   public LOTREnchantmentToolSpeed(String s, float speed) {
      super(s, new LOTREnchantmentType[]{LOTREnchantmentType.TOOL, LOTREnchantmentType.SHEARS});
      this.speedFactor = speed;
      this.setValueModifier(this.speedFactor);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.toolSpeed.desc", new Object[]{this.formatMultiplicative(this.speedFactor)});
   }

   public boolean isBeneficial() {
      return this.speedFactor >= 1.0F;
   }
}
