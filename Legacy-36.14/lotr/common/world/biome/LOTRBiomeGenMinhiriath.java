package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenMinhiriath extends LOTRBiomeGenEriador {
   protected WorldGenerator boulderGen;

   public LOTRBiomeGenMinhiriath(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.clearBiomeVariants();
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_SPRUCE);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK_SPRUCE);
      this.decorator.grassPerChunk = 5;
      this.decorator.doubleGrassPerChunk = 3;
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
      this.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 300);
      this.decorator.addTree(LOTRTreeType.BEECH_DEAD, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH_DEAD, 50);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterMinhiriath;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.ERIADOR.getSubregion("minhiriath");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(16) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.05F;
   }
}
