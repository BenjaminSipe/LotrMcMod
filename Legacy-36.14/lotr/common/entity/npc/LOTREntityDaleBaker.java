package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDaleBaker extends LOTREntityDaleMan implements LOTRTradeable {
   public LOTREntityDaleBaker(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcLocationName = "entity.lotr.DaleBaker.locationName";
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.DALE_BAKER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.DALE_BAKER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.rollingPin));
      this.npcItemsInv.setIdleItem(new ItemStack(Items.field_151025_P));
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int ingredients = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < ingredients; ++l) {
         if (this.field_70146_Z.nextBoolean()) {
            this.func_145779_a(Items.field_151015_O, 1);
         } else {
            this.func_145779_a(Items.field_151102_aT, 1);
         }
      }

   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDaleBaker);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "dale/baker/friendly" : "dale/baker/friendly";
      } else {
         return "dale/baker/hostile";
      }
   }
}
