package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.AbstractBlock.Properties;

public class LOTRWallSignBlock extends WallSignBlock {
   public LOTRWallSignBlock(Supplier sign) {
      super(Properties.func_200950_a((AbstractBlock)sign.get()).func_222379_b((Block)sign.get()), ((LOTRStandingSignBlock)sign.get()).signType);
      SignSetupHelper.add(this);
   }
}
