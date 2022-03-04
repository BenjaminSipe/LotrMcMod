package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNomadMason extends LOTREntityNomadTrader {
   public LOTREntityNomadMason(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.HARAD_MASON_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.HARAD_MASON_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeBronze));
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.brick, 1, 15));
      return data;
   }
}
