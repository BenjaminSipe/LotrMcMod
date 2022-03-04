package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBreeFarmer extends LOTREntityBreeMan implements LOTRTradeable, LOTRUnitTradeable {
   public LOTREntityBreeFarmer(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.BREE_FARMER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.BREE_FARMER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151019_K));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      LOTRItemLeatherHat.setHatColor(hat, 10390131);
      this.func_70062_b(4, hat);
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public LOTRInvasions getWarhorn() {
      return null;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      if (type == LOTRTradeEntries.TradeType.BUY && (itemstack.func_77973_b() == Items.field_151034_e || itemstack.func_77973_b() == LOTRMod.appleGreen)) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyAppleBreeFarmer);
      }

   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.BREE_FARMER;
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireBreeFarmer);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "bree/farmer/friendly" : "bree/farmer/hostile";
   }
}
