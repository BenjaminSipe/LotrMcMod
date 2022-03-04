package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class LOTRContainerHobbitOven extends Container {
   private LOTRTileEntityHobbitOven theOven;
   private int currentCookTime = 0;
   private int ovenCookTime = 0;
   private int currentItemFuelValue = 0;

   public LOTRContainerHobbitOven(InventoryPlayer inv, LOTRTileEntityHobbitOven oven) {
      this.theOven = oven;

      int i;
      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(oven, i, 8 + i * 18, 21));
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new SlotFurnace(inv.field_70458_d, oven, i + 9, 8 + i * 18, 67));
      }

      this.func_75146_a(new Slot(oven, 18, 80, 111));

      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 133 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 191));
      }

   }

   public void func_75132_a(ICrafting crafting) {
      super.func_75132_a(crafting);
      crafting.func_71112_a(this, 0, this.theOven.currentCookTime);
      crafting.func_71112_a(this, 1, this.theOven.ovenCookTime);
      crafting.func_71112_a(this, 2, this.theOven.currentItemFuelValue);
   }

   public void func_75142_b() {
      super.func_75142_b();

      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         ICrafting crafting = (ICrafting)this.field_75149_d.get(i);
         if (this.currentCookTime != this.theOven.currentCookTime) {
            crafting.func_71112_a(this, 0, this.theOven.currentCookTime);
         }

         if (this.ovenCookTime != this.theOven.ovenCookTime) {
            crafting.func_71112_a(this, 1, this.theOven.ovenCookTime);
         }

         if (this.currentItemFuelValue != this.theOven.currentItemFuelValue) {
            crafting.func_71112_a(this, 2, this.theOven.currentItemFuelValue);
         }
      }

      this.currentCookTime = this.theOven.currentCookTime;
      this.ovenCookTime = this.theOven.ovenCookTime;
      this.currentItemFuelValue = this.theOven.currentItemFuelValue;
   }

   @SideOnly(Side.CLIENT)
   public void func_75137_b(int i, int j) {
      if (i == 0) {
         this.theOven.currentCookTime = j;
      }

      if (i == 1) {
         this.theOven.ovenCookTime = j;
      }

      if (i == 2) {
         this.theOven.currentItemFuelValue = j;
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theOven.func_70300_a(entityplayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i >= 9 && i < 18) {
            if (!this.func_75135_a(itemstack1, 19, 55, true)) {
               return null;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (i >= 9 && i != 18) {
            if (LOTRTileEntityHobbitOven.isCookResultAcceptable(FurnaceRecipes.func_77602_a().func_151395_a(itemstack1))) {
               if (!this.func_75135_a(itemstack1, 0, 9, false)) {
                  return null;
               }
            } else if (TileEntityFurnace.func_145954_b(itemstack1)) {
               if (!this.func_75135_a(itemstack1, 18, 19, false)) {
                  return null;
               }
            } else if (i >= 19 && i < 46) {
               if (!this.func_75135_a(itemstack1, 46, 55, false)) {
                  return null;
               }
            } else if (i >= 46 && i < 55 && !this.func_75135_a(itemstack1, 19, 46, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 19, 55, false)) {
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
