package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public abstract class CloverBlock extends BushBlock {
   private static final VoxelShape SHAPE = Block.func_208617_a(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D);

   public CloverBlock(Properties properties) {
      super(properties);
      CompostingHelper.prepareCompostable(this, 0.3F);
   }

   public CloverBlock() {
      this(Properties.func_200945_a(Material.field_151582_l).func_200942_a().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c));
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      Vector3d offset = state.func_191059_e(world, pos);
      return SHAPE.func_197751_a(offset.field_72450_a, offset.field_72448_b, offset.field_72449_c);
   }

   public OffsetType func_176218_Q() {
      return OffsetType.XYZ;
   }
}
