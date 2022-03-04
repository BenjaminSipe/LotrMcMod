package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class LOTRGravelBlock extends FallingBlock {
   private final int dustColor;

   public LOTRGravelBlock(Properties properties, int dust) {
      super(properties);
      this.dustColor = dust;
   }

   public LOTRGravelBlock(MaterialColor materialColor, int dust) {
      this(Properties.func_200949_a(Material.field_151595_p, materialColor).func_200943_b(0.6F).func_200947_a(SoundType.field_185849_b).harvestTool(ToolType.SHOVEL), dust);
   }

   @OnlyIn(Dist.CLIENT)
   public int func_189876_x(BlockState state, IBlockReader reader, BlockPos pos) {
      return this.dustColor;
   }
}
