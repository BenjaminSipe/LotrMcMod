package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBarrel;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.item.ItemStack;

public class LOTRTradeEntryBarrel extends LOTRTradeEntry {
   public LOTRTradeEntryBarrel(ItemStack itemstack, int cost) {
      super(itemstack, cost);
   }

   public ItemStack createTradeItem() {
      ItemStack drinkItem = super.createTradeItem();
      ItemStack barrelItem = new ItemStack(LOTRMod.barrel);
      LOTRTileEntityBarrel barrel = new LOTRTileEntityBarrel();
      barrel.func_70299_a(9, new ItemStack(drinkItem.func_77973_b(), LOTRBrewingRecipes.BARREL_CAPACITY, drinkItem.func_77960_j()));
      barrel.barrelMode = 2;
      LOTRItemBarrel.setBarrelDataFromTE(barrelItem, barrel);
      return barrelItem;
   }
}
