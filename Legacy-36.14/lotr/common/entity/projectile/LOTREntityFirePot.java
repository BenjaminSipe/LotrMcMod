package lotr.common.entity.projectile;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockPlate;
import lotr.common.block.LOTRBlockRhunFireJar;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTREntityFirePot extends EntityThrowable {
   public LOTREntityFirePot(World world) {
      super(world);
   }

   public LOTREntityFirePot(World world, EntityLivingBase entityliving) {
      super(world, entityliving);
   }

   public LOTREntityFirePot(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   protected void func_70184_a(MovingObjectPosition m) {
      double d;
      if (!this.field_70170_p.field_72995_K) {
         EntityLivingBase thrower = this.func_85052_h();
         Entity hitEntity = m.field_72308_g;
         d = 3.0D;
         List entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, this.field_70121_D.func_72314_b(d, d, d));
         if (hitEntity instanceof EntityLivingBase && !entities.contains(hitEntity)) {
            entities.add(hitEntity);
         }

         Iterator var7 = entities.iterator();

         while(var7.hasNext()) {
            Object obj = var7.next();
            EntityLivingBase entity = (EntityLivingBase)obj;
            float damage = 1.0F;
            if (entity == hitEntity) {
               damage = 3.0F;
            }

            if (entity == hitEntity && thrower instanceof EntityPlayer && hitEntity instanceof LOTREntityBird && !((LOTREntityBird)hitEntity).isBirdStill()) {
               LOTRLevelData.getData((EntityPlayer)thrower).addAchievement(LOTRAchievement.hitBirdFirePot);
            }

            if (entity.func_70097_a(DamageSource.func_76356_a(this, thrower), damage)) {
               int fire = 2 + this.field_70146_Z.nextInt(3);
               if (entity == hitEntity) {
                  fire += 2 + this.field_70146_Z.nextInt(3);
               }

               entity.func_70015_d(fire);
            }
         }

         if (m.field_72313_a == MovingObjectType.BLOCK) {
            Block block = this.field_70170_p.func_147439_a(m.field_72311_b, m.field_72312_c, m.field_72309_d);
            if (block instanceof LOTRBlockRhunFireJar) {
               ((LOTRBlockRhunFireJar)block).explode(this.field_70170_p, m.field_72311_b, m.field_72312_c, m.field_72309_d);
            }
         }

         this.field_70170_p.func_72956_a(this, LOTRBlockPlate.soundTypePlate.func_150495_a(), 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         this.func_70106_y();
      }

      int l;
      for(l = 0; l < 8; ++l) {
         double d = this.field_70165_t + (double)MathHelper.func_151240_a(this.field_70146_Z, -0.25F, 0.25F);
         double d1 = this.field_70163_u + (double)MathHelper.func_151240_a(this.field_70146_Z, -0.25F, 0.25F);
         double d2 = this.field_70161_v + (double)MathHelper.func_151240_a(this.field_70146_Z, -0.25F, 0.25F);
         this.field_70170_p.func_72869_a("blockcrack_" + Block.func_149682_b(LOTRMod.rhunFireJar) + "_0", d, d1, d2, 0.0D, 0.0D, 0.0D);
      }

      for(l = 0; l < 16; ++l) {
         String s = this.field_70146_Z.nextBoolean() ? "flame" : "smoke";
         d = this.field_70165_t;
         double d1 = this.field_70163_u;
         double d2 = this.field_70161_v;
         double d3 = (double)MathHelper.func_151240_a(this.field_70146_Z, -0.1F, 0.1F);
         double d4 = (double)MathHelper.func_151240_a(this.field_70146_Z, 0.2F, 0.3F);
         double d5 = (double)MathHelper.func_151240_a(this.field_70146_Z, -0.1F, 0.1F);
         this.field_70170_p.func_72869_a(s, d, d1, d2, d3, d4, d5);
      }

   }

   protected float func_70182_d() {
      return 1.2F;
   }

   protected float func_70185_h() {
      return 0.04F;
   }
}
