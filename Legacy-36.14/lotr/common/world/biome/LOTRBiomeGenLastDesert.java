package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenLastDesert extends LOTRBiome {
   protected WorldGenerator boulderGen;

   public LOTRBiomeGenLastDesert(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.func_76745_m();
      this.field_76752_A = Blocks.field_150354_m;
      this.field_76753_B = Blocks.field_150354_m;
      this.field_76762_K.clear();
      this.field_76762_K.add(new SpawnListEntry(LOTREntityCamel.class, 10, 2, 6));
      this.spawnableLOTRAmbientList.clear();
      this.field_76761_J.add(new SpawnListEntry(LOTREntityDesertScorpion.class, 10, 4, 4));
      this.npcSpawnList.clear();
      this.variantChance = 0.3F;
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.decorator.grassPerChunk = 0;
      this.decorator.doubleGrassPerChunk = 0;
      this.decorator.flowersPerChunk = 0;
      this.decorator.doubleFlowersPerChunk = 0;
      this.decorator.cactiPerChunk = 0;
      this.decorator.deadBushPerChunk = 0;
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
      this.registerRhunPlainsFlowers();
      this.biomeColors.setGrass(16767886);
      this.biomeColors.setSky(14736588);
      this.biomeColors.setClouds(10853237);
      this.biomeColors.setFog(14406319);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterLastDesert;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.RHUN;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.RHUN.getSubregion("lastDesert");
   }

   public boolean getEnableRiver() {
      return false;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      d2 *= 0.6D;
      if (d1 + d2 > 0.7D) {
         this.field_76752_A = Blocks.field_150349_c;
         this.topBlockMeta = 0;
         this.field_76753_B = Blocks.field_150346_d;
         this.fillerBlockMeta = 0;
      } else if (d1 + d2 > 0.2D) {
         this.field_76752_A = Blocks.field_150346_d;
         this.topBlockMeta = 1;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int trees;
      int l;
      int i1;
      if (random.nextInt(8) == 0) {
         trees = i + random.nextInt(16) + 8;
         l = k + random.nextInt(16) + 8;
         i1 = world.func_72976_f(trees, l);
         this.func_76730_b(random).func_76484_a(world, random, trees, i1, l);
      }

      if (random.nextInt(100) == 0) {
         trees = i + random.nextInt(16) + 8;
         l = k + random.nextInt(16) + 8;
         i1 = world.func_72976_f(trees, l);
         (new WorldGenCactus()).func_76484_a(world, random, trees, i1, l);
      }

      if (random.nextInt(20) == 0) {
         trees = i + random.nextInt(16) + 8;
         l = k + random.nextInt(16) + 8;
         i1 = world.func_72976_f(trees, l);
         (new WorldGenDeadBush(Blocks.field_150330_I)).func_76484_a(world, random, trees, i1, l);
      }

      int k1;
      if (random.nextInt(32) == 0) {
         trees = 1 + random.nextInt(4);

         for(l = 0; l < trees; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

      if (random.nextInt(500) == 0) {
         trees = 1 + random.nextInt(4);

         for(l = 0; l < trees; ++l) {
            i1 = i + random.nextInt(8) + 8;
            k1 = k + random.nextInt(8) + 8;
            int j1 = world.func_72976_f(i1, k1);
            this.decorator.genTree(world, random, i1, j1, k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.0F;
   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return new LOTRBiome.GrassBlockAndMeta(LOTRMod.aridGrass, 0);
   }

   public float getChanceToSpawnAnimals() {
      return 0.02F;
   }
}
