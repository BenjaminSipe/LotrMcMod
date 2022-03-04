package lotr.common.tileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lotr.common.inventory.LOTRSlotStackSize;
import lotr.common.item.LOTRItemMug;
import lotr.common.item.LOTRPoisonedDrinks;
import lotr.common.recipe.LOTRBrewingRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.ArrayUtils;

public class LOTRTileEntityBarrel extends TileEntity implements ISidedInventory {
   public static final int EMPTY = 0;
   public static final int BREWING = 1;
   public static final int FULL = 2;
   public static final int brewTime = 12000;
   public static final int brewAnimTime = 32;
   private ItemStack[] inventory = new ItemStack[10];
   private static final int[] INGREDIENT_SLOTS = new int[]{0, 1, 2, 3, 4, 5};
   private static final int[] BUCKET_SLOTS = new int[]{6, 7, 8};
   public static final int BARREL_SLOT = 9;
   public int barrelMode;
   public int brewingTime;
   public int brewingAnim;
   public int brewingAnimPrev;
   private String specialBarrelName;
   public List players = new ArrayList();

   public ItemStack getBrewedDrink() {
      if (this.barrelMode == 2 && this.inventory[9] != null) {
         ItemStack itemstack = this.inventory[9].func_77946_l();
         return itemstack;
      } else {
         return null;
      }
   }

   public void consumeMugRefill() {
      if (this.barrelMode == 2 && this.inventory[9] != null) {
         --this.inventory[9].field_77994_a;
         if (this.inventory[9].field_77994_a <= 0) {
            this.inventory[9] = null;
            this.barrelMode = 0;
         }
      }

   }

   private void updateBrewingRecipe() {
      if (this.barrelMode == 0) {
         this.inventory[9] = LOTRBrewingRecipes.findMatchingRecipe(this);
      }

   }

   public void handleBrewingButtonPress() {
      if (this.barrelMode == 0 && this.inventory[9] != null) {
         this.barrelMode = 1;

         int i;
         for(i = 0; i < 9; ++i) {
            if (this.inventory[i] != null) {
               ItemStack containerItem = null;
               if (this.inventory[i].func_77973_b().hasContainerItem(this.inventory[i])) {
                  containerItem = this.inventory[i].func_77973_b().getContainerItem(this.inventory[i]);
                  if (containerItem.func_77984_f() && containerItem.func_77960_j() > containerItem.func_77958_k()) {
                     containerItem = null;
                  }
               }

               --this.inventory[i].field_77994_a;
               if (this.inventory[i].field_77994_a <= 0) {
                  this.inventory[i] = null;
                  if (containerItem != null) {
                     this.inventory[i] = containerItem;
                  }
               }
            }
         }

         if (!this.field_145850_b.field_72995_K) {
            for(i = 0; i < this.players.size(); ++i) {
               EntityPlayerMP entityplayer = (EntityPlayerMP)this.players.get(i);
               entityplayer.field_71070_bA.func_75142_b();
               entityplayer.func_71120_a(entityplayer.field_71070_bA);
            }
         }
      } else if (this.barrelMode == 1 && this.inventory[9] != null && this.inventory[9].func_77960_j() > 0) {
         this.barrelMode = 2;
         this.brewingTime = 0;
         ItemStack itemstack = this.inventory[9].func_77946_l();
         itemstack.func_77964_b(itemstack.func_77960_j() - 1);
         this.inventory[9] = itemstack;
      }

   }

   public boolean canPoisonBarrel() {
      if (this.barrelMode != 0 && this.inventory[9] != null) {
         ItemStack itemstack = this.inventory[9];
         return LOTRPoisonedDrinks.canPoison(itemstack) && !LOTRPoisonedDrinks.isDrinkPoisoned(itemstack);
      } else {
         return false;
      }
   }

   public void poisonBarrel(EntityPlayer entityplayer) {
      ItemStack itemstack = this.inventory[9];
      LOTRPoisonedDrinks.setDrinkPoisoned(itemstack, true);
      LOTRPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
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
            if (i != 9) {
               this.updateBrewingRecipe();
            }

            return itemstack;
         } else {
            itemstack = this.inventory[i].func_77979_a(j);
            if (this.inventory[i].field_77994_a == 0) {
               this.inventory[i] = null;
            }

            if (i != 9) {
               this.updateBrewingRecipe();
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

      if (i != 9) {
         this.updateBrewingRecipe();
      }

   }

   public String func_145825_b() {
      return this.func_145818_k_() ? this.specialBarrelName : StatCollector.func_74838_a("container.lotr.barrel");
   }

   public String getInvSubtitle() {
      ItemStack brewingItem = this.func_70301_a(9);
      if (this.barrelMode == 0) {
         return StatCollector.func_74838_a("container.lotr.barrel.empty");
      } else if (this.barrelMode == 1 && brewingItem != null) {
         return StatCollector.func_74837_a("container.lotr.barrel.brewing", new Object[]{brewingItem.func_82833_r(), LOTRItemMug.getStrengthSubtitle(brewingItem)});
      } else {
         return this.barrelMode == 2 && brewingItem != null ? StatCollector.func_74837_a("container.lotr.barrel.full", new Object[]{brewingItem.func_82833_r(), LOTRItemMug.getStrengthSubtitle(brewingItem), brewingItem.field_77994_a}) : "";
      }
   }

   public boolean func_145818_k_() {
      return this.specialBarrelName != null && this.specialBarrelName.length() > 0;
   }

   public void setBarrelName(String s) {
      this.specialBarrelName = s;
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.readBarrelFromNBT(nbt);
   }

   public void readBarrelFromNBT(NBTTagCompound nbt) {
      NBTTagList items = nbt.func_150295_c("Items", 10);
      this.inventory = new ItemStack[this.func_70302_i_()];

      for(int i = 0; i < items.func_74745_c(); ++i) {
         NBTTagCompound itemData = items.func_150305_b(i);
         int slot = itemData.func_74771_c("Slot");
         if (slot >= 0 && slot < this.inventory.length) {
            this.inventory[slot] = ItemStack.func_77949_a(itemData);
         }
      }

      this.barrelMode = nbt.func_74771_c("BarrelMode");
      this.brewingTime = nbt.func_74762_e("BrewingTime");
      if (nbt.func_74764_b("CustomName")) {
         this.specialBarrelName = nbt.func_74779_i("CustomName");
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      this.writeBarrelToNBT(nbt);
   }

   public void writeBarrelToNBT(NBTTagCompound nbt) {
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
      nbt.func_74774_a("BarrelMode", (byte)this.barrelMode);
      nbt.func_74768_a("BrewingTime", this.brewingTime);
      if (this.func_145818_k_()) {
         nbt.func_74778_a("CustomName", this.specialBarrelName);
      }

   }

   public int func_70297_j_() {
      return 64;
   }

   public int getBrewProgressScaled(int i) {
      return this.brewingTime * i / 12000;
   }

   public int getBrewAnimationProgressScaled(int i) {
      return this.brewingAnim * i / 32;
   }

   public float getBrewAnimationProgressScaledF(int i, float f) {
      float f1 = (float)this.brewingAnimPrev * (float)i / 32.0F;
      float f2 = (float)this.brewingAnim * (float)i / 32.0F;
      return f1 + (f2 - f1) * f;
   }

   public int getBarrelFullAmountScaled(int i) {
      return this.inventory[9] == null ? 0 : this.inventory[9].field_77994_a * i / LOTRBrewingRecipes.BARREL_CAPACITY;
   }

   public void func_145845_h() {
      boolean needUpdate = false;
      if (!this.field_145850_b.field_72995_K) {
         if (this.barrelMode == 1) {
            if (this.inventory[9] != null) {
               ++this.brewingTime;
               if (this.brewingTime >= 12000) {
                  this.brewingTime = 0;
                  if (this.inventory[9].func_77960_j() < 4) {
                     this.inventory[9].func_77964_b(this.inventory[9].func_77960_j() + 1);
                     needUpdate = true;
                  } else {
                     this.barrelMode = 2;
                  }
               }
            } else {
               this.barrelMode = 0;
            }
         } else {
            this.brewingTime = 0;
         }

         if (this.barrelMode == 2 && this.inventory[9] == null) {
            this.barrelMode = 0;
         }
      } else {
         this.brewingAnimPrev = this.brewingAnim;
         if (this.barrelMode == 1) {
            ++this.brewingAnim;
            if (this.brewingAnim >= 32) {
               this.brewingAnim = 0;
               this.brewingAnimPrev = this.brewingAnim;
            }
         } else {
            this.brewingAnim = 0;
            this.brewingAnimPrev = this.brewingAnim;
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
      if (ArrayUtils.contains(INGREDIENT_SLOTS, slot)) {
         return true;
      } else {
         return ArrayUtils.contains(BUCKET_SLOTS, slot) ? LOTRBrewingRecipes.isWaterSource(itemstack) : false;
      }
   }

   public int[] func_94128_d(int side) {
      if (side == 0) {
         return BUCKET_SLOTS;
      } else if (side != 1) {
         return BUCKET_SLOTS;
      } else {
         List slotsWithStackSize = new ArrayList();
         int[] sortedSlots = INGREDIENT_SLOTS;
         int i = sortedSlots.length;

         for(int var5 = 0; var5 < i; ++var5) {
            int slot = sortedSlots[var5];
            int size = this.func_70301_a(slot) == null ? 0 : this.func_70301_a(slot).field_77994_a;
            slotsWithStackSize.add(new LOTRSlotStackSize(slot, size));
         }

         Collections.sort(slotsWithStackSize);
         sortedSlots = new int[INGREDIENT_SLOTS.length];

         for(i = 0; i < sortedSlots.length; ++i) {
            LOTRSlotStackSize slotAndStack = (LOTRSlotStackSize)slotsWithStackSize.get(i);
            sortedSlots[i] = slotAndStack.slot;
         }

         return sortedSlots;
      }
   }

   public boolean func_102007_a(int slot, ItemStack insertItem, int side) {
      return this.func_94041_b(slot, insertItem);
   }

   public boolean func_102008_b(int slot, ItemStack extractItem, int side) {
      if (ArrayUtils.contains(BUCKET_SLOTS, slot)) {
         return !this.func_94041_b(slot, extractItem);
      } else {
         return false;
      }
   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      this.writeBarrelToNBT(data);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.readBarrelFromNBT(data);
   }
}
