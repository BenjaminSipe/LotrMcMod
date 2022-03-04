package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityTermite extends EntityMob {
   private int fuseTime;
   private static final int maxFuseTime = 20;
   public static final float explosionSize = 2.0F;

   public LOTREntityTermite(World world) {
      super(world);
      this.func_70105_a(0.4F, 0.4F);
      this.field_70155_l = 2.0D;
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 1.0D, true));
      this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, 1.0D));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, LOTREntityNPC.class, 0, true));
      this.field_70728_aV = 2;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
   }

   public boolean func_70650_aV() {
      return true;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         EntityLivingBase target = this.func_70638_az();
         if (target == null) {
            --this.fuseTime;
         } else {
            float dist = this.func_70032_d(target);
            if (dist < 3.0F) {
               if (this.fuseTime == 0) {
                  this.field_70170_p.func_72956_a(this, "creeper.primed", 1.0F, 0.5F);
               }

               ++this.fuseTime;
               if (this.fuseTime >= 20) {
                  this.explode();
               }
            } else {
               --this.fuseTime;
            }
         }

         this.fuseTime = Math.min(Math.max(this.fuseTime, 0), 20);
      }

   }

   public boolean func_70652_k(Entity entity) {
      return true;
   }

   private void explode() {
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72876_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0F, LOTRMod.canGrief(this.field_70170_p));
         this.func_70106_y();
      }

   }

   protected String func_70639_aQ() {
      return "mob.silverfish.say";
   }

   protected String func_70621_aR() {
      return "mob.silverfish.hit";
   }

   protected String func_70673_aS() {
      return "mob.silverfish.kill";
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer) {
         this.func_145779_a(LOTRMod.termite, 1);
         this.func_70106_y();
      }

   }

   protected boolean func_70692_ba() {
      return false;
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
