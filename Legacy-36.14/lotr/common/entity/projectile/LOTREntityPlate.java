package lotr.common.entity.projectile;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTREntityPlate extends EntityThrowable implements IEntityAdditionalSpawnData {
   private int plateSpin;
   private Block plateBlock;

   public LOTREntityPlate(World world) {
      super(world);
      this.func_70105_a(0.5F, 0.5F);
   }

   public LOTREntityPlate(World world, Block block, EntityLivingBase entityliving) {
      super(world, entityliving);
      this.func_70105_a(0.5F, 0.5F);
      this.plateBlock = block;
   }

   public LOTREntityPlate(World world, Block block, double d, double d1, double d2) {
      super(world, d, d1, d2);
      this.func_70105_a(0.5F, 0.5F);
      this.plateBlock = block;
   }

   public void writeSpawnData(ByteBuf data) {
      data.writeShort(Block.func_149682_b(this.plateBlock));
   }

   public void readSpawnData(ByteBuf data) {
      Block block = Block.func_149729_e(data.readShort());
      if (block == null) {
         block = LOTRMod.plateBlock;
      }

      this.plateBlock = block;
   }

   public Block getPlateBlock() {
      return this.plateBlock;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      ++this.plateSpin;
      this.field_70177_z = (float)(this.plateSpin % 12) / 12.0F * 360.0F;
      float speed = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      if (speed > 0.1F && this.field_70181_x < 0.0D && this.func_70090_H()) {
         float factor = MathHelper.func_151240_a(this.field_70146_Z, 0.4F, 0.8F);
         this.field_70159_w *= (double)factor;
         this.field_70179_y *= (double)factor;
         this.field_70181_x += (double)factor;
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      if (this.plateBlock != null) {
         nbt.func_74777_a("PlateBlockID", (short)Block.func_149682_b(this.plateBlock));
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("PlateBlockID")) {
         this.plateBlock = Block.func_149729_e(nbt.func_74765_d("PlateBlockID"));
      }

      if (this.plateBlock == null) {
         this.plateBlock = LOTRMod.plateBlock;
      }

   }

   protected void func_70184_a(MovingObjectPosition m) {
      int i;
      if (m.field_72313_a == MovingObjectType.ENTITY) {
         if (m.field_72308_g == this.func_85052_h()) {
            return;
         }

         m.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), 1.0F);
      } else if (m.field_72313_a == MovingObjectType.BLOCK && !this.field_70170_p.field_72995_K) {
         i = m.field_72311_b;
         int j = m.field_72312_c;
         int k = m.field_72309_d;
         if (this.breakGlass(i, j, k)) {
            int range = 2;

            for(int i1 = i - range; i1 <= i + range; ++i1) {
               for(int j1 = j - range; j1 <= j + range; ++j1) {
                  for(int k1 = k - range; k1 <= k + range; ++k1) {
                     if (this.field_70146_Z.nextInt(4) != 0) {
                        this.breakGlass(i1, j1, k1);
                     }
                  }
               }
            }

            return;
         }
      }

      for(i = 0; i < 8; ++i) {
         double d = this.field_70165_t + (double)MathHelper.func_151240_a(this.field_70146_Z, -0.25F, 0.25F);
         double d1 = this.field_70163_u + (double)MathHelper.func_151240_a(this.field_70146_Z, -0.25F, 0.25F);
         double d2 = this.field_70161_v + (double)MathHelper.func_151240_a(this.field_70146_Z, -0.25F, 0.25F);
         this.field_70170_p.func_72869_a("blockcrack_" + Block.func_149682_b(this.plateBlock) + "_0", d, d1, d2, 0.0D, 0.0D, 0.0D);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72956_a(this, this.plateBlock.field_149762_H.func_150495_a(), 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         this.func_70106_y();
      }

   }

   private boolean breakGlass(int i, int j, int k) {
      Block block = this.field_70170_p.func_147439_a(i, j, k);
      if (block.func_149688_o() == Material.field_151592_s) {
         boolean bannerProtection = LOTRBannerProtection.isProtected(this.field_70170_p, i, j, k, LOTRBannerProtection.forThrown(this), true);
         if (!bannerProtection) {
            this.field_70170_p.func_72926_e(2001, i, j, k, Block.func_149682_b(block) + (this.field_70170_p.func_72805_g(i, j, k) << 12));
            this.field_70170_p.func_147468_f(i, j, k);
            return true;
         }
      }

      return false;
   }

   protected float func_70182_d() {
      return 1.5F;
   }

   protected float func_70185_h() {
      return 0.02F;
   }
}
