package lotr.common.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRValuableItems {
   private static List toolMaterials = new ArrayList();
   private static boolean initTools = false;

   private static void registerToolMaterials() {
      if (!initTools) {
         toolMaterials.clear();
         ToolMaterial[] allMaterials = ToolMaterial.values();
         ToolMaterial[] var1 = allMaterials;
         int var2 = allMaterials.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            ToolMaterial material = var1[var3];
            if (material.func_77996_d() >= 2) {
               ItemStack repair = material.getRepairItemStack();
               if (repair != null && repair.func_77973_b() != null) {
                  toolMaterials.add(repair.func_77946_l());
               }
            }
         }

         initTools = true;
      }

   }

   public static List getToolMaterials() {
      registerToolMaterials();
      return toolMaterials;
   }

   public static boolean canMagpieSteal(ItemStack itemstack) {
      registerToolMaterials();
      Item item = itemstack.func_77973_b();
      if (!(item instanceof LOTRItemCoin) && !(item instanceof LOTRItemRing) && !(item instanceof LOTRItemGem)) {
         Iterator var2 = toolMaterials.iterator();

         ItemStack listItem;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            listItem = (ItemStack)var2.next();
         } while(!LOTRRecipes.checkItemEquals(listItem, itemstack));

         return true;
      } else {
         return true;
      }
   }
}
