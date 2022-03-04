package lotr.common.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTREntityThrownRock extends EntityThrowable {
   private int rockRotation;
   private float damage;

   public LOTREntityThrownRock(World world) {
      super(world);
      this.func_70105_a(4.0F, 4.0F);
   }

   public LOTREntityThrownRock(World world, EntityLivingBase entityliving) {
      super(world, entityliving);
      this.func_70105_a(4.0F, 4.0F);
   }

   public LOTREntityThrownRock(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
      this.func_70105_a(4.0F, 4.0F);
   }

   public void setDamage(float f) {
      this.damage = f;
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, (byte)0);
   }

   public boolean getSpawnsTroll() {
      return this.field_70180_af.func_75683_a(16) == 1;
   }

   public void setSpawnsTroll(boolean flag) {
      this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70193_a) {
         ++this.rockRotation;
         if (this.rockRotation > 60) {
            this.rockRotation = 0;
         }

         for(this.field_70125_A = (float)this.rockRotation / 60.0F * 360.0F; this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F) {
         }

         while(this.field_70125_A - this.field_70127_C >= 180.0F) {
            this.field_70127_C += 360.0F;
         }
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74776_a("RockDamage", this.damage);
      nbt.func_74757_a("Troll", this.getSpawnsTroll());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setDamage(nbt.func_74760_g("RockDamage"));
      this.setSpawnsTroll(nbt.func_74767_n("Troll"));
   }

   protected void func_70184_a(MovingObjectPosition m) {
      if (!this.field_70170_p.field_72995_K) {
         boolean flag = false;
         if (m.field_72308_g != null && m.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), this.damage)) {
            flag = true;
         }

         if (m.field_72313_a == MovingObjectType.BLOCK) {
            flag = true;
         }

         if (flag) {
            if (this.getSpawnsTroll()) {
               LOTREntityTroll troll = new LOTREntityTroll(this.field_70170_p);
               if (this.field_70146_Z.nextInt(3) == 0) {
                  troll = (new LOTREntityMountainTroll(this.field_70170_p)).setCanDropTrollTotem(false);
               }

               ((LOTREntityTroll)troll).func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
               ((LOTREntityTroll)troll).func_110161_a((IEntityLivingData)null);
               this.field_70170_p.func_72838_d((Entity)troll);
            }

            this.field_70170_p.func_72960_a(this, (byte)15);
            int drops = 1 + this.field_70146_Z.nextInt(3);

            for(int l = 0; l < drops; ++l) {
               this.func_145779_a(Item.func_150898_a(Blocks.field_150347_e), 1);
            }

            this.func_85030_a("lotr:troll.rockSmash", 2.0F, (1.0F + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
            this.func_70106_y();
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 15) {
         for(int l = 0; l < 32; ++l) {
            LOTRMod.proxy.spawnParticle("largeStone", this.field_70165_t + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   protected float func_70182_d() {
      return 0.75F;
   }

   protected float func_70185_h() {
      return 0.1F;
   }
}
