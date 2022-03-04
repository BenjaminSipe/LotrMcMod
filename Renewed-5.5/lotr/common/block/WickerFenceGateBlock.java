package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class WickerFenceGateBlock extends FenceGateBlock {
   public WickerFenceGateBlock() {
      super(Properties.func_200945_a(Material.field_151594_q).func_200943_b(0.5F).func_200947_a(SoundType.field_222470_q).harvestTool(ToolType.AXE));
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 20);
      CompostingHelper.prepareCompostable(this, 0.85F);
   }
}
