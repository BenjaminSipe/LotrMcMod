package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorGreengrocer extends LOTREntityGondorMarketTrader {
   public LOTREntityGondorGreengrocer(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GONDOR_GREENGROCER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GONDOR_GREENGROCER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(3);
      if (i == 0) {
         this.npcItemsInv.setIdleItem(new ItemStack(Items.field_151034_e));
      } else if (i == 1) {
         this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.appleGreen));
      } else if (i == 2) {
         this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pear));
      }

      return data;
   }
}
