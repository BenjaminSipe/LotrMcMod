package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHighElfSmith extends LOTREntityHighElf implements LOTRTradeable.Smith {
   public LOTREntityHighElfSmith(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.HIGH_ELF_SMITH_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.HIGH_ELF_SMITH_SELL;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      if (type == LOTRTradeEntries.TradeType.BUY) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeHighElfSmith);
      }

   }

   public boolean shouldRenderNPCHair() {
      return false;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "highElf/smith/friendly" : "highElf/smith/neutral";
      } else {
         return "highElf/smith/hostile";
      }
   }
}
