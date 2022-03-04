package lotr.common.init;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class LOTRWaterLilyBlock extends LilyPadBlock {
   public LOTRWaterLilyBlock(Properties properties) {
      super(properties);
      CompostingHelper.prepareCompostable(this, 0.65F);
   }

   public LOTRWaterLilyBlock() {
      this(Properties.func_200945_a(Material.field_151585_k).func_200943_b(0.0F).func_200947_a(SoundType.field_235600_d_).func_226896_b_());
   }

   public PlantType getPlantType(IBlockReader world, BlockPos pos) {
      return ((IPlantable)Blocks.field_196651_dG).getPlantType(world, pos);
   }
}
