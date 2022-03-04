package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarfMerchant extends LOTREntityBlueDwarf implements LOTRTravellingTrader {
   public LOTREntityBlueDwarfMerchant(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.BLUE_DWARF_MERCHANT_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.BLUE_DWARF_MERCHANT_SELL;
   }

   public LOTREntityNPC createTravellingEscort() {
      return new LOTREntityBlueDwarf(this.field_70170_p);
   }

   public String getDepartureSpeech() {
      return "blueDwarf/merchant/departure";
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBlueDwarfMerchant);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "blueDwarf/merchant/friendly" : "blueDwarf/dwarf/hostile";
   }
}
