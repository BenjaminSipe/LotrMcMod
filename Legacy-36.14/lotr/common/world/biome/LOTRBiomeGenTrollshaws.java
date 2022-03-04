package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenRhudaurCastle;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenTrollshaws extends LOTRBiome {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenTrollshaws(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 2, 6);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 8, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(EntityWolf.class, 10, 4, 8));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 20, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityElk.class, 6, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 10, 1, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(10);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10).setSpawnChance(1000);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(60);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(40);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 10);
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
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
      this.decorator.treesPerChunk = 1;
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 2;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.generateAthelas = true;
      this.decorator.addTree(LOTRTreeType.OAK, 500);
      this.decorator.addTree(LOTRTreeType.OAK_TALL, 500);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.BEECH, 500);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 100);
      this.decorator.addTree(LOTRTreeType.FIR, 100);
      this.decorator.addTree(LOTRTreeType.PINE, 100);
      this.decorator.addTree(LOTRTreeType.MAPLE, 50);
      this.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.ASPEN, 100);
      this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
      this.registerForestFlowers();
      this.decorator.generateOrcDungeon = true;
      this.decorator.generateTrollHoard = true;
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 300);
      this.decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 1000);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterTrollshaws;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.TROLLSHAWS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.ERIADOR.getSubregion("trollshaws");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.ARNOR.setRepair(0.7F);
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int l;
      int i1;
      int k1;
      if (random.nextInt(8) == 0) {
         for(l = 0; l < 3; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

      for(l = 0; l < 10; ++l) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         int j1 = world.func_72976_f(i1, k1);
         if (j1 < 82) {
            this.decorator.genTree(world, random, i1, j1, k1);
         }
      }

   }
}
