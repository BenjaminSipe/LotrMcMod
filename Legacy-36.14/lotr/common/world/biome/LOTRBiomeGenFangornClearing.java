package lotr.common.world.biome;

import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;

public class LOTRBiomeGenFangornClearing extends LOTRBiomeGenFangorn {
   public LOTRBiomeGenFangornClearing(int i, boolean major) {
      super(i, major);
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10);
      var10000.add(var10001);
      this.clearBiomeVariants();
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 4;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 8;
   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }
}
