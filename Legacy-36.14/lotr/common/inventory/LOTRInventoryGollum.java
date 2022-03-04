package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class LOTRInventoryGollum implements IInventory {
   private ItemStack[] inventory = new ItemStack[9];
   private LOTREntityGollum theGollum;

   public LOTRInventoryGollum(LOTREntityGollum gollum) {
      this.theGollum = gollum;
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
            this.func_70296_d();
            return itemstack;
         } else {
            itemstack = this.inventory[i].func_77979_a(j);
            if (this.inventory[i].field_77994_a == 0) {
               this.inventory[i] = null;
            }

            this.func_70296_d();
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

      this.func_70296_d();
   }

   public int func_70302_i_() {
      return this.inventory.length;
   }

   public String func_145825_b() {
      return "Gollum";
   }

   public boolean func_145818_k_() {
      return false;
   }

   public int func_70297_j_() {
      return 64;
   }

   public boolean func_70300_a(EntityPlayer entityplayer) {
      return true;
   }

   public void func_70296_d() {
   }

   public void func_70295_k_() {
   }

   public void func_70305_f() {
   }

   public boolean func_94041_b(int i, ItemStack itemstack) {
      return true;
   }

   public void readFromNBT(NBTTagCompound nbt) {
      NBTTagList items = nbt.func_150295_c("Items", 10);
      this.inventory = new ItemStack[this.func_70302_i_()];

      for(int i = 0; i < items.func_74745_c(); ++i) {
         NBTTagCompound itemData = items.func_150305_b(i);
         byte byte0 = itemData.func_74771_c("Slot");
         if (byte0 >= 0 && byte0 < this.inventory.length) {
            this.inventory[byte0] = ItemStack.func_77949_a(itemData);
         }
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
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
   }

   public void dropAllItems() {
      for(int i = 0; i < this.inventory.length; ++i) {
         if (this.inventory[i] != null) {
            this.theGollum.func_70099_a(this.inventory[i], 0.0F);
            this.inventory[i] = null;
         }
      }

   }
}
