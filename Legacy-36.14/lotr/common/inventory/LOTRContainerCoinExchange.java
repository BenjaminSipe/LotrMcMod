package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemCoin;
import lotr.common.quest.IPickpocketable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRContainerCoinExchange extends Container {
   public IInventory coinInputInv = new LOTRContainerCoinExchange.InventoryCoinExchangeSlot(1);
   public IInventory exchangeInv = new LOTRContainerCoinExchange.InventoryCoinExchangeSlot(2);
   private World theWorld;
   public LOTREntityNPC theTraderNPC;
   public boolean exchanged = false;

   public LOTRContainerCoinExchange(EntityPlayer entityplayer, LOTREntityNPC npc) {
      this.theWorld = entityplayer.field_70170_p;
      this.theTraderNPC = npc;
      this.func_75146_a(new Slot(this.coinInputInv, 0, 80, 46) {
         public boolean func_75214_a(ItemStack itemstack) {
            return super.func_75214_a(itemstack) && itemstack != null && LOTRContainerCoinExchange.isValidCoin(itemstack);
         }
      });

      class SlotCoinResult extends Slot {
         public SlotCoinResult(IInventory inv, int i, int j, int k) {
            super(inv, i, j, k);
         }

         public boolean func_75214_a(ItemStack itemstack) {
            return false;
         }

         public boolean func_82869_a(EntityPlayer entityplayer) {
            return LOTRContainerCoinExchange.this.exchanged;
         }
      }

      this.func_75146_a(new SlotCoinResult(this.exchangeInv, 0, 26, 46));
      this.func_75146_a(new SlotCoinResult(this.exchangeInv, 1, 134, 46));

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(entityplayer.field_71071_by, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(entityplayer.field_71071_by, i, 8 + i * 18, 164));
      }

      this.func_75130_a(this.coinInputInv);
   }

   public static boolean isValidCoin(ItemStack item) {
      return item.func_77973_b() == LOTRMod.silverCoin && !IPickpocketable.Helper.isPickpocketed(item);
   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return true;
   }

   public void handleExchangePacket(int slot) {
      if (!this.exchanged && this.coinInputInv.func_70301_a(0) != null && slot >= 0 && slot < this.exchangeInv.func_70302_i_() && this.exchangeInv.func_70301_a(slot) != null) {
         this.exchanged = true;
         int coins = this.exchangeInv.func_70301_a(slot).field_77994_a;
         int coinsTaken = 0;
         if (slot == 0) {
            coinsTaken = coins / 10;
         } else if (slot == 1) {
            coinsTaken = coins * 10;
         }

         this.coinInputInv.func_70298_a(0, coinsTaken);

         for(int i = 0; i < this.exchangeInv.func_70302_i_(); ++i) {
            if (i != slot) {
               this.exchangeInv.func_70299_a(i, (ItemStack)null);
            }
         }

         this.func_75142_b();
         this.theTraderNPC.playTradeSound();
      }

   }

   public void func_75132_a(ICrafting crafting) {
      this.sendClientExchangedData(crafting);
      super.func_75132_a(crafting);
   }

   public void func_75142_b() {
      for(int i = 0; i < this.field_75149_d.size(); ++i) {
         ICrafting crafting = (ICrafting)this.field_75149_d.get(i);
         this.sendClientExchangedData(crafting);
      }

      super.func_75142_b();
   }

   private void sendClientExchangedData(ICrafting crafting) {
      crafting.func_71112_a(this, 0, this.exchanged ? 1 : 0);
   }

   @SideOnly(Side.CLIENT)
   public void func_75137_b(int i, int j) {
      if (i == 0) {
         this.exchanged = j == 1;
      }

   }

   public void func_75130_a(IInventory inv) {
      int coins;
      if (inv == this.coinInputInv) {
         if (!this.exchanged) {
            ItemStack coin = this.coinInputInv.func_70301_a(0);
            if (coin != null && coin.field_77994_a > 0 && isValidCoin(coin)) {
               coins = coin.field_77994_a;
               int coinType = coin.func_77960_j();
               if (coinType <= 0) {
                  this.exchangeInv.func_70299_a(0, (ItemStack)null);
               } else {
                  int coinsFloor;
                  for(coinsFloor = coins; coinsFloor * 10 > this.exchangeInv.func_70297_j_(); --coinsFloor) {
                  }

                  this.exchangeInv.func_70299_a(0, new ItemStack(LOTRMod.silverCoin, coinsFloor * 10, coinType - 1));
               }

               if (coinType < LOTRItemCoin.values.length - 1 && coins >= 10) {
                  this.exchangeInv.func_70299_a(1, new ItemStack(LOTRMod.silverCoin, coins / 10, coinType + 1));
               } else {
                  this.exchangeInv.func_70299_a(1, (ItemStack)null);
               }
            } else {
               this.exchangeInv.func_70299_a(0, (ItemStack)null);
               this.exchangeInv.func_70299_a(1, (ItemStack)null);
            }
         }
      } else if (inv == this.exchangeInv && this.exchanged) {
         boolean anyItems = false;

         for(coins = 0; coins < this.exchangeInv.func_70302_i_(); ++coins) {
            if (this.exchangeInv.func_70301_a(coins) != null) {
               anyItems = true;
            }
         }

         if (!anyItems) {
            this.exchanged = false;
            this.func_75130_a(this.coinInputInv);
         }
      }

      super.func_75130_a(inv);
   }

   public void func_75134_a(EntityPlayer entityplayer) {
      super.func_75134_a(entityplayer);
      if (!entityplayer.field_70170_p.field_72995_K) {
         int i;
         ItemStack itemstack;
         for(i = 0; i < this.coinInputInv.func_70302_i_(); ++i) {
            itemstack = this.coinInputInv.func_70304_b(i);
            if (itemstack != null) {
               entityplayer.func_71019_a(itemstack, false);
            }
         }

         if (this.exchanged) {
            for(i = 0; i < this.exchangeInv.func_70302_i_(); ++i) {
               itemstack = this.exchangeInv.func_70304_b(i);
               if (itemstack != null) {
                  entityplayer.func_71019_a(itemstack, false);
               }
            }
         }
      }

   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i < 3) {
            if (!this.func_75135_a(itemstack1, 3, 39, true)) {
               return null;
            }

            this.func_75130_a(slot.field_75224_c);
         } else {
            boolean flag = false;
            Slot coinSlot = (Slot)this.field_75151_b.get(0);
            ItemStack coinStack = coinSlot.func_75211_c();
            if (coinSlot.func_75214_a(itemstack1) && this.func_75135_a(itemstack1, 0, 1, true)) {
               flag = true;
            }

            if (!flag) {
               if (i >= 3 && i < 30) {
                  if (!this.func_75135_a(itemstack1, 30, 39, false)) {
                     return null;
                  }
               } else if (!this.func_75135_a(itemstack1, 3, 30, false)) {
                  return null;
               }
            }
         }

         if (itemstack1.field_77994_a == 0) {
            slot.func_75215_d((ItemStack)null);
            this.func_75142_b();
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

   protected void func_75133_b(int i, int j, boolean flag, EntityPlayer entityplayer) {
   }

   public class InventoryCoinExchangeSlot extends InventoryBasic {
      public InventoryCoinExchangeSlot(int i) {
         super("coinExchange", true, i);
      }

      public void func_70296_d() {
         super.func_70296_d();
         LOTRContainerCoinExchange.this.func_75130_a(this);
      }
   }
}
