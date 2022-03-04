package lotr.common.entity.projectile;

import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class LOTREntityProjectileBase extends Entity implements IThrowableEntity, IProjectile {
   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private Block inTile;
   private int inData = 0;
   public boolean inGround = false;
   public int shake = 0;
   public Entity shootingEntity;
   private int ticksInGround;
   private int ticksInAir = 0;
   public int canBePickedUp = 0;
   public int knockbackStrength = 0;

   public LOTREntityProjectileBase(World world) {
      super(world);
      this.func_70105_a(0.5F, 0.5F);
   }

   public LOTREntityProjectileBase(World world, ItemStack item, double d, double d1, double d2) {
      super(world);
      this.setProjectileItem(item);
      this.func_70105_a(0.5F, 0.5F);
      this.func_70107_b(d, d1, d2);
      this.field_70129_M = 0.0F;
   }

   public LOTREntityProjectileBase(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
      super(world);
      this.setProjectileItem(item);
      this.shootingEntity = entityliving;
      if (entityliving instanceof EntityPlayer) {
         this.canBePickedUp = 1;
      }

      this.func_70105_a(0.5F, 0.5F);
      this.func_70012_b(entityliving.field_70165_t, entityliving.field_70163_u + (double)entityliving.func_70047_e(), entityliving.field_70161_v, entityliving.field_70177_z, entityliving.field_70125_A);
      this.field_70165_t -= (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
      this.field_70163_u -= 0.1D;
      this.field_70161_v -= (double)(MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70129_M = 0.0F;
      this.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F));
      this.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F));
      this.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.1415927F));
      this.func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, charge * 1.5F, 1.0F);
   }

   public LOTREntityProjectileBase(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
      super(world);
      this.setProjectileItem(item);
      this.shootingEntity = entityliving;
      if (entityliving instanceof EntityPlayer) {
         this.canBePickedUp = 1;
      }

      this.func_70105_a(0.5F, 0.5F);
      this.field_70163_u = entityliving.field_70163_u + (double)entityliving.func_70047_e() - 0.1D;
      double d = target.field_70165_t - entityliving.field_70165_t;
      double d1 = target.field_70163_u + (double)target.func_70047_e() - 0.7D - this.field_70163_u;
      double d2 = target.field_70161_v - entityliving.field_70161_v;
      double d3 = (double)MathHelper.func_76133_a(d * d + d2 * d2);
      if (d3 >= 1.0E-7D) {
         float f = (float)(Math.atan2(d2, d) * 180.0D / 3.141592653589793D) - 90.0F;
         float f1 = (float)(-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D));
         double d4 = d / d3;
         double d5 = d2 / d3;
         this.func_70012_b(entityliving.field_70165_t + d4, this.field_70163_u, entityliving.field_70161_v + d5, f, f1);
         this.field_70129_M = 0.0F;
         float d6 = (float)d3 * 0.2F;
         this.func_70186_c(d, d1 + (double)d6, d2, charge * 1.5F, inaccuracy);
      }

   }

   public Entity getThrower() {
      return this.shootingEntity;
   }

   public void setThrower(Entity entity) {
      this.shootingEntity = entity;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_70112_a(double d) {
      double d1 = this.field_70121_D.func_72320_b() * 4.0D;
      d1 *= 64.0D;
      return d < d1 * d1;
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(17, (byte)0);
      this.field_70180_af.func_82709_a(18, 5);
   }

   public ItemStack getProjectileItem() {
      return this.field_70180_af.func_82710_f(18);
   }

   public void setProjectileItem(ItemStack item) {
      this.field_70180_af.func_75692_b(18, item);
   }

   public void func_70186_c(double d, double d1, double d2, float f, float f1) {
      float f2 = MathHelper.func_76133_a(d * d + d1 * d1 + d2 * d2);
      d /= (double)f2;
      d1 /= (double)f2;
      d2 /= (double)f2;
      d += this.field_70146_Z.nextGaussian() * 0.0075D * (double)f1;
      d1 += this.field_70146_Z.nextGaussian() * 0.0075D * (double)f1;
      d2 += this.field_70146_Z.nextGaussian() * 0.0075D * (double)f1;
      d *= (double)f;
      d1 *= (double)f;
      d2 *= (double)f;
      this.field_70159_w = d;
      this.field_70181_x = d1;
      this.field_70179_y = d2;
      float f3 = MathHelper.func_76133_a(d * d + d2 * d2);
      this.field_70126_B = this.field_70177_z = (float)(Math.atan2(d, d2) * 180.0D / 3.141592653589793D);
      this.field_70127_C = this.field_70125_A = (float)(Math.atan2(d1, (double)f3) * 180.0D / 3.141592653589793D);
      this.ticksInGround = 0;
   }

   public void func_70016_h(double d, double d1, double d2) {
      this.field_70159_w = d;
      this.field_70181_x = d1;
      this.field_70179_y = d2;
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(d * d + d2 * d2);
         this.field_70126_B = this.field_70177_z = (float)(Math.atan2(d, d2) * 180.0D / 3.141592653589793D);
         this.field_70127_C = this.field_70125_A = (float)(Math.atan2(d1, (double)f) * 180.0D / 3.141592653589793D);
         this.field_70127_C = this.field_70125_A;
         this.field_70126_B = this.field_70177_z;
         this.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         this.ticksInGround = 0;
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / 3.141592653589793D);
         this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)f) * 180.0D / 3.141592653589793D);
      }

      Block block = this.field_70170_p.func_147439_a(this.xTile, this.yTile, this.zTile);
      if (block != Blocks.field_150350_a) {
         block.func_149719_a(this.field_70170_p, this.xTile, this.yTile, this.zTile);
         AxisAlignedBB axisalignedbb = block.func_149668_a(this.field_70170_p, this.xTile, this.yTile, this.zTile);
         if (axisalignedbb != null && axisalignedbb.func_72318_a(Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
            this.inGround = true;
         }
      }

      if (this.shake > 0) {
         --this.shake;
      }

      if (this.inGround) {
         Block j = this.field_70170_p.func_147439_a(this.xTile, this.yTile, this.zTile);
         int k = this.field_70170_p.func_72805_g(this.xTile, this.yTile, this.zTile);
         if (j == this.inTile && k == this.inData) {
            ++this.ticksInGround;
            if (this.ticksInGround >= this.maxTicksInGround()) {
               this.func_70106_y();
            }
         } else {
            this.inGround = false;
            this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         }
      } else {
         ++this.ticksInAir;
         Vec3 vec3d = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         Vec3 vec3d1 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         MovingObjectPosition movingobjectposition = this.field_70170_p.func_147447_a(vec3d, vec3d1, false, true, false);
         vec3d = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         vec3d1 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         if (movingobjectposition != null) {
            vec3d1 = Vec3.func_72443_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
         }

         Entity entity = null;
         List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
         double d = 0.0D;

         int l;
         for(l = 0; l < list.size(); ++l) {
            Entity entity1 = (Entity)list.get(l);
            if (entity1.func_70067_L() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
               float f5 = 0.3F;
               AxisAlignedBB axisalignedbb1 = entity1.field_70121_D.func_72314_b((double)f5, (double)f5, (double)f5);
               MovingObjectPosition movingobjectposition1 = axisalignedbb1.func_72327_a(vec3d, vec3d1);
               if (movingobjectposition1 != null) {
                  double d1 = vec3d.func_72438_d(movingobjectposition1.field_72307_f);
                  if (d1 < d || d == 0.0D) {
                     entity = entity1;
                     d = d1;
                  }
               }
            }
         }

         if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
         }

         if (movingobjectposition != null && movingobjectposition.field_72308_g instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.field_72308_g;
            if (entityplayer.field_71075_bZ.field_75102_a || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).func_96122_a(entityplayer)) {
               movingobjectposition = null;
            }
         }

         float f4;
         int k1;
         if (movingobjectposition != null) {
            Entity hitEntity = movingobjectposition.field_72308_g;
            if (hitEntity == null) {
               this.xTile = movingobjectposition.field_72311_b;
               this.yTile = movingobjectposition.field_72312_c;
               this.zTile = movingobjectposition.field_72309_d;
               this.inTile = this.field_70170_p.func_147439_a(this.xTile, this.yTile, this.zTile);
               this.inData = this.field_70170_p.func_72805_g(this.xTile, this.yTile, this.zTile);
               this.field_70159_w = (double)((float)(movingobjectposition.field_72307_f.field_72450_a - this.field_70165_t));
               this.field_70181_x = (double)((float)(movingobjectposition.field_72307_f.field_72448_b - this.field_70163_u));
               this.field_70179_y = (double)((float)(movingobjectposition.field_72307_f.field_72449_c - this.field_70161_v));
               f4 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
               this.field_70165_t -= this.field_70159_w / (double)f4 * 0.05D;
               this.field_70163_u -= this.field_70181_x / (double)f4 * 0.05D;
               this.field_70161_v -= this.field_70179_y / (double)f4 * 0.05D;
               this.field_70170_p.func_72956_a(this, this.getImpactSound(), 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
               this.inGround = true;
               this.shake = 7;
               this.setIsCritical(false);
               if (this.inTile.func_149688_o() != Material.field_151579_a) {
                  this.inTile.func_149670_a(this.field_70170_p, this.xTile, this.yTile, this.zTile, this);
               }
            } else {
               ItemStack itemstack = this.getProjectileItem();
               k1 = MathHelper.func_76143_f((double)this.getBaseImpactDamage(hitEntity, itemstack));
               int fireAspect = 0;
               if (itemstack != null) {
                  if (this.shootingEntity instanceof EntityLivingBase && hitEntity instanceof EntityLivingBase) {
                     this.knockbackStrength += EnchantmentHelper.func_77507_b((EntityLivingBase)this.shootingEntity, (EntityLivingBase)hitEntity);
                  } else {
                     this.knockbackStrength += LOTRWeaponStats.getTotalKnockback(itemstack);
                  }
               }

               if (this.getIsCritical()) {
                  k1 += this.field_70146_Z.nextInt(k1 / 2 + 2);
               }

               double[] prevMotion = new double[]{hitEntity.field_70159_w, hitEntity.field_70181_x, hitEntity.field_70179_y};
               DamageSource damagesource = this.getDamageSource();
               if (hitEntity.func_70097_a(damagesource, (float)k1)) {
                  double[] newMotion = new double[]{hitEntity.field_70159_w, hitEntity.field_70181_x, hitEntity.field_70179_y};
                  float kbf = this.getKnockbackFactor();
                  hitEntity.field_70159_w = prevMotion[0] + (newMotion[0] - prevMotion[0]) * (double)kbf;
                  hitEntity.field_70181_x = prevMotion[1] + (newMotion[1] - prevMotion[1]) * (double)kbf;
                  hitEntity.field_70179_y = prevMotion[2] + (newMotion[2] - prevMotion[2]) * (double)kbf;
                  if (this.func_70027_ad()) {
                     hitEntity.func_70015_d(5);
                  }

                  if (hitEntity instanceof EntityLivingBase) {
                     EntityLivingBase hitEntityLiving = (EntityLivingBase)hitEntity;
                     if (this.knockbackStrength > 0) {
                        float knockback = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                        if (knockback > 0.0F) {
                           hitEntityLiving.func_70024_g(this.field_70159_w * (double)this.knockbackStrength * 0.6D / (double)knockback, 0.1D, this.field_70179_y * (double)this.knockbackStrength * 0.6D / (double)knockback);
                        }
                     }

                     if (fireAspect > 0) {
                        hitEntityLiving.func_70015_d(fireAspect * 4);
                     }

                     if (this.shootingEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a(hitEntityLiving, this.shootingEntity);
                        EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, hitEntityLiving);
                     }

                     if (this.shootingEntity instanceof EntityPlayerMP && hitEntityLiving instanceof EntityPlayer) {
                        ((EntityPlayerMP)this.shootingEntity).field_71135_a.func_147359_a(new S2BPacketChangeGameState(6, 0.0F));
                     }
                  }

                  this.field_70170_p.func_72956_a(this, this.getImpactSound(), 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
                  this.onCollideWithTarget(hitEntity);
               } else {
                  this.field_70159_w *= -0.1D;
                  this.field_70181_x *= -0.1D;
                  this.field_70179_y *= -0.1D;
                  this.field_70177_z += 180.0F;
                  this.field_70126_B += 180.0F;
                  this.ticksInAir = 0;
               }
            }
         }

         if (this.getIsCritical()) {
            for(l = 0; l < 4; ++l) {
               this.field_70170_p.func_72869_a("crit", this.field_70165_t + this.field_70159_w * (double)l / 4.0D, this.field_70163_u + this.field_70181_x * (double)l / 4.0D, this.field_70161_v + this.field_70179_y * (double)l / 4.0D, -this.field_70159_w, -this.field_70181_x + 0.2D, -this.field_70179_y);
            }
         }

         this.field_70165_t += this.field_70159_w;
         this.field_70163_u += this.field_70181_x;
         this.field_70161_v += this.field_70179_y;
         float f3 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / 3.141592653589793D);

         for(this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)f3) * 180.0D / 3.141592653589793D); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F) {
         }

         while(this.field_70125_A - this.field_70127_C >= 180.0F) {
            this.field_70127_C += 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B < -180.0F) {
            this.field_70126_B -= 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B >= 180.0F) {
            this.field_70126_B += 360.0F;
         }

         this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
         this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
         f4 = this.getSpeedReduction();
         if (this.func_70090_H()) {
            for(k1 = 0; k1 < 4; ++k1) {
               float f7 = 0.25F;
               this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * (double)f7, this.field_70163_u - this.field_70181_x * (double)f7, this.field_70161_v - this.field_70179_y * (double)f7, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            }

            f4 = 0.8F;
         }

         this.field_70159_w *= (double)f4;
         this.field_70181_x *= (double)f4;
         this.field_70179_y *= (double)f4;
         this.field_70181_x -= 0.05000000074505806D;
         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         this.func_145775_I();
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      nbt.func_74768_a("xTile", this.xTile);
      nbt.func_74768_a("yTile", this.yTile);
      nbt.func_74768_a("zTile", this.zTile);
      nbt.func_74768_a("inTile", Block.func_149682_b(this.inTile));
      nbt.func_74774_a("inData", (byte)this.inData);
      nbt.func_74774_a("shake", (byte)this.shake);
      nbt.func_74774_a("inGround", (byte)(this.inGround ? 1 : 0));
      nbt.func_74774_a("pickup", (byte)this.canBePickedUp);
      nbt.func_74774_a("Knockback", (byte)this.knockbackStrength);
      if (this.getProjectileItem() != null) {
         nbt.func_74782_a("ProjectileItem", this.getProjectileItem().func_77955_b(new NBTTagCompound()));
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      this.xTile = nbt.func_74762_e("xTile");
      this.yTile = nbt.func_74762_e("yTile");
      this.zTile = nbt.func_74762_e("zTile");
      this.inTile = Block.func_149729_e(nbt.func_74762_e("inTile"));
      this.inData = nbt.func_74771_c("inData");
      this.shake = nbt.func_74771_c("shake");
      this.inGround = nbt.func_74771_c("inGround") == 1;
      this.canBePickedUp = nbt.func_74771_c("pickup");
      this.knockbackStrength = nbt.func_74771_c("Knockback");
      if (nbt.func_74764_b("itemID")) {
         ItemStack item = new ItemStack(Item.func_150899_d(nbt.func_74762_e("itemID")), 1, nbt.func_74762_e("itemDamage"));
         if (nbt.func_74764_b("ItemTagCompound")) {
            item.func_77982_d(nbt.func_74775_l("ItemTagCompound"));
         }

         this.setProjectileItem(item);
      } else {
         this.setProjectileItem(ItemStack.func_77949_a(nbt.func_74775_l("ProjectileItem")));
      }

   }

   protected ItemStack createPickupDrop(EntityPlayer entityplayer) {
      ItemStack itemstack = this.getProjectileItem();
      if (itemstack != null) {
         ItemStack itemPickup = itemstack.func_77946_l();
         if (itemPickup.func_77984_f()) {
            itemPickup.func_77972_a(1, entityplayer);
            if (itemPickup.func_77960_j() >= itemPickup.func_77958_k()) {
               return null;
            }
         }

         return itemPickup;
      } else {
         return null;
      }
   }

   public void func_70100_b_(EntityPlayer entityplayer) {
      if (!this.field_70170_p.field_72995_K && this.inGround && this.shake <= 0) {
         ItemStack itemstack = this.createPickupDrop(entityplayer);
         if (itemstack != null) {
            boolean pickup = this.canBePickedUp == 1 || this.canBePickedUp == 2 && entityplayer.field_71075_bZ.field_75098_d;
            if (this.canBePickedUp == 1 && !entityplayer.field_71071_by.func_70441_a(itemstack.func_77946_l())) {
               pickup = false;
               EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, itemstack);
               entityitem.field_145804_b = 0;
               this.field_70170_p.func_72838_d(entityitem);
               this.func_70106_y();
            }

            if (pickup) {
               if (!this.field_70128_L) {
                  EntityTracker entitytracker = ((WorldServer)this.field_70170_p).func_73039_n();
                  entitytracker.func_151247_a(this, new S0DPacketCollectItem(this.func_145782_y(), entityplayer.func_145782_y()));
               }

               this.func_85030_a("random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
               this.func_70106_y();
            }
         } else {
            this.func_70106_y();
         }
      }

   }

   protected void onCollideWithTarget(Entity entity) {
      if (!this.field_70170_p.field_72995_K) {
         ItemStack itemstack = this.getProjectileItem();
         if (itemstack == null || !itemstack.func_77984_f()) {
            this.func_70106_y();
         }
      }

   }

   protected boolean func_70041_e_() {
      return false;
   }

   public float func_70053_R() {
      return 0.0F;
   }

   public boolean func_70075_an() {
      return false;
   }

   public abstract float getBaseImpactDamage(Entity var1, ItemStack var2);

   protected float getKnockbackFactor() {
      return 1.0F;
   }

   public DamageSource getDamageSource() {
      return this.shootingEntity == null ? DamageSource.func_76356_a(this, this) : DamageSource.func_76356_a(this, this.shootingEntity);
   }

   public void setIsCritical(boolean flag) {
      this.field_70180_af.func_75692_b(17, (byte)(flag ? 1 : 0));
   }

   public boolean getIsCritical() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public String getImpactSound() {
      return "random.bowhit";
   }

   public float getSpeedReduction() {
      return 0.99F;
   }

   public int maxTicksInGround() {
      return this.canBePickedUp == 1 ? 6000 : 1200;
   }
}
