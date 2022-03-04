package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Item.Properties;

public class FallenLeavesItem extends WaterPlantBlockItem {
   public FallenLeavesItem(Block blockIn, Properties properties) {
      super(blockIn, properties);
   }

   protected boolean canAttemptPlaceNormally(ItemUseContext context) {
      return context.func_195991_k().func_204610_c(context.func_195995_a().func_177972_a(context.func_196000_l())).func_206888_e();
   }
}
