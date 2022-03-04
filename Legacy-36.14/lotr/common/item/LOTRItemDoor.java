package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRItemDoor extends ItemBlock {
   public LOTRItemDoor(Block block) {
      super(block);
      this.field_77777_bU = 1;
      this.func_77637_a(LOTRCreativeTabs.tabDeco);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (side != 1) {
         return false;
      } else {
         ++j;
         Block doorBlock = this.field_150939_a;
         if (entityplayer.func_82247_a(i, j, k, side, itemstack) && entityplayer.func_82247_a(i, j + 1, k, side, itemstack)) {
            if (!doorBlock.func_149742_c(world, i, j, k)) {
               return false;
            } else {
               int doorMeta = MathHelper.func_76128_c((double)((entityplayer.field_70177_z + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
               ItemDoor.func_150924_a(world, i, j, k, doorMeta, doorBlock);
               --itemstack.field_77994_a;
               return true;
            }
         } else {
            return false;
         }
      }
   }
}
