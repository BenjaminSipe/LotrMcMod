package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBreeMason extends LOTREntityBreeMarketTrader {
   public LOTREntityBreeMason(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.BREE_MASON_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.BREE_MASON_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151035_b));
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.cobblebrick, 1, 0));
      return data;
   }
}
