package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityGemsbok;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenSkullPile;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenHalfTrollForest extends LOTRBiomeGenFarHarad {
   private WorldGenerator deadMoundGen;

   public LOTRBiomeGenHalfTrollForest(int i, boolean major) {
      super(i, major);
      this.deadMoundGen = new LOTRWorldGenBoulder(LOTRMod.wasteBlock, 0, 1, 2);
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(LOTREntityRhino.class, 8, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityGemsbok.class, 4, 4, 4));
      this.spawnableLOTRAmbientList.clear();
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10).setSpawnChance(1000);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 5);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
      var10000.add(var10001);
      this.clearBiomeVariants();
      this.variantChance = 0.7F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.CLEARING);
      this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
      this.decorator.treesPerChunk = 3;
      this.decorator.vinesPerChunk = 4;
      this.decorator.logsPerChunk = 2;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 10;
      this.decorator.flowersPerChunk = 1;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.ACACIA, 600);
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 200);
      this.decorator.addTree(LOTRTreeType.BAOBAB, 20);
      this.decorator.addTree(LOTRTreeType.ACACIA_DEAD, 300);
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.TAUREDAIN(1, 2), 500);
      this.biomeColors.setSky(12698028);
      this.biomeColors.setClouds(14869216);
      this.biomeColors.setFog(8885125);
      this.biomeColors.setFoggy(true);
      this.biomeColors.setWater(10923394);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
      this.invasionSpawns.addInvasion(LOTRInvasions.HALF_TROLL, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterHalfTrollForest;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.PERTOROGWAITH_FOREST;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.PERDOROGWAITH.getSubregion("pertorogwaith");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(40) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            int j1 = world.func_72976_f(i1, k1);
            this.deadMoundGen.func_76484_a(world, random, i1, j1, k1);
            (new LOTRWorldGenSkullPile()).func_76484_a(world, random, i1, j1, k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.5F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.05F;
   }

   public boolean canSpawnHostilesInDay() {
      return true;
   }
}
