package lotr.common.inventory;

import lotr.common.item.LOTRItemDaleCracker;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSealCracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerDaleCracker extends Container {
   private ItemStack theCrackerItem;
   private IInventory crackerInventory;
   private int capacity;

   public LOTRContainerDaleCracker(EntityPlayer entityplayer) {
      this.theCrackerItem = entityplayer.field_71071_by.func_70448_g();
      this.capacity = 3;
      this.crackerInventory = new InventoryBasic("cracker", false, this.capacity);

      int i;
      for(i = 0; i < this.capacity; ++i) {
         this.func_75146_a(new LOTRSlotDaleCracker(this.crackerInventory, i, 62 + i * 18, 24));
      }

      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(entityplayer.field_71071_by, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(entityplayer.field_71071_by, i, 8 + i * 18, 142));
      }

   }

   public boolean isCrackerInvEmpty() {
      for(int i = 0; i < this.crackerInventory.func_70302_i_(); ++i) {
         ItemStack itemstack = this.crackerInventory.func_70301_a(i);
         if (itemstack != null) {
            return false;
         }
      }

      return true;
   }

   public void sendSealingPacket(EntityPlayer entityplayer) {
      LOTRPacketSealCracker packet = new LOTRPacketSealCracker();
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }

   public void receiveSealingPacket(EntityPlayer entityplayer) {
      if (!this.isCrackerInvEmpty()) {
         IInventory tempContents = new InventoryBasic("crackerTemp", false, this.crackerInventory.func_70302_i_());

         for(int i = 0; i < tempContents.func_70302_i_(); ++i) {
            tempContents.func_70299_a(i, this.crackerInventory.func_70301_a(i));
            this.crackerInventory.func_70299_a(i, (ItemStack)null);
         }

         LOTRItemDaleCracker.setEmpty(this.theCrackerItem, false);
         LOTRItemDaleCracker.setSealingPlayerName(this.theCrackerItem, entityplayer.func_70005_c_());
         LOTRItemDaleCracker.setCustomCrackerContents(this.theCrackerItem, tempContents);
      }

   }

   public void func_75134_a(EntityPlayer entityplayer) {
      super.func_75134_a(entityplayer);
      if (!entityplayer.field_70170_p.field_72995_K) {
         for(int i = 0; i < this.crackerInventory.func_70302_i_(); ++i) {
            ItemStack itemstack = this.crackerInventory.func_70304_b(i);
            if (itemstack != null) {
               entityplayer.func_71019_a(itemstack, false);
            }
         }
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return ItemStack.func_77989_b(this.theCrackerItem, entityplayer.field_71071_by.func_70448_g());
   }

   public ItemStack func_75144_a(int slotNo, int j, int k, EntityPlayer entityplayer) {
      if (slotNo >= 0 && slotNo < this.field_75151_b.size()) {
         Slot slot = (Slot)this.field_75151_b.get(slotNo);
         if (slot.field_75224_c == entityplayer.field_71071_by && slot.getSlotIndex() == entityplayer.field_71071_by.field_70461_c) {
            return null;
         }
      }

      return super.func_75144_a(slotNo, j, k, entityplayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      Slot aCrackerSlot = this.func_75147_a(this.crackerInventory, 0);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i < this.capacity) {
            if (!this.func_75135_a(itemstack1, this.capacity, this.capacity + 36, true)) {
               return null;
            }
         } else if (aCrackerSlot.func_75214_a(itemstack1) && !this.func_75135_a(itemstack1, 0, this.capacity, false)) {
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
