package lotr.common.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class ByWaterConfig implements IPlacementConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(Codec.INT.fieldOf("range").forGetter((config) -> {
         return config.range;
      }), Codec.INT.fieldOf("tries").forGetter((config) -> {
         return config.tries;
      })).apply(instance, ByWaterConfig::new);
   });
   public final int range;
   public final int tries;

   public ByWaterConfig(int range, int tries) {
      this.range = range;
      this.tries = tries;
   }
}
