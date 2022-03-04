package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenBrownLands extends LOTRBiome {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenBrownLands(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 2);
      this.func_76745_m();
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      this.npcSpawnList.clear();
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 0;
      this.decorator.grassPerChunk = 2;
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
      this.biomeColors.setGrass(11373417);
      this.biomeColors.setSky(8878434);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 3), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 1000);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.UNCOMMON);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterBrownLands;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.BROWN_LANDS;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.BROWN_LANDS.getSubregion("brownLands");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.08D, (double)k * 0.08D);
      double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.7D, (double)k * 0.7D);
      if (d1 + d2 > 0.1D) {
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
      if (random.nextInt(8) == 0) {
         int boulders = 1 + random.nextInt(6);

         for(int l = 0; l < boulders; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return random.nextInt(3) == 0 ? new LOTRBiome.GrassBlockAndMeta(Blocks.field_150329_H, 1) : new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 0);
   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }
}
