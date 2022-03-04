package lotr.common.world.genlayer;

import java.util.List;
import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

public class LOTRGenLayerClassicBiomes extends LOTRGenLayer {
   private LOTRDimension dimension;

   public LOTRGenLayerClassicBiomes(long l, LOTRGenLayer layer, LOTRDimension dim) {
      super(l);
      this.lotrParent = layer;
      this.dimension = dim;
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int[] oceans = this.lotrParent.getInts(world, i, k, xSize, zSize);
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            this.func_75903_a((long)(i + i1), (long)(k + k1));
            int isOcean = oceans[i1 + k1 * xSize];
            int biomeID;
            if (isOcean == 1) {
               biomeID = LOTRBiome.ocean.field_76756_M;
            } else {
               List biomeList = this.dimension.majorBiomes;
               int randIndex = this.func_75902_a(biomeList.size());
               LOTRBiome biome = (LOTRBiome)biomeList.get(randIndex);
               biomeID = biome.field_76756_M;
            }

            ints[i1 + k1 * xSize] = biomeID;
         }
      }

      return ints;
   }
}
