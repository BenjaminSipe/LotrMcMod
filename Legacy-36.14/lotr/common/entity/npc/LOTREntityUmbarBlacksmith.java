package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUmbarBlacksmith extends LOTREntityUmbarian implements LOTRTradeable.Smith {
   public LOTREntityUmbarBlacksmith(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.UMBAR_BLACKSMITH_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.UMBAR_BLACKSMITH_SELL;
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
      ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
      LOTRItemHaradRobes.setRobesColor(turban, 4462600);
      this.func_70062_b(4, turban);
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int ingots = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < ingots; ++l) {
         this.func_145779_a(Items.field_151042_j, 1);
      }

   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeUmbarBlacksmith);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "nearHarad/umbar/blacksmith/friendly" : "nearHarad/umbar/blacksmith/neutral";
      } else {
         return "nearHarad/umbar/blacksmith/hostile";
      }
   }
}
