package lotr.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class LOTRContainerPouch extends Container {
   private final int thePouchSlot;
   private ItemStack thePouchItem;
   public LOTRInventoryPouch pouchInventory;
   public int capacity;

   public LOTRContainerPouch(EntityPlayer entityplayer, int slot) {
      this.thePouchSlot = slot;
      this.thePouchItem = entityplayer.field_71071_by.func_70301_a(this.thePouchSlot);
      this.pouchInventory = new LOTRInventoryPouch(entityplayer, this, slot);
      this.capacity = this.pouchInventory.func_70302_i_();
      int rows = this.capacity / 9;

      int i;
      int j;
      for(i = 0; i < rows; ++i) {
         for(j = 0; j < 9; ++j) {
            this.func_75146_a(new LOTRSlotPouch(this.pouchInventory, j + i * 9, 8 + j * 18, 30 + i * 18));
         }
      }

      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(entityplayer.field_71071_by, j + i * 9 + 9, 8 + j * 18, 98 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(entityplayer.field_71071_by, i, 8 + i * 18, 156));
      }

   }

   public String getDisplayName() {
      return this.pouchInventory.func_145825_b();
   }

   public void renamePouch(String name) {
      if (StringUtils.isBlank(name)) {
         this.pouchInventory.getPouchItem().func_135074_t();
      } else {
         this.pouchInventory.getPouchItem().func_151001_c(name);
      }

      this.syncPouchItem(this.pouchInventory.getPouchItem());
   }

   public void syncPouchItem(ItemStack itemstack) {
      this.thePouchItem = itemstack;
   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return ItemStack.func_77989_b(this.thePouchItem, this.pouchInventory.getPouchItem());
   }

   public ItemStack func_75144_a(int slotNo, int subActionNo, int actionNo, EntityPlayer entityplayer) {
      if (isPouchSlot(this, slotNo, entityplayer, this.thePouchSlot)) {
         return null;
      } else {
         return actionNo == 2 && subActionNo == this.thePouchSlot ? null : super.func_75144_a(slotNo, subActionNo, actionNo, entityplayer);
      }
   }

   public static boolean isPouchSlot(Container container, int slotNo, EntityPlayer entityplayer, int pouchSlotNo) {
      if (slotNo >= 0 && slotNo < container.field_75151_b.size()) {
         Slot slot = (Slot)container.field_75151_b.get(slotNo);
         if (slot.field_75224_c == entityplayer.field_71071_by && slot.getSlotIndex() == pouchSlotNo) {
            return true;
         }
      }

      return false;
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      Slot aPouchSlot = this.func_75147_a(this.pouchInventory, 0);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i < this.capacity) {
            if (!this.func_75135_a(itemstack1, this.capacity, this.capacity + 36, true)) {
               return null;
            }
         } else if (aPouchSlot.func_75214_a(itemstack1) && !this.func_75135_a(itemstack1, 0, this.capacity, false)) {
            return null;
         }

         if (itemstack1.field_77994_a == 0) {
            slot.func_75215_d((ItemStack)null);
         } else {
            slot.func_75218_e();
         }

         if (itemstack1.field_77994_a == itemstack.field_77994_a) {
            return null;
         }

         slot.func_82870_a(entityplayer, itemstack1);
      }

      return itemstack;
   }
}
