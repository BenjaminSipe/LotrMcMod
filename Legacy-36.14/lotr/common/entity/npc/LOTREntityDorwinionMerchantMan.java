package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionMerchantMan extends LOTREntityDorwinionMan implements LOTRTravellingTrader {
   public static final int[] hatColors = new int[]{15387062, 12361599, 7422850, 12677797, 13401212, 11350064, 9523548, 12502137, 11718290, 8817612, 6316484};
   public static final int[] featherColors = new int[]{16777215, 12887724, 15061504, 0, 7475245, 4402118, 8311657};

   public LOTREntityDorwinionMerchantMan(World world) {
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
      return "dorwinion/merchant/departure";
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      int colorHat = hatColors[this.field_70146_Z.nextInt(hatColors.length)];
      int colorFeather = featherColors[this.field_70146_Z.nextInt(featherColors.length)];
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
      return this.isFriendlyAndAligned(entityplayer) ? "dorwinion/merchant/friendly" : "dorwinion/merchant/hostile";
   }
}
