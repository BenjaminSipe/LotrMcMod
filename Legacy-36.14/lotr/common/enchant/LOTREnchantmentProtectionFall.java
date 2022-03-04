package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtectionFall extends LOTREnchantmentProtectionSpecial {
   public LOTREnchantmentProtectionFall(String s, int level) {
      super(s, LOTREnchantmentType.ARMOR_FEET, level);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.protectFall.desc", new Object[]{this.formatAdditiveInt(this.calcIntProtection())});
   }

   protected boolean isCompatibleWithOtherProtection() {
      return true;
   }

   protected boolean protectsAgainst(DamageSource source) {
      return source == DamageSource.field_76379_h;
   }

   protected int calcIntProtection() {
      float f = (float)this.protectLevel * (float)(this.protectLevel + 1) / 2.0F;
      return 3 + MathHelper.func_76141_d(f);
   }
}
