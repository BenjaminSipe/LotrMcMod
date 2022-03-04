package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.LOTRWorldGenHaradPyramid;
import lotr.common.world.structure2.LOTRWorldGenHaradRuinedFort;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenNearHaradSemiDesert extends LOTRBiomeGenNearHarad {
   public LOTRBiomeGenNearHaradSemiDesert(int i, boolean major) {
      super(i, major);
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 20).setSpawnChance(500);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 15).setSpawnChance(500);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS);
      var10000.add(var10001);
      this.clearBiomeVariants();
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
      this.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
      this.decorator.grassPerChunk = 5;
      this.decorator.doubleGrassPerChunk = 0;
      this.decorator.cactiPerChunk = 1;
      this.decorator.deadBushPerChunk = 1;
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenHaradPyramid(false), 4000);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 4), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
      this.decorator.clearVillages();
      this.decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.5F));
      this.registerTravellingTrader(LOTREntityNomadMerchant.class);
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.08D, (double)k * 0.08D);
      double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      if (d1 + d2 > 0.3D) {
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
         int i1 = i + random.nextInt(16) + 8;
         int k1 = k + random.nextInt(16) + 8;
         int j1 = world.func_72976_f(i1, k1) - 1;
         if (world.func_147439_a(i1, j1, k1) == Blocks.field_150354_m) {
            world.func_147449_b(i1, j1, k1, Blocks.field_150346_d);
            LOTRTreeType treeType = LOTRTreeType.OAK_DESERT;
            WorldGenerator tree = treeType.create(false, random);
            if (!tree.func_76484_a(world, random, i1, j1 + 1, k1)) {
               world.func_147449_b(i1, j1, k1, Blocks.field_150354_m);
            }
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.05F;
   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return new LOTRBiome.GrassBlockAndMeta(LOTRMod.aridGrass, 0);
   }
}
