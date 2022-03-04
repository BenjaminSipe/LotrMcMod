package lotr.common.world.genlayer;

import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenNearHaradOasis;
import net.minecraft.world.World;

public class LOTRGenLayerOasisLake extends LOTRGenLayer {
   private LOTRDimension dimension;

   public LOTRGenLayerOasisLake(long l, LOTRGenLayer layer, LOTRDimension dim) {
      super(l);
      this.lotrParent = layer;
      this.dimension = dim;
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int[] biomes = this.lotrParent.getInts(world, i - 1, k - 1, xSize + 2, zSize + 2);
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            this.func_75903_a((long)(i + i1), (long)(k + k1));
            int biomeID = biomes[i1 + 1 + (k1 + 1) * (xSize + 2)];
            LOTRBiome biome = this.dimension.biomeList[biomeID];
            int newBiomeID = biomeID;
            if (biome instanceof LOTRBiomeGenNearHaradOasis) {
               boolean surrounded = true;
               int i2 = -1;

               while(true) {
                  if (i2 > 1) {
                     if (surrounded) {
                        newBiomeID = LOTRBiome.lake.field_76756_M;
                     }
                     break;
                  }

                  for(int k2 = -1; k2 <= 1; ++k2) {
                     int adjBiomeID = biomes[i1 + 1 + i2 + (k1 + 1 + k2) * (xSize + 2)];
                     LOTRBiome adjBiome = this.dimension.biomeList[adjBiomeID];
                     if (!(adjBiome instanceof LOTRBiomeGenNearHaradOasis)) {
                        surrounded = false;
                     }
                  }

                  ++i2;
               }
            }

            ints[i1 + k1 * xSize] = newBiomeID;
         }
      }

      return ints;
   }
}
