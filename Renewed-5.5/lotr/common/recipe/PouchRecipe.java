package lotr.common.recipe;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import lotr.common.fac.Faction;
import lotr.common.inv.FactionCraftingInventory;
import lotr.common.inv.PouchInventory;
import lotr.common.item.PouchItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class PouchRecipe extends SpecialRecipe {
   private final IRecipeType recipeType;

   public PouchRecipe(ResourceLocation id, IRecipeType recipeType) {
      super(id);
      this.recipeType = recipeType;
   }

   public IRecipeType func_222127_g() {
      return this.recipeType;
   }

   public boolean matches(CraftingInventory inv, World world) {
      return !this.getCraftingResult(inv).func_190926_b();
   }

   public ItemStack getCraftingResult(CraftingInventory inv) {
      Optional tableColoringFaction = inv instanceof FactionCraftingInventory ? ((FactionCraftingInventory)inv).getPouchColoringFaction() : Optional.empty();
      List pouches = new ArrayList();
      int[] rgb = new int[3];
      int brightestIngredientColor = 0;
      int coloredItems = 0;
      boolean anyDye = false;
      boolean shouldApplyColorToResult = false;

      int b;
      float brightestIngredientColorPerItem;
      float brightestAvgIngredientRgb;
      int color;
      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack ingredient = inv.func_70301_a(i);
         if (!ingredient.func_190926_b()) {
            Item ingredientItem = ingredient.func_77973_b();
            if (ingredientItem instanceof PouchItem) {
               pouches.add(ingredient);
               b = PouchItem.getPouchColor(ingredient);
               brightestIngredientColorPerItem = (float)(b >> 16 & 255) / 255.0F;
               brightestAvgIngredientRgb = (float)(b >> 8 & 255) / 255.0F;
               float b = (float)(b & 255) / 255.0F;
               brightestIngredientColor = (int)((float)brightestIngredientColor + Math.max(brightestIngredientColorPerItem, Math.max(brightestAvgIngredientRgb, b)) * 255.0F);
               rgb[0] = (int)((float)rgb[0] + brightestIngredientColorPerItem * 255.0F);
               rgb[1] = (int)((float)rgb[1] + brightestAvgIngredientRgb * 255.0F);
               rgb[2] = (int)((float)rgb[2] + b * 255.0F);
               ++coloredItems;
               if (PouchItem.isPouchDyed(ingredient)) {
                  shouldApplyColorToResult = true;
               }
            } else {
               if (!(ingredientItem instanceof DyeItem)) {
                  return ItemStack.field_190927_a;
               }

               float[] dyeColors = ((DyeItem)ingredientItem).func_195962_g().func_193349_f();
               int r = (int)(dyeColors[0] * 255.0F);
               int g = (int)(dyeColors[1] * 255.0F);
               color = (int)(dyeColors[2] * 255.0F);
               brightestIngredientColor += Math.max(r, Math.max(g, color));
               rgb[0] += r;
               rgb[1] += g;
               rgb[2] += color;
               ++coloredItems;
               shouldApplyColorToResult = true;
               anyDye = true;
            }
         }
      }

      if (pouches.isEmpty()) {
         return ItemStack.field_190927_a;
      } else {
         ItemStack pouch;
         if (pouches.size() == 1) {
            if (!anyDye && !tableColoringFaction.isPresent()) {
               return ItemStack.field_190927_a;
            }

            pouch = ((ItemStack)pouches.get(0)).func_77946_l();
         } else {
            Item combinedPouchItem = this.getCombinedPouchItem(pouches);
            if (combinedPouchItem == null) {
               return ItemStack.field_190927_a;
            }

            pouch = new ItemStack(combinedPouchItem, 1);
            List combinedContents = new ArrayList();
            Iterator var23 = pouches.iterator();

            while(var23.hasNext()) {
               ItemStack craftingPouch = (ItemStack)var23.next();
               PouchInventory craftingPouchInv = PouchInventory.temporaryReadOnly(craftingPouch);

               for(color = 0; color < craftingPouchInv.func_70302_i_(); ++color) {
                  ItemStack slotItem = craftingPouchInv.func_70301_a(color);
                  if (!slotItem.func_190926_b()) {
                     combinedContents.add(slotItem.func_77946_l());
                  }
               }
            }

            PouchInventory combinedPouchInv = PouchInventory.temporaryWritable(pouch);
            combinedPouchInv.fillPouchFromList(combinedContents);
            boolean pickedUpNewItems = pouches.stream().anyMatch(PouchItem::getPickedUpNewItems);
            PouchItem.setPickedUpNewItems(pouch, pickedUpNewItems);
         }

         if (tableColoringFaction.isPresent() && !anyDye) {
            PouchItem.setPouchDyedByFaction(pouch, (Faction)tableColoringFaction.get());
         } else if (shouldApplyColorToResult && coloredItems > 0) {
            int r = rgb[0] / coloredItems;
            int g = rgb[1] / coloredItems;
            b = rgb[2] / coloredItems;
            brightestIngredientColorPerItem = (float)brightestIngredientColor / (float)coloredItems;
            brightestAvgIngredientRgb = (float)Math.max(r, Math.max(g, b));
            r = (int)((float)r * brightestIngredientColorPerItem / brightestAvgIngredientRgb);
            g = (int)((float)g * brightestIngredientColorPerItem / brightestAvgIngredientRgb);
            b = (int)((float)b * brightestIngredientColorPerItem / brightestAvgIngredientRgb);
            color = (r << 16) + (g << 8) + b;
            PouchItem.setPouchDyedByColor(pouch, color);
         }

         return pouch;
      }
   }

   private PouchItem getCombinedPouchItem(List pouches) {
      int totalCapacity = 0;

      Item item;
      for(Iterator var3 = pouches.iterator(); var3.hasNext(); totalCapacity += ((PouchItem)item).getCapacity()) {
         ItemStack pouch = (ItemStack)var3.next();
         item = pouch.func_77973_b();
         if (!(item instanceof PouchItem)) {
            return null;
         }
      }

      return (PouchItem)PouchItem.POUCHES_BY_CAPACITY.getOrDefault(totalCapacity, (Object)null);
   }

   public boolean func_194133_a(int width, int height) {
      return width * height >= 2;
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.CRAFTING_SPECIAL_POUCH.get();
   }

   public static class Serializer extends ForgeRegistryEntry implements IRecipeSerializer {
      public PouchRecipe read(ResourceLocation recipeId, JsonObject json) {
         String recipeTypeName = JSONUtils.func_151219_a(json, "table_type", "");
         IRecipeType recipeType = LOTRRecipes.findRecipeTypeByNameOrThrow(recipeTypeName, IRecipeType.class);
         return new PouchRecipe(recipeId, recipeType);
      }

      public PouchRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
         String recipeTypeName = buffer.func_150789_c(32767);
         IRecipeType recipeType = LOTRRecipes.findRecipeTypeByNameOrThrow(recipeTypeName, IRecipeType.class);
         return new PouchRecipe(recipeId, recipeType);
      }

      public void write(PacketBuffer buffer, PouchRecipe recipe) {
         String recipeTypeName = LOTRRecipes.findRecipeTypeName(recipe.recipeType);
         buffer.func_211400_a(recipeTypeName, 32767);
      }
   }
}
