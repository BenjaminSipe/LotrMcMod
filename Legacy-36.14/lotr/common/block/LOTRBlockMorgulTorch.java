package lotr.common.block;

import java.util.Random;

public class LOTRBlockMorgulTorch extends LOTRBlockTorch {
   public LOTRBlockTorch.TorchParticle createTorchParticle(Random random) {
      double d3 = -0.05D + (double)random.nextFloat() * 0.1D;
      double d4 = 0.1D + (double)random.nextFloat() * 0.1D;
      double d5 = -0.05D + (double)random.nextFloat() * 0.1D;
      return new LOTRBlockTorch.TorchParticle("morgulPortal", 0.0D, 0.0D, 0.0D, d3, d4, d5);
   }
}
