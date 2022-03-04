package lotr.common.block;

import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityDwarvenForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockDwarvenForge extends LOTRBlockForgeBase {
   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityDwarvenForge();
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (!world.field_72995_K) {
         entityplayer.openGui(LOTRMod.instance, 5, world, i, j, k);
      }

      return true;
   }

   protected boolean useLargeSmoke() {
      return true;
   }
}
