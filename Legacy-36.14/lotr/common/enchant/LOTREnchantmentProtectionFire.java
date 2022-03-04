package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtectionFire extends LOTREnchantmentProtectionSpecial {
   public LOTREnchantmentProtectionFire(String s, int level) {
      super(s, level);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.protectFire.desc", new Object[]{this.formatAdditiveInt(this.calcIntProtection())});
   }

   protected boolean protectsAgainst(DamageSource source) {
      return source.func_76347_k();
   }

   protected int calcIntProtection() {
      return 1 + this.protectLevel;
   }
}
