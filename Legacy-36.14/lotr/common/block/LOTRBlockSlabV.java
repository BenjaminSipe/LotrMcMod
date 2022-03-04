package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabV extends LOTRBlockSlabBase {
   public LOTRBlockSlabV(boolean flag) {
      super(flag, Material.field_151576_e, 6);
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j == 0) {
         return Blocks.field_150417_aV.func_149691_a(i, 1);
      } else if (j == 1) {
         return Blocks.field_150417_aV.func_149691_a(i, 2);
      } else if (j == 2) {
         return LOTRMod.redBrick.func_149691_a(i, 0);
      } else if (j == 3) {
         return LOTRMod.redBrick.func_149691_a(i, 1);
      } else if (j == 4) {
         return Blocks.field_150341_Y.func_149691_a(i, 0);
      } else {
         return j == 5 ? Blocks.field_150348_b.func_149691_a(i, 0) : super.func_149691_a(i, j);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
