package lotr.common.entity.npc;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUmbarMiner extends LOTREntityUmbarTrader {
   public LOTREntityUmbarMiner(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.HARAD_MINER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.HARAD_MINER_SELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151035_b));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }
}
