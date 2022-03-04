package lotr.common.inv;

import java.util.List;
import lotr.common.item.PouchItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class PouchInventory extends Inventory {
   private final ItemStack pouch;
   private final boolean isWritable;
   private boolean enableWriting = true;

   public static PouchInventory temporaryReadOnly(ItemStack pouch) {
      return new PouchInventory(pouch, false);
   }

   public static PouchInventory temporaryWritable(ItemStack pouch) {
      return new PouchInventory(pouch, true);
   }

   public static PouchInventory worldSidedInventory(ItemStack pouch, World world) {
      return new PouchInventory(pouch, !world.field_72995_K);
   }

   private PouchInventory(ItemStack pouch, boolean isWritable) {
      super(getCapacity(pouch));
      this.pouch = pouch;
      this.isWritable = isWritable;
      this.enableWriting = false;
      this.loadPouchContents();
      this.enableWriting = true;
   }

   private static int getCapacity(ItemStack pouch) {
      if (pouch.func_77973_b() instanceof PouchItem) {
         return ((PouchItem)pouch.func_77973_b()).getCapacity();
      } else {
         throw new IllegalArgumentException("Item " + pouch.func_77973_b().getRegistryName() + " does not contain a pouch inventory!");
      }
   }

   public void func_70296_d() {
      super.func_70296_d();
      if (this.isWritable && this.enableWriting) {
         this.savePouchContents();
      }

   }

   public void reloadFromItemNBT() {
      this.loadPouchContents();
   }

   public void fillPouchFromList(List contents) {
      if (contents.size() > this.func_70302_i_()) {
         throw new IllegalArgumentException("Too many items (" + contents.size() + ") for a pouch of size " + this.func_70302_i_());
      } else {
         this.enableWriting = false;

         for(int i = 0; i < contents.size(); ++i) {
            this.func_70299_a(i, (ItemStack)contents.get(i));
         }

         this.enableWriting = true;
         this.func_70296_d();
      }
   }

   private void loadPouchContents() {
      CompoundNBT pouchNBT = PouchItem.getOrCreatePouchRootNBT(this.pouch);
      NonNullList temp = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      ItemStackHelper.func_191283_b(pouchNBT, temp);

      for(int i = 0; i < this.func_70302_i_(); ++i) {
         this.func_70299_a(i, (ItemStack)temp.get(i));
      }

   }

   private void savePouchContents() {
      NonNullList temp = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);

      for(int i = 0; i < this.func_70302_i_(); ++i) {
         temp.set(i, this.func_70301_a(i));
      }

      CompoundNBT pouchNBT = PouchItem.getOrCreatePouchRootNBT(this.pouch);
      ItemStackHelper.func_191281_a(pouchNBT, temp, true);
   }

   public int getNumSlotsFull() {
      int slotsFull = 0;

      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack stack = this.func_70301_a(i);
         if (!stack.func_190926_b()) {
            ++slotsFull;
         }
      }

      return slotsFull;
   }
}
