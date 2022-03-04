package lotr.common.inventory;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class LOTREntityInventory extends InventoryBasic {
   protected EntityLivingBase theEntity;
   private String nbtName;

   public LOTREntityInventory(String s, EntityLivingBase npc, int i) {
      super(s, true, i);
      this.theEntity = npc;
      this.nbtName = s;
   }

   public void writeToNBT(NBTTagCompound nbt) {
      NBTTagList items = new NBTTagList();

      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (itemstack != null) {
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.func_74774_a("Slot", (byte)i);
            itemstack.func_77955_b(itemData);
            items.func_74742_a(itemData);
         }
      }

      nbt.func_74782_a(this.nbtName, items);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      NBTTagList items = nbt.func_150295_c(this.nbtName, 10);

      for(int i = 0; i < items.func_74745_c(); ++i) {
         NBTTagCompound itemData = items.func_150305_b(i);
         int slot = itemData.func_74771_c("Slot");
         if (slot >= 0 && slot < this.func_70302_i_()) {
            this.func_70299_a(slot, ItemStack.func_77949_a(itemData));
         }
      }

   }

   public void dropAllItems() {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (itemstack != null) {
            this.dropItem(itemstack);
            this.func_70299_a(i, (ItemStack)null);
         }
      }

   }

   protected void dropItem(ItemStack itemstack) {
      this.theEntity.func_70099_a(itemstack, 0.0F);
   }

   public boolean isEmpty() {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         if (this.func_70301_a(i) != null) {
            return false;
         }
      }

      return true;
   }

   public boolean isFull() {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         if (this.func_70301_a(i) == null) {
            return false;
         }
      }

      return true;
   }

   public boolean addItemToInventory(ItemStack itemstack) {
      int origStack = itemstack.field_77994_a;
      if (itemstack != null && itemstack.field_77994_a > 0) {
         for(int i = 0; i < this.func_70302_i_() && itemstack.field_77994_a > 0; ++i) {
            ItemStack itemInSlot = this.func_70301_a(i);
            if (itemInSlot == null || itemInSlot.field_77994_a < itemInSlot.func_77976_d() && itemstack.func_77969_a(itemInSlot) && ItemStack.func_77970_a(itemInSlot, itemstack)) {
               if (itemInSlot == null) {
                  ItemStack copy = itemstack.func_77946_l();
                  copy.field_77994_a = Math.min(copy.field_77994_a, this.func_70297_j_());
                  this.func_70299_a(i, copy);
                  itemstack.field_77994_a -= copy.field_77994_a;
               } else {
                  int maxStackSize = itemInSlot.func_77976_d();
                  maxStackSize = Math.min(maxStackSize, this.func_70297_j_());
                  int difference = maxStackSize - itemInSlot.field_77994_a;
                  difference = Math.min(difference, itemstack.field_77994_a);
                  itemstack.field_77994_a -= difference;
                  itemInSlot.field_77994_a += difference;
                  this.func_70299_a(i, itemInSlot);
               }
            }
         }
      }

      return itemstack != null && itemstack.field_77994_a < origStack;
   }
}
