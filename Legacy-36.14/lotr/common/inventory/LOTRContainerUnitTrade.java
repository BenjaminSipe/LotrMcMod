package lotr.common.inventory;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHireableBase;
import lotr.common.entity.npc.LOTRUnitTradeable;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRContainerUnitTrade extends Container {
   public LOTRHireableBase theUnitTrader;
   public LOTREntityNPC theLivingTrader;
   private LOTRFaction traderFaction;
   private World theWorld;
   private IInventory alignmentRewardInv;
   public int alignmentRewardSlots;

   public LOTRContainerUnitTrade(EntityPlayer entityplayer, LOTRHireableBase trader, World world) {
      this.theUnitTrader = trader;
      this.theLivingTrader = (LOTREntityNPC)this.theUnitTrader;
      this.traderFaction = this.theLivingTrader.getFaction();
      this.theWorld = world;
      ItemStack reward = null;
      if (this.theUnitTrader instanceof LOTRUnitTradeable) {
         LOTRInvasions conquestType = ((LOTRUnitTradeable)this.theUnitTrader).getWarhorn();
         reward = conquestType == null ? null : conquestType.createConquestHorn();
      }

      boolean hasReward = reward != null;
      this.alignmentRewardSlots = hasReward ? 1 : 0;
      this.alignmentRewardInv = new InventoryBasic("specialItem", false, this.alignmentRewardSlots);
      if (hasReward) {
         this.func_75146_a(new LOTRSlotAlignmentReward(this, this.alignmentRewardInv, 0, 174, 78, this.theUnitTrader, reward.func_77946_l()));
         if (!world.field_72995_K && LOTRLevelData.getData(entityplayer).getAlignment(this.traderFaction) >= 1500.0F) {
            this.alignmentRewardInv.func_70299_a(0, reward.func_77946_l());
         }
      }

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(entityplayer.field_71071_by, j + i * 9 + 9, 30 + j * 18, 174 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.func_75146_a(new Slot(entityplayer.field_71071_by, i, 30 + i * 18, 232));
      }

   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return this.theLivingTrader != null && (double)entityplayer.func_70032_d(this.theLivingTrader) <= 12.0D && this.theLivingTrader.func_70089_S() && this.theLivingTrader.func_70638_az() == null && this.theUnitTrader.canTradeWith(entityplayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityplayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i >= 0 && i < this.alignmentRewardSlots) {
            if (!this.func_75135_a(itemstack1, this.alignmentRewardSlots, 36 + this.alignmentRewardSlots, true)) {
               return null;
            }
         } else if (i >= this.alignmentRewardSlots && i < 27 + this.alignmentRewardSlots) {
            if (!this.func_75135_a(itemstack1, 27 + this.alignmentRewardSlots, 36 + this.alignmentRewardSlots, false)) {
               return null;
            }
         } else if (i >= 27 + this.alignmentRewardSlots && i < 36 + this.alignmentRewardSlots) {
            if (!this.func_75135_a(itemstack1, this.alignmentRewardSlots, 27 + this.alignmentRewardSlots, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, this.alignmentRewardSlots, 27 + this.alignmentRewardSlots, false)) {
            return null;
         }

         if (itemstack1.field_77994_a == 0) {
            slot.func_75215_d((ItemStack)null);
         } else {
            slot.func_75218_e();
         }

         if (itemstack1.field_77994_a == itemstack.field_77994_a) {
            return null;
         }

         slot.func_82870_a(entityplayer, itemstack1);
      }

      return itemstack;
   }
}
