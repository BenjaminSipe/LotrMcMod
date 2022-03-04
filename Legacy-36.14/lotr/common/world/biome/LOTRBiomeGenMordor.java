package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenMordorMoss;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenMordorTower;
import lotr.common.world.structure2.LOTRWorldGenBlackUrukFort;
import lotr.common.world.structure2.LOTRWorldGenMordorCamp;
import lotr.common.world.structure2.LOTRWorldGenMordorWargPit;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenMordor extends LOTRBiome {
   protected WorldGenerator boulderGen;
   protected boolean enableMordorBoulders;
   protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(389502092662L), 1);
   protected static NoiseGeneratorPerlin noiseGravel = new NoiseGeneratorPerlin(new Random(1379468206L), 1);

   public LOTRBiomeGenMordor(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 0, 2, 8);
      this.enableMordorBoulders = true;
      this.field_76752_A = LOTRMod.rock;
      this.topBlockMeta = 0;
      this.field_76753_B = LOTRMod.rock;
      this.fillerBlockMeta = 0;
      if (this.isGorgoroth()) {
         this.func_76745_m();
      }

      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[5];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 5);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 30);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 10);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 7);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(1);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WICKED_DWARVES, 10);
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
      this.npcSpawnList.conquestGainRate = 0.5F;
      this.decorator.clearOres();
      this.decorator.addSoil(new WorldGenMinable(LOTRMod.mordorDirt, 0, 60, LOTRMod.rock), 10.0F, 0, 60);
      this.decorator.addSoil(new WorldGenMinable(LOTRMod.mordorGravel, 0, 32, LOTRMod.rock), 10.0F, 0, 60);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreNaurite, 12, LOTRMod.rock), 20.0F, 0, 64);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreMorgulIron, 1, 8, LOTRMod.rock), 20.0F, 0, 64);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 1, 8, LOTRMod.rock), 6.0F, 0, 32);
      this.decorator.flowersPerChunk = 0;
      this.decorator.grassPerChunk = 1;
      this.decorator.generateWater = false;
      if (this.isGorgoroth()) {
         this.decorator.sandPerChunk = 0;
         this.decorator.clayPerChunk = 0;
         this.decorator.dryReedChance = 1.0F;
         this.enableRocky = false;
      }

      this.decorator.addTree(LOTRTreeType.CHARRED, 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenMordorCamp(false), 100);
      this.decorator.addRandomStructure(new LOTRWorldGenMordorWargPit(false), 300);
      this.decorator.addRandomStructure(new LOTRWorldGenMordorTower(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenBlackUrukFort(false), 2000);
      this.biomeColors.setGrass(5980459);
      this.biomeColors.setFoliage(6508333);
      this.biomeColors.setSky(6700595);
      this.biomeColors.setClouds(4924185);
      this.biomeColors.setFog(3154711);
      this.biomeColors.setWater(2498845);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public boolean hasSky() {
      return !this.isGorgoroth();
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterMordor;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.MORDOR;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.MORDOR.getSubregion("mordor");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.MORDOR;
   }

   public LOTRRoadType.BridgeType getBridgeBlock() {
      return LOTRRoadType.BridgeType.CHARRED;
   }

   public boolean isGorgoroth() {
      return true;
   }

   protected boolean hasMordorSoils() {
      return true;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      if (this.isGorgoroth() && this.hasMordorSoils()) {
         double d1 = noiseDirt.func_151601_a((double)i * 0.08D, (double)k * 0.08D);
         double d2 = noiseDirt.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
         double d3 = noiseGravel.func_151601_a((double)i * 0.08D, (double)k * 0.08D);
         double d4 = noiseGravel.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
         if (d3 + d4 > 0.8D) {
            this.field_76752_A = LOTRMod.mordorGravel;
            this.topBlockMeta = 0;
            this.field_76753_B = this.field_76752_A;
            this.fillerBlockMeta = this.topBlockMeta;
         } else if (d1 + d2 > 0.5D) {
            this.field_76752_A = LOTRMod.mordorDirt;
            this.topBlockMeta = 0;
            this.field_76753_B = this.field_76752_A;
            this.fillerBlockMeta = this.topBlockMeta;
         }
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      for(int j = ySize - 1; j >= 0; --j) {
         int index = xzIndex * ySize + j;
         Block block = blocks[index];
         if (block == Blocks.field_150348_b) {
            blocks[index] = LOTRMod.rock;
            meta[index] = 0;
         }
      }

   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int i1;
      int k1;
      int j1;
      if (this.isGorgoroth()) {
         if (this.enableMordorBoulders && random.nextInt(24) == 0) {
            for(i1 = 0; i1 < 6; ++i1) {
               k1 = i + random.nextInt(16) + 8;
               j1 = k + random.nextInt(16) + 8;
               this.boulderGen.func_76484_a(world, random, k1, world.func_72976_f(k1, j1), j1);
            }
         }

         int j1;
         if (random.nextInt(60) == 0) {
            for(i1 = 0; i1 < 8; ++i1) {
               k1 = i + random.nextInt(16) + 8;
               j1 = k + random.nextInt(16) + 8;
               j1 = world.func_72976_f(k1, j1);
               this.decorator.genTree(world, random, k1, j1, j1);
            }
         }

         if (this.decorator.grassPerChunk > 0) {
            if (random.nextInt(20) == 0) {
               for(i1 = 0; i1 < 6; ++i1) {
                  k1 = i + random.nextInt(6) + 8;
                  j1 = k + random.nextInt(6) + 8;
                  j1 = world.func_72976_f(k1, j1);
                  if (world.func_147437_c(k1, j1, j1) && LOTRMod.mordorThorn.func_149718_j(world, k1, j1, j1)) {
                     world.func_147465_d(k1, j1, j1, LOTRMod.mordorThorn, 0, 2);
                  }
               }
            }

            if (random.nextInt(20) == 0) {
               i1 = i + random.nextInt(16) + 8;
               k1 = k + random.nextInt(16) + 8;
               j1 = world.func_72976_f(i1, k1);
               if (world.func_147437_c(i1, j1, k1) && LOTRMod.mordorMoss.func_149718_j(world, i1, j1, k1)) {
                  (new LOTRWorldGenMordorMoss()).func_76484_a(world, random, i1, j1, k1);
               }
            }
         }
      }

      if (LOTRFixedStructures.MORDOR_CHERRY_TREE.isAt(world, i, k)) {
         i1 = i + 8;
         k1 = k + 8;
         j1 = world.func_72976_f(i1, k1);
         LOTRTreeType.CHERRY_MORDOR.create(false, random).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.0F;
   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return this.isGorgoroth() ? new LOTRBiome.GrassBlockAndMeta(LOTRMod.mordorGrass, 0) : super.getRandomGrass(random);
   }

   public float getChanceToSpawnAnimals() {
      return 0.0F;
   }

   public boolean canSpawnHostilesInDay() {
      return true;
   }

   public static boolean isSurfaceMordorBlock(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      int meta = world.func_72805_g(i, j, k);
      return block == LOTRMod.rock && meta == 0 || block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel;
   }
}
