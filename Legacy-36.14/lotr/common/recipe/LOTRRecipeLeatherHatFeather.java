package lotr.common.recipe;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemFeatherDyed;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipeLeatherHatFeather implements IRecipe {
   public boolean func_77569_a(InventoryCrafting inv, World world) {
      ItemStack hat = null;
      ItemStack feather = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == LOTRMod.leatherHat && !LOTRItemLeatherHat.hasFeather(itemstack)) {
               if (hat != null) {
                  return false;
               }

               hat = itemstack;
            } else {
               if (!LOTRMod.isOreNameEqual(itemstack, "feather") && itemstack.func_77973_b() != LOTRMod.featherDyed) {
                  return false;
               }

               if (feather != null) {
                  return false;
               }

               feather = itemstack;
            }
         }
      }

      return hat != null && feather != null;
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack hat = null;
      ItemStack feather = null;

      int featherColor;
      for(featherColor = 0; featherColor < inv.func_70302_i_(); ++featherColor) {
         ItemStack itemstack = inv.func_70301_a(featherColor);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == LOTRMod.leatherHat && !LOTRItemLeatherHat.hasFeather(itemstack)) {
               if (hat != null) {
                  return null;
               }

               hat = itemstack.func_77946_l();
            } else {
               if (!LOTRMod.isOreNameEqual(itemstack, "feather") && itemstack.func_77973_b() != LOTRMod.featherDyed) {
                  return null;
               }

               if (feather != null) {
                  return null;
               }

               feather = itemstack.func_77946_l();
            }
         }
      }

      if (hat != null && feather != null) {
         featherColor = 16777215;
         if (feather.func_77973_b() == LOTRMod.featherDyed) {
            featherColor = LOTRItemFeatherDyed.getFeatherColor(feather);
         }

         LOTRItemLeatherHat.setFeatherColor(hat, featherColor);
         return hat;
      } else {
         return null;
      }
   }

   public int func_77570_a() {
      return 2;
   }

   public ItemStack func_77571_b() {
      return null;
   }
}
