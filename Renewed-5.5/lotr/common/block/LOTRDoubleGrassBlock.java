package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LOTRDoubleGrassBlock extends DoublePlantBlock {
   public LOTRDoubleGrassBlock(Properties properties) {
      super(properties);
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 100);
      CompostingHelper.prepareCompostable(this, 0.5F);
   }

   public LOTRDoubleGrassBlock() {
      this(Properties.func_200945_a(Material.field_151582_l).func_200942_a().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c));
   }

   public static void accessRemoveBottomHalf(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      DoublePlantBlock.func_241471_b_(world, pos, state, player);
   }
}
