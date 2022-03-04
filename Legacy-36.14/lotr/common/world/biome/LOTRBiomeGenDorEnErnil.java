package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntitySwan;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantElf;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenGondorRuin;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure2.LOTRWorldGenDolAmrothWatchfort;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenGondorStructure;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.village.LOTRVillageGenGondor;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenDorEnErnil extends LOTRBiome {
   public LOTRBiomeGenDorEnErnil(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 30, 2, 6));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntitySwan.class, 20, 4, 8));
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 10).setSpawnChance(30);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(1);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[6];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 20);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 1);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 4).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[8];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBARIANS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 20);
      var10004 = this.npcSpawnList;
      var10001[7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_RENEGADES, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.5F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_BIRCH);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.1F);
      this.decorator.addSoil(new WorldGenMinable(LOTRMod.rock, 1, 60, Blocks.field_150348_b), 2.0F, 0, 64);
      this.decorator.treesPerChunk = 0;
      this.decorator.setTreeCluster(10, 30);
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 4;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 4;
      this.decorator.generateAthelas = true;
      this.decorator.whiteSand = true;
      this.decorator.addTree(LOTRTreeType.BIRCH, 200);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 200);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.OAK, 100);
      this.decorator.addTree(LOTRTreeType.OAK_TALL, 100);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.CEDAR, 100);
      this.decorator.addTree(LOTRTreeType.CYPRESS, 50);
      this.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.APPLE, 5);
      this.decorator.addTree(LOTRTreeType.PEAR, 5);
      this.decorator.addTree(LOTRTreeType.LEMON, 5);
      this.decorator.addTree(LOTRTreeType.ORANGE, 5);
      this.decorator.addTree(LOTRTreeType.LIME, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 2);
      this.decorator.addTree(LOTRTreeType.ALMOND, 5);
      this.decorator.addTree(LOTRTreeType.PLUM, 5);
      this.registerPlainsFlowers();
      this.decorator.addVillage(new LOTRVillageGenGondor(this, LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH, 0.75F));
      this.decorator.addRandomStructure(new LOTRWorldGenDolAmrothWatchfort(false), 800);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorRuin(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 600);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 600);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDorwinionMerchantElf.class);
      this.registerTravellingTrader(LOTREntityDaleMerchant.class);
      this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_HARNEDOR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_COAST, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_UMBAR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.UNCOMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterDorEnErnil;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.DOR_EN_ERNIL;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.GONDOR.getSubregion("dolAmroth");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.DOL_AMROTH;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(24) == 0) {
         int i1 = i + random.nextInt(16) + 8;
         int j1 = random.nextInt(128);
         int k1 = k + random.nextInt(16) + 8;
         (new WorldGenFlowers(LOTRMod.pipeweedPlant)).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.03F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }

   public int spawnCountMultiplier() {
      return 4;
   }
}
