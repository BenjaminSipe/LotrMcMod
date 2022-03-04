package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;

public class LOTRBiomeGenRivendellHills extends LOTRBiomeGenRivendell {
   public LOTRBiomeGenRivendellHills(int i, boolean major) {
      super(i, major);
      this.field_76753_B = LOTRMod.rock;
      this.fillerBlockMeta = 5;
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10).setSpawnChance(500);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.2F;
      this.clearBiomeVariants();
      this.variantChance = 0.4F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.decorator.treesPerChunk = 3;
      this.decorator.flowersPerChunk = 2;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.PINE, 1000);
      this.decorator.addTree(LOTRTreeType.PINE_SHRUB, 200);
      this.decorator.addTree(LOTRTreeType.FIR, 100);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 100);
      this.decorator.addTree(LOTRTreeType.ASPEN, 100);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.OAK, 100);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
      this.biomeColors.resetGrass();
      this.decorator.clearRandomStructures();
      this.invasionSpawns.clearInvasions();
      this.invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_RIVENDELL, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRBiome.loneLands.getBiomeAchievement();
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRBiome.loneLands.getBiomeWaypoints();
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRBiome.loneLands.getBiomeMusic();
   }

   public boolean hasSeasonalGrass() {
      return true;
   }

   public float getChanceToSpawnAnimals() {
      return 1.0F;
   }

   public int spawnCountMultiplier() {
      return 1;
   }
}
