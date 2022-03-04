package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockSmoothStoneBase extends LOTRBlockBrickBase {
   @SideOnly(Side.CLIENT)
   private IIcon[] topIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] sideIcons;

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.brickNames.length) {
         j = 0;
      }

      return i != 0 && i != 1 ? this.sideIcons[j] : this.topIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.topIcons = new IIcon[this.brickNames.length];
      this.sideIcons = new IIcon[this.brickNames.length];

      for(int i = 0; i < this.brickNames.length; ++i) {
         this.topIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.brickNames[i] + "_top");
         this.sideIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.brickNames[i] + "_side");
      }

   }
}
