package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemStew extends ItemFood {
   public LOTRItemStew(int j, float f, boolean flag) {
      super(j, f, flag);
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabFood);
      this.func_77642_a(Items.field_151054_z);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      super.func_77654_b(itemstack, world, entityplayer);
      return new ItemStack(Items.field_151054_z);
   }
}
