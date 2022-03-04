package lotr.common.entity.npc.ai.goal;

import lotr.common.util.LOTRUtil;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class WatchSunriseSunsetGoal extends SkyWatchingGoal {
   private WatchSunriseSunsetGoal.Watching watching;
   private long lastWatchTime;
   private static final long MIN_WATCH_INTERVAL = (long)LOTRUtil.secondsToTicks(20);

   public WatchSunriseSunsetGoal(MobEntity entity, float chance) {
      super(entity, chance);
   }

   protected boolean shouldStartWatching() {
      if (this.getCurrentGameTime() - this.lastWatchTime < MIN_WATCH_INTERVAL) {
         return false;
      } else if (this.canSeeSunset()) {
         this.watching = WatchSunriseSunsetGoal.Watching.SUNSET;
         return true;
      } else if (this.canSeeSunrise()) {
         this.watching = WatchSunriseSunsetGoal.Watching.SUNRISE;
         return true;
      } else {
         return false;
      }
   }

   private long getCurrentGameTime() {
      return this.world.func_82737_E();
   }

   private boolean canSeeSunrise() {
      return this.isSunrise() && this.canSeeSunProjectedPos();
   }

   private boolean canSeeSunset() {
      return this.isSunset() && this.canSeeSunProjectedPos();
   }

   private boolean isSunrise() {
      float sunCycle = this.world.func_242415_f(1.0F);
      return sunCycle > 0.729F && sunCycle < 0.76F;
   }

   private boolean isSunset() {
      float sunCycle = this.world.func_242415_f(1.0F);
      return sunCycle > 0.24F && sunCycle < 0.271F;
   }

   protected Vector3d getCurrentWatchTarget() {
      return this.getSunProjectedPos();
   }

   private Vector3d getSunProjectedPos() {
      this.world.func_217381_Z().func_76320_a("getSunProjectedPos");
      Vector3d eyePos = this.entity.func_174824_e(1.0F);
      Vector3d sunNoonVector = new Vector3d(0.0D, 100.0D, 0.0D);
      float sunAngleFromNoon = this.world.func_72929_e(1.0F);
      sunAngleFromNoon *= -1.0F;
      float cos = MathHelper.func_76134_b(sunAngleFromNoon);
      float sin = MathHelper.func_76126_a(sunAngleFromNoon);
      double x = sunNoonVector.field_72450_a * (double)cos + sunNoonVector.field_72448_b * (double)sin;
      double y = sunNoonVector.field_72448_b * (double)cos - sunNoonVector.field_72450_a * (double)sin;
      double z = sunNoonVector.field_72449_c;
      Vector3d sunVector = new Vector3d(x, y, z);
      Vector3d sunPos = eyePos.func_178787_e(sunVector);
      this.world.func_217381_Z().func_76319_b();
      return sunPos;
   }

   private boolean canSeeSunProjectedPos() {
      this.world.func_217381_Z().func_76320_a("canSeeSunProjectedPos");
      boolean canSee = this.canSeeSkyWatchTarget(this.getSunProjectedPos());
      this.world.func_217381_Z().func_76319_b();
      return canSee;
   }

   protected boolean recheckShouldContinueWatching() {
      if (this.watching == WatchSunriseSunsetGoal.Watching.SUNRISE) {
         return this.canSeeSunrise();
      } else {
         return this.watching == WatchSunriseSunsetGoal.Watching.SUNSET ? this.canSeeSunset() : false;
      }
   }

   protected int getWatchingDuration() {
      return LOTRUtil.secondsToTicks(5 + this.rand.nextInt(15));
   }

   public void func_75251_c() {
      super.func_75251_c();
      this.watching = null;
   }

   public void func_75246_d() {
      super.func_75246_d();
      this.lastWatchTime = this.getCurrentGameTime();
   }

   private static enum Watching {
      SUNRISE,
      SUNSET;
   }
}
