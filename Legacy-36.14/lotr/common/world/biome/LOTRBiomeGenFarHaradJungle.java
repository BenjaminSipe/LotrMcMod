package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityFlamingo;
import lotr.common.entity.animal.LOTREntityJungleScorpion;
import lotr.common.entity.animal.LOTREntityMidges;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenObsidianGravel;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradJungle extends LOTRBiomeGenFarHarad {
   private WorldGenerator obsidianGen = new LOTRWorldGenObsidianGravel();
   protected int obsidianGravelRarity = 20;

   public LOTRBiomeGenFarHaradJungle(int i, boolean major) {
      super(i, major);
      if (this.isMuddy()) {
         this.field_76752_A = LOTRMod.mudGrass;
         this.field_76753_B = LOTRMod.mud;
      }

      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(LOTREntityFlamingo.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 15, 4, 4));
      if (this.isMuddy()) {
         this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityMidges.class, 10, 4, 4));
      }

      this.field_76761_J.add(new SpawnListEntry(LOTREntityJungleScorpion.class, 30, 4, 4));
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 10).setSpawnChance(5000);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 30).setSpawnChance(5000);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 4);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
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
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
      var10000.add(var10001);
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
      this.addBiomeVariant(LOTRBiomeVariant.JUNGLE_DENSE);
      if (this.isMuddy()) {
         this.decorator.addSoil(new WorldGenMinable(LOTRMod.mud, 32), 80.0F, 0, 256);
         this.decorator.addSoil(new WorldGenMinable(LOTRMod.mud, 32), 80.0F, 0, 64);
      }

      this.decorator.addOre(new WorldGenMinable(Blocks.field_150352_o, 4), 3.0F, 0, 48);
      this.decorator.addGem(new WorldGenMinable(LOTRMod.oreGem, 4, 8, Blocks.field_150348_b), 3.0F, 0, 48);
      this.decorator.treesPerChunk = 40;
      this.decorator.vinesPerChunk = 50;
      this.decorator.flowersPerChunk = 4;
      this.decorator.doubleFlowersPerChunk = 4;
      this.decorator.grassPerChunk = 15;
      this.decorator.doubleGrassPerChunk = 10;
      this.decorator.enableFern = true;
      this.decorator.canePerChunk = 5;
      this.decorator.cornPerChunk = 10;
      this.decorator.melonPerChunk = 0.2F;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.JUNGLE, 1000);
      this.decorator.addTree(LOTRTreeType.JUNGLE_LARGE, 500);
      this.decorator.addTree(LOTRTreeType.MAHOGANY, 500);
      this.decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
      this.decorator.addTree(LOTRTreeType.MANGO, 20);
      this.decorator.addTree(LOTRTreeType.BANANA, 50);
      this.registerJungleFlowers();
      this.biomeColors.setGrass(10607421);
      this.biomeColors.setFoliage(8376636);
      this.biomeColors.setSky(11977908);
      this.biomeColors.setFog(11254938);
      this.biomeColors.setWater(4104311);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.TAUREDAIN(1, 4), 100);
      this.invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterFarHaradJungle;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.FAR_HARAD_JUNGLE;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("jungle");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.TAUREDAIN.setRepair(0.8F);
   }

   public boolean hasJungleLakes() {
      return true;
   }

   public boolean isMuddy() {
      return true;
   }

   protected double modifyStoneNoiseForFiller(double stoneNoise) {
      if (this.isMuddy()) {
         stoneNoise += 40.0D;
      }

      return stoneNoise;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      WorldGenVines vines = new WorldGenVines();

      int i1;
      int k1;
      for(i1 = 0; i1 < 10; ++i1) {
         k1 = i + random.nextInt(16) + 8;
         int j1 = 24;
         int k1 = k + random.nextInt(16) + 8;
         vines.func_76484_a(world, random, k1, j1, k1);
      }

      if (this.obsidianGravelRarity > 0 && random.nextInt(this.obsidianGravelRarity) == 0) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         int j1 = world.func_72825_h(i1, k1);
         this.obsidianGen.func_76484_a(world, random, i1, j1, k1);
      }

   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return random.nextInt(4) == 0 ? new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 5) : super.getRandomGrass(random);
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }
}
