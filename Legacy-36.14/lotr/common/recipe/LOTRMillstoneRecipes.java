package lotr.common.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LOTRMillstoneRecipes {
   private static Map recipeList = new HashMap();

   public static void createRecipes() {
      addRecipe(Blocks.field_150348_b, new ItemStack(Blocks.field_150347_e));
      addRecipe(Blocks.field_150347_e, new ItemStack(Blocks.field_150351_n), 0.75F);
      addRecipe(new ItemStack(LOTRMod.rock, 1, 0), new ItemStack(LOTRMod.mordorGravel), 0.75F);
      addRecipe(Blocks.field_150351_n, new ItemStack(Items.field_151145_ak), 0.25F);
      addRecipe(LOTRMod.mordorGravel, new ItemStack(Items.field_151145_ak), 0.25F);
      addRecipe(LOTRMod.obsidianGravel, new ItemStack(LOTRMod.obsidianShard), 1.0F);
      addRecipe(LOTRMod.oreSalt, new ItemStack(LOTRMod.salt));
      addRecipe(new ItemStack(Blocks.field_150322_A, 1, 0), new ItemStack(Blocks.field_150354_m, 2, 0));
      addRecipe(new ItemStack(LOTRMod.redSandstone, 1, 0), new ItemStack(Blocks.field_150354_m, 2, 1));
      addRecipe(new ItemStack(LOTRMod.whiteSandstone, 1, 0), new ItemStack(LOTRMod.whiteSand, 2, 0));
      addCrackedBricks(new ItemStack(Blocks.field_150336_V, 1, 0), new ItemStack(LOTRMod.redBrick, 1, 1));
      addCrackedBricks(new ItemStack(Blocks.field_150417_aV, 1, 0), new ItemStack(Blocks.field_150417_aV, 1, 2));
      addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 0), new ItemStack(LOTRMod.brick, 1, 7));
      addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 1), new ItemStack(LOTRMod.brick, 1, 3));
      addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 6), new ItemStack(LOTRMod.brick4, 1, 5));
      addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 11), new ItemStack(LOTRMod.brick, 1, 13));
      addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 15), new ItemStack(LOTRMod.brick3, 1, 11));
      addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 0), new ItemStack(LOTRMod.brick2, 1, 1));
      addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 3), new ItemStack(LOTRMod.brick2, 1, 5));
      addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 8), new ItemStack(LOTRMod.brick2, 1, 9));
      addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 2), new ItemStack(LOTRMod.brick3, 1, 4));
      addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 5), new ItemStack(LOTRMod.brick3, 1, 7));
      addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 10), new ItemStack(LOTRMod.brick6, 1, 13));
      addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 13), new ItemStack(LOTRMod.brick3, 1, 14));
      addCrackedBricks(new ItemStack(LOTRMod.brick4, 1, 0), new ItemStack(LOTRMod.brick4, 1, 2));
      addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 1), new ItemStack(LOTRMod.brick6, 1, 4));
      addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 2), new ItemStack(LOTRMod.brick5, 1, 5));
      addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 8), new ItemStack(LOTRMod.brick5, 1, 10));
      addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 11), new ItemStack(LOTRMod.brick5, 1, 14));
      addCrackedBricks(new ItemStack(LOTRMod.brick6, 1, 6), new ItemStack(LOTRMod.brick6, 1, 7));
      addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 0), new ItemStack(LOTRMod.pillar2, 1, 0));
      addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 1), new ItemStack(LOTRMod.pillar, 1, 2));
      addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 10), new ItemStack(LOTRMod.pillar, 1, 11));
      addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 12), new ItemStack(LOTRMod.pillar, 1, 13));
      addCrackedBricks(new ItemStack(LOTRMod.pillar2, 1, 13), new ItemStack(LOTRMod.pillar2, 1, 14));
   }

   public static void addRecipe(Block block, ItemStack result) {
      addRecipe(block, result, 1.0F);
   }

   public static void addRecipe(Block block, ItemStack result, float chance) {
      addRecipe(Item.func_150898_a(block), result, chance);
   }

   public static void addRecipe(Item item, ItemStack result) {
      addRecipe(new ItemStack(item, 1, 32767), result, 1.0F);
   }

   public static void addRecipe(Item item, ItemStack result, float chance) {
      addRecipe(new ItemStack(item, 1, 32767), result, chance);
   }

   public static void addRecipe(ItemStack itemstack, ItemStack result) {
      addRecipe(itemstack, result, 1.0F);
   }

   public static void addRecipe(ItemStack itemstack, ItemStack result, float chance) {
      recipeList.put(itemstack, new LOTRMillstoneRecipes.MillstoneResult(result, chance));
   }

   public static void addCrackedBricks(ItemStack itemstack, ItemStack result) {
      addRecipe(itemstack, result, 1.0F);
      GameRegistry.addSmelting(itemstack, result, 0.1F);
   }

   public static LOTRMillstoneRecipes.MillstoneResult getMillingResult(ItemStack itemstack) {
      Iterator var1 = recipeList.entrySet().iterator();

      ItemStack target;
      LOTRMillstoneRecipes.MillstoneResult result;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         Entry e = (Entry)var1.next();
         target = (ItemStack)e.getKey();
         result = (LOTRMillstoneRecipes.MillstoneResult)e.getValue();
         ItemStack resultItem = result.resultItem;
      } while(!matches(itemstack, target));

      return result;
   }

   private static boolean matches(ItemStack itemstack, ItemStack target) {
      return target.func_77973_b() == itemstack.func_77973_b() && (target.func_77960_j() == 32767 || target.func_77960_j() == itemstack.func_77960_j());
   }

   public static class MillstoneResult {
      public final ItemStack resultItem;
      public final float chance;

      public MillstoneResult(ItemStack itemstack, float f) {
         this.resultItem = itemstack;
         this.chance = f;
      }
   }
}
