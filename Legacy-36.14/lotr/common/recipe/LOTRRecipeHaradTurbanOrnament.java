package lotr.common.recipe;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradTurban;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipeHaradTurbanOrnament implements IRecipe {
   public boolean func_77569_a(InventoryCrafting inv, World world) {
      ItemStack turban = null;
      ItemStack gold = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == LOTRMod.helmetHaradRobes && !LOTRItemHaradTurban.hasOrnament(itemstack)) {
               if (turban != null) {
                  return false;
               }

               turban = itemstack;
            } else {
               if (!LOTRMod.isOreNameEqual(itemstack, "nuggetGold")) {
                  return false;
               }

               if (gold != null) {
                  return false;
               }

               gold = itemstack;
            }
         }
      }

      return turban != null && gold != null;
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack turban = null;
      ItemStack gold = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == LOTRMod.helmetHaradRobes && !LOTRItemHaradTurban.hasOrnament(itemstack)) {
               if (turban != null) {
                  return null;
               }

               turban = itemstack.func_77946_l();
            } else {
               if (!LOTRMod.isOreNameEqual(itemstack, "nuggetGold")) {
                  return null;
               }

               if (gold != null) {
                  return null;
               }

               gold = itemstack.func_77946_l();
            }
         }
      }

      if (turban != null && gold != null) {
         LOTRItemHaradTurban.setHasOrnament(turban, true);
         return turban;
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
