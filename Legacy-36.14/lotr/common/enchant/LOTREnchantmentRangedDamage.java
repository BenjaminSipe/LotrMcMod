package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentRangedDamage extends LOTREnchantment {
   public final float damageFactor;

   public LOTREnchantmentRangedDamage(String s, float damage) {
      super(s, LOTREnchantmentType.RANGED_LAUNCHER);
      this.damageFactor = damage;
      if (this.damageFactor > 1.0F) {
         this.setValueModifier(this.damageFactor * 2.0F);
      } else {
         this.setValueModifier(this.damageFactor);
      }

   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.rangedDamage.desc", new Object[]{this.formatMultiplicative(this.damageFactor)});
   }

   public boolean isBeneficial() {
      return this.damageFactor >= 1.0F;
   }
}
