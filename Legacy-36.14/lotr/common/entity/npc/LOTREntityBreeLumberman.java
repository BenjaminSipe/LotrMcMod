package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBreeLumberman extends LOTREntityBreeMarketTrader {
   public LOTREntityBreeLumberman(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.BREE_LUMBERMAN_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.BREE_LUMBERMAN_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151036_c));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      LOTRItemLeatherHat.setHatColor(hat, 6834742);
      LOTRItemLeatherHat.setFeatherColor(hat, 3916082);
      this.func_70062_b(4, hat);
      return data;
   }
}
