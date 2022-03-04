package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.village.LOTRVillageGenBree;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenBreeland extends LOTRBiome {
   public LOTRBiomeGenBreeland(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 8, 2, 6));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BREE_MEN, 10).setSpawnChance(500);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BREE_GUARDS, 4).setSpawnChance(500);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(5);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RUFFIANS, 10).setSpawnChance(500);
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
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 1.0F);
      this.decorator.setTreeCluster(8, 20);
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 2;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.generateAthelas = true;
      this.decorator.addTree(LOTRTreeType.OAK, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
      this.decorator.addTree(LOTRTreeType.BEECH, 300);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 75);
      this.decorator.addTree(LOTRTreeType.MAPLE, 200);
      this.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 300);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 75);
      this.decorator.addTree(LOTRTreeType.BIRCH, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.ASPEN, 50);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.APPLE, 3);
      this.decorator.addTree(LOTRTreeType.PEAR, 3);
      this.registerPlainsFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 3), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 3), 1000);
      LOTRVillageGenBree villageGen = new LOTRVillageGenBree(this, 1.0F);
      villageGen.addFixedLocationMapOffset(LOTRWaypoint.BREE, 2, 0, 3, "Bree");
      villageGen.addFixedLocationMapOffset(LOTRWaypoint.STADDLE, -2, 0, 1, "Staddle");
      villageGen.addFixedLocationMapOffset(LOTRWaypoint.COMBE, 2, 0, 3, "Combe");
      villageGen.addFixedLocationMapOffset(LOTRWaypoint.ARCHET, 0, -2, 2, "Archet");
      this.decorator.addVillage(villageGen);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDaleMerchant.class);
      this.registerTravellingTrader(LOTREntityRivendellTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public boolean hasDomesticAnimals() {
      return true;
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterBreeland;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.BREE_LAND;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.BREE.getSubregion("bree");
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public float getTreeIncreaseChance() {
      return 0.05F;
   }
}
