package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockBrick extends LOTRBlockBrickBase implements LOTRConnectedBlock {
   @SideOnly(Side.CLIENT)
   private IIcon iconRohanSide;

   public LOTRBlockBrick() {
      this.setBrickNames(new String[]{"mordor", "gondor", "gondorMossy", "gondorCracked", "rohan", "gondorCarved", "dwarven", "mordorCracked", "dwarvenSilver", "dwarvenGold", "dwarvenMithril", "galadhrim", "galadhrimMossy", "galadhrimCracked", "blueRock", "nearHarad"});
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.brickIcons = new IIcon[this.brickNames.length];

      for(int i = 0; i < this.brickNames.length; ++i) {
         if (i != 8 && i != 9 && i != 10) {
            this.brickIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.brickNames[i]);
            if (i == 4) {
               this.iconRohanSide = iconregister.func_94245_a(this.func_149641_N() + "_" + this.brickNames[i] + "_side");
            }
         } else {
            LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      int meta = world.func_72805_g(i, j, k);
      return meta != 8 && meta != 9 && meta != 10 ? super.func_149673_e(world, i, j, k, side) : LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j != 8 && j != 9 && j != 10) {
         return j == 4 && i != 0 && i != 1 ? this.iconRohanSide : super.func_149691_a(i, j);
      } else {
         return LOTRConnectedTextures.getConnectedIconItem(this, j);
      }
   }

   public String getConnectedName(int meta) {
      return this.field_149768_d + "_" + this.brickNames[meta];
   }

   public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
      return LOTRConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
   }
}
