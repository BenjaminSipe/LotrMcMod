package lotr.common.tileentity;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockBookshelfStorage;
import lotr.common.inventory.LOTRContainerBookshelf;
import lotr.common.item.LOTRItemModifierTemplate;
import lotr.common.item.LOTRItemRedBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityBookshelf extends TileEntity implements IInventory {
   private ItemStack[] chestContents = new ItemStack[this.func_70302_i_()];
   public int numPlayersUsing;
   private int ticksSinceSync;

   public int func_70302_i_() {
      return 27;
   }

   public ItemStack func_70301_a(int i) {
      return this.chestContents[i];
   }

   public ItemStack func_70298_a(int i, int j) {
      if (this.chestContents[i] != null) {
         ItemStack itemstack;
         if (this.chestContents[i].field_77994_a <= j) {
            itemstack = this.chestContents[i];
            this.chestContents[i] = null;
            this.func_70296_d();
            return itemstack;
         } else {
            itemstack = this.chestContents[i].func_77979_a(j);
            if (this.chestContents[i].field_77994_a == 0) {
               this.chestContents[i] = null;
            }

            this.func_70296_d();
            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack func_70304_b(int i) {
      if (this.chestContents[i] != null) {
         ItemStack itemstack = this.chestContents[i];
         this.chestContents[i] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void func_70299_a(int i, ItemStack itemstack) {
      this.chestContents[i] = itemstack;
      if (itemstack != null && itemstack.field_77994_a > this.func_70297_j_()) {
         itemstack.field_77994_a = this.func_70297_j_();
      }

      this.func_70296_d();
   }

   public String func_145825_b() {
      return "container.lotr.bookshelf";
   }

   public boolean func_145818_k_() {
      return false;
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      NBTTagList itemTags = nbt.func_150295_c("Items", 10);
      this.chestContents = new ItemStack[this.func_70302_i_()];

      for(int i = 0; i < itemTags.func_74745_c(); ++i) {
         NBTTagCompound slotData = itemTags.func_150305_b(i);
         int slot = slotData.func_74771_c("Slot") & 255;
         if (slot >= 0 && slot < this.chestContents.length) {
            this.chestContents[slot] = ItemStack.func_77949_a(slotData);
         }
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      NBTTagList itemTags = new NBTTagList();

      for(int i = 0; i < this.chestContents.length; ++i) {
         if (this.chestContents[i] != null) {
            NBTTagCompound slotData = new NBTTagCompound();
            slotData.func_74774_a("Slot", (byte)i);
            this.chestContents[i].func_77955_b(slotData);
            itemTags.func_74742_a(slotData);
         }
      }

      nbt.func_74782_a("Items", itemTags);
   }

   public int func_70297_j_() {
      return 64;
   }

   public boolean func_70300_a(EntityPlayer entityplayer) {
      return this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && entityplayer.func_70092_e((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D) <= 64.0D;
   }

   public void func_145845_h() {
      super.func_145845_h();
      ++this.ticksSinceSync;
      if (!this.field_145850_b.field_72995_K && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.field_145851_c + this.field_145848_d + this.field_145849_e) % 200 == 0) {
         this.numPlayersUsing = 0;
         float range = 16.0F;
         List players = this.field_145850_b.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)((float)this.field_145851_c - range), (double)((float)this.field_145848_d - range), (double)((float)this.field_145849_e - range), (double)((float)(this.field_145851_c + 1) + range), (double)((float)(this.field_145848_d + 1) + range), (double)((float)(this.field_145849_e + 1) + range)));
         Iterator var3 = players.iterator();

         while(var3.hasNext()) {
            Object obj = var3.next();
            EntityPlayer entityplayer = (EntityPlayer)obj;
            if (entityplayer.field_71070_bA instanceof LOTRContainerBookshelf) {
               IInventory playerShelfInv = ((LOTRContainerBookshelf)entityplayer.field_71070_bA).shelfInv;
               if (playerShelfInv == this) {
                  ++this.numPlayersUsing;
               }
            }
         }
      }

   }

   public void func_70295_k_() {
      if (this.numPlayersUsing < 0) {
         this.numPlayersUsing = 0;
      }

      ++this.numPlayersUsing;
   }

   public void func_70305_f() {
      if (this.func_145838_q() instanceof LOTRBlockBookshelfStorage) {
         --this.numPlayersUsing;
      }

   }

   public boolean func_94041_b(int i, ItemStack itemstack) {
      return isBookItem(itemstack);
   }

   public void func_145843_s() {
      super.func_145843_s();
      this.func_145836_u();
   }

   public static boolean isBookItem(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (!(item instanceof ItemBook) && !(item instanceof ItemWritableBook) && !(item instanceof ItemEditableBook)) {
            if (!(item instanceof LOTRItemRedBook) && item != LOTRMod.mithrilBook) {
               if (item instanceof ItemEnchantedBook) {
                  return true;
               } else if (item instanceof ItemMapBase) {
                  return true;
               } else if (item == Items.field_151121_aF) {
                  return true;
               } else if (item instanceof LOTRItemModifierTemplate) {
                  return true;
               } else {
                  return false;
               }
            } else {
               return true;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }
}
