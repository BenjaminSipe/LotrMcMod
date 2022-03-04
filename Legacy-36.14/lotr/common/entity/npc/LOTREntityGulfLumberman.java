package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfLumberman extends LOTREntityGulfTrader {
   public LOTREntityGulfLumberman(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GULF_LUMBERMAN_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GULF_LUMBERMAN_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeBronze));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }
}
