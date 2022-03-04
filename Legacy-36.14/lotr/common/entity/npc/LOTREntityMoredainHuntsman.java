package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMoredainHuntsman extends LOTREntityMoredainVillageTrader {
   public LOTREntityMoredainHuntsman(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.MOREDAIN_HUNTSMAN_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.MOREDAIN_HUNTSMAN_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearMoredain));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setSpearBackup((ItemStack)null);
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsMoredain));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsMoredain));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyMoredain));
      return data;
   }
}
