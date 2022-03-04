package lotr.common.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

public class ByWater extends Placement {
   public ByWater(Codec codec) {
      super(codec);
   }

   public Stream getPositions(WorldDecoratingHelper helper, Random rand, ByWaterConfig config, BlockPos pos) {
      Mutable nearbyPos = new Mutable();

      for(int l = 0; l < config.tries; ++l) {
         int dx = MathHelper.func_76136_a(rand, -config.range, config.range);
         int dy = MathHelper.func_76136_a(rand, -config.range, config.range);
         int dz = MathHelper.func_76136_a(rand, -config.range, config.range);
         nearbyPos.func_239621_a_(pos, dx, dy, dz);
         if (helper.func_242894_a(nearbyPos).func_185904_a() == Material.field_151586_h) {
            return Stream.of(pos);
         }
      }

      return Stream.empty();
   }
}
