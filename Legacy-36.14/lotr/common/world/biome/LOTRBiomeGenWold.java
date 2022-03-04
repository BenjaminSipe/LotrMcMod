package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenRohanWatchtower;
import lotr.common.world.village.LOTRVillageGenRohan;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenWold extends LOTRBiomeGenRohan {
   private WorldGenerator woldBoulderGen;

   public LOTRBiomeGenWold(int i, boolean major) {
      super(i, major);
      this.woldBoulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 2, 2, 4);
      this.clearBiomeVariants();
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.decorator.treesPerChunk = 0;
      this.decorator.setTreeCluster(8, 100);
      this.decorator.flowersPerChunk = 1;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 400);
      this.decorator.addTree(LOTRTreeType.BEECH_DEAD, 400);
      this.registerPlainsFlowers();
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenRohanWatchtower(false), 1000);
      this.decorator.clearVillages();
      this.decorator.addVillage(new LOTRVillageGenRohan(this, 0.25F));
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.ROHAN.getSubregion("wold");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.005D, (double)k * 0.005D);
      double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      if (d1 + d2 > 1.0D) {
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
      int rocks;
      int l;
      int i1;
      if (random.nextInt(16) == 0) {
         for(rocks = 0; rocks < 4; ++rocks) {
            l = i + random.nextInt(16) + 8;
            i1 = k + random.nextInt(16) + 8;
            this.woldBoulderGen.func_76484_a(world, random, l, world.func_72976_f(l, i1), i1);
         }
      }

      if (random.nextInt(30) == 0) {
         rocks = 10 + random.nextInt(20);

         for(l = 0; l < rocks; ++l) {
            i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            int j1 = world.func_72976_f(i1, k1);
            Block block = world.func_147439_a(i1, j1 - 1, k1);
            if (block == this.field_76752_A || block == this.field_76753_B) {
               Block rockBlock;
               byte rockMeta;
               if (random.nextBoolean()) {
                  rockBlock = LOTRMod.rock;
                  rockMeta = 2;
               } else if (random.nextInt(5) == 0) {
                  rockBlock = Blocks.field_150351_n;
                  rockMeta = 0;
               } else {
                  rockBlock = Blocks.field_150348_b;
                  rockMeta = 0;
               }

               if (random.nextInt(3) == 0) {
                  world.func_147465_d(i1, j1 - 1, k1, rockBlock, rockMeta, 2);
               } else {
                  world.func_147465_d(i1, j1, k1, rockBlock, rockMeta, 2);
                  block.onPlantGrow(world, i1, j1 - 1, k1, i1, j1, k1);
               }
            }
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.1F;
   }

   public float getTreeIncreaseChance() {
      return 0.005F;
   }

   public int spawnCountMultiplier() {
      return super.spawnCountMultiplier() * 3;
   }
}
