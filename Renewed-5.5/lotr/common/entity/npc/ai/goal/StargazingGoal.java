package lotr.common.entity.npc.ai.goal;

import lotr.common.util.LOTRUtil;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class StargazingGoal extends SkyWatchingGoal {
   private Vector3d currentStargazeTarget;
   private int gazeHereTick;

   public StargazingGoal(MobEntity entity, float chance) {
      super(entity, chance);
   }

   protected boolean shouldStartWatching() {
      if (this.isNightTime()) {
         Vector3d target = this.getRandomStargazeTarget();
         if (this.canSeeStargazeTarget(target)) {
            this.currentStargazeTarget = target;
            this.gazeHereTick = this.getCurrentGazeDuration();
            return true;
         }
      }

      return false;
   }

   private boolean isNightTime() {
      float sunCycle = this.world.func_242415_f(1.0F);
      return sunCycle > 0.26F && sunCycle < 0.74F;
   }

   protected Vector3d getCurrentWatchTarget() {
      return this.currentStargazeTarget;
   }

   private Vector3d getRandomStargazeTarget() {
      this.world.func_217381_Z().func_76320_a("getRandomStargazeTarget");
      Vector3d eyePos = this.entity.func_174824_e(1.0F);
      Vector3d skyVector = new Vector3d(0.0D, 300.0D, 0.0D);
      float randPitch = (float)Math.toRadians((double)MathHelper.func_151240_a(this.rand, -40.0F, 40.0F));
      float randYaw = this.rand.nextFloat() * 3.1415927F * 2.0F;
      skyVector = skyVector.func_178789_a(randPitch).func_178785_b(randYaw);
      Vector3d skyPos = eyePos.func_178787_e(skyVector);
      this.world.func_217381_Z().func_76319_b();
      return skyPos;
   }

   private boolean canSeeStargazeTarget(Vector3d target) {
      this.world.func_217381_Z().func_76320_a("canSeeStargazeTarget");
      boolean canSee = this.canSeeSkyWatchTarget(target);
      this.world.func_217381_Z().func_76319_b();
      return canSee;
   }

   protected boolean recheckShouldContinueWatching() {
      return this.isNightTime() && this.canSeeStargazeTarget(this.currentStargazeTarget);
   }

   protected int getWatchingDuration() {
      return LOTRUtil.secondsToTicks(10 + this.entity.func_70681_au().nextInt(50));
   }

   private int getCurrentGazeDuration() {
      return LOTRUtil.secondsToTicks(4 + this.entity.func_70681_au().nextInt(12));
   }

   public void func_75246_d() {
      super.func_75246_d();
      --this.gazeHereTick;
      if (this.gazeHereTick <= 0) {
         this.currentStargazeTarget = this.getRandomStargazeTarget();
         this.gazeHereTick = this.getCurrentGazeDuration();
      }

   }

   public void func_75251_c() {
      super.func_75251_c();
      this.currentStargazeTarget = null;
      this.gazeHereTick = 0;
   }
}
