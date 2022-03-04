package lotr.common.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TerrainSharpenFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BlockState.field_235877_b_.listOf().fieldOf("block_targets").forGetter((config) -> {
         return ImmutableList.copyOf(config.targetStates);
      }), Codec.INT.fieldOf("min_height").orElse(1).forGetter((config) -> {
         return config.minHeight;
      }), Codec.INT.fieldOf("max_height").orElse(3).forGetter((config) -> {
         return config.maxHeight;
      })).apply(instance, TerrainSharpenFeatureConfig::new);
   });
   public final List targetStates;
   public final int minHeight;
   public final int maxHeight;

   public TerrainSharpenFeatureConfig(List states, int min, int max) {
      this.targetStates = states;
      this.minHeight = min;
      this.maxHeight = max;
   }
}
