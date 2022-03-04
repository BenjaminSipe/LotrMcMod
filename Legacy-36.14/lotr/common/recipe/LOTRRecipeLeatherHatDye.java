package lotr.common.recipe;

import java.util.ArrayList;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemDye;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipeLeatherHatDye implements IRecipe {
   public boolean func_77569_a(InventoryCrafting inv, World world) {
      ItemStack hat = null;
      ArrayList dyes = new ArrayList();

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == LOTRMod.leatherHat) {
               if (hat != null || LOTRItemLeatherHat.getFeatherColor(itemstack) > -1) {
                  return false;
               }

               hat = itemstack;
            } else {
               if (LOTRItemDye.isItemDye(itemstack) == -1) {
                  return false;
               }

               dyes.add(itemstack);
            }
         }
      }

      return hat != null && !dyes.isEmpty();
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack hat = null;
      int[] rgb = new int[3];
      int totalColor = 0;
      int coloredItems = 0;

      int r;
      int hatColor;
      float r;
      float g;
      int g;
      for(r = 0; r < inv.func_70302_i_(); ++r) {
         ItemStack itemstack = inv.func_70301_a(r);
         if (itemstack != null) {
            if (itemstack.func_77973_b() == LOTRMod.leatherHat) {
               if (hat != null || LOTRItemLeatherHat.getFeatherColor(itemstack) > -1) {
                  return null;
               }

               hat = itemstack.func_77946_l();
               hat.field_77994_a = 1;
               if (LOTRItemLeatherHat.isHatDyed(hat)) {
                  hatColor = LOTRItemLeatherHat.getHatColor(hat);
                  r = (float)(hatColor >> 16 & 255) / 255.0F;
                  g = (float)(hatColor >> 8 & 255) / 255.0F;
                  float b = (float)(hatColor & 255) / 255.0F;
                  totalColor = (int)((float)totalColor + Math.max(r, Math.max(g, b)) * 255.0F);
                  rgb[0] = (int)((float)rgb[0] + r * 255.0F);
                  rgb[1] = (int)((float)rgb[1] + g * 255.0F);
                  rgb[2] = (int)((float)rgb[2] + b * 255.0F);
                  ++coloredItems;
               }
            } else {
               hatColor = LOTRItemDye.isItemDye(itemstack);
               if (hatColor == -1) {
                  return null;
               }

               float[] dyeColors = EntitySheep.field_70898_d[BlockColored.func_150031_c(hatColor)];
               int r = (int)(dyeColors[0] * 255.0F);
               g = (int)(dyeColors[1] * 255.0F);
               int b = (int)(dyeColors[2] * 255.0F);
               totalColor += Math.max(r, Math.max(g, b));
               rgb[0] += r;
               rgb[1] += g;
               rgb[2] += b;
               ++coloredItems;
            }
         }
      }

      if (hat == null) {
         return null;
      } else {
         r = rgb[0] / coloredItems;
         int g = rgb[1] / coloredItems;
         hatColor = rgb[2] / coloredItems;
         r = (float)totalColor / (float)coloredItems;
         g = (float)Math.max(r, Math.max(g, hatColor));
         r = (int)((float)r * r / g);
         g = (int)((float)g * r / g);
         hatColor = (int)((float)hatColor * r / g);
         g = (r << 16) + (g << 8) + hatColor;
         LOTRItemLeatherHat.setHatColor(hat, g);
         return hat;
      }
   }

   public int func_77570_a() {
      return 10;
   }

   public ItemStack func_77571_b() {
      return null;
   }
}
