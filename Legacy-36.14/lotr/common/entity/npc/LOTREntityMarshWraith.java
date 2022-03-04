package lotr.common.entity.npc;

import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityMarshWraithBall;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityMarshWraith extends LOTREntityNPC {
   public UUID attackTargetUUID;
   private boolean checkedForAttackTarget;
   private int timeUntilDespawn = -1;
   private static final int maxTimeUntilDespawn = 100;

   public LOTREntityMarshWraith(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.field_70714_bg.func_75776_a(0, new LOTREntityAIRangedAttack(this, 1.6D, 40, 12.0F));
      this.field_70714_bg.func_75776_a(1, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(3, new EntityAILookIdle(this));
      this.field_70158_ak = true;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, 0);
      this.field_70180_af.func_75682_a(17, 0);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(50.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public int getSpawnFadeTime() {
      return this.field_70180_af.func_75679_c(16);
   }

   public void setSpawnFadeTime(int i) {
      this.field_70180_af.func_75692_b(16, i);
   }

   public int getDeathFadeTime() {
      return this.field_70180_af.func_75679_c(17);
   }

   public void setDeathFadeTime(int i) {
      this.field_70180_af.func_75692_b(17, i);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.HOSTILE;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("SpawnFadeTime", this.getSpawnFadeTime());
      nbt.func_74768_a("DeathFadeTime", this.getDeathFadeTime());
      if (this.attackTargetUUID != null) {
         nbt.func_74772_a("TargetUUIDMost", this.attackTargetUUID.getMostSignificantBits());
         nbt.func_74772_a("TargetUUIDLeast", this.attackTargetUUID.getLeastSignificantBits());
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setSpawnFadeTime(nbt.func_74762_e("SpawnFadeTime"));
      this.setDeathFadeTime(nbt.func_74762_e("DeathFadeTime"));
      if (nbt.func_74764_b("TargetUUIDMost") && nbt.func_74764_b("TargetUUIDLeast")) {
         this.attackTargetUUID = new UUID(nbt.func_74763_f("TargetUUIDMost"), nbt.func_74763_f("TargetUUIDLeast"));
      }

   }

   public void func_70110_aj() {
   }

   public void func_70636_d() {
      super.func_70636_d();
      int j;
      int k;
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         int hover = 2;
         j = MathHelper.func_76128_c(this.field_70165_t);
         k = MathHelper.func_76128_c(this.field_70163_u);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         double newY = this.field_70163_u;

         for(int j1 = 0; j1 <= hover; ++j1) {
            int j2 = k - j1;
            Block block = this.field_70170_p.func_147439_a(j, j2, k);
            Material material = block.func_149688_o();
            if (material.func_76220_a() || material.func_76224_d()) {
               newY = Math.max(newY, (double)(k + j1 + 1));
            }
         }

         this.field_70181_x += (newY - this.field_70163_u) * 0.04D;
      }

      if (this.field_70146_Z.nextBoolean()) {
         this.field_70170_p.func_72869_a("smoke", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
      }

      if (!this.field_70170_p.field_72995_K) {
         int i;
         if (this.func_70638_az() == null && this.attackTargetUUID != null && !this.checkedForAttackTarget) {
            for(i = 0; i < this.field_70170_p.field_72996_f.size(); ++i) {
               Entity entity = (Entity)this.field_70170_p.field_72996_f.get(i);
               if (entity instanceof EntityLiving && entity.func_110124_au().equals(this.attackTargetUUID)) {
                  this.func_70624_b((EntityLiving)entity);
                  break;
               }
            }

            this.checkedForAttackTarget = true;
         }

         if (this.getSpawnFadeTime() < 30) {
            this.setSpawnFadeTime(this.getSpawnFadeTime() + 1);
         }

         if (this.getDeathFadeTime() > 0) {
            this.setDeathFadeTime(this.getDeathFadeTime() - 1);
         }

         if (this.getSpawnFadeTime() == 30 && this.getDeathFadeTime() == 0) {
            if (this.func_70638_az() != null && !this.func_70638_az().field_70128_L) {
               if (this.timeUntilDespawn == -1) {
                  this.timeUntilDespawn = 100;
               }

               i = MathHelper.func_76128_c(this.func_70638_az().field_70165_t);
               j = MathHelper.func_76128_c(this.func_70638_az().field_70121_D.field_72338_b);
               k = MathHelper.func_76128_c(this.func_70638_az().field_70161_v);
               if (this.field_70170_p.func_147439_a(i, j, k).func_149688_o() != Material.field_151586_h && this.field_70170_p.func_147439_a(i, j - 1, k).func_149688_o() != Material.field_151586_h) {
                  if (this.timeUntilDespawn > 0) {
                     --this.timeUntilDespawn;
                  } else {
                     this.setDeathFadeTime(30);
                     this.func_70624_b((EntityLivingBase)null);
                  }
               } else {
                  this.timeUntilDespawn = 100;
               }
            } else {
               this.setDeathFadeTime(30);
            }
         }

         if (this.getDeathFadeTime() == 1) {
            this.func_70106_y();
         }
      }

   }

   public void func_82196_d(EntityLivingBase target, float f) {
      if (this.getSpawnFadeTime() == 30 && this.getDeathFadeTime() == 0) {
         LOTREntityMarshWraithBall ball = new LOTREntityMarshWraithBall(this.field_70170_p, this, target);
         this.func_85030_a("lotr:wraith.marshWraith_shoot", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
         this.field_70170_p.func_72838_d(ball);
      }

   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean vulnerable = false;
      Entity entity = damagesource.func_76346_g();
      if (entity instanceof EntityLivingBase && entity == damagesource.func_76364_f()) {
         ItemStack itemstack = ((EntityLivingBase)entity).func_70694_bm();
         if (itemstack != null && LOTREnchantmentHelper.hasEnchant(itemstack, LOTREnchantment.baneWraith)) {
            vulnerable = true;
         }
      }

      if (vulnerable && this.getDeathFadeTime() == 0) {
         boolean flag = super.func_70097_a(damagesource, f);
         if (flag) {
            this.timeUntilDespawn = 100;
         }

         return flag;
      } else {
         return false;
      }
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K) {
         this.setDeathFadeTime(30);
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int flesh = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < flesh; ++l) {
         this.func_145779_a(Items.field_151078_bh, 1);
      }

      this.dropChestContents(LOTRChestContents.MARSH_REMAINS, 1, 3 + i);
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killMarshWraith;
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected String func_70621_aR() {
      return "lotr:wight.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:wight.death";
   }

   public boolean func_70072_I() {
      return false;
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      return false;
   }
}
