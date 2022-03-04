package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.entity.npc.LOTRInventoryHiredReplacedItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerHiredWarriorInventory extends Container {
   private LOTREntityNPC theNPC;
   public LOTRInventoryHiredReplacedItems npcInv;
   public IInventory proxyInv;
   private final int npcFullInvSize;
   private int npcActiveSlotCount;

   public LOTRContainerHiredWarriorInventory(InventoryPlayer inv, LOTREntityNPC entity) {
      this.theNPC = entity;
      this.npcInv = this.theNPC.hiredReplacedInv;
      this.npcFullInvSize = this.npcInv.func_70302_i_();
      this.proxyInv = new InventoryBasic("npcTemp", false, this.npcFullInvSize);

      int i;
      LOTRSlotHiredReplaceItem slot;
      for(i = 0; i < 4; ++i) {
         slot = new LOTRSlotHiredReplaceItem(new LOTRSlotArmorStand(this.proxyInv, i, 80, 21 + i * 18, i, inv.field_70458_d), this.theNPC);
         this.func_75146_a(slot);
      }

      int[] var10000 = new int[1];
      LOTRInventoryHiredReplacedItems var10003 = this.npcInv;
      var10000[0] = 4;
      int[] var8 = var10000;
      int j = var8.length;

      for(int var5 = 0; var5 < j; ++var5) {
         int i = var8[var5];
         Slot slot = new LOTRSlotHiredReplaceItem(new LOTRSlotMeleeWeapon(this.proxyInv, i, 50, 48), this.theNPC);
         this.func_75146_a(slot);
      }

      if (this.theNPC instanceof LOTREntityOrc && ((LOTREntityOrc)this.theNPC).isOrcBombardier()) {
         LOTRInventoryHiredReplacedItems var11 = this.npcInv;
         int i = 5;
         slot = new LOTRSlotHiredReplaceItem(new LOTRSlotBomb(this.proxyInv, i, 110, 48), this.theNPC);
         this.func_75146_a(slot);
      }

      for(i = 0; i < this.npcFullInvSize; ++i) {
         if (this.func_75147_a(this.proxyInv, i) != null) {
            ++this.npcActiveSlotCount;
         }
      }

      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 165));
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theNPC != null && this.theNPC.func_70089_S() && this.theNPC.hiredNPCInfo.isActive && this.theNPC.hiredNPCInfo.getHiringPlayer() == entityplayer && this.theNPC.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR && entityplayer.func_70068_e(this.theNPC) <= 144.0D;
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
         if (slot.field_75224_c == this.proxyInv) {
            if (!this.func_75135_a(itemstack1, this.npcActiveSlotCount, this.field_75151_b.size(), true)) {
               return null;
            }
         } else {
            for(int j = 0; j < this.npcFullInvSize; ++j) {
               Slot npcSlot = this.func_75147_a(this.proxyInv, j);
               if (npcSlot != null) {
                  int npcSlotNo = npcSlot.field_75222_d;
                  if (npcSlot.func_75214_a(itemstack1) && !this.func_75135_a(itemstack1, npcSlotNo, npcSlotNo + 1, false)) {
                     return null;
                  }
               }
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
