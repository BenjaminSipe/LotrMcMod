package lotr.common.recipe;

import java.util.Iterator;
import lotr.common.init.LOTRTags;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DrinkBrewingRecipe implements IRecipe {
   protected final ResourceLocation recipeId;
   protected final String group;
   protected final NonNullList ingredients;
   protected final ItemStack result;
   protected final float experience;
   protected final int brewTime;
   private final int waters = 3;

   public DrinkBrewingRecipe(ResourceLocation i, String grp, NonNullList ingr, ItemStack res, float xp, int time) {
      this.recipeId = i;
      this.group = grp;
      this.ingredients = ingr;
      this.result = res;
      this.experience = xp;
      this.brewTime = time;
   }

   public boolean func_77569_a(IInventory inv, World worldIn) {
      int invSize = inv.func_70302_i_();
      int invSizeToCheck = invSize - 3;
      if (invSizeToCheck > this.ingredients.size()) {
         return false;
      } else {
         boolean[] matchedSlots = new boolean[invSizeToCheck];
         Iterator var6 = this.ingredients.iterator();

         boolean matchedIngredient;
         do {
            if (!var6.hasNext()) {
               for(int i = 0; i < 3; ++i) {
                  int waterSlot = invSizeToCheck + i;
                  if (!isWaterSource(inv.func_70301_a(waterSlot))) {
                     return false;
                  }
               }

               return true;
            }

            Ingredient ing = (Ingredient)var6.next();
            matchedIngredient = false;

            for(int i = 0; i < invSizeToCheck; ++i) {
               if (!matchedSlots[i]) {
                  ItemStack stackInSlot = inv.func_70301_a(i);
                  if (ing.test(stackInSlot)) {
                     matchedIngredient = true;
                     matchedSlots[i] = true;
                     break;
                  }
               }
            }
         } while(matchedIngredient);

         return false;
      }
   }

   public static boolean isWaterSource(ItemStack stack) {
      return stack.func_77973_b().func_206844_a(LOTRTags.Items.KEG_BREWING_WATER_SOURCES);
   }

   public ItemStack func_77572_b(IInventory inv) {
      return this.result.func_77946_l();
   }

   public boolean func_194133_a(int width, int height) {
      return true;
   }

   public NonNullList func_192400_c() {
      return this.ingredients;
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

   public int getBrewTime() {
      return this.brewTime;
   }

   public ResourceLocation func_199560_c() {
      return this.recipeId;
   }

   public IRecipeType func_222127_g() {
      return LOTRRecipes.DRINK_BREWING;
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.DRINK_BREWING_SERIALIZER.get();
   }
}
