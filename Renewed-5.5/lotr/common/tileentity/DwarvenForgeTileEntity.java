package lotr.common.tileentity;

import lotr.common.init.LOTRTileEntities;
import lotr.common.inv.AlloyForgeContainer;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DwarvenForgeTileEntity extends AbstractAlloyForgeTileEntity {
   public DwarvenForgeTileEntity() {
      super((TileEntityType)LOTRTileEntities.DWARVEN_FORGE.get(), new IRecipeType[]{LOTRRecipes.DWARVEN_FORGE, IRecipeType.field_222150_b}, new IRecipeType[]{LOTRRecipes.DWARVEN_FORGE_ALLOY, LOTRRecipes.ALLOY_FORGE});
   }

   protected ITextComponent func_213907_g() {
      return new TranslationTextComponent("container.lotr.dwarven_forge");
   }

   protected Container func_213906_a(int id, PlayerInventory player) {
      return new AlloyForgeContainer(id, player, this, this.forgeData);
   }
}
