package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityBarrowWight extends LOTREntityNPC {
   private static Potion[] attackEffects;

   public LOTREntityBarrowWight(World world) {
      super(world);
      this.func_70105_a(0.8F, 2.5F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, this.getWightAttackAI());
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
      this.addTargetTasks(true);
      this.spawnsInDarkness = true;
   }

   public EntityAIBase getWightAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, -1);
   }

   public int getTargetEntityID() {
      return this.field_70180_af.func_75679_c(16);
   }

   public void setTargetEntityID(Entity entity) {
      this.field_70180_af.func_75692_b(16, entity == null ? -1 : entity.func_145782_y());
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(50.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(npcAttackDamage).func_111128_a(6.0D);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.HOSTILE;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.field_70170_p.field_72995_K) {
         for(int l = 0; l < 1; ++l) {
            double d = this.field_70165_t + (double)this.field_70130_N * MathHelper.func_82716_a(this.field_70146_Z, -0.5D, 0.5D);
            double d1 = this.field_70163_u + (double)this.field_70131_O * MathHelper.func_82716_a(this.field_70146_Z, 0.4D, 0.8D);
            double d2 = this.field_70161_v + (double)this.field_70130_N * MathHelper.func_82716_a(this.field_70146_Z, -0.5D, 0.5D);
            double d3 = MathHelper.func_82716_a(this.field_70146_Z, -0.1D, 0.1D);
            double d4 = MathHelper.func_82716_a(this.field_70146_Z, -0.2D, -0.05D);
            double d5 = MathHelper.func_82716_a(this.field_70146_Z, -0.1D, 0.1D);
            if (this.field_70146_Z.nextBoolean()) {
               LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
            } else {
               this.field_70170_p.func_72869_a("smoke", d, d1, d2, d3, d4, d5);
            }
         }
      }

   }

   public void setAttackTarget(EntityLivingBase target, boolean speak) {
      super.setAttackTarget(target, speak);
      if (!this.field_70170_p.field_72995_K) {
         this.setTargetEntityID(target);
      }

   }

   public boolean func_70652_k(Entity entity) {
      if (!super.func_70652_k(entity)) {
         return false;
      } else {
         if (entity instanceof EntityLivingBase) {
            int difficulty = this.field_70170_p.field_73013_u.func_151525_a();
            int duration = difficulty * (difficulty + 5) / 2;
            if (duration > 0) {
               Potion[] var4 = attackEffects;
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Potion effect = var4[var6];
                  ((EntityLivingBase)entity).func_70690_d(new PotionEffect(effect.field_76415_H, duration * 20, 0));
               }
            }
         }

         return true;
      }
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killBarrowWight;
   }

   public float getAlignmentBonus() {
      return 0.0F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 4 + this.field_70146_Z.nextInt(5);
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

      if (this.field_70146_Z.nextBoolean()) {
         this.dropChestContents(LOTRChestContents.BARROW_DOWNS, 1, 2 + i + 1);
      }

   }

   public boolean canDropRares() {
      return true;
   }

   protected String func_70621_aR() {
      return "lotr:wight.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:wight.death";
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         if (this.liftSpawnRestrictions) {
            return true;
         } else {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            return j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == this.field_70170_p.func_72807_a(i, k).field_76752_A;
         }
      } else {
         return false;
      }
   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      return false;
   }

   static {
      attackEffects = new Potion[]{Potion.field_76421_d, Potion.field_76419_f, Potion.field_82731_v};
   }
}
