package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfBrewer extends LOTREntityGulfTrader {
   public LOTREntityGulfBrewer(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.HARAD_BREWER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.HARAD_BREWER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      ItemStack drink = new ItemStack(LOTRMod.mugAraq);
      LOTRItemMug.setVessel(drink, this.getHaradrimDrinks().getRandomVessel(this.field_70146_Z), true);
      this.npcItemsInv.setIdleItem(drink);
      return data;
   }
}
