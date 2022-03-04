package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemHangingFruit extends LOTRItemFood {
   private Block fruitBlock;

   public LOTRItemHangingFruit(int i, float f, boolean flag, Block block) {
      super(i, f, flag);
      this.fruitBlock = block;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ) {
      if (world.func_147439_a(i, j, k).isWood(world, i, j, k)) {
         if (side != 0 && side != 1) {
            if (side == 2) {
               --k;
            }

            if (side == 3) {
               ++k;
            }

            if (side == 4) {
               --i;
            }

            if (side == 5) {
               ++i;
            }

            if (world.func_147437_c(i, j, k)) {
               int meta = ForgeDirection.getOrientation(side - 2).getOpposite().ordinal();
               world.func_147465_d(i, j, k, this.fruitBlock, meta, 3);
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  --itemstack.field_77994_a;
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
