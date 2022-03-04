package lotr.common.world.gen.layer;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import lotr.common.world.biome.LOTRBiomeWrapper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public class MEShoreLayers {
   private static boolean isSea(int biomeID, IWorld world) {
      return biomeID == LOTRBiomes.getBiomeID(LOTRBiomes.SEA, world);
   }

   private static boolean isIsland(int biomeID, IWorld world) {
      return biomeID == LOTRBiomes.getBiomeID(LOTRBiomes.ISLAND, world);
   }

   public static enum ForIsland implements ICastleTransformer {
      INSTANCE;

      public int func_202748_a(INoiseRandom noiseRand, int north, int east, int south, int west, int center) {
         IWorld world = LOTRBiomes.getServerBiomeContextWorld();
         return !MEShoreLayers.isSea(center, world) || !MEShoreLayers.isIsland(north, world) && !MEShoreLayers.isIsland(east, world) && !MEShoreLayers.isIsland(south, world) && !MEShoreLayers.isIsland(west, world) ? center : LOTRBiomes.getBiomeID((PreRegisteredLOTRBiome)LOTRBiomes.BEACH, world);
      }
   }

   public static enum ForMainland implements ICastleTransformer {
      INSTANCE;

      public int func_202748_a(INoiseRandom noiseRand, int north, int east, int south, int west, int center) {
         IWorld world = LOTRBiomes.getServerBiomeContextWorld();
         if (MEShoreLayers.isSea(center, world) || MEShoreLayers.isIsland(center, world) || !MEShoreLayers.isSea(north, world) && !MEShoreLayers.isSea(east, world) && !MEShoreLayers.isSea(south, world) && !MEShoreLayers.isSea(west, world)) {
            return center;
         } else {
            Biome centerBiome = LOTRBiomes.getBiomeByID(center, world);
            LOTRBiomeWrapper shoreBiome = LOTRBiomes.getWrapperFor(centerBiome, world).getShore();
            return LOTRBiomes.getBiomeID((LOTRBiomeWrapper)shoreBiome, world);
         }
      }
   }
}
