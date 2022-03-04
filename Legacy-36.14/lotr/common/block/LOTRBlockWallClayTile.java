package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWallClayTile extends LOTRBlockWallBase {
   public LOTRBlockWallClayTile() {
      super(LOTRMod.clayTile, 1);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return LOTRMod.clayTile.func_149691_a(i, j);
   }
}
