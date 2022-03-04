package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingBlacksmith extends LOTREntityEasterling implements LOTRTradeable.Smith {
   public LOTREntityEasterlingBlacksmith(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.RHUN_BLACKSMITH_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.RHUN_BLACKSMITH_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.blacksmithHammer));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int ingots = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < ingots; ++l) {
         if (this.field_70146_Z.nextBoolean()) {
            this.func_145779_a(Items.field_151042_j, 1);
         } else {
            this.func_145779_a(LOTRMod.gildedIron, 1);
         }
      }

   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRhunBlacksmith);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "rhun/blacksmith/friendly" : "rhun/blacksmith/neutral";
      } else {
         return "rhun/blacksmith/hostile";
      }
   }
}
