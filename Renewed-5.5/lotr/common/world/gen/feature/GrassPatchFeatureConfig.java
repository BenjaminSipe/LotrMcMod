package lotr.common.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class GrassPatchFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BlockState.field_235877_b_.listOf().fieldOf("block_targets").forGetter((config) -> {
         return ImmutableList.copyOf(config.targetStates);
      }), Codec.INT.fieldOf("min_radius").orElse(1).forGetter((config) -> {
         return config.minRadius;
      }), Codec.INT.fieldOf("max_radius").orElse(5).forGetter((config) -> {
         return config.maxRadius;
      }), Codec.INT.fieldOf("min_depth").orElse(4).forGetter((config) -> {
         return config.minDepth;
      }), Codec.INT.fieldOf("max_depth").orElse(5).forGetter((config) -> {
         return config.maxDepth;
      })).apply(instance, GrassPatchFeatureConfig::new);
   });
   public final List targetStates;
   public final int minRadius;
   public final int maxRadius;
   public final int minDepth;
   public final int maxDepth;

   public GrassPatchFeatureConfig(List states, int rMin, int rMax, int dMin, int dMax) {
      this.targetStates = states;
      this.minRadius = rMin;
      this.maxRadius = rMax;
      this.minDepth = dMin;
      this.maxDepth = dMax;
   }
}
