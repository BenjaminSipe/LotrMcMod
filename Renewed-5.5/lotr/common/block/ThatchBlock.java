package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ThatchBlock extends Block {
   public ThatchBlock(Properties props) {
      super(props);
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 20);
      CompostingHelper.prepareCompostable(this, 0.85F);
   }

   public ThatchBlock() {
      this(MaterialColor.field_151658_d);
   }

   public ThatchBlock(MaterialColor color) {
      this(Properties.func_200949_a(Material.field_151577_b, color).func_200943_b(0.5F).func_200947_a(SoundType.field_185850_c).harvestTool(ToolType.HOE));
   }

   public void func_180658_a(World world, BlockPos pos, Entity entity, float fallDistance) {
      doStandardHayFall(world, pos, entity, fallDistance);
   }

   public static void doStandardHayFall(World world, BlockPos pos, Entity entity, float fallDistance) {
      entity.func_225503_b_(fallDistance, 0.2F);
   }
}
