package lotr.common.inv;

import java.util.Optional;
import lotr.common.LOTRLog;
import lotr.common.util.UsernameHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class FactionCraftingResultSlot extends CraftingResultSlot {
   private final PlayerEntity slotPlayer;
   private final CraftingInventory craftMatrix;
   private final CraftResultInventory resultInventory;
   private final FactionCraftingContainer tableContainer;

   public FactionCraftingResultSlot(PlayerEntity player, FactionCraftingContainer table, CraftingInventory tableInv, CraftResultInventory resultInv, int slot, int x, int y) {
      super(player, tableInv, resultInv, slot, x, y);
      this.slotPlayer = player;
      this.craftMatrix = tableInv;
      this.resultInventory = resultInv;
      this.tableContainer = table;
   }

   public ItemStack func_190901_a(PlayerEntity player, ItemStack stack) {
      this.func_75208_c(stack);
      ForgeHooks.setCraftingPlayer(player);
      IRecipeType recipeType = this.determineRecipeType(player);
      NonNullList nonnulllist = player.field_70170_p.func_199532_z().func_215369_c(recipeType, this.craftMatrix, player.field_70170_p);
      ForgeHooks.setCraftingPlayer((PlayerEntity)null);

      for(int i = 0; i < nonnulllist.size(); ++i) {
         ItemStack inMatrix = this.craftMatrix.func_70301_a(i);
         ItemStack inList = (ItemStack)nonnulllist.get(i);
         if (!inMatrix.func_190926_b()) {
            this.craftMatrix.func_70298_a(i, 1);
            inMatrix = this.craftMatrix.func_70301_a(i);
         }

         if (!inList.func_190926_b()) {
            if (inMatrix.func_190926_b()) {
               this.craftMatrix.func_70299_a(i, inList);
            } else if (ItemStack.func_179545_c(inMatrix, inList) && ItemStack.func_77970_a(inMatrix, inList)) {
               inList.func_190917_f(inMatrix.func_190916_E());
               this.craftMatrix.func_70299_a(i, inList);
            } else if (!this.slotPlayer.field_71071_by.func_70441_a(inList)) {
               this.slotPlayer.func_71019_a(inList, false);
            }
         }
      }

      return stack;
   }

   private IRecipeType determineRecipeType(PlayerEntity player) {
      Optional optRecipe = this.tableContainer.findMatchingRecipeOfAppropriateType(this.slotPlayer.field_70170_p, this.slotPlayer, this.craftMatrix);
      if (optRecipe.isPresent()) {
         ICraftingRecipe recipe = (ICraftingRecipe)optRecipe.get();
         World world = player.field_70170_p;
         boolean canUseRecipe = world.field_72995_K ? true : this.resultInventory.func_201561_a(world, (ServerPlayerEntity)player, recipe);
         if (canUseRecipe) {
            return ((ICraftingRecipe)optRecipe.get()).func_222127_g();
         }
      }

      LOTRLog.error("Faction crafting table (%s) failed to determine the type of the crafted recipe (crafter = %s)", this.tableContainer.func_216957_a().getRegistryName(), UsernameHelper.getRawUsername(this.slotPlayer));
      return IRecipeType.field_222149_a;
   }
}
