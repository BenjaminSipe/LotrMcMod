package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWall4 extends LOTRBlockWallBase {
   public LOTRBlockWall4() {
      super(LOTRMod.brick, 16);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j == 0) {
         return LOTRMod.brick4.func_149691_a(i, 0);
      } else if (j == 1) {
         return LOTRMod.brick4.func_149691_a(i, 1);
      } else if (j == 2) {
         return LOTRMod.brick4.func_149691_a(i, 2);
      } else if (j == 3) {
         return LOTRMod.brick4.func_149691_a(i, 3);
      } else if (j == 4) {
         return LOTRMod.brick4.func_149691_a(i, 4);
      } else if (j == 5) {
         return LOTRMod.brick4.func_149691_a(i, 5);
      } else if (j == 6) {
         return LOTRMod.brick4.func_149691_a(i, 14);
      } else if (j == 7) {
         return LOTRMod.brick5.func_149691_a(i, 8);
      } else if (j == 8) {
         return LOTRMod.brick5.func_149691_a(i, 9);
      } else if (j == 9) {
         return LOTRMod.brick5.func_149691_a(i, 10);
      } else if (j == 10) {
         return LOTRMod.brick5.func_149691_a(i, 13);
      } else if (j == 11) {
         return LOTRMod.brick5.func_149691_a(i, 14);
      } else if (j == 12) {
         return LOTRMod.brick5.func_149691_a(i, 15);
      } else if (j == 13) {
         return LOTRMod.brick6.func_149691_a(i, 1);
      } else if (j == 14) {
         return LOTRMod.brick6.func_149691_a(i, 3);
      } else {
         return j == 15 ? LOTRMod.brick6.func_149691_a(i, 4) : super.func_149691_a(i, j);
      }
   }
}
