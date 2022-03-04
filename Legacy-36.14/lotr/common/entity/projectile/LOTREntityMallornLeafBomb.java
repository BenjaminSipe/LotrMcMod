package lotr.common.entity.projectile;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTREntityMallornLeafBomb extends EntityThrowable {
   private UUID throwerUUID;
   private int leavesAge;
   private static int MAX_LEAVES_AGE = 200;
   public float leavesDamage;

   public LOTREntityMallornLeafBomb(World world) {
      super(world);
      this.func_70105_a(2.0F, 2.0F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public LOTREntityMallornLeafBomb(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.func_70105_a(2.0F, 2.0F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.throwerUUID = thrower.func_110124_au();
   }

   public LOTREntityMallornLeafBomb(World world, EntityLivingBase thrower, EntityLivingBase target) {
      super(world, thrower);
      this.func_70105_a(2.0F, 2.0F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.throwerUUID = thrower.func_110124_au();
      this.field_70163_u = thrower.field_70163_u + (double)thrower.func_70047_e() - 0.1D;
      double dx = target.field_70165_t - thrower.field_70165_t;
      double dy = target.field_70121_D.field_72338_b + (double)(target.field_70131_O / 3.0F) - this.field_70163_u;
      double dz = target.field_70161_v - thrower.field_70161_v;
      double dxz = (double)MathHelper.func_76133_a(dx * dx + dz * dz);
      if (dxz >= 1.0E-7D) {
         float f2 = (float)(Math.atan2(dz, dx) * 180.0D / 3.141592653589793D) - 90.0F;
         float f3 = (float)(-(Math.atan2(dy, dxz) * 180.0D / 3.141592653589793D));
         double d4 = dx / dxz;
         double d5 = dz / dxz;
         this.func_70012_b(thrower.field_70165_t + d4, this.field_70163_u, thrower.field_70161_v + d5, f2, f3);
         this.field_70129_M = 0.0F;
         this.func_70186_c(dx, dy, dz, this.func_70182_d(), 1.0F);
      }

   }

   public LOTREntityMallornLeafBomb(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
      this.func_70105_a(2.0F, 2.0F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("LeavesAge", this.leavesAge);
      nbt.func_74776_a("LeavesDamage", this.leavesDamage);
      if (this.throwerUUID != null) {
         nbt.func_74778_a("ThrowerUUID", this.throwerUUID.toString());
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.leavesAge = nbt.func_74762_e("LeavesAge");
      this.leavesDamage = nbt.func_74760_g("LeavesDamage");
      if (nbt.func_74764_b("ThrowerUUID")) {
         this.throwerUUID = UUID.fromString(nbt.func_74779_i("ThrowerUUID"));
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         ++this.leavesAge;
         if (this.leavesAge >= MAX_LEAVES_AGE) {
            this.explode((Entity)null);
         }
      } else {
         Vec3 axis = Vec3.func_72443_a(-this.field_70159_w, -this.field_70181_x, -this.field_70179_y);
         int leaves = 20;

         for(int l = 0; l < leaves; ++l) {
            float angle = (float)l / (float)leaves * 2.0F * 3.1415927F;
            Vec3 rotate = Vec3.func_72443_a(1.0D, 1.0D, 1.0D);
            rotate.func_72440_a((float)Math.toRadians(40.0D));
            rotate.func_72442_b(angle);
            float dot = (float)rotate.func_72430_b(axis);
            Vec3 parallel = Vec3.func_72443_a(axis.field_72450_a * (double)dot, axis.field_72448_b * (double)dot, axis.field_72449_c * (double)dot);
            Vec3 perp = parallel.func_72444_a(rotate);
            Vec3 cross = rotate.func_72431_c(axis);
            float sin = MathHelper.func_76126_a(-angle);
            float cos = MathHelper.func_76134_b(-angle);
            Vec3 crossSin = Vec3.func_72443_a(cross.field_72450_a * (double)sin, cross.field_72448_b * (double)sin, cross.field_72449_c * (double)sin);
            Vec3 perpCos = Vec3.func_72443_a(perp.field_72450_a * (double)cos, perp.field_72448_b * (double)cos, perp.field_72449_c * (double)cos);
            Vec3 result = parallel.func_72441_c(crossSin.field_72450_a + perpCos.field_72450_a, crossSin.field_72448_b + perpCos.field_72448_b, crossSin.field_72449_c + perpCos.field_72449_c);
            double d = this.field_70165_t;
            double d1 = this.field_70163_u;
            double d2 = this.field_70161_v;
            double d3 = result.field_72450_a / 10.0D;
            double d4 = result.field_72448_b / 10.0D;
            double d5 = result.field_72449_c / 10.0D;
            LOTRMod.proxy.spawnParticle("leafGold_30", d, d1, d2, d3, d4, d5);
            LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.func_149682_b(LOTRMod.leaves) + "_" + 1, d, d1, d2, d3 * 0.5D, d4 * 0.5D, d5 * 0.5D);
         }
      }

   }

   protected void func_70184_a(MovingObjectPosition m) {
      if (!this.field_70170_p.field_72995_K) {
         if (m.field_72313_a == MovingObjectType.BLOCK) {
            this.explode((Entity)null);
         } else if (m.field_72313_a == MovingObjectType.ENTITY) {
            Entity entity = m.field_72308_g;
            if (this.isEntityVulnerable(entity)) {
               this.explode(entity);
            }
         }
      }

   }

   private void explode(Entity target) {
      if (!this.field_70170_p.field_72995_K) {
         double range = 2.0D;
         List entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, this.field_70121_D.func_72314_b(range, range, range));
         if (!entities.isEmpty()) {
            for(int i = 0; i < entities.size(); ++i) {
               EntityLivingBase entity = (EntityLivingBase)entities.get(i);
               if (this.isEntityVulnerable(entity)) {
                  float damage = this.leavesDamage / Math.max(1.0F, this.func_70032_d(entity));
                  if (damage > 0.0F) {
                     entity.func_70097_a(DamageSource.func_76358_a(this.func_85052_h()), damage);
                  }
               }
            }
         }

         this.func_70106_y();
      }

   }

   private boolean isEntityVulnerable(Entity target) {
      if (target == this.func_85052_h()) {
         return false;
      } else if (target instanceof EntityLivingBase) {
         EntityLivingBase livingTarget = (EntityLivingBase)target;
         EntityLivingBase thrower = this.func_85052_h();
         if (thrower instanceof LOTREntityNPC) {
            ((LOTREntityNPC)thrower).func_70683_ar().func_75661_b();
            return LOTRMod.canNPCAttackEntity((LOTREntityNPC)thrower, livingTarget, false);
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public EntityLivingBase func_85052_h() {
      if (this.throwerUUID != null) {
         Iterator var1 = this.field_70170_p.field_72996_f.iterator();

         while(var1.hasNext()) {
            Object obj = var1.next();
            Entity entity = (Entity)obj;
            if (entity instanceof EntityLivingBase && entity.func_110124_au().equals(this.throwerUUID)) {
               return (EntityLivingBase)entity;
            }
         }
      }

      return null;
   }

   protected float func_70182_d() {
      return 1.0F;
   }

   protected float func_70185_h() {
      return 0.0F;
   }
}
