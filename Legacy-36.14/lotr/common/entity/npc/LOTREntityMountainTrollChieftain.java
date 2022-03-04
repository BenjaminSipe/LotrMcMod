package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIBossJumpAttack;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import lotr.common.item.LOTRItemBossTrophy;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityMountainTrollChieftain extends LOTREntityMountainTroll implements LOTRBoss {
   private static final int SPAWN_TIME = 100;
   private int trollDeathTick;
   private int healAmount;

   public LOTREntityMountainTrollChieftain(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIBossJumpAttack(this, 1.5D, 0.03F));
   }

   public float getTrollScale() {
      return 2.0F;
   }

   protected EntityAIBase getTrollRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.2D, 20, 50, 24.0F);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(22, Short.valueOf((short)0));
      this.field_70180_af.func_75682_a(23, -1);
      this.field_70180_af.func_75682_a(24, (byte)2);
   }

   public boolean hasTwoHeads() {
      return true;
   }

   public int getTrollSpawnTick() {
      return this.field_70180_af.func_75693_b(22);
   }

   public void setTrollSpawnTick(int i) {
      this.field_70180_af.func_75692_b(22, (short)i);
   }

   public int getHealingEntityID() {
      return this.field_70180_af.func_75679_c(23);
   }

   public void setHealingEntityID(int i) {
      this.field_70180_af.func_75692_b(23, i);
   }

   public int getTrollArmorLevel() {
      return this.field_70180_af.func_75683_a(24);
   }

   public void setTrollArmorLevel(int i) {
      this.field_70180_af.func_75692_b(24, (byte)i);
   }

   public int func_70658_aO() {
      return 12;
   }

   public float getArmorLevelChanceModifier() {
      int i = 3 - this.getTrollArmorLevel();
      if (i < 1) {
         i = 1;
      }

      return (float)i;
   }

   public float getBaseChanceModifier() {
      return this.bossInfo.getHealthChanceModifier() * this.getArmorLevelChanceModifier();
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(50.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(npcAttackDamage).func_111128_a(8.0D);
      this.func_110148_a(thrownRockDamage).func_111128_a(8.0D);
   }

   public float getSpawningOffset(float f) {
      float f1 = ((float)this.getTrollSpawnTick() + f) / 100.0F;
      f1 = Math.min(f1, 1.0F);
      return (1.0F - f1) * -5.0F;
   }

   public void func_70636_d() {
      super.func_70636_d();
      int i;
      double d;
      double d1;
      double d2;
      if (this.getTrollSpawnTick() < 100) {
         if (!this.field_70170_p.field_72995_K) {
            this.setTrollSpawnTick(this.getTrollSpawnTick() + 1);
            if (this.getTrollSpawnTick() == 100) {
               this.bossInfo.doJumpAttack(1.5D);
            }
         } else {
            for(i = 0; i < 32; ++i) {
               d = this.field_70165_t + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D;
               d1 = this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O + (double)this.getSpawningOffset(0.0F);
               d2 = this.field_70161_v + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D;
               LOTRMod.proxy.spawnParticle("mtcSpawn", d, d1, d2, 0.0D, 0.0D, 0.0D);
            }
         }
      }

      if (this.field_70170_p.field_72995_K && this.getTrollArmorLevel() == 0) {
         for(i = 0; i < 4; ++i) {
            this.field_70170_p.func_72869_a("largesmoke", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }
      }

      if (!this.field_70170_p.field_72995_K && (this.getTrollBurnTime() >= 0 || this.trollDeathTick > 0)) {
         if (this.trollDeathTick == 0) {
            this.field_70170_p.func_72956_a(this, "lotr:troll.transform", this.func_70599_aP(), this.func_70647_i());
         }

         if (this.trollDeathTick % 5 == 0) {
            this.field_70170_p.func_72960_a(this, (byte)15);
         }

         if (this.trollDeathTick % 10 == 0) {
            this.func_85030_a(this.func_70639_aQ(), this.func_70599_aP() * 2.0F, 0.8F);
         }

         ++this.trollDeathTick;
         this.field_70177_z += 60.0F * (this.field_70146_Z.nextFloat() - 0.5F);
         this.field_70759_as += 60.0F * (this.field_70146_Z.nextFloat() - 0.5F);
         this.field_70125_A += 60.0F * (this.field_70146_Z.nextFloat() - 0.5F);
         this.field_70721_aZ += 60.0F * (this.field_70146_Z.nextFloat() - 0.5F);
         if (this.trollDeathTick >= 200) {
            this.func_70106_y();
         }
      }

      float f;
      if (!this.field_70170_p.field_72995_K && this.func_110143_aJ() < this.func_110138_aP()) {
         f = this.getBaseChanceModifier();
         f *= 0.02F;
         if (this.field_70146_Z.nextFloat() < f) {
            List nearbyTrolls = this.field_70170_p.func_72872_a(LOTREntityTroll.class, this.field_70121_D.func_72314_b(24.0D, 8.0D, 24.0D));
            if (!nearbyTrolls.isEmpty()) {
               LOTREntityTroll troll = (LOTREntityTroll)nearbyTrolls.get(this.field_70146_Z.nextInt(nearbyTrolls.size()));
               if (!(troll instanceof LOTREntityMountainTrollChieftain) && troll.func_70089_S()) {
                  this.setHealingEntityID(troll.func_145782_y());
                  this.healAmount = 8 + this.field_70146_Z.nextInt(3);
               }
            }
         }
      }

      if (this.getHealingEntityID() != -1) {
         Entity entity = this.field_70170_p.func_73045_a(this.getHealingEntityID());
         if (entity != null && entity instanceof LOTREntityTroll && entity.func_70089_S()) {
            if (!this.field_70170_p.field_72995_K) {
               if (this.field_70173_aa % 20 == 0) {
                  this.func_70691_i(3.0F);
                  entity.func_70097_a(DamageSource.field_76377_j, 3.0F);
                  --this.healAmount;
                  if (!entity.func_70089_S() || this.func_110143_aJ() >= this.func_110138_aP() || this.healAmount <= 0) {
                     this.setHealingEntityID(-1);
                  }
               }
            } else {
               d = entity.field_70165_t;
               d1 = entity.field_70163_u + (double)entity.field_70131_O / 2.0D;
               d2 = entity.field_70161_v;
               double d3 = this.field_70165_t - d;
               double d4 = this.field_70163_u + (double)this.field_70131_O / 2.0D - d1;
               double d5 = this.field_70161_v - d2;
               d3 /= 30.0D;
               d4 /= 30.0D;
               d5 /= 30.0D;
               LOTRMod.proxy.spawnParticle("mtcHeal", d, d1, d2, d3, d4, d5);
            }
         } else if (!this.field_70170_p.field_72995_K) {
            this.setHealingEntityID(-1);
         }
      }

      if (!this.field_70170_p.field_72995_K && this.func_110143_aJ() < this.func_110138_aP() && this.field_70146_Z.nextInt(50) == 0 && !this.isThrowingRocks()) {
         LOTREntityThrownRock rock = this.getThrownRock();
         if (rock.getSpawnsTroll()) {
            rock.func_70012_b(this.field_70165_t, this.field_70163_u + (double)this.field_70131_O, this.field_70161_v, 0.0F, 0.0F);
            rock.field_70159_w = 0.0D;
            rock.field_70181_x = 1.5D;
            rock.field_70179_y = 0.0D;
            this.field_70170_p.func_72838_d(rock);
            this.func_71038_i();
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         f = this.getBaseChanceModifier();
         f *= 0.05F;
         if (this.field_70146_Z.nextFloat() < f) {
            this.bossInfo.doTargetedJumpAttack(1.5D);
         }
      }

   }

   protected boolean func_70610_aX() {
      return this.getTrollSpawnTick() >= 100 && this.trollDeathTick <= 0 ? super.func_70610_aX() : true;
   }

   public void onJumpAttackFall() {
      this.field_70170_p.func_72960_a(this, (byte)20);
      this.func_85030_a("lotr:troll.rockSmash", 1.5F, 0.75F);
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      int i;
      if (b == 20) {
         for(i = 0; i < 360; i += 2) {
            float angle = (float)Math.toRadians((double)i);
            double distance = 2.0D;
            double d = distance * (double)MathHelper.func_76126_a(angle);
            double d1 = distance * (double)MathHelper.func_76134_b(angle);
            LOTRMod.proxy.spawnParticle("largeStone", this.field_70165_t + d, this.field_70121_D.field_72338_b + 0.1D, this.field_70161_v + d1, d * 0.2D, 0.2D, d1 * 0.2D);
         }
      } else if (b == 21) {
         for(i = 0; i < 64; ++i) {
            LOTRMod.proxy.spawnParticle("mtcArmor", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("TrollSpawnTick", this.getTrollSpawnTick());
      nbt.func_74768_a("TrollDeathTick", this.trollDeathTick);
      nbt.func_74768_a("TrollArmorLevel", this.getTrollArmorLevel());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setTrollSpawnTick(nbt.func_74762_e("TrollSpawnTick"));
      this.trollDeathTick = nbt.func_74762_e("TrollDeathTick");
      if (nbt.func_74764_b("TrollArmorLevel")) {
         this.setTrollArmorLevel(nbt.func_74762_e("TrollArmorLevel"));
      }

   }

   protected LOTREntityThrownRock getThrownRock() {
      LOTREntityThrownRock rock = super.getThrownRock();
      float f = this.getBaseChanceModifier();
      f *= 0.4F;
      int maxNearbyTrolls = 5;
      List nearbyTrolls = this.field_70170_p.func_72872_a(LOTREntityTroll.class, this.field_70121_D.func_72314_b(24.0D, 8.0D, 24.0D));
      float nearbyModifier = (float)(maxNearbyTrolls - nearbyTrolls.size()) / (float)maxNearbyTrolls;
      f *= nearbyModifier;
      if (this.field_70146_Z.nextFloat() < f) {
         rock.setSpawnsTroll(true);
      }

      return rock;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (this.getTrollSpawnTick() >= 100 && this.trollDeathTick <= 0) {
         if (LOTRMod.getDamagingPlayerIncludingUnits(damagesource) == null && f > 1.0F) {
            f = 1.0F;
         }

         boolean flag = super.func_70097_a(damagesource, f);
         return flag;
      } else {
         return false;
      }
   }

   protected void func_70665_d(DamageSource damagesource, float f) {
      super.func_70665_d(damagesource, f);
      if (!this.field_70170_p.field_72995_K && this.getTrollArmorLevel() > 0 && this.func_110143_aJ() <= 0.0F) {
         this.setTrollArmorLevel(this.getTrollArmorLevel() - 1);
         double maxHealth;
         if (this.getTrollArmorLevel() == 0) {
            maxHealth = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
            maxHealth *= 1.5D;
            this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(maxHealth);
         }

         maxHealth = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
         maxHealth *= 2.0D;
         this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(maxHealth);
         this.func_70606_j(this.func_110138_aP());
         this.field_70170_p.func_72960_a(this, (byte)21);
      }

   }

   public void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int drops = 3 + this.field_70146_Z.nextInt(4) + this.field_70146_Z.nextInt(i * 2 + 1);

      int bones;
      for(bones = 0; bones < drops; ++bones) {
         this.dropTrollItems(flag, i);
      }

      bones = MathHelper.func_76136_a(this.field_70146_Z, 4, 8) + this.field_70146_Z.nextInt(i * 3 + 1);

      int coins;
      for(coins = 0; coins < bones; ++coins) {
         this.func_145779_a(LOTRMod.trollBone, 1);
      }

      int dropped;
      for(coins = MathHelper.func_76136_a(this.field_70146_Z, 50, 100 + i * 100); coins > 0; coins -= dropped) {
         dropped = Math.min(20, coins);
         this.func_145779_a(LOTRMod.silverCoin, dropped);
      }

      this.dropChestContents(LOTRChestContents.TROLL_HOARD, 5, 8 + i * 3);
      this.func_70099_a(new ItemStack(LOTRMod.bossTrophy, 1, LOTRItemBossTrophy.TrophyType.MOUNTAIN_TROLL_CHIEFTAIN.trophyID), 0.0F);
      float swordChance = 0.3F;
      swordChance += (float)i * 0.1F;
      if (this.field_70146_Z.nextFloat() < swordChance) {
         this.func_145779_a(LOTRMod.swordGondolin, 1);
      }

      float armorChance = 0.2F;
      armorChance += (float)i * 0.05F;
      if (this.field_70146_Z.nextFloat() < armorChance) {
         this.func_145779_a(LOTRMod.helmetGondolin, 1);
      }

      if (this.field_70146_Z.nextFloat() < armorChance) {
         this.func_145779_a(LOTRMod.bodyGondolin, 1);
      }

      if (this.field_70146_Z.nextFloat() < armorChance) {
         this.func_145779_a(LOTRMod.legsGondolin, 1);
      }

      if (this.field_70146_Z.nextFloat() < armorChance) {
         this.func_145779_a(LOTRMod.bootsGondolin, 1);
      }

   }

   protected void dropTrollTotemPart(boolean flag, int i) {
   }

   public LOTRAchievement getBossKillAchievement() {
      return LOTRAchievement.killMountainTrollChieftain;
   }

   public float getAlignmentBonus() {
      return 50.0F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 100;
   }
}
