package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class WrappedTreeFeature extends Feature {
   public WrappedTreeFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, WrappedTreeFeatureConfig config) {
      BlockPos belowPos = pos.func_177977_b();
      BlockState belowState = world.func_180495_p(belowPos);
      boolean setAlternativeSoil = config.alternativeSoilType.testTerrain(belowState);
      if (setAlternativeSoil) {
         world.func_180501_a(belowPos, Blocks.field_150346_d.func_176223_P(), 1);
      }

      ConfiguredFeature tree = Feature.field_236291_c_.func_225566_b_(config.treeConfig);
      boolean generated = tree.func_242765_a(world, generator, rand, pos);
      if (setAlternativeSoil) {
         world.func_180501_a(belowPos, belowState, 1);
      }

      return generated;
   }
}
