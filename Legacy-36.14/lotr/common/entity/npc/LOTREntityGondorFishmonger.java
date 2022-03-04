package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorFishmonger extends LOTREntityGondorMarketTrader {
   public LOTREntityGondorFishmonger(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GONDOR_FISHMONGER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GONDOR_FISHMONGER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(Items.field_151112_aM));
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      LOTRItemLeatherHat.setHatColor(hat, 9013900);
      this.func_70062_b(4, hat);
      return data;
   }
}
