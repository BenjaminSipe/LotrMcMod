package lotr.common.inventory;

import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRContainerBookshelf extends Container {
   public LOTRTileEntityBookshelf shelfInv;
   private int numRows;

   public LOTRContainerBookshelf(IInventory player, LOTRTileEntityBookshelf shelf) {
      this.shelfInv = shelf;
      this.numRows = this.shelfInv.func_70302_i_() / 9;
      this.shelfInv.func_70295_k_();
      int playerSlotY = (this.numRows - 4) * 18;

      int i;
      int i;
      for(i = 0; i < this.numRows; ++i) {
         for(i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot(this.shelfInv, i + i * 9, 8 + i * 18, 18 + i * 18) {
               public boolean func_75214_a(ItemStack itemstack) {
                  return LOTRTileEntityBookshelf.isBookItem(itemstack);
               }
            });
         }
      }

      for(i = 0; i < 3; ++i) {
         for(i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot(player, i + i * 9 + 9, 8 + i * 18, 103 + i * 18 + playerSlotY));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(player, i, 8 + i * 18, 161 + playerSlotY));
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.shelfInv.func_70300_a(entityplayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i < this.numRows * 9) {
            if (!this.func_75135_a(itemstack1, this.numRows * 9, this.field_75151_b.size(), true)) {
               return null;
            }
         } else {
            if (!LOTRTileEntityBookshelf.isBookItem(itemstack)) {
               return null;
            }

            if (!this.func_75135_a(itemstack1, 0, this.numRows * 9, false)) {
               return null;
            }
         }

         if (itemstack1.field_77994_a == 0) {
            slot.func_75215_d((ItemStack)null);
         } else {
            slot.func_75218_e();
         }
      }

      return itemstack;
   }

   public void func_75134_a(EntityPlayer entityplayer) {
      boolean anyContents = false;

      for(int i = 0; i < this.shelfInv.func_70302_i_(); ++i) {
         if (this.shelfInv.func_70301_a(i) != null) {
            anyContents = true;
            break;
         }
      }

      super.func_75134_a(entityplayer);
      this.shelfInv.func_70305_f();
      if (!anyContents && this.shelfInv.numPlayersUsing <= 0) {
         World world = this.shelfInv.func_145831_w();
         if (!world.field_72995_K) {
            world.func_147465_d(this.shelfInv.field_145851_c, this.shelfInv.field_145848_d, this.shelfInv.field_145849_e, Blocks.field_150342_X, 0, 3);
         }
      }

   }
}
