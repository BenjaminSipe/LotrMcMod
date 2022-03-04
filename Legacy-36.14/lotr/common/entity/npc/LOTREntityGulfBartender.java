package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfBartender extends LOTREntityGulfHaradrim implements LOTRTradeable.Bartender {
   public LOTREntityGulfBartender(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcLocationName = "entity.lotr.GulfBartender.locationName";
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GULF_BARTENDER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GULF_BARTENDER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.skullCup));
      return data;
   }

   public void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int drinks = 1 + this.field_70146_Z.nextInt(4) + i;

      for(int l = 0; l < drinks; ++l) {
         ItemStack drink = LOTRFoods.GULF_HARAD_DRINK.getRandomFood(this.field_70146_Z);
         this.func_70099_a(drink, 0.0F);
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeHaradBartender);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/gulf/bartender/friendly" : "nearHarad/gulf/bartender/hostile";
   }
}
