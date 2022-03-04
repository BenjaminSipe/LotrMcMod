package lotr.common.entity.projectile;

import lotr.common.init.LOTREntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SmokeRingEntity extends ThrowableEntity {
   private static final DataParameter SMOKE_COLOR;
   private static final DataParameter SMOKE_MAGIC;
   private static final DataParameter SMOKE_AGE;
   private static final DataParameter SMOKE_MAX_AGE;
   private static final DataParameter SMOKE_SCALE;
   private static final int DEFAULT_MAX_AGE = 300;
   private int prevRenderSmokeAge = -1;
   private int renderSmokeAge = -1;

   public SmokeRingEntity(EntityType type, World w) {
      super(type, w);
   }

   public SmokeRingEntity(World w, LivingEntity e) {
      super((EntityType)LOTREntities.SMOKE_RING.get(), e, w);
   }

   public SmokeRingEntity(World w, double x, double y, double z) {
      super((EntityType)LOTREntities.SMOKE_RING.get(), x, y, z, w);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(SMOKE_COLOR, (byte)DyeColor.WHITE.func_196059_a());
      this.field_70180_af.func_187214_a(SMOKE_MAGIC, false);
      this.field_70180_af.func_187214_a(SMOKE_AGE, 0);
      this.field_70180_af.func_187214_a(SMOKE_MAX_AGE, 300);
      this.field_70180_af.func_187214_a(SMOKE_SCALE, 1.0F);
   }

   public DyeColor getSmokeColor() {
      return DyeColor.func_196056_a((Byte)this.field_70180_af.func_187225_a(SMOKE_COLOR));
   }

   public void setSmokeColor(DyeColor color) {
      this.field_70180_af.func_187227_b(SMOKE_COLOR, (byte)color.func_196059_a());
   }

   public boolean isMagicSmoke() {
      return (Boolean)this.field_70180_af.func_187225_a(SMOKE_MAGIC);
   }

   public void setMagicSmoke(boolean flag) {
      this.field_70180_af.func_187227_b(SMOKE_MAGIC, flag);
   }

   private int getSmokeAge() {
      return (Integer)this.field_70180_af.func_187225_a(SMOKE_AGE);
   }

   private void setSmokeAge(int age) {
      this.field_70180_af.func_187227_b(SMOKE_AGE, age);
   }

   private int getSmokeMaxAge() {
      return (Integer)this.field_70180_af.func_187225_a(SMOKE_MAX_AGE);
   }

   private void setSmokeMaxAge(int maxAge) {
      this.field_70180_af.func_187227_b(SMOKE_MAX_AGE, maxAge);
   }

   public float getSmokeScale() {
      return (Float)this.field_70180_af.func_187225_a(SMOKE_SCALE);
   }

   public void setSmokeScale(float scale) {
      this.field_70180_af.func_187227_b(SMOKE_SCALE, scale);
   }

   public IPacket func_213297_N() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void func_213281_b(CompoundNBT nbt) {
      super.func_213281_b(nbt);
      nbt.func_74774_a("SmokeColor", (byte)this.getSmokeColor().func_196059_a());
      nbt.func_74757_a("SmokeMagic", this.isMagicSmoke());
      nbt.func_74768_a("SmokeAge", this.getSmokeAge());
      nbt.func_74768_a("SmokeMaxAge", this.getSmokeMaxAge());
      nbt.func_74776_a("SmokeScale", this.getSmokeScale());
   }

   public void func_70037_a(CompoundNBT nbt) {
      super.func_70037_a(nbt);
      this.setSmokeColor(DyeColor.func_196056_a(nbt.func_74771_c("SmokeColor")));
      this.setMagicSmoke(nbt.func_74767_n("SmokeMagic"));
      this.setSmokeAge(nbt.func_74762_e("SmokeAge"));
      if (nbt.func_74764_b("SmokeMaxAge")) {
         this.setSmokeMaxAge(nbt.func_74762_e("SmokeMaxAge"));
      }

      if (nbt.func_74764_b("SmokeScale")) {
         this.setSmokeScale(nbt.func_74760_g("SmokeScale"));
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         int smokeAge = this.getSmokeAge();
         int maxAge = this.getSmokeMaxAge();
         if (smokeAge >= maxAge) {
            this.func_70106_y();
         } else {
            if (this.isMagicSmoke()) {
               int spawnInterval = 20;
               int div = smokeAge / spawnInterval;
               if (smokeAge % spawnInterval == 0 && div > 0 && div <= 5) {
                  SmokeRingEntity trailingRing = new SmokeRingEntity(this.field_70170_p, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_());
                  trailingRing.func_212361_a(this.func_234616_v_());
                  double slow = 0.5D;
                  trailingRing.func_213317_d(this.func_213322_ci().func_216372_d(slow, slow, slow));
                  trailingRing.setSmokeColor(this.getSmokeColor());
                  trailingRing.setSmokeScale(this.getSmokeScale() * 0.35F);
                  trailingRing.setSmokeMaxAge(maxAge / 2);
                  this.field_70170_p.func_217376_c(trailingRing);
               }
            }

            this.setSmokeAge(smokeAge + 1);
         }

         if (this.func_70090_H()) {
            this.func_70106_y();
         }
      } else {
         if (this.renderSmokeAge == -1) {
            this.prevRenderSmokeAge = this.renderSmokeAge = this.getSmokeAge();
         } else {
            this.prevRenderSmokeAge = this.renderSmokeAge;
         }

         this.renderSmokeAge = this.getSmokeAge();
      }

   }

   public float getRenderSmokeAge(float f) {
      float smokeAge = (float)this.prevRenderSmokeAge + (float)(this.renderSmokeAge - this.prevRenderSmokeAge) * f;
      return smokeAge / (float)this.getSmokeMaxAge();
   }

   protected void func_70227_a(RayTraceResult target) {
      if (target.func_216346_c() == Type.ENTITY) {
         Entity hitEntity = ((EntityRayTraceResult)target).func_216348_a();
         if (hitEntity == this.func_234616_v_() || hitEntity instanceof SmokeRingEntity) {
            return;
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70106_y();
      }

   }

   protected float func_70185_h() {
      return 0.0F;
   }

   static {
      SMOKE_COLOR = EntityDataManager.func_187226_a(SmokeRingEntity.class, DataSerializers.field_187191_a);
      SMOKE_MAGIC = EntityDataManager.func_187226_a(SmokeRingEntity.class, DataSerializers.field_187198_h);
      SMOKE_AGE = EntityDataManager.func_187226_a(SmokeRingEntity.class, DataSerializers.field_187192_b);
      SMOKE_MAX_AGE = EntityDataManager.func_187226_a(SmokeRingEntity.class, DataSerializers.field_187192_b);
      SMOKE_SCALE = EntityDataManager.func_187226_a(SmokeRingEntity.class, DataSerializers.field_187193_c);
   }
}
