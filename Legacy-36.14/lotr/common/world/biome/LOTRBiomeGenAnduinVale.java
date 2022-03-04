package lotr.common.world.biome;

import java.util.Random;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenAnduinVale extends LOTRBiomeGenAnduin {
   private WorldGenerator valeBoulders;

   public LOTRBiomeGenAnduinVale(int i, boolean major) {
      super(i, major);
      this.valeBoulders = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 5);
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2F);
      this.variantChance = 0.3F;
      this.decorator.setTreeCluster(10, 20);
      this.decorator.willowPerChunk = 2;
      this.decorator.flowersPerChunk = 5;
      this.decorator.doubleFlowersPerChunk = 2;
      this.decorator.grassPerChunk = 10;
      this.decorator.doubleGrassPerChunk = 3;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(16) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.valeBoulders.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }
}
