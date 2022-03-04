package lotr.common.recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;

public class LOTREntJarRecipes {
   private static Map recipes = new HashMap();

   public static void createDraughtRecipes() {
      addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 0), new ItemStack(LOTRMod.entDraught, 1, 0));
      addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 1), new ItemStack(LOTRMod.entDraught, 1, 1));
      addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 2), new ItemStack(LOTRMod.entDraught, 1, 2));
      addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 3), new ItemStack(LOTRMod.entDraught, 1, 3));
      addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 4), new ItemStack(LOTRMod.entDraught, 1, 4));
      addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 5), new ItemStack(LOTRMod.entDraught, 1, 5));
      addDraughtRecipe(new ItemStack(LOTRMod.fangornRiverweed), new ItemStack(LOTRMod.entDraught, 1, 6));
   }

   private static void addDraughtRecipe(ItemStack ingredient, ItemStack result) {
      recipes.put(ingredient, result);
   }

   public static ItemStack findMatchingRecipe(ItemStack input) {
      if (input == null) {
         return null;
      } else {
         Iterator it = recipes.keySet().iterator();

         ItemStack recipeInput;
         do {
            if (!it.hasNext()) {
               return null;
            }

            recipeInput = (ItemStack)it.next();
         } while(!LOTRRecipes.checkItemEquals(recipeInput, input));

         return ((ItemStack)recipes.get(recipeInput)).func_77946_l();
      }
   }
}
