package lotr.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerChestWithPouch extends ContainerChest {
   public IInventory chestInv;
   public LOTRContainerPouch pouchContainer;
   private final int thePouchSlot;
   private int numChestRows;
   private int numPouchRows;

   public LOTRContainerChestWithPouch(EntityPlayer entityplayer, int pouchSlot, IInventory chest) {
      super(entityplayer.field_71071_by, chest);
      this.field_75151_b.clear();
      this.field_75153_a.clear();
      this.chestInv = chest;
      this.numChestRows = chest.func_70302_i_() / 9;
      this.thePouchSlot = pouchSlot;
      this.pouchContainer = new LOTRContainerPouch(entityplayer, this.thePouchSlot);
      this.numPouchRows = this.pouchContainer.capacity / 9;

      int pouchSlotsY;
      int playerSlotsY;
      for(pouchSlotsY = 0; pouchSlotsY < this.numChestRows; ++pouchSlotsY) {
         for(playerSlotsY = 0; playerSlotsY < 9; ++playerSlotsY) {
            this.func_75146_a(new Slot(chest, playerSlotsY + pouchSlotsY * 9, 8 + playerSlotsY * 18, 18 + pouchSlotsY * 18));
         }
      }

      pouchSlotsY = 103 + (this.numChestRows - 4) * 18;

      int i;
      int j;
      for(playerSlotsY = 0; playerSlotsY < this.numPouchRows; ++playerSlotsY) {
         for(i = 0; i < 9; ++i) {
            j = i + playerSlotsY * 9;
            Slot slot = this.pouchContainer.func_75147_a(this.pouchContainer.pouchInventory, j);
            slot.field_75223_e = 8 + i * 18;
            slot.field_75221_f = pouchSlotsY + playerSlotsY * 18;
            this.func_75146_a(slot);
         }
      }

      playerSlotsY = pouchSlotsY + 67;

      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(entityplayer.field_71071_by, j + i * 9 + 9, 8 + j * 18, playerSlotsY + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(entityplayer.field_71071_by, i, 8 + i * 18, playerSlotsY + 58));
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.chestInv.func_70300_a(entityplayer) && this.pouchContainer.func_75145_c(entityplayer);
   }

   public ItemStack func_75144_a(int slotNo, int subActionNo, int actionNo, EntityPlayer entityplayer) {
      if (LOTRContainerPouch.isPouchSlot(this, slotNo, entityplayer, this.thePouchSlot)) {
         return null;
      } else {
         return actionNo == 2 && subActionNo == this.thePouchSlot ? null : super.func_75144_a(slotNo, subActionNo, actionNo, entityplayer);
      }
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      Slot aPouchSlot = this.func_75147_a(this.pouchContainer.pouchInventory, 0);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i < this.numChestRows * 9) {
            if (aPouchSlot.func_75214_a(itemstack1) && !this.func_75135_a(itemstack1, this.numChestRows * 9, (this.numChestRows + this.numPouchRows) * 9, true)) {
               return null;
            }
         } else if (i < (this.numChestRows + this.numPouchRows) * 9) {
            if (!this.func_75135_a(itemstack1, 0, this.numChestRows * 9, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 0, this.numChestRows * 9, false)) {
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

   public void func_75134_a(EntityPlayer entityplayer) {
      super.func_75134_a(entityplayer);
   }
}
