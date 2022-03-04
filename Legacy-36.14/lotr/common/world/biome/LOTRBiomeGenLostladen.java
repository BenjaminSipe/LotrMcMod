package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenLostladen extends LOTRBiome {
   private static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(486938207230702L), 1);
   private static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(28507830789060732L), 1);
   private static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(275928960292060726L), 1);
   private WorldGenerator boulderGen;
   private WorldGenerator boulderGenSandstone;

   public LOTRBiomeGenLostladen(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.boulderGenSandstone = new LOTRWorldGenBoulder(Blocks.field_150322_A, 0, 1, 3);
      this.field_76762_K.clear();
      this.npcSpawnList.clear();
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK, 3.0F);
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150369_x, 6), 1.0F, 0, 48);
      this.decorator.treesPerChunk = 0;
      this.decorator.grassPerChunk = 3;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.flowersPerChunk = 1;
      this.decorator.cactiPerChunk = 1;
      this.decorator.deadBushPerChunk = 2;
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 200);
      this.registerHaradFlowers();
      this.biomeColors.setSky(15592678);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.setBanditEntityClass(LOTREntityBanditHarad.class);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterLostladen;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.LOSTLADEN;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.NEAR_HARAD.getSubregion("lostladen");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.HARAD.setRepair(0.3F);
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseDirt.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d2 = noiseDirt.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      double d3 = noiseSand.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d4 = noiseSand.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      double d5 = noiseStone.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d6 = noiseStone.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      if (d5 + d6 > 0.3D) {
         if (random.nextInt(5) == 0) {
            this.field_76752_A = Blocks.field_150351_n;
            this.topBlockMeta = 0;
         } else {
            this.field_76752_A = Blocks.field_150348_b;
            this.topBlockMeta = 0;
         }

         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d3 + d4 > 0.1D) {
         if (random.nextInt(5) == 0) {
            this.field_76752_A = Blocks.field_150322_A;
            this.topBlockMeta = 0;
         } else {
            this.field_76752_A = Blocks.field_150354_m;
            this.topBlockMeta = 0;
         }

         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 > -0.2D) {
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
      if (random.nextInt(20) == 0) {
         int boulders = 1 + random.nextInt(4);

         for(int l = 0; l < boulders; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            int j1 = world.func_72976_f(i1, k1);
            if (random.nextBoolean()) {
               this.boulderGen.func_76484_a(world, random, i1, j1, k1);
            } else {
               this.boulderGenSandstone.func_76484_a(world, random, i1, j1, k1);
            }
         }
      }

   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return random.nextBoolean() ? new LOTRBiome.GrassBlockAndMeta(LOTRMod.aridGrass, 0) : super.getRandomGrass(random);
   }

   public float getTreeIncreaseChance() {
      return 0.01F;
   }
}
