package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanBrewer extends LOTREntityRohanMarketTrader {
   public LOTREntityRohanBrewer(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.ROHAN_BREWER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.ROHAN_BREWER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.mugMead));
      return data;
   }
}
