package lotr.common.entity.npc;

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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTREntityMirkTroll extends LOTREntityTroll {
   public LOTREntityMirkTroll(World world) {
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
      return 1.2F;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(70.0D);
      this.func_110148_a(npcAttackDamage).func_111128_a(6.0D);
   }

   public int func_70658_aO() {
      return 12;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.DOL_GULDUR;
   }

   protected boolean hasTrollName() {
      return false;
   }

   protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
      return false;
   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         if (entity instanceof EntityLivingBase) {
            int difficulty = this.field_70170_p.field_73013_u.func_151525_a();
            int duration = difficulty * 3 - 1;
            if (duration > 0) {
               ((EntityLivingBase)entity).func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, duration * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killMirkTroll;
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
      return 4 + this.field_70146_Z.nextInt(7);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return null;
   }
}
