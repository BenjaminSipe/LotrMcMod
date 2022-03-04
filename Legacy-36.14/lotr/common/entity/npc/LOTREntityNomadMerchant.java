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

public class LOTREntityNomadMerchant extends LOTREntityNomad implements LOTRTravellingTrader {
   private static int[] robeColors = new int[]{15723226, 13551017, 6512465, 2499615, 11376219, 7825215};

   public LOTREntityNomadMerchant(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.NOMAD_MERCHANT_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.NOMAD_MERCHANT_SELL;
   }

   public LOTREntityNPC createTravellingEscort() {
      return new LOTREntityNomad(this.field_70170_p);
   }

   public String getDepartureSpeech() {
      return "nearHarad/nomad/merchant/departure";
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pouch, 1, 3));
      int robeColor = robeColors[this.field_70146_Z.nextInt(robeColors.length)];
      ItemStack[] robe = new ItemStack[]{new ItemStack(LOTRMod.bootsHaradRobes), new ItemStack(LOTRMod.legsHaradRobes), new ItemStack(LOTRMod.bodyHaradRobes), new ItemStack(LOTRMod.helmetHaradRobes)};
      ItemStack[] var4 = robe;
      int var5 = robe.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemStack item = var4[var6];
         LOTRItemHaradRobes.setRobesColor(item, robeColor);
      }

      if (this.field_70146_Z.nextBoolean()) {
         LOTRItemHaradTurban.setHasOrnament(robe[3], true);
      }

      this.func_70062_b(1, robe[0]);
      this.func_70062_b(2, robe[1]);
      this.func_70062_b(3, robe[2]);
      this.func_70062_b(4, robe[3]);
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNomadMerchant);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/nomad/merchant/friendly" : "nearHarad/nomad/merchant/hostile";
   }
}
