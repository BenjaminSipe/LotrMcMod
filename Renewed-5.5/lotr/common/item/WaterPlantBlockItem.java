package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.world.World;

public abstract class WaterPlantBlockItem extends LOTRBlockItem {
   public WaterPlantBlockItem(Block block, Properties properties) {
      super(block, properties);
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      return this.canAttemptPlaceNormally(context) ? super.func_195939_a(context) : ActionResultType.PASS;
   }

   protected boolean canAttemptPlaceNormally(ItemUseContext context) {
      return false;
   }

   protected boolean canPlaceOnIce() {
      return false;
   }

   protected void playPlaceSound(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      this.playNormalPlaceSound(world, pos, state, player);
   }

   protected final void playNormalPlaceSound(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      SoundType sound = state.getSoundType(world, pos, player);
      world.func_184133_a(player, pos, this.getPlaceSound(state, world, pos, player), SoundCategory.BLOCKS, (sound.func_185843_a() + 1.0F) / 2.0F, sound.func_185847_b() * 0.8F);
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      BlockRayTraceResult blockTarget = func_219968_a(world, player, FluidMode.SOURCE_ONLY);
      BlockRayTraceResult blockTargetAbove = blockTarget.func_237485_a_(blockTarget.func_216350_a().func_177984_a());
      ActionResultType result = super.func_195939_a(new ItemUseContext(player, hand, blockTargetAbove));
      return new ActionResult(result, player.func_184586_b(hand));
   }
}
