package lotr.common.entity.item;

import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class RingPortalEntity extends Entity {
   private static final DataParameter PORTAL_AGE;
   public static final int MAX_PORTAL_AGE = 120;
   private float prevPortalRotation;
   private float portalRotation;
   private float portalSpinSpeed;
   private static final float STANDARD_SPIN_SPEED = 4.0F;
   private static final float RECENT_USE_SPIN_SPEED = 12.0F;
   private int recentUseTick;
   private static final int RECENT_USE_TIME = 120;
   private int clientPortalAge;
   private int clientPrevPortalAge;

   public RingPortalEntity(EntityType type, World w) {
      super(type, w);
      this.portalSpinSpeed = 4.0F;
      this.func_184224_h(true);
   }

   public RingPortalEntity(World w) {
      this((EntityType)LOTREntities.RING_PORTAL.get(), w);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(PORTAL_AGE, 0);
   }

   public int getPortalAge() {
      return (Integer)this.field_70180_af.func_187225_a(PORTAL_AGE);
   }

   private void setPortalAge(int i) {
      this.field_70180_af.func_187227_b(PORTAL_AGE, i);
   }

   public float getPortalScale(float f) {
      return ((float)this.clientPrevPortalAge + (float)(this.clientPortalAge - this.clientPrevPortalAge) * f) / 120.0F;
   }

   public float getPortalRotation(float f) {
      return this.prevPortalRotation + (this.portalRotation - this.prevPortalRotation) * f;
   }

   private float getRecentUseProportion() {
      return (float)this.recentUseTick / 120.0F;
   }

   public float getScriptBrightness(float f) {
      float freq = MathHelper.func_219799_g(this.getRecentUseProportion(), 0.01F, 0.1F);
      return 0.75F + MathHelper.func_76134_b(((float)this.field_70173_aa + f) * freq) * 0.25F;
   }

   public IPacket func_213297_N() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void func_213281_b(CompoundNBT compound) {
      compound.func_74768_a("PortalAge", this.getPortalAge());
   }

   public void func_70037_a(CompoundNBT compound) {
      this.setPortalAge(compound.func_74762_e("PortalAge"));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K && !LOTRDimensions.isDimension(this.field_70170_p, World.field_234918_g_) && !LOTRDimensions.isDimension(this.field_70170_p, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
         this.func_70106_y();
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.getPortalAge() < 120) {
            this.setPortalAge(this.getPortalAge() + 1);
         }
      } else {
         if (this.recentUseTick > 0) {
            this.portalSpinSpeed = MathHelper.func_219799_g(this.getRecentUseProportion(), 4.0F, 12.0F);
            --this.recentUseTick;
         } else {
            this.portalSpinSpeed = 4.0F;
         }

         this.prevPortalRotation = this.portalRotation;

         for(this.portalRotation += this.portalSpinSpeed; this.portalRotation - this.prevPortalRotation < -180.0F; this.prevPortalRotation -= 360.0F) {
         }

         while(this.portalRotation - this.prevPortalRotation >= 180.0F) {
            this.prevPortalRotation += 360.0F;
         }

         this.clientPrevPortalAge = this.clientPortalAge;
         this.clientPortalAge = this.getPortalAge();
         if (this.field_70146_Z.nextFloat() < this.getPortalScale(1.0F)) {
            for(int i = 0; i < 1; ++i) {
               float w = this.func_213311_cf();
               float h = this.func_213302_cg();
               double x = this.func_226277_ct_();
               double y = this.func_226278_cu_() + (double)(h / 2.0F);
               double z = this.func_226281_cx_();
               double d = x + (double)MathHelper.func_151240_a(this.field_70146_Z, -w, w);
               double d1 = y + (double)MathHelper.func_151240_a(this.field_70146_Z, -h * 0.5F, h * 0.5F);
               double d2 = z + (double)MathHelper.func_151240_a(this.field_70146_Z, -w, w);
               double d3 = (x - d) / 8.0D;
               double d4 = (y - d1) / 8.0D;
               double d5 = (z - d2) / 8.0D;
               double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
               double d7 = 1.0D - d6;
               double d8 = 0.0D;
               double d9 = 0.0D;
               double d10 = 0.0D;
               if (d7 > 0.0D) {
                  d7 *= d7;
                  d8 += d3 / d6 * d7 * 0.2D;
                  d9 += d4 / d6 * d7 * 0.2D;
                  d10 += d5 / d6 * d7 * 0.2D;
               }

               this.field_70170_p.func_195594_a(ParticleTypes.field_197631_x, d, d1, d2, d8, d9, d10);
            }
         }
      }

      if (this.getPortalAge() >= 120) {
         double searchRange = 8.0D;
         List entities = this.field_70170_p.func_175647_a(Entity.class, this.func_174813_aQ().func_72321_a(searchRange, searchRange, searchRange), (entity) -> {
            if (entity != this && !(entity instanceof RingPortalEntity)) {
               return entity.func_174813_aQ().func_72326_a(this.func_174813_aQ()) && (entity instanceof PlayerEntity || !entity.func_242280_ah());
            } else {
               return false;
            }
         });
         if (!entities.isEmpty()) {
            entities.forEach((e) -> {
               LOTRMod.PROXY.setInRingPortal(e, this);
            });
         }

         if (this.field_70146_Z.nextInt(50) == 0) {
            this.func_184185_a(SoundEvents.field_187810_eg, 0.5F, this.field_70146_Z.nextFloat() * 0.4F + 0.8F);
         }
      }

   }

   public void onTransferEntity() {
      this.field_70170_p.func_72960_a(this, (byte)17);
   }

   protected void func_213284_aV() {
   }

   protected boolean func_225502_at_() {
      return false;
   }

   public boolean func_70104_M() {
      return false;
   }

   public boolean func_145773_az() {
      return true;
   }

   public boolean func_70067_L() {
      return true;
   }

   public void func_70108_f(Entity entity) {
   }

   public boolean func_85031_j(Entity entity) {
      return entity instanceof PlayerEntity ? this.func_70097_a(DamageSource.func_76365_a((PlayerEntity)entity), 0.0F) : false;
   }

   public boolean func_70097_a(DamageSource source, float f) {
      Entity entity = source.func_76346_g();
      if (entity instanceof PlayerEntity && entity == source.func_76364_f() && ((PlayerEntity)entity).field_71075_bZ.field_75098_d) {
         if (!this.field_70170_p.field_72995_K) {
            SoundType sound = SoundType.field_185853_f;
            this.func_184185_a(sound.func_185845_c(), (sound.func_185843_a() + 1.0F) / 2.0F, sound.func_185847_b() * 0.8F);
            this.field_70170_p.func_72960_a(this, (byte)16);
            this.func_70106_y();
         }

         return true;
      } else {
         return false;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 16) {
         ItemStack breakingItem = new ItemStack((IItemProvider)LOTRItems.GOLD_RING.get());

         for(int l = 0; l < 16; ++l) {
            this.field_70170_p.func_195594_a(new ItemParticleData(ParticleTypes.field_197591_B, breakingItem), this.func_226277_ct_() + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.func_213311_cf(), this.func_226278_cu_() + this.field_70146_Z.nextDouble() * (double)this.func_213302_cg(), this.func_226281_cx_() + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.func_213311_cf(), 0.0D, 0.0D, 0.0D);
         }
      } else if (b == 17) {
         this.recentUseTick = 120;
      } else {
         super.func_70103_a(b);
      }

   }

   public ItemStack getPickedResult(RayTraceResult target) {
      return new ItemStack((IItemProvider)LOTRItems.GOLD_RING.get());
   }

   static {
      PORTAL_AGE = EntityDataManager.func_187226_a(RingPortalEntity.class, DataSerializers.field_187192_b);
   }
}
