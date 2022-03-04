package lotr.common.world.genlayer;

import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.world.World;

public class LOTRGenLayerNearHaradRiverbanks extends LOTRGenLayer {
   private LOTRGenLayer biomeLayer;
   private LOTRGenLayer mapRiverLayer;
   private LOTRDimension dimension;

   public LOTRGenLayerNearHaradRiverbanks(long l, LOTRGenLayer biomes, LOTRGenLayer rivers, LOTRDimension dim) {
      super(l);
      this.biomeLayer = biomes;
      this.mapRiverLayer = rivers;
      this.dimension = dim;
   }

   public void func_75905_a(long l) {
      super.func_75905_a(l);
      this.biomeLayer.func_75905_a(l);
      this.mapRiverLayer.func_75905_a(l);
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int[] biomes = this.biomeLayer.getInts(world, i - 2, k - 2, xSize + 3, zSize + 3);
      int[] mapRivers = this.mapRiverLayer.getInts(world, i - 2, k - 2, xSize + 3, zSize + 3);
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            this.func_75903_a((long)(i + i1), (long)(k + k1));
            int biomeID = biomes[i1 + 2 + (k1 + 2) * (xSize + 3)];
            LOTRBiome biome = this.dimension.biomeList[biomeID];
            int newBiomeID = biomeID;
            if (biome instanceof LOTRBiomeGenNearHarad) {
               boolean adjRiver = false;
               int i2 = -2;

               while(true) {
                  if (i2 > 1) {
                     if (adjRiver) {
                        newBiomeID = LOTRBiome.nearHaradRiverbank.field_76756_M;
                     }
                     break;
                  }

                  for(int k2 = -2; k2 <= 1; ++k2) {
                     if (i2 != -2 && k2 != -2 && i2 != 1 && k2 == 1) {
                     }

                     int adjRiverCode = mapRivers[i1 + 2 + i2 + (k1 + 2 + k2) * (xSize + 3)];
                     if (adjRiverCode == 2) {
                        adjRiver = true;
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
