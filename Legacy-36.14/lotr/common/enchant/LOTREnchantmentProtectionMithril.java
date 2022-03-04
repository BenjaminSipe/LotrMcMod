package lotr.common.enchant;

import lotr.common.item.LOTRMaterial;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtectionMithril extends LOTREnchantmentProtectionSpecial {
   public LOTREnchantmentProtectionMithril(String s) {
      super(s, 1);
      this.setValueModifier(2.0F);
   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.protectMithril.desc", new Object[]{this.formatAdditiveInt(this.calcIntProtection())});
   }

   public boolean canApply(ItemStack itemstack, boolean considering) {
      if (super.canApply(itemstack, considering)) {
         Item item = itemstack.func_77973_b();
         return item instanceof ItemArmor && ((ItemArmor)item).func_82812_d() == LOTRMaterial.MITHRIL.toArmorMaterial();
      } else {
         return false;
      }
   }

   protected boolean protectsAgainst(DamageSource source) {
      Entity attacker = source.func_76346_g();
      Entity entity = source.func_76364_f();
      if (attacker instanceof EntityLivingBase && attacker == entity) {
         ItemStack weapon = ((EntityLivingBase)attacker).func_70694_bm();
         if (weapon != null) {
            ItemStack weaponBase = weapon.func_77946_l();
            LOTREnchantmentHelper.clearEnchants(weaponBase);
            float range = LOTRWeaponStats.getMeleeReachFactor(weaponBase);
            if (range >= 1.3F) {
               return true;
            }
         }
      }

      return false;
   }

   protected int calcIntProtection() {
      return 4;
   }
}
