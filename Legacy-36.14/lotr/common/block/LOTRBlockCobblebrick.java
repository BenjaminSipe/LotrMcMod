package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockCobblebrick extends LOTRBlockBrickBase implements LOTRConnectedBlock {
   public LOTRBlockCobblebrick() {
      this.setBrickNames(new String[]{"cob"});
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.brickIcons = new IIcon[this.brickNames.length];

      for(int i = 0; i < this.brickNames.length; ++i) {
         LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      boolean[][] adjacentFlags;
      if (i != 0 && i != 1) {
         adjacentFlags = new boolean[][]{{false, true, false}, {false, true, false}, {false, true, false}};
      } else {
         adjacentFlags = new boolean[][]{{false, false, false}, {false, true, false}, {false, false, false}};
      }

      return LOTRConnectedTextures.getConnectedIconItem(this, j, adjacentFlags);
   }

   public String getConnectedName(int meta) {
      return this.field_149768_d + "_" + this.brickNames[meta];
   }

   public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
      return LOTRConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
   }
}
