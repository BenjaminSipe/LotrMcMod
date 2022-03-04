package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;

public class LOTRBlockButton extends BlockButton {
   private String iconPath;

   public LOTRBlockButton(boolean flag, String s) {
      super(flag);
      this.iconPath = s;
      this.func_149647_a(LOTRCreativeTabs.tabMisc);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.field_149761_L = iconregister.func_94245_a(this.iconPath);
   }
}
