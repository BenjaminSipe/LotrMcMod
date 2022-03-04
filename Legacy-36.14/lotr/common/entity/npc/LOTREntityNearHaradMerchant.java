package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.item.LOTRItemHaradTurban;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNearHaradMerchant extends LOTREntityNearHaradrim implements LOTRTravellingTrader {
   private static int[] robeColors = new int[]{15723226, 14829087, 12653845, 8526876, 2625038};

   public LOTREntityNearHaradMerchant(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.NEAR_HARAD_MERCHANT_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.NEAR_HARAD_MERCHANT_SELL;
   }

   public LOTREntityNPC createTravellingEscort() {
      return new LOTREntityNearHaradrim(this.field_70170_p);
   }

   public String getDepartureSpeech() {
      return "nearHarad/merchant/departure";
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pouch, 1, 3));
      int robeColor = robeColors[this.field_70146_Z.nextInt(robeColors.length)];
      ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
      LOTRItemHaradRobes.setRobesColor(turban, robeColor);
      if (this.field_70146_Z.nextBoolean()) {
         LOTRItemHaradTurban.setHasOrnament(turban, true);
      }

      this.func_70062_b(1, (ItemStack)null);
      this.func_70062_b(2, (ItemStack)null);
      this.func_70062_b(3, (ItemStack)null);
      this.func_70062_b(4, turban);
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNearHaradMerchant);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/merchant/friendly" : "nearHarad/merchant/hostile";
   }
}
