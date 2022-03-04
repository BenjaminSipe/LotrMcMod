package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentMeleeReach extends LOTREnchantment {
   public final float reachFactor;

   public LOTREnchantmentMeleeReach(String s, float reach) {
      super(s, LOTREnchantmentType.MELEE);
      this.reachFactor = reach;
      this.setValueModifier(this.reachFactor);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.meleeReach.desc", new Object[]{this.formatMultiplicative(this.reachFactor)});
   }

   public boolean isBeneficial() {
      return this.reachFactor >= 1.0F;
   }

   public boolean canApply(ItemStack itemstack, boolean considering) {
      if (super.canApply(itemstack, considering)) {
         float reach = LOTRWeaponStats.getMeleeReachFactor(itemstack);
         reach *= this.reachFactor;
         return reach <= LOTRWeaponStats.MAX_MODIFIABLE_REACH;
      } else {
         return false;
      }
   }
}
