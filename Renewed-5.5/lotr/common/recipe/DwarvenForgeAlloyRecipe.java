package lotr.common.recipe;

import lotr.common.init.LOTRBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class DwarvenForgeAlloyRecipe extends AbstractAlloyForgeRecipe {
   public DwarvenForgeAlloyRecipe(ResourceLocation i, String grp, Ingredient ingr, Ingredient alloy, boolean swap, ItemStack res, float xp, int time) {
      super(LOTRRecipes.DWARVEN_FORGE_ALLOY, i, grp, ingr, alloy, swap, res, xp, time);
   }

   public ItemStack func_222128_h() {
      return new ItemStack((IItemProvider)LOTRBlocks.DWARVEN_FORGE.get());
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.DWARVEN_FORGE_ALLOY_SERIALIZER.get();
   }
}
