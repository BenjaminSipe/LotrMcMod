package lotr.common.entity.npc;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRLevelData;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketTraderInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;

public class LOTRTraderNPCInfo {
   private LOTREntityNPC theEntity;
   private LOTRTradeEntry[] buyTrades;
   private LOTRTradeEntry[] sellTrades;
   private int timeUntilAdvertisement;
   private int timeSinceTrade;
   private boolean shouldLockTrades = true;
   private int lockTradeAtValue = 200;
   private static final int defaultLockTradeAtValue = 200;
   private int lockValueDecayTicks = 60;
   private static final int defaultLockValueDecayTicks = 60;
   private boolean shouldRefresh = true;
   private int valueSinceRefresh;
   private int refreshAtValue = 5000;
   private static final int defaultRefreshAtValue = 5000;
   private int lockTicksAfterRefresh = 6000;
   private static final int defaultLockTicksAfterRefresh = 6000;

   public LOTRTraderNPCInfo(LOTREntityNPC npc) {
      this.theEntity = npc;
      if (this.theEntity instanceof LOTRTradeable && !this.theEntity.field_70170_p.field_72995_K) {
         this.refreshTrades();
      }

   }

   public LOTRTradeEntry[] getBuyTrades() {
      return this.buyTrades;
   }

   public LOTRTradeEntry[] getSellTrades() {
      return this.sellTrades;
   }

   private void setBuyTrades(LOTRTradeEntry[] trades) {
      LOTRTradeEntry[] var2 = trades;
      int var3 = trades.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRTradeEntry trade = var2[var4];
         trade.setOwningTrader(this);
      }

      this.buyTrades = trades;
   }

   private void setSellTrades(LOTRTradeEntry[] trades) {
      LOTRTradeEntry[] var2 = trades;
      int var3 = trades.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRTradeEntry trade = var2[var4];
         trade.setOwningTrader(this);
      }

      this.sellTrades = trades;
   }

   protected boolean shouldLockTrades() {
      return this.shouldLockTrades;
   }

   protected int getLockTradeAtValue() {
      return this.lockTradeAtValue;
   }

   protected int getValueDecayTicks() {
      return this.lockValueDecayTicks;
   }

   public void onTrade(EntityPlayer entityplayer, LOTRTradeEntry trade, LOTRTradeEntries.TradeType type, int value) {
      ((LOTRTradeable)this.theEntity).onPlayerTrade(entityplayer, type, trade.createTradeItem());
      LOTRLevelData.getData(entityplayer).getFactionData(this.theEntity.getFaction()).addTrade();
      trade.doTransaction(value);
      this.timeSinceTrade = 0;
      this.valueSinceRefresh += value;
      this.sendClientPacket(entityplayer);
   }

   private void refreshTrades() {
      LOTRTradeable theTrader = (LOTRTradeable)this.theEntity;
      Random rand = this.theEntity.func_70681_au();
      this.setBuyTrades(theTrader.getBuyPool().getRandomTrades(rand));
      this.setSellTrades(theTrader.getSellPool().getRandomTrades(rand));
      this.valueSinceRefresh = 0;

      for(int i = 0; i < this.theEntity.field_70170_p.field_73010_i.size(); ++i) {
         EntityPlayer entityplayer = (EntityPlayer)this.theEntity.field_70170_p.field_73010_i.get(i);
         Container container = entityplayer.field_71070_bA;
         if (container instanceof LOTRContainerTrade && ((LOTRContainerTrade)container).theTraderNPC == this.theEntity) {
            ((LOTRContainerTrade)container).updateAllTradeSlots();
         }
      }

   }

   private void setAllTradesDelayed() {
      int delay = this.lockTicksAfterRefresh;
      LOTRTradeEntry[] var2 = this.buyTrades;
      int var3 = var2.length;

      int var4;
      LOTRTradeEntry trade;
      for(var4 = 0; var4 < var3; ++var4) {
         trade = var2[var4];
         if (trade != null) {
            trade.setLockedForTicks(delay);
         }
      }

      var2 = this.sellTrades;
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         trade = var2[var4];
         if (trade != null) {
            trade.setLockedForTicks(delay);
         }
      }

   }

   public void onUpdate() {
      if (!this.theEntity.field_70170_p.field_72995_K) {
         if (this.timeUntilAdvertisement > 0) {
            --this.timeUntilAdvertisement;
         }

         ++this.timeSinceTrade;
         int ticksExisted = this.theEntity.field_70173_aa;
         boolean sendUpdate = false;
         LOTRTradeEntry[] var3 = this.buyTrades;
         int var4 = var3.length;

         int var5;
         LOTRTradeEntry trade;
         for(var5 = 0; var5 < var4; ++var5) {
            trade = var3[var5];
            if (trade != null && trade.updateAvailability(ticksExisted)) {
               sendUpdate = true;
            }
         }

         var3 = this.sellTrades;
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            trade = var3[var5];
            if (trade != null && trade.updateAvailability(ticksExisted)) {
               sendUpdate = true;
            }
         }

         if (this.shouldRefresh && this.valueSinceRefresh >= this.refreshAtValue) {
            this.refreshTrades();
            this.setAllTradesDelayed();
            sendUpdate = true;
         }

         if (sendUpdate) {
            for(int i = 0; i < this.theEntity.field_70170_p.field_73010_i.size(); ++i) {
               EntityPlayer entityplayer = (EntityPlayer)this.theEntity.field_70170_p.field_73010_i.get(i);
               Container container = entityplayer.field_71070_bA;
               if (container instanceof LOTRContainerTrade && ((LOTRContainerTrade)container).theTraderNPC == this.theEntity) {
                  this.sendClientPacket(entityplayer);
               }
            }
         }

         if (this.theEntity.func_70089_S() && this.theEntity.func_70638_az() == null && this.timeUntilAdvertisement == 0 && this.timeSinceTrade > 600) {
            double range = 10.0D;
            List players = this.theEntity.field_70170_p.func_72872_a(EntityPlayer.class, this.theEntity.field_70121_D.func_72314_b(range, range, range));
            Iterator var15 = players.iterator();

            while(true) {
               EntityPlayer entityplayer;
               do {
                  do {
                     do {
                        if (!var15.hasNext()) {
                           this.timeUntilAdvertisement = 20 * MathHelper.func_76136_a(this.theEntity.func_70681_au(), 5, 20);
                           return;
                        }

                        Object obj = var15.next();
                        entityplayer = (EntityPlayer)obj;
                     } while(!entityplayer.func_70089_S());
                  } while(entityplayer.field_71075_bZ.field_75098_d);
               } while(entityplayer.field_71070_bA != null && entityplayer.field_71070_bA != entityplayer.field_71069_bz);

               String speechBank = this.theEntity.getSpeechBank(entityplayer);
               if (speechBank != null && this.theEntity.func_70681_au().nextInt(3) == 0) {
                  this.theEntity.sendSpeechBank(entityplayer, speechBank);
               }
            }
         }
      }

   }

   public void writeToNBT(NBTTagCompound data) {
      NBTTagList sellTradesTags;
      LOTRTradeEntry[] var3;
      int var4;
      int var5;
      LOTRTradeEntry trade;
      NBTTagCompound nbt;
      NBTTagCompound sellTradesData;
      if (this.buyTrades != null) {
         sellTradesTags = new NBTTagList();
         var3 = this.buyTrades;
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            trade = var3[var5];
            if (trade != null) {
               nbt = new NBTTagCompound();
               trade.writeToNBT(nbt);
               sellTradesTags.func_74742_a(nbt);
            }
         }

         sellTradesData = new NBTTagCompound();
         sellTradesData.func_74782_a("Trades", sellTradesTags);
         data.func_74782_a("LOTRBuyTrades", sellTradesData);
      }

      if (this.sellTrades != null) {
         sellTradesTags = new NBTTagList();
         var3 = this.sellTrades;
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            trade = var3[var5];
            if (trade != null) {
               nbt = new NBTTagCompound();
               trade.writeToNBT(nbt);
               sellTradesTags.func_74742_a(nbt);
            }
         }

         sellTradesData = new NBTTagCompound();
         sellTradesData.func_74782_a("Trades", sellTradesTags);
         data.func_74782_a("LOTRSellTrades", sellTradesData);
      }

      data.func_74768_a("TimeSinceTrade", this.timeSinceTrade);
      data.func_74757_a("ShouldLockTrades", this.shouldLockTrades);
      data.func_74768_a("LockTradeAtValue", this.lockTradeAtValue);
      data.func_74768_a("LockValueDecayTicks", this.lockValueDecayTicks);
      data.func_74757_a("ShouldRefresh", this.shouldRefresh);
      data.func_74768_a("RefreshAtValue", this.refreshAtValue);
      data.func_74768_a("LockTicksAfterRefresh", this.lockTicksAfterRefresh);
      data.func_74768_a("ValueSinceRefresh", this.valueSinceRefresh);
   }

   public void readFromNBT(NBTTagCompound data) {
      NBTTagCompound sellTradesData;
      NBTTagList sellTradesTags;
      int i;
      NBTTagCompound nbt;
      LOTRTradeEntry trade;
      if (data.func_74764_b("LOTRBuyTrades")) {
         sellTradesData = data.func_74775_l("LOTRBuyTrades");
         if (sellTradesData.func_74764_b("Trades")) {
            sellTradesTags = sellTradesData.func_150295_c("Trades", 10);
            this.buyTrades = new LOTRTradeEntry[sellTradesTags.func_74745_c()];

            for(i = 0; i < sellTradesTags.func_74745_c(); ++i) {
               nbt = sellTradesTags.func_150305_b(i);
               trade = LOTRTradeEntry.readFromNBT(nbt);
               trade.setOwningTrader(this);
               this.buyTrades[i] = trade;
            }
         }
      }

      if (data.func_74764_b("LOTRSellTrades")) {
         sellTradesData = data.func_74775_l("LOTRSellTrades");
         if (sellTradesData.func_74764_b("Trades")) {
            sellTradesTags = sellTradesData.func_150295_c("Trades", 10);
            this.sellTrades = new LOTRTradeEntry[sellTradesTags.func_74745_c()];

            for(i = 0; i < sellTradesTags.func_74745_c(); ++i) {
               nbt = sellTradesTags.func_150305_b(i);
               trade = LOTRTradeEntry.readFromNBT(nbt);
               trade.setOwningTrader(this);
               this.sellTrades[i] = trade;
            }
         }
      }

      this.timeSinceTrade = data.func_74762_e("TimeSinceTrade");
      if (data.func_74764_b("ShouldLockTrades")) {
         this.shouldLockTrades = data.func_74767_n("ShouldLockTrades");
      }

      if (data.func_74764_b("LockTradeAtValue")) {
         this.lockTradeAtValue = data.func_74762_e("LockTradeAtValue");
      }

      if (data.func_74764_b("LockValueDecayTicks")) {
         this.lockValueDecayTicks = data.func_74762_e("LockValueDecayTicks");
      }

      if (data.func_74764_b("ShouldRefresh")) {
         this.shouldRefresh = data.func_74767_n("ShouldRefresh");
      }

      if (data.func_74764_b("RefreshAtValue")) {
         this.refreshAtValue = data.func_74762_e("RefreshAtValue");
      }

      if (data.func_74764_b("LockTicksAfterRefresh")) {
         this.lockTicksAfterRefresh = data.func_74762_e("LockTicksAfterRefresh");
      }

      this.valueSinceRefresh = data.func_74762_e("ValueSinceRefresh");
   }

   public void sendClientPacket(EntityPlayer entityplayer) {
      NBTTagCompound nbt = new NBTTagCompound();
      this.writeToNBT(nbt);
      LOTRPacketTraderInfo packet = new LOTRPacketTraderInfo(nbt);
      LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
   }

   public void receiveClientPacket(LOTRPacketTraderInfo packet) {
      NBTTagCompound nbt = packet.traderData;
      this.readFromNBT(nbt);
   }
}
