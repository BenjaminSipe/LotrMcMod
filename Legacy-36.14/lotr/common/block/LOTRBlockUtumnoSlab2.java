package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoSlab2 extends LOTRBlockUtumnoSlabBase {
   public LOTRBlockUtumnoSlab2(boolean flag) {
      super(flag, 3);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j == 0) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 6);
      } else if (j == 1) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 7);
      } else {
         return j == 2 ? LOTRMod.utumnoBrick.func_149691_a(i, 8) : super.func_149691_a(i, j);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
