package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTRItemWaterPlant extends ItemBlock {
   public LOTRItemWaterPlant(Block block) {
      super(block);
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      return tryPlaceWaterPlant(this, itemstack, world, entityplayer, this.func_77621_a(world, entityplayer, true));
   }

   public static ItemStack tryPlaceWaterPlant(ItemBlock itemblock, ItemStack itemstack, World world, EntityPlayer entityplayer, MovingObjectPosition targetBlock) {
      if (targetBlock == null) {
         return itemstack;
      } else {
         if (targetBlock.field_72313_a == MovingObjectType.BLOCK) {
            int i = targetBlock.field_72311_b;
            int j = targetBlock.field_72312_c;
            int k = targetBlock.field_72309_d;
            if (!world.func_72962_a(entityplayer, i, j, k)) {
               return itemstack;
            }

            if (!entityplayer.func_82247_a(i, j, k, targetBlock.field_72310_e, itemstack)) {
               return itemstack;
            }

            Block block = itemblock.field_150939_a;
            int meta = itemblock.func_77647_b(itemstack.func_77960_j());
            if (world.func_147439_a(i, j, k).func_149688_o() == Material.field_151586_h && world.func_72805_g(i, j, k) == 0 && world.func_147437_c(i, j + 1, k) && block.func_149742_c(world, i, j + 1, k) && itemblock.placeBlockAt(itemstack, entityplayer, world, i, j + 1, k, 1, 0.5F, 0.5F, 0.5F, meta)) {
               SoundType stepsound = block.field_149762_H;
               world.func_72908_a((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), stepsound.func_150496_b(), (stepsound.func_150497_c() + 1.0F) / 2.0F, stepsound.func_150494_d() * 0.8F);
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  --itemstack.field_77994_a;
               }
            }
         }

         return itemstack;
      }
   }
}
