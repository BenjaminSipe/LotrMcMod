package lotr.common.recipe;

import java.util.ArrayList;
import lotr.common.item.LOTRItemDye;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.item.LOTRMaterial;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipeHaradRobesDye implements IRecipe {
   private ArmorMaterial robeMaterial;

   public LOTRRecipeHaradRobesDye() {
      this(LOTRMaterial.HARAD_ROBES);
   }

   public LOTRRecipeHaradRobesDye(LOTRMaterial material) {
      this.robeMaterial = material.toArmorMaterial();
   }

   public boolean func_77569_a(InventoryCrafting inv, World world) {
      ItemStack robes = null;
      ArrayList dyes = new ArrayList();

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() instanceof LOTRItemHaradRobes && ((LOTRItemHaradRobes)itemstack.func_77973_b()).func_82812_d() == this.robeMaterial) {
               if (robes != null) {
                  return false;
               }

               robes = itemstack;
            } else {
               if (LOTRItemDye.isItemDye(itemstack) == -1) {
                  return false;
               }

               dyes.add(itemstack);
            }
         }
      }

      return robes != null && !dyes.isEmpty();
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      ItemStack robes = null;
      int[] rgb = new int[3];
      int totalColor = 0;
      int coloredItems = 0;

      int r;
      int robeColor;
      int g;
      float r;
      float g;
      for(r = 0; r < inv.func_70302_i_(); ++r) {
         ItemStack itemstack = inv.func_70301_a(r);
         if (itemstack != null) {
            if (itemstack.func_77973_b() instanceof LOTRItemHaradRobes && ((LOTRItemHaradRobes)itemstack.func_77973_b()).func_82812_d() == this.robeMaterial) {
               if (robes != null) {
                  return null;
               }

               robes = itemstack.func_77946_l();
               robes.field_77994_a = 1;
               if (LOTRItemHaradRobes.areRobesDyed(robes)) {
                  robeColor = LOTRItemHaradRobes.getRobesColor(robes);
                  r = (float)(robeColor >> 16 & 255) / 255.0F;
                  g = (float)(robeColor >> 8 & 255) / 255.0F;
                  float b = (float)(robeColor & 255) / 255.0F;
                  totalColor = (int)((float)totalColor + Math.max(r, Math.max(g, b)) * 255.0F);
                  rgb[0] = (int)((float)rgb[0] + r * 255.0F);
                  rgb[1] = (int)((float)rgb[1] + g * 255.0F);
                  rgb[2] = (int)((float)rgb[2] + b * 255.0F);
                  ++coloredItems;
               }
            } else {
               robeColor = LOTRItemDye.isItemDye(itemstack);
               if (robeColor == -1) {
                  return null;
               }

               float[] dyeColors = EntitySheep.field_70898_d[BlockColored.func_150031_c(robeColor)];
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

      if (robes == null) {
         return null;
      } else {
         r = rgb[0] / coloredItems;
         int g = rgb[1] / coloredItems;
         robeColor = rgb[2] / coloredItems;
         r = (float)totalColor / (float)coloredItems;
         g = (float)Math.max(r, Math.max(g, robeColor));
         r = (int)((float)r * r / g);
         g = (int)((float)g * r / g);
         robeColor = (int)((float)robeColor * r / g);
         g = (r << 16) + (g << 8) + robeColor;
         LOTRItemHaradRobes.setRobesColor(robes, g);
         return robes;
      }
   }

   public int func_77570_a() {
      return 10;
   }

   public ItemStack func_77571_b() {
      return null;
   }
}
