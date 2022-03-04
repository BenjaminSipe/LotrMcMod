package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class FallenLogFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(Codec.BOOL.fieldOf("stripped").orElse(false).forGetter((config) -> {
         return config.isStripped;
      }), Codec.BOOL.fieldOf("horizontal_only").orElse(false).forGetter((config) -> {
         return config.horizontalOnly;
      })).apply(instance, FallenLogFeatureConfig::new);
   });
   public final boolean isStripped;
   public final boolean horizontalOnly;

   public FallenLogFeatureConfig(boolean stripped, boolean horizontal) {
      this.isStripped = stripped;
      this.horizontalOnly = horizontal;
   }
}
