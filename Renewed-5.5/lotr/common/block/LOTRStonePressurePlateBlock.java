package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.PressurePlateBlock.Sensitivity;

public class LOTRStonePressurePlateBlock extends PressurePlateBlock {
   public LOTRStonePressurePlateBlock(Supplier stone) {
      super(Sensitivity.MOBS, Properties.func_200950_a((AbstractBlock)stone.get()).func_200942_a().func_200943_b(0.5F));
   }
}
