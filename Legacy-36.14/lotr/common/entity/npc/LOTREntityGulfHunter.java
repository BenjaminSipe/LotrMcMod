package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfHunter extends LOTREntityGulfTrader {
   public LOTREntityGulfHunter(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.GULF_HUNTER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.GULF_HUNTER_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.lionFur));
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsGemsbok));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsGemsbok));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyGemsbok));
      return data;
   }
}
