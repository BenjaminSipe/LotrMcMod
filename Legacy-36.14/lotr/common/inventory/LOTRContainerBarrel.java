package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerBarrel extends Container {
   public LOTRTileEntityBarrel theBarrel;
   private int barrelMode = 0;
   private int brewingTime = 0;

   public LOTRContainerBarrel(InventoryPlayer inv, LOTRTileEntityBarrel barrel) {
      this.theBarrel = barrel;

      int i;
      int j;
      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 3; ++j) {
            LOTRSlotBarrel slot = new LOTRSlotBarrel(barrel, j + i * 3, 14 + j * 18, 34 + i * 18);
            if (i == 2) {
               slot.setWaterSource();
            }

            this.func_75146_a(slot);
         }
      }

      this.func_75146_a(new LOTRSlotBarrelResult(barrel, 9, 108, 52));

      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 25 + j * 18, 139 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 25 + i * 18, 197));
      }

      if (!barrel.func_145831_w().field_72995_K && inv.field_70458_d instanceof EntityPlayerMP) {
         barrel.players.add(inv.field_70458_d);
      }

   }

   public void func_75132_a(ICrafting crafting) {
      super.func_75132_a(crafting);
      crafting.func_71112_a(this, 0, this.theBarrel.barrelMode);
      crafting.func_71112_a(this, 1, this.theBarrel.brewingTime);
   }

   public void func_75142_b() {
      super.func_75142_b();

      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         ICrafting crafting = (ICrafting)this.field_75149_d.get(i);
         if (this.barrelMode != this.theBarrel.barrelMode) {
            crafting.func_71112_a(this, 0, this.theBarrel.barrelMode);
         }

         if (this.brewingTime != this.theBarrel.brewingTime) {
            crafting.func_71112_a(this, 1, this.theBarrel.brewingTime);
         }
      }

      this.barrelMode = this.theBarrel.barrelMode;
      this.brewingTime = this.theBarrel.brewingTime;
   }

   @SideOnly(Side.CLIENT)
   public void func_75137_b(int i, int j) {
      if (i == 0) {
         this.theBarrel.barrelMode = j;
      }

      if (i == 1) {
         this.theBarrel.brewingTime = j;
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theBarrel.func_70300_a(entityplayer);
   }

   public void func_75134_a(EntityPlayer entityplayer) {
      super.func_75134_a(entityplayer);
      if (!this.theBarrel.func_145831_w().field_72995_K && entityplayer instanceof EntityPlayerMP) {
         this.theBarrel.players.remove(entityplayer);
      }

   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i < 9) {
            if (!this.func_75135_a(itemstack1, 10, 46, true)) {
               return null;
            }
         } else if (i != 9) {
            boolean flag = false;
            Slot aBarrelSlot = (Slot)this.field_75151_b.get(0);
            if (aBarrelSlot.func_75214_a(itemstack1)) {
               if (LOTRBrewingRecipes.isWaterSource(itemstack1)) {
                  flag = this.func_75135_a(itemstack1, 6, 9, false);
               } else {
                  flag = this.func_75135_a(itemstack1, 0, 6, false);
               }
            }

            if (!flag) {
               if (i >= 10 && i < 37) {
                  if (!this.func_75135_a(itemstack1, 37, 46, false)) {
                     return null;
                  }
               } else if (!this.func_75135_a(itemstack1, 10, 37, false)) {
                  return null;
               }
            }
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
