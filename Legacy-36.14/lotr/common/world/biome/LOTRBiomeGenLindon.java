package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntitySeagull;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenHighElvenHall;
import lotr.common.world.structure2.LOTRWorldGenHighElfHouse;
import lotr.common.world.structure2.LOTRWorldGenHighElvenForge;
import lotr.common.world.structure2.LOTRWorldGenHighElvenTower;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenLindon extends LOTRBiome {
   public LOTRBiomeGenLindon(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntitySeagull.class, 6, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LINDON_ELVES, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LINDON_WARRIORS, 2);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.5F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_BIRCH);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 1.0F);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreQuendite, 6), 6.0F, 0, 48);
      this.decorator.setTreeCluster(10, 20);
      this.decorator.treesPerChunk = 0;
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 3;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.whiteSand = true;
      this.decorator.addTree(LOTRTreeType.OAK, 100);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 25);
      this.decorator.addTree(LOTRTreeType.BIRCH, 500);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 500);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.BIRCH_PARTY, 50);
      this.decorator.addTree(LOTRTreeType.BEECH, 100);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 25);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 40);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.ASPEN, 300);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.APPLE, 2);
      this.decorator.addTree(LOTRTreeType.PEAR, 2);
      this.registerPlainsFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenHighElfHouse(false), 300);
      this.decorator.addRandomStructure(new LOTRWorldGenHighElvenHall(false), 600);
      this.decorator.addRandomStructure(new LOTRWorldGenHighElvenForge(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenHighElvenTower(false), 900);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
      this.registerTravellingTrader(LOTREntityRivendellTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterLindon;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.LINDON;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.LINDON.getSubregion("lindon");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.HIGH_ELVEN;
   }

   public boolean hasSeasonalGrass() {
      return false;
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return 5;
   }
}
