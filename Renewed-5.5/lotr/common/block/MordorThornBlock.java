package lotr.common.block;

import lotr.common.event.CompostingHelper;
import lotr.common.fac.EntityFactionHelper;
import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRDamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MordorThornBlock extends MordorPlantBlock {
   private static final VoxelShape SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public MordorThornBlock() {
      super(Properties.func_200945_a(Material.field_151582_l).func_200942_a().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c));
      CompostingHelper.prepareCompostable(this, 0.3F);
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   public void func_196262_a(BlockState state, World world, BlockPos pos, Entity entity) {
      if (!FactionPointers.MORDOR.matches(EntityFactionHelper.getFaction(entity))) {
         entity.func_70097_a(LOTRDamageSources.PLANT, 2.0F);
      }

   }
}
