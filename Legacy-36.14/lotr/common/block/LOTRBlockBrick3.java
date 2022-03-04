package lotr.common.block;

import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockBrick3 extends LOTRBlockBrickBase {
   public LOTRBlockBrick3() {
      this.setBrickNames(new String[]{"blueCarved", "redCarved", "highElven", "highElvenMossy", "highElvenCracked", "woodElven", "woodElvenMossy", "woodElvenCracked", "nearHaradCarved", "dolAmroth", "moredain", "nearHaradCracked", "dwarvenGlowing", "nearHaradRed", "nearHaradRedCracked", "nearHaradRedCarved"});
   }

   public int getLightValue(IBlockAccess world, int i, int j, int k) {
      return world.func_72805_g(i, j, k) == 12 ? Blocks.field_150426_aN.func_149750_m() : 0;
   }
}
