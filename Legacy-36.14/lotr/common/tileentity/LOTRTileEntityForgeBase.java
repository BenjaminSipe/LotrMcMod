package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import lotr.common.block.LOTRBlockForgeBase;
import lotr.common.inventory.LOTRSlotStackSize;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LOTRTileEntityForgeBase extends TileEntity implements IInventory, ISidedInventory {
   protected ItemStack[] inventory = new ItemStack[this.getForgeInvSize()];
   private String specialForgeName;
   public int forgeSmeltTime = 0;
   public int currentItemFuelValue = 0;
   public int currentSmeltTime = 0;
   public int[] inputSlots;
   public int[] outputSlots;
   public int fuelSlot;

   public LOTRTileEntityForgeBase() {
      this.setupForgeSlots();
   }

   public abstract int getForgeInvSize();

   public abstract void setupForgeSlots();

   public abstract int getSmeltingDuration();

   protected boolean canMachineInsertInput(ItemStack itemstack) {
      return true;
   }

   protected boolean canMachineInsertFuel(ItemStack itemstack) {
      return TileEntityFurnace.func_145954_b(itemstack);
   }

   public int func_70302_i_() {
      return this.inventory.length;
   }

   public ItemStack func_70301_a(int i) {
      return this.inventory[i];
   }

   public ItemStack func_70298_a(int i, int j) {
      if (this.inventory[i] != null) {
         ItemStack itemstack;
         if (this.inventory[i].field_77994_a <= j) {
            itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
         } else {
            itemstack = this.inventory[i].func_77979_a(j);
            if (this.inventory[i].field_77994_a == 0) {
               this.inventory[i] = null;
            }

            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack func_70304_b(int i) {
      if (this.inventory[i] != null) {
         ItemStack itemstack = this.inventory[i];
         this.inventory[i] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void func_70299_a(int i, ItemStack itemstack) {
      this.inventory[i] = itemstack;
      if (itemstack != null && itemstack.field_77994_a > this.func_70297_j_()) {
         itemstack.field_77994_a = this.func_70297_j_();
      }

   }

   public String func_145825_b() {
      return this.func_145818_k_() ? this.specialForgeName : this.getForgeName();
   }

   public abstract String getForgeName();

   public boolean func_145818_k_() {
      return this.specialForgeName != null && this.specialForgeName.length() > 0;
   }

   public void setSpecialForgeName(String s) {
      this.specialForgeName = s;
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      NBTTagList items = nbt.func_150295_c("Items", 10);
      this.inventory = new ItemStack[this.func_70302_i_()];

      for(int i = 0; i < items.func_74745_c(); ++i) {
         NBTTagCompound itemData = items.func_150305_b(i);
         int slot = itemData.func_74771_c("Slot");
         if (slot >= 0 && slot < this.inventory.length) {
            this.inventory[slot] = ItemStack.func_77949_a(itemData);
         }
      }

      this.forgeSmeltTime = nbt.func_74765_d("BurnTime");
      this.currentSmeltTime = nbt.func_74765_d("SmeltTime");
      this.currentItemFuelValue = TileEntityFurnace.func_145952_a(this.inventory[this.fuelSlot]);
      if (nbt.func_74764_b("CustomName")) {
         this.specialForgeName = nbt.func_74779_i("CustomName");
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      NBTTagList items = new NBTTagList();

      for(int i = 0; i < this.inventory.length; ++i) {
         if (this.inventory[i] != null) {
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.func_74774_a("Slot", (byte)i);
            this.inventory[i].func_77955_b(itemData);
            items.func_74742_a(itemData);
         }
      }

      nbt.func_74782_a("Items", items);
      nbt.func_74777_a("BurnTime", (short)this.forgeSmeltTime);
      nbt.func_74777_a("SmeltTime", (short)this.currentSmeltTime);
      if (this.func_145818_k_()) {
         nbt.func_74778_a("CustomName", this.specialForgeName);
      }

   }

   public int func_70297_j_() {
      return 64;
   }

   @SideOnly(Side.CLIENT)
   public int getSmeltProgressScaled(int i) {
      return this.currentSmeltTime * i / this.getSmeltingDuration();
   }

   @SideOnly(Side.CLIENT)
   public int getSmeltTimeRemainingScaled(int i) {
      if (this.currentItemFuelValue == 0) {
         this.currentItemFuelValue = this.getSmeltingDuration();
      }

      return this.forgeSmeltTime * i / this.currentItemFuelValue;
   }

   public boolean isSmelting() {
      return this.forgeSmeltTime > 0;
   }

   protected void toggleForgeActive() {
      LOTRBlockForgeBase.toggleForgeActive(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
   }

   protected abstract boolean canDoSmelting();

   protected abstract void doSmelt();

   public void func_145845_h() {
      boolean smelting = this.forgeSmeltTime > 0;
      boolean needUpdate = false;
      if (this.forgeSmeltTime > 0) {
         --this.forgeSmeltTime;
      }

      if (!this.field_145850_b.field_72995_K) {
         if (this.forgeSmeltTime == 0 && this.canDoSmelting()) {
            this.currentItemFuelValue = this.forgeSmeltTime = TileEntityFurnace.func_145952_a(this.inventory[this.fuelSlot]);
            if (this.forgeSmeltTime > 0) {
               needUpdate = true;
               if (this.inventory[this.fuelSlot] != null) {
                  --this.inventory[this.fuelSlot].field_77994_a;
                  if (this.inventory[this.fuelSlot].field_77994_a == 0) {
                     this.inventory[this.fuelSlot] = this.inventory[this.fuelSlot].func_77973_b().getContainerItem(this.inventory[this.fuelSlot]);
                  }
               }
            }
         }

         if (this.isSmelting() && this.canDoSmelting()) {
            ++this.currentSmeltTime;
            if (this.currentSmeltTime == this.getSmeltingDuration()) {
               this.currentSmeltTime = 0;
               this.doSmelt();
               needUpdate = true;
            }
         } else {
            this.currentSmeltTime = 0;
         }

         if (smelting != this.forgeSmeltTime > 0) {
            needUpdate = true;
            this.toggleForgeActive();
         }
      }

      if (needUpdate) {
         this.func_70296_d();
      }

   }

   public boolean func_70300_a(EntityPlayer entityplayer) {
      return this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && entityplayer.func_70092_e((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D) <= 64.0D;
   }

   public void func_70295_k_() {
   }

   public void func_70305_f() {
   }

   public boolean func_94041_b(int slot, ItemStack itemstack) {
      if (ArrayUtils.contains(this.inputSlots, slot)) {
         return this.canMachineInsertInput(itemstack);
      } else {
         return slot == this.fuelSlot ? this.canMachineInsertFuel(itemstack) : false;
      }
   }

   public int[] func_94128_d(int side) {
      ArrayList slotsWithStackSize;
      int[] sortedSlots;
      int i;
      int var5;
      int slot;
      if (side == 0) {
         slotsWithStackSize = new ArrayList();
         sortedSlots = this.outputSlots;
         i = sortedSlots.length;

         for(var5 = 0; var5 < i; ++var5) {
            slot = sortedSlots[var5];
            slotsWithStackSize.add(slot);
         }

         slotsWithStackSize.add(this.fuelSlot);
         sortedSlots = new int[slotsWithStackSize.size()];

         for(i = 0; i < sortedSlots.length; ++i) {
            sortedSlots[i] = (Integer)slotsWithStackSize.get(i);
         }

         return sortedSlots;
      } else if (side != 1) {
         return new int[]{this.fuelSlot};
      } else {
         slotsWithStackSize = new ArrayList();
         sortedSlots = this.inputSlots;
         i = sortedSlots.length;

         for(var5 = 0; var5 < i; ++var5) {
            slot = sortedSlots[var5];
            int size = this.func_70301_a(slot) == null ? 0 : this.func_70301_a(slot).field_77994_a;
            slotsWithStackSize.add(new LOTRSlotStackSize(slot, size));
         }

         Collections.sort(slotsWithStackSize);
         sortedSlots = new int[this.inputSlots.length];

         for(i = 0; i < sortedSlots.length; ++i) {
            LOTRSlotStackSize slotAndStack = (LOTRSlotStackSize)slotsWithStackSize.get(i);
            sortedSlots[i] = slotAndStack.slot;
         }

         return sortedSlots;
      }
   }

   public boolean func_102007_a(int slot, ItemStack itemstack, int side) {
      return this.func_94041_b(slot, itemstack);
   }

   public boolean func_102008_b(int slot, ItemStack itemstack, int side) {
      if (side == 0) {
         if (slot == this.fuelSlot) {
            return itemstack.func_77973_b() == Items.field_151133_ar;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
      if (packet.func_148857_g() != null && packet.func_148857_g().func_74764_b("CustomName")) {
         this.specialForgeName = packet.func_148857_g().func_74779_i("CustomName");
      }

   }
}
