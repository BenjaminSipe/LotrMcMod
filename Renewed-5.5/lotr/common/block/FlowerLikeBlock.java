package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class FlowerLikeBlock extends BushBlock {
   protected static final VoxelShape SHAPE = Block.func_208617_a(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
   private VoxelShape thisShape;

   public FlowerLikeBlock(Properties properties) {
      super(properties);
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 100);
      this.thisShape = SHAPE;
      CompostingHelper.prepareCompostable(this, 0.65F);
   }

   public FlowerLikeBlock() {
      this(Properties.func_200945_a(Material.field_151585_k).func_200942_a().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c));
   }

   public FlowerLikeBlock setPlantShape(VoxelShape shape) {
      this.thisShape = shape;
      return this;
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      Vector3d offset = state.func_191059_e(world, pos);
      return this.thisShape.func_197751_a(offset.field_72450_a, offset.field_72448_b, offset.field_72449_c);
   }

   public OffsetType func_176218_Q() {
      return OffsetType.XZ;
   }
}
