package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIBalrogCharge;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityBalrog extends LOTREntityNPC {
   public static final IAttribute balrogChargeDamage = (new RangedAttribute("lotr.balrogChargeDamage", 2.0D, 0.0D, Double.MAX_VALUE)).func_111117_a("Balrog Charge Damage");
   private int chargeLean;
   private int prevChargeLean;
   private static final int chargeLeanTime = 10;
   public int chargeFrustration = 0;

   public LOTREntityBalrog(World world) {
      super(world);
      this.func_70105_a(2.4F, 5.0F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIBalrogCharge(this, 3.0D, 20.0F, 200));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAttackOnCollide(this, 1.6D, false));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 24.0F, 0.02F));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 16.0F, 0.02F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
      this.addTargetTasks(true);
      this.spawnsInDarkness = true;
      this.field_70178_ae = true;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(20, (byte)0);
   }

   public boolean isBalrogCharging() {
      return this.field_70180_af.func_75683_a(20) == 1;
   }

   public void setBalrogCharging(boolean flag) {
      this.field_70180_af.func_75692_b(20, (byte)(flag ? 1 : 0));
   }

   public float getInterpolatedChargeLean(float f) {
      return ((float)this.prevChargeLean + (float)(this.chargeLean - this.prevChargeLean) * f) / 10.0F;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
      this.func_110148_a(npcAttackDamage).func_111128_a(10.0D);
      this.func_110140_aT().func_111150_b(balrogChargeDamage).func_111128_a(15.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextBoolean()) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.balrogWhip));
      } else {
         int i = this.field_70146_Z.nextInt(3);
         if (i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
         } else if (i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUtumno));
         } else if (i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUtumno));
         }
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UTUMNO;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.func_110143_aJ() < this.func_110138_aP() && this.field_70173_aa % 10 == 0) {
         this.func_70691_i(1.0F);
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.func_70638_az() == null) {
            this.chargeFrustration = 0;
         } else if (this.isBalrogCharging()) {
            this.chargeFrustration = 0;
         } else {
            ++this.chargeFrustration;
         }
      }

      this.prevChargeLean = this.chargeLean;
      if (this.isBalrogCharging()) {
         if (this.chargeLean < 10) {
            ++this.chargeLean;
         }
      } else if (this.chargeLean > 0) {
         --this.chargeLean;
      }

      if (this.isWreathedInFlame()) {
         if (!this.field_70170_p.field_72995_K && this.field_70146_Z.nextInt(80) == 0) {
            boolean isUtumno = this.field_70170_p.field_73011_w instanceof LOTRWorldProviderUtumno;

            for(int l = 0; l < 24; ++l) {
               int i = MathHelper.func_76128_c(this.field_70165_t);
               int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
               int k = MathHelper.func_76128_c(this.field_70161_v);
               i += MathHelper.func_76136_a(this.field_70146_Z, -8, 8);
               j += MathHelper.func_76136_a(this.field_70146_Z, -4, 8);
               k += MathHelper.func_76136_a(this.field_70146_Z, -8, 8);
               Block block = this.field_70170_p.func_147439_a(i, j, k);
               float maxResistance = Blocks.field_150343_Z.func_149638_a(this);
               if ((block.isReplaceable(this.field_70170_p, i, j, k) || isUtumno && block.func_149638_a(this) <= maxResistance) && Blocks.field_150480_ab.func_149742_c(this.field_70170_p, i, j, k)) {
                  this.field_70170_p.func_147465_d(i, j, k, Blocks.field_150480_ab, 0, 3);
               }
            }
         }

         int l;
         String s;
         double d0;
         double d1;
         double d2;
         if (this.isBalrogCharging()) {
            for(l = 0; l < 10; ++l) {
               s = this.field_70146_Z.nextInt(3) == 0 ? "flame" : "largesmoke";
               d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N * 1.5D;
               d1 = this.field_70163_u + (double)this.field_70131_O * MathHelper.func_82716_a(this.field_70146_Z, 0.5D, 1.5D);
               d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N * 1.5D;
               double d3 = this.field_70159_w * -2.0D;
               double d4 = this.field_70181_x * -0.5D;
               double d5 = this.field_70179_y * -2.0D;
               this.field_70170_p.func_72869_a(s, d0, d1, d2, d3, d4, d5);
            }
         } else {
            for(l = 0; l < 3; ++l) {
               s = this.field_70146_Z.nextInt(3) == 0 ? "flame" : "largesmoke";
               d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N * 2.0D;
               d1 = this.field_70163_u + 0.5D + this.field_70146_Z.nextDouble() * (double)this.field_70131_O * 1.5D;
               d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N * 2.0D;
               this.field_70170_p.func_72869_a(s, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
         }
      }

   }

   public boolean isWreathedInFlame() {
      return this.func_70089_S() && !this.func_70026_G();
   }

   public void func_70653_a(Entity entity, float f, double d, double d1) {
      super.func_70653_a(entity, f, d, d1);
      this.field_70159_w /= 4.0D;
      this.field_70181_x /= 4.0D;
      this.field_70179_y /= 4.0D;
   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         EntityLivingBase curTarget = this.func_70638_az();
         if (curTarget != null && entity == curTarget) {
            this.chargeFrustration = 0;
         }

         if (this.func_70694_bm() == null) {
            entity.func_70015_d(5);
         }

         float attackDamage = (float)this.func_110148_a(LOTREntityNPC.npcAttackDamage).func_111126_e();
         float knockbackModifier = 0.25F * attackDamage;
         entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * knockbackModifier * 0.5F), (double)knockbackModifier * 0.1D, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * knockbackModifier * 0.5F));
         return true;
      } else {
         return false;
      }
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (damagesource == DamageSource.field_76379_h) {
         return false;
      } else {
         boolean flag = super.func_70097_a(damagesource, f);
         if (flag) {
            EntityLivingBase curTarget = this.func_70638_az();
            if (curTarget != null && damagesource.func_76346_g() == curTarget && damagesource.func_76364_f() == curTarget) {
               this.chargeFrustration = 0;
            }

            return true;
         } else {
            return false;
         }
      }
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killBalrog;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 15 + this.field_70146_Z.nextInt(10);
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int coals = MathHelper.func_76136_a(this.field_70146_Z, 4, 16 + i * 8);

      int fires;
      for(fires = 0; fires < coals; ++fires) {
         this.func_145779_a(Items.field_151044_h, 1);
      }

      fires = 1;
      if (i > 0) {
         float x;
         for(x = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, (float)i * 0.667F); x > 1.0F; ++fires) {
            --x;
         }

         if (this.field_70146_Z.nextFloat() < x) {
            ++fires;
         }
      }

      for(int l = 0; l < fires; ++l) {
         this.func_145779_a(LOTRMod.balrogFire, 1);
      }

   }

   public void dropNPCEquipment(boolean flag, int i) {
      if (flag && this.field_70146_Z.nextInt(3) == 0) {
         ItemStack heldItem = this.func_70694_bm();
         if (heldItem != null) {
            this.func_70099_a(heldItem, 0.0F);
            this.func_70062_b(0, (ItemStack)null);
         }
      }

      super.dropNPCEquipment(flag, i);
   }

   public void func_70645_a(DamageSource damagesource) {
      if (!this.field_70170_p.field_72995_K) {
         int exRange = 8;
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70163_u);
         int k = MathHelper.func_76128_c(this.field_70161_v);

         for(int i1 = i - exRange; i1 <= i + exRange; ++i1) {
            for(int j1 = j - exRange; j1 <= j + exRange; ++j1) {
               for(int k1 = k - exRange; k1 <= k + exRange; ++k1) {
                  Block block = this.field_70170_p.func_147439_a(i1, j1, k1);
                  if (block.func_149688_o() == Material.field_151581_o) {
                     this.field_70170_p.func_147468_f(i1, j1, k1);
                  }
               }
            }
         }
      } else {
         for(int l = 0; l < 100; ++l) {
            double d = this.field_70165_t + (double)(this.field_70130_N * MathHelper.func_151240_a(this.field_70146_Z, -1.0F, 1.0F));
            double d1 = this.field_70163_u + (double)(this.field_70131_O * MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 1.0F));
            double d2 = this.field_70161_v + (double)(this.field_70130_N * MathHelper.func_151240_a(this.field_70146_Z, -1.0F, 1.0F));
            double d3 = this.field_70146_Z.nextGaussian() * 0.03D;
            double d4 = this.field_70146_Z.nextGaussian() * 0.03D;
            double d5 = this.field_70146_Z.nextGaussian() * 0.03D;
            if (this.field_70146_Z.nextInt(3) == 0) {
               this.field_70170_p.func_72869_a("explode", d, d1, d2, d3, d4, d5);
            } else {
               this.field_70170_p.func_72869_a("flame", d, d1, d2, d3, d4, d5);
            }
         }
      }

      super.func_70645_a(damagesource);
      this.func_85030_a(this.func_70621_aR(), this.func_70599_aP() * 2.0F, this.func_70647_i() * 0.75F);
   }

   public String func_70639_aQ() {
      return "lotr:troll.say";
   }

   public String func_70621_aR() {
      return "lotr:troll.say";
   }

   protected float func_70599_aP() {
      return 1.5F;
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
      this.func_85030_a("lotr:troll.step", 0.75F, this.func_70647_i());
   }
}
