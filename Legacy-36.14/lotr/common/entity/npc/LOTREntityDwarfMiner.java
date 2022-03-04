package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDwarfMiner extends LOTREntityDwarf implements LOTRTradeable {
   public LOTREntityDwarfMiner(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.DWARF_MINER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.DWARF_MINER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeDwarven));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   protected boolean canDwarfSpawnAboveGround() {
      return false;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDwarfMiner);
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      if (flag) {
         if (this.field_70146_Z.nextInt(4) == 0) {
            this.dropChestContents(LOTRChestContents.DWARVEN_MINE_CORRIDOR, 1, 2 + i);
         }

         if (this.field_70146_Z.nextInt(15) == 0) {
            this.func_70099_a(new ItemStack(LOTRMod.mithrilNugget), 0.0F);
         }
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "dwarf/miner/friendly" : "dwarf/miner/neutral";
      } else {
         return "dwarf/dwarf/hostile";
      }
   }
}
