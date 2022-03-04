package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockWallV extends LOTRBlockWallBase {
   public LOTRBlockWallV() {
      super(Blocks.field_150417_aV, 9);
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j == 0) {
         return Blocks.field_150348_b.func_149691_a(i, 0);
      } else if (j == 1) {
         return Blocks.field_150417_aV.func_149691_a(i, 0);
      } else if (j == 2) {
         return Blocks.field_150417_aV.func_149691_a(i, 1);
      } else if (j == 3) {
         return Blocks.field_150417_aV.func_149691_a(i, 2);
      } else if (j == 4) {
         return Blocks.field_150322_A.func_149691_a(i, 0);
      } else if (j == 5) {
         return LOTRMod.redSandstone.func_149691_a(i, 0);
      } else if (j == 6) {
         return Blocks.field_150336_V.func_149691_a(i, 0);
      } else if (j == 7) {
         return LOTRMod.redBrick.func_149691_a(i, 0);
      } else {
         return j == 8 ? LOTRMod.redBrick.func_149691_a(i, 1) : super.func_149691_a(i, j);
      }
   }
}
