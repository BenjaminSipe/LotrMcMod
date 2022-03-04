package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeEntries;
import lotr.common.entity.npc.LOTRTradeEntry;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class LOTRSlotTrade extends LOTRSlotProtected {
   private LOTRContainerTrade theContainer;
   private LOTREntityNPC theEntity;
   private LOTRTradeEntries.TradeType tradeType;

   public LOTRSlotTrade(LOTRContainerTrade container, IInventory inv, int i, int j, int k, LOTREntityNPC entity, LOTRTradeEntries.TradeType type) {
      super(inv, i, j, k);
      this.theContainer = container;
      this.theEntity = entity;
      this.tradeType = type;
   }

   public int cost() {
      LOTRTradeEntry trade = this.getTrade();
      return trade == null ? 0 : trade.getCost();
   }

   public LOTRTradeEntry getTrade() {
      LOTRTradeEntry[] trades = null;
      if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
         trades = this.theEntity.traderNPCInfo.getBuyTrades();
      } else if (this.tradeType == LOTRTradeEntries.TradeType.SELL) {
         trades = this.theEntity.traderNPCInfo.getSellTrades();
      }

      if (trades == null) {
         return null;
      } else {
         int i = this.getSlotIndex();
         return i >= 0 && i < trades.length ? trades[i] : null;
      }
   }

   public boolean func_82869_a(EntityPlayer entityplayer) {
      if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
         if (this.getTrade() != null && !this.getTrade().isAvailable()) {
            return false;
         }

         int coins = LOTRItemCoin.getInventoryValue(entityplayer, false);
         if (coins < this.cost()) {
            return false;
         }
      }

      return this.tradeType == LOTRTradeEntries.TradeType.SELL ? false : super.func_82869_a(entityplayer);
   }

   public void func_82870_a(EntityPlayer entityplayer, ItemStack itemstack) {
      if (this.tradeType == LOTRTradeEntries.TradeType.BUY && !entityplayer.field_70170_p.field_72995_K) {
         LOTRItemCoin.takeCoins(this.cost(), entityplayer);
      }

      super.func_82870_a(entityplayer, itemstack);
      if (this.tradeType == LOTRTradeEntries.TradeType.BUY) {
         LOTRTradeEntry trade = this.getTrade();
         if (!entityplayer.field_70170_p.field_72995_K && trade != null) {
            this.func_75215_d(trade.createTradeItem());
            ((EntityPlayerMP)entityplayer).func_71120_a(this.theContainer);
            this.theEntity.traderNPCInfo.onTrade(entityplayer, trade, LOTRTradeEntries.TradeType.BUY, this.cost());
            this.theEntity.playTradeSound();
         }
      }

   }
}
