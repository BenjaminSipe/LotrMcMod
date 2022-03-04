package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWall5 extends LOTRBlockWallBase {
   public LOTRBlockWall5() {
      super(LOTRMod.brick, 5);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j == 0) {
         return LOTRMod.brick6.func_149691_a(i, 6);
      } else if (j == 1) {
         return LOTRMod.brick6.func_149691_a(i, 7);
      } else if (j == 2) {
         return LOTRMod.brick6.func_149691_a(i, 10);
      } else if (j == 3) {
         return LOTRMod.brick6.func_149691_a(i, 11);
      } else {
         return j == 4 ? LOTRMod.brick6.func_149691_a(i, 13) : super.func_149691_a(i, j);
      }
   }
}
