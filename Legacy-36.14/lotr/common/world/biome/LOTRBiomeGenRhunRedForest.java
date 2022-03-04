package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenRhunRedForest extends LOTRBiomeGenRhunLand {
   public LOTRBiomeGenRhunRedForest(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(EntityWolf.class, 16, 4, 8));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 20, 4, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 10, 1, 4));
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10).setSpawnChance(1000);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 8).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 1).setConquestOnly();
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELVES, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELF_WARRIORS, 3);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.5F;
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
      this.decorator.treesPerChunk = 6;
      this.decorator.logsPerChunk = 1;
      this.decorator.flowersPerChunk = 4;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.enableFern = true;
      this.decorator.addTree(LOTRTreeType.REDWOOD, 10000);
      this.decorator.addTree(LOTRTreeType.REDWOOD_2, 10000);
      this.decorator.addTree(LOTRTreeType.REDWOOD_3, 5000);
      this.decorator.addTree(LOTRTreeType.REDWOOD_4, 5000);
      this.decorator.addTree(LOTRTreeType.REDWOOD_5, 2000);
      this.registerForestFlowers();
      this.decorator.clearRandomStructures();
      this.decorator.clearVillages();
      this.biomeColors.resetGrass();
      this.biomeColors.setGrass(8951356);
      this.biomeColors.setFog(11259063);
      this.biomeColors.setFoggy(true);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterRhunRedwood;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      int chunkX = i & 15;
      int chunkZ = k & 15;
      int xzIndex = chunkX * 16 + chunkZ;
      int ySize = blocks.length / 256;
      if (variant.treeFactor >= 1.0F) {
         double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.05D, (double)k * 0.05D);
         double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
         if (d1 + d2 > -0.8D) {
            int index = xzIndex * ySize + height;
            if (random.nextFloat() < 0.75F) {
               blocks[index] = Blocks.field_150346_d;
               meta[index] = 2;
            }
         }
      }

   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return random.nextFloat() < 0.7F ? new LOTRBiome.GrassBlockAndMeta(Blocks.field_150329_H, 2) : super.getRandomGrass(random);
   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }
}
