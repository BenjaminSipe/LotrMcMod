package lotr.common.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LOTRVesselRecipes {
   public static void addRecipes(ItemStack result, Object[] ingredients) {
      addRecipes(result, (Item)null, ingredients);
   }

   public static void addRecipes(ItemStack result, Item drinkBase, Object[] ingredients) {
      List recipes = generateRecipes(result, drinkBase, ingredients);
      Iterator var4 = recipes.iterator();

      while(var4.hasNext()) {
         IRecipe r = (IRecipe)var4.next();
         GameRegistry.addRecipe(r);
      }

   }

   public static List generateRecipes(ItemStack result, Object[] ingredients) {
      return generateRecipes(result, (Item)null, ingredients);
   }

   public static List generateRecipes(ItemStack result, Item drinkBase, Object[] ingredients) {
      List recipes = new ArrayList();
      LOTRItemMug.Vessel[] var4 = LOTRItemMug.Vessel.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         LOTRItemMug.Vessel v = var4[var6];
         List vIngredients = new ArrayList();
         ItemStack vBase = v.getEmptyVessel();
         if (drinkBase != null) {
            vBase = new ItemStack(drinkBase);
            LOTRItemMug.setVessel(vBase, v, true);
         }

         vIngredients.add(vBase);
         vIngredients.addAll(Arrays.asList(ingredients));
         ItemStack vResult = result.func_77946_l();
         LOTRItemMug.setVessel(vResult, v, true);
         IRecipe recipe = new ShapelessOreRecipe(vResult, vIngredients.toArray());
         recipes.add(recipe);
      }

      return recipes;
   }
}
