package lotr.common.world.gen.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public enum ClassicRemoveSeaAtOriginLayer implements IAreaTransformer1 {
   INSTANCE;

   public int func_215728_a(IExtendedNoiseRandom noiseRand, IArea zoomedSeaLayer, int x, int z) {
      int sea = zoomedSeaLayer.func_202678_a(x, z);
      if (Math.abs(x) <= 1 && Math.abs(z) <= 1) {
         sea = 0;
      }

      return sea;
   }

   public int func_215721_a(int x) {
      return x;
   }

   public int func_215722_b(int z) {
      return z;
   }
}
