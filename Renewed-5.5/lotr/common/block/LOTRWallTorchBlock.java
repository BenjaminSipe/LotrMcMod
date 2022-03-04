package lotr.common.block;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LOTRWallTorchBlock extends WallTorchBlock {
   private List torchParticles;

   public LOTRWallTorchBlock(Properties properties) {
      super(properties, ParticleTypes.field_197631_x);
      this.torchParticles = Arrays.asList(() -> {
         return ParticleTypes.field_197601_L;
      }, () -> {
         return ParticleTypes.field_197631_x;
      });
   }

   public LOTRWallTorchBlock(Supplier lootBlock) {
      this(SoundType.field_185848_a, lootBlock);
   }

   public LOTRWallTorchBlock(SoundType sound, Supplier lootBlock) {
      this(Properties.func_200950_a((AbstractBlock)lootBlock.get()).func_222379_b((Block)lootBlock.get()));
      this.torchParticles = ((LOTRTorchBlock)lootBlock.get()).copyTorchParticles();
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      Direction facing = (Direction)state.func_177229_b(field_196532_a);
      double d0 = (double)pos.func_177958_n() + 0.5D;
      double d1 = (double)pos.func_177956_o() + 0.7D;
      double d2 = (double)pos.func_177952_p() + 0.5D;
      double up = 0.22D;
      double across = 0.27D;
      Direction opposite = facing.func_176734_d();
      Iterator var17 = this.torchParticles.iterator();

      while(var17.hasNext()) {
         Supplier particle = (Supplier)var17.next();
         world.func_195594_a((IParticleData)particle.get(), d0 + across * (double)opposite.func_82601_c(), d1 + up, d2 + across * (double)opposite.func_82599_e(), 0.0D, 0.0D, 0.0D);
      }

   }
}
