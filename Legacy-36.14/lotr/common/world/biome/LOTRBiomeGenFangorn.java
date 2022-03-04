package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityCrebain;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenEntJars;
import lotr.common.world.feature.LOTRWorldGenWaterPlant;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenFangorn extends LOTRBiome {
   private boolean isBirchFangorn = false;

   public LOTRBiomeGenFangorn(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 30, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityCrebain.class, 6, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 5);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0F);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.2F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.decorator.treesPerChunk = 12;
      this.decorator.willowPerChunk = 3;
      this.decorator.logsPerChunk = 5;
      this.decorator.flowersPerChunk = 6;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 12;
      this.decorator.doubleGrassPerChunk = 6;
      this.decorator.enableFern = true;
      this.decorator.addTree(LOTRTreeType.DARK_OAK, 400);
      this.decorator.addTree(LOTRTreeType.OAK, 100);
      this.decorator.addTree(LOTRTreeType.OAK_TALL, 200);
      this.decorator.addTree(LOTRTreeType.OAK_TALLER, 200);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH, 20);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 20);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.BEECH, 20);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.OAK_FANGORN, 50);
      this.decorator.addTree(LOTRTreeType.BEECH_FANGORN, 20);
      this.decorator.addTree(LOTRTreeType.ASPEN, 50);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
      this.registerForestFlowers();
      this.addFlower(LOTRMod.fangornPlant, 0, 1);
      this.addFlower(LOTRMod.fangornPlant, 1, 1);
      this.addFlower(LOTRMod.fangornPlant, 2, 1);
      this.addFlower(LOTRMod.fangornPlant, 3, 1);
      this.addFlower(LOTRMod.fangornPlant, 4, 1);
      this.addFlower(LOTRMod.fangornPlant, 5, 1);
      this.biomeColors.setSky(7774322);
      this.biomeColors.setFog(3308875);
      this.biomeColors.setFoggy(true);
      this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRBiomeGenFangorn setBirchFangorn() {
      this.isBirchFangorn = true;
      this.decorator.addTree(LOTRTreeType.BIRCH, 2000);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 2000);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 2000);
      this.decorator.addTree(LOTRTreeType.BIRCH_FANGORN, 1000);
      return this;
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterFangorn;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.FANGORN;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FANGORN.getSubregion("fangorn");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int i1;
      int k1;
      int j1;
      if (random.nextInt(2) == 0) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;

         for(j1 = 64 + random.nextInt(64); j1 > 0 && world.func_147439_a(i1, j1 - 1, k1) == Blocks.field_150350_a; --j1) {
         }

         (new LOTRWorldGenWaterPlant(LOTRMod.fangornRiverweed)).func_76484_a(world, random, i1, j1, k1);
      }

      if (random.nextInt(10) == 0) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         j1 = world.func_72976_f(i1, k1);
         (new LOTRWorldGenEntJars()).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
