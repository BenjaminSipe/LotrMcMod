package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenRottenHouse;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenNindalf extends LOTRBiome {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenNindalf(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 2, 4);
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 20);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 1);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 2);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
      var10000.add(var10001);
      this.clearBiomeVariants();
      this.variantChance = 1.0F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_SWAMP);
      this.decorator.sandPerChunk = 0;
      this.decorator.quagmirePerChunk = 2;
      this.decorator.treesPerChunk = 0;
      this.decorator.willowPerChunk = 0;
      this.decorator.logsPerChunk = 2;
      this.decorator.flowersPerChunk = 0;
      this.decorator.grassPerChunk = 12;
      this.decorator.doubleGrassPerChunk = 8;
      this.decorator.enableFern = true;
      this.decorator.canePerChunk = 10;
      this.decorator.reedPerChunk = 2;
      this.decorator.waterlilyPerChunk = 3;
      this.decorator.addTree(LOTRTreeType.OAK, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 1000);
      this.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 500);
      this.registerSwampFlowers();
      this.biomeColors.setGrass(7106635);
      this.biomeColors.setFoliage(7041093);
      this.biomeColors.setSky(9013080);
      this.biomeColors.setClouds(8224100);
      this.biomeColors.setFog(6579288);
      this.biomeColors.setWater(3159334);
      this.decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 500);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterNindalf;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.NINDALF;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.DEAD_MARSHES.getSubregion("nindalf");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(24) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.5F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }
}
