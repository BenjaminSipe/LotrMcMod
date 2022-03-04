package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerHiredFarmerInventory extends Container {
   private LOTREntityNPC theNPC;

   public LOTRContainerHiredFarmerInventory(InventoryPlayer inv, LOTREntityNPC entity) {
      this.theNPC = entity;
      this.func_75146_a(new LOTRSlotSeeds(this.theNPC.hiredNPCInfo.getHiredInventory(), 0, 80, 21, this.theNPC.field_70170_p));

      int i;
      for(i = 0; i < 2; ++i) {
         this.func_75146_a(new LOTRSlotProtected(this.theNPC.hiredNPCInfo.getHiredInventory(), i + 1, 71 + i * 18, 47));
      }

      this.func_75146_a(new LOTRSlotBonemeal(this.theNPC.hiredNPCInfo.getHiredInventory(), 3, 123, 34, this.theNPC.field_70170_p));

      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 79 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 137));
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theNPC != null && this.theNPC.func_70089_S() && this.theNPC.hiredNPCInfo.isActive && this.theNPC.hiredNPCInfo.getHiringPlayer() == entityplayer && this.theNPC.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER && entityplayer.func_70068_e(this.theNPC) <= 144.0D;
   }

   public void func_75134_a(EntityPlayer entityplayer) {
      super.func_75134_a(entityplayer);
      if (!this.theNPC.field_70170_p.field_72995_K) {
         this.theNPC.hiredNPCInfo.sendClientPacket(true);
      }

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
            if (((Slot)this.field_75151_b.get(0)).func_75214_a(itemstack1) && !this.func_75135_a(itemstack1, 0, 1, false)) {
               return null;
            }

            if (((Slot)this.field_75151_b.get(3)).func_75214_a(itemstack1) && !this.func_75135_a(itemstack1, 3, 4, false)) {
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
