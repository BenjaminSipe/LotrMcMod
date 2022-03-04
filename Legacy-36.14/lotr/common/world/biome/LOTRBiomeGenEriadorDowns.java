package lotr.common.world.biome;

import java.util.Random;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenEriadorDowns extends LOTRBiomeGenEriador {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenEriadorDowns(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 4);
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.grassPerChunk = 5;
      this.decorator.doubleGrassPerChunk = 1;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(24) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }
}
