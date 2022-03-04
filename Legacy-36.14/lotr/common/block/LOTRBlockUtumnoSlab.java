package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoSlab extends LOTRBlockUtumnoSlabBase {
   public LOTRBlockUtumnoSlab(boolean flag) {
      super(flag, 6);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j == 0) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 0);
      } else if (j == 1) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 2);
      } else if (j == 2) {
         return LOTRMod.utumnoBrick.func_149691_a(i, 4);
      } else if (j == 3) {
         return LOTRMod.utumnoPillar.func_149691_a(i, 0);
      } else if (j == 4) {
         return LOTRMod.utumnoPillar.func_149691_a(i, 1);
      } else {
         return j == 5 ? LOTRMod.utumnoPillar.func_149691_a(i, 2) : super.func_149691_a(i, j);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
