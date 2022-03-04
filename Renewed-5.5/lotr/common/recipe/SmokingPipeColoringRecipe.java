package lotr.common.recipe;

import lotr.common.init.LOTRTags;
import lotr.common.item.SmokingPipeItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SmokingPipeColoringRecipe extends SpecialRecipe {
   public SmokingPipeColoringRecipe(ResourceLocation id) {
      super(id);
   }

   public boolean matches(CraftingInventory inv, World world) {
      return !this.getCraftingResult(inv).func_190926_b();
   }

   public ItemStack getCraftingResult(CraftingInventory inv) {
      ItemStack pipe = ItemStack.field_190927_a;
      DyeColor pipeColor = null;
      boolean pipeMagic = false;
      DyeColor newColor = null;
      boolean newMagic = false;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack ingredient = inv.func_70301_a(i);
         if (!ingredient.func_190926_b()) {
            Item ingredientItem = ingredient.func_77973_b();
            if (ingredientItem instanceof SmokingPipeItem && pipe.func_190926_b()) {
               pipe = ingredient;
               pipeColor = SmokingPipeItem.getSmokeColor(ingredient);
               pipeMagic = SmokingPipeItem.isMagicSmoke(ingredient);
            } else if (ingredientItem instanceof DyeItem && newColor == null) {
               newColor = ((DyeItem)ingredientItem).func_195962_g();
            } else {
               if (!ingredientItem.func_206844_a(LOTRTags.Items.PIPE_MAGIC_SMOKE_INGREDIENTS) || newMagic) {
                  return ItemStack.field_190927_a;
               }

               newMagic = true;
            }
         }
      }

      if (pipe.func_190926_b() || (newColor == null || newColor == pipeColor) && (!newMagic || pipeMagic)) {
         return ItemStack.field_190927_a;
      } else {
         ItemStack result = pipe.func_77946_l();
         if (newColor != null) {
            SmokingPipeItem.setSmokeColor(result, newColor);
         }

         if (newMagic) {
            SmokingPipeItem.setMagicSmoke(result, newMagic);
         }

         return result;
      }
   }

   public boolean func_194133_a(int width, int height) {
      return width * height >= 2;
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.CRAFTING_SPECIAL_SMOKING_PIPE_COLORING.get();
   }
}
