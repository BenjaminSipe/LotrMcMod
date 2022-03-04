package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRDispenseCrossbowBolt extends BehaviorProjectileDispense {
   private Item theBoltItem;

   public LOTRDispenseCrossbowBolt(Item item) {
      this.theBoltItem = item;
   }

   protected IProjectile func_82499_a(World world, IPosition iposition) {
      ItemStack itemstack = new ItemStack(this.theBoltItem);
      LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(world, itemstack, iposition.func_82615_a(), iposition.func_82617_b(), iposition.func_82616_c());
      bolt.canBePickedUp = 1;
      return bolt;
   }

   protected void func_82485_a(IBlockSource source) {
      source.func_82618_k().func_72908_a((double)source.func_82623_d(), (double)source.func_82622_e(), (double)source.func_82621_f(), "lotr:item.crossbow", 1.0F, 1.2F);
   }
}
