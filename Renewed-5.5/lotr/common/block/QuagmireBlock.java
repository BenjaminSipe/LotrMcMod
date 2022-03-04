package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRDamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class QuagmireBlock extends Block {
   public QuagmireBlock() {
      super(Properties.func_200949_a(Material.field_151578_c, MaterialColor.field_151654_J).func_200948_a(1.0F, 0.5F).func_200942_a().func_200947_a(SoundType.field_226947_m_).harvestTool(ToolType.SHOVEL).func_235828_a_(LOTRBlocks::posPredicateTrue).func_235847_c_(LOTRBlocks::posPredicateTrue).func_235842_b_(LOTRBlocks::posPredicateTrue));
   }

   public void func_196262_a(BlockState state, World world, BlockPos pos, Entity entity) {
      entity.func_213295_a(state, new Vector3d(0.25D, 0.075D, 0.25D));
      if (entity instanceof LivingEntity && entity.func_70089_S() && entity.func_226280_cw_() < (double)(pos.func_177956_o() + 1)) {
         entity.func_70097_a(LOTRDamageSources.QUAGMIRE, 1.0F);
      }

   }

   public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
      return true;
   }
}
