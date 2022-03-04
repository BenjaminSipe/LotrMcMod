package lotr.common.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class AtSurfaceLayerLimitedWithExtraConfig implements IPlacementConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(Codec.INT.fieldOf("count").forGetter((config) -> {
         return config.count;
      }), Codec.FLOAT.fieldOf("extra_chance").forGetter((config) -> {
         return config.extraChance;
      }), Codec.INT.fieldOf("extra_count").forGetter((config) -> {
         return config.extraCount;
      }), Codec.INT.fieldOf("layer_limit").forGetter((config) -> {
         return config.layerLimit;
      }), Codec.BOOL.fieldOf("is_upper_limit").orElse(true).forGetter((config) -> {
         return config.isLayerUpperLimit;
      })).apply(instance, AtSurfaceLayerLimitedWithExtraConfig::new);
   });
   public final int count;
   public final float extraChance;
   public final int extraCount;
   public final int layerLimit;
   public final boolean isLayerUpperLimit;

   public AtSurfaceLayerLimitedWithExtraConfig(int n, float chance, int extra, int layer, boolean isUpper) {
      this.count = n;
      this.extraChance = chance;
      this.extraCount = extra;
      this.layerLimit = layer;
      this.isLayerUpperLimit = isUpper;
   }
}
