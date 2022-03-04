package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemMatch extends Item {
   public LOTRItemMatch() {
      this.func_77664_n();
      this.func_77637_a(LOTRCreativeTabs.tabTools);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      ItemStack proxyItem = new ItemStack(Items.field_151033_d);
      if (proxyItem.func_77943_a(entityplayer, world, i, j, k, side, f, f1, f2)) {
         --itemstack.field_77994_a;
         return true;
      } else {
         return false;
      }
   }
}
