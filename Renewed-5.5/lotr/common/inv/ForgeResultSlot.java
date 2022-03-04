package lotr.common.inv;

import lotr.common.tileentity.AbstractAlloyForgeTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.hooks.BasicEventHooks;

public class ForgeResultSlot extends Slot {
   private final PlayerEntity player;
   private int removeCount;

   public ForgeResultSlot(PlayerEntity p, IInventory inv, int i, int x, int y) {
      super(inv, i, x, y);
      this.player = p;
   }

   public boolean func_75214_a(ItemStack stack) {
      return false;
   }

   public ItemStack func_75209_a(int amount) {
      if (this.func_75216_d()) {
         this.removeCount += Math.min(amount, this.func_75211_c().func_190916_E());
      }

      return super.func_75209_a(amount);
   }

   public ItemStack func_190901_a(PlayerEntity p, ItemStack stack) {
      this.func_75208_c(stack);
      super.func_190901_a(p, stack);
      return stack;
   }

   protected void func_75210_a(ItemStack stack, int amount) {
      this.removeCount += amount;
      this.func_75208_c(stack);
   }

   protected void func_75208_c(ItemStack stack) {
      stack.func_77980_a(this.player.field_70170_p, this.player, this.removeCount);
      if (!this.player.field_70170_p.field_72995_K && this.field_75224_c instanceof AbstractAlloyForgeTileEntity) {
         ((AbstractAlloyForgeTileEntity)this.field_75224_c).onResultTaken(this.player);
      }

      this.removeCount = 0;
      BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
   }
}
