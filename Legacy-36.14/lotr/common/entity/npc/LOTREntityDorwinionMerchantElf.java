package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionMerchantElf extends LOTREntityDorwinionElf implements LOTRTravellingTrader {
   public LOTREntityDorwinionMerchantElf(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.DORWINION_MERCHANT_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.DORWINION_MERCHANT_SELL;
   }

   public LOTREntityNPC createTravellingEscort() {
      return new LOTREntityDorwinionGuard(this.field_70170_p);
   }

   public String getDepartureSpeech() {
      return "dorwinion/merchantElf/departure";
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      int colorHat = LOTREntityDorwinionMerchantMan.hatColors[this.field_70146_Z.nextInt(LOTREntityDorwinionMerchantMan.hatColors.length)];
      int colorFeather = LOTREntityDorwinionMerchantMan.featherColors[this.field_70146_Z.nextInt(LOTREntityDorwinionMerchantMan.featherColors.length)];
      LOTRItemLeatherHat.setHatColor(hat, colorHat);
      LOTRItemLeatherHat.setFeatherColor(hat, colorFeather);
      this.func_70062_b(4, hat);
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDorwinionMerchant);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "dorwinion/merchantElf/friendly" : "dorwinion/merchantElf/hostile";
   }
}
