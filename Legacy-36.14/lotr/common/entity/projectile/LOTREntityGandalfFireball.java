package lotr.common.entity.projectile;

import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTREntityGandalfFireball extends EntityThrowable {
   public int animationTick;

   public LOTREntityGandalfFireball(World world) {
      super(world);
   }

   public LOTREntityGandalfFireball(World world, EntityLivingBase entityliving) {
      super(world, entityliving);
   }

   public LOTREntityGandalfFireball(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, Short.valueOf((short)0));
   }

   public int getFireballAge() {
      return this.field_70180_af.func_75693_b(16);
   }

   public void setFireballAge(int age) {
      this.field_70180_af.func_75692_b(16, (short)age);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("FireballAge", this.getFireballAge());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setFireballAge(nbt.func_74762_e("FireballAge"));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70173_aa % 5 == 0) {
         ++this.animationTick;
         if (this.animationTick >= 4) {
            this.animationTick = 0;
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         this.setFireballAge(this.getFireballAge() + 1);
         if (this.getFireballAge() >= 200) {
            this.explode((Entity)null);
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
         this.field_70170_p.func_72956_a(this, "lotr:item.gandalfFireball", 4.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.FIREBALL_GANDALF_WHITE, this);
         LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(this, 64.0D));
         if (target != null && this.isEntityVulnerable(target)) {
            target.func_70097_a(DamageSource.func_76358_a(this.func_85052_h()), 10.0F);
         }

         List entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, this.field_70121_D.func_72314_b(6.0D, 6.0D, 6.0D));
         if (!entities.isEmpty()) {
            for(int i = 0; i < entities.size(); ++i) {
               EntityLivingBase entity = (EntityLivingBase)entities.get(i);
               if (entity != target && this.isEntityVulnerable(entity)) {
                  float damage = 10.0F - this.func_70032_d(entity) * 0.5F;
                  if (damage > 0.0F) {
                     entity.func_70097_a(DamageSource.func_76358_a(this.func_85052_h()), damage);
                  }
               }
            }
         }

         this.func_70106_y();
      }
   }

   private boolean isEntityVulnerable(Entity entity) {
      if (entity == this.func_85052_h()) {
         return false;
      } else if (!(entity instanceof EntityLivingBase)) {
         return false;
      } else if (entity instanceof EntityPlayer) {
         return LOTRLevelData.getData((EntityPlayer)entity).getAlignment(LOTRFaction.HIGH_ELF) < 0.0F;
      } else {
         return !LOTRFaction.HIGH_ELF.isGoodRelation(LOTRMod.getNPCFaction(entity));
      }
   }

   protected float func_70182_d() {
      return 1.5F;
   }

   protected float func_70185_h() {
      return 0.0F;
   }
}
