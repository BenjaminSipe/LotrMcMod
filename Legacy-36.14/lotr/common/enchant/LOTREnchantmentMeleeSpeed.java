package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentMeleeSpeed extends LOTREnchantment {
   public final float speedFactor;

   public LOTREnchantmentMeleeSpeed(String s, float speed) {
      super(s, LOTREnchantmentType.MELEE);
      this.speedFactor = speed;
      this.setValueModifier(this.speedFactor);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.meleeSpeed.desc", new Object[]{this.formatMultiplicative(this.speedFactor)});
   }

   public boolean isBeneficial() {
      return this.speedFactor >= 1.0F;
   }

   public boolean canApply(ItemStack itemstack, boolean considering) {
      if (super.canApply(itemstack, considering)) {
         float speed = LOTRWeaponStats.getMeleeSpeed(itemstack);
         speed *= this.speedFactor;
         return speed <= LOTRWeaponStats.MAX_MODIFIABLE_SPEED;
      } else {
         return false;
      }
   }
}
