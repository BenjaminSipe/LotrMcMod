package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemKaftan;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingMason extends LOTREntityEasterlingMarketTrader {
   public LOTREntityEasterlingMason(World world) {
      super(world);
   }

   public LOTRTradeEntries getBuyPool() {
      return LOTRTradeEntries.RHUN_MASON_BUY;
   }

   public LOTRTradeEntries getSellPool() {
      return LOTRTradeEntries.RHUN_MASON_SELL;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151035_b));
      this.npcItemsInv.setIdleItem(new ItemStack(Blocks.field_150348_b));
      int robeColor = 10855057;
      ItemStack body = new ItemStack(LOTRMod.bodyKaftan);
      ItemStack legs = new ItemStack(LOTRMod.legsKaftan);
      LOTRItemKaftan.setRobesColor(body, robeColor);
      LOTRItemKaftan.setRobesColor(legs, robeColor);
      this.func_70062_b(3, body);
      this.func_70062_b(2, legs);
      return data;
   }
}
