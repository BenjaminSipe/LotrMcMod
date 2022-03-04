package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class SkyWatchingGoal extends Goal {
   protected final MobEntity entity;
   protected final World world;
   protected final Random rand;
   private final float chance;
   private int watchingTick;
   private int reCheckLookTick;
   private final int reCheckLookTime = 20;

   public SkyWatchingGoal(MobEntity entity, float chance) {
      this.entity = entity;
      this.world = entity.field_70170_p;
      this.rand = entity.func_70681_au();
      this.chance = chance;
      this.func_220684_a(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   public boolean func_75250_a() {
      return this.rand.nextFloat() < this.chance ? this.shouldStartWatching() : false;
   }

   protected abstract boolean shouldStartWatching();

   protected abstract Vector3d getCurrentWatchTarget();

   protected boolean canSeeSkyWatchTarget(Vector3d target) {
      if (!this.world.func_72896_J()) {
         Vector3d eyePos = this.entity.func_174824_e(1.0F);
         Type type = this.world.func_217299_a(new RayTraceContext(eyePos, target, BlockMode.VISUAL, FluidMode.NONE, this.entity)).func_216346_c();
         return type == Type.MISS;
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      if (this.watchingTick <= 0) {
         return false;
      } else if (this.reCheckLookTick <= 0) {
         this.reCheckLookTick = 20;
         return this.recheckShouldContinueWatching();
      } else {
         return true;
      }
   }

   protected abstract boolean recheckShouldContinueWatching();

   public void func_75249_e() {
      this.watchingTick = this.getWatchingDuration();
      this.reCheckLookTick = 20;
   }

   protected abstract int getWatchingDuration();

   public void func_75251_c() {
      this.watchingTick = 0;
      this.reCheckLookTick = 0;
   }

   public void func_75246_d() {
      this.entity.func_70671_ap().func_220674_a(this.getCurrentWatchTarget());
      --this.watchingTick;
      --this.reCheckLookTick;
   }
}
