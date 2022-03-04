package lotr.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DrinkBrewingRecipeSerializer extends ForgeRegistryEntry implements IRecipeSerializer {
   private final int defaultBrewingTime;

   public DrinkBrewingRecipeSerializer(int time) {
      this.defaultBrewingTime = time;
   }

   public DrinkBrewingRecipe read(ResourceLocation recipeId, JsonObject json) {
      String group = JSONUtils.func_151219_a(json, "group", "");
      NonNullList ingList = readIngredients(JSONUtils.func_151214_t(json, "ingredients"));
      if (ingList.isEmpty()) {
         throw new JsonParseException("No ingredients for drink brewing recipe");
      } else if (!json.has("result")) {
         throw new JsonSyntaxException("Missing result, expected to find a string or object");
      } else {
         ItemStack result;
         if (json.get("result").isJsonObject()) {
            result = ShapedRecipe.func_199798_a(JSONUtils.func_152754_s(json, "result"));
         } else {
            String resultString = JSONUtils.func_151200_h(json, "result");
            ResourceLocation resultId = new ResourceLocation(resultString);
            result = new ItemStack((IItemProvider)Registry.field_212630_s.func_241873_b(resultId).orElseThrow(() -> {
               return new IllegalStateException("Item: " + resultString + " does not exist");
            }));
         }

         float xp = JSONUtils.func_151221_a(json, "experience", 0.0F);
         int time = JSONUtils.func_151208_a(json, "brewtime", this.defaultBrewingTime);
         return new DrinkBrewingRecipe(recipeId, group, ingList, result, xp, time);
      }
   }

   private static NonNullList readIngredients(JsonArray array) {
      NonNullList list = NonNullList.func_191196_a();

      for(int i = 0; i < array.size(); ++i) {
         Ingredient ing = Ingredient.func_199802_a(array.get(i));
         if (!ing.func_203189_d()) {
            list.add(ing);
         }
      }

      return list;
   }

   public DrinkBrewingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
      String group = buffer.func_150789_c(32767);
      int numIngs = buffer.func_150792_a();
      NonNullList ingList = NonNullList.func_191197_a(numIngs, Ingredient.field_193370_a);

      for(int i = 0; i < ingList.size(); ++i) {
         ingList.set(i, Ingredient.func_199566_b(buffer));
      }

      ItemStack result = buffer.func_150791_c();
      float xp = buffer.readFloat();
      int time = buffer.func_150792_a();
      return new DrinkBrewingRecipe(recipeId, group, ingList, result, xp, time);
   }

   public void write(PacketBuffer buffer, DrinkBrewingRecipe recipe) {
      buffer.func_180714_a(recipe.group);
      buffer.func_150787_b(recipe.ingredients.size());
      Iterator var3 = recipe.ingredients.iterator();

      while(var3.hasNext()) {
         Ingredient ing = (Ingredient)var3.next();
         ing.func_199564_a(buffer);
      }

      buffer.func_150788_a(recipe.result);
      buffer.writeFloat(recipe.experience);
      buffer.func_150787_b(recipe.brewTime);
   }
}
