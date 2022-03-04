package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWallBone extends LOTRBlockWallBase {
   public LOTRBlockWallBone() {
      super(LOTRMod.boneBlock, 1);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return j == 0 ? LOTRMod.boneBlock.func_149691_a(i, 0) : super.func_149691_a(i, j);
   }
}
