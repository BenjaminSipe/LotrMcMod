package lotr.common.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum ClassicSeedSeasLayer implements IAreaTransformer0 {
   INSTANCE;

   public int func_215735_a(INoiseRandom noiseRand, int x, int z) {
      boolean sea = noiseRand.func_202696_a(4) == 0;
      return sea ? 1 : 0;
   }
}
