package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenNurnWheatFarm;
import lotr.common.world.structure.LOTRWorldGenOrcSlaverTower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenNurn extends LOTRBiomeGenMordor {
   protected WorldGenerator nurnBoulderGen;

   public LOTRBiomeGenNurn(int i, boolean major) {
      super(i, major);
      this.nurnBoulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 0, 1, 3);
      this.field_76752_A = Blocks.field_150349_c;
      this.field_76753_B = Blocks.field_150346_d;
      this.field_76765_S = true;
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[6];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 5);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 5).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 2).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
      var10000.add(var10001);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.decorator.setTreeCluster(6, 30);
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 0;
      this.decorator.doubleFlowersPerChunk = 0;
      this.decorator.grassPerChunk = 8;
      this.decorator.dryReedChance = 0.6F;
      this.decorator.generateWater = true;
      this.decorator.addTree(LOTRTreeType.OAK, 500);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
      this.decorator.addTree(LOTRTreeType.CEDAR, 100);
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 200);
      this.decorator.addTree(LOTRTreeType.CHARRED, 200);
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenNurnWheatFarm(false), 40);
      this.decorator.addRandomStructure(new LOTRWorldGenOrcSlaverTower(false), 200);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
      this.biomeColors.setGrass(10066237);
      this.biomeColors.setFoliage(7042874);
      this.biomeColors.setSky(10526098);
      this.biomeColors.resetClouds();
      this.biomeColors.resetFog();
      this.biomeColors.setWater(8877157);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterNurn;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.NURN;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.MORDOR.getSubregion("nurn");
   }

   public boolean isGorgoroth() {
      return false;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(40) == 0) {
         for(int l = 0; l < 4; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.nurnBoulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
