package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab14 extends LOTRBlockSlabBase {
   public LOTRBlockSlab14(boolean flag) {
      super(flag, Material.field_151576_e, 5);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j == 0) {
         return LOTRMod.pillar2.func_149691_a(i, 14);
      } else if (j == 1) {
         return LOTRMod.brick6.func_149691_a(i, 11);
      } else if (j == 2) {
         return LOTRMod.brick6.func_149691_a(i, 13);
      } else if (j == 3) {
         return LOTRMod.pillar2.func_149691_a(i, 15);
      } else {
         return j == 4 ? LOTRMod.pillar3.func_149691_a(i, 0) : super.func_149691_a(i, j);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
