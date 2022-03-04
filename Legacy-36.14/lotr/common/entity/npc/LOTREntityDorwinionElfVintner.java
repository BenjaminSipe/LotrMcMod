package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfVintner extends LOTREntityDorwinionElf implements LOTRTradeable {
   public LOTREntityDorwinionElfVintner(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.DORWINION_VINTNER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.DORWINION_VINTNER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      ItemStack drink = this.getBuyPool().getRandomTrades(this.field_70146_Z)[0].createTradeItem();
      this.npcItemsInv.setIdleItem(drink);
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      if (type == LOTRTradeEntries.TradeType.BUY) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyWineVintner);
      }

   }

   public boolean shouldRenderNPCHair() {
      return false;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "dorwinion/elfVintner/friendly" : "dorwinion/elfVintner/neutral";
      } else {
         return "dorwinion/elf/hostile";
      }
   }
}
