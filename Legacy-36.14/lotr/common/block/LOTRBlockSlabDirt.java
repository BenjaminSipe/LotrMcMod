package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabDirt extends LOTRBlockSlabBase {
   public LOTRBlockSlabDirt(boolean flag) {
      super(flag, Material.field_151578_c, 5);
      this.func_149711_c(0.5F);
      this.func_149672_a(Block.field_149767_g);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j == 0) {
         return Blocks.field_150346_d.func_149691_a(i, 0);
      } else if (j == 1) {
         return LOTRMod.dirtPath.func_149691_a(i, 0);
      } else if (j == 2) {
         return LOTRMod.mud.func_149691_a(i, 0);
      } else if (j == 3) {
         return LOTRMod.mordorDirt.func_149691_a(i, 0);
      } else {
         return j == 4 ? LOTRMod.dirtPath.func_149691_a(i, 1) : super.func_149691_a(i, j);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
