package lotr.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class FactionShapedRecipe extends ShapedRecipe {
   private final FactionBasedRecipeType tableType;

   public FactionShapedRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList recipeItemsIn, ItemStack recipeOutputIn, FactionBasedRecipeType tableType) {
      super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
      this.tableType = tableType;
   }

   public IRecipeType func_222127_g() {
      return this.tableType;
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.FACTION_SHAPED.get();
   }

   public ItemStack func_222128_h() {
      return this.tableType.getFactionTableIcon();
   }

   public static class Serializer extends ForgeRegistryEntry implements IRecipeSerializer {
      private final net.minecraft.item.crafting.ShapedRecipe.Serializer internalSerializer = new net.minecraft.item.crafting.ShapedRecipe.Serializer();

      public FactionShapedRecipe read(ResourceLocation recipeId, JsonObject json) {
         ShapedRecipe recipe = this.internalSerializer.func_199425_a_(recipeId, json);
         String tableTypeName = JSONUtils.func_151219_a(json, "table", "");
         FactionBasedRecipeType tableType = (FactionBasedRecipeType)LOTRRecipes.findRecipeTypeByNameOrThrow(tableTypeName, FactionBasedRecipeType.class);
         return new FactionShapedRecipe(recipe.func_199560_c(), recipe.func_193358_e(), recipe.func_192403_f(), recipe.func_192404_g(), recipe.func_192400_c(), recipe.func_77571_b(), tableType);
      }

      public FactionShapedRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
         ShapedRecipe recipe = this.internalSerializer.func_199426_a_(recipeId, buffer);
         String tableTypeName = buffer.func_150789_c(32767);
         FactionBasedRecipeType tableType = (FactionBasedRecipeType)LOTRRecipes.findRecipeTypeByNameOrThrow(tableTypeName, FactionBasedRecipeType.class);
         return new FactionShapedRecipe(recipe.func_199560_c(), recipe.func_193358_e(), recipe.func_192403_f(), recipe.func_192404_g(), recipe.func_192400_c(), recipe.func_77571_b(), tableType);
      }

      public void write(PacketBuffer buffer, FactionShapedRecipe recipe) {
         this.internalSerializer.func_199427_a_(buffer, recipe);
         String tableTypeName = LOTRRecipes.findRecipeTypeName(recipe.tableType);
         buffer.func_211400_a(tableTypeName, 32767);
      }
   }
}
