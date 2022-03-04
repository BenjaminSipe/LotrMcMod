package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.block.LOTRBlockDoubleTorch;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemDoubleTorch extends Item {
   private Block torchBlock;

   public LOTRItemDoubleTorch(Block block) {
      this.func_77637_a(LOTRCreativeTabs.tabDeco);
      this.torchBlock = block;
      ((LOTRBlockDoubleTorch)this.torchBlock).torchItem = this;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      Block block = world.func_147439_a(i, j, k);
      if (block == Blocks.field_150431_aC) {
         side = 1;
      } else if (!block.isReplaceable(world, i, j, k)) {
         if (side == 0) {
            --j;
         }

         if (side == 1) {
            ++j;
         }

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
      }

      if (entityplayer.func_82247_a(i, j, k, side, itemstack) && entityplayer.func_82247_a(i, j + 1, k, side, itemstack)) {
         if (world.func_147472_a(block, i, j, k, false, side, (Entity)null, itemstack) && world.func_147472_a(block, i, j + 1, k, false, side, (Entity)null, itemstack)) {
            if (!LOTRBlockDoubleTorch.canPlaceTorchOn(world, i, j - 1, k)) {
               return false;
            } else {
               if (!world.field_72995_K) {
                  world.func_147465_d(i, j, k, this.torchBlock, 0, 2);
                  world.func_147465_d(i, j + 1, k, this.torchBlock, 1, 2);
                  SoundType stepSound = this.torchBlock.field_149762_H;
                  world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, stepSound.func_150496_b(), (stepSound.func_150497_c() + 1.0F) / 2.0F, stepSound.func_150494_d() * 0.8F);
                  --itemstack.field_77994_a;
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
