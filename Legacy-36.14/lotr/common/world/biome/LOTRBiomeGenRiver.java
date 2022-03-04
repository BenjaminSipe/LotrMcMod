package lotr.common.world.biome;

import lotr.common.world.spawning.LOTREventSpawner;

public class LOTRBiomeGenRiver extends LOTRBiome {
   public LOTRBiomeGenRiver(int i, boolean major) {
      super(i, major);
      this.field_76762_K.clear();
      this.npcSpawnList.clear();
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
      this.invasionSpawns.clearInvasions();
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return null;
   }

   public boolean isRiver() {
      return true;
   }

   public float getTreeIncreaseChance() {
      return 0.5F;
   }
}
