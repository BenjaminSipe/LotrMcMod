package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityGaladhrimTrader extends LOTREntityGaladhrimElf implements LOTRTravellingTrader {
   public LOTREntityGaladhrimTrader(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAttackOnCollide(this, 1.6D, false));
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.GALADHRIM_TRADER;
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GALADHRIM_TRADER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GALADHRIM_TRADER_SELL;
   }

   public LOTREntityNPC createTravellingEscort() {
      return new LOTREntityGaladhrimElf(this.field_70170_p);
   }

   public String getDepartureSpeech() {
      return "galadhrim/trader/departure";
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
      if (b == 15) {
         for(int i = 0; i < 16; ++i) {
            double d = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N;
            double d1 = this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O;
            double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N;
            double d3 = -0.2D + (double)(this.field_70146_Z.nextFloat() * 0.4F);
            double d4 = -0.2D + (double)(this.field_70146_Z.nextFloat() * 0.4F);
            double d5 = -0.2D + (double)(this.field_70146_Z.nextFloat() * 0.4F);
            LOTRMod.proxy.spawnParticle("leafGold_" + (30 + this.field_70146_Z.nextInt(30)), d, d1, d2, d3, d4, d5);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 75.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeElvenTrader);
   }

   public boolean shouldRenderNPCHair() {
      return false;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "galadhrim/trader/friendly" : "galadhrim/trader/neutral";
      } else {
         return "galadhrim/trader/hostile";
      }
   }
}
