package lotr.common.block;

import java.util.Random;
import java.util.function.ToIntFunction;
import lotr.common.tileentity.AbstractAlloyForgeTileEntity;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractAlloyForgeBlock extends AbstractFurnaceBlock {
   public AbstractAlloyForgeBlock(Properties properties) {
      super(properties);
   }

   public AbstractAlloyForgeBlock(MaterialColor color) {
      this(Properties.func_200949_a(Material.field_151576_e, color).func_235861_h_().func_200943_b(3.5F).func_235838_a_(lightIfLit(13)));
   }

   protected static ToIntFunction lightIfLit(int lightValue) {
      return (state) -> {
         return (Boolean)state.func_177229_b(BlockStateProperties.field_208190_q) ? lightValue : 0;
      };
   }

   protected abstract ResourceLocation getForgeInteractionStat();

   protected void func_220089_a(World world, BlockPos pos, PlayerEntity player) {
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof INamedContainerProvider) {
         NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)te, (extraData) -> {
            extraData.func_179255_a(pos);
         });
         player.func_195066_a(this.getForgeInteractionStat());
      }

   }

   public void func_180633_a(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      if (stack.func_82837_s()) {
         TileEntity te = world.func_175625_s(pos);
         if (te instanceof AbstractAlloyForgeTileEntity) {
            ((AbstractAlloyForgeTileEntity)te).func_213903_a(stack.func_200301_q());
         }
      }

   }

   public void func_196243_a(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.func_177230_c() != newState.func_177230_c()) {
         TileEntity te = world.func_175625_s(pos);
         if (te instanceof AbstractAlloyForgeTileEntity) {
            InventoryHelper.func_180175_a(world, pos, (AbstractAlloyForgeTileEntity)te);
            world.func_175666_e(pos, this);
         }

         super.func_196243_a(state, world, pos, newState, isMoving);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if ((Boolean)state.func_177229_b(field_220091_b)) {
         double x = (double)pos.func_177958_n() + 0.5D;
         double y = (double)pos.func_177956_o();
         double z = (double)pos.func_177952_p() + 0.5D;
         if (rand.nextDouble() < 0.1D) {
            world.func_184134_a(x, y, z, SoundEvents.field_187652_bv, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
         }

         Direction dir = (Direction)state.func_177229_b(field_220090_a);
         Axis axis = dir.func_176740_k();
         double out = 0.52D;
         double front = rand.nextDouble() * 0.6D - 0.3D;
         double partX = axis == Axis.X ? (double)dir.func_82601_c() * out : front;
         double partY = rand.nextDouble() * 6.0D / 16.0D;
         double partZ = axis == Axis.Z ? (double)dir.func_82599_e() * out : front;
         world.func_195594_a(ParticleTypes.field_197601_L, x + partX, y + partY, z + partZ, 0.0D, 0.0D, 0.0D);
         world.func_195594_a(ParticleTypes.field_197631_x, x + partX, y + partY, z + partZ, 0.0D, 0.0D, 0.0D);
         if (!LOTRUtil.hasSolidSide(world, pos.func_177984_a(), Direction.DOWN)) {
            for(int l = 0; l < 2; ++l) {
               double smokeX = x + rand.nextDouble() * 0.1D - 0.05D;
               double smokeY = y + 1.0D;
               double smokeZ = z + rand.nextDouble() * 0.1D - 0.05D;
               world.func_195594_a(ParticleTypes.field_197601_L, smokeX, smokeY, smokeZ, 0.0D, 0.0D, 0.0D);
            }
         }
      }

   }
}
