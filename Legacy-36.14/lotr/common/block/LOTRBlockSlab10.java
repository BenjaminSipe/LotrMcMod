package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab10 extends LOTRBlockSlabBase {
   public LOTRBlockSlab10(boolean flag) {
      super(flag, Material.field_151576_e, 8);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j == 0) {
         return LOTRMod.pillar2.func_149691_a(i, 5);
      } else if (j == 1) {
         return LOTRMod.brick5.func_149691_a(i, 4);
      } else if (j == 2) {
         return LOTRMod.brick5.func_149691_a(i, 5);
      } else if (j == 3) {
         return LOTRMod.brick5.func_149691_a(i, 7);
      } else if (j == 4) {
         return LOTRMod.pillar2.func_149691_a(i, 6);
      } else if (j == 5) {
         return LOTRMod.pillar2.func_149691_a(i, 7);
      } else if (j == 6) {
         return LOTRMod.whiteSandstone.func_149691_a(i, 0);
      } else {
         return j == 7 ? LOTRMod.rock.func_149691_a(i, 0) : super.func_149691_a(i, j);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
