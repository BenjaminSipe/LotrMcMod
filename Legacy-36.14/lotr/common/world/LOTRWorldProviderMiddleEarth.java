package lotr.common.world;

import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.chunk.IChunkProvider;

public class LOTRWorldProviderMiddleEarth extends LOTRWorldProvider {
   public LOTRDimension getLOTRDimension() {
      return LOTRDimension.MIDDLE_EARTH;
   }

   public IChunkProvider func_76555_c() {
      return new LOTRChunkProvider(this.field_76579_a, this.field_76579_a.func_72905_C());
   }

   public ChunkCoordinates getSpawnPoint() {
      return new ChunkCoordinates(LOTRLevelData.middleEarthPortalX, LOTRLevelData.middleEarthPortalY, LOTRLevelData.middleEarthPortalZ);
   }

   public void setSpawnPoint(int i, int j, int k) {
   }

   public void setRingPortalLocation(int i, int j, int k) {
      LOTRLevelData.markMiddleEarthPortalLocation(i, j, k);
   }
}
