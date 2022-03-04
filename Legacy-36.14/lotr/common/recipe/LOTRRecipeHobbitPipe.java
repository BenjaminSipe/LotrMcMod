package lotr.common.recipe;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemDye;
import lotr.common.item.LOTRItemHobbitPipe;
import net.minecraft.block.BlockColored;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipeHobbitPipe implements IRecipe {
   public boolean func_77569_a(InventoryCrafting inv, World world) {
      ItemStack hobbitPipe = null;
      ItemStack dye = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == LOTRMod.hobbitPipe) {
               if (hobbitPipe != null) {
                  return false;
               }

               hobbitPipe = itemstack;
            } else if (itemstack.func_77973_b() == LOTRMod.mithrilNugget) {
               dye = itemstack;
            } else {
               if (LOTRItemDye.isItemDye(itemstack) == -1) {
                  return false;
               }

               dye = itemstack;
            }
         }
      }

      return hobbitPipe != null && dye != null;
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack hobbitPipe = null;
      ItemStack dye = null;

      int itemDamage;
      for(itemDamage = 0; itemDamage < inv.func_70302_i_(); ++itemDamage) {
         ItemStack itemstack = inv.func_70301_a(itemDamage);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == LOTRMod.hobbitPipe) {
               if (hobbitPipe != null) {
                  return null;
               }

               hobbitPipe = itemstack;
            } else if (itemstack.func_77973_b() == LOTRMod.mithrilNugget) {
               dye = itemstack;
            } else {
               if (LOTRItemDye.isItemDye(itemstack) == -1) {
                  return null;
               }

               dye = itemstack;
            }
         }
      }

      if (hobbitPipe != null && dye != null) {
         itemDamage = hobbitPipe.func_77960_j();
         int smokeType = dye.func_77973_b() == LOTRMod.mithrilNugget ? 16 : BlockColored.func_150031_c(LOTRItemDye.isItemDye(dye));
         ItemStack result = new ItemStack(LOTRMod.hobbitPipe);
         result.func_77964_b(itemDamage);
         LOTRItemHobbitPipe.setSmokeColor(result, smokeType);
         return result;
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
