package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanBaker extends LOTREntityRohanMarketTrader {
   public LOTREntityRohanBaker(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.ROHAN_BAKER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.ROHAN_BAKER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.rollingPin));
      this.npcItemsInv.setIdleItem(new ItemStack(Items.field_151025_P));
      return data;
   }
}
