package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockScorchedWall extends LOTRBlockWallBase {
   public LOTRBlockScorchedWall() {
      super(LOTRMod.scorchedStone, 1);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return LOTRMod.scorchedStone.func_149691_a(i, j);
   }
}
