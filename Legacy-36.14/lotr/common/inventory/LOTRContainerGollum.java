package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerGollum extends Container {
   public LOTREntityGollum theGollum;

   public LOTRContainerGollum(InventoryPlayer inv, LOTREntityGollum gollum) {
      this.theGollum = gollum;

      int i;
      int j;
      for(i = 0; i < LOTREntityGollum.INV_ROWS; ++i) {
         for(j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(gollum.inventory, j + i * 9, 8 + j * 18, 18 + i * 18));
         }
      }

      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 144));
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theGollum != null && this.theGollum.getGollumOwner() == entityplayer && entityplayer.func_70068_e(this.theGollum) <= 144.0D && this.theGollum.func_70089_S();
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i < LOTREntityGollum.INV_ROWS * 9) {
            if (!this.func_75135_a(itemstack1, LOTREntityGollum.INV_ROWS * 9, this.field_75151_b.size(), true)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 0, LOTREntityGollum.INV_ROWS * 9, false)) {
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
