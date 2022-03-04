package lotr.common.entity.capabilities;

import lotr.common.LOTRLog;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class PlateFallingData {
   private static final int TICK_DELAY_FACTOR = 1;
   private static final int MAX_STACK_SIZE = 64;
   private static final int NUM_FALLERS = 65;
   private Entity theEntity;
   private int updateTick;
   private float[] posXTicksAgo = new float[65];
   private boolean[] isFalling = new boolean[65];
   private float[] fallerPos = new float[65];
   private float[] prevFallerPos = new float[65];
   private float[] fallerSpeed = new float[65];
   private boolean loggedNullEntityWarning = false;

   public boolean isEntitySet() {
      return this.theEntity != null;
   }

   public PlateFallingData setEntity(Entity e) {
      if (this.isEntitySet()) {
         throw new IllegalStateException("Entity is already set");
      } else {
         this.theEntity = e;
         return this;
      }
   }

   public void update() {
      float curPos = (float)this.theEntity.func_226278_cu_();
      int l;
      if (!this.theEntity.func_233570_aj_() && this.theEntity.func_213322_ci().func_82617_b() > 0.0D) {
         for(l = 0; l < this.posXTicksAgo.length; ++l) {
            this.posXTicksAgo[l] = Math.max(this.posXTicksAgo[l], curPos);
         }
      }

      if (this.updateTick % 1 == 0) {
         for(l = this.posXTicksAgo.length - 1; l > 0; --l) {
            this.posXTicksAgo[l] = this.posXTicksAgo[l - 1];
         }

         this.posXTicksAgo[0] = curPos;
      }

      ++this.updateTick;

      for(l = 0; l < this.fallerPos.length; ++l) {
         this.prevFallerPos[l] = this.fallerPos[l];
         float pos = this.fallerPos[l];
         float speed = this.fallerSpeed[l];
         boolean fall = this.isFalling[l];
         if (!fall && pos > this.posXTicksAgo[l]) {
            fall = true;
         }

         this.isFalling[l] = fall;
         if (fall) {
            speed = (float)((double)speed + 0.08D);
            pos -= speed;
            speed = (float)((double)speed * 0.98D);
         } else {
            speed = 0.0F;
         }

         if (pos < curPos) {
            pos = curPos;
            speed = 0.0F;
            this.isFalling[l] = false;
         }

         this.fallerPos[l] = pos;
         this.fallerSpeed[l] = speed;
      }

   }

   public float getPlateOffsetY(float partialTick) {
      return this.getOffsetY(0, partialTick);
   }

   public float getFoodOffsetY(int foodSlot, float partialTick) {
      return this.getOffsetY(foodSlot - 1, partialTick);
   }

   private float getOffsetY(int index, float partialTick) {
      if (this.theEntity == null) {
         if (!this.loggedNullEntityWarning) {
            LOTRLog.warn("A PlateFallingData was asked for y-offset, but its entity was not set! This should not happen - it may be a compatibility problem. Stack trace:");
            Thread.dumpStack();
            this.loggedNullEntityWarning = true;
         }

         return 0.0F;
      } else {
         index = MathHelper.func_76125_a(index, 0, this.fallerPos.length - 1);
         float pos = this.prevFallerPos[index] + (this.fallerPos[index] - this.prevFallerPos[index]) * partialTick;
         float offset = pos - (float)(this.theEntity.field_70167_r + (this.theEntity.func_226278_cu_() - this.theEntity.field_70167_r) * (double)partialTick);
         offset = Math.max(offset, 0.0F);
         return offset;
      }
   }
}
