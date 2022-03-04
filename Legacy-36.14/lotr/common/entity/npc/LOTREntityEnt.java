package lotr.common.entity.npc;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockCorruptMallorn;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIEntHealSapling;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemEntDraught;
import net.minecraft.block.Block;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityEnt extends LOTREntityTree {
   private Random branchRand = new Random();
   public int eyesClosed;
   public ChunkCoordinates saplingHealTarget;
   public boolean canHealSapling = true;

   public LOTREntityEnt(World world) {
      super(world);
      this.func_70105_a(1.4F, 4.6F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIEntHealSapling(this, 1.5D));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 2.0D, false));
      this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(4, new EntityAIWatchClosest(this, EntityLiving.class, 10.0F, 0.02F));
      this.field_70714_bg.func_75776_a(5, new EntityAILookIdle(this));
      this.addTargetTasks(true);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(18, (byte)0);
   }

   public boolean isHealingSapling() {
      return this.field_70180_af.func_75683_a(18) == 1;
   }

   public void setHealingSapling(boolean flag) {
      this.field_70180_af.func_75692_b(18, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getEntName(this.field_70146_Z));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(npcAttackDamage).func_111128_a(7.0D);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.FANGORN;
   }

   public void setAttackTarget(EntityLivingBase target, boolean speak) {
      super.setAttackTarget(target, speak);
      if (this.func_70638_az() == null) {
         this.canHealSapling = true;
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      if (this.saplingHealTarget != null) {
         nbt.func_74768_a("SaplingHealX", this.saplingHealTarget.field_71574_a);
         nbt.func_74768_a("SaplingHealY", this.saplingHealTarget.field_71572_b);
         nbt.func_74768_a("SaplingHealZ", this.saplingHealTarget.field_71573_c);
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("EntName")) {
         this.familyInfo.setName(nbt.func_74779_i("EntName"));
      }

      if (nbt.func_74764_b("SaplingHealX")) {
         int x = nbt.func_74762_e("SaplingHealX");
         int y = nbt.func_74762_e("SaplingHealY");
         int z = nbt.func_74762_e("SaplingHealZ");
         this.saplingHealTarget = new ChunkCoordinates(x, y, z);
      }

   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public int getExtraHeadBranches() {
      long l = this.func_110124_au().getLeastSignificantBits();
      l ^= l * 365620672396L ^ l * 12784892284L;
      l = l * l * 18569660L + l * 6639092L;
      this.branchRand.setSeed(l);
      return this.branchRand.nextBoolean() ? 0 : MathHelper.func_76136_a(this.branchRand, 2, 5);
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.field_70170_p.field_72995_K) {
         if (this.eyesClosed > 0) {
            --this.eyesClosed;
         } else if (this.field_70146_Z.nextInt(400) == 0) {
            this.eyesClosed = 30;
         }

         if (this.isHealingSapling()) {
            for(int l = 0; l < 2; ++l) {
               float angle = this.field_70759_as + 90.0F + MathHelper.func_151240_a(this.field_70146_Z, -40.0F, 40.0F);
               angle = (float)Math.toRadians((double)angle);
               double d = this.field_70165_t + (double)MathHelper.func_76134_b(angle) * 1.5D;
               double d1 = this.field_70121_D.field_72338_b + (double)(this.field_70131_O * MathHelper.func_151240_a(this.field_70146_Z, 0.3F, 0.6F));
               double d2 = this.field_70161_v + (double)MathHelper.func_76126_a(angle) * 1.5D;
               double d3 = (double)MathHelper.func_76134_b(angle) * 0.06D;
               double d4 = -0.03D;
               double d5 = (double)MathHelper.func_76126_a(angle) * 0.06D;
               LOTRMod.proxy.spawnParticle("leafGold_30", d, d1, d2, d3, d4, d5);
            }
         }
      }

   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         float knockbackModifier = 1.5F;
         entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * knockbackModifier * 0.5F), 0.15D, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * knockbackModifier * 0.5F));
         return true;
      } else {
         return false;
      }
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (!this.field_70170_p.field_72995_K && flag) {
         if (damagesource.func_76346_g() != null) {
            this.setHealingSapling(false);
         }

         if (this.func_70638_az() != null) {
            this.canHealSapling = false;
         }
      }

      return flag;
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer && this.saplingHealTarget != null) {
         int i = this.saplingHealTarget.field_71574_a;
         int j = this.saplingHealTarget.field_71572_b;
         int k = this.saplingHealTarget.field_71573_c;
         Block block = this.field_70170_p.func_147439_a(i, j, k);
         int meta = this.field_70170_p.func_72805_g(i, j, k);
         if (block == LOTRMod.corruptMallorn) {
            ++meta;
            if (meta >= LOTRBlockCorruptMallorn.ENT_KILLS) {
               LOTRBlockCorruptMallorn.summonEntBoss(this.field_70170_p, i, j, k);
            } else {
               this.field_70170_p.func_72921_c(i, j, k, meta, 3);
            }
         }
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killEnt;
   }

   public float getAlignmentBonus() {
      return 3.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      if (flag) {
         int dropChance = 10 - i * 2;
         if (dropChance < 1) {
            dropChance = 1;
         }

         if (this.field_70146_Z.nextInt(dropChance) == 0) {
            this.func_70099_a(new ItemStack(LOTRMod.entDraught, 1, this.field_70146_Z.nextInt(LOTRItemEntDraught.draughtTypes.length)), 0.0F);
         }
      }

   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 5 + this.field_70146_Z.nextInt(6);
   }

   protected float func_70599_aP() {
      return 1.5F;
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
      this.func_85030_a("lotr:ent.step", 0.75F, this.func_70647_i());
   }

   protected LOTRAchievement getTalkAchievement() {
      return LOTRAchievement.talkEnt;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "ent/ent/friendly" : "ent/ent/hostile";
   }
}
