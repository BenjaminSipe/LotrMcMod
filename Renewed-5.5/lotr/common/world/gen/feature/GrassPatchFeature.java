package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.Feature;

public class GrassPatchFeature extends Feature {
   public GrassPatchFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, GrassPatchFeatureConfig config) {
      BlockState below = world.func_180495_p(pos.func_177977_b());
      if (!config.targetStates.contains(below)) {
         return false;
      } else {
         int r = MathHelper.func_76136_a(rand, config.minRadius, config.maxRadius);
         int depth = MathHelper.func_76136_a(rand, config.minDepth, config.maxDepth);
         Mutable movingPos = new Mutable();

         for(int x = -r; x <= r; ++x) {
            for(int z = -r; z <= r; ++z) {
               movingPos.func_189533_g(pos).func_196234_d(x, 0, z);
               if (x * x + z * z < r * r) {
                  BlockPos heightPos = world.func_205770_a(Type.MOTION_BLOCKING, movingPos);
                  if (Math.abs(heightPos.func_177956_o() - pos.func_177956_o()) <= 3) {
                     boolean coarse = rand.nextInt(5) == 0;

                     for(int y = 0; y < depth; ++y) {
                        movingPos.func_189533_g(heightPos).func_196234_d(0, -y - 1, 0);
                        BlockState state = world.func_180495_p(movingPos);
                        if (!config.targetStates.contains(state)) {
                           break;
                        }

                        if (coarse) {
                           world.func_180501_a(movingPos, Blocks.field_196660_k.func_176223_P(), 3);
                        } else if (y == 0) {
                           world.func_180501_a(movingPos, Blocks.field_196658_i.func_176223_P(), 3);
                        } else {
                           world.func_180501_a(movingPos, Blocks.field_150346_d.func_176223_P(), 3);
                        }
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
