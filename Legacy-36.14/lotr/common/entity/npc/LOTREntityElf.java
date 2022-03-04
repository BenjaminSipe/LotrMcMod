package lotr.common.entity.npc;

import java.util.List;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.LOTRPatron;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityElf extends LOTREntityNPC {
   protected EntityAIBase rangedAttackAI = this.createElfRangedAttackAI();
   protected EntityAIBase meleeAttackAI = this.createElfMeleeAttackAI();
   private int soloTick;
   private float soloSpinSpeed;
   private float soloSpin;
   private float prevSoloSpin;
   private static final int bowTickMax = 40;
   private float bowAmount;
   private float prevBowAmount;
   private static final float bowStep = 0.2F;

   public LOTREntityElf(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIEat(this, LOTRFoods.ELF, 12000));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIDrink(this, this.getElfDrinks(), 8000));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.addTargetTasks(true);
   }

   protected LOTRFoods getElfDrinks() {
      return LOTRFoods.ELF_DRINK;
   }

   protected EntityAIBase createElfRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 40, 60, 16.0F);
   }

   protected EntityAIBase createElfMeleeAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(22, (byte)0);
      this.field_70180_af.func_75682_a(23, Short.valueOf((short)0));
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(this.field_70146_Z.nextBoolean());
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.5D);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("BoopBoopBaDoop", this.isJazz());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setJazz(nbt.func_74767_n("BoopBoopBaDoop"));
   }

   public String getEntityClassName() {
      return this.isJazz() ? "Jazz-elf" : super.getEntityClassName();
   }

   private boolean getJazzFlag(int i) {
      byte b = this.field_70180_af.func_75683_a(22);
      int pow2 = 1 << i;
      return (b & pow2) != 0;
   }

   private void setJazzFlag(int i, boolean flag) {
      byte b = this.field_70180_af.func_75683_a(22);
      int pow2 = 1 << i;
      if (flag) {
         b = (byte)(b | pow2);
      } else {
         b = (byte)(b & ~pow2);
      }

      this.field_70180_af.func_75692_b(22, b);
   }

   public boolean isJazz() {
      return this.getJazzFlag(0);
   }

   public void setJazz(boolean flag) {
      this.setJazzFlag(0, flag);
   }

   public boolean isSolo() {
      return this.getJazzFlag(1);
   }

   public void setSolo(boolean flag) {
      this.setJazzFlag(1, flag);
   }

   private int getBowingTick() {
      return this.field_70180_af.func_75693_b(23);
   }

   private void setBowingTick(int i) {
      this.field_70180_af.func_75692_b(23, (short)i);
   }

   public void func_70636_d() {
      super.func_70636_d();
      double d;
      final double d1;
      if (this.isJazz()) {
         if (!this.field_70170_p.field_72995_K) {
            if (this.soloTick > 0) {
               --this.soloTick;
               this.field_70125_A = -10.0F + (MathHelper.func_76126_a((float)this.soloTick * 0.3F) + 1.0F) / 2.0F * -30.0F;
            } else if (this.field_70146_Z.nextInt(200) == 0) {
               this.soloTick = 60 + this.field_70146_Z.nextInt(300);
            }

            this.setSolo(this.soloTick > 0);
         } else if (this.isSolo()) {
            if (this.field_70146_Z.nextInt(3) == 0) {
               d = this.field_70165_t;
               d1 = this.field_70121_D.field_72338_b + (double)this.func_70047_e();
               double d2 = this.field_70161_v;
               double d3 = MathHelper.func_82716_a(this.field_70146_Z, -0.1D, 0.1D);
               double d4 = MathHelper.func_82716_a(this.field_70146_Z, -0.1D, 0.1D);
               double d5 = MathHelper.func_82716_a(this.field_70146_Z, -0.1D, 0.1D);
               LOTRMod.proxy.spawnParticle("music", d, d1, d2, d3, d4, d5);
            }

            if (this.soloSpinSpeed == 0.0F || this.field_70146_Z.nextInt(30) == 0) {
               this.soloSpinSpeed = MathHelper.func_151240_a(this.field_70146_Z, -25.0F, 25.0F);
            }

            this.prevSoloSpin = this.soloSpin;
            this.soloSpin += this.soloSpinSpeed;
         } else {
            this.prevSoloSpin = this.soloSpin = 0.0F;
            this.soloSpinSpeed = 0.0F;
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         d = 8.0D;
         d1 = d * d;
         EntityPlayer bowingPlayer = null;
         List players = this.field_70170_p.func_82733_a(EntityPlayer.class, this.field_70121_D.func_72314_b(d, d, d), new IEntitySelector() {
            public boolean func_82704_a(Entity entity) {
               EntityPlayer entityplayer = (EntityPlayer)entity;
               return entityplayer.func_70089_S() && LOTREntityElf.this.isFriendlyAndAligned(entityplayer) && LOTREntityElf.this.func_70068_e(entityplayer) <= d1 ? entityplayer.func_110124_au().equals(LOTRPatron.elfBowPlayer) : false;
            }
         });
         if (!players.isEmpty() && this.func_70638_az() == null) {
            int tick = this.getBowingTick();
            if (tick >= 0) {
               ++tick;
            }

            if (tick > 40) {
               tick = -1;
            }

            this.setBowingTick(tick);
            if (tick >= 0) {
               this.func_70661_as().func_75499_g();
               bowingPlayer = (EntityPlayer)players.get(0);
               float bowLook = (float)Math.toDegrees(Math.atan2(bowingPlayer.field_70161_v - this.field_70161_v, bowingPlayer.field_70165_t - this.field_70165_t));
               bowLook -= 90.0F;
               this.field_70177_z = this.field_70759_as = bowLook;
            }
         } else {
            this.setBowingTick(0);
         }
      } else {
         this.prevBowAmount = this.bowAmount;
         int tick = this.getBowingTick();
         if (tick <= 0 && this.bowAmount > 0.0F) {
            this.bowAmount -= 0.2F;
            this.bowAmount = Math.max(this.bowAmount, 0.0F);
         } else if (tick > 0 && this.bowAmount < 1.0F) {
            this.bowAmount += 0.2F;
            this.bowAmount = Math.min(this.bowAmount, 1.0F);
         }
      }

   }

   public float getBowingAmount(float f) {
      return this.prevBowAmount + (this.bowAmount - this.prevBowAmount) * f;
   }

   public float getSoloSpin(float f) {
      return this.prevSoloSpin + (this.soloSpin - this.prevSoloSpin) * f;
   }

   public ItemStack func_70694_bm() {
      return this.field_70170_p.field_72995_K && this.isJazz() && this.isSolo() ? null : super.func_70694_bm();
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      }

      if (mode == LOTREntityNPC.AttackMode.MELEE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

      if (mode == LOTREntityNPC.AttackMode.RANGED) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getRangedWeapon());
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(LOTRMod.elfBone, 1);
      }

      this.dropNPCArrows(i);
      this.dropElfItems(flag, i);
   }

   protected void dropElfItems(boolean flag, int i) {
      if (flag) {
         int dropChance = 40 - i * 8;
         dropChance = Math.max(dropChance, 1);
         if (this.field_70146_Z.nextInt(dropChance) == 0) {
            this.func_145779_a(LOTRMod.lembas, 1);
         }
      }

   }

   public boolean func_70601_bi() {
      if (!super.func_70601_bi()) {
         return false;
      } else {
         return this.liftSpawnRestrictions || this.canElfSpawnHere();
      }
   }

   public abstract boolean canElfSpawnHere();

   public void func_70690_d(PotionEffect effect) {
      if (effect.func_76456_a() != Potion.field_76436_u.field_76415_H) {
         super.func_70690_d(effect);
      }
   }

   public String func_70639_aQ() {
      return this.func_70638_az() == null && this.field_70146_Z.nextInt(10) == 0 && this.familyInfo.isMale() ? "lotr:elf.male.say" : super.func_70639_aQ();
   }

   public String getAttackSound() {
      return this.familyInfo.isMale() ? "lotr:elf.male.attack" : super.getAttackSound();
   }
}
