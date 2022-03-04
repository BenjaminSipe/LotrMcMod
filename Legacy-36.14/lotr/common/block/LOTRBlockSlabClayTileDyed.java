package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabClayTileDyed extends LOTRBlockSlabBase {
   public LOTRBlockSlabClayTileDyed(boolean flag) {
      super(flag, Material.field_151576_e, 8);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(1.25F);
      this.func_149752_b(7.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      return LOTRMod.clayTileDyed.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
