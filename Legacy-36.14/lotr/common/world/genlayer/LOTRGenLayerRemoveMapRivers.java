package lotr.common.world.genlayer;

import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

public class LOTRGenLayerRemoveMapRivers extends LOTRGenLayer {
   private static int MAX_PIXEL_RANGE = 4;
   private LOTRDimension dimension;

   public LOTRGenLayerRemoveMapRivers(long l, LOTRGenLayer biomes, LOTRDimension dim) {
      super(l);
      this.lotrParent = biomes;
      this.dimension = dim;
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int maxRange = MAX_PIXEL_RANGE;
      int[] biomes = this.lotrParent.getInts(world, i - maxRange, k - maxRange, xSize + maxRange * 2, zSize + maxRange * 2);
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            this.func_75903_a((long)(i + i1), (long)(k + k1));
            int biomeID = biomes[i1 + maxRange + (k1 + maxRange) * (xSize + maxRange * 2)];
            if (biomeID != LOTRBiome.river.field_76756_M) {
               ints[i1 + k1 * xSize] = biomeID;
            } else {
               int replaceID = -1;

               for(int range = 1; range <= maxRange; ++range) {
                  Map viableBiomes = new HashMap();
                  Map viableBiomesWateryAdjacent = new HashMap();

                  int maxCount;
                  for(int k2 = k1 - range; k2 <= k1 + range; ++k2) {
                     for(int i2 = i1 - range; i2 <= i1 + range; ++i2) {
                        if (Math.abs(i2 - i1) == range || Math.abs(k2 - k1) == range) {
                           maxCount = i2 + maxRange + (k2 + maxRange) * (xSize + maxRange * 2);
                           int subBiomeID = biomes[maxCount];
                           LOTRBiome subBiome = this.dimension.biomeList[subBiomeID];
                           if (subBiome != LOTRBiome.river) {
                              boolean wateryAdjacent = subBiome.isWateryBiome() && range == 1;
                              Map srcMap = wateryAdjacent ? viableBiomesWateryAdjacent : viableBiomes;
                              int count = 0;
                              if (srcMap.containsKey(subBiomeID)) {
                                 count = (Integer)srcMap.get(subBiomeID);
                              }

                              ++count;
                              srcMap.put(subBiomeID, count);
                           }
                        }
                     }
                  }

                  Map priorityMap = viableBiomes;
                  if (!viableBiomesWateryAdjacent.isEmpty()) {
                     priorityMap = viableBiomesWateryAdjacent;
                  }

                  if (!priorityMap.isEmpty()) {
                     List maxCountBiomes = new ArrayList();
                     maxCount = 0;
                     Iterator var26 = priorityMap.entrySet().iterator();

                     Entry e;
                     int id;
                     int count;
                     while(var26.hasNext()) {
                        e = (Entry)var26.next();
                        id = (Integer)e.getKey();
                        count = (Integer)e.getValue();
                        if (count > maxCount) {
                           maxCount = count;
                        }
                     }

                     var26 = priorityMap.entrySet().iterator();

                     while(var26.hasNext()) {
                        e = (Entry)var26.next();
                        id = (Integer)e.getKey();
                        count = (Integer)e.getValue();
                        if (count == maxCount) {
                           maxCountBiomes.add(id);
                        }
                     }

                     replaceID = (Integer)maxCountBiomes.get(this.func_75902_a(maxCountBiomes.size()));
                     break;
                  }
               }

               if (replaceID == -1) {
                  FMLLog.warning("WARNING! LOTR map generation failed to replace map river at %d, %d", new Object[]{i, k});
                  ints[i1 + k1 * xSize] = 0;
               } else {
                  ints[i1 + k1 * xSize] = replaceID;
               }
            }
         }
      }

      return ints;
   }
}
