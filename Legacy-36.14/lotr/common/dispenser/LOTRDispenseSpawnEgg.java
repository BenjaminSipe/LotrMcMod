package lotr.common.dispenser;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemSpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class LOTRDispenseSpawnEgg extends BehaviorDefaultDispenseItem {
   public ItemStack func_82487_b(IBlockSource dispenser, ItemStack itemstack) {
      EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.func_82620_h());
      double d = dispenser.func_82615_a() + (double)enumfacing.func_82601_c();
      double d1 = (double)dispenser.func_82622_e() + 0.2D;
      double d2 = dispenser.func_82616_c() + (double)enumfacing.func_82599_e();
      Entity entity = LOTRItemSpawnEgg.spawnCreature(dispenser.func_82618_k(), itemstack.func_77960_j(), d, d1, d2);
      if (entity instanceof EntityLiving && itemstack.func_82837_s()) {
         ((EntityLiving)entity).func_94058_c(itemstack.func_82833_r());
      }

      if (entity instanceof LOTREntityNPC) {
         ((LOTREntityNPC)entity).setPersistentAndTraderShouldRespawn();
      }

      itemstack.func_77979_a(1);
      return itemstack;
   }
}
