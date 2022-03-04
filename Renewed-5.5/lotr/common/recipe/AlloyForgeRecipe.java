package lotr.common.recipe;

import lotr.common.init.LOTRBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class AlloyForgeRecipe extends AbstractAlloyForgeRecipe {
   public AlloyForgeRecipe(ResourceLocation i, String grp, Ingredient ingr, Ingredient alloy, boolean swap, ItemStack res, float xp, int time) {
      super(LOTRRecipes.ALLOY_FORGE, i, grp, ingr, alloy, swap, res, xp, time);
   }

   public ItemStack func_222128_h() {
      return new ItemStack((IItemProvider)LOTRBlocks.ALLOY_FORGE.get());
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.ALLOY_SERIALIZER.get();
   }
}
