package lotr.common.entity.npc;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorButcher extends LOTREntityGondorMarketTrader {
   public LOTREntityGondorButcher(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GONDOR_BUTCHER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GONDOR_BUTCHER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(Items.field_151147_al));
      return data;
   }
}
