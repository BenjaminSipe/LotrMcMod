package lotr.common.entity.animal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntitySwan extends EntityCreature implements LOTRAmbientCreature {
   public float flapPhase;
   public float flapPower;
   public float prevFlapPower;
   public float prevFlapPhase;
   public float flapAccel = 1.0F;
   private int peckTime;
   private int peckLength;
   private int timeUntilHiss;
   private static Random violenceRand = new Random();
   private boolean assignedAttackOrFlee = false;
   private EntityAIBase attackAI = new LOTREntityAIAttackOnCollide(this, 1.4D, true);
   private EntityAIBase fleeAI = new EntityAIPanic(this, 1.8D);
   private IEntitySelector swanAttackRange = new IEntitySelector() {
      public boolean func_82704_a(Entity entity) {
         return entity instanceof EntityLivingBase && entity.func_70089_S() && LOTREntitySwan.this.func_70068_e(entity) < 16.0D;
      }
   };
   private static boolean wreckBalrogs = false;

   public LOTREntitySwan(World world) {
      super(world);
      this.func_70105_a(0.5F, 0.7F);
      this.func_70661_as().func_75491_a(false);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, this.attackAI);
      this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest(this, EntityLivingBase.class, 10.0F, 0.05F));
      this.field_70714_bg.func_75776_a(4, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(0, new EntityAIHurtByTarget(this, false));
      if (wreckBalrogs) {
         this.field_70715_bh.func_75776_a(1, new EntityAINearestAttackableTarget(this, LOTREntityBalrog.class, 0, true));
      }

      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, this.swanAttackRange));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0D);
   }

   protected boolean func_70650_aV() {
      return true;
   }

   public void func_70636_d() {
      super.func_70636_d();
      this.prevFlapPhase = this.flapPhase;
      this.prevFlapPower = this.flapPower;
      this.flapPower += this.field_70122_E ? -0.02F : 0.05F;
      this.flapPower = Math.max(0.0F, Math.min(this.flapPower, 1.0F));
      if (!this.field_70122_E) {
         this.flapAccel = 0.6F;
      }

      this.flapPhase += this.flapAccel;
      this.flapAccel *= 0.95F;
      if (!this.field_70122_E && this.field_70181_x < 0.0D) {
         this.field_70181_x *= 0.6D;
      }

      if (this.field_70171_ac && this.field_70181_x < 0.0D) {
         this.field_70181_x *= 0.01D;
      }

      if (!this.field_70170_p.field_72995_K && this.func_70638_az() != null) {
         EntityLivingBase target = this.func_70638_az();
         if (!target.func_70089_S() || target instanceof EntityPlayer && ((EntityPlayer)target).field_71075_bZ.field_75098_d) {
            this.func_70624_b((EntityLivingBase)null);
         }
      }

      if (this.peckLength > 0) {
         ++this.peckTime;
         if (this.peckTime >= this.peckLength) {
            this.peckTime = 0;
            this.peckLength = 0;
         }
      } else {
         this.peckTime = 0;
      }

   }

   private boolean isViolentSwan() {
      long seed = this.func_110124_au().getLeastSignificantBits();
      violenceRand.setSeed(seed);
      return violenceRand.nextBoolean();
   }

   public void func_70619_bc() {
      if (!this.assignedAttackOrFlee) {
         this.field_70714_bg.func_85156_a(this.attackAI);
         this.field_70714_bg.func_85156_a(this.fleeAI);
         boolean violent = this.isViolentSwan();
         if (violent) {
            this.field_70714_bg.func_75776_a(1, this.attackAI);
         } else {
            this.field_70714_bg.func_75776_a(1, this.fleeAI);
         }

         this.assignedAttackOrFlee = true;
      }

      super.func_70619_bc();
      if (this.timeUntilHiss <= 0) {
         if (this.func_70638_az() == null && this.field_70146_Z.nextInt(3) == 0) {
            double range = 8.0D;
            List nearbyPlayers = this.field_70170_p.func_82733_a(EntityPlayer.class, this.field_70121_D.func_72314_b(range, range, range), LOTRMod.selectNonCreativePlayers());
            if (!nearbyPlayers.isEmpty()) {
               EntityPlayer entityplayer = (EntityPlayer)nearbyPlayers.get(this.field_70146_Z.nextInt(nearbyPlayers.size()));
               this.func_70661_as().func_75499_g();
               float hissLook = (float)Math.toDegrees(Math.atan2(entityplayer.field_70161_v - this.field_70161_v, entityplayer.field_70165_t - this.field_70165_t));
               hissLook -= 90.0F;
               this.field_70177_z = this.field_70759_as = hissLook;
               this.field_70170_p.func_72960_a(this, (byte)21);
               this.func_85030_a("lotr:swan.hiss", this.func_70599_aP(), this.func_70647_i());
               this.timeUntilHiss = 80 + this.field_70146_Z.nextInt(80);
            }
         }
      } else {
         --this.timeUntilHiss;
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 20) {
         this.peckLength = 10;
      } else if (b == 21) {
         this.peckLength = 40;
      } else {
         super.func_70103_a(b);
      }

   }

   public float getPeckAngle(float tick) {
      if (this.peckLength == 0) {
         return 0.0F;
      } else {
         float peck = ((float)this.peckTime + tick) / (float)this.peckLength;
         float cutoff = 0.2F;
         if (peck < cutoff) {
            return peck / cutoff;
         } else {
            return peck < 1.0F - cutoff ? 1.0F : (1.0F - peck) / cutoff;
         }
      }
   }

   public boolean func_70652_k(Entity entity) {
      float f = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
      if (wreckBalrogs && entity instanceof LOTREntityBalrog) {
         f *= 50.0F;
      }

      boolean flag = entity.func_70097_a(DamageSource.func_76358_a(this), f);
      if (flag) {
         this.field_70170_p.func_72960_a(this, (byte)20);
         if (wreckBalrogs && entity instanceof LOTREntityBalrog) {
            entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * 2.0F), 0.2D, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * 2.0F));
            this.func_70015_d(0);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      Entity entity = damagesource.func_76346_g();
      if (wreckBalrogs && entity instanceof LOTREntityBalrog) {
         f /= 20.0F;
      }

      boolean flag = super.func_70097_a(damagesource, f);
      if (flag) {
         if (wreckBalrogs && entity instanceof LOTREntityBalrog) {
            this.func_70015_d(0);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void func_70069_a(float f) {
   }

   public void func_70628_a(boolean flag, int i) {
      int feathers = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      for(int l = 0; l < feathers; ++l) {
         this.func_145779_a(LOTRMod.swanFeather, 1);
      }

   }

   protected boolean func_70692_ba() {
      return true;
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() ? LOTRAmbientSpawnChecks.canSpawn(this, 16, 8, 40, 2, Material.field_151586_h) : false;
   }

   public float func_70783_a(int i, int j, int k) {
      return this.field_70170_p.func_147439_a(i, j - 1, k) == this.field_70170_p.func_72807_a(i, k).field_76752_A ? 10.0F : this.field_70170_p.func_72801_o(i, j, k) - 0.5F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 1 + this.field_70170_p.field_73012_v.nextInt(2);
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
