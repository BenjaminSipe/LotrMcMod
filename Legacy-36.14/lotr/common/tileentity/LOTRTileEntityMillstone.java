package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockMillstone;
import lotr.common.recipe.LOTRMillstoneRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityMillstone extends TileEntity implements IInventory, ISidedInventory {
   protected ItemStack[] inventory = new ItemStack[2];
   private static final int inputSlot = 0;
   private static final int outputSlot = 1;
   private String specialMillstoneName;
   public boolean isMilling;
   public int currentMillTime = 0;
   private static final int fullMillTime = 200;

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
      return this.func_145818_k_() ? this.specialMillstoneName : StatCollector.func_74838_a("container.lotr.millstone");
   }

   public boolean func_145818_k_() {
      return this.specialMillstoneName != null && this.specialMillstoneName.length() > 0;
   }

   public void setSpecialMillstoneName(String s) {
      this.specialMillstoneName = s;
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

      this.isMilling = nbt.func_74767_n("Milling");
      this.currentMillTime = nbt.func_74762_e("MillTime");
      if (nbt.func_74764_b("CustomName")) {
         this.specialMillstoneName = nbt.func_74779_i("CustomName");
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
      nbt.func_74757_a("Milling", this.isMilling);
      nbt.func_74768_a("MillTime", this.currentMillTime);
      if (this.func_145818_k_()) {
         nbt.func_74778_a("CustomName", this.specialMillstoneName);
      }

   }

   public int func_70297_j_() {
      return 64;
   }

   @SideOnly(Side.CLIENT)
   public int getMillProgressScaled(int i) {
      return this.currentMillTime * i / 200;
   }

   public boolean isMilling() {
      return this.isMilling;
   }

   protected void toggleMillstoneActive() {
      LOTRBlockMillstone.toggleMillstoneActive(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
   }

   public void func_145845_h() {
      boolean needUpdate = false;
      if (!this.field_145850_b.field_72995_K) {
         boolean powered = this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         if (powered && !this.isMilling) {
            this.isMilling = true;
            this.currentMillTime = 0;
            needUpdate = true;
            this.toggleMillstoneActive();
         } else if (!powered && this.isMilling) {
            this.isMilling = false;
            this.currentMillTime = 0;
            needUpdate = true;
            this.toggleMillstoneActive();
         }

         if (this.isMilling && this.canMill()) {
            ++this.currentMillTime;
            if (this.currentMillTime == 200) {
               this.currentMillTime = 0;
               this.millItem();
               needUpdate = true;
            }
         } else if (this.currentMillTime != 0) {
            this.currentMillTime = 0;
            needUpdate = true;
         }
      }

      if (needUpdate) {
         this.func_70296_d();
      }

   }

   private boolean canMill() {
      ItemStack itemstack = this.inventory[0];
      if (itemstack == null) {
         return false;
      } else {
         LOTRMillstoneRecipes.MillstoneResult result = LOTRMillstoneRecipes.getMillingResult(itemstack);
         if (result == null) {
            return false;
         } else {
            ItemStack resultItem = result.resultItem;
            ItemStack inResultSlot = this.inventory[1];
            if (inResultSlot == null) {
               return true;
            } else if (!inResultSlot.func_77969_a(resultItem)) {
               return false;
            } else {
               int resultSize = inResultSlot.field_77994_a + resultItem.field_77994_a;
               return resultSize <= this.func_70297_j_() && resultSize <= resultItem.func_77976_d();
            }
         }
      }
   }

   private void millItem() {
      if (this.canMill()) {
         ItemStack itemstack = this.inventory[0];
         LOTRMillstoneRecipes.MillstoneResult result = LOTRMillstoneRecipes.getMillingResult(itemstack);
         ItemStack resultItem = result.resultItem;
         float chance = result.chance;
         if (this.field_145850_b.field_73012_v.nextFloat() < chance) {
            ItemStack inResultSlot = this.inventory[1];
            if (inResultSlot == null) {
               inResultSlot = resultItem.func_77946_l();
            } else if (inResultSlot.func_77969_a(resultItem)) {
               inResultSlot.field_77994_a += resultItem.field_77994_a;
            }

            this.inventory[1] = inResultSlot;
         }

         --itemstack.field_77994_a;
         if (itemstack.field_77994_a <= 0) {
            this.inventory[0] = null;
         }
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
      if (slot != 0) {
         return false;
      } else {
         return itemstack != null && LOTRMillstoneRecipes.getMillingResult(itemstack) != null;
      }
   }

   public int[] func_94128_d(int side) {
      if (side == 0) {
         return new int[]{1};
      } else {
         return side == 1 ? new int[]{0} : new int[]{0};
      }
   }

   public boolean func_102007_a(int slot, ItemStack itemstack, int side) {
      return this.func_94041_b(slot, itemstack);
   }

   public boolean func_102008_b(int slot, ItemStack itemstack, int side) {
      return side == 0 ? true : true;
   }

   public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
      if (packet.func_148857_g() != null && packet.func_148857_g().func_74764_b("CustomName")) {
         this.specialMillstoneName = packet.func_148857_g().func_74779_i("CustomName");
      }

   }
}
