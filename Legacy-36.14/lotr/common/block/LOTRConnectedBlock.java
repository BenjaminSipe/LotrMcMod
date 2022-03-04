package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public interface LOTRConnectedBlock {
   String getConnectedName(int var1);

   boolean areBlocksConnected(IBlockAccess var1, int var2, int var3, int var4, int var5, int var6, int var7);

   public static class Checks {
      public static boolean matchBlockAndMeta(Block block, IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
         int meta = world.func_72805_g(i, j, k);
         Block otherBlock = world.func_147439_a(i1, j1, k1);
         int otherMeta = world.func_72805_g(i1, j1, k1);
         return otherBlock == block && meta == otherMeta;
      }
   }
}
