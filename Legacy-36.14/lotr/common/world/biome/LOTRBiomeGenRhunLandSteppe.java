package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.village.LOTRVillageGenRhun;

public class LOTRBiomeGenRhunLandSteppe extends LOTRBiomeGenRhunLand {
   public LOTRBiomeGenRhunLandSteppe(int i, boolean major) {
      super(i, major);
      this.rhunBoulders = false;
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10).setSpawnChance(500);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 5).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DALE_SOLDIERS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 1);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_GUARDS, 10);
      var10000.add(var10001);
      this.clearBiomeVariants();
      this.variantChance = 0.3F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.3F);
      this.decorator.resetTreeCluster();
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 0;
      this.decorator.grassPerChunk = 12;
      this.decorator.doubleGrassPerChunk = 8;
      this.decorator.addTree(LOTRTreeType.PINE_SHRUB, 2000);
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.RHUN(1, 4), 3000);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 1000);
      this.decorator.clearVillages();
      this.decorator.addVillage(new LOTRVillageGenRhun(this, 0.4F, false));
      this.biomeColors.setGrass(13946702);
      this.invasionSpawns.addInvasion(LOTRInvasions.RHUN, LOTREventSpawner.EventChance.RARE);
   }

   public float getTreeIncreaseChance() {
      return 0.05F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }
}
