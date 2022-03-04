package lotr.common.entity.animal;

import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTREntityRhino extends LOTREntityHorse {
   public LOTREntityRhino(World world) {
      super(world);
      this.func_70105_a(1.7F, 1.9F);
   }

   protected boolean isMountHostile() {
      return true;
   }

   protected EntityAIBase createMountAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.0D, true);
   }

   public int func_110265_bP() {
      return 0;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
   }

   protected void onLOTRHorseSpawn() {
      double maxHealth = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
      maxHealth *= 1.5D;
      maxHealth = Math.max(maxHealth, 40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(maxHealth);
      double speed = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
      speed *= 1.2D;
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(speed);
      double jumpStrength = this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111126_e();
      jumpStrength *= 0.5D;
      this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111128_a(jumpStrength);
   }

   protected double clampChildHealth(double health) {
      return MathHelper.func_151237_a(health, 20.0D, 50.0D);
   }

   protected double clampChildJump(double jump) {
      return MathHelper.func_151237_a(jump, 0.2D, 0.8D);
   }

   protected double clampChildSpeed(double speed) {
      return MathHelper.func_151237_a(speed, 0.12D, 0.42D);
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack != null && itemstack.func_77973_b() == Items.field_151015_O;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70153_n instanceof EntityLivingBase) {
            EntityLivingBase rhinoRider = (EntityLivingBase)this.field_70153_n;
            float momentum = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            if (momentum > 0.2F) {
               this.func_70031_b(true);
            } else {
               this.func_70031_b(false);
            }

            if (momentum >= 0.32F) {
               float strength = momentum * 15.0F;
               Vec3 position = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
               Vec3 look = this.func_70040_Z();
               float sightWidth = 1.0F;
               double range = 0.5D;
               List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72331_e(1.0D, 1.0D, 1.0D).func_72321_a(look.field_72450_a * range, look.field_72448_b * range, look.field_72449_c * range).func_72314_b((double)sightWidth, (double)sightWidth, (double)sightWidth));
               boolean hitAnyEntities = false;

               for(int i = 0; i < list.size(); ++i) {
                  Entity obj = (Entity)list.get(i);
                  if (obj instanceof EntityLivingBase) {
                     EntityLivingBase entity = (EntityLivingBase)obj;
                     if (entity != rhinoRider && (!(rhinoRider instanceof EntityPlayer) || LOTRMod.canPlayerAttackEntity((EntityPlayer)rhinoRider, entity, false)) && (!(rhinoRider instanceof EntityCreature) || LOTRMod.canNPCAttackEntity((EntityCreature)rhinoRider, entity, false))) {
                        boolean flag = entity.func_70097_a(DamageSource.func_76358_a(this), strength);
                        if (flag) {
                           float knockback = strength * 0.05F;
                           entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * knockback), (double)knockback, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * knockback));
                           hitAnyEntities = true;
                           if (entity instanceof EntityLiving) {
                              EntityLiving entityliving = (EntityLiving)entity;
                              if (entityliving.func_70638_az() == this) {
                                 entityliving.func_70661_as().func_75499_g();
                                 entityliving.func_70624_b(rhinoRider);
                              }
                           }
                        }
                     }
                  }
               }

               if (hitAnyEntities) {
                  this.field_70170_p.func_72956_a(this, "lotr:troll.ologHai_hammer", 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
               }
            }
         } else if (this.func_70638_az() != null) {
            float momentum = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            if (momentum > 0.2F) {
               this.func_70031_b(true);
            } else {
               this.func_70031_b(false);
            }
         } else {
            this.func_70031_b(false);
         }
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      int j = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(1 + i);

      int meat;
      for(meat = 0; meat < j; ++meat) {
         this.func_145779_a(LOTRMod.rhinoHorn, 1);
      }

      meat = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      for(int l = 0; l < meat; ++l) {
         if (this.func_70027_ad()) {
            this.func_145779_a(LOTRMod.rhinoCooked, 1);
         } else {
            this.func_145779_a(LOTRMod.rhinoRaw, 1);
         }
      }

   }

   protected String func_70639_aQ() {
      super.func_70639_aQ();
      return "lotr:rhino.say";
   }

   protected String func_70621_aR() {
      super.func_70621_aR();
      return "lotr:rhino.hurt";
   }

   protected String func_70673_aS() {
      super.func_70673_aS();
      return "lotr:rhino.death";
   }

   protected String func_110217_cl() {
      super.func_110217_cl();
      return "lotr:rhino.say";
   }
}
