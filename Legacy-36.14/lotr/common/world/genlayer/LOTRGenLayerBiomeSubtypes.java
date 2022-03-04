package lotr.common.world.genlayer;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

public class LOTRGenLayerBiomeSubtypes extends LOTRGenLayer {
   private LOTRGenLayer biomeLayer;
   private LOTRGenLayer variantsLayer;

   public LOTRGenLayerBiomeSubtypes(long l, LOTRGenLayer biomes, LOTRGenLayer subtypes) {
      super(l);
      this.biomeLayer = biomes;
      this.variantsLayer = subtypes;
   }

   public void func_75905_a(long l) {
      this.biomeLayer.func_75905_a(l);
      this.variantsLayer.func_75905_a(l);
      super.func_75905_a(l);
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int[] biomes = this.biomeLayer.getInts(world, i, k, xSize, zSize);
      int[] variants = this.variantsLayer.getInts(world, i, k, xSize, zSize);
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            this.func_75903_a((long)(i + i1), (long)(k + k1));
            int biome = biomes[i1 + k1 * xSize];
            int variant = variants[i1 + k1 * xSize];
            int newBiome = biome;
            if (biome == LOTRBiome.shire.field_76756_M && variant < 15 && variant != 0) {
               newBiome = LOTRBiome.shireWoodlands.field_76756_M;
            } else if (biome == LOTRBiome.forodwaithMountains.field_76756_M && variant < 5) {
               newBiome = LOTRBiome.forodwaithGlacier.field_76756_M;
            } else if (biome == LOTRBiome.farHarad.field_76756_M && variant < 20) {
               newBiome = LOTRBiome.farHaradForest.field_76756_M;
            } else if (biome == LOTRBiome.farHaradJungle.field_76756_M && variant < 15) {
               newBiome = LOTRBiome.tauredainClearing.field_76756_M;
            } else if (biome == LOTRBiome.pertorogwaith.field_76756_M && variant < 15) {
               newBiome = LOTRBiome.farHaradVolcano.field_76756_M;
            } else if (biome == LOTRBiome.ocean.field_76756_M && variant < 2) {
               newBiome = LOTRBiome.island.field_76756_M;
            }

            if (newBiome != biome) {
               ints[i1 + k1 * xSize] = newBiome;
            } else {
               ints[i1 + k1 * xSize] = biome;
            }
         }
      }

      return ints;
   }
}
