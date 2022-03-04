package lotr.common.world;

import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;

public class LOTRWorldInfo extends DerivedWorldInfo {
   private long lotrTotalTime;
   private long lotrWorldTime;

   public LOTRWorldInfo(WorldInfo worldinfo) {
      super(worldinfo);
   }

   public long func_82573_f() {
      return this.lotrTotalTime;
   }

   public long func_76073_f() {
      return this.lotrWorldTime;
   }

   public void func_82572_b(long time) {
   }

   public void func_76068_b(long time) {
   }

   public void lotr_setTotalTime(long time) {
      this.lotrTotalTime = time;
   }

   public void lotr_setWorldTime(long time) {
      this.lotrWorldTime = time;
   }
}
