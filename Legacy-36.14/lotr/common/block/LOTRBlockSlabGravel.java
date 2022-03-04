package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabGravel extends LOTRBlockSlabFalling {
   public LOTRBlockSlabGravel(boolean flag) {
      super(flag, Material.field_151595_p, 3);
      this.func_149711_c(0.6F);
      this.func_149672_a(Block.field_149767_g);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j == 0) {
         return Blocks.field_150351_n.func_149691_a(i, 0);
      } else if (j == 1) {
         return LOTRMod.mordorGravel.func_149691_a(i, 0);
      } else {
         return j == 2 ? LOTRMod.obsidianGravel.func_149691_a(i, 0) : super.func_149691_a(i, j);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
