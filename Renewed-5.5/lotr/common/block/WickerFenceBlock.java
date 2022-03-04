package lotr.common.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.stream.Collectors;
import lotr.common.event.CompostingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class WickerFenceBlock extends WallBlock {
   private final Map wickerStateToShapeMap;
   private final Map wickerStateToCollisionShapeMap;

   public WickerFenceBlock(Properties properties) {
      super(properties);
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 20);
      CompostingHelper.prepareCompostable(this, 0.85F);
      this.wickerStateToShapeMap = this.func_235624_a_(1.0F, 1.0F, 16.0F, 0.0F, 13.0F, 16.0F);
      this.wickerStateToCollisionShapeMap = this.func_235624_a_(1.0F, 1.0F, 24.0F, 0.0F, 24.0F, 24.0F);
   }

   public WickerFenceBlock() {
      this(Properties.func_200945_a(Material.field_151594_q).func_200943_b(0.5F).func_200947_a(SoundType.field_222470_q).harvestTool(ToolType.AXE));
   }

   public Map func_235624_a_(float f, float f1, float f2, float f3, float f4, float f5) {
      Map shapes = super.func_235624_a_(f, f1, f2, f3, f4, f5);
      return ImmutableMap.builder().putAll((Map)shapes.keySet().stream().collect(Collectors.toMap((state) -> {
         return state;
      }, (state) -> {
         return (VoxelShape)shapes.get(state.func_206870_a(field_176256_a, true));
      }))).build();
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return (VoxelShape)this.wickerStateToShapeMap.get(state);
   }

   public VoxelShape func_220071_b(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return (VoxelShape)this.wickerStateToCollisionShapeMap.get(state);
   }

   public boolean func_220113_a(BlockState state, boolean sideSolid, Direction direction) {
      return super.func_220113_a(state, sideSolid, direction) || state.func_235714_a_(BlockTags.field_219748_G) && state.func_235714_a_(BlockTags.field_219756_j) == this.func_203417_a(BlockTags.field_219756_j);
   }
}
