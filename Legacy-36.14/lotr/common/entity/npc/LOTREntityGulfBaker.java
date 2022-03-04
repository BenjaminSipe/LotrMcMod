package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfBaker extends LOTREntityGulfTrader {
   public LOTREntityGulfBaker(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GULF_BAKER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GULF_BAKER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.rollingPin));
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.oliveBread));
      return data;
   }
}
