package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.tileentity.LOTRTileEntityAlloyForgeBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class LOTRContainerAlloyForge extends Container {
   private LOTRTileEntityAlloyForgeBase theForge;
   private int currentSmeltTime = 0;
   private int forgeSmeltTime = 0;
   private int currentItemFuelValue = 0;

   public LOTRContainerAlloyForge(InventoryPlayer inv, LOTRTileEntityAlloyForgeBase forge) {
      this.theForge = forge;

      int i;
      for(i = 0; i < 4; ++i) {
         this.func_75146_a(new Slot(forge, i, 53 + i * 18, 21));
      }

      for(i = 0; i < 4; ++i) {
         this.func_75146_a(new Slot(forge, i + 4, 53 + i * 18, 39));
      }

      for(i = 0; i < 4; ++i) {
         this.func_75146_a(new SlotFurnace(inv.field_70458_d, forge, i + 8, 53 + i * 18, 85));
      }

      this.func_75146_a(new Slot(forge, 12, 80, 129));

      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 151 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 209));
      }

   }

   public void func_75132_a(ICrafting crafting) {
      super.func_75132_a(crafting);
      crafting.func_71112_a(this, 0, this.theForge.currentSmeltTime);
      crafting.func_71112_a(this, 1, this.theForge.forgeSmeltTime);
      crafting.func_71112_a(this, 2, this.theForge.currentItemFuelValue);
   }

   public void func_75142_b() {
      super.func_75142_b();

      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         ICrafting crafting = (ICrafting)this.field_75149_d.get(i);
         if (this.currentSmeltTime != this.theForge.currentSmeltTime) {
            crafting.func_71112_a(this, 0, this.theForge.currentSmeltTime);
         }

         if (this.forgeSmeltTime != this.theForge.forgeSmeltTime) {
            crafting.func_71112_a(this, 1, this.theForge.forgeSmeltTime);
         }

         if (this.currentItemFuelValue != this.theForge.currentItemFuelValue) {
            crafting.func_71112_a(this, 2, this.theForge.currentItemFuelValue);
         }
      }

      this.currentSmeltTime = this.theForge.currentSmeltTime;
      this.forgeSmeltTime = this.theForge.forgeSmeltTime;
      this.currentItemFuelValue = this.theForge.currentItemFuelValue;
   }

   @SideOnly(Side.CLIENT)
   public void func_75137_b(int i, int j) {
      if (i == 0) {
         this.theForge.currentSmeltTime = j;
      }

      if (i == 1) {
         this.theForge.forgeSmeltTime = j;
      }

      if (i == 2) {
         this.theForge.currentItemFuelValue = j;
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theForge.func_70300_a(entityplayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i >= 8 && i < 12) {
            if (!this.func_75135_a(itemstack1, 13, 49, true)) {
               return null;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if (i >= 8 && i != 12) {
            if (this.theForge.getSmeltingResult(itemstack1) != null) {
               if (!this.func_75135_a(itemstack1, 4, 8, false)) {
                  return null;
               }
            } else if (TileEntityFurnace.func_145954_b(itemstack1)) {
               if (!this.func_75135_a(itemstack1, 12, 13, false)) {
                  return null;
               }
            } else if (i >= 13 && i < 40) {
               if (!this.func_75135_a(itemstack1, 40, 49, false)) {
                  return null;
               }
            } else if (i >= 40 && i < 49 && !this.func_75135_a(itemstack1, 13, 40, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 13, 49, false)) {
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
