package lotr.common.block;

import java.util.Random;

public class LOTRBlockElvenTorch extends LOTRBlockTorch {
   public LOTRBlockTorch.TorchParticle createTorchParticle(Random random) {
      return random.nextInt(3) == 0 ? new LOTRBlockTorch.TorchParticle("elvenGlow", 0.0D, -0.1D, 0.0D, 0.0D, 0.0D, 0.0D) : null;
   }
}
