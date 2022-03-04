package lotr.common.enchant;

import net.minecraft.util.DamageSource;

public abstract class LOTREnchantmentProtectionSpecial extends LOTREnchantment {
   public final int protectLevel;

   public LOTREnchantmentProtectionSpecial(String s, int level) {
      this(s, LOTREnchantmentType.ARMOR, level);
   }

   public LOTREnchantmentProtectionSpecial(String s, LOTREnchantmentType type, int level) {
      super(s, type);
      this.protectLevel = level;
      this.setValueModifier((2.0F + (float)this.protectLevel) / 2.0F);
   }

   public boolean isBeneficial() {
      return true;
   }

   public boolean isCompatibleWith(LOTREnchantment other) {
      if (super.isCompatibleWith(other)) {
         if (!(other instanceof LOTREnchantmentProtectionSpecial)) {
            return true;
         } else {
            return this.isCompatibleWithOtherProtection() || ((LOTREnchantmentProtectionSpecial)other).isCompatibleWithOtherProtection();
         }
      } else {
         return false;
      }
   }

   protected boolean isCompatibleWithOtherProtection() {
      return false;
   }

   protected abstract boolean protectsAgainst(DamageSource var1);

   public final int calcSpecialProtection(DamageSource source) {
      if (source.func_76357_e()) {
         return 0;
      } else {
         return this.protectsAgainst(source) ? this.calcIntProtection() : 0;
      }
   }

   protected abstract int calcIntProtection();
}
