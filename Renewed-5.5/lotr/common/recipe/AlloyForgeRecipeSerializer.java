package lotr.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AlloyForgeRecipeSerializer extends ForgeRegistryEntry implements IRecipeSerializer {
   private final AlloyForgeRecipeSerializer.IFactory recipeFactory;
   private final int defaultCookingTime;

   public AlloyForgeRecipeSerializer(AlloyForgeRecipeSerializer.IFactory factory, int time) {
      this.recipeFactory = factory;
      this.defaultCookingTime = time;
   }

   public AbstractAlloyForgeRecipe read(ResourceLocation recipeId, JsonObject json) {
      String group = JSONUtils.func_151219_a(json, "group", "");
      JsonElement jsonIngredient = JSONUtils.func_151202_d(json, "ingredient") ? JSONUtils.func_151214_t(json, "ingredient") : JSONUtils.func_152754_s(json, "ingredient");
      JsonElement jsonAlloy = JSONUtils.func_151202_d(json, "alloy") ? JSONUtils.func_151214_t(json, "alloy") : JSONUtils.func_152754_s(json, "alloy");
      Ingredient ingredient = Ingredient.func_199802_a((JsonElement)jsonIngredient);
      Ingredient alloy = Ingredient.func_199802_a((JsonElement)jsonAlloy);
      boolean swappable = json.get("swappable").getAsBoolean();
      if (!json.has("result")) {
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
         int time = JSONUtils.func_151208_a(json, "cookingtime", this.defaultCookingTime);
         return this.recipeFactory.create(recipeId, group, ingredient, alloy, swappable, result, xp, time);
      }
   }

   public AbstractAlloyForgeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
      String group = buffer.func_150789_c(32767);
      Ingredient ingredient = Ingredient.func_199566_b(buffer);
      Ingredient alloy = Ingredient.func_199566_b(buffer);
      boolean swappable = buffer.readBoolean();
      ItemStack result = buffer.func_150791_c();
      float xp = buffer.readFloat();
      int time = buffer.func_150792_a();
      return this.recipeFactory.create(recipeId, group, ingredient, alloy, swappable, result, xp, time);
   }

   public void write(PacketBuffer buffer, AbstractAlloyForgeRecipe recipe) {
      buffer.func_180714_a(recipe.group);
      recipe.ingredient.func_199564_a(buffer);
      recipe.alloyIngredient.func_199564_a(buffer);
      buffer.writeBoolean(recipe.swappable);
      buffer.func_150788_a(recipe.result);
      buffer.writeFloat(recipe.experience);
      buffer.func_150787_b(recipe.cookTime);
   }

   public interface IFactory {
      AbstractAlloyForgeRecipe create(ResourceLocation var1, String var2, Ingredient var3, Ingredient var4, boolean var5, ItemStack var6, float var7, int var8);
   }
}
