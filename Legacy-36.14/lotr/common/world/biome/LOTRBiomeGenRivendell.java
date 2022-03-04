package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenRivendellHall;
import lotr.common.world.structure2.LOTRWorldGenRivendellForge;
import lotr.common.world.structure2.LOTRWorldGenRivendellHouse;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenRivendell extends LOTRBiome {
   public LOTRBiomeGenRivendell(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_ELVES, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 2);
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
      this.variantChance = 0.3F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreQuendite, 6), 6.0F, 0, 48);
      this.decorator.treesPerChunk = 0;
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 5;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.OAK, 500);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.BEECH, 500);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.BIRCH, 200);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 50);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.ASPEN, 50);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.APPLE, 2);
      this.decorator.addTree(LOTRTreeType.PEAR, 2);
      this.registerPlainsFlowers();
      this.decorator.addRandomStructure(new LOTRWorldGenRivendellHouse(false), 100);
      this.decorator.addRandomStructure(new LOTRWorldGenRivendellHall(false), 200);
      this.decorator.addRandomStructure(new LOTRWorldGenRivendellForge(false), 200);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterRivendell;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.RIVENDELL_VALE;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.RIVENDELL.getSubregion("rivendell");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.HIGH_ELVEN;
   }

   public boolean hasSeasonalGrass() {
      return false;
   }

   public boolean getEnableRiver() {
      return false;
   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
