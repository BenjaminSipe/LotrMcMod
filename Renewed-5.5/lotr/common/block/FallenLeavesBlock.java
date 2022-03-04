package lotr.common.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class FallenLeavesBlock extends Block {
   public static final List ALL_FALLEN_LEAVES = new ArrayList();
   private static final Map LEAVES_TO_FALLEN_LEAVES = new HashMap();
   private static final VoxelShape SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
   private final Block baseLeafBlock;

   public FallenLeavesBlock(Block leaf, Properties properties) {
      super(properties);
      this.baseLeafBlock = leaf;
      ALL_FALLEN_LEAVES.add(this);
      LEAVES_TO_FALLEN_LEAVES.put(this.baseLeafBlock, this);
   }

   public FallenLeavesBlock(Block leaf) {
      this(leaf, Properties.func_200945_a(Material.field_151582_l).func_200943_b(0.2F).func_200947_a(SoundType.field_185850_c).func_200942_a().func_226896_b_().harvestTool(ToolType.HOE));
   }

   public FallenLeavesBlock(Supplier leaf) {
      this((Block)leaf.get());
   }

   public Block getBaseLeafBlock() {
      return this.baseLeafBlock;
   }

   public static FallenLeavesBlock getFallenLeavesFor(Block leafBlock) {
      return (FallenLeavesBlock)LEAVES_TO_FALLEN_LEAVES.get(leafBlock);
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      BlockPos belowPos = pos.func_177977_b();
      return world.func_180495_p(belowPos).func_224755_d(world, belowPos, Direction.UP) || world.func_204610_c(belowPos).func_206886_c() == Fluids.field_204546_a;
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return facing == Direction.DOWN && !state.func_196955_c(world, currentPos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }
}
