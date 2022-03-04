package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentRangedKnockback extends LOTREnchantment {
   public final int knockback;

   public LOTREnchantmentRangedKnockback(String s, int i) {
      super(s, LOTREnchantmentType.RANGED_LAUNCHER);
      this.knockback = i;
      this.setValueModifier((float)(this.knockback + 2) / 2.0F);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.rangedKnockback.desc", new Object[]{this.formatAdditiveInt(this.knockback)});
   }

   public boolean isBeneficial() {
      return this.knockback >= 0;
   }
}
