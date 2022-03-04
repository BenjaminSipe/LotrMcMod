package lotr.common.entity.npc;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAIUntamedPanic;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntitySpiderBase extends LOTREntityNPCRideable {
   public static int VENOM_NONE = 0;
   public static int VENOM_SLOWNESS = 1;
   public static int VENOM_POISON = 2;
   private static final int CLIMB_TIME = 100;

   public LOTREntitySpiderBase(World world) {
      super(world);
      this.func_70105_a(1.4F, 0.8F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, new EntityAILeapAtTarget(this, 0.4F));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIAttackOnCollide(this, 1.2D, false));
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIUntamedPanic(this, 1.2D));
      this.field_70714_bg.func_75776_a(5, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(6, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.addTargetTasks(true);
      this.spawnsInDarkness = true;
   }

   protected abstract int getRandomSpiderScale();

   protected abstract int getRandomSpiderType();

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(20, (byte)0);
      this.field_70180_af.func_75682_a(21, (byte)0);
      this.field_70180_af.func_75682_a(22, (byte)this.getRandomSpiderScale());
      this.setSpiderType(this.getRandomSpiderType());
      this.field_70180_af.func_75682_a(23, Short.valueOf((short)0));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D + (double)this.getSpiderScale() * 6.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.35D - (double)this.getSpiderScale() * 0.03D);
      this.func_110148_a(npcAttackDamage).func_111128_a(2.0D + (double)this.getSpiderScale());
   }

   public boolean isSpiderClimbing() {
      return (this.field_70180_af.func_75683_a(20) & 1) != 0;
   }

   public void setSpiderClimbing(boolean flag) {
      byte b = this.field_70180_af.func_75683_a(20);
      if (flag) {
         b = (byte)(b | 1);
      } else {
         b &= -2;
      }

      this.field_70180_af.func_75692_b(20, b);
   }

   public int getSpiderType() {
      return this.field_70180_af.func_75683_a(21);
   }

   public void setSpiderType(int i) {
      this.field_70180_af.func_75692_b(21, (byte)i);
   }

   public int getSpiderScale() {
      return this.field_70180_af.func_75683_a(22);
   }

   public void setSpiderScale(int i) {
      this.field_70180_af.func_75692_b(22, (byte)i);
   }

   public float getSpiderScaleAmount() {
      return 0.5F + (float)this.getSpiderScale() / 2.0F;
   }

   public int getSpiderClimbTime() {
      return this.field_70180_af.func_75693_b(23);
   }

   public void setSpiderClimbTime(int i) {
      this.field_70180_af.func_75692_b(23, (short)i);
   }

   public boolean shouldRenderClimbingMeter() {
      return !this.field_70122_E && this.getSpiderClimbTime() > 0;
   }

   public float getClimbFractionRemaining() {
      float f = (float)this.getSpiderClimbTime() / 100.0F;
      f = Math.min(f, 1.0F);
      f = 1.0F - f;
      return f;
   }

   public boolean isMountSaddled() {
      return this.isNPCTamed() && this.field_70153_n instanceof EntityPlayer;
   }

   public boolean getBelongsToNPC() {
      return false;
   }

   public void setBelongsToNPC(boolean flag) {
   }

   public String getMountArmorTexture() {
      return null;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("SpiderType", (byte)this.getSpiderType());
      nbt.func_74774_a("SpiderScale", (byte)this.getSpiderScale());
      nbt.func_74777_a("SpiderRideTime", (short)this.getSpiderClimbTime());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setSpiderType(nbt.func_74771_c("SpiderType"));
      this.setSpiderScale(nbt.func_74771_c("SpiderScale"));
      this.func_110148_a(npcAttackDamage).func_111128_a(2.0D + (double)this.getSpiderScale());
      this.setSpiderClimbTime(nbt.func_74765_d("SpiderRideTime"));
   }

   protected float getNPCScale() {
      return this.getSpiderScaleAmount();
   }

   public float func_70603_bj() {
      return this.getSpiderScaleAmount();
   }

   protected boolean canRideSpider() {
      return this.getSpiderScale() > 0;
   }

   protected double getBaseMountedYOffset() {
      return (double)this.field_70131_O - 0.7D;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         Entity rider = this.field_70153_n;
         if (rider instanceof EntityPlayer && !this.field_70122_E) {
            if (this.field_70123_F) {
               this.setSpiderClimbTime(this.getSpiderClimbTime() + 1);
            }
         } else {
            this.setSpiderClimbTime(0);
         }

         if (this.getSpiderClimbTime() >= 100) {
            this.setSpiderClimbing(false);
            if (this.field_70122_E) {
               this.setSpiderClimbTime(0);
            }
         } else {
            this.setSpiderClimbing(this.field_70123_F);
         }
      }

      if (!this.field_70170_p.field_72995_K && this.field_70153_n instanceof EntityPlayer && LOTRLevelData.getData((EntityPlayer)this.field_70153_n).getAlignment(this.getFaction()) < 50.0F) {
         this.field_70153_n.func_70078_a((Entity)null);
      }

   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      if (this.getSpiderType() == VENOM_POISON && itemstack != null && itemstack.func_77973_b() == Items.field_151069_bo) {
         --itemstack.field_77994_a;
         if (itemstack.field_77994_a <= 0) {
            entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, new ItemStack(LOTRMod.bottlePoison));
         } else if (!entityplayer.field_71071_by.func_70441_a(new ItemStack(LOTRMod.bottlePoison)) && !entityplayer.field_71075_bZ.field_75098_d) {
            entityplayer.func_71019_a(new ItemStack(LOTRMod.bottlePoison), false);
         }

         return true;
      } else if (!this.field_70170_p.field_72995_K && !this.hiredNPCInfo.isActive) {
         if (LOTRMountFunctions.interact(this, entityplayer)) {
            return true;
         } else {
            if (this.canRideSpider() && this.func_70638_az() != entityplayer) {
               boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0F;
               boolean notifyNotEnoughAlignment = false;
               if (!notifyNotEnoughAlignment && itemstack != null && LOTRMod.isOreNameEqual(itemstack, "bone") && this.isNPCTamed() && this.func_110143_aJ() < this.func_110138_aP()) {
                  if (hasRequiredAlignment) {
                     if (!entityplayer.field_71075_bZ.field_75098_d) {
                        --itemstack.field_77994_a;
                        if (itemstack.field_77994_a == 0) {
                           entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
                        }
                     }

                     this.func_70691_i(4.0F);
                     this.func_85030_a(this.func_70639_aQ(), this.func_70599_aP(), this.func_70647_i() * 1.5F);
                     return true;
                  }

                  notifyNotEnoughAlignment = true;
               }

               if (!notifyNotEnoughAlignment && this.field_70153_n == null) {
                  if (itemstack != null && itemstack.func_111282_a(entityplayer, this)) {
                     return true;
                  }

                  if (hasRequiredAlignment) {
                     entityplayer.func_70078_a(this);
                     this.func_70624_b((EntityLivingBase)null);
                     this.func_70661_as().func_75499_g();
                     return true;
                  }

                  notifyNotEnoughAlignment = true;
               }

               if (notifyNotEnoughAlignment) {
                  LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 50.0F, this.getFaction());
                  return true;
               }
            }

            return super.func_70085_c(entityplayer);
         }
      } else {
         return false;
      }
   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         if (entity instanceof EntityLivingBase) {
            int difficulty = this.field_70170_p.field_73013_u.func_151525_a();
            int duration = difficulty * (difficulty + 5) / 2;
            if (duration > 0) {
               if (this.getSpiderType() == VENOM_SLOWNESS) {
                  ((EntityLivingBase)entity).func_70690_d(new PotionEffect(Potion.field_76421_d.field_76415_H, duration * 20, 0));
               } else if (this.getSpiderType() == VENOM_POISON) {
                  ((EntityLivingBase)entity).func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, duration * 20, 0));
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      return damagesource == DamageSource.field_76379_h ? false : super.func_70097_a(damagesource, f);
   }

   protected String func_70639_aQ() {
      return "mob.spider.say";
   }

   protected String func_70621_aR() {
      return "mob.spider.say";
   }

   protected String func_70673_aS() {
      return "mob.spider.death";
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
      this.func_85030_a("mob.spider.step", 0.15F, 1.0F);
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int string = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int j = 0; j < string; ++j) {
         this.func_145779_a(Items.field_151007_F, 1);
      }

      if (flag && (this.field_70146_Z.nextInt(3) == 0 || this.field_70146_Z.nextInt(1 + i) > 0)) {
         this.func_145779_a(Items.field_151070_bp, 1);
      }

   }

   public boolean canDropRares() {
      return false;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      int i = this.getSpiderScale();
      return 2 + i + this.field_70146_Z.nextInt(i + 2);
   }

   public boolean func_70617_f_() {
      return this.isSpiderClimbing();
   }

   public void func_70110_aj() {
   }

   public void setInQuag() {
      super.func_70110_aj();
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   public boolean func_70687_e(PotionEffect effect) {
      if (this.getSpiderType() == VENOM_SLOWNESS && effect.func_76456_a() == Potion.field_76421_d.field_76415_H) {
         return false;
      } else {
         return this.getSpiderType() == VENOM_POISON && effect.func_76456_a() == Potion.field_76436_u.field_76415_H ? false : super.func_70687_e(effect);
      }
   }

   public boolean func_110164_bC() {
      return this.isNPCTamed();
   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      return false;
   }
}
