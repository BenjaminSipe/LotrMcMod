package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBlastedLand;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenSkullPile;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenRuinedRohanWatchtower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenUrukCamp;
import lotr.common.world.structure2.LOTRWorldGenUrukWargPit;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenRohanUruk extends LOTRBiomeGenRohan {
   private WorldGenerator deadMoundGen;

   public LOTRBiomeGenRohanUruk(int i, boolean major) {
      super(i, major);
      this.deadMoundGen = new LOTRWorldGenBoulder(LOTRMod.wasteBlock, 0, 1, 3);
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 4);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 5);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
      var10000.add(var10001);
      this.clearBiomeVariants();
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.decorator.resetTreeCluster();
      this.decorator.willowPerChunk = 0;
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenUrukCamp(false), 120);
      this.decorator.addRandomStructure(new LOTRWorldGenUrukWargPit(false), 300);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedRohanWatchtower(false), 300);
      this.decorator.addRandomStructure(new LOTRWorldGenBlastedLand(true), 40);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 300);
      this.decorator.clearVillages();
      this.clearTravellingTraders();
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
      this.invasionSpawns.clearInvasions();
      this.invasionSpawns.addInvasion(LOTRInvasions.ROHAN, LOTREventSpawner.EventChance.COMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterRohanUrukHighlands;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.ISENGARD.getSubregion("rohan");
   }

   public boolean getEnableRiver() {
      return false;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int i1;
      int k1;
      int j1;
      if (random.nextInt(30) == 0) {
         WorldGenerator treeGen = random.nextInt(3) == 0 ? LOTRTreeType.OAK_DEAD.create(false, random) : LOTRTreeType.CHARRED.create(false, random);
         i1 = 3 + random.nextInt(5);

         for(k1 = 0; k1 < i1; ++k1) {
            j1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            treeGen.func_76484_a(world, random, j1, world.func_72976_f(j1, k1), k1);
         }
      }

      if (random.nextInt(32) == 0) {
         for(int l = 0; l < 3; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            j1 = world.func_72976_f(i1, k1);
            this.deadMoundGen.func_76484_a(world, random, i1, j1, k1);
            (new LOTRWorldGenSkullPile()).func_76484_a(world, random, i1, j1, k1);
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }

   public boolean canSpawnHostilesInDay() {
      return true;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
