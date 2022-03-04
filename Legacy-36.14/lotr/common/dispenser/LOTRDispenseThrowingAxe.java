package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class LOTRDispenseThrowingAxe extends BehaviorDefaultDispenseItem {
   public ItemStack func_82487_b(IBlockSource dispenser, ItemStack itemstack) {
      World world = dispenser.func_82618_k();
      IPosition iposition = BlockDispenser.func_149939_a(dispenser);
      EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.func_82620_h());
      LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(world, itemstack.func_77946_l(), iposition.func_82615_a(), iposition.func_82617_b(), iposition.func_82616_c());
      axe.func_70186_c((double)enumfacing.func_82601_c(), (double)((float)enumfacing.func_96559_d() + 0.1F), (double)enumfacing.func_82599_e(), 1.1F, 6.0F);
      axe.canBePickedUp = 1;
      world.func_72838_d(axe);
      itemstack.func_77979_a(1);
      return itemstack;
   }

   protected void func_82485_a(IBlockSource dispenser) {
      dispenser.func_82618_k().func_72926_e(1002, dispenser.func_82623_d(), dispenser.func_82622_e(), dispenser.func_82621_f(), 0);
   }
}
