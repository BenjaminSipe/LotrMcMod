package lotr.common.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.inventory.LOTRInventoryPouch;
import lotr.common.item.LOTRItemDye;
import lotr.common.item.LOTRItemPouch;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipesPouch implements IRecipe {
   private int overrideColor;
   private boolean hasOverrideColor;

   public LOTRRecipesPouch() {
      this(-1, false);
   }

   public LOTRRecipesPouch(LOTRFaction f) {
      this(f.getFactionColor(), true);
   }

   public LOTRRecipesPouch(int color, boolean flag) {
      this.overrideColor = color;
      this.hasOverrideColor = flag;
   }

   public boolean func_77569_a(InventoryCrafting inv, World world) {
      List pouches = new ArrayList();
      List dyes = new ArrayList();

      int meta;
      for(meta = 0; meta < inv.func_70302_i_(); ++meta) {
         ItemStack itemstack = inv.func_70301_a(meta);
         if (itemstack != null) {
            if (itemstack.func_77973_b() instanceof LOTRItemPouch) {
               pouches.add(itemstack);
            } else {
               if (LOTRItemDye.isItemDye(itemstack) == -1) {
                  return false;
               }

               dyes.add(itemstack);
            }
         }
      }

      if (pouches.isEmpty()) {
         return false;
      } else if (pouches.size() != 1) {
         meta = this.getCombinedMeta(pouches);
         return LOTRItemPouch.getCapacityForMeta(meta) <= LOTRItemPouch.getMaxPouchCapacity();
      } else {
         return this.hasOverrideColor || !dyes.isEmpty();
      }
   }

   private int getCombinedMeta(List pouches) {
      int size = 0;

      ItemStack pouch;
      for(Iterator var3 = pouches.iterator(); var3.hasNext(); size += pouch.func_77960_j() + 1) {
         pouch = (ItemStack)var3.next();
      }

      return size - 1;
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      List pouches = new ArrayList();
      int[] rgb = new int[3];
      int totalColor = 0;
      int coloredItems = 0;
      boolean anyDye = false;

      int g;
      float averageColor;
      float maxColor;
      int color;
      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() instanceof LOTRItemPouch) {
               pouches.add(itemstack);
               g = LOTRItemPouch.getPouchColor(itemstack);
               float r = (float)(g >> 16 & 255) / 255.0F;
               averageColor = (float)(g >> 8 & 255) / 255.0F;
               maxColor = (float)(g & 255) / 255.0F;
               totalColor = (int)((float)totalColor + Math.max(r, Math.max(averageColor, maxColor)) * 255.0F);
               rgb[0] = (int)((float)rgb[0] + r * 255.0F);
               rgb[1] = (int)((float)rgb[1] + averageColor * 255.0F);
               rgb[2] = (int)((float)rgb[2] + maxColor * 255.0F);
               ++coloredItems;
               if (LOTRItemPouch.isPouchDyed(itemstack)) {
                  anyDye = true;
               }
            } else {
               g = LOTRItemDye.isItemDye(itemstack);
               if (g == -1) {
                  return null;
               }

               float[] dyeColors = EntitySheep.field_70898_d[BlockColored.func_150031_c(g)];
               int r = (int)(dyeColors[0] * 255.0F);
               int g = (int)(dyeColors[1] * 255.0F);
               color = (int)(dyeColors[2] * 255.0F);
               totalColor += Math.max(r, Math.max(g, color));
               rgb[0] += r;
               rgb[1] += g;
               rgb[2] += color;
               ++coloredItems;
               anyDye = true;
            }
         }
      }

      if (pouches.isEmpty()) {
         return null;
      } else {
         ItemStack pouch;
         int r;
         int slot;
         if (pouches.size() == 1) {
            pouch = ((ItemStack)pouches.get(0)).func_77946_l();
         } else {
            r = this.getCombinedMeta(pouches);
            pouch = new ItemStack(LOTRMod.pouch);
            pouch.field_77994_a = 1;
            pouch.func_77964_b(r);
            LOTRInventoryPouch pouchInv = new LOTRInventoryPouch(pouch);
            slot = 0;
            Iterator var22 = pouches.iterator();

            while(var22.hasNext()) {
               ItemStack craftingPouch = (ItemStack)var22.next();
               LOTRInventoryPouch craftingPouchInv = new LOTRInventoryPouch(craftingPouch);

               for(int i = 0; i < craftingPouchInv.func_70302_i_(); ++i) {
                  ItemStack slotItem = craftingPouchInv.func_70301_a(i);
                  if (slotItem != null) {
                     pouchInv.func_70299_a(slot, slotItem.func_77946_l());
                     ++slot;
                  }
               }
            }
         }

         if (this.hasOverrideColor) {
            LOTRItemPouch.setPouchColor(pouch, this.overrideColor);
         } else if (anyDye && coloredItems > 0) {
            r = rgb[0] / coloredItems;
            g = rgb[1] / coloredItems;
            slot = rgb[2] / coloredItems;
            averageColor = (float)totalColor / (float)coloredItems;
            maxColor = (float)Math.max(r, Math.max(g, slot));
            r = (int)((float)r * averageColor / maxColor);
            g = (int)((float)g * averageColor / maxColor);
            slot = (int)((float)slot * averageColor / maxColor);
            color = (r << 16) + (g << 8) + slot;
            LOTRItemPouch.setPouchColor(pouch, color);
         }

         return pouch;
      }
   }

   public int func_77570_a() {
      return 10;
   }

   public ItemStack func_77571_b() {
      return null;
   }
}
