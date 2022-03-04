package lotr.common.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ProxyFurnaceInventory implements IInventory {
   private final IInventory parentInv;
   private final int parentInputSlot;
   private final int parentFuelSlot;
   private final int parentOutputSlot;
   private static final int FURNACE_INPUT_SLOT = 0;
   private static final int FURNACE_FUEL_SLOT = 1;
   private static final int FURNACE_OUTPUT_SLOT = 2;

   public ProxyFurnaceInventory(IInventory inv, int in, int fuel, int out) {
      this.parentInv = inv;
      this.parentInputSlot = in;
      this.parentFuelSlot = fuel;
      this.parentOutputSlot = out;
   }

   private int mapFurnaceSlot(int index) {
      switch(index) {
      case 0:
         return this.parentInputSlot;
      case 1:
         return this.parentFuelSlot;
      case 2:
         return this.parentOutputSlot;
      default:
         throw new IllegalArgumentException("Invalid index " + index + " for a Proxy Furnace inventory");
      }
   }

   public void func_174888_l() {
      this.parentInv.func_70299_a(this.parentInputSlot, ItemStack.field_190927_a);
      this.parentInv.func_70299_a(this.parentFuelSlot, ItemStack.field_190927_a);
      this.parentInv.func_70299_a(this.parentOutputSlot, ItemStack.field_190927_a);
   }

   public int func_70302_i_() {
      return 3;
   }

   public boolean func_191420_l() {
      return !this.parentInv.func_70301_a(this.parentInputSlot).func_190926_b() || !this.parentInv.func_70301_a(this.parentFuelSlot).func_190926_b() || !this.parentInv.func_70301_a(this.parentOutputSlot).func_190926_b();
   }

   public ItemStack func_70301_a(int index) {
      return this.parentInv.func_70301_a(this.mapFurnaceSlot(index));
   }

   public ItemStack func_70298_a(int index, int count) {
      return this.parentInv.func_70298_a(this.mapFurnaceSlot(index), count);
   }

   public ItemStack func_70304_b(int index) {
      return this.parentInv.func_70304_b(this.mapFurnaceSlot(index));
   }

   public void func_70299_a(int index, ItemStack stack) {
      this.parentInv.func_70299_a(this.mapFurnaceSlot(index), stack);
   }

   public void func_70296_d() {
      this.parentInv.func_70296_d();
   }

   public boolean func_70300_a(PlayerEntity player) {
      return this.parentInv.func_70300_a(player);
   }
}
