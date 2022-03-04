package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class MordorBasaltFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BlockState.field_235877_b_.listOf().fieldOf("surface_blocks").forGetter((config) -> {
         return (List)config.surfaceBlocks.stream().map(Block::func_176223_P).collect(Collectors.toList());
      }), FeatureSpread.func_242254_a(1, 16, 15).fieldOf("radius").forGetter((config) -> {
         return config.radius;
      }), FeatureSpread.func_242254_a(1, 6, 5).fieldOf("depth").forGetter((config) -> {
         return config.depth;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("min_density").orElse(0.4F).forGetter((config) -> {
         return config.minDensity;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("max_density").orElse(0.95F).forGetter((config) -> {
         return config.minDensity;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("min_prominence").orElse(0.0F).forGetter((config) -> {
         return config.minProminence;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("max_prominence").orElse(0.3F).forGetter((config) -> {
         return config.maxProminence;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("lava_chance").orElse(0.2F).forGetter((config) -> {
         return config.lavaChance;
      })).apply(instance, MordorBasaltFeatureConfig::new);
   });
   public final Set surfaceBlocks;
   public final FeatureSpread radius;
   public final FeatureSpread depth;
   public final float minDensity;
   public final float maxDensity;
   public final float minProminence;
   public final float maxProminence;
   public final float lavaChance;

   public MordorBasaltFeatureConfig(List surfaceBlocks, FeatureSpread radius, FeatureSpread depth, float minDensity, float maxDensity, float minProminence, float maxProminence, float lavaChance) {
      this((Set)surfaceBlocks.stream().map(AbstractBlockState::func_177230_c).collect(Collectors.toSet()), radius, depth, minDensity, maxDensity, minProminence, maxProminence, lavaChance);
   }

   public MordorBasaltFeatureConfig(Set surfaceBlocks, FeatureSpread radius, FeatureSpread depth, float minDensity, float maxDensity, float minProminence, float maxProminence, float lavaChance) {
      this.surfaceBlocks = surfaceBlocks;
      this.radius = radius;
      this.depth = depth;
      this.minDensity = minDensity;
      this.maxDensity = maxDensity;
      this.minProminence = minProminence;
      this.maxProminence = maxProminence;
      this.lavaChance = lavaChance;
   }
}
