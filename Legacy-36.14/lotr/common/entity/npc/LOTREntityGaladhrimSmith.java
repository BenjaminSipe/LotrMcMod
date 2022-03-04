package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGaladhrimSmith extends LOTREntityGaladhrimElf implements LOTRTradeable.Smith {
   public LOTREntityGaladhrimSmith(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GALADHRIM_SMITH_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GALADHRIM_SMITH_SELL;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      if (type == LOTRTradeEntries.TradeType.BUY) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeGaladhrimSmith);
      }

   }

   public boolean shouldRenderNPCHair() {
      return false;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "galadhrim/smith/friendly" : "galadhrim/smith/neutral";
      } else {
         return "galadhrim/smith/hostile";
      }
   }
}
