package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.init.LOTRTileEntities;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class PalantirBlock extends Block {
   private static final VoxelShape SHAPE = Block.func_208617_a(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

   public PalantirBlock() {
      super(Properties.func_200945_a(LOTRBlockMaterial.PALANTIR).func_235861_h_().func_200948_a(5.0F, 6.0F).func_226896_b_().func_235838_a_(LOTRBlocks.constantLight(3)).func_200947_a(SoundType.field_185853_f).harvestTool(ToolType.PICKAXE).harvestLevel(2));
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      BlockPos belowPos = pos.func_177977_b();
      return world.func_180495_p(belowPos).func_224755_d(world, belowPos, Direction.UP);
   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult target) {
      if (!world.field_72995_K) {
         LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.palantir.ponder"));
         world.func_184148_a((PlayerEntity)null, (double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D, LOTRSoundEvents.PALANTIR_PONDER, SoundCategory.BLOCKS, 1.0F, 1.0F);
      }

      return ActionResultType.SUCCESS;
   }

   public boolean hasTileEntity(BlockState state) {
      return true;
   }

   public TileEntity createTileEntity(BlockState state, IBlockReader world) {
      return ((TileEntityType)LOTRTileEntities.PALANTIR.get()).func_200968_a();
   }
}
