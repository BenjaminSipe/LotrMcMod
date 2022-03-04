package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemMechanism extends Item {
   public LOTRItemMechanism() {
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      Block block = world.func_147439_a(i, j, k);
      if (block == Blocks.field_150448_aq) {
         int meta = world.func_72805_g(i, j, k);
         if (meta >= 0 && meta <= 5) {
            Block setBlock = LOTRMod.mechanisedRailOn;
            world.func_147465_d(i, j, k, setBlock, meta | 8, 3);
            SoundType sound = setBlock.field_149762_H;
            world.func_72908_a((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), sound.func_150496_b(), (sound.func_150497_c() + 1.0F) / 2.0F, sound.func_150494_d() * 0.8F);
            --itemstack.field_77994_a;
            return true;
         }
      }

      return false;
   }
}
