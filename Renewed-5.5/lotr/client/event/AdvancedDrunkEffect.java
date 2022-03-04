package lotr.client.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;

public class AdvancedDrunkEffect {
   private final int secondsMaxDrunk = 120;
   private int yawDirection = 1;
   private int rollDirection = 1;
   private float yawAdd;
   private float prevYawAdd;
   private float pitchAdd;
   private float prevPitchAdd;
   private float rollAdd;
   private float prevRollAdd;
   private float drunkFactor;
   private float prevDrunkFactor;

   public void update(LivingEntity viewer) {
      this.prevYawAdd = this.yawAdd;
      this.prevPitchAdd = this.pitchAdd;
      this.prevRollAdd = this.rollAdd;
      this.prevDrunkFactor = this.drunkFactor;
      if (viewer.func_70644_a(Effects.field_76431_k)) {
         float drunk = (float)viewer.func_70660_b(Effects.field_76431_k).func_76459_b();
         drunk /= 20.0F;
         drunk /= 120.0F;
         this.drunkFactor = Math.min(drunk, 1.0F);
         this.yawAdd += (float)this.rollDirection * 1.2F;
         this.yawAdd = MathHelper.func_76131_a(this.yawAdd, -30.0F, 30.0F);
         this.rollAdd += (float)this.rollDirection * 0.6F;
         this.rollAdd = MathHelper.func_76131_a(this.rollAdd, -30.0F, 30.0F);
         if (viewer.func_70681_au().nextInt(200) == 0) {
            this.yawDirection *= -1;
         }

         if (viewer.func_70681_au().nextInt(100) == 0) {
            this.rollDirection *= -1;
         }
      } else {
         this.yawAdd = 0.0F;
         this.pitchAdd = 0.0F;
         this.rollAdd = 0.0F;
         this.drunkFactor = 0.0F;
      }

   }

   public void handle(CameraSetup event) {
      float yaw = event.getYaw();
      float pitch = event.getPitch();
      float roll = event.getRoll();
      float tick = (float)event.getRenderPartialTicks();
      float factor = this.prevDrunkFactor + (this.drunkFactor - this.prevDrunkFactor) * tick;
      yaw += (this.prevYawAdd + (this.yawAdd - this.prevYawAdd) * tick) * factor;
      pitch += (this.prevPitchAdd + (this.pitchAdd - this.prevPitchAdd) * tick) * factor;
      roll += (this.prevRollAdd + (this.rollAdd - this.prevRollAdd) * tick) * factor;
      event.setYaw(yaw);
      event.setPitch(pitch);
      event.setRoll(roll);
   }

   public void reset() {
      this.yawDirection = this.rollDirection = 1;
      this.prevYawAdd = this.yawAdd = 0.0F;
      this.prevPitchAdd = this.pitchAdd = 0.0F;
      this.prevRollAdd = this.rollAdd = 0.0F;
      this.prevDrunkFactor = this.drunkFactor = 0.0F;
   }
}
