package lotr.common.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HearthBlock extends Block {
   public static final BooleanProperty LIT;

   public HearthBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(LIT, false));
   }

   public HearthBlock() {
      this(Properties.func_200949_a(Material.field_151576_e, MaterialColor.field_151645_D).func_235861_h_().func_200948_a(1.0F, 5.0F).func_200947_a(SoundType.field_185851_d));
   }

   protected Block getFireBlock() {
      return Blocks.field_150480_ab;
   }

   public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
      return side == Direction.UP;
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{LIT});
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if (facing != Direction.UP) {
         return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
      } else {
         Block block = facingState.func_177230_c();
         return (BlockState)state.func_206870_a(LIT, block == this.getFireBlock());
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if ((Boolean)state.func_177229_b(LIT)) {
         int smokeHeight = 3;

         for(int j = 1; j <= smokeHeight; ++j) {
            BlockPos upPos = pos.func_177981_b(j);
            if (world.func_180495_p(upPos).func_200132_m()) {
               break;
            }

            for(int l = 0; l < 2; ++l) {
               double d = (double)((float)upPos.func_177958_n() + rand.nextFloat());
               double d1 = (double)((float)upPos.func_177956_o() + rand.nextFloat());
               double d2 = (double)((float)upPos.func_177952_p() + rand.nextFloat());
               if (rand.nextInt(3) == 0) {
                  world.func_195594_a(ParticleTypes.field_218417_ae, d, d1, d2, 0.0D, 0.05D, 0.0D);
               } else {
                  world.func_195594_a(ParticleTypes.field_197594_E, d, d1, d2, 0.0D, 0.0D, 0.0D);
               }
            }
         }
      }

   }

   static {
      LIT = BlockStateProperties.field_208190_q;
   }
}
