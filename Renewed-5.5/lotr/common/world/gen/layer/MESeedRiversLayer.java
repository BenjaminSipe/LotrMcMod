package lotr.common.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum MESeedRiversLayer implements IAreaTransformer0 {
   INSTANCE;

   public int func_215735_a(INoiseRandom context, int x, int z) {
      return context.func_202696_a(299999) + 2;
   }
}
