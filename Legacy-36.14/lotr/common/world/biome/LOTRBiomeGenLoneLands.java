package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanHouse;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanVillage;
import lotr.common.world.structure2.LOTRWorldGenBurntHouse;
import lotr.common.world.structure2.LOTRWorldGenGundabadCamp;
import lotr.common.world.structure2.LOTRWorldGenRangerCamp;
import lotr.common.world.structure2.LOTRWorldGenRangerWatchtower;
import lotr.common.world.structure2.LOTRWorldGenRhudaurCastle;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenLoneLands extends LOTRBiome {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenLoneLands(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 5);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 6, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(8, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10).setSpawnChance(500);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(100);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(50);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 10).setSpawnChance(10000);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
      var10000.add(var10001);
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0F);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
      this.addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2F);
      this.decorator.treesPerChunk = 0;
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 3;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 6;
      this.decorator.generateAthelas = true;
      this.decorator.addTree(LOTRTreeType.OAK, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 300);
      this.decorator.addTree(LOTRTreeType.BEECH, 100);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH, 10);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 5);
      this.decorator.addTree(LOTRTreeType.ASPEN, 50);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.APPLE, 1);
      this.decorator.addTree(LOTRTreeType.PEAR, 1);
      this.registerPlainsFlowers();
      this.biomeColors.setGrass(12892772);
      this.decorator.generateOrcDungeon = true;
      this.decorator.addRandomStructure(new LOTRWorldGenGundabadCamp(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 800);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenBurntHouse(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenRangerCamp(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 400);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 400);
      this.decorator.addRandomStructure(new LOTRWorldGenRangerWatchtower(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 4000);
      this.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanHouse(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 200);
      this.decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 1000);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityRivendellTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_RIVENDELL, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterLoneLands;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.LONE_LANDS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.ERIADOR.getSubregion("loneLands");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.ARNOR.setRepair(0.4F);
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(32) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }
}
