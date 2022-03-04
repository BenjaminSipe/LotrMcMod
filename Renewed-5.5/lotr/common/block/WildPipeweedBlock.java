package lotr.common.block;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WildPipeweedBlock extends FlowerLikeBlock {
   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if (rand.nextInt(4) == 0) {
         Vector3d offset = state.func_191059_e(world, pos);
         double x = (double)pos.func_177958_n() + offset.field_72450_a;
         double y = (double)pos.func_177956_o() + offset.field_72448_b;
         double z = (double)pos.func_177952_p() + offset.field_72449_c;
         x += (double)MathHelper.func_151240_a(rand, 0.1F, 0.9F);
         y += (double)MathHelper.func_151240_a(rand, 0.5F, 0.75F);
         z += (double)MathHelper.func_151240_a(rand, 0.1F, 0.9F);
         world.func_195594_a(ParticleTypes.field_197601_L, x, y, z, 0.0D, 0.0D, 0.0D);
      }

   }
}
