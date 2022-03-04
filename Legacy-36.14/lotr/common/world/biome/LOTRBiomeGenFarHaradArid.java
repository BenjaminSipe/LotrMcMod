package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenFarHaradArid extends LOTRBiomeGenFarHaradSavannah {
   protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(35952060662L), 1);
   protected static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(5925366672L), 1);

   public LOTRBiomeGenFarHaradArid(int i, boolean major) {
      super(i, major);
      this.decorator.flowersPerChunk = 1;
      this.decorator.doubleFlowersPerChunk = 1;
      this.spawnableLOTRAmbientList.clear();
      this.biomeColors.setGrass(14073692);
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseDirt.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d2 = noiseDirt.func_151601_a((double)i * 0.15D, (double)k * 0.15D);
      double d3 = noiseSand.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d4 = noiseSand.func_151601_a((double)i * 0.15D, (double)k * 0.15D);
      if (d3 + d4 > 0.6D) {
         this.field_76752_A = Blocks.field_150354_m;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 > 0.1D) {
         this.field_76752_A = Blocks.field_150346_d;
         this.topBlockMeta = 1;
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return random.nextBoolean() ? new LOTRBiome.GrassBlockAndMeta(LOTRMod.aridGrass, 0) : super.getRandomGrass(random);
   }

   public float getChanceToSpawnAnimals() {
      return super.getChanceToSpawnAnimals() * 0.5F;
   }
}
