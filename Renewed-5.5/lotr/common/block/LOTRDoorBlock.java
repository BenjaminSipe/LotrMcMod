package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;

public class LOTRDoorBlock extends DoorBlock {
   public LOTRDoorBlock(Supplier planks) {
      super(Properties.func_200949_a(Material.field_151575_d, ((LOTRPlanksBlock)planks.get()).planksColor).func_200943_b(3.0F).func_200947_a(SoundType.field_185848_a).func_226896_b_());
   }
}
