package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantElf;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenWoodElfTower;
import lotr.common.world.structure2.LOTRWorldGenWoodElfHouse;
import lotr.common.world.structure2.LOTRWorldGenWoodElvenForge;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenWoodlandRealm extends LOTRBiome {
   public LOTRBiomeGenWoodlandRealm(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityElk.class, 30, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 20, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 2, 1, 4));
      this.field_82914_M.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELF_WARRIORS, 3);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.2F;
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.1F);
      this.variantChance = 0.3F;
      this.decorator.treesPerChunk = 1;
      this.decorator.willowPerChunk = 2;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 4;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.enableFern = true;
      this.decorator.generateLava = false;
      this.decorator.generateCobwebs = false;
      this.decorator.addTree(LOTRTreeType.GREEN_OAK, 500);
      this.decorator.addTree(LOTRTreeType.GREEN_OAK_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.GREEN_OAK_EXTREME, 80);
      this.decorator.addTree(LOTRTreeType.RED_OAK, 40);
      this.decorator.addTree(LOTRTreeType.RED_OAK_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.OAK, 50);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 100);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 50);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.BEECH, 50);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.LARCH, 100);
      this.decorator.addTree(LOTRTreeType.FIR, 200);
      this.decorator.addTree(LOTRTreeType.PINE, 200);
      this.decorator.addTree(LOTRTreeType.ASPEN, 50);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
      this.registerForestFlowers();
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenWoodElfHouse(false), 16);
      this.decorator.addRandomStructure(new LOTRWorldGenWoodElfTower(false), 100);
      this.decorator.addRandomStructure(new LOTRWorldGenWoodElvenForge(false), 80);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityDorwinionMerchantElf.class);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
      this.invasionSpawns.addInvasion(LOTRInvasions.DOL_GULDUR, LOTREventSpawner.EventChance.UNCOMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterWoodlandRealm;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.WOODLAND_REALM;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.WOODLAND_REALM.getSubregion("woodlandRealm");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.WOOD_ELVEN;
   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
