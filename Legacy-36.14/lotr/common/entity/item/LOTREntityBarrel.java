package lotr.common.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBarrel;
import lotr.common.world.biome.LOTRBiomeGenMirkwood;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityBarrel extends Entity {
   private boolean field_70279_a;
   private double speedMultiplier;
   private static double minSpeedMultiplier = 0.04D;
   private static double maxSpeedMultiplier = 0.25D;
   private int boatPosRotationIncrements;
   private double boatX;
   private double boatY;
   private double boatZ;
   private double boatYaw;
   private double boatPitch;
   private double velocityX;
   private double velocityY;
   private double velocityZ;
   public NBTTagCompound barrelItemData;

   public LOTREntityBarrel(World world) {
      super(world);
      this.field_70279_a = true;
      this.speedMultiplier = minSpeedMultiplier;
      this.field_70156_m = true;
      this.func_70105_a(1.0F, 1.0F);
      this.field_70129_M = 0.0F;
   }

   public LOTREntityBarrel(World world, double d, double d1, double d2) {
      this(world);
      this.func_70107_b(d, d1 + (double)this.field_70129_M, d2);
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
      this.field_70169_q = d;
      this.field_70167_r = d1;
      this.field_70166_s = d2;
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(17, 0);
      this.field_70180_af.func_75682_a(18, 1);
      this.field_70180_af.func_75682_a(19, 0.0F);
      this.field_70180_af.func_75682_a(20, new ItemStack(LOTRMod.barrel));
   }

   public void setTimeSinceHit(int i) {
      this.field_70180_af.func_75692_b(17, i);
   }

   public int getTimeSinceHit() {
      return this.field_70180_af.func_75679_c(17);
   }

   public void setForwardDirection(int i) {
      this.field_70180_af.func_75692_b(18, i);
   }

   public int getForwardDirection() {
      return this.field_70180_af.func_75679_c(18);
   }

   public void setDamageTaken(float f) {
      this.field_70180_af.func_75692_b(19, f);
   }

   public float getDamageTaken() {
      return this.field_70180_af.func_111145_d(19);
   }

   private void setBarrelItem(ItemStack itemstack) {
      this.field_70180_af.func_75692_b(20, itemstack);
   }

   private ItemStack getBarrelItem() {
      return this.field_70180_af.func_82710_f(20);
   }

   protected boolean func_70041_e_() {
      return false;
   }

   public AxisAlignedBB func_70114_g(Entity par1Entity) {
      return par1Entity.field_70121_D;
   }

   public AxisAlignedBB func_70046_E() {
      return this.field_70121_D;
   }

   public boolean func_70104_M() {
      return true;
   }

   public double func_70042_X() {
      return 0.5D;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (this.func_85032_ar()) {
         return false;
      } else if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         this.setForwardDirection(-this.getForwardDirection());
         this.setTimeSinceHit(10);
         this.setDamageTaken(this.getDamageTaken() + f * 10.0F);
         SoundType stepSound = LOTRMod.barrel.field_149762_H;
         this.func_85030_a(stepSound.func_150495_a(), (stepSound.func_150497_c() + 1.0F) / 2.0F, stepSound.func_150494_d() * 0.8F);
         this.func_70018_K();
         boolean isCreative = damagesource.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)damagesource.func_76346_g()).field_71075_bZ.field_75098_d;
         if (isCreative || this.getDamageTaken() > 40.0F) {
            if (this.field_70153_n != null) {
               this.field_70153_n.func_70078_a(this);
            }

            if (!isCreative) {
               this.func_70099_a(this.getBarrelDrop(), 0.0F);
            }

            this.func_70106_y();
         }

         return true;
      } else {
         return true;
      }
   }

   private ItemStack getBarrelDrop() {
      ItemStack itemstack = new ItemStack(LOTRMod.barrel);
      if (this.barrelItemData != null) {
         LOTRItemBarrel.setBarrelData(itemstack, this.barrelItemData);
      }

      return itemstack;
   }

   @SideOnly(Side.CLIENT)
   public void func_70057_ab() {
      this.setForwardDirection(-this.getForwardDirection());
      this.setTimeSinceHit(10);
      this.setDamageTaken(this.getDamageTaken() * 11.0F);
   }

   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   @SideOnly(Side.CLIENT)
   public void func_70056_a(double d, double d1, double d2, float f, float f1, int i) {
      if (this.field_70279_a) {
         this.boatPosRotationIncrements = i + 5;
      } else {
         double d3 = d - this.field_70165_t;
         double d4 = d1 - this.field_70163_u;
         double d5 = d2 - this.field_70161_v;
         double d6 = d3 * d3 + d4 * d4 + d5 * d5;
         if (d6 <= 1.0D) {
            return;
         }

         this.boatPosRotationIncrements = 3;
      }

      this.boatX = d;
      this.boatY = d1;
      this.boatZ = d2;
      this.boatYaw = (double)f;
      this.boatPitch = (double)f1;
      this.field_70159_w = this.velocityX;
      this.field_70181_x = this.velocityY;
      this.field_70179_y = this.velocityZ;
   }

   @SideOnly(Side.CLIENT)
   public void func_70016_h(double par1, double par3, double par5) {
      this.velocityX = this.field_70159_w = par1;
      this.velocityY = this.field_70181_x = par3;
      this.velocityZ = this.field_70179_y = par5;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         this.setBarrelItem(this.getBarrelDrop());
      }

      if (this.getTimeSinceHit() > 0) {
         this.setTimeSinceHit(this.getTimeSinceHit() - 1);
      }

      if (this.getDamageTaken() > 0.0F) {
         this.setDamageTaken(this.getDamageTaken() - 1.0F);
      }

      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      byte b0 = 5;
      double d0 = 0.0D;

      for(int i = 0; i < b0; ++i) {
         double d1 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (double)(i + 0) / (double)b0 - 0.125D;
         double d2 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (double)(i + 1) / (double)b0 - 0.125D;
         AxisAlignedBB axisalignedbb = AxisAlignedBB.func_72330_a(this.field_70121_D.field_72340_a, d1, this.field_70121_D.field_72339_c, this.field_70121_D.field_72336_d, d2, this.field_70121_D.field_72334_f);
         if (this.field_70170_p.func_72830_b(axisalignedbb, Material.field_151586_h)) {
            d0 += 1.0D / (double)b0;
         }
      }

      double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      double d4;
      double d5;
      if (d3 > 0.2625D) {
         d4 = Math.cos((double)this.field_70177_z * 3.141592653589793D / 180.0D);
         d5 = Math.sin((double)this.field_70177_z * 3.141592653589793D / 180.0D);

         for(int j = 0; (double)j < 1.0D + d3 * 60.0D; ++j) {
            double d6 = (double)(this.field_70146_Z.nextFloat() * 2.0F - 1.0F);
            double d7 = (double)(this.field_70146_Z.nextInt(2) * 2 - 1) * 0.7D;
            double d8;
            double d9;
            if (this.field_70146_Z.nextBoolean()) {
               d8 = this.field_70165_t - d4 * d6 * 0.8D + d5 * d7;
               d9 = this.field_70161_v - d5 * d6 * 0.8D - d4 * d7;
               this.field_70170_p.func_72869_a("splash", d8, this.field_70163_u - 0.125D, d9, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            } else {
               d8 = this.field_70165_t + d4 + d5 * d6 * 0.7D;
               d9 = this.field_70161_v + d5 - d4 * d6 * 0.7D;
               this.field_70170_p.func_72869_a("splash", d8, this.field_70163_u - 0.125D, d9, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            }
         }
      }

      double d11;
      double d10;
      if (this.field_70170_p.field_72995_K && this.field_70279_a) {
         if (this.boatPosRotationIncrements > 0) {
            d4 = this.field_70165_t + (this.boatX - this.field_70165_t) / (double)this.boatPosRotationIncrements;
            d5 = this.field_70163_u + (this.boatY - this.field_70163_u) / (double)this.boatPosRotationIncrements;
            d11 = this.field_70161_v + (this.boatZ - this.field_70161_v) / (double)this.boatPosRotationIncrements;
            d10 = MathHelper.func_76138_g(this.boatYaw - (double)this.field_70177_z);
            this.field_70177_z = (float)((double)this.field_70177_z + d10 / (double)this.boatPosRotationIncrements);
            this.field_70125_A = (float)((double)this.field_70125_A + (this.boatPitch - (double)this.field_70125_A) / (double)this.boatPosRotationIncrements);
            --this.boatPosRotationIncrements;
            this.func_70107_b(d4, d5, d11);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
         } else {
            d4 = this.field_70165_t + this.field_70159_w;
            d5 = this.field_70163_u + this.field_70181_x;
            d11 = this.field_70161_v + this.field_70179_y;
            this.func_70107_b(d4, d5, d11);
            if (this.field_70122_E) {
               this.field_70159_w *= 0.5D;
               this.field_70181_x *= 0.5D;
               this.field_70179_y *= 0.5D;
            }

            this.field_70159_w *= 0.99D;
            this.field_70181_x *= 0.95D;
            this.field_70179_y *= 0.99D;
         }
      } else {
         if (d0 < 1.0D) {
            d4 = d0 * 2.0D - 1.0D;
            this.field_70181_x += 0.04D * d4;
         } else {
            if (this.field_70181_x < 0.0D) {
               this.field_70181_x /= 2.0D;
            }

            this.field_70181_x += 0.007D;
         }

         if (this.field_70153_n != null && this.field_70153_n instanceof EntityLivingBase) {
            d4 = (double)((EntityLivingBase)this.field_70153_n).field_70701_bs;
            if (d4 > 0.0D) {
               d5 = -Math.sin((double)(this.field_70153_n.field_70177_z * 3.1415927F / 180.0F));
               d11 = Math.cos((double)(this.field_70153_n.field_70177_z * 3.1415927F / 180.0F));
               this.field_70159_w += d5 * this.speedMultiplier * 0.05D;
               this.field_70179_y += d11 * this.speedMultiplier * 0.05D;
            }
         }

         d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         if (d4 > maxSpeedMultiplier) {
            d5 = maxSpeedMultiplier / d4;
            this.field_70159_w *= d5;
            this.field_70179_y *= d5;
            d4 = maxSpeedMultiplier;
         }

         if (d4 > d3 && this.speedMultiplier < maxSpeedMultiplier) {
            this.speedMultiplier += (maxSpeedMultiplier - this.speedMultiplier) / (maxSpeedMultiplier / 150.0D);
            if (this.speedMultiplier > maxSpeedMultiplier) {
               this.speedMultiplier = maxSpeedMultiplier;
            }
         } else {
            this.speedMultiplier -= (this.speedMultiplier - minSpeedMultiplier) / (maxSpeedMultiplier / 150.0D);
            if (this.speedMultiplier < minSpeedMultiplier) {
               this.speedMultiplier = minSpeedMultiplier;
            }
         }

         if (this.field_70122_E) {
            this.field_70159_w *= 0.5D;
            this.field_70181_x *= 0.5D;
            this.field_70179_y *= 0.5D;
         }

         this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.99D;
         this.field_70181_x *= 0.95D;
         this.field_70179_y *= 0.99D;
         this.field_70125_A = 0.0F;
         d5 = (double)this.field_70177_z;
         d11 = this.field_70169_q - this.field_70165_t;
         d10 = this.field_70166_s - this.field_70161_v;
         if (d11 * d11 + d10 * d10 > 0.001D) {
            d5 = (double)((float)(Math.atan2(d10, d11) * 180.0D / 3.141592653589793D));
         }

         double d12 = MathHelper.func_76138_g(d5 - (double)this.field_70177_z);
         if (d12 > 20.0D) {
            d12 = 20.0D;
         }

         if (d12 < -20.0D) {
            d12 = -20.0D;
         }

         this.field_70177_z = (float)((double)this.field_70177_z + d12);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         if (!this.field_70170_p.field_72995_K) {
            List nearbyEntities = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(0.2D, 0.0D, 0.2D));
            if (nearbyEntities != null && !nearbyEntities.isEmpty()) {
               for(int l = 0; l < nearbyEntities.size(); ++l) {
                  Entity entity = (Entity)nearbyEntities.get(l);
                  if (entity != this.field_70153_n && entity.func_70104_M() && entity instanceof LOTREntityBarrel) {
                     entity.func_70108_f(this);
                  }
               }
            }

            if (this.field_70153_n != null && this.field_70153_n.field_70128_L) {
               this.field_70153_n = null;
            }
         }
      }

      if (!this.field_70170_p.field_72995_K && this.field_70153_n instanceof EntityPlayer && this.field_70170_p.func_72830_b(this.field_70121_D, Material.field_151586_h)) {
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         if (this.field_70170_p.func_72807_a(i, k) instanceof LOTRBiomeGenMirkwood) {
            LOTRLevelData.getData((EntityPlayer)this.field_70153_n).addAchievement(LOTRAchievement.rideBarrelMirkwood);
         }
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      if (this.barrelItemData != null) {
         nbt.func_74782_a("BarrelItemData", this.barrelItemData);
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      if (nbt.func_74764_b("BarrelItemData")) {
         this.barrelItemData = nbt.func_74775_l("BarrelItemData");
      }

   }

   @SideOnly(Side.CLIENT)
   public float func_70053_R() {
      return 0.0F;
   }

   public boolean func_130002_c(EntityPlayer entityplayer) {
      if (this.field_70153_n != null && this.field_70153_n instanceof EntityPlayer && this.field_70153_n != entityplayer) {
         return true;
      } else {
         if (!this.field_70170_p.field_72995_K) {
            entityplayer.func_70078_a(this);
         }

         return true;
      }
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return this.getBarrelItem();
   }
}
