package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWallClayTileDyed extends LOTRBlockWallBase {
   public LOTRBlockWallClayTileDyed() {
      super(LOTRMod.clayTileDyed, 16);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return LOTRMod.clayTileDyed.func_149691_a(i, j);
   }
}
