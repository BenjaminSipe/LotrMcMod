package lotr.common.tileentity;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockKebabStand;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class LOTRTileEntityKebabStand extends TileEntity implements IInventory {
   public static final int MEAT_SLOTS = 8;
   private ItemStack[] inventory = new ItemStack[8];
   private boolean[] cooked = new boolean[8];
   private int cookTime;
   private static final int cookTimeMax = 200;
   private int fuelTime;
   private boolean cookedClient;
   private boolean cookingClient;
   private int meatAmountClient;
   private float kebabSpin;
   private float prevKebabSpin;

   public String getStandTextureName() {
      Block block = this.func_145838_q();
      return block instanceof LOTRBlockKebabStand ? ((LOTRBlockKebabStand)block).getStandTextureName() : "";
   }

   public float getKebabSpin(float f) {
      return this.prevKebabSpin + (this.kebabSpin - this.prevKebabSpin) * f;
   }

   public boolean isCooked() {
      if (this.field_145850_b != null && this.field_145850_b.field_72995_K) {
         return this.cookedClient;
      } else {
         for(int i = 0; i < this.func_70302_i_(); ++i) {
            if (this.cooked[i]) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isFullyCooked() {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (itemstack != null && !this.cooked[i]) {
            return false;
         }
      }

      return true;
   }

   public boolean isCooking() {
      if (this.field_145850_b != null && this.field_145850_b.field_72995_K) {
         return this.cookingClient;
      } else {
         return this.fuelTime > 0;
      }
   }

   public int getMeatAmount() {
      if (this.field_145850_b != null && this.field_145850_b.field_72995_K) {
         return this.meatAmountClient;
      } else {
         int meats = 0;

         for(int i = 0; i < this.func_70302_i_(); ++i) {
            ItemStack itemstack = this.func_70301_a(i);
            if (itemstack != null) {
               ++meats;
            }
         }

         return meats;
      }
   }

   public boolean isMeat(ItemStack meat) {
      if (meat == null) {
         return false;
      } else {
         Item item = meat.func_77973_b();
         if (item instanceof ItemFood) {
            ItemFood itemfood = (ItemFood)item;
            if (itemfood.func_77845_h()) {
               ItemStack cookedFood = FurnaceRecipes.func_77602_a().func_151395_a(meat);
               return cookedFood != null;
            }
         }

         return false;
      }
   }

   public boolean hasEmptyMeatSlot() {
      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (itemstack == null) {
            return true;
         }
      }

      return false;
   }

   public boolean addMeat(ItemStack meat) {
      ItemStack copyMeat = meat.func_77946_l();
      copyMeat.field_77994_a = 1;
      boolean added = false;

      for(int i = 0; i < this.func_70302_i_(); ++i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (itemstack == null) {
            this.func_70299_a(i, copyMeat);
            this.cooked[i] = false;
            added = true;
            break;
         }
      }

      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
      return added;
   }

   public ItemStack removeFirstMeat() {
      ItemStack meat = null;

      int i;
      ItemStack itemstack;
      for(i = this.func_70302_i_() - 1; i >= 0; --i) {
         itemstack = this.func_70301_a(i);
         if (itemstack != null && this.cooked[i]) {
            meat = itemstack;
            this.func_70299_a(i, (ItemStack)null);
            this.cooked[i] = false;
            break;
         }
      }

      if (meat == null) {
         for(i = this.func_70302_i_() - 1; i >= 0; --i) {
            itemstack = this.func_70301_a(i);
            if (itemstack != null && !this.cooked[i]) {
               meat = itemstack;
               this.func_70299_a(i, (ItemStack)null);
               break;
            }
         }
      }

      if (this.isCooking() && this.getMeatAmount() == 0) {
         this.stopCooking();
      }

      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
      return meat;
   }

   private boolean canCook() {
      return !this.isFullyCooked() && this.getMeatAmount() > 0;
   }

   private void startCooking(int i) {
      this.cookTime = 0;
      this.fuelTime = i;
   }

   private void addFuel(int i) {
      this.fuelTime += i;
   }

   private void stopCooking() {
      this.cookTime = 0;
      this.fuelTime = 0;
   }

   public void func_145845_h() {
      super.func_145845_h();
      if (!this.field_145850_b.field_72995_K) {
         boolean prevCooking = this.isCooking();
         boolean prevCooked = this.isCooked();
         int fuel;
         if (this.isCooking()) {
            if (!this.canCook()) {
               this.stopCooking();
            } else {
               ++this.cookTime;
               if (this.cookTime > this.fuelTime) {
                  fuel = this.takeFuelFromBelow();
                  if (fuel > 0) {
                     this.addFuel(fuel);
                  } else {
                     this.stopCooking();
                  }
               } else if (this.cookTime >= 200) {
                  this.cookFirstMeat();
               }
            }
         } else if (this.canCook()) {
            fuel = this.takeFuelFromBelow();
            if (fuel > 0) {
               this.startCooking(fuel);
            }
         }

         if (this.isCooking() != prevCooking || this.isCooked() != prevCooked) {
            this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
            this.func_70296_d();
         }
      } else if (this.isCooking()) {
         this.prevKebabSpin = this.kebabSpin;
         this.kebabSpin += 4.0F;
         if (this.field_145850_b.field_73012_v.nextInt(4) == 0) {
            double d = (double)((float)this.field_145851_c + this.field_145850_b.field_73012_v.nextFloat());
            double d1 = (double)((float)this.field_145848_d + this.field_145850_b.field_73012_v.nextFloat() * 0.2F);
            double d2 = (double)((float)this.field_145849_e + this.field_145850_b.field_73012_v.nextFloat());
            this.field_145850_b.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
            this.field_145850_b.func_72869_a("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      } else if (this.kebabSpin > 0.0F) {
         this.prevKebabSpin = this.kebabSpin;
         this.kebabSpin += 20.0F;
         if ((float)Math.ceil((double)(this.kebabSpin / 360.0F)) > (float)Math.ceil((double)(this.prevKebabSpin / 360.0F))) {
            float ds = this.kebabSpin - this.prevKebabSpin;
            this.kebabSpin = 0.0F;
            this.prevKebabSpin = this.kebabSpin - ds;
         }
      } else {
         this.prevKebabSpin = this.kebabSpin = 0.0F;
      }

   }

   private void cookFirstMeat() {
      this.cookTime = 0;
      this.fuelTime -= 200;

      for(int i = this.func_70302_i_() - 1; i >= 0; --i) {
         ItemStack itemstack = this.func_70301_a(i);
         if (itemstack != null && !this.cooked[i]) {
            this.func_70299_a(i, new ItemStack(LOTRMod.kebab));
            this.cooked[i] = true;
            break;
         }
      }

   }

   private int takeFuelFromBelow() {
      TileEntity belowTE = this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
      if (belowTE instanceof IInventory) {
         IInventory inv = (IInventory)belowTE;

         for(int i = 0; i < inv.func_70302_i_(); ++i) {
            ItemStack itemstack = inv.func_70301_a(i);
            if (itemstack != null && TileEntityFurnace.func_145954_b(itemstack)) {
               int fuel = TileEntityFurnace.func_145952_a(itemstack);
               --itemstack.field_77994_a;
               if (itemstack.field_77994_a <= 0) {
                  inv.func_70299_a(i, (ItemStack)null);
               } else {
                  inv.func_70299_a(i, itemstack);
               }

               return fuel;
            }
         }
      }

      return 0;
   }

   public void generateCookedKebab(int kebab) {
      for(int i = 0; i < kebab && i < this.func_70302_i_(); ++i) {
         this.func_70299_a(i, new ItemStack(LOTRMod.kebab));
         this.cooked[i] = true;
      }

   }

   public boolean shouldSaveBlockData() {
      return this.getMeatAmount() > 0;
   }

   public void onReplaced() {
      this.stopCooking();
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
      return "KebabStand";
   }

   public boolean func_145818_k_() {
      return false;
   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      this.writeKebabStandToNBT(nbt);
   }

   public void writeKebabStandToNBT(NBTTagCompound nbt) {
      NBTTagList items = new NBTTagList();

      for(int i = 0; i < this.inventory.length; ++i) {
         NBTTagCompound itemData = new NBTTagCompound();
         itemData.func_74774_a("Slot", (byte)i);
         ItemStack slotItem = this.inventory[i];
         boolean slotCooked = this.cooked[i];
         itemData.func_74757_a("SlotItem", slotItem != null);
         if (slotItem != null) {
            slotItem.func_77955_b(itemData);
         }

         itemData.func_74757_a("SlotCooked", slotCooked);
         items.func_74742_a(itemData);
      }

      nbt.func_74782_a("Items", items);
      nbt.func_74777_a("CookTime", (short)this.cookTime);
      nbt.func_74777_a("FuelTime", (short)this.fuelTime);
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.readKebabStandFromNBT(nbt);
   }

   public void readKebabStandFromNBT(NBTTagCompound nbt) {
      NBTTagList items = nbt.func_150295_c("Items", 10);
      this.inventory = new ItemStack[this.func_70302_i_()];
      this.cooked = new boolean[this.inventory.length];

      for(int i = 0; i < items.func_74745_c(); ++i) {
         NBTTagCompound itemData = items.func_150305_b(i);
         int slot = itemData.func_74771_c("Slot");
         if (slot >= 0 && slot < this.inventory.length) {
            boolean slotItem = itemData.func_74767_n("SlotItem");
            if (slotItem) {
               this.inventory[slot] = ItemStack.func_77949_a(itemData);
            }

            boolean slotCooked = itemData.func_74767_n("SlotCooked");
            this.cooked[i] = slotCooked;
         }
      }

      this.cookTime = nbt.func_74765_d("CookTime");
      this.fuelTime = nbt.func_74765_d("FuelTime");
   }

   public int func_70297_j_() {
      return 64;
   }

   public boolean func_70300_a(EntityPlayer entityplayer) {
      return this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && entityplayer.func_70092_e((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D) <= 64.0D;
   }

   public void func_70295_k_() {
   }

   public void func_70305_f() {
   }

   public boolean func_94041_b(int slot, ItemStack itemstack) {
      return false;
   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      data.func_74757_a("Cooked", this.isCooked());
      data.func_74757_a("Cooking", this.isCooking());
      data.func_74774_a("Meats", (byte)this.getMeatAmount());
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.cookedClient = data.func_74767_n("Cooked");
      this.cookingClient = data.func_74767_n("Cooking");
      this.meatAmountClient = data.func_74771_c("Meats");
   }
}
