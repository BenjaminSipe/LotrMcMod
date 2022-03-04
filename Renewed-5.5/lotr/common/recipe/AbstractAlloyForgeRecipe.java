package lotr.common.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AbstractAlloyForgeRecipe implements IRecipe {
   protected final IRecipeType recipeType;
   protected final ResourceLocation recipeId;
   protected final String group;
   protected final Ingredient ingredient;
   protected final Ingredient alloyIngredient;
   protected final boolean swappable;
   protected final ItemStack result;
   protected final float experience;
   protected final int cookTime;

   public AbstractAlloyForgeRecipe(IRecipeType ty, ResourceLocation i, String grp, Ingredient ingr, Ingredient alloy, boolean swap, ItemStack res, float xp, int time) {
      this.recipeType = ty;
      this.recipeId = i;
      this.group = grp;
      this.ingredient = ingr;
      this.alloyIngredient = alloy;
      this.swappable = swap;
      this.result = res;
      this.experience = xp;
      this.cookTime = time;
   }

   public boolean func_77569_a(IInventory inv, World worldIn) {
      ItemStack invIngredient = inv.func_70301_a(0);
      ItemStack invAlloy = inv.func_70301_a(1);
      if (this.ingredient.test(invIngredient) && this.alloyIngredient.test(invAlloy)) {
         return true;
      } else {
         return this.swappable && this.ingredient.test(invAlloy) && this.alloyIngredient.test(invIngredient);
      }
   }

   public ItemStack func_77572_b(IInventory inv) {
      return this.result.func_77946_l();
   }

   public boolean func_194133_a(int width, int height) {
      return true;
   }

   public NonNullList func_192400_c() {
      NonNullList list = NonNullList.func_191196_a();
      list.add(this.ingredient);
      list.add(this.alloyIngredient);
      return list;
   }

   public float getExperience() {
      return this.experience;
   }

   public ItemStack func_77571_b() {
      return this.result;
   }

   public String func_193358_e() {
      return this.group;
   }

   public int getCookTime() {
      return this.cookTime;
   }

   public ResourceLocation func_199560_c() {
      return this.recipeId;
   }

   public IRecipeType func_222127_g() {
      return this.recipeType;
   }
}
