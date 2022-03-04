package lotr.common.entity.npc;

import lotr.common.item.LOTRItemMug;
import lotr.common.quest.IPickpocketable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class LOTRTradeEntry {
   private final ItemStack tradeItem;
   private int tradeCost;
   private int recentTradeValue;
   private int lockedTicks;
   private LOTRTraderNPCInfo theTrader;

   public LOTRTradeEntry(ItemStack itemstack, int cost) {
      this.tradeItem = itemstack;
      this.tradeCost = cost;
   }

   public ItemStack createTradeItem() {
      return this.tradeItem.func_77946_l();
   }

   public int getCost() {
      return this.tradeCost;
   }

   public void setCost(int cost) {
      this.tradeCost = cost;
   }

   public void setOwningTrader(LOTRTraderNPCInfo trader) {
      if (this.theTrader != null) {
         throw new IllegalArgumentException("Cannot assign already-owned trade entry to a different trader!");
      } else {
         this.theTrader = trader;
      }
   }

   public boolean isAvailable() {
      if (this.theTrader != null && this.theTrader.shouldLockTrades()) {
         return this.recentTradeValue < this.theTrader.getLockTradeAtValue() && this.lockedTicks <= 0;
      } else {
         return true;
      }
   }

   public float getLockedProgress() {
      return this.theTrader != null && this.theTrader.shouldLockTrades() ? (float)this.recentTradeValue / (float)this.theTrader.getLockTradeAtValue() : 0.0F;
   }

   private int getLockedProgressInt(int i) {
      float f = this.getLockedProgress();
      return Math.round(f * (float)i);
   }

   public int getLockedProgressForSlot() {
      return this.getLockedProgressInt(16);
   }

   public boolean updateAvailability(int tick) {
      boolean prevAvailable = this.isAvailable();
      int prevLockProgress = this.getLockedProgressForSlot();
      if (tick % this.theTrader.getValueDecayTicks() == 0 && this.recentTradeValue > 0) {
         --this.recentTradeValue;
      }

      if (this.lockedTicks > 0) {
         --this.lockedTicks;
      }

      if (this.isAvailable() != prevAvailable) {
         return true;
      } else {
         return this.getLockedProgressForSlot() != prevLockProgress;
      }
   }

   public boolean matches(ItemStack itemstack) {
      if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
         return false;
      } else {
         ItemStack tradeCreated = this.createTradeItem();
         if (LOTRItemMug.isItemFullDrink(tradeCreated)) {
            ItemStack tradeDrink = LOTRItemMug.getEquivalentDrink(tradeCreated);
            ItemStack offerDrink = LOTRItemMug.getEquivalentDrink(itemstack);
            return tradeDrink.func_77973_b() == offerDrink.func_77973_b();
         } else {
            return OreDictionary.itemMatches(tradeCreated, itemstack, false);
         }
      }
   }

   public void doTransaction(int value) {
      this.recentTradeValue += value;
   }

   public void setLockedForTicks(int ticks) {
      this.lockedTicks = ticks;
   }

   public void writeToNBT(NBTTagCompound nbt) {
      this.tradeItem.func_77955_b(nbt);
      nbt.func_74768_a("Cost", this.tradeCost);
      nbt.func_74768_a("RecentTradeValue", this.recentTradeValue);
      nbt.func_74768_a("LockedTicks", this.lockedTicks);
   }

   public static LOTRTradeEntry readFromNBT(NBTTagCompound nbt) {
      ItemStack savedItem = ItemStack.func_77949_a(nbt);
      if (savedItem != null) {
         int cost = nbt.func_74762_e("Cost");
         LOTRTradeEntry trade = new LOTRTradeEntry(savedItem, cost);
         if (nbt.func_74764_b("RecentTradeValue")) {
            trade.recentTradeValue = nbt.func_74762_e("RecentTradeValue");
         }

         trade.lockedTicks = nbt.func_74762_e("LockedTicks");
         return trade;
      } else {
         return null;
      }
   }
}
