package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBiomeFlowers;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure2.LOTRWorldGenBurntHouse;
import lotr.common.world.structure2.LOTRWorldGenGundabadCamp;
import lotr.common.world.structure2.LOTRWorldGenRangerCamp;
import lotr.common.world.structure2.LOTRWorldGenRangerWatchtower;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.FlowerEntry;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenEriador extends LOTRBiome {
   public static NoiseGeneratorPerlin lavenderNoise = new NoiseGeneratorPerlin(new Random(2571548905158015L), 1);

   public LOTRBiomeGenEriador(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 6, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(10, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10).setSpawnChance(200);
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
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 4).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LINDON_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
      var10000.add(var10001);
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 1.0F);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND, 1.0F);
      this.addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
      this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2F);
      this.decorator.setTreeCluster(8, 12);
      this.decorator.willowPerChunk = 1;
      this.decorator.grassPerChunk = 9;
      this.decorator.doubleGrassPerChunk = 4;
      this.decorator.generateAthelas = true;
      this.decorator.addTree(LOTRTreeType.OAK, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 200);
      this.decorator.addTree(LOTRTreeType.BEECH, 20);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 2);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 100);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.ASPEN, 50);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 5);
      this.decorator.addTree(LOTRTreeType.APPLE, 2);
      this.decorator.addTree(LOTRTreeType.PEAR, 2);
      this.registerPlainsFlowers();
      this.addFlower(LOTRMod.lavender, 0, 20);
      this.decorator.generateOrcDungeon = true;
      this.decorator.addRandomStructure(new LOTRWorldGenGundabadCamp(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenBurntHouse(false), 3000);
      this.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 3000);
      this.decorator.addRandomStructure(new LOTRWorldGenRangerCamp(false), 1500);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 3), 800);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 3), 800);
      this.decorator.addRandomStructure(new LOTRWorldGenRangerWatchtower(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityRivendellTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterEriador;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.ERIADOR;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.ERIADOR.getSubregion("eriador");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.ARNOR.setRepair(0.92F);
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      LOTRBiomeVariant biomeVariant = ((LOTRWorldChunkManager)world.func_72959_q()).getBiomeVariantAt(i + 8, k + 8);
      if (biomeVariant.flowerFactor >= 1.0F) {
         double lavNoise = lavenderNoise.func_151601_a((double)i * 0.001D, (double)k * 0.001D);
         lavNoise += lavenderNoise.func_151601_a((double)i * 0.03D, (double)k * 0.03D);
         lavNoise /= 2.0D;
         lavNoise -= 0.75D;
         if (lavNoise >= 0.0D) {
            int num = (int)Math.round(lavNoise * 16.0D);
            num = Math.max(num, 4);
            LOTRWorldGenBiomeFlowers lavGen = new LOTRWorldGenBiomeFlowers(LOTRMod.lavender, 0);

            for(int l = 0; l < num; ++l) {
               int i1 = i + random.nextInt(16) + 8;
               int k1 = k + random.nextInt(16) + 8;
               int j1 = world.func_72825_h(i1, k1);
               lavGen.func_76484_a(world, random, i1, j1, k1);
            }
         }
      }

   }

   public FlowerEntry getRandomFlower(World world, Random rand, int i, int j, int k) {
      LOTRBiomeVariant variant = ((LOTRWorldChunkManager)world.func_72959_q()).getBiomeVariantAt(i, k);
      return variant.flowerFactor > 1.0F && rand.nextFloat() < 0.9F ? new FlowerEntry(LOTRMod.lavender, 0, 100) : (FlowerEntry)WeightedRandom.func_76271_a(rand, this.flowers);
   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }

   public float getTreeIncreaseChance() {
      return 0.05F;
   }

   public int spawnCountMultiplier() {
      return 4;
   }
}
