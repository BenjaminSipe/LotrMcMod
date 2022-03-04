package lotr.common.tileentity;

import lotr.common.init.LOTRTags;
import lotr.common.init.LOTRTileEntities;
import lotr.common.inv.AlloyForgeContainer;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class HobbitOvenTileEntity extends AbstractAlloyForgeTileEntity {
   public HobbitOvenTileEntity() {
      super((TileEntityType)LOTRTileEntities.HOBBIT_OVEN.get(), new IRecipeType[]{LOTRRecipes.HOBBIT_OVEN, IRecipeType.field_222150_b}, new IRecipeType[]{LOTRRecipes.HOBBIT_OVEN_ALLOY});
   }

   protected ITextComponent func_213907_g() {
      return new TranslationTextComponent("container.lotr.hobbit_oven");
   }

   protected Container func_213906_a(int id, PlayerInventory player) {
      return new AlloyForgeContainer(id, player, this, this.forgeData);
   }

   protected boolean isDefaultFurnaceRecipeAcceptable(ItemStack ingredientStack, ItemStack resultStack) {
      Item resultItem = resultStack.func_77973_b();
      if (resultItem.func_219971_r()) {
         return true;
      } else {
         Item ingredientItem = ingredientStack.func_77973_b();
         return ingredientItem.func_206844_a(LOTRTags.Items.HOBBIT_OVEN_EXTRA_COOKABLES);
      }
   }
}
