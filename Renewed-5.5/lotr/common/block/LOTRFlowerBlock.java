package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class LOTRFlowerBlock extends FlowerBlock {
   private static final VoxelShape WIDE_SHAPE = Block.func_208617_a(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);
   private static final VoxelShape WIDE_FLAT_SHAPE = Block.func_208617_a(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D);
   protected VoxelShape flowerShape;

   public LOTRFlowerBlock(Effect effect, int effectDuration, Properties properties) {
      super(effect, effectDuration, properties);
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 100);
      CompostingHelper.prepareCompostable(this, 0.65F);
      this.flowerShape = FlowerBlock.field_196398_a;
   }

   public LOTRFlowerBlock(Effect effect, int effectDuration) {
      this(effect, effectDuration, createDefaultFlowerProperties());
   }

   protected static Properties createDefaultFlowerProperties() {
      return Properties.func_200945_a(Material.field_151585_k).func_200942_a().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c);
   }

   public LOTRFlowerBlock setWide() {
      this.flowerShape = WIDE_SHAPE;
      return this;
   }

   public LOTRFlowerBlock setWideFlat() {
      this.flowerShape = WIDE_FLAT_SHAPE;
      return this;
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      Vector3d offset = state.func_191059_e(world, pos);
      return this.flowerShape.func_197751_a(offset.field_72450_a, offset.field_72448_b, offset.field_72449_c);
   }
}
