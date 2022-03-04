package lotr.common.entity.item;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRBannerProtectable;
import lotr.common.item.LOTRItemBossTrophy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityBossTrophy extends Entity implements LOTRBannerProtectable {
   public LOTREntityBossTrophy(World world) {
      super(world);
      this.func_70105_a(1.0F, 1.0F);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(18, (byte)0);
      this.field_70180_af.func_75682_a(19, (byte)0);
      this.field_70180_af.func_75682_a(20, (byte)0);
   }

   private int getTrophyTypeID() {
      return this.field_70180_af.func_75683_a(18);
   }

   private void setTrophyTypeID(int i) {
      this.field_70180_af.func_75692_b(18, (byte)i);
   }

   public void setTrophyType(LOTRItemBossTrophy.TrophyType type) {
      this.setTrophyTypeID(type.trophyID);
   }

   public LOTRItemBossTrophy.TrophyType getTrophyType() {
      return LOTRItemBossTrophy.TrophyType.forID(this.getTrophyTypeID());
   }

   public boolean isTrophyHanging() {
      return this.field_70180_af.func_75683_a(19) == 1;
   }

   public void setTrophyHanging(boolean flag) {
      this.field_70180_af.func_75692_b(19, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public int getTrophyFacing() {
      int i = this.field_70180_af.func_75683_a(20);
      if (i < 0 || i >= Direction.field_82373_c.length) {
         i = 0;
      }

      return i;
   }

   public void setTrophyFacing(int i) {
      this.field_70180_af.func_75692_b(20, (byte)i);
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
      if (this.isTrophyHanging()) {
         if (!this.hangingOnValidSurface() && !this.field_70170_p.field_72995_K && !this.field_70128_L) {
            this.dropAsItem(true);
         }
      } else {
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
      }

   }

   public boolean hangingOnValidSurface() {
      if (this.isTrophyHanging()) {
         int direction = this.getTrophyFacing();
         int opposite = Direction.field_71580_e[direction];
         int dx = Direction.field_71583_a[opposite];
         int dz = Direction.field_71581_b[opposite];
         int blockX = MathHelper.func_76128_c(this.field_70165_t);
         int blockY = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
         int blockZ = MathHelper.func_76128_c(this.field_70161_v);
         blockX += dx;
         blockZ += dz;
         Block block = this.field_70170_p.func_147439_a(blockX, blockY, blockZ);
         int blockSide = Direction.field_71582_c[direction];
         return block.isSideSolid(this.field_70170_p, blockX, blockY, blockZ, ForgeDirection.getOrientation(blockSide));
      } else {
         return false;
      }
   }

   public void func_70014_b(NBTTagCompound nbt) {
      nbt.func_74774_a("TrophyType", (byte)this.getTrophyTypeID());
      nbt.func_74757_a("TrophyHanging", this.isTrophyHanging());
      nbt.func_74774_a("TrophyFacing", (byte)this.getTrophyFacing());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      this.setTrophyTypeID(nbt.func_74771_c("TrophyType"));
      this.setTrophyHanging(nbt.func_74767_n("TrophyHanging"));
      this.setTrophyFacing(nbt.func_74771_c("TrophyFacing"));
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L && damagesource.func_76364_f() instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76364_f();
         this.dropAsItem(!entityplayer.field_71075_bZ.field_75098_d);
         return true;
      } else {
         return false;
      }
   }

   private void dropAsItem(boolean dropItem) {
      this.field_70170_p.func_72956_a(this, Blocks.field_150348_b.field_149762_H.func_150495_a(), (Blocks.field_150348_b.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150348_b.field_149762_H.func_150494_d() * 0.8F);
      if (dropItem) {
         this.func_70099_a(new ItemStack(LOTRMod.bossTrophy, 1, this.getTrophyType().trophyID), 0.0F);
      }

      this.func_70106_y();
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.bossTrophy, 1, this.getTrophyType().trophyID);
   }
}
