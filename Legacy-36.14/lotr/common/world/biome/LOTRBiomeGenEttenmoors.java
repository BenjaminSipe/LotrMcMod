package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanHouse;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanVillage;
import lotr.common.world.structure2.LOTRWorldGenRhudaurCastle;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenEttenmoors extends LOTRBiome {
   private WorldGenerator boulderGenLarge;
   private WorldGenerator boulderGenSmall;

   public LOTRBiomeGenEttenmoors(int i, boolean major) {
      super(i, major);
      this.boulderGenLarge = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 2, 5);
      this.boulderGenSmall = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 2);
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(EntityWolf.class, 10, 4, 8));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityElk.class, 6, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 6, 1, 4));
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(35);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 30);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 7);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(70);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[5];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 40);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HILL_TROLLS, 20);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 20).setSpawnChance(500);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 15);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 5);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.75F;
      this.biomeTerrain.setXZScale(100.0D);
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 1.0F);
      this.decorator.biomeGemFactor = 0.75F;
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 4;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.generateAthelas = true;
      this.decorator.addTree(LOTRTreeType.FIR, 400);
      this.decorator.addTree(LOTRTreeType.PINE, 800);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 500);
      this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 500);
      this.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 200);
      this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 100);
      this.registerTaigaFlowers();
      this.decorator.generateOrcDungeon = true;
      this.decorator.generateTrollHoard = true;
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 100);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 100);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ANGMAR(1, 4), 100);
      this.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanHouse(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
      this.decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 3000);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.COMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.COMMON);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterEttenmoors;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.ETTENMOORS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.ANGMAR.getSubregion("ettenmoors");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int l;
      int i1;
      int k1;
      for(l = 0; l < 3; ++l) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         int j1 = world.func_72976_f(i1, k1);
         if (j1 > 84) {
            this.decorator.genTree(world, random, i1, j1, k1);
         }
      }

      if (random.nextInt(4) == 0) {
         for(l = 0; l < 3; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderGenLarge.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

      for(l = 0; l < 2; ++l) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         this.boulderGenSmall.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }
}
