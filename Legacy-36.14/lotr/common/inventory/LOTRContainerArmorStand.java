package lotr.common.inventory;

import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class LOTRContainerArmorStand extends Container {
   private LOTRTileEntityArmorStand theArmorStand;

   public LOTRContainerArmorStand(InventoryPlayer inv, LOTRTileEntityArmorStand armorStand) {
      this.theArmorStand = armorStand;

      int i;
      for(i = 0; i < 4; ++i) {
         this.func_75146_a(new LOTRSlotArmorStand(armorStand, i, 80, 21 + i * 18, i, inv.field_70458_d));
      }

      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 165));
      }

      for(i = 0; i < 4; ++i) {
         this.func_75146_a(new LOTRSlotArmorStand(inv, 36 + (3 - i), 8, 21 + i * 18, i, inv.field_70458_d));
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theArmorStand.func_70300_a(entityplayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i < 4) {
            if (!this.func_75135_a(itemstack1, 4, 40, true)) {
               return null;
            }
         } else {
            if (!(itemstack.func_77973_b() instanceof ItemArmor) || this.func_75147_a(this.theArmorStand, ((ItemArmor)itemstack.func_77973_b()).field_77881_a).func_75216_d()) {
               return null;
            }

            int j = ((ItemArmor)itemstack.func_77973_b()).field_77881_a;
            if (!this.func_75135_a(itemstack1, j, j + 1, false)) {
               return null;
            }
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
