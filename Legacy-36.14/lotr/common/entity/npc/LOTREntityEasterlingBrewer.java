package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemKaftan;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingBrewer extends LOTREntityEasterlingMarketTrader {
   public LOTREntityEasterlingBrewer(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.RHUN_BREWER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.RHUN_BREWER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.mugAraq));
      int robeColor = 12346368;
      ItemStack body = new ItemStack(LOTRMod.bodyKaftan);
      ItemStack legs = new ItemStack(LOTRMod.legsKaftan);
      LOTRItemKaftan.setRobesColor(body, robeColor);
      LOTRItemKaftan.setRobesColor(legs, robeColor);
      this.func_70062_b(3, body);
      this.func_70062_b(2, legs);
      return data;
   }
}
