package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.recipe.LOTRMillstoneRecipes;
import lotr.common.tileentity.LOTRTileEntityMillstone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerMillstone extends Container {
   private LOTRTileEntityMillstone theMillstone;
   private int currentMillTime = 0;
   private boolean isMilling;

   public LOTRContainerMillstone(InventoryPlayer inv, LOTRTileEntityMillstone millstone) {
      this.theMillstone = millstone;
      this.func_75146_a(new Slot(millstone, 0, 84, 25));
      this.func_75146_a(new LOTRSlotMillstone(inv.field_70458_d, millstone, 1, 84, 71));

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 100 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 158));
      }

   }

   public void func_75132_a(ICrafting crafting) {
      super.func_75132_a(crafting);
      crafting.func_71112_a(this, 0, this.theMillstone.currentMillTime);
      crafting.func_71112_a(this, 1, this.theMillstone.isMilling ? 1 : 0);
   }

   public void func_75142_b() {
      super.func_75142_b();

      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         ICrafting crafting = (ICrafting)this.field_75149_d.get(i);
         if (this.currentMillTime != this.theMillstone.currentMillTime) {
            crafting.func_71112_a(this, 0, this.theMillstone.currentMillTime);
         }

         if (this.isMilling != this.theMillstone.isMilling) {
            crafting.func_71112_a(this, 1, this.theMillstone.isMilling ? 1 : 0);
         }
      }

      this.currentMillTime = this.theMillstone.currentMillTime;
      this.isMilling = this.theMillstone.isMilling;
   }

   @SideOnly(Side.CLIENT)
   public void func_75137_b(int i, int j) {
      if (i == 0) {
         this.theMillstone.currentMillTime = j;
      }

      if (i == 1) {
         this.theMillstone.isMilling = j == 1;
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theMillstone.func_70300_a(entityplayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i == 1) {
            if (!this.func_75135_a(itemstack1, 2, 38, true)) {
               return null;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (i != 0) {
            if (LOTRMillstoneRecipes.getMillingResult(itemstack1) != null) {
               if (!this.func_75135_a(itemstack1, 0, 1, false)) {
                  return null;
               }
            } else if (i >= 2 && i < 29) {
               if (!this.func_75135_a(itemstack1, 29, 38, false)) {
                  return null;
               }
            } else if (i >= 29 && i < 38 && !this.func_75135_a(itemstack1, 2, 29, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 2, 38, false)) {
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
