package lotr.common.recipe;

import java.util.ArrayList;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemDye;
import lotr.common.item.LOTRItemFeatherDyed;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipeFeatherDye implements IRecipe {
   public boolean func_77569_a(InventoryCrafting inv, World world) {
      ItemStack feather = null;
      ArrayList dyes = new ArrayList();

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (!LOTRMod.isOreNameEqual(itemstack, "feather") && itemstack.func_77973_b() != LOTRMod.featherDyed) {
               if (LOTRItemDye.isItemDye(itemstack) == -1) {
                  return false;
               }

               dyes.add(itemstack);
            } else {
               if (feather != null) {
                  return false;
               }

               feather = itemstack;
            }
         }
      }

      return feather != null && !dyes.isEmpty();
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack feather = null;
      int[] rgb = new int[3];
      int totalColor = 0;
      int coloredItems = 0;

      int r;
      int featherColor;
      float r;
      float g;
      int g;
      for(r = 0; r < inv.func_70302_i_(); ++r) {
         ItemStack itemstack = inv.func_70301_a(r);
         if (itemstack != null) {
            if (!LOTRMod.isOreNameEqual(itemstack, "feather") && itemstack.func_77973_b() != LOTRMod.featherDyed) {
               featherColor = LOTRItemDye.isItemDye(itemstack);
               if (featherColor == -1) {
                  return null;
               }

               float[] dyeColors = EntitySheep.field_70898_d[BlockColored.func_150031_c(featherColor)];
               int r = (int)(dyeColors[0] * 255.0F);
               g = (int)(dyeColors[1] * 255.0F);
               int b = (int)(dyeColors[2] * 255.0F);
               totalColor += Math.max(r, Math.max(g, b));
               rgb[0] += r;
               rgb[1] += g;
               rgb[2] += b;
               ++coloredItems;
            } else {
               if (feather != null) {
                  return null;
               }

               feather = itemstack.func_77946_l();
               feather.field_77994_a = 1;
               if (feather.func_77973_b() == LOTRMod.featherDyed) {
                  featherColor = LOTRItemFeatherDyed.getFeatherColor(feather);
                  r = (float)(featherColor >> 16 & 255) / 255.0F;
                  g = (float)(featherColor >> 8 & 255) / 255.0F;
                  float b = (float)(featherColor & 255) / 255.0F;
                  totalColor = (int)((float)totalColor + Math.max(r, Math.max(g, b)) * 255.0F);
                  rgb[0] = (int)((float)rgb[0] + r * 255.0F);
                  rgb[1] = (int)((float)rgb[1] + g * 255.0F);
                  rgb[2] = (int)((float)rgb[2] + b * 255.0F);
                  ++coloredItems;
               }
            }
         }
      }

      if (feather == null) {
         return null;
      } else {
         r = rgb[0] / coloredItems;
         int g = rgb[1] / coloredItems;
         featherColor = rgb[2] / coloredItems;
         r = (float)totalColor / (float)coloredItems;
         g = (float)Math.max(r, Math.max(g, featherColor));
         r = (int)((float)r * r / g);
         g = (int)((float)g * r / g);
         featherColor = (int)((float)featherColor * r / g);
         g = (r << 16) + (g << 8) + featherColor;
         feather = new ItemStack(LOTRMod.featherDyed);
         LOTRItemFeatherDyed.setFeatherColor(feather, g);
         return feather;
      }
   }

   public int func_77570_a() {
      return 10;
   }

   public ItemStack func_77571_b() {
      return null;
   }
}
