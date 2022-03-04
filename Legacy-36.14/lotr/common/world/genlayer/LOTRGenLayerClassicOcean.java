package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerClassicOcean extends LOTRGenLayer {
   public LOTRGenLayerClassicOcean(long l) {
      super(l);
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            this.func_75903_a((long)(i + i1), (long)(k + k1));
            boolean ocean = this.func_75902_a(5) == 0;
            if (ocean) {
               ints[i1 + k1 * xSize] = 1;
            } else {
               ints[i1 + k1 * xSize] = 0;
            }
         }
      }

      return ints;
   }
}
