package lotr.common.entity.animal;

import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.biome.LOTRBiomeGenFarHaradSwamp;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityCrocodile extends EntityMob {
   private EntityAIBase targetAI;
   private boolean prevCanTarget = true;

   public LOTREntityCrocodile(World world) {
      super(world);
      this.func_70105_a(2.1F, 0.7F);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 1.5D, false));
      this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F));
      this.field_70714_bg.func_75776_a(4, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(0, new EntityAIHurtByTarget(this, false));
      this.field_70715_bh.func_75776_a(1, this.targetAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, LOTREntityNPC.class, 400, true));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(20, 0);
   }

   public int getSnapTime() {
      return this.field_70180_af.func_75679_c(20);
   }

   public void setSnapTime(int i) {
      this.field_70180_af.func_75692_b(20, i);
   }

   public boolean func_70650_aV() {
      return true;
   }

   public boolean func_70648_aU() {
      return true;
   }

   protected String func_70639_aQ() {
      return "lotr:crocodile.say";
   }

   protected String func_70673_aS() {
      return "lotr:crocodile.death";
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.func_70090_H()) {
         this.field_70181_x += 0.02D;
      }

      if (!this.field_70170_p.field_72995_K && this.func_70638_az() != null) {
         EntityLivingBase entity = this.func_70638_az();
         if (!entity.func_70089_S() || entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
            this.func_70624_b((EntityLivingBase)null);
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         boolean canTarget = this.func_70013_c(1.0F) < 0.5F;
         if (canTarget != this.prevCanTarget) {
            if (canTarget) {
               this.field_70715_bh.func_75776_a(1, this.targetAI);
            } else {
               this.field_70715_bh.func_85156_a(this.targetAI);
            }
         }

         this.prevCanTarget = canTarget;
      }

      if (!this.field_70170_p.field_72995_K) {
         int i = this.getSnapTime();
         if (i > 0) {
            this.setSnapTime(i - 1);
         }
      }

      if (this.func_70638_az() == null && this.field_70170_p.field_73012_v.nextInt(1000) == 0) {
         List list = this.field_70170_p.func_72872_a(EntityAnimal.class, this.field_70121_D.func_72314_b(12.0D, 6.0D, 12.0D));
         if (!list.isEmpty()) {
            EntityAnimal entityanimal = (EntityAnimal)list.get(this.field_70146_Z.nextInt(list.size()));
            if (entityanimal.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111264_e) == null) {
               this.func_70624_b(entityanimal);
            }
         }
      }

   }

   protected Item func_146068_u() {
      return Items.field_151078_bh;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int count = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int j = 0; j < count; ++j) {
         int drop = this.field_70146_Z.nextInt(5);
         switch(drop) {
         case 0:
            this.func_145779_a(Items.field_151103_aS, 1);
            break;
         case 1:
            this.func_145779_a(Items.field_151115_aP, 1);
            break;
         case 2:
            this.func_145779_a(Items.field_151116_aA, 1);
            break;
         case 3:
            this.func_145779_a(LOTRMod.zebraRaw, 1);
            break;
         case 4:
            this.func_145779_a(LOTRMod.gemsbokHide, 1);
         }
      }

   }

   public boolean func_70652_k(Entity entity) {
      boolean flag = super.func_70652_k(entity);
      if (flag) {
         if (!this.field_70170_p.field_72995_K) {
            this.setSnapTime(20);
         }

         this.field_70170_p.func_72956_a(this, "lotr:crocodile.snap", this.func_70599_aP(), this.func_70647_i());
      }

      return flag;
   }

   public boolean func_70601_bi() {
      List nearbyCrocodiles = this.field_70170_p.func_72872_a(this.getClass(), this.field_70121_D.func_72314_b(24.0D, 12.0D, 24.0D));
      if (nearbyCrocodiles.size() > 3) {
         return false;
      } else {
         if (this.field_70170_p.func_72855_b(this.field_70121_D) && this.func_70814_o() && this.field_70170_p.func_72945_a(this, this.field_70121_D).size() == 0) {
            for(int i = -8; i <= 8; ++i) {
               for(int j = -8; j <= 8; ++j) {
                  for(int k = -8; k <= 8; ++k) {
                     int i1 = MathHelper.func_76128_c(this.field_70165_t) + i;
                     int j1 = MathHelper.func_76128_c(this.field_70163_u) + j;
                     int k1 = MathHelper.func_76128_c(this.field_70161_v) + k;
                     if (this.field_70170_p.func_72899_e(i1, j1, k1)) {
                        Block block = this.field_70170_p.func_147439_a(i1, j1, k1);
                        if (block.func_149688_o() == Material.field_151586_h) {
                           if (this.field_70163_u > 60.0D) {
                              return true;
                           }

                           if (this.field_70146_Z.nextInt(50) == 0) {
                              return true;
                           }
                        }
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   protected boolean func_70814_o() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      return this.field_70170_p.func_72807_a(i, k) instanceof LOTRBiomeGenFarHaradSwamp ? true : super.func_70814_o();
   }

   public void func_70612_e(float f, float f1) {
      if (!this.field_70170_p.field_72995_K && this.func_70090_H() && this.func_70638_az() != null) {
         this.func_70060_a(f, f1, 0.1F);
      }

      super.func_70612_e(f, f1);
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
