package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ReedsFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(BlockStateProvider.field_236796_a_.fieldOf("state_provider").forGetter((config) -> {
         return config.blockProvider;
      }), Codec.INT.fieldOf("tries").orElse(32).forGetter((config) -> {
         return config.tries;
      }), Codec.INT.fieldOf("xspread").orElse(5).forGetter((config) -> {
         return config.xspread;
      }), Codec.INT.fieldOf("yspread").orElse(2).forGetter((config) -> {
         return config.yspread;
      }), Codec.INT.fieldOf("zspread").orElse(5).forGetter((config) -> {
         return config.zspread;
      }), Codec.FLOAT.fieldOf("fully_grown_chance").orElse(0.75F).forGetter((config) -> {
         return config.fullyGrownChance;
      })).apply(instance, ReedsFeatureConfig::new);
   });
   public final BlockStateProvider blockProvider;
   public final int tries;
   public final int xspread;
   public final int yspread;
   public final int zspread;
   public final float fullyGrownChance;

   public ReedsFeatureConfig(BlockStateProvider blp, int t, int x, int y, int z, float fullChance) {
      this.blockProvider = blp;
      this.tries = t;
      this.xspread = x;
      this.yspread = y;
      this.zspread = z;
      this.fullyGrownChance = fullChance;
   }
}
