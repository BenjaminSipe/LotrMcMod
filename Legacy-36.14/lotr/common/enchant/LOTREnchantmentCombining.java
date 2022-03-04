package lotr.common.enchant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemModifierTemplate;
import net.minecraft.item.ItemStack;

public class LOTREnchantmentCombining {
   private static List allCombineRecipes = new ArrayList();

   private static void combine(LOTREnchantment in, LOTREnchantment out, int cost) {
      if (in.hasTemplateItem() && out.hasTemplateItem()) {
         if (cost < 0) {
            throw new IllegalArgumentException("Cost must not be negative!");
         } else {
            allCombineRecipes.add(new LOTREnchantmentCombining.CombineRecipe(in, out, cost));
         }
      } else {
         throw new IllegalArgumentException("Cannot create a modifier combining recipe for modifiers which lack scroll items!");
      }
   }

   public static void createRecipes() {
      combine(LOTREnchantment.strong1, LOTREnchantment.strong2, 200);
      combine(LOTREnchantment.strong2, LOTREnchantment.strong3, 800);
      combine(LOTREnchantment.strong3, LOTREnchantment.strong4, 1600);
      combine(LOTREnchantment.durable1, LOTREnchantment.durable2, 300);
      combine(LOTREnchantment.durable2, LOTREnchantment.durable3, 1500);
      combine(LOTREnchantment.knockback1, LOTREnchantment.knockback2, 2500);
      combine(LOTREnchantment.toolSpeed1, LOTREnchantment.toolSpeed2, 200);
      combine(LOTREnchantment.toolSpeed2, LOTREnchantment.toolSpeed3, 800);
      combine(LOTREnchantment.toolSpeed3, LOTREnchantment.toolSpeed4, 1500);
      combine(LOTREnchantment.looting1, LOTREnchantment.looting2, 400);
      combine(LOTREnchantment.looting2, LOTREnchantment.looting3, 1500);
      combine(LOTREnchantment.protect1, LOTREnchantment.protect2, 2000);
      combine(LOTREnchantment.protectFire1, LOTREnchantment.protectFire2, 400);
      combine(LOTREnchantment.protectFire2, LOTREnchantment.protectFire3, 1500);
      combine(LOTREnchantment.protectFall1, LOTREnchantment.protectFall2, 400);
      combine(LOTREnchantment.protectFall2, LOTREnchantment.protectFall3, 1500);
      combine(LOTREnchantment.protectRanged1, LOTREnchantment.protectRanged2, 400);
      combine(LOTREnchantment.protectRanged2, LOTREnchantment.protectRanged3, 1500);
      combine(LOTREnchantment.rangedStrong1, LOTREnchantment.rangedStrong2, 400);
      combine(LOTREnchantment.rangedStrong2, LOTREnchantment.rangedStrong3, 1500);
      combine(LOTREnchantment.rangedKnockback1, LOTREnchantment.rangedKnockback2, 2500);
   }

   public static LOTREnchantmentCombining.CombineRecipe getCombinationResult(ItemStack item1, ItemStack item2) {
      if (item1 != null && item2 != null && item1.func_77973_b() instanceof LOTRItemModifierTemplate && item2.func_77973_b() instanceof LOTRItemModifierTemplate) {
         LOTREnchantment mod1 = LOTRItemModifierTemplate.getModifier(item1);
         LOTREnchantment mod2 = LOTRItemModifierTemplate.getModifier(item2);
         if (mod1 == mod2) {
            Iterator var4 = allCombineRecipes.iterator();

            while(var4.hasNext()) {
               LOTREnchantmentCombining.CombineRecipe recipe = (LOTREnchantmentCombining.CombineRecipe)var4.next();
               if (recipe.inputMod == mod1) {
                  return recipe;
               }
            }
         }
      }

      return null;
   }

   public static class CombineRecipe {
      public final LOTREnchantment inputMod;
      public final LOTREnchantment outputMod;
      public final int cost;

      public CombineRecipe(LOTREnchantment in, LOTREnchantment out, int c) {
         this.inputMod = in;
         this.outputMod = out;
         this.cost = c;
      }

      public ItemStack createOutputItem() {
         ItemStack item = new ItemStack(LOTRMod.modTemplate);
         LOTRItemModifierTemplate.setModifier(item, this.outputMod);
         return item;
      }
   }
}
