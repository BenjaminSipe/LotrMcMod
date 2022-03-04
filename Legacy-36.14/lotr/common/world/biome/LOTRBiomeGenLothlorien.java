package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.animal.LOTREntitySwan;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import lotr.common.world.structure2.LOTRWorldGenGaladhrimForge;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenLothlorien extends LOTRBiome {
   public LOTRBiomeGenLothlorien(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 20, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 30, 4, 6));
      this.field_76755_L.clear();
      this.field_82914_M.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM, 10).setSpawnChance(50);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARRIORS, 2).setSpawnChance(50);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 1).setSpawnChance(50);
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
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.2F;
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityRabbit.class, 6, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntitySwan.class, 15, 4, 8));
      this.variantChance = 0.7F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT, 2.0F);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.CLEARING, 0.5F);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreQuendite, 6), 6.0F, 0, 48);
      this.enablePodzol = false;
      this.decorator.treesPerChunk = 3;
      this.decorator.willowPerChunk = 2;
      this.decorator.flowersPerChunk = 6;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.generateLava = false;
      this.decorator.generateCobwebs = false;
      this.decorator.whiteSand = true;
      this.decorator.addTree(LOTRTreeType.OAK, 300);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.LARCH, 200);
      this.decorator.addTree(LOTRTreeType.BEECH, 100);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.MALLORN, 300);
      this.decorator.addTree(LOTRTreeType.MALLORN_BOUGHS, 600);
      this.decorator.addTree(LOTRTreeType.MALLORN_PARTY, 100);
      this.decorator.addTree(LOTRTreeType.MALLORN_EXTREME, 30);
      this.decorator.addTree(LOTRTreeType.ASPEN, 100);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.LAIRELOSSE, 50);
      this.registerForestFlowers();
      this.addFlower(LOTRMod.elanor, 0, 30);
      this.addFlower(LOTRMod.niphredil, 0, 20);
      this.biomeColors.setGrass(11527451);
      this.biomeColors.setFog(16770660);
      this.decorator.addRandomStructure(new LOTRWorldGenGaladhrimForge(false), 120);
      this.registerTravellingTrader(LOTREntityRivendellTrader.class);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterLothlorien;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.LOTHLORIEN;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.LOTHLORIEN.getSubregion("lothlorien");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.GALADHRIM;
   }

   public boolean hasSeasonalGrass() {
      return false;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      for(int l = 0; l < 120; ++l) {
         int i1 = i + random.nextInt(16) + 8;
         int j1 = 60 + random.nextInt(50);
         int k1 = k + random.nextInt(16) + 8;
         if (world.func_147437_c(i1, j1 - 1, k1) && world.func_147437_c(i1, j1, k1) && world.func_147437_c(i1, j1 + 1, k1)) {
            Block torchBlock = LOTRWorldGenElfHouse.getRandomTorch(random);
            if (world.func_147439_a(i1 - 1, j1, k1) == LOTRMod.wood && world.func_72805_g(i1 - 1, j1, k1) == 1 && world.func_147437_c(i1 - 1, j1, k1 - 1) && world.func_147437_c(i1 - 1, j1, k1 + 1)) {
               world.func_147465_d(i1, j1, k1, torchBlock, 1, 2);
            } else if (world.func_147439_a(i1 + 1, j1, k1) == LOTRMod.wood && world.func_72805_g(i1 + 1, j1, k1) == 1 && world.func_147437_c(i1 + 1, j1, k1 - 1) && world.func_147437_c(i1 + 1, j1, k1 + 1)) {
               world.func_147465_d(i1, j1, k1, torchBlock, 2, 2);
            } else if (world.func_147439_a(i1, j1, k1 - 1) == LOTRMod.wood && world.func_72805_g(i1, j1, k1 - 1) == 1 && world.func_147437_c(i1 - 1, j1, k1 - 1) && world.func_147437_c(i1 + 1, j1, k1 - 1)) {
               world.func_147465_d(i1, j1, k1, torchBlock, 3, 2);
            } else if (world.func_147439_a(i1, j1, k1 + 1) == LOTRMod.wood && world.func_72805_g(i1, j1, k1 + 1) == 1 && world.func_147437_c(i1 - 1, j1, k1 + 1) && world.func_147437_c(i1 + 1, j1, k1 + 1)) {
               world.func_147465_d(i1, j1, k1, torchBlock, 4, 2);
            }
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }
}
