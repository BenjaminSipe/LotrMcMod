package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityTrollSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTREntitySnowTroll extends LOTREntityTroll {
   private EntityAIBase rangedAttackAI = this.getTrollRangedAttackAI();
   private EntityAIBase meleeAttackAI;

   public LOTREntitySnowTroll(World world) {
      super(world);
      this.isImmuneToFrost = true;
   }

   public float getTrollScale() {
      return 0.8F;
   }

   public EntityAIBase getTrollAttackAI() {
      return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   protected EntityAIBase getTrollRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.2D, 20, 30, 24.0F);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(21, (byte)0);
   }

   public boolean isThrowingSnow() {
      return this.field_70180_af.func_75683_a(21) == 1;
   }

   public void setThrowingSnow(boolean flag) {
      this.field_70180_af.func_75692_b(21, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
   }

   protected boolean hasTrollName() {
      return false;
   }

   protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
      return false;
   }

   public double getMeleeRange() {
      return 12.0D;
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.setThrowingSnow(false);
      }

      if (mode == LOTREntityNPC.AttackMode.MELEE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(3, this.meleeAttackAI);
         this.setThrowingSnow(false);
      }

      if (mode == LOTREntityNPC.AttackMode.RANGED) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(3, this.rangedAttackAI);
         this.setThrowingSnow(true);
      }

   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         if (entity instanceof EntityLivingBase) {
            int difficulty = this.field_70170_p.field_73013_u.func_151525_a();
            int duration = difficulty * (difficulty + 5) / 2;
            if (duration > 0) {
               ((EntityLivingBase)entity).func_70690_d(new PotionEffect(Potion.field_76421_d.field_76415_H, duration * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void func_82196_d(EntityLivingBase target, float f) {
      EntityArrow template = new EntityArrow(this.field_70170_p, this, target, f * 1.6F, 0.5F);
      EntitySnowball snowball = new LOTREntityTrollSnowball(this.field_70170_p, this);
      snowball.func_70012_b(template.field_70165_t, template.field_70163_u, template.field_70161_v, template.field_70177_z, template.field_70125_A);
      snowball.field_70159_w = template.field_70159_w;
      snowball.field_70181_x = template.field_70181_x;
      snowball.field_70179_y = template.field_70179_y;
      this.field_70170_p.func_72838_d(snowball);
      this.func_85030_a("random.bow", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.func_71038_i();
   }

   public void onTrollDeathBySun() {
      this.field_70170_p.func_72956_a(this, "lotr:troll.transform", this.func_70599_aP(), this.func_70647_i());
      this.field_70170_p.func_72960_a(this, (byte)15);
      this.func_70106_y();
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 15) {
         super.func_70103_a(b);

         for(int l = 0; l < 64; ++l) {
            this.field_70170_p.func_72869_a("snowballpoof", this.field_70165_t + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int furs = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      int snows;
      for(snows = 0; snows < furs; ++snows) {
         this.func_145779_a(LOTRMod.fur, 1);
      }

      snows = 2 + this.field_70146_Z.nextInt(4) + this.field_70146_Z.nextInt(i * 2 + 1);

      for(int l = 0; l < snows; ++l) {
         this.func_145779_a(Items.field_151126_ay, 1);
      }

   }

   public void dropTrollItems(boolean flag, int i) {
      if (this.field_70146_Z.nextBoolean()) {
         super.dropTrollItems(flag, i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killSnowTroll;
   }

   public float getAlignmentBonus() {
      return 3.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return null;
   }
}
