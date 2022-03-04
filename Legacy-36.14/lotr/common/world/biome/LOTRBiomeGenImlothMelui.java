package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenImlothMelui extends LOTRBiomeGenLossarnach {
   public LOTRBiomeGenImlothMelui(int i, boolean major) {
      super(i, major);
      this.clearBiomeVariants();
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 20;
      this.decorator.doubleFlowersPerChunk = 12;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 3;
      this.registerPlainsFlowers();
      this.addFlower(Blocks.field_150328_O, 0, 80);
      this.decorator.addTree(LOTRTreeType.MAPLE, 500);
      this.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.BEECH, 500);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 100);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterImlothMelui;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int i1;
      int k1;
      for(i1 = 0; i1 < 1; ++i1) {
         WorldGenerator shrub = LOTRTreeType.OAK_SHRUB.create(false, random);
         k1 = i + random.nextInt(16) + 8;
         int k1 = k + random.nextInt(16) + 8;
         int j1 = world.func_72976_f(k1, k1);
         shrub.func_76484_a(world, random, k1, j1, k1);
      }

      if (random.nextInt(8) == 0) {
         i1 = i + random.nextInt(16) + 8;
         int j1 = random.nextInt(128);
         k1 = k + random.nextInt(16) + 8;
         (new WorldGenFlowers(LOTRMod.athelas)).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.5F;
   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      if (random.nextInt(5) > 0) {
         WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
         doubleFlowerGen.func_150548_a(4);
         return doubleFlowerGen;
      } else {
         return super.getRandomWorldGenForDoubleFlower(random);
      }
   }
}
