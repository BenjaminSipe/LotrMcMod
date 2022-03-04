package lotr.common.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityBannerWall extends EntityHanging {
   private NBTTagCompound protectData;
   private boolean updatedClientBB = false;

   public LOTREntityBannerWall(World world) {
      super(world);
      this.func_70105_a(0.0F, 0.0F);
   }

   public LOTREntityBannerWall(World world, int i, int j, int k, int dir) {
      super(world, i, j, k, dir);
      this.func_70105_a(0.0F, 0.0F);
      this.func_82328_a(dir);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(10, 0);
      this.field_70180_af.func_75682_a(11, 0);
      this.field_70180_af.func_75682_a(12, 0);
      this.field_70180_af.func_75682_a(13, (byte)0);
      this.field_70180_af.func_75682_a(18, (byte)0);
   }

   private void updateWatchedDirection() {
      this.field_70180_af.func_75692_b(10, this.field_146063_b);
      this.field_70180_af.func_75692_b(11, this.field_146064_c);
      this.field_70180_af.func_75692_b(12, this.field_146062_d);
      this.field_70180_af.func_75692_b(13, (byte)this.field_82332_a);
   }

   public void getWatchedDirection() {
      this.field_146063_b = this.field_70180_af.func_75679_c(10);
      this.field_146064_c = this.field_70180_af.func_75679_c(11);
      this.field_146062_d = this.field_70180_af.func_75679_c(12);
      this.field_82332_a = this.field_70180_af.func_75683_a(13);
   }

   private int getBannerTypeID() {
      return this.field_70180_af.func_75683_a(18);
   }

   private void setBannerTypeID(int i) {
      this.field_70180_af.func_75692_b(18, (byte)i);
   }

   public void setBannerType(LOTRItemBanner.BannerType type) {
      this.setBannerTypeID(type.bannerID);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.forID(this.getBannerTypeID());
   }

   public void setProtectData(NBTTagCompound nbt) {
      this.protectData = nbt;
   }

   public void func_82328_a(int dir) {
      if (dir < 0 || dir >= Direction.field_82373_c.length) {
         dir = 0;
      }

      this.field_82332_a = dir;
      this.field_70126_B = this.field_70177_z = (float)Direction.field_71580_e[dir] * 90.0F;
      float width = 1.0F;
      float thickness = 0.0625F;
      float edge = 0.01F;
      float xSize;
      float zSize;
      float xEdge;
      float zEdge;
      if (dir != 0 && dir != 2) {
         xSize = thickness;
         zSize = width;
         xEdge = edge;
         zEdge = thickness + edge;
      } else {
         xSize = width;
         zSize = thickness;
         xEdge = thickness + edge;
         zEdge = edge;
      }

      float f = (float)this.field_146063_b + 0.5F;
      float f1 = (float)this.field_146064_c + 0.5F;
      float f2 = (float)this.field_146062_d + 0.5F;
      float f3 = 0.5F + thickness / 2.0F;
      f += (float)Direction.field_71583_a[dir] * f3;
      f2 += (float)Direction.field_71581_b[dir] * f3;
      this.func_70107_b((double)f, (double)f1, (double)f2);
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.field_70121_D.func_72324_b((double)(f - xSize / 2.0F), (double)(f1 - 1.5F), (double)(f2 - zSize / 2.0F), (double)(f + xSize / 2.0F), (double)(f1 + 0.5F), (double)(f2 + zSize / 2.0F));
      this.field_70121_D.func_72328_c(this.field_70121_D.func_72331_e((double)xEdge, (double)edge, (double)zEdge));
      if (!this.field_70170_p.field_72995_K) {
         this.updateWatchedDirection();
      }

   }

   @SideOnly(Side.CLIENT)
   public int func_70070_b(float f) {
      if (!this.updatedClientBB) {
         this.getWatchedDirection();
         this.func_82328_a(this.field_82332_a);
         this.updatedClientBB = true;
      }

      int i = MathHelper.func_76128_c(this.field_70165_t);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      if (this.field_70170_p.func_72899_e(i, 0, k)) {
         int j = MathHelper.func_76128_c(this.field_70163_u);
         return this.field_70170_p.func_72802_i(i, j, k, 0);
      } else {
         return 0;
      }
   }

   public void func_70071_h_() {
      if (this.field_70170_p.field_72995_K && !this.updatedClientBB) {
         this.getWatchedDirection();
         this.func_82328_a(this.field_82332_a);
         this.updatedClientBB = true;
      }

      super.func_70071_h_();
   }

   public boolean func_70518_d() {
      if (!this.field_70170_p.func_72945_a(this, this.field_70121_D).isEmpty()) {
         return false;
      } else {
         int i = this.field_146063_b;
         int j = this.field_146064_c;
         int k = this.field_146062_d;
         Block block = this.field_70170_p.func_147439_a(i, j, k);
         if (!block.func_149688_o().func_76220_a()) {
            return false;
         } else {
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D);
            Iterator var6 = list.iterator();

            Object obj;
            do {
               if (!var6.hasNext()) {
                  return true;
               }

               obj = var6.next();
            } while(!(obj instanceof EntityHanging));

            return false;
         }
      }
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("BannerType", (byte)this.getBannerTypeID());
      if (this.protectData != null) {
         nbt.func_74782_a("ProtectData", this.protectData);
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setBannerTypeID(nbt.func_74771_c("BannerType"));
      if (nbt.func_74764_b("ProtectData")) {
         this.protectData = nbt.func_74775_l("ProtectData");
      }

   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76346_g();
         if (LOTRBannerProtection.isProtected(this.field_70170_p, this, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
            return false;
         }
      }

      return super.func_70097_a(damagesource, f);
   }

   public void func_110128_b(Entity entity) {
      this.field_70170_p.func_72956_a(this, Blocks.field_150344_f.field_149762_H.func_150495_a(), (Blocks.field_150344_f.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150344_f.field_149762_H.func_150494_d() * 0.8F);
      boolean flag = true;
      if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
         flag = false;
      }

      if (flag) {
         this.func_70099_a(this.getBannerItem(), 0.0F);
      }

   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return this.getBannerItem();
   }

   private ItemStack getBannerItem() {
      ItemStack item = new ItemStack(LOTRMod.banner, 1, this.getBannerType().bannerID);
      if (this.protectData != null) {
         LOTRItemBanner.setProtectionData(item, this.protectData);
      }

      return item;
   }

   public int func_82329_d() {
      return 16;
   }

   public int func_82330_g() {
      return 32;
   }
}
