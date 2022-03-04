package lotr.common.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum SeedBiomeSubtypesLayer implements IAreaTransformer0 {
   INSTANCE;

   private static final int RNG_BOUND = 1000;

   public int func_215735_a(INoiseRandom noiseRand, int x, int z) {
      return x == 0 && z == 0 ? 1000 : noiseRand.func_202696_a(1000);
   }

   public static final float randFloat(int initVal) {
      return (float)initVal / 1000.0F;
   }
}
