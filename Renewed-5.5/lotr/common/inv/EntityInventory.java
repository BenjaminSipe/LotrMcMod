package lotr.common.inv;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class EntityInventory extends Inventory {
   protected final LivingEntity theEntity;
   private final String nbtName;

   public EntityInventory(LivingEntity entity, int size, String name) {
      super(size);
      this.theEntity = entity;
      this.nbtName = name;
   }

   public boolean isFull() {
      return !this.func_191420_l();
   }

   public final void dropAllItems() {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            this.doDropItem(itemstack);
            this.func_70299_a(i, ItemStack.field_190927_a);
         }
      }

   }

   protected void doDropItem(ItemStack itemstack) {
      this.theEntity.func_70099_a(itemstack, 0.0F);
   }

   public final ListNBT func_70487_g() {
      ListNBT items = new ListNBT();

      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            CompoundNBT itemData = new CompoundNBT();
            itemData.func_74774_a("Slot", (byte)i);
            itemstack.func_77955_b(itemData);
            items.add(itemData);
         }
      }

      return items;
   }

   public final void func_70486_a(ListNBT items) {
      for(int i = 0; i < items.size(); ++i) {
         CompoundNBT itemData = items.func_150305_b(i);
         int slot = itemData.func_74771_c("Slot");
         if (slot >= 0 && slot < this.func_70302_i_()) {
            this.func_70299_a(slot, ItemStack.func_199557_a(itemData));
         }
      }

   }

   public void writeToEntityNBT(CompoundNBT nbt) {
      nbt.func_218657_a(this.nbtName, this.func_70487_g());
   }

   public void readFromEntityNBT(CompoundNBT nbt) {
      this.func_70486_a(nbt.func_150295_c(this.nbtName, 10));
   }
}
