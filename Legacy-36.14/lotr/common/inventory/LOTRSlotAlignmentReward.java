package lotr.common.inventory;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHireableBase;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class LOTRSlotAlignmentReward extends LOTRSlotProtected {
   public static int REWARD_COST = 2000;
   private LOTRContainerUnitTrade theContainer;
   private LOTRHireableBase theTrader;
   private LOTREntityNPC theLivingTrader;
   private ItemStack alignmentReward;

   public LOTRSlotAlignmentReward(LOTRContainerUnitTrade container, IInventory inv, int i, int j, int k, LOTRHireableBase entity, ItemStack item) {
      super(inv, i, j, k);
      this.theContainer = container;
      this.theTrader = entity;
      this.theLivingTrader = (LOTREntityNPC)this.theTrader;
      this.alignmentReward = item.func_77946_l();
   }

   public boolean func_82869_a(EntityPlayer entityplayer) {
      if (LOTRLevelData.getData(entityplayer).getAlignment(this.theTrader.getFaction()) < 1500.0F) {
         return false;
      } else {
         int coins = LOTRItemCoin.getInventoryValue(entityplayer, false);
         return coins < REWARD_COST ? false : super.func_82869_a(entityplayer);
      }
   }

   public void func_82870_a(EntityPlayer entityplayer, ItemStack itemstack) {
      LOTRFaction faction = this.theLivingTrader.getFaction();
      if (!entityplayer.field_70170_p.field_72995_K) {
         LOTRItemCoin.takeCoins(REWARD_COST, entityplayer);
         LOTRLevelData.getData(entityplayer).getFactionData(faction).takeConquestHorn();
         this.theLivingTrader.playTradeSound();
      }

      super.func_82870_a(entityplayer, itemstack);
      if (!entityplayer.field_70170_p.field_72995_K) {
         ItemStack reward = this.alignmentReward.func_77946_l();
         this.func_75215_d(reward);
         ((EntityPlayerMP)entityplayer).func_71120_a(this.theContainer);
      }

   }
}
