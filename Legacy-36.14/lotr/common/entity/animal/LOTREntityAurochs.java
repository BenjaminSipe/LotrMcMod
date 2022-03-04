package lotr.common.entity.animal;

import java.util.List;
import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityAurochs extends EntityCow implements LOTRRandomSkinEntity {
   private EntityAIBase attackAI;
   private EntityAIBase panicAI;
   private boolean prevIsChild = true;
   protected final float aurochsWidth = 1.5F;
   protected final float aurochsHeight = 1.7F;

   public LOTREntityAurochs(World world) {
      super(world);
      this.func_70105_a(this.aurochsWidth, this.aurochsHeight);
      EntityAITaskEntry panic = LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
      this.field_70714_bg.func_75776_a(panic.field_75731_b, panic.field_75733_a);
      this.panicAI = panic.field_75733_a;
      this.attackAI = this.createAurochsAttackAI();
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
   }

   protected EntityAIBase createAurochsAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.7D, true);
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(20, (byte)0);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
   }

   public boolean isAurochsEnraged() {
      return this.field_70180_af.func_75683_a(20) == 1;
   }

   public void setAurochsEnraged(boolean flag) {
      this.field_70180_af.func_75692_b(20, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         boolean isChild = this.func_70631_g_();
         if (isChild != this.prevIsChild) {
            EntityAITaskEntry taskEntry;
            if (isChild) {
               taskEntry = LOTREntityUtils.removeAITask(this, this.attackAI.getClass());
               this.field_70714_bg.func_75776_a(taskEntry.field_75731_b, this.panicAI);
            } else {
               taskEntry = LOTREntityUtils.removeAITask(this, this.panicAI.getClass());
               this.field_70714_bg.func_75776_a(taskEntry.field_75731_b, this.attackAI);
            }
         }

         EntityLivingBase target;
         if (this.func_70638_az() != null) {
            target = this.func_70638_az();
            if (!target.func_70089_S() || target instanceof EntityPlayer && ((EntityPlayer)target).field_71075_bZ.field_75098_d) {
               this.func_70624_b((EntityLivingBase)null);
            }
         }

         if (this.field_70153_n instanceof EntityLiving) {
            target = ((EntityLiving)this.field_70153_n).func_70638_az();
            this.func_70624_b(target);
         } else if (this.field_70153_n instanceof EntityPlayer) {
            this.func_70624_b((EntityLivingBase)null);
         }

         this.setAurochsEnraged(this.func_70638_az() != null);
      }

      this.prevIsChild = this.func_70631_g_();
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      return this.isAurochsEnraged() ? false : super.func_70085_c(entityplayer);
   }

   public boolean func_70652_k(Entity entity) {
      float f = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
      boolean flag = entity.func_70097_a(DamageSource.func_76358_a(this), f);
      if (flag) {
         float kb = 0.75F;
         entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * kb * 0.5F), 0.0D, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * kb * 0.5F));
      }

      return flag;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag && this.func_70631_g_()) {
         Entity attacker = damagesource.func_76346_g();
         if (attacker instanceof EntityLivingBase) {
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(12.0D, 12.0D, 12.0D));

            for(int i = 0; i < list.size(); ++i) {
               Entity entity = (Entity)list.get(i);
               if (entity.getClass() == this.getClass()) {
                  LOTREntityAurochs aurochs = (LOTREntityAurochs)entity;
                  if (!aurochs.func_70631_g_()) {
                     aurochs.func_70624_b((EntityLivingBase)attacker);
                  }
               }
            }
         }
      }

      return flag;
   }

   protected void func_70628_a(boolean flag, int i) {
      int hides = 2 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      int meats;
      for(meats = 0; meats < hides; ++meats) {
         this.func_145779_a(Items.field_151116_aA, 1);
      }

      meats = 2 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      for(int l = 0; l < meats; ++l) {
         if (this.func_70027_ad()) {
            this.func_145779_a(Items.field_151083_be, 1);
         } else {
            this.func_145779_a(Items.field_151082_bd, 1);
         }
      }

      this.dropHornItem(flag, i);
   }

   protected void dropHornItem(boolean flag, int i) {
      this.func_145779_a(LOTRMod.horn, 1);
   }

   public EntityCow func_90011_a(EntityAgeable entity) {
      return new LOTREntityAurochs(this.field_70170_p);
   }

   protected String func_70639_aQ() {
      return "lotr:aurochs.say";
   }

   protected String func_70621_aR() {
      return "lotr:aurochs.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:aurochs.hurt";
   }

   protected float func_70599_aP() {
      return 1.0F;
   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.75F;
   }

   public int func_70627_aG() {
      return 200;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
