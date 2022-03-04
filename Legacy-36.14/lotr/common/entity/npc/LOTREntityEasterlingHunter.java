package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingHunter extends LOTREntityEasterlingMarketTrader {
   public LOTREntityEasterlingHunter(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.RHUN_HUNTER_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.RHUN_HUNTER_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBronze));
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.fur));
      this.func_70062_b(1, new ItemStack(Items.field_151021_T));
      this.func_70062_b(2, new ItemStack(Items.field_151026_S));
      this.func_70062_b(3, new ItemStack(Items.field_151027_R));
      return data;
   }
}
