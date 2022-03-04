package lotr.common.recipe;

import com.google.gson.JsonObject;
import lotr.common.item.IEmptyVesselItem;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselType;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class VesselDrinkShapelessRecipe extends ShapelessRecipe {
   private final boolean leaveDrinkIngredientContainers;

   public VesselDrinkShapelessRecipe(ResourceLocation rId, String grp, ItemStack output, NonNullList ings, boolean leaveDrinkIngredientContainers) {
      super(rId, grp, output, ings);
      this.leaveDrinkIngredientContainers = leaveDrinkIngredientContainers;
   }

   public IRecipeSerializer func_199559_b() {
      return (IRecipeSerializer)LOTRRecipes.VESSEL_DRINK_SHAPELESS.get();
   }

   public boolean func_77569_a(CraftingInventory inv, World world) {
      return super.func_77569_a(inv, world) && this.determineSingleVessel(inv) != null;
   }

   private VesselType determineSingleVessel(CraftingInventory inv) {
      VesselType singleType = null;

      for(int i = 0; i < inv.func_70302_i_(); ++i) {
         ItemStack invStack = inv.func_70301_a(i);
         if (!invStack.func_190926_b()) {
            Item invItem = invStack.func_77973_b();
            VesselType invVessel = null;
            if (invItem instanceof VesselDrinkItem) {
               invVessel = VesselDrinkItem.getVessel(invStack);
            } else if (invItem instanceof IEmptyVesselItem) {
               invVessel = ((IEmptyVesselItem)invItem).getVesselType();
            }

            if (invVessel != null) {
               if (singleType == null) {
                  singleType = invVessel;
               } else if (invVessel != singleType) {
                  singleType = null;
                  break;
               }
            }
         }
      }

      return singleType;
   }

   public ItemStack func_77572_b(CraftingInventory inv) {
      ItemStack result = super.func_77572_b(inv);
      if (result.func_77973_b() instanceof VesselDrinkItem) {
         VesselType ves = this.determineSingleVessel(inv);
         if (ves == null) {
            ves = VesselType.WOODEN_MUG;
         }

         VesselDrinkItem.setVessel(result, ves);
      }

      return result;
   }

   public NonNullList getRemainingItems(CraftingInventory inv) {
      NonNullList list = NonNullList.func_191197_a(inv.func_70302_i_(), ItemStack.field_190927_a);

      for(int i = 0; i < list.size(); ++i) {
         ItemStack item = inv.func_70301_a(i);
         if (item.hasContainerItem() && (this.leaveDrinkIngredientContainers || !(item.func_77973_b() instanceof VesselDrinkItem))) {
            list.set(i, item.getContainerItem());
         }
      }

      return list;
   }

   public static class Serializer extends ForgeRegistryEntry implements IRecipeSerializer {
      private final net.minecraft.item.crafting.ShapelessRecipe.Serializer internalSerializer = new net.minecraft.item.crafting.ShapelessRecipe.Serializer();

      public VesselDrinkShapelessRecipe read(ResourceLocation recipeId, JsonObject json) {
         ShapelessRecipe recipe = this.internalSerializer.func_199425_a_(recipeId, json);
         boolean leaveDrinkIngredientContainers = json.has("leave_drink_ingredient_containers") ? json.get("leave_drink_ingredient_containers").getAsBoolean() : true;
         return new VesselDrinkShapelessRecipe(recipe.func_199560_c(), recipe.func_193358_e(), recipe.func_77571_b(), recipe.func_192400_c(), leaveDrinkIngredientContainers);
      }

      public VesselDrinkShapelessRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
         ShapelessRecipe recipe = this.internalSerializer.func_199426_a_(recipeId, buffer);
         boolean leaveDrinkIngredientContainers = buffer.readBoolean();
         return new VesselDrinkShapelessRecipe(recipe.func_199560_c(), recipe.func_193358_e(), recipe.func_77571_b(), recipe.func_192400_c(), leaveDrinkIngredientContainers);
      }

      public void write(PacketBuffer buffer, VesselDrinkShapelessRecipe recipe) {
         this.internalSerializer.func_199427_a_(buffer, recipe);
         buffer.writeBoolean(recipe.leaveDrinkIngredientContainers);
      }
   }
}
