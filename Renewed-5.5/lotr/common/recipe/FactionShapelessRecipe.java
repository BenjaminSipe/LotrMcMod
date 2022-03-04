package lotr.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class FactionShapelessRecipe extends ShapelessRecipe {
   private final FactionBasedRecipeType tableType;

   public FactionShapelessRecipe(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList recipeItemsIn, FactionBasedRecipeType tableType) {
      super(idIn, groupIn, recipeOutputIn, recipeItemsIn);
      this.tableType = tableType;
   }

   public IRecipeType func_222127_g() {
      return this.tableType;
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.FACTION_SHAPELESS.get();
   }

   public ItemStack func_222128_h() {
      return this.tableType.getFactionTableIcon();
   }

   public static class Serializer extends ForgeRegistryEntry implements IRecipeSerializer {
      private final net.minecraft.item.crafting.ShapelessRecipe.Serializer internalSerializer = new net.minecraft.item.crafting.ShapelessRecipe.Serializer();

      public FactionShapelessRecipe read(ResourceLocation recipeId, JsonObject json) {
         ShapelessRecipe recipe = this.internalSerializer.func_199425_a_(recipeId, json);
         String tableTypeName = JSONUtils.func_151219_a(json, "table", "");
         FactionBasedRecipeType tableType = (FactionBasedRecipeType)LOTRRecipes.findRecipeTypeByNameOrThrow(tableTypeName, FactionBasedRecipeType.class);
         return new FactionShapelessRecipe(recipe.func_199560_c(), recipe.func_193358_e(), recipe.func_77571_b(), recipe.func_192400_c(), tableType);
      }

      public FactionShapelessRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
         ShapelessRecipe recipe = this.internalSerializer.func_199426_a_(recipeId, buffer);
         String tableTypeName = buffer.func_150789_c(32767);
         FactionBasedRecipeType tableType = (FactionBasedRecipeType)LOTRRecipes.findRecipeTypeByNameOrThrow(tableTypeName, FactionBasedRecipeType.class);
         return new FactionShapelessRecipe(recipe.func_199560_c(), recipe.func_193358_e(), recipe.func_77571_b(), recipe.func_192400_c(), tableType);
      }

      public void write(PacketBuffer buffer, FactionShapelessRecipe recipe) {
         this.internalSerializer.func_199427_a_(buffer, recipe);
         String tableTypeName = LOTRRecipes.findRecipeTypeName(recipe.tableType);
         buffer.func_211400_a(tableTypeName, 32767);
      }
   }
}
