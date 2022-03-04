package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class LOTRContainerUnsmeltery extends Container {
   private LOTRTileEntityUnsmeltery theUnsmeltery;
   private int currentSmeltTime = 0;
   private int forgeSmeltTime = 0;
   private int currentItemFuelValue = 0;

   public LOTRContainerUnsmeltery(InventoryPlayer inv, LOTRTileEntityUnsmeltery unsmeltery) {
      this.theUnsmeltery = unsmeltery;
      this.func_75146_a(new Slot(unsmeltery, 0, 56, 17));
      this.func_75146_a(new Slot(unsmeltery, 1, 56, 53));
      this.func_75146_a(new LOTRSlotUnsmeltResult(unsmeltery, 2, 116, 35));

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 142));
      }

   }

   public void func_75132_a(ICrafting crafting) {
      super.func_75132_a(crafting);
      crafting.func_71112_a(this, 0, this.theUnsmeltery.currentSmeltTime);
      crafting.func_71112_a(this, 1, this.theUnsmeltery.forgeSmeltTime);
      crafting.func_71112_a(this, 2, this.theUnsmeltery.currentItemFuelValue);
   }

   public void func_75142_b() {
      super.func_75142_b();

      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         ICrafting crafting = (ICrafting)this.field_75149_d.get(i);
         if (this.currentSmeltTime != this.theUnsmeltery.currentSmeltTime) {
            crafting.func_71112_a(this, 0, this.theUnsmeltery.currentSmeltTime);
         }

         if (this.forgeSmeltTime != this.theUnsmeltery.forgeSmeltTime) {
            crafting.func_71112_a(this, 1, this.theUnsmeltery.forgeSmeltTime);
         }

         if (this.currentItemFuelValue != this.theUnsmeltery.currentItemFuelValue) {
            crafting.func_71112_a(this, 2, this.theUnsmeltery.currentItemFuelValue);
         }
      }

      this.currentSmeltTime = this.theUnsmeltery.currentSmeltTime;
      this.forgeSmeltTime = this.theUnsmeltery.forgeSmeltTime;
      this.currentItemFuelValue = this.theUnsmeltery.currentItemFuelValue;
   }

   @SideOnly(Side.CLIENT)
   public void func_75137_b(int i, int j) {
      if (i == 0) {
         this.theUnsmeltery.currentSmeltTime = j;
      }

      if (i == 1) {
         this.theUnsmeltery.forgeSmeltTime = j;
      }

      if (i == 2) {
         this.theUnsmeltery.currentItemFuelValue = j;
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theUnsmeltery.func_70300_a(entityplayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i == 2) {
            if (!this.func_75135_a(itemstack1, 3, 39, true)) {
               return null;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (i != 1 && i != 0) {
            if (this.theUnsmeltery.canBeUnsmelted(itemstack1)) {
               if (!this.func_75135_a(itemstack1, 0, 1, false)) {
                  return null;
               }
            } else if (TileEntityFurnace.func_145954_b(itemstack1)) {
               if (!this.func_75135_a(itemstack1, 1, 2, false)) {
                  return null;
               }
            } else if (i >= 3 && i < 30) {
               if (!this.func_75135_a(itemstack1, 30, 39, false)) {
                  return null;
               }
            } else if (i >= 30 && i < 39 && !this.func_75135_a(itemstack1, 3, 30, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 3, 39, false)) {
            return null;
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
