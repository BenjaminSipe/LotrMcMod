package lotr.common.dispenser;

import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class LOTRDispenseOrcBomb extends BehaviorDefaultDispenseItem {
   protected ItemStack func_82487_b(IBlockSource dispenser, ItemStack itemstack) {
      EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.func_82620_h());
      World world = dispenser.func_82618_k();
      int i = dispenser.func_82623_d() + enumfacing.func_82601_c();
      int j = dispenser.func_82622_e() + enumfacing.func_96559_d();
      int k = dispenser.func_82621_f() + enumfacing.func_82599_e();
      LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), (EntityLivingBase)null);
      bomb.field_70516_a += itemstack.func_77960_j() * 10;
      bomb.setBombStrengthLevel(itemstack.func_77960_j());
      bomb.droppedByPlayer = true;
      world.func_72838_d(bomb);
      --itemstack.field_77994_a;
      return itemstack;
   }
}
