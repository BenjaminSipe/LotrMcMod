package lotr.common.block;

import java.util.Random;
import net.minecraft.block.Block;

public class LOTRBlockMallornTorch extends LOTRBlockTorch {
   private int torchColor;

   public LOTRBlockMallornTorch(int color) {
      this.func_149711_c(0.0F);
      this.func_149672_a(Block.field_149766_f);
      this.func_149715_a(0.875F);
      this.torchColor = color;
   }

   public LOTRBlockTorch.TorchParticle createTorchParticle(Random random) {
      return random.nextInt(3) == 0 ? new LOTRBlockTorch.TorchParticle("elvenGlow_" + Integer.toHexString(this.torchColor), 0.0D, -0.1D, 0.0D, 0.0D, 0.0D, 0.0D) : null;
   }
}
