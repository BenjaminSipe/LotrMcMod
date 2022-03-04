package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDaleMerchant extends LOTREntityDaleMan implements LOTRTravellingTrader {
   private static final int[] hatColors = new int[]{8874591, 11895125, 4949452, 8298956, 5657939};
   private static final int[] featherColors = new int[]{16777215, 6736967, 15358290, 156402, 15719168};

   public LOTREntityDaleMerchant(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.DALE_MERCHANT_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.DALE_MERCHANT_SELL;
   }

   public LOTREntityNPC createTravellingEscort() {
      return new LOTREntityDaleMan(this.field_70170_p);
   }

   public String getDepartureSpeech() {
      return "dale/merchant/departure";
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
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDaleMerchant);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "dale/merchant/friendly" : "dale/merchant/hostile";
   }
}
