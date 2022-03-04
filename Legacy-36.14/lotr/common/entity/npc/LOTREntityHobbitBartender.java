package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHobbitBartender extends LOTREntityHobbit implements LOTRTradeable.Bartender {
   public LOTREntityHobbitBartender(World world) {
      super(world);
      this.npcLocationName = "entity.lotr.HobbitBartender.locationName";
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.HOBBIT_BARTENDER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.HOBBIT_BARTENDER_SELL;
   }

   protected void dropHobbitItems(boolean flag, int i) {
      int count = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int k = 0; k < count; ++k) {
         int j = this.field_70146_Z.nextInt(10);
         switch(j) {
         case 0:
         case 1:
            this.func_70099_a(LOTRFoods.HOBBIT.getRandomFood(this.field_70146_Z), 0.0F);
            break;
         case 2:
            this.func_70099_a(new ItemStack(Items.field_151074_bl, 2 + this.field_70146_Z.nextInt(3)), 0.0F);
            break;
         case 3:
            this.func_70099_a(new ItemStack(Items.field_151054_z, 1 + this.field_70146_Z.nextInt(4)), 0.0F);
            break;
         case 4:
            this.func_70099_a(new ItemStack(LOTRMod.hobbitPipe, 1, this.field_70146_Z.nextInt(100)), 0.0F);
            break;
         case 5:
            this.func_70099_a(new ItemStack(LOTRMod.pipeweed, 1 + this.field_70146_Z.nextInt(2)), 0.0F);
            break;
         case 6:
         case 7:
         case 8:
            this.func_70099_a(new ItemStack(LOTRMod.mug), 0.0F);
            break;
         case 9:
            Item drink = LOTRFoods.HOBBIT_DRINK.getRandomFood(this.field_70146_Z).func_77973_b();
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
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBartender);
      if (type == LOTRTradeEntries.TradeType.SELL && itemstack.func_77973_b() == LOTRMod.pipeweedLeaf) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.sellPipeweedLeaf);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "hobbit/bartender/friendly" : "hobbit/bartender/hostile";
   }
}
