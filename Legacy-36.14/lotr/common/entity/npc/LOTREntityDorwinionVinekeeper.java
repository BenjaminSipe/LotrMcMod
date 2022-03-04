package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionVinekeeper extends LOTREntityDorwinionMan implements LOTRTradeable, LOTRUnitTradeable {
   public LOTREntityDorwinionVinekeeper(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.DORWINION_VINEKEEPER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.DORWINION_VINEKEEPER_SELL;
   }

   protected EntityAIBase createDorwinionAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151019_K));
      if (this.field_70146_Z.nextBoolean()) {
         this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.grapeRed));
      } else {
         this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.grapeWhite));
      }

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
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.DORWINION_VINEKEEPER;
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireDorwinionVinekeeper);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "dorwinion/vinekeeper/friendly" : "dorwinion/vinekeeper/hostile";
   }
}
