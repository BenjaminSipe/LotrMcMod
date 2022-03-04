package lotr.common.entity.npc;

import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetOrc;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityOlogHai extends LOTREntityTroll {
   public LOTREntityOlogHai(World world) {
      super(world);
      this.field_70714_bg.field_75782_a.clear();
      this.field_70715_bh.field_75782_a.clear();
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAttackOnCollide(this, 2.0D, false));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F, 0.01F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
      this.addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
      this.trollImmuneToSun = true;
   }

   public float getTrollScale() {
      return 1.25F;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(80.0D);
      this.func_110148_a(npcAttackDamage).func_111128_a(7.0D);
   }

   public int func_70658_aO() {
      return 15;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.MORDOR;
   }

   protected boolean hasTrollName() {
      return false;
   }

   protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
      return false;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
   }

   public boolean func_70652_k(Entity entity) {
      if (!super.func_70652_k(entity)) {
         return false;
      } else {
         float attackDamage = (float)this.func_110148_a(LOTREntityNPC.npcAttackDamage).func_111126_e();
         float knockbackModifier = 0.25F * attackDamage;
         entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * knockbackModifier * 0.5F), 0.0D, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * knockbackModifier * 0.5F));
         this.field_70170_p.func_72956_a(entity, "lotr:troll.ologHai_hammer", 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         if (!this.field_70170_p.field_72995_K) {
            List entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, entity.field_70121_D.func_72314_b(4.0D, 4.0D, 4.0D));
            if (!entities.isEmpty()) {
               for(int i = 0; i < entities.size(); ++i) {
                  EntityLivingBase hitEntity = (EntityLivingBase)entities.get(i);
                  if (hitEntity != this && hitEntity != entity && LOTRMod.canNPCAttackEntity(this, hitEntity, false)) {
                     float strength = 4.0F - entity.func_70032_d(hitEntity);
                     ++strength;
                     if (strength > 4.0F) {
                        strength = 4.0F;
                     }

                     if (hitEntity.func_70097_a(DamageSource.func_76358_a(this), strength / 4.0F * attackDamage)) {
                        float knockback = strength * 0.25F;
                        if (knockback < 0.75F) {
                           knockback = 0.75F;
                        }

                        hitEntity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * knockback * 0.5F), 0.2D + 0.12D * (double)knockback, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * knockback * 0.5F));
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killOlogHai;
   }

   public float getAlignmentBonus() {
      return 4.0F;
   }

   public void dropTrollItems(boolean flag, int i) {
      if (flag) {
         int rareDropChance = 8 - i;
         if (rareDropChance < 1) {
            rareDropChance = 1;
         }

         if (this.field_70146_Z.nextInt(rareDropChance) == 0) {
            int drops = 1 + this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

            for(int j = 0; j < drops; ++j) {
               this.func_145779_a(LOTRMod.orcSteel, 1);
            }
         }
      }

   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 5 + this.field_70146_Z.nextInt(8);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return null;
   }
}
