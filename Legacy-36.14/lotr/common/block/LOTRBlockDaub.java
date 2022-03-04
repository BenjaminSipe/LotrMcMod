package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockDaub extends Block implements LOTRConnectedBlock {
   public LOTRBlockDaub() {
      super(Material.field_151577_b);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(1.0F);
      this.func_149672_a(Block.field_149779_h);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      LOTRConnectedTextures.registerConnectedIcons(iconregister, this, 0, false);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return LOTRConnectedTextures.getConnectedIconItem(this, j);
   }

   public String getConnectedName(int meta) {
      return this.field_149768_d;
   }

   public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
      int meta = world.func_72805_g(i, j, k);
      Block otherBlock = world.func_147439_a(i1, j1, k1);
      int otherMeta = world.func_72805_g(i1, j1, k1);
      return otherBlock == this && otherMeta == meta;
   }
}
