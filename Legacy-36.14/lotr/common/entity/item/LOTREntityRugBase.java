package lotr.common.entity.item;

import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class LOTREntityRugBase extends Entity implements LOTRBannerProtectable {
   private int timeSinceLastGrowl = this.getTimeUntilGrowl();

   public LOTREntityRugBase(World world) {
      super(world);
   }

   protected void func_70088_a() {
   }

   public boolean func_70067_L() {
      return true;
   }

   public AxisAlignedBB func_70046_E() {
      return this.field_70121_D;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.field_70181_x -= 0.04D;
      this.func_145771_j(this.field_70165_t, (this.field_70121_D.field_72338_b + this.field_70121_D.field_72337_e) / 2.0D, this.field_70161_v);
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      float f = 0.98F;
      if (this.field_70122_E) {
         f = 0.588F;
         Block i = this.field_70170_p.func_147439_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v));
         if (i.func_149688_o() != Material.field_151579_a) {
            f = i.field_149765_K * 0.98F;
         }
      }

      this.field_70159_w *= (double)f;
      this.field_70181_x *= 0.98D;
      this.field_70179_y *= (double)f;
      if (this.field_70122_E) {
         this.field_70181_x *= -0.5D;
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.timeSinceLastGrowl > 0) {
            --this.timeSinceLastGrowl;
         } else if (this.field_70146_Z.nextInt(5000) == 0) {
            this.field_70170_p.func_72956_a(this, this.getRugNoise(), 1.0F, (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.2F + 1.0F);
            this.timeSinceLastGrowl = this.getTimeUntilGrowl();
         }
      }

   }

   private int getTimeUntilGrowl() {
      return (60 + this.field_70146_Z.nextInt(150)) * 20;
   }

   protected abstract String getRugNoise();

   protected void func_70037_a(NBTTagCompound nbt) {
   }

   protected void func_70014_b(NBTTagCompound nbt) {
   }

   protected abstract ItemStack getRugItem();

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         SoundType blockSound = Blocks.field_150325_L.field_149762_H;
         this.field_70170_p.func_72956_a(this, blockSound.func_150495_a(), (blockSound.func_150497_c() + 1.0F) / 2.0F, blockSound.func_150494_d() * 0.8F);
         Entity attacker = damagesource.func_76346_g();
         boolean creative = attacker instanceof EntityPlayer && ((EntityPlayer)attacker).field_71075_bZ.field_75098_d;
         if (!creative) {
            this.func_70099_a(this.getRugItem(), 0.0F);
         }

         this.func_70106_y();
      }

      return true;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return this.getRugItem();
   }
}
