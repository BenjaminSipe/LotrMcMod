package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class LOTRItemChisel extends Item {
   private Block signBlock;

   public LOTRItemChisel(Block block) {
      this.signBlock = block;
      this.func_77637_a(LOTRCreativeTabs.tabTools);
      this.func_77625_d(1);
      this.func_77656_e(100);
      this.func_77664_n();
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (side != 0 && side != 1) {
         Block block = world.func_147439_a(i, j, k);
         Material mt = block.func_149688_o();
         if (block.func_149662_c() && (mt == Material.field_151576_e || mt == Material.field_151575_d || mt == Material.field_151573_f)) {
            i += Facing.field_71586_b[side];
            j += Facing.field_71587_c[side];
            k += Facing.field_71585_d[side];
            if (!entityplayer.func_82247_a(i, j, k, side, itemstack)) {
               return false;
            } else if (!this.signBlock.func_149742_c(world, i, j, k)) {
               return false;
            } else {
               if (!world.field_72995_K) {
                  world.func_147465_d(i, j, k, this.signBlock, side, 3);
                  itemstack.func_77972_a(1, entityplayer);
                  LOTRTileEntitySign sign = (LOTRTileEntitySign)world.func_147438_o(i, j, k);
                  if (sign != null) {
                     sign.openEditGUI((EntityPlayerMP)entityplayer);
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
