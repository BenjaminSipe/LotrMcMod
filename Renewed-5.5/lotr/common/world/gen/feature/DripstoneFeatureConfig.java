package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import lotr.common.block.DripstoneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class DripstoneFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BlockState.field_235877_b_.optionalFieldOf("forced_blockstate").forGetter((config) -> {
         return Optional.ofNullable(config.forcedBlockState);
      }), Codec.INT.fieldOf("tries").orElse(64).forGetter((config) -> {
         return config.tries;
      }), Codec.INT.fieldOf("xspread").orElse(8).forGetter((config) -> {
         return config.xspread;
      }), Codec.INT.fieldOf("yspread").orElse(4).forGetter((config) -> {
         return config.yspread;
      }), Codec.INT.fieldOf("zspread").orElse(8).forGetter((config) -> {
         return config.zspread;
      }), Codec.FLOAT.fieldOf("doubleChance").orElse(0.33F).forGetter((config) -> {
         return config.doubleChance;
      })).apply(instance, DripstoneFeatureConfig::new);
   });
   public final BlockState forcedBlockState;
   public final int tries;
   public final int xspread;
   public final int yspread;
   public final int zspread;
   public final float doubleChance;

   public DripstoneFeatureConfig(int t, int x, int y, int z, float dc) {
      this((BlockState)null, t, x, y, z, dc);
   }

   private DripstoneFeatureConfig(Optional state, int t, int x, int y, int z, float dc) {
      this((BlockState)state.orElse((Object)null), t, x, y, z, dc);
   }

   public DripstoneFeatureConfig(BlockState state, int t, int x, int y, int z, float dc) {
      this.forcedBlockState = state;
      this.tries = t;
      this.xspread = x;
      this.yspread = y;
      this.zspread = z;
      this.doubleChance = dc;
   }

   public boolean hasForcedDripstoneState() {
      return this.forcedBlockState != null && this.forcedBlockState.func_177230_c() instanceof DripstoneBlock;
   }
}
