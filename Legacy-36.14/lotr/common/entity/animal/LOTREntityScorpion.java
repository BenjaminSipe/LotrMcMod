package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRMobSpawnerCondition;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTREntityHaradPyramidWraith;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class LOTREntityScorpion extends EntityMob implements LOTRMobSpawnerCondition {
   private float scorpionWidth = -1.0F;
   private float scorpionHeight;
   protected boolean spawningFromSpawner = false;
   private static IEntitySelector noWraiths = new IEntitySelector() {
      public boolean func_82704_a(Entity entity) {
         return !(entity instanceof LOTREntityHaradPyramidWraith);
      }
   };

   public LOTREntityScorpion(World world) {
      super(world);
      this.func_70105_a(1.2F, 0.9F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 1.2D, false));
      this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 0.05F));
      this.field_70714_bg.func_75776_a(4, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, LOTREntityNPC.class, 0, true, false, noWraiths));
   }

   public void setSpawningFromMobSpawner(boolean flag) {
      this.spawningFromSpawner = flag;
   }

   protected int getRandomScorpionScale() {
      return this.field_70146_Z.nextInt(3);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(18, (byte)this.getRandomScorpionScale());
      this.field_70180_af.func_75682_a(19, 0);
   }

   public int getScorpionScale() {
      return this.field_70180_af.func_75683_a(18);
   }

   public void setScorpionScale(int i) {
      this.field_70180_af.func_75692_b(18, (byte)i);
   }

   public float getScorpionScaleAmount() {
      return 0.5F + (float)this.getScorpionScale() / 2.0F;
   }

   public int getStrikeTime() {
      return this.field_70180_af.func_75679_c(19);
   }

   public void setStrikeTime(int i) {
      this.field_70180_af.func_75692_b(19, i);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D + (double)this.getScorpionScale() * 6.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.35D - (double)this.getScorpionScale() * 0.05D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0D + (double)this.getScorpionScale());
   }

   public boolean func_70650_aV() {
      return true;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("ScorpionScale", (byte)this.getScorpionScale());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setScorpionScale(nbt.func_74771_c("ScorpionScale"));
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0D + (double)this.getScorpionScale());
   }

   public float func_70783_a(int i, int j, int k) {
      return this.spawningFromSpawner ? 0.0F : super.func_70783_a(i, j, k);
   }

   public void func_70636_d() {
      super.func_70636_d();
      this.rescaleScorpion(this.getScorpionScaleAmount());
      if (!this.field_70170_p.field_72995_K) {
         int i = this.getStrikeTime();
         if (i > 0) {
            this.setStrikeTime(i - 1);
         }
      }

   }

   protected void func_70105_a(float f, float f1) {
      boolean flag = this.scorpionWidth > 0.0F;
      this.scorpionWidth = f;
      this.scorpionHeight = f1;
      if (!flag) {
         this.rescaleScorpion(1.0F);
      }

   }

   private void rescaleScorpion(float f) {
      super.func_70105_a(this.scorpionWidth * f, this.scorpionHeight * f);
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      if (itemstack != null && itemstack.func_77973_b() == Items.field_151069_bo) {
         --itemstack.field_77994_a;
         if (itemstack.field_77994_a <= 0) {
            entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, new ItemStack(LOTRMod.bottlePoison));
         } else if (!entityplayer.field_71071_by.func_70441_a(new ItemStack(LOTRMod.bottlePoison)) && !entityplayer.field_71075_bZ.field_75098_d) {
            entityplayer.func_71019_a(new ItemStack(LOTRMod.bottlePoison), false);
         }

         return true;
      } else {
         return super.func_70085_c(entityplayer);
      }
   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         if (!this.field_70170_p.field_72995_K) {
            this.setStrikeTime(20);
         }

         if (entity instanceof EntityLivingBase) {
            int difficulty = this.field_70170_p.field_73013_u.func_151525_a();
            int duration = difficulty * (difficulty + 5) / 2;
            if (duration > 0) {
               ((EntityLivingBase)entity).func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, duration * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
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
      int k = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int j = 0; j < k; ++j) {
         this.func_145779_a(Items.field_151078_bh, 1);
      }

   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      int i = this.getScorpionScale();
      return 2 + i + this.field_70146_Z.nextInt(i + 2);
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   public boolean func_70687_e(PotionEffect effect) {
      return effect.func_76456_a() == Potion.field_76436_u.field_76415_H ? false : super.func_70687_e(effect);
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
