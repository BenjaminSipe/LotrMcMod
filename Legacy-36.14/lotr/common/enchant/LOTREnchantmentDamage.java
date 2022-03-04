package lotr.common.enchant;

import lotr.common.item.LOTRItemThrowingAxe;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentDamage extends LOTREnchantment {
   private final float baseDamageBoost;

   public LOTREnchantmentDamage(String s, float boost) {
      super(s, new LOTREnchantmentType[]{LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE});
      this.baseDamageBoost = boost;
      if (this.baseDamageBoost >= 0.0F) {
         this.setValueModifier((7.0F + this.baseDamageBoost * 5.0F) / 7.0F);
      } else {
         this.setValueModifier((7.0F + this.baseDamageBoost) / 7.0F);
      }

   }

   public float getBaseDamageBoost() {
      return this.baseDamageBoost;
   }

   public float getEntitySpecificDamage(EntityLivingBase entity) {
      return 0.0F;
   }

   public String getDescription(ItemStack itemstack) {
      return itemstack != null && itemstack.func_77973_b() instanceof LOTRItemThrowingAxe ? StatCollector.func_74837_a("lotr.enchant.damage.desc.throw", new Object[]{this.formatAdditive(this.baseDamageBoost)}) : StatCollector.func_74837_a("lotr.enchant.damage.desc", new Object[]{this.formatAdditive(this.baseDamageBoost)});
   }

   public boolean isBeneficial() {
      return this.baseDamageBoost >= 0.0F;
   }

   public boolean canApply(ItemStack itemstack, boolean considering) {
      if (super.canApply(itemstack, considering)) {
         float dmg = LOTRWeaponStats.getMeleeDamageBonus(itemstack);
         dmg += this.baseDamageBoost;
         return dmg > 0.0F;
      } else {
         return false;
      }
   }
}
