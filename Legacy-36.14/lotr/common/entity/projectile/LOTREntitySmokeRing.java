package lotr.common.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTREntitySmokeRing extends EntityThrowable {
   public static int MAX_AGE = 300;
   public int renderSmokeAge = -1;
   public int prevRenderSmokeAge = -1;

   public LOTREntitySmokeRing(World world) {
      super(world);
   }

   public LOTREntitySmokeRing(World world, EntityLivingBase entityliving) {
      super(world, entityliving);
   }

   public LOTREntitySmokeRing(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, 0);
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public int getSmokeAge() {
      return this.field_70180_af.func_75679_c(16);
   }

   public void setSmokeAge(int age) {
      this.field_70180_af.func_75692_b(16, age);
   }

   public int getSmokeColour() {
      return this.field_70180_af.func_75683_a(17);
   }

   public void setSmokeColour(int colour) {
      this.field_70180_af.func_75692_b(17, (byte)colour);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("SmokeAge", this.getSmokeAge());
      nbt.func_74768_a("SmokeColour", this.getSmokeColour());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setSmokeAge(nbt.func_74762_e("SmokeAge"));
      this.setSmokeColour(nbt.func_74762_e("SmokeColour"));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.func_70090_H() && !this.field_70170_p.field_72995_K) {
         this.func_70106_y();
      }

      if (this.renderSmokeAge == -1) {
         this.prevRenderSmokeAge = this.renderSmokeAge = this.getSmokeAge();
      } else {
         this.prevRenderSmokeAge = this.renderSmokeAge;
      }

      if (!this.field_70170_p.field_72995_K) {
         this.setSmokeAge(this.getSmokeAge() + 1);
         if (this.getSmokeAge() >= MAX_AGE) {
            this.func_70106_y();
         }
      }

      this.renderSmokeAge = this.getSmokeAge();
   }

   public float getRenderSmokeAge(float f) {
      float smokeAge = (float)this.prevRenderSmokeAge + (float)(this.renderSmokeAge - this.prevRenderSmokeAge) * f;
      return smokeAge / (float)MAX_AGE;
   }

   protected void func_70184_a(MovingObjectPosition m) {
      if (m.field_72313_a != MovingObjectType.ENTITY || m.field_72308_g != this.func_85052_h()) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_70106_y();
         }

      }
   }

   protected float func_70182_d() {
      return 0.1F;
   }

   protected float func_70185_h() {
      return 0.0F;
   }
}
