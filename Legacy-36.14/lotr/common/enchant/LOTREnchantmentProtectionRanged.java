package lotr.common.enchant;

import lotr.common.item.LOTRMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtectionRanged extends LOTREnchantmentProtectionSpecial {
   public LOTREnchantmentProtectionRanged(String s, int level) {
      super(s, level);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.protectRanged.desc", new Object[]{this.formatAdditiveInt(this.calcIntProtection())});
   }

   public boolean canApply(ItemStack itemstack, boolean considering) {
      if (super.canApply(itemstack, considering)) {
         Item item = itemstack.func_77973_b();
         return !(item instanceof ItemArmor) || ((ItemArmor)item).func_82812_d() != LOTRMaterial.GALVORN.toArmorMaterial();
      } else {
         return false;
      }
   }

   protected boolean protectsAgainst(DamageSource source) {
      return source.func_76352_a();
   }

   protected int calcIntProtection() {
      return this.protectLevel;
   }
}
