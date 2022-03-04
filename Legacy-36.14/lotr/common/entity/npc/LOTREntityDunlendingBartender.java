package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDunlendingBartender extends LOTREntityDunlending implements LOTRTradeable.Bartender {
   public LOTREntityDunlendingBartender(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcLocationName = "entity.lotr.DunlendingBartender.locationName";
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.DUNLENDING_BARTENDER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.DUNLENDING_BARTENDER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.mug));
      return data;
   }

   public void dropDunlendingItems(boolean flag, int i) {
      int j = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int k = 0; k < j; ++k) {
         int l = this.field_70146_Z.nextInt(7);
         switch(l) {
         case 0:
         case 1:
         case 2:
            Item food = LOTRFoods.DUNLENDING.getRandomFood(this.field_70146_Z).func_77973_b();
            this.func_70099_a(new ItemStack(food), 0.0F);
            break;
         case 3:
            this.func_70099_a(new ItemStack(Items.field_151074_bl, 2 + this.field_70146_Z.nextInt(3)), 0.0F);
            break;
         case 4:
         case 5:
            this.func_70099_a(new ItemStack(LOTRMod.mug), 0.0F);
            break;
         case 6:
            Item drink = LOTRFoods.DUNLENDING_DRINK.getRandomFood(this.field_70146_Z).func_77973_b();
            this.func_70099_a(new ItemStack(drink, 1, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
         }
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDunlendingBartender);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "dunlending/bartender/friendly" : "dunlending/dunlending/hostile";
   }
}
