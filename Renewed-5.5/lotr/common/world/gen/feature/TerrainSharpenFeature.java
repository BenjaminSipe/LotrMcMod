package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class TerrainSharpenFeature extends Feature {
   public TerrainSharpenFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, TerrainSharpenFeatureConfig config) {
      BlockState state = world.func_180495_p(pos.func_177977_b());
      if (!config.targetStates.contains(state)) {
         return false;
      } else {
         int height = MathHelper.func_76136_a(rand, config.minHeight, config.maxHeight);
         Mutable movingPos = new Mutable();

         for(int y = 0; y < height; ++y) {
            movingPos.func_189533_g(pos).func_196234_d(0, y, 0);
            if (world.func_180495_p(movingPos).func_200015_d(world, movingPos)) {
               break;
            }

            world.func_180501_a(movingPos, state, 3);
         }

         return true;
      }
   }
}
