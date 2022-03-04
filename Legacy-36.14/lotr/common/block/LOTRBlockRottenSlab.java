package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockRottenSlab extends LOTRBlockSlabBase {
   public LOTRBlockRottenSlab(boolean flag) {
      super(flag, Material.field_151575_d, 1);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      j += 8;
      return LOTRMod.planksRotten.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
