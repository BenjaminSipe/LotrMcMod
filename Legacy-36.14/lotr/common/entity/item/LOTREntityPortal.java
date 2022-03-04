package lotr.common.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRTeleporter;
import net.minecraft.block.Block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class LOTREntityPortal extends Entity {
   public static int MAX_SCALE = 120;
   private float prevPortalRotation;
   private float portalRotation;

   public LOTREntityPortal(World world) {
      super(world);
      this.func_70105_a(3.0F, 1.5F);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(10, Short.valueOf((short)0));
   }

   public int getScale() {
      return this.field_70180_af.func_75693_b(10);
   }

   public void setScale(int i) {
      this.field_70180_af.func_75692_b(10, (short)i);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      nbt.func_74768_a("Scale", this.getScale());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      this.setScale(nbt.func_74762_e("Scale"));
   }

   public float getPortalRotation(float f) {
      return this.prevPortalRotation + (this.portalRotation - this.prevPortalRotation) * f;
   }

   public void func_70071_h_() {
      this.prevPortalRotation = this.portalRotation;

      for(this.portalRotation += 4.0F; this.portalRotation - this.prevPortalRotation < -180.0F; this.prevPortalRotation -= 360.0F) {
      }

      while(this.portalRotation - this.prevPortalRotation >= 180.0F) {
         this.prevPortalRotation += 360.0F;
      }

      if (!this.field_70170_p.field_72995_K && this.field_71093_bK != 0 && this.field_71093_bK != LOTRDimension.MIDDLE_EARTH.dimensionID) {
         this.func_70106_y();
      }

      if (!this.field_70170_p.field_72995_K && this.getScale() < MAX_SCALE) {
         this.setScale(this.getScale() + 1);
      }

      if (this.getScale() >= MAX_SCALE) {
         List players = this.field_70170_p.func_72872_a(EntityPlayer.class, this.field_70121_D.func_72314_b(8.0D, 8.0D, 8.0D));

         for(int i = 0; i < players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)players.get(i);
            if (this.field_70121_D.func_72326_a(entityplayer.field_70121_D) && entityplayer.field_70154_o == null && entityplayer.field_70153_n == null) {
               LOTRMod.proxy.setInPortal(entityplayer);
            }
         }

         List entities = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(8.0D, 8.0D, 8.0D));

         int i;
         for(i = 0; i < entities.size(); ++i) {
            Entity entity = (Entity)entities.get(i);
            if (!(entity instanceof EntityPlayer) && this.field_70121_D.func_72326_a(entity.field_70121_D) && entity.field_70154_o == null && entity.field_70153_n == null && entity.field_71088_bW == 0) {
               this.transferEntity(entity);
            }
         }

         if (this.field_70146_Z.nextInt(50) == 0) {
            this.field_70170_p.func_72956_a(this, "portal.portal", 0.5F, this.field_70146_Z.nextFloat() * 0.4F + 0.8F);
         }

         for(i = 0; i < 2; ++i) {
            double d = this.field_70165_t - 3.0D + (double)(this.field_70146_Z.nextFloat() * 6.0F);
            double d1 = this.field_70163_u - 0.75D + (double)(this.field_70146_Z.nextFloat() * 3.0F);
            double d2 = this.field_70161_v - 3.0D + (double)(this.field_70146_Z.nextFloat() * 6.0F);
            double d3 = (this.field_70165_t - d) / 8.0D;
            double d4 = (this.field_70163_u + 1.5D - d1) / 8.0D;
            double d5 = (this.field_70161_v - d2) / 8.0D;
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

            this.field_70170_p.func_72869_a("flame", d, d1, d2, d8, d9, d10);
         }
      }

   }

   protected boolean func_70041_e_() {
      return false;
   }

   public boolean func_85032_ar() {
      return true;
   }

   public boolean func_70104_M() {
      return false;
   }

   public boolean func_145773_az() {
      return true;
   }

   private void transferEntity(Entity entity) {
      if (!this.field_70170_p.field_72995_K) {
         int dimension = 0;
         if (entity.field_71093_bK == 0) {
            dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
         } else if (entity.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            dimension = 0;
         }

         LOTRMod.transferEntityToDimension(entity, dimension, new LOTRTeleporter(DimensionManager.getWorld(dimension), true));
      }

   }

   public boolean func_70067_L() {
      return true;
   }

   public void func_70108_f(Entity entity) {
   }

   public boolean func_85031_j(Entity entity) {
      return entity instanceof EntityPlayer ? this.func_70097_a(DamageSource.func_76365_a((EntityPlayer)entity), 0.0F) : false;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      Entity entity = damagesource.func_76346_g();
      if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
         if (!this.field_70170_p.field_72995_K) {
            SoundType sound = Blocks.field_150359_w.field_149762_H;
            this.field_70170_p.func_72956_a(this, sound.func_150495_a(), (sound.func_150497_c() + 1.0F) / 2.0F, sound.func_150494_d() * 0.8F);
            this.field_70170_p.func_72960_a(this, (byte)16);
            this.func_70106_y();
         }

         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 16) {
         for(int l = 0; l < 16; ++l) {
            this.field_70170_p.func_72869_a("iconcrack_" + Item.func_150891_b(LOTRMod.goldRing), this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.goldRing);
   }
}
