package lotr.common.dispenser;

import lotr.common.block.LOTRBlockRhunFireJar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class LOTRDispenseRhunFireJar extends BehaviorDefaultDispenseItem {
   private final BehaviorDefaultDispenseItem dispenseDefault = new BehaviorDefaultDispenseItem();

   protected ItemStack func_82487_b(IBlockSource dispenser, ItemStack itemstack) {
      EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.func_82620_h());
      World world = dispenser.func_82618_k();
      int i = dispenser.func_82623_d() + enumfacing.func_82601_c();
      int j = dispenser.func_82622_e() + enumfacing.func_96559_d();
      int k = dispenser.func_82621_f() + enumfacing.func_82599_e();
      if (world.func_147439_a(i, j, k).isReplaceable(world, i, j, k)) {
         LOTRBlockRhunFireJar.explodeOnAdded = false;
         world.func_147465_d(i, j, k, Block.func_149634_a(itemstack.func_77973_b()), itemstack.func_77960_j(), 3);
         LOTRBlockRhunFireJar.explodeOnAdded = true;
         --itemstack.field_77994_a;
         return itemstack;
      } else {
         return this.dispenseDefault.func_82482_a(dispenser, itemstack);
      }
   }
}
