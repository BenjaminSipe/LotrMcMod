package lotr.common.block;

import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.world.World;

public class LOTRBlockMordorPlant extends LOTRBlockFlower {
   public boolean func_149718_j(World world, int i, int j, int k) {
      return LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
   }
}
