package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.init.Blocks;

public class LOTRWorldGenGundabadForgeTent extends LOTRWorldGenGundabadTent {
   public LOTRWorldGenGundabadForgeTent(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tentBlock = Blocks.field_150347_e;
      this.tentMeta = 0;
      this.fenceBlock = Blocks.field_150463_bK;
      this.fenceMeta = 0;
      this.hasOrcForge = true;
   }
}
