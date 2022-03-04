package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerNPCMountInventory extends Container {
   private IInventory theMountInv;
   private LOTREntityNPCRideable theMount;

   public LOTRContainerNPCMountInventory(IInventory playerInv, IInventory mountInv, final LOTREntityNPCRideable mount) {
      this.theMountInv = mountInv;
      this.theMount = mount;
      mountInv.func_70295_k_();
      this.func_75146_a(new Slot(mountInv, 0, 8, 18) {
         public boolean func_75214_a(ItemStack itemstack) {
            return super.func_75214_a(itemstack) && itemstack.func_77973_b() == Items.field_151141_av && !this.func_75216_d();
         }
      });
      this.func_75146_a(new Slot(mountInv, 1, 8, 36) {
         public boolean func_75214_a(ItemStack itemstack) {
            return super.func_75214_a(itemstack) && mount.isMountArmorValid(itemstack);
         }
      });
      int chestRows = 3;
      int yOffset = (chestRows - 4) * 18;

      int j;
      for(j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.func_75146_a(new Slot(playerInv, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + yOffset));
         }
      }

      for(j = 0; j < 9; ++j) {
         this.func_75146_a(new Slot(playerInv, j, 8 + j * 18, 160 + yOffset));
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theMountInv.func_70300_a(entityplayer) && this.theMount.func_70089_S() && this.theMount.func_70032_d(entityplayer) < 8.0F;
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int slotIndex) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(slotIndex);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (slotIndex < this.theMountInv.func_70302_i_()) {
            if (!this.func_75135_a(itemstack1, this.theMountInv.func_70302_i_(), this.field_75151_b.size(), true)) {
               return null;
            }
         } else if (this.func_75139_a(1).func_75214_a(itemstack1) && !this.func_75139_a(1).func_75216_d()) {
            if (!this.func_75135_a(itemstack1, 1, 2, false)) {
               return null;
            }
         } else if (this.func_75139_a(0).func_75214_a(itemstack1)) {
            if (!this.func_75135_a(itemstack1, 0, 1, false)) {
               return null;
            }
         } else if (this.theMountInv.func_70302_i_() <= 2 || !this.func_75135_a(itemstack1, 2, this.theMountInv.func_70302_i_(), false)) {
            return null;
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
      super.func_75134_a(entityplayer);
      this.theMountInv.func_70305_f();
   }
}
