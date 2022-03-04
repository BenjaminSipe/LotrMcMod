package lotr.common.recipe;

import lotr.common.init.LOTRBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class DwarvenForgeRecipe extends AbstractCookingRecipe {
   public DwarvenForgeRecipe(ResourceLocation rl, String grp, Ingredient ingr, ItemStack res, float xp, int time) {
      super(LOTRRecipes.DWARVEN_FORGE, rl, grp, ingr, res, xp, time);
   }

   public ItemStack func_222128_h() {
      return new ItemStack((IItemProvider)LOTRBlocks.DWARVEN_FORGE.get());
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.DWARVEN_FORGE_SERIALIZER.get();
   }
}
