package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab13 extends LOTRBlockSlabBase {
   public LOTRBlockSlab13(boolean flag) {
      super(flag, Material.field_151576_e, 8);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j == 0) {
         return LOTRMod.brick6.func_149691_a(i, 3);
      } else if (j == 1) {
         return LOTRMod.brick6.func_149691_a(i, 4);
      } else if (j == 2) {
         return LOTRMod.brick6.func_149691_a(i, 6);
      } else if (j == 3) {
         return LOTRMod.brick6.func_149691_a(i, 7);
      } else if (j == 4) {
         return LOTRMod.pillar2.func_149691_a(i, 10);
      } else if (j == 5) {
         return LOTRMod.pillar2.func_149691_a(i, 11);
      } else if (j == 6) {
         return LOTRMod.pillar2.func_149691_a(i, 12);
      } else {
         return j == 7 ? LOTRMod.pillar2.func_149691_a(i, 13) : super.func_149691_a(i, j);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
