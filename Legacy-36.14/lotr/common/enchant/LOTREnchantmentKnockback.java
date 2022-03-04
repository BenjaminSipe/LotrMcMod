package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentKnockback extends LOTREnchantment {
   public final int knockback;

   public LOTREnchantmentKnockback(String s, int i) {
      super(s, new LOTREnchantmentType[]{LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE});
      this.knockback = i;
      this.setValueModifier((float)(this.knockback + 2) / 2.0F);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.knockback.desc", new Object[]{this.formatAdditiveInt(this.knockback)});
   }

   public boolean isBeneficial() {
      return this.knockback >= 0;
   }

   public boolean canApply(ItemStack itemstack, boolean considering) {
      return super.canApply(itemstack, considering) && LOTRWeaponStats.getBaseExtraKnockback(itemstack) + this.knockback <= LOTRWeaponStats.MAX_MODIFIABLE_KNOCKBACK;
   }
}
