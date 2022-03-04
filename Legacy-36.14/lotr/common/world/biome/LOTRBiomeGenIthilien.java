package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityHorse;
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
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure.LOTRWorldGenRuinedGondorTower;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenIthilienHideout;
import lotr.common.world.structure2.LOTRWorldGenRuinedBeaconTower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenIthilien extends LOTRBiome {
   public LOTRBiomeGenIthilien(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 10, 4, 6));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(10);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3).setSpawnChance(100);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[10];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LOSSARNACH_SOLDIERS, 4);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LEBENNIN_SOLDIERS, 4).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_HILLMEN, 1).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_SOLDIERS, 1).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACKROOT_SOLDIERS, 1).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.PINNATH_GELIN_SOLDIERS, 1).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_MEN, 10).setConquestThreshold(500.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(90);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[6];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 1);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_SPIDERS, 1).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(150.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
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
      this.variantChance = 0.7F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK_NOSTEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT, 4.0F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST, 2.0F);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK, 4.0F);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0F);
      this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_LEBETHRON);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.1F);
      this.decorator.addSoil(new WorldGenMinable(LOTRMod.rock, 1, 60, Blocks.field_150348_b), 2.0F, 0, 64);
      this.decorator.treesPerChunk = 0;
      this.decorator.willowPerChunk = 2;
      this.decorator.logsPerChunk = 1;
      this.decorator.flowersPerChunk = 4;
      this.decorator.doubleFlowersPerChunk = 4;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 4;
      this.decorator.waterlilyPerChunk = 2;
      this.decorator.generateAthelas = true;
      this.decorator.addTree(LOTRTreeType.OAK, 500);
      this.decorator.addTree(LOTRTreeType.OAK_TALL, 100);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.LEBETHRON, 100);
      this.decorator.addTree(LOTRTreeType.LEBETHRON_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH, 150);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.CEDAR, 200);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 100);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.PINE, 100);
      this.decorator.addTree(LOTRTreeType.CYPRESS, 100);
      this.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.APPLE, 5);
      this.decorator.addTree(LOTRTreeType.PEAR, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 5);
      this.decorator.addTree(LOTRTreeType.ALMOND, 15);
      this.registerForestFlowers();
      this.addFlower(LOTRMod.asphodel, 0, 10);
      this.addFlower(Blocks.field_150328_O, 2, 5);
      this.decorator.generateOrcDungeon = true;
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedBeaconTower(false), 200);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 800);
      this.decorator.addRandomStructure(new LOTRWorldGenIthilienHideout(false), 50);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GONDOR, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GONDOR_ITHILIEN, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GONDOR_DOL_AMROTH, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_NAN_UNGOL, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_HARNEDOR, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_COAST, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.RHUN, LOTREventSpawner.EventChance.UNCOMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterIthilien;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.ITHILIEN;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.GONDOR.getSubregion("ithilien");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.GONDOR_MIX.setRepair(0.7F);
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int i1;
      int j1;
      int k1;
      if (random.nextInt(3) == 0) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         k1 = k + random.nextInt(16) + 8;
         WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
         doubleFlowerGen.func_150548_a(0);
         doubleFlowerGen.func_76484_a(world, random, i1, j1, k1);
      }

      if (random.nextInt(24) == 0) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         k1 = k + random.nextInt(16) + 8;
         (new WorldGenFlowers(LOTRMod.pipeweedPlant)).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.4F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }
}
