package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeEntries;
import lotr.common.entity.npc.LOTRTradeEntry;
import lotr.common.entity.npc.LOTRTradeSellResult;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class LOTRPacketSell implements IMessage {
   public void toBytes(ByteBuf data) {
   }

   public void fromBytes(ByteBuf data) {
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketSell packet, MessageContext context) {
         EntityPlayer entityplayer = context.getServerHandler().field_147369_b;
         Container container = entityplayer.field_71070_bA;
         if (container != null && container instanceof LOTRContainerTrade) {
            LOTRContainerTrade tradeContainer = (LOTRContainerTrade)container;
            LOTREntityNPC trader = tradeContainer.theTraderNPC;
            IInventory invSellOffer = tradeContainer.tradeInvSellOffer;
            Map tradesUsed = new HashMap();
            int totalCoins = 0;

            int tradeIndex;
            for(int i = 0; i < invSellOffer.func_70302_i_(); ++i) {
               ItemStack itemstack = invSellOffer.func_70301_a(i);
               if (itemstack != null) {
                  LOTRTradeSellResult sellResult = LOTRTradeEntries.getItemSellResult(itemstack, trader);
                  if (sellResult != null) {
                     tradeIndex = sellResult.tradeIndex;
                     int value = sellResult.totalSellValue;
                     int itemsSold = sellResult.itemsSold;
                     LOTRTradeEntry[] sellTrades = trader.traderNPCInfo.getSellTrades();
                     LOTRTradeEntry trade = null;
                     if (sellTrades != null) {
                        trade = sellTrades[tradeIndex];
                     }

                     totalCoins += value;
                     if (trade != null) {
                        Integer prevValue = (Integer)tradesUsed.get(trade);
                        if (prevValue == null) {
                           tradesUsed.put(trade, value);
                        } else {
                           tradesUsed.put(trade, prevValue + value);
                        }
                     }

                     itemstack.field_77994_a -= itemsSold;
                     if (itemstack.field_77994_a <= 0) {
                        invSellOffer.func_70299_a(i, (ItemStack)null);
                     }
                  }
               }
            }

            if (totalCoins > 0) {
               Iterator var19 = tradesUsed.entrySet().iterator();

               while(var19.hasNext()) {
                  Entry e = (Entry)var19.next();
                  LOTRTradeEntry trade = (LOTRTradeEntry)e.getKey();
                  tradeIndex = (Integer)e.getValue();
                  trader.traderNPCInfo.onTrade(entityplayer, trade, LOTRTradeEntries.TradeType.SELL, tradeIndex);
               }

               LOTRItemCoin.giveCoins(totalCoins, entityplayer);
               if (totalCoins >= 1000) {
                  LOTRLevelData.getData((EntityPlayer)entityplayer).addAchievement(LOTRAchievement.earnManyCoins);
               }

               trader.playTradeSound();
            }
         }

         return null;
      }
   }
}
