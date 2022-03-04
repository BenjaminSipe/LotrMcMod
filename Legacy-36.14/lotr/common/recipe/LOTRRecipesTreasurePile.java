package lotr.common.recipe;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipesTreasurePile implements IRecipe {
   private Block treasureBlock;
   private Item ingotItem;

   public LOTRRecipesTreasurePile(Block block, Item item) {
      this.treasureBlock = block;
      this.ingotItem = item;
   }

   public boolean func_77569_a(InventoryCrafting inv, World world) {
      return this.func_77572_b(inv) != null;
   }

   public ItemStack func_77572_b(InventoryCrafting inv) {
      int ingredientCount = 0;
      int ingredientTotalSize = 0;
      int resultCount = 0;
      int resultMeta = 0;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack itemstack = inv.func_70301_a(i);
         if (itemstack != null) {
            if (itemstack.func_77973_b() != Item.func_150898_a(this.treasureBlock)) {
               return null;
            }

            ++ingredientCount;
            int meta = itemstack.func_77960_j();
            ingredientTotalSize += meta + 1;
         }
      }

      if (ingredientCount > 0) {
         if (ingredientCount == 1) {
            if (ingredientTotalSize > 1) {
               resultCount = ingredientTotalSize;
               resultMeta = 0;
            }
         } else {
            resultCount = 1;
            resultMeta = ingredientTotalSize - 1;
         }
      }

      if (resultCount > 0 && resultMeta <= 7) {
         if (ingredientCount == 1 && ingredientTotalSize == 8) {
            return new ItemStack(this.ingotItem, 4);
         } else {
            return new ItemStack(this.treasureBlock, resultCount, resultMeta);
         }
      } else {
         return null;
      }
   }

   public int func_77570_a() {
      return 10;
   }

   public ItemStack func_77571_b() {
      return null;
   }
}
