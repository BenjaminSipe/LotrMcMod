package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoWall extends LOTRBlockWallBase implements LOTRWorldProviderUtumno.UtumnoBlock {
   public LOTRBlockUtumnoWall() {
      super(LOTRMod.utumnoBrick, 6);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j == 0) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 0);
      } else if (j == 1) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 2);
      } else if (j == 2) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 4);
      } else if (j == 3) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 6);
      } else if (j == 4) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 7);
      } else {
         return j == 5 ? LOTRMod.utumnoBrick.func_149691_a(i, 8) : super.func_149691_a(i, j);
      }
   }
}
