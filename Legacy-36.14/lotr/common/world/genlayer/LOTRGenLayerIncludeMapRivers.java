package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerIncludeMapRivers extends LOTRGenLayer {
   private LOTRGenLayer riverLayer;
   private LOTRGenLayer mapRiverLayer;

   public LOTRGenLayerIncludeMapRivers(long l, LOTRGenLayer rivers, LOTRGenLayer mapRivers) {
      super(l);
      this.riverLayer = rivers;
      this.mapRiverLayer = mapRivers;
   }

   public void func_75905_a(long l) {
      super.func_75905_a(l);
      this.riverLayer.func_75905_a(l);
      this.mapRiverLayer.func_75905_a(l);
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int[] rivers = this.riverLayer.getInts(world, i, k, xSize, zSize);
      int[] mapRivers = this.mapRiverLayer.getInts(world, i, k, xSize, zSize);
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            this.func_75903_a((long)(i + i1), (long)(k + k1));
            int index = i1 + k1 * xSize;
            int isRiver = rivers[index];
            int isMapRiver = mapRivers[index];
            if (isMapRiver == 2) {
               ints[index] = isMapRiver;
            } else {
               ints[index] = isRiver;
            }
         }
      }

      return ints;
   }
}
