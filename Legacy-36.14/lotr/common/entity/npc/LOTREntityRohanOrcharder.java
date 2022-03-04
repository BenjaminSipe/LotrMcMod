package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanOrcharder extends LOTREntityRohanMarketTrader {
   public LOTREntityRohanOrcharder(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.ROHAN_ORCHARDER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.ROHAN_ORCHARDER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextBoolean()) {
         this.npcItemsInv.setIdleItem(new ItemStack(Items.field_151034_e));
      } else {
         this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.appleGreen));
      }

      return data;
   }
}
