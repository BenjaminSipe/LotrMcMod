package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure.LOTRWorldGenGondorRuin;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure.LOTRWorldGenRuinedGondorTower;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenAndrast extends LOTRBiomeGenGondor {
   protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(285939985023633003L), 1);
   protected static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(4148990259960304L), 1);
   private WorldGenerator boulderGen;
   private WorldGenerator boulderGenGondor;

   public LOTRBiomeGenAndrast(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 4);
      this.boulderGenGondor = new LOTRWorldGenBoulder(LOTRMod.rock, 1, 1, 4);
      this.npcSpawnList.clear();
      this.clearBiomeVariants();
      this.variantChance = 0.5F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0F);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
      this.addBiomeVariant(LOTRBiomeVariant.WASTELAND, 3.0F);
      this.decorator.setTreeCluster(10, 30);
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 12;
      this.decorator.doubleGrassPerChunk = 4;
      this.decorator.addTree(LOTRTreeType.OAK, 400);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
      this.decorator.addTree(LOTRTreeType.BIRCH, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.BEECH, 50);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.DARK_OAK, 500);
      this.decorator.addTree(LOTRTreeType.FIR, 200);
      this.decorator.addTree(LOTRTreeType.PINE, 200);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 200);
      this.decorator.addTree(LOTRTreeType.LARCH, 200);
      this.decorator.addTree(LOTRTreeType.APPLE, 5);
      this.decorator.addTree(LOTRTreeType.PEAR, 5);
      this.decorator.addTree(LOTRTreeType.PLUM, 5);
      this.decorator.addTree(LOTRTreeType.OAK_SHRUB, 1500);
      this.registerPlainsFlowers();
      this.biomeColors.setGrass(10202470);
      this.decorator.clearVillages();
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 2000);
      this.decorator.addRandomStructure(new LOTRWorldGenGondorRuin(false), 1500);
      this.clearTravellingTraders();
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
      this.invasionSpawns.clearInvasions();
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterAndrast;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.PUKEL.getSubregion("andrast");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseDirt.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d2 = noiseDirt.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      double d3 = noiseStone.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d4 = noiseStone.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      if (d3 + d4 > 1.1D) {
         this.field_76752_A = Blocks.field_150348_b;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 > 0.6D) {
         this.field_76752_A = Blocks.field_150346_d;
         this.topBlockMeta = 1;
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(5) == 0) {
         for(int l = 0; l < 4; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            if (random.nextBoolean()) {
               this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
            } else {
               this.boulderGenGondor.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
            }
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.5F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return 1;
   }
}
