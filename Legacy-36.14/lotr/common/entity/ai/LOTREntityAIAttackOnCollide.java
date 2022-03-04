package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.projectile.LOTREntitySpear;
import lotr.common.item.LOTRItemSpear;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityAIAttackOnCollide extends EntityAIBase {
   protected World worldObj;
   protected EntityCreature theOwner;
   protected EntityLivingBase attackTarget;
   protected int attackTick = 0;
   protected double moveSpeed;
   protected boolean sightNotRequired;
   protected PathEntity entityPathEntity;
   protected int pathCheckTimer;
   protected boolean avoidsWater;

   public LOTREntityAIAttackOnCollide(EntityCreature entity, double speed, boolean flag) {
      this.theOwner = entity;
      this.worldObj = entity.field_70170_p;
      this.moveSpeed = speed;
      this.sightNotRequired = flag;
      this.avoidsWater = entity.func_70661_as().func_75486_a();
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.theOwner instanceof LOTREntityNPC && ((LOTREntityNPC)this.theOwner).isPassive) {
         return false;
      } else {
         EntityLivingBase entity = this.theOwner.func_70638_az();
         if (entity == null) {
            return false;
         } else {
            this.attackTarget = entity;
            this.theOwner.func_70661_as().func_75491_a(false);
            this.entityPathEntity = this.getPathEntity();
            if (this.entityPathEntity != null) {
               return true;
            } else {
               this.theOwner.func_70661_as().func_75491_a(this.avoidsWater);
               return false;
            }
         }
      }
   }

   public boolean func_75253_b() {
      if (!this.theOwner.func_70089_S()) {
         return false;
      } else {
         this.attackTarget = this.theOwner.func_70638_az();
         if (this.attackTarget != null && this.attackTarget.func_70089_S()) {
            if (this.sightNotRequired) {
               return this.theOwner.func_110176_b(MathHelper.func_76128_c(this.attackTarget.field_70165_t), MathHelper.func_76128_c(this.attackTarget.field_70163_u), MathHelper.func_76128_c(this.attackTarget.field_70161_v));
            } else {
               return !this.theOwner.func_70661_as().func_75500_f();
            }
         } else {
            return false;
         }
      }
   }

   public void func_75249_e() {
      this.theOwner.func_70661_as().func_75484_a(this.entityPathEntity, this.moveSpeed);
      this.pathCheckTimer = 0;
   }

   public void func_75251_c() {
      this.attackTarget = null;
      this.theOwner.func_70661_as().func_75499_g();
      this.theOwner.func_70661_as().func_75491_a(this.avoidsWater);
   }

   public void func_75246_d() {
      this.updateLookAndPathing();
      if (this.attackTick > 0) {
         --this.attackTick;
      }

      ItemStack weapon = this.theOwner.func_70694_bm();
      if (weapon != null && weapon.func_77973_b() instanceof LOTRItemSpear && this.attackTick <= 0 && this.theOwner instanceof LOTREntityNPC) {
         LOTREntityNPC theNPC = (LOTREntityNPC)this.theOwner;
         ItemStack spearBackup = theNPC.npcItemsInv.getSpearBackup();
         if (spearBackup != null) {
            LOTRItemSpear spearItem = (LOTRItemSpear)weapon.func_77973_b();
            double d = (double)this.theOwner.func_70032_d(this.attackTarget);
            double range = (double)this.theOwner.func_70661_as().func_111269_d();
            if (d > 5.0D && d < range * 0.75D) {
               LOTREntitySpear spear = new LOTREntitySpear(this.worldObj, this.theOwner, this.attackTarget, weapon.func_77946_l(), 0.75F + (float)d * 0.025F, 0.5F);
               this.worldObj.func_72956_a(this.theOwner, "random.bow", 1.0F, 1.0F / (this.worldObj.field_73012_v.nextFloat() * 0.4F + 1.2F) + 0.25F);
               this.worldObj.func_72838_d(spear);
               this.attackTick = 30 + this.theOwner.func_70681_au().nextInt(20);
               if (ItemStack.func_77989_b(theNPC.npcItemsInv.getIdleItem(), theNPC.npcItemsInv.getMeleeWeapon())) {
                  theNPC.npcItemsInv.setIdleItem(spearBackup);
               }

               theNPC.npcItemsInv.setMeleeWeapon(spearBackup);
               theNPC.npcItemsInv.setSpearBackup((ItemStack)null);
               return;
            }
         }
      }

      float weaponReach = 1.0F;
      if (this.theOwner.field_70154_o != null) {
         weaponReach = LOTREntityNPC.MOUNT_RANGE_BONUS;
      }

      weaponReach *= LOTRWeaponStats.getMeleeReachFactor(this.theOwner.func_70694_bm());
      float meleeRange = (float)this.theOwner.field_70121_D.func_72320_b() + weaponReach;
      if (this.theOwner.func_70068_e(this.attackTarget) <= (double)(meleeRange * meleeRange) && this.attackTick <= 0) {
         this.attackTick = LOTRWeaponStats.getAttackTimeMob(weapon);
         this.theOwner.func_70652_k(this.attackTarget);
         this.theOwner.func_71038_i();
      }

   }

   protected void updateLookAndPathing() {
      this.theOwner.func_70671_ap().func_75651_a(this.attackTarget, 30.0F, 30.0F);
      if (this.theOwner.field_70153_n != null && this.theOwner.field_70153_n instanceof EntityLiving) {
         ((EntityLiving)this.theOwner.field_70153_n).field_70177_z = this.theOwner.field_70177_z;
         ((EntityLiving)this.theOwner.field_70153_n).field_70759_as = this.theOwner.field_70759_as;
      }

      if ((this.sightNotRequired || this.theOwner.func_70635_at().func_75522_a(this.attackTarget)) && --this.pathCheckTimer <= 0) {
         this.pathCheckTimer = 10 + this.theOwner.func_70681_au().nextInt(10);
         PathEntity path = this.getPathEntity();
         if (path != null) {
            this.theOwner.func_70661_as().func_75484_a(path, this.moveSpeed);
         }
      }

   }

   private PathEntity getPathEntity() {
      return this.theOwner.field_70154_o != null ? this.worldObj.func_72865_a(this.theOwner, this.attackTarget, this.theOwner.func_70661_as().func_111269_d(), true, this.theOwner.func_70661_as().func_75507_c(), this.theOwner.func_70661_as().func_75486_a(), false) : this.theOwner.func_70661_as().func_75494_a(this.attackTarget);
   }
}
