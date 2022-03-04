package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;

public class LOTRWorldGenHarnedorPalisadeRuined extends LOTRWorldGenHarnedorPalisade {
   public LOTRWorldGenHarnedorPalisadeRuined(boolean flag) {
      super(flag);
   }

   protected boolean isRuined() {
      return true;
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      if (random.nextBoolean()) {
         this.woodBlock = LOTRMod.wood;
         this.woodMeta = 3;
      }

   }
}
