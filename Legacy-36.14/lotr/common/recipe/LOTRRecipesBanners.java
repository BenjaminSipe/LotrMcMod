package lotr.common.recipe;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTRRecipesBanners implements IRecipe {
   public boolean func_77569_a(InventoryCrafting inv, World world) {
      return this.func_77572_b(inv) != null;
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack baseBanner = null;
      int emptyBanners = 0;

      for(int pass = 0; pass < 2; ++pass) {
         for(int i = 0; i < inv.func_70302_i_(); ++i) {
            ItemStack itemstack = inv.func_70301_a(i);
            if (itemstack != null) {
               if (!(itemstack.func_77973_b() instanceof LOTRItemBanner)) {
                  return null;
               }

               NBTTagCompound data = LOTRItemBanner.getProtectionData(itemstack);
               if (pass == 0 && data != null) {
                  if (baseBanner != null) {
                     return null;
                  }

                  baseBanner = itemstack;
               }

               if (pass == 1) {
                  if (baseBanner == null || itemstack.func_77960_j() != baseBanner.func_77960_j()) {
                     return null;
                  }

                  if (data == null) {
                     ++emptyBanners;
                  }
               }
            }
         }
      }

      if (baseBanner == null) {
         return null;
      } else {
         ItemStack result;
         if (emptyBanners > 0) {
            result = baseBanner.func_77946_l();
            result.field_77994_a = emptyBanners + 1;
            return result;
         } else {
            result = baseBanner.func_77946_l();
            result.field_77994_a = 1;
            result.func_77982_d((NBTTagCompound)null);
            return result;
         }
      }
   }

   public int func_77570_a() {
      return 1;
   }

   public ItemStack func_77571_b() {
      return null;
   }
}
