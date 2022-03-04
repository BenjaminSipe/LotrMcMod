package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class SnowPathBlock extends LOTRPathBlock {
   public SnowPathBlock() {
      super(Properties.func_200945_a(Material.field_151596_z).func_200943_b(0.2F).func_200947_a(SoundType.field_185856_i).func_235861_h_().harvestTool(ToolType.SHOVEL));
   }

   protected BlockState getUnpathedBlockState() {
      return Blocks.field_196604_cC.func_176223_P();
   }

   public static ActionResultType makeSnowPathUnderSnowLayer(World world, BlockPos pos, Direction side, PlayerEntity player, Hand hand, ItemStack heldItem) {
      BlockState stateBefore = world.func_180495_p(pos);
      BlockPos belowPos = pos.func_177977_b();
      if (stateBefore.func_177230_c() == Blocks.field_150433_aE && (Integer)stateBefore.func_177229_b(SnowBlock.field_176315_a) == 1 && world.func_180495_p(belowPos).func_177230_c() == Blocks.field_196604_cC) {
         world.func_175656_a(pos, Blocks.field_150350_a.func_176223_P());
         BlockRayTraceResult fakedBelowHitVec = new BlockRayTraceResult(Vector3d.func_237489_a_(belowPos), side, belowPos, false);
         ItemUseContext context = new ItemUseContext(world, player, hand, heldItem, fakedBelowHitVec);
         ActionResultType shovelResult = heldItem.func_196084_a(context);
         if (!shovelResult.func_226246_a_()) {
            world.func_175656_a(pos, stateBefore);
         }

         return shovelResult;
      } else {
         return ActionResultType.PASS;
      }
   }
}
