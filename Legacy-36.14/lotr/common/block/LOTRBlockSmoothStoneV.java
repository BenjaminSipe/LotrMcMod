package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockSmoothStoneV extends LOTRBlockSmoothStoneBase {
   public LOTRBlockSmoothStoneV() {
      this.setBrickNames(new String[]{"stone"});
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return j == 0 ? Blocks.field_150333_U.func_149691_a(i, 0) : super.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
