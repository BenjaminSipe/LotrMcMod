package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class WeightedFeature {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(ConfiguredFeature.field_242763_a.fieldOf("feature").forGetter((config) -> {
         return config.feature;
      }), Codec.INT.fieldOf("weight").orElse(1).forGetter((config) -> {
         return config.weight;
      })).apply(instance, WeightedFeature::make);
   });
   public final ConfiguredFeature feature;
   public final int weight;

   private WeightedFeature(ConfiguredFeature feat, int w) {
      this.feature = feat;
      this.weight = w;
   }

   public static WeightedFeature make(ConfiguredFeature feature, int weight) {
      return new WeightedFeature(feature, weight);
   }
}
