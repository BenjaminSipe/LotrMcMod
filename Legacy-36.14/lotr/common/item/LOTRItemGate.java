package lotr.common.item;

import lotr.common.block.LOTRBlockGate;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRItemGate extends ItemBlock {
   private LOTRBlockGate gateBlock;

   public LOTRItemGate(Block block) {
      super(block);
      this.gateBlock = (LOTRBlockGate)block;
   }

   public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int meta) {
      int yaw = MathHelper.func_76128_c((double)(entityplayer.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      float horizontalAngle = 40.0F;
      boolean lookingUp = entityplayer.field_70125_A < -horizontalAngle;
      boolean lookingDown = entityplayer.field_70125_A > horizontalAngle;
      boolean fullBlock = this.gateBlock.fullBlockGate;
      if (side != 0 && side != 1) {
         if (!lookingUp && !lookingDown) {
            int dir = Direction.field_71579_d[side];
            if (fullBlock) {
               meta = Direction.field_71582_c[Direction.field_71580_e[dir]];
            } else {
               meta = Direction.field_71582_c[Direction.field_71578_g[dir]];
            }
         } else if (fullBlock) {
            if (entityplayer.field_70125_A > 0.0F) {
               meta = 0;
            } else {
               meta = 1;
            }
         } else if (f1 > 0.5F) {
            meta = 0;
         } else {
            meta = 1;
         }
      } else {
         meta = Direction.field_71582_c[yaw];
      }

      return super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, meta);
   }
}
