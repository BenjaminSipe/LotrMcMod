package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class BronzeLanternBlock extends NonWaterloggableLanternBlock {
   private static final VoxelShape bronzeGroundShape = Block.func_208617_a(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
   private static final VoxelShape bronzeHangingShape = Block.func_208617_a(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
   private static final VoxelShape bronzeGroundCollisionShape = Block.func_208617_a(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D);
   private static final VoxelShape bronzeHangingCollisionShape = Block.func_208617_a(5.0D, 1.0D, 5.0D, 11.0D, 3.0D, 11.0D);

   public BronzeLanternBlock() {
      super(Properties.func_200945_a(Material.field_151594_q).func_235861_h_().harvestTool(ToolType.PICKAXE).func_200943_b(3.5F).func_200947_a(SoundType.field_222475_v).func_235838_a_(LOTRBlocks.constantLight(14)).func_226896_b_());
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return (Boolean)state.func_177229_b(HANGING) ? bronzeHangingShape : bronzeGroundShape;
   }

   public VoxelShape func_220071_b(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return (Boolean)state.func_177229_b(HANGING) ? bronzeHangingCollisionShape : bronzeGroundCollisionShape;
   }
}
