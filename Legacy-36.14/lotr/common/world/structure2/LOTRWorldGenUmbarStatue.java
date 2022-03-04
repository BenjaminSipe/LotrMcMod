package lotr.common.world.structure2;

import java.util.Random;

public class LOTRWorldGenUmbarStatue extends LOTRWorldGenSouthronStatue {
   public LOTRWorldGenUmbarStatue(boolean flag) {
      super(flag);
   }

   protected boolean isUmbar() {
      return true;
   }

   protected String getRandomStatueStrscan(Random random) {
      String[] statues = new String[]{"pillar", "snake", "pharazon"};
      String str = "umbar_statue_" + statues[random.nextInt(statues.length)];
      return str;
   }
}
