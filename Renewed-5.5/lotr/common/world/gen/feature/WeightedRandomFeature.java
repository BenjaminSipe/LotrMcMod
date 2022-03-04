package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class WeightedRandomFeature extends Feature {
   public WeightedRandomFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, WeightedRandomFeatureConfig config) {
      ConfiguredFeature selectedFeature = config.getRandomFeature(rand);
      return selectedFeature.func_242765_a(world, generator, rand, pos);
   }
}
