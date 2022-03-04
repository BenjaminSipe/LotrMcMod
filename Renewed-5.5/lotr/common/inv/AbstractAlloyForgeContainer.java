package lotr.common.inv;

import lotr.common.tileentity.AbstractAlloyForgeTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public abstract class AbstractAlloyForgeContainer extends Container {
   private final AbstractAlloyForgeTileEntity theForge;
   private final IIntArray forgeData;
   protected final World world;
   private final int forgeSlots;

   public AbstractAlloyForgeContainer(ContainerType type, int id, PlayerInventory playerInv, AbstractAlloyForgeTileEntity forge) {
      this(type, id, playerInv, forge, new IntArray(4));
   }

   public AbstractAlloyForgeContainer(ContainerType type, int id, PlayerInventory playerInv, AbstractAlloyForgeTileEntity forge, IIntArray data) {
      super(type, id);
      func_216962_a(forge, 13);
      func_216959_a(data, 4);
      this.theForge = forge;
      this.forgeData = data;
      this.world = playerInv.field_70458_d.field_70170_p;
      this.forgeSlots = 4;

      int x;
      for(x = 0; x < this.forgeSlots; ++x) {
         this.func_75146_a(new Slot(this.theForge, x, 53 + x * 18, 39));
      }

      for(x = 0; x < this.forgeSlots; ++x) {
         this.func_75146_a(new Slot(this.theForge, x + this.forgeSlots, 53 + x * 18, 21));
      }

      for(x = 0; x < this.forgeSlots; ++x) {
         this.func_75146_a(new ForgeResultSlot(playerInv.field_70458_d, this.theForge, x + this.forgeSlots * 2, 53 + x * 18, 85));
      }

      this.func_75146_a(new ForgeFuelSlot(this, this.theForge, this.forgeSlots * 3, 80, 129));

      for(x = 0; x < 3; ++x) {
         for(int x = 0; x < 9; ++x) {
            this.func_75146_a(new Slot(playerInv, x + x * 9 + 9, 8 + x * 18, 151 + x * 18));
         }
      }

      for(x = 0; x < 9; ++x) {
         this.func_75146_a(new Slot(playerInv, x, 8 + x * 18, 209));
      }

      this.func_216961_a(data);
   }

   public boolean func_75145_c(PlayerEntity player) {
      return this.theForge.func_70300_a(player);
   }

   public ItemStack func_82846_b(PlayerEntity player, int index) {
      ItemStack itemstack = ItemStack.field_190927_a;
      Slot slot = (Slot)this.field_75151_b.get(index);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         int forgeSize = this.theForge.func_70302_i_();
         if (index >= this.forgeSlots * 2 && index < this.forgeSlots * 3) {
            if (!this.func_75135_a(itemstack1, forgeSize, forgeSize + 36, true)) {
               return ItemStack.field_190927_a;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (index >= this.forgeSlots * 2 && index != this.forgeSlots * 3) {
            boolean couldForgeAcceptItem = false;
            if (this.testSmeltable(itemstack1)) {
               couldForgeAcceptItem = true;
               if (this.func_75135_a(itemstack1, 0, this.forgeSlots, false)) {
               }
            }

            if (this.isFuel(itemstack1)) {
               couldForgeAcceptItem = true;
               if (this.func_75135_a(itemstack1, this.forgeSlots * 3, this.forgeSlots * 3 + 1, false)) {
               }
            }

            for(int i = 0; i < this.forgeSlots; ++i) {
               int alloySlot = i + this.forgeSlots;
               ItemStack ingredientInForge = this.theForge.func_70301_a(i);
               ItemStack alloyInForge = this.theForge.func_70301_a(alloySlot);
               if (this.testAlloySmeltable(ingredientInForge, itemstack1)) {
                  couldForgeAcceptItem = true;
                  if (this.func_75135_a(itemstack1, alloySlot, alloySlot + 1, false)) {
                  }
               } else if (this.testAlloySmeltable(itemstack1, alloyInForge)) {
                  couldForgeAcceptItem = true;
                  if (this.func_75135_a(itemstack1, i, i + 1, false)) {
                  }
               }
            }

            if (!couldForgeAcceptItem) {
               if (index >= forgeSize && index < forgeSize + 27) {
                  if (!this.func_75135_a(itemstack1, forgeSize + 27, forgeSize + 36, false)) {
                     return ItemStack.field_190927_a;
                  }
               } else if (index >= forgeSize + 27 && index < forgeSize + 36 && !this.func_75135_a(itemstack1, forgeSize, forgeSize + 27, false)) {
                  return ItemStack.field_190927_a;
               }
            }
         } else if (!this.func_75135_a(itemstack1, forgeSize, forgeSize + 36, false)) {
            return ItemStack.field_190927_a;
         }

         if (itemstack1.func_190926_b()) {
            slot.func_75215_d(ItemStack.field_190927_a);
         } else {
            slot.func_75218_e();
         }

         if (itemstack1.func_190916_E() == itemstack.func_190916_E()) {
            return ItemStack.field_190927_a;
         }

         slot.func_190901_a(player, itemstack1);
      }

      return itemstack;
   }

   private boolean testSmeltable(ItemStack stack) {
      return this.testAlloySmeltable(stack, ItemStack.field_190927_a);
   }

   private boolean testAlloySmeltable(ItemStack stack, ItemStack alloy) {
      return !this.theForge.getSmeltingResult(stack, alloy).func_190926_b();
   }

   public boolean isFuel(ItemStack stack) {
      return AbstractFurnaceTileEntity.func_213991_b(stack);
   }

   public int getCookProgressionScaled() {
      int i = this.forgeData.func_221476_a(2);
      int j = this.forgeData.func_221476_a(3);
      return j != 0 && i != 0 ? i * 24 / j : 0;
   }

   public int getBurnLeftScaled() {
      int i = this.forgeData.func_221476_a(1);
      if (i == 0) {
         i = 200;
      }

      return this.forgeData.func_221476_a(0) * 13 / i;
   }

   public boolean isBurning() {
      return this.forgeData.func_221476_a(0) > 0;
   }
}
