package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockHobbitOven;
import lotr.common.inventory.LOTRSlotStackSize;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityHobbitOven extends TileEntity implements IInventory, ISidedInventory {
   private ItemStack[] inventory = new ItemStack[19];
   public int ovenCookTime = 0;
   public int currentItemFuelValue = 0;
   public int currentCookTime = 0;
   private String specialOvenName;
   private int[] inputSlots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
   private int[] outputSlots = new int[]{9, 10, 11, 12, 13, 14, 15, 16, 17};
   private int fuelSlot = 18;

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
      return this.func_145818_k_() ? this.specialOvenName : StatCollector.func_74838_a("container.lotr.hobbitOven");
   }

   public boolean func_145818_k_() {
      return this.specialOvenName != null && this.specialOvenName.length() > 0;
   }

   public void setOvenName(String s) {
      this.specialOvenName = s;
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      NBTTagList items = nbt.func_150295_c("Items", 10);
      this.inventory = new ItemStack[this.func_70302_i_()];

      for(int i = 0; i < items.func_74745_c(); ++i) {
         NBTTagCompound itemData = items.func_150305_b(i);
         byte byte0 = itemData.func_74771_c("Slot");
         if (byte0 >= 0 && byte0 < this.inventory.length) {
            this.inventory[byte0] = ItemStack.func_77949_a(itemData);
         }
      }

      this.ovenCookTime = nbt.func_74765_d("BurnTime");
      this.currentCookTime = nbt.func_74765_d("CookTime");
      this.currentItemFuelValue = TileEntityFurnace.func_145952_a(this.inventory[18]);
      if (nbt.func_74764_b("CustomName")) {
         this.specialOvenName = nbt.func_74779_i("CustomName");
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
      nbt.func_74777_a("BurnTime", (short)this.ovenCookTime);
      nbt.func_74777_a("CookTime", (short)this.currentCookTime);
      if (this.func_145818_k_()) {
         nbt.func_74778_a("CustomName", this.specialOvenName);
      }

   }

   public int func_70297_j_() {
      return 64;
   }

   @SideOnly(Side.CLIENT)
   public int getCookProgressScaled(int i) {
      return this.currentCookTime * i / 400;
   }

   @SideOnly(Side.CLIENT)
   public int getCookTimeRemainingScaled(int i) {
      if (this.currentItemFuelValue == 0) {
         this.currentItemFuelValue = 400;
      }

      return this.ovenCookTime * i / this.currentItemFuelValue;
   }

   public boolean isCooking() {
      return this.ovenCookTime > 0;
   }

   public void func_145845_h() {
      boolean cooking = this.ovenCookTime > 0;
      boolean needUpdate = false;
      if (this.ovenCookTime > 0) {
         --this.ovenCookTime;
      }

      if (!this.field_145850_b.field_72995_K) {
         if (this.ovenCookTime == 0 && this.canCookAnyItem()) {
            this.currentItemFuelValue = this.ovenCookTime = TileEntityFurnace.func_145952_a(this.inventory[18]);
            if (this.ovenCookTime > 0) {
               needUpdate = true;
               if (this.inventory[18] != null) {
                  --this.inventory[18].field_77994_a;
                  if (this.inventory[18].field_77994_a == 0) {
                     this.inventory[18] = this.inventory[18].func_77973_b().getContainerItem(this.inventory[18]);
                  }
               }
            }
         }

         if (this.isCooking() && this.canCookAnyItem()) {
            ++this.currentCookTime;
            if (this.currentCookTime == 400) {
               this.currentCookTime = 0;

               for(int i = 0; i < 9; ++i) {
                  this.cookItem(i);
               }

               needUpdate = true;
            }
         } else {
            this.currentCookTime = 0;
         }

         if (cooking != this.ovenCookTime > 0) {
            needUpdate = true;
            LOTRBlockHobbitOven.setOvenActive(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
         }
      }

      if (needUpdate) {
         this.func_70296_d();
      }

   }

   private boolean canCookAnyItem() {
      for(int i = 0; i < 9; ++i) {
         if (this.canCook(i)) {
            return true;
         }
      }

      return false;
   }

   private boolean canCook(int i) {
      if (this.inventory[i] == null) {
         return false;
      } else {
         ItemStack result = FurnaceRecipes.func_77602_a().func_151395_a(this.inventory[i]);
         if (!isCookResultAcceptable(result)) {
            return false;
         } else if (this.inventory[i + 9] == null) {
            return true;
         } else if (!this.inventory[i + 9].func_77969_a(result)) {
            return false;
         } else {
            int resultSize = this.inventory[i + 9].field_77994_a + result.field_77994_a;
            return resultSize <= this.func_70297_j_() && resultSize <= result.func_77976_d();
         }
      }
   }

   private void cookItem(int i) {
      if (this.canCook(i)) {
         ItemStack itemstack = FurnaceRecipes.func_77602_a().func_151395_a(this.inventory[i]);
         if (this.inventory[i + 9] == null) {
            this.inventory[i + 9] = itemstack.func_77946_l();
         } else if (this.inventory[i + 9].func_77969_a(itemstack)) {
            ItemStack var10000 = this.inventory[i + 9];
            var10000.field_77994_a += itemstack.field_77994_a;
         }

         --this.inventory[i].field_77994_a;
         if (this.inventory[i].field_77994_a <= 0) {
            this.inventory[i] = null;
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

   public static boolean isCookResultAcceptable(ItemStack result) {
      if (result == null) {
         return false;
      } else {
         Item item = result.func_77973_b();
         return item instanceof ItemFood || item == LOTRMod.pipeweed || item == Item.func_150898_a(LOTRMod.driedReeds);
      }
   }

   public boolean func_94041_b(int slot, ItemStack itemstack) {
      if (slot < 9) {
         return itemstack == null ? false : isCookResultAcceptable(FurnaceRecipes.func_77602_a().func_151395_a(itemstack));
      } else {
         return slot < 18 ? false : TileEntityFurnace.func_145954_b(itemstack);
      }
   }

   public int[] func_94128_d(int side) {
      ArrayList list;
      int i;
      int size;
      int[] temp;
      if (side == 0) {
         list = new ArrayList();
         temp = this.outputSlots;
         i = temp.length;

         for(size = 0; size < i; ++size) {
            int i = temp[size];
            list.add(i);
         }

         list.add(this.fuelSlot);
         temp = new int[list.size()];

         for(i = 0; i < temp.length; ++i) {
            temp[i] = (Integer)list.get(i);
         }

         return temp;
      } else if (side != 1) {
         return new int[]{this.fuelSlot};
      } else {
         list = new ArrayList();

         for(int i = 0; i < this.inputSlots.length; ++i) {
            i = this.inputSlots[i];
            size = this.func_70301_a(i) == null ? 0 : this.func_70301_a(i).field_77994_a;
            list.add(new LOTRSlotStackSize(i, size));
         }

         Collections.sort(list);
         temp = new int[this.inputSlots.length];

         for(i = 0; i < temp.length; ++i) {
            LOTRSlotStackSize obj = (LOTRSlotStackSize)list.get(i);
            temp[i] = obj.slot;
         }

         return temp;
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
         this.specialOvenName = packet.func_148857_g().func_74779_i("CustomName");
      }

   }
}
