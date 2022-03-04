package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.block.LOTRBlockBed;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemBed extends Item {
   private LOTRBlockBed theBedBlock;

   public LOTRItemBed(Block block) {
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabUtil);
      this.theBedBlock = (LOTRBlockBed)block;
      this.theBedBlock.bedItem = this;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (world.field_72995_K) {
         return true;
      } else if (side != 1) {
         return false;
      } else {
         ++j;
         int i1 = MathHelper.func_76128_c((double)(entityplayer.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
         byte b0 = 0;
         byte b1 = 0;
         if (i1 == 0) {
            b1 = 1;
         }

         if (i1 == 1) {
            b0 = -1;
         }

         if (i1 == 2) {
            b1 = -1;
         }

         if (i1 == 3) {
            b0 = 1;
         }

         if (entityplayer.func_82247_a(i, j, k, side, itemstack) && entityplayer.func_82247_a(i + b0, j, k + b1, side, itemstack)) {
            if (world.func_147437_c(i, j, k) && world.func_147437_c(i + b0, j, k + b1) && world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && world.func_147439_a(i + b0, j - 1, k + b1).isSideSolid(world, i + b0, j - 1, k + b1, ForgeDirection.UP)) {
               world.func_147465_d(i, j, k, this.theBedBlock, i1, 3);
               if (world.func_147439_a(i, j, k) == this.theBedBlock) {
                  world.func_147465_d(i + b0, j, k + b1, this.theBedBlock, i1 + 8, 3);
               }

               world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, this.theBedBlock.field_149762_H.func_150496_b(), (this.theBedBlock.field_149762_H.func_150497_c() + 1.0F) / 2.0F, this.theBedBlock.field_149762_H.func_150494_d() * 0.8F);
               --itemstack.field_77994_a;
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }
}
