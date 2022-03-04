package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityRivendellTrader extends LOTREntityRivendellElf implements LOTRTravellingTrader {
   public LOTREntityRivendellTrader(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAttackOnCollide(this, 1.6D, false));
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.RIVENDELL_TRADER;
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.RIVENDELL_TRADER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.RIVENDELL_TRADER_SELL;
   }

   public LOTREntityNPC createTravellingEscort() {
      return new LOTREntityRivendellElf(this.field_70170_p);
   }

   public String getDepartureSpeech() {
      return "rivendell/trader/departure";
   }

   public int func_70658_aO() {
      return 10;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.func_70089_S() && this.travellingTraderInfo.timeUntilDespawn == 0) {
         this.field_70170_p.func_72960_a(this, (byte)15);
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 5 + this.field_70146_Z.nextInt(3);
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72960_a(this, (byte)15);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b != 15) {
         super.func_70103_a(b);
      }

   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 75.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRivendellTrader);
   }

   public boolean shouldRenderNPCHair() {
      return false;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "rivendell/trader/friendly" : "rivendell/trader/neutral";
      } else {
         return "rivendell/trader/hostile";
      }
   }
}
