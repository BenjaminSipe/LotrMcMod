package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeEntries;
import lotr.common.entity.npc.LOTRTradeEntry;
import lotr.common.entity.npc.LOTRTradeSellResult;
import lotr.common.entity.npc.LOTRTradeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRContainerTrade extends Container {
   public IInventory tradeInvBuy = new InventoryBasic("trade", false, 9);
   public IInventory tradeInvSell = new InventoryBasic("trade", false, 9);
   public IInventory tradeInvSellOffer = new InventoryBasic("trade", false, 9);
   public LOTRTradeable theTrader;
   public LOTREntityNPC theTraderNPC;
   private World theWorld;

   public LOTRContainerTrade(InventoryPlayer inv, LOTRTradeable trader, World world) {
      this.theTrader = trader;
      this.theTraderNPC = (LOTREntityNPC)trader;
      this.theWorld = world;
      if (!world.field_72995_K) {
         this.updateAllTradeSlots();
      }

      int i;
      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new LOTRSlotTrade(this, this.tradeInvBuy, i, 8 + i * 18, 40, this.theTraderNPC, LOTRTradeEntries.TradeType.BUY));
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new LOTRSlotTrade(this, this.tradeInvSell, i, 8 + i * 18, 92, this.theTraderNPC, LOTRTradeEntries.TradeType.SELL));
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(this.tradeInvSellOffer, i, 8 + i * 18, 141));
      }

      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 188 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(inv, i, 8 + i * 18, 246));
      }

   }

   public void updateAllTradeSlots() {
      LOTRTradeEntry[] buyTrades = this.theTraderNPC.traderNPCInfo.getBuyTrades();
      LOTRTradeEntry[] sellTrades = this.theTraderNPC.traderNPCInfo.getSellTrades();
      int i;
      LOTRTradeEntry trade;
      if (buyTrades != null) {
         for(i = 0; i < this.tradeInvBuy.func_70302_i_(); ++i) {
            trade = null;
            if (i < buyTrades.length) {
               trade = buyTrades[i];
            }

            if (trade != null) {
               this.tradeInvBuy.func_70299_a(i, trade.createTradeItem());
            } else {
               this.tradeInvBuy.func_70299_a(i, (ItemStack)null);
            }
         }
      }

      if (sellTrades != null) {
         for(i = 0; i < this.tradeInvSell.func_70302_i_(); ++i) {
            trade = null;
            if (i < sellTrades.length) {
               trade = sellTrades[i];
            }

            if (trade != null) {
               this.tradeInvSell.func_70299_a(i, trade.createTradeItem());
            } else {
               this.tradeInvSell.func_70299_a(i, (ItemStack)null);
            }
         }
      }

   }

   public void func_75132_a(ICrafting crafting) {
      super.func_75132_a(crafting);
      this.theTraderNPC.traderNPCInfo.sendClientPacket((EntityPlayer)crafting);
   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theTraderNPC != null && (double)entityplayer.func_70032_d(this.theTraderNPC) <= 12.0D && this.theTraderNPC.func_70089_S() && this.theTraderNPC.func_70638_az() == null && this.theTrader.canTradeWith(entityplayer);
   }

   public void func_75134_a(EntityPlayer entityplayer) {
      super.func_75134_a(entityplayer);
      if (!this.theWorld.field_72995_K) {
         for(int i = 0; i < this.tradeInvSellOffer.func_70302_i_(); ++i) {
            ItemStack itemstack = this.tradeInvSellOffer.func_70304_b(i);
            if (itemstack != null) {
               entityplayer.func_71019_a(itemstack, false);
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
         LOTRTradeSellResult sellResult = LOTRTradeEntries.getItemSellResult(itemstack1, this.theTraderNPC);
         boolean sellable = sellResult != null && sellResult.tradesMade > 0;
         if (i < 9) {
            if (!this.func_75135_a(itemstack1, 27, 63, true)) {
               return null;
            }
         } else {
            if (i >= 9 && i < 18) {
               return null;
            }

            if (i >= 18 && i < 27) {
               if (!this.func_75135_a(itemstack1, 27, 63, true)) {
                  return null;
               }
            } else if (sellable) {
               if (!this.func_75135_a(itemstack1, 18, 27, false)) {
                  return null;
               }
            } else if (i >= 27 && i < 54) {
               if (!this.func_75135_a(itemstack1, 54, 63, false)) {
                  return null;
               }
            } else if (i >= 54 && i < 63 && !this.func_75135_a(itemstack1, 27, 54, false)) {
               return null;
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
