package lotr.common.enchant;

import lotr.common.item.LOTRMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtection extends LOTREnchantment {
   public final int protectLevel;

   public LOTREnchantmentProtection(String s, int level) {
      this(s, LOTREnchantmentType.ARMOR, level);
   }

   public LOTREnchantmentProtection(String s, LOTREnchantmentType type, int level) {
      super(s, type);
      this.protectLevel = level;
      if (this.protectLevel >= 0) {
         this.setValueModifier((2.0F + (float)this.protectLevel) / 2.0F);
      } else {
         this.setValueModifier((4.0F + (float)this.protectLevel) / 4.0F);
      }

   }

   public String getDescription(ItemStack itemstack) {
      return StatCollector.func_74837_a("lotr.enchant.protect.desc", new Object[]{this.formatAdditiveInt(this.protectLevel)});
   }

   public boolean isBeneficial() {
      return this.protectLevel >= 0;
   }

   public boolean canApply(ItemStack itemstack, boolean considering) {
      if (super.canApply(itemstack, considering)) {
         Item item = itemstack.func_77973_b();
         if (item instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)item;
            if (armor.func_82812_d() == LOTRMaterial.GALVORN.toArmorMaterial()) {
               return false;
            } else {
               int prot = armor.field_77879_b;
               int total = prot + this.protectLevel;
               if (total > 0) {
                  if (considering) {
                     return true;
                  } else {
                     return total <= LOTRMaterial.MITHRIL.toArmorMaterial().func_78044_b(armor.field_77881_a);
                  }
               } else {
                  return false;
               }
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }
}
