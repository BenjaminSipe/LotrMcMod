package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRItemArmorStand extends Item {
   public LOTRItemArmorStand() {
      this.func_77637_a(LOTRCreativeTabs.tabDeco);
      this.func_77625_d(1);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (world.field_72995_K) {
         return true;
      } else {
         i += Facing.field_71586_b[side];
         j += Facing.field_71587_c[side];
         k += Facing.field_71585_d[side];
         Block block = LOTRMod.armorStand;
         Block block1 = world.func_147439_a(i, j, k);
         Block block2 = world.func_147439_a(i, j + 1, k);
         if (block1 != null && !block1.isReplaceable(world, i, j, k) || block2 != null && !block2.isReplaceable(world, i, j + 1, k)) {
            return false;
         } else if (entityplayer.func_82247_a(i, j, k, side, itemstack) && entityplayer.func_82247_a(i, j + 1, k, side, itemstack)) {
            if (!block.func_149742_c(world, i, j, k)) {
               return false;
            } else {
               int l = MathHelper.func_76128_c((double)(entityplayer.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
               world.func_147465_d(i, j, k, block, l, 3);
               world.func_147465_d(i, j + 1, k, block, l | 4, 3);
               world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, block.field_149762_H.func_150496_b(), (block.field_149762_H.func_150497_c() + 1.0F) / 2.0F, block.field_149762_H.func_150494_d() * 0.8F);
               --itemstack.field_77994_a;
               return true;
            }
         } else {
            return false;
         }
      }
   }
}
