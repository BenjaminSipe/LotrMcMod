package lotr.common.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

public class AtSurfaceLayerLimitedWithExtra extends Placement {
   public AtSurfaceLayerLimitedWithExtra(Codec codec) {
      super(codec);
   }

   public Stream getPositions(WorldDecoratingHelper helper, Random rand, AtSurfaceLayerLimitedWithExtraConfig config, BlockPos pos) {
      int i = config.count;
      if (rand.nextFloat() < config.extraChance) {
         i += config.extraCount;
      }

      return IntStream.range(0, i).mapToObj((index) -> {
         int x = rand.nextInt(16) + pos.func_177958_n();
         int z = rand.nextInt(16) + pos.func_177952_p();
         int y = helper.func_242893_a(Type.MOTION_BLOCKING, x, z);
         return new BlockPos(x, y, z);
      }).filter((aPos) -> {
         if (config.isLayerUpperLimit) {
            return aPos.func_177956_o() <= config.layerLimit;
         } else {
            return aPos.func_177956_o() >= config.layerLimit;
         }
      });
   }
}
