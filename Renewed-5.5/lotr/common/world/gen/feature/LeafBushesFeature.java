package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;

public class LeafBushesFeature extends Feature {
   public LeafBushesFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      BlockState leafBlockState = null;
      int searchTries = 40;
      int searchXRange = 6;
      int searchYRangeUp = 12;
      int searchZRange = 6;
      Mutable movingPos = new Mutable();

      int size;
      for(int i = 0; i < searchTries; ++i) {
         int x = MathHelper.func_76136_a(rand, -searchXRange, searchXRange);
         size = rand.nextInt(searchYRangeUp + 1);
         int z = MathHelper.func_76136_a(rand, -searchZRange, searchZRange);
         movingPos.func_189533_g(pos).func_196234_d(x, size, z);
         BlockState state = world.func_180495_p(movingPos);
         if (state.func_235714_a_(BlockTags.field_206952_E)) {
            leafBlockState = state.func_177230_c().func_176223_P();
            if (leafBlockState.func_235901_b_(LeavesBlock.field_208495_b)) {
               leafBlockState = (BlockState)leafBlockState.func_206870_a(LeavesBlock.field_208495_b, true);
            }
            break;
         }
      }

      if (leafBlockState != null) {
         BlockPos belowPos = pos.func_177977_b();
         BlockState below = world.func_180495_p(belowPos);
         if (below.canSustainPlant(world, belowPos, Direction.UP, (IPlantable)Blocks.field_196674_t)) {
            size = 0;
            if (rand.nextInt(3) == 0) {
               ++size;
            }

            int y = 0;

            for(int x = -size; x <= size; ++x) {
               for(int z = -size; z <= size; ++z) {
                  if (size == 0 || Math.abs(x) != size || Math.abs(z) != size || rand.nextInt(3) == 0) {
                     movingPos.func_189533_g(pos).func_196234_d(x, y, z);
                     BlockState state = world.func_180495_p(movingPos);
                     if (!state.func_185904_a().func_76224_d() && state.func_185904_a().func_76222_j()) {
                        world.func_180501_a(movingPos, leafBlockState, 2);
                     }
                  }
               }
            }

            return true;
         }
      }

      return false;
   }
}
