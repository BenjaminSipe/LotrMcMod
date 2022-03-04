package lotr.common.world.genlayer;

import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenBeach;
import lotr.common.world.biome.LOTRBiomeGenFarHaradCoast;
import lotr.common.world.biome.LOTRBiomeGenFarHaradVolcano;
import lotr.common.world.biome.LOTRBiomeGenForodwaith;
import lotr.common.world.biome.LOTRBiomeGenLindon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRGenLayerBeach extends LOTRGenLayer {
   private LOTRDimension dimension;
   private BiomeGenBase targetBiome;

   public LOTRGenLayerBeach(long l, LOTRGenLayer layer, LOTRDimension dim, BiomeGenBase target) {
      super(l);
      this.lotrParent = layer;
      this.dimension = dim;
      this.targetBiome = target;
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
            if (biomeID != this.targetBiome.field_76756_M && !biome.isWateryBiome()) {
               int biome1 = biomes[i1 + 1 + (k1 + 1 - 1) * (xSize + 2)];
               int biome2 = biomes[i1 + 1 + 1 + (k1 + 1) * (xSize + 2)];
               int biome3 = biomes[i1 + 1 - 1 + (k1 + 1) * (xSize + 2)];
               int biome4 = biomes[i1 + 1 + (k1 + 1 + 1) * (xSize + 2)];
               if (biome1 == this.targetBiome.field_76756_M || biome2 == this.targetBiome.field_76756_M || biome3 == this.targetBiome.field_76756_M || biome4 == this.targetBiome.field_76756_M) {
                  if (biome instanceof LOTRBiomeGenLindon) {
                     if (this.func_75902_a(3) == 0) {
                        newBiomeID = LOTRBiome.lindonCoast.field_76756_M;
                     } else {
                        newBiomeID = LOTRBiome.beachWhite.field_76756_M;
                     }
                  } else if (biome instanceof LOTRBiomeGenForodwaith) {
                     newBiomeID = LOTRBiome.forodwaithCoast.field_76756_M;
                  } else if (biome instanceof LOTRBiomeGenFarHaradCoast) {
                     newBiomeID = biomeID;
                  } else if (biome instanceof LOTRBiomeGenFarHaradVolcano) {
                     newBiomeID = biomeID;
                  } else if (!(biome instanceof LOTRBiomeGenBeach)) {
                     if (biome.decorator.whiteSand) {
                        newBiomeID = LOTRBiome.beachWhite.field_76756_M;
                     } else if (this.func_75902_a(20) == 0) {
                        newBiomeID = LOTRBiome.beachGravel.field_76756_M;
                     } else {
                        newBiomeID = LOTRBiome.beach.field_76756_M;
                     }
                  }
               }
            }

            ints[i1 + k1 * xSize] = newBiomeID;
         }
      }

      return ints;
   }
}
