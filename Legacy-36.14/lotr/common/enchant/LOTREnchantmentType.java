package lotr.common.enchant;

import lotr.common.item.LOTRItemBlowgun;
import lotr.common.item.LOTRItemCommandSword;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemThrowingAxe;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;

public enum LOTREnchantmentType {
   BREAKABLE,
   ARMOR,
   ARMOR_FEET,
   ARMOR_LEGS,
   ARMOR_BODY,
   ARMOR_HEAD,
   MELEE,
   TOOL,
   SHEARS,
   RANGED,
   RANGED_LAUNCHER,
   THROWING_AXE,
   FISHING;

   public boolean canApply(ItemStack itemstack, boolean considering) {
      Item item = itemstack.func_77973_b();
      if (this == BREAKABLE && item.func_77645_m()) {
         return true;
      } else {
         if (item instanceof ItemArmor && ((ItemArmor)item).field_77879_b > 0) {
            if (this == ARMOR) {
               return true;
            }

            ItemArmor itemarmor = (ItemArmor)item;
            int armorType = itemarmor.field_77881_a;
            if (armorType == 0) {
               return this == ARMOR_HEAD;
            }

            if (armorType == 1) {
               return this == ARMOR_BODY;
            }

            if (armorType == 1) {
               return this == ARMOR_BODY;
            }

            if (armorType == 2) {
               return this == ARMOR_LEGS;
            }

            if (armorType == 3) {
               return this == ARMOR_FEET;
            }
         }

         if (this == MELEE && LOTRWeaponStats.isMeleeWeapon(itemstack) && !(item instanceof LOTRItemCommandSword)) {
            return true;
         } else if (this == TOOL && !item.getToolClasses(itemstack).isEmpty()) {
            return true;
         } else if (this == SHEARS && item instanceof ItemShears) {
            return true;
         } else if (this == RANGED && LOTRWeaponStats.isRangedWeapon(itemstack)) {
            return true;
         } else if (this != RANGED_LAUNCHER || !(item instanceof ItemBow) && !(item instanceof LOTRItemCrossbow) && !(item instanceof LOTRItemBlowgun)) {
            if (this == THROWING_AXE && item instanceof LOTRItemThrowingAxe) {
               return true;
            } else {
               return this == FISHING && item instanceof ItemFishingRod;
            }
         } else {
            return true;
         }
      }
   }
}
