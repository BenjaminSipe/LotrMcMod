package lotr.common.entity.animal;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;

public class LOTREntityBear extends EntityAnimal implements LOTRAnimalSpawnConditions {
   private EntityAIBase attackAI = new LOTREntityAIAttackOnCollide(this, 1.7D, false);
   private EntityAIBase panicAI = new EntityAIPanic(this, 1.5D);
   private EntityAIBase targetNearAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);
   private int hostileTick = 0;
   private boolean prevIsChild = true;

   public LOTREntityBear(World world) {
      super(world);
      this.func_70105_a(1.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, this.panicAI);
      this.field_70714_bg.func_75776_a(3, new EntityAIMate(this, 1.0D));
      this.field_70714_bg.func_75776_a(4, new EntityAITempt(this, 1.4D, Items.field_151115_aP, false));
      this.field_70714_bg.func_75776_a(5, new EntityAIFollowParent(this, 1.4D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(0, new EntityAIHurtByTarget(this, false));
      this.field_70715_bh.func_75776_a(1, this.targetNearAI);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(18, (byte)0);
      this.field_70180_af.func_75682_a(20, (byte)0);
      this.setBearType(LOTREntityBear.BearType.forID(this.field_70146_Z.nextInt(LOTREntityBear.BearType.values().length)));
   }

   public LOTREntityBear.BearType getBearType() {
      int i = this.field_70180_af.func_75683_a(18);
      return LOTREntityBear.BearType.forID(i);
   }

   public void setBearType(LOTREntityBear.BearType t) {
      this.field_70180_af.func_75692_b(18, (byte)t.bearID);
   }

   public boolean isHostile() {
      return this.field_70180_af.func_75683_a(20) == 1;
   }

   public void setHostile(boolean flag) {
      this.field_70180_af.func_75692_b(20, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      IEntityLivingData data = super.func_110161_a(data);
      if (data == null) {
         data = new LOTREntityBear.BearGroupSpawnData();
         ((LOTREntityBear.BearGroupSpawnData)data).numSpawned = 1;
      } else if (data instanceof LOTREntityBear.BearGroupSpawnData) {
         LOTREntityBear.BearGroupSpawnData bgsd = (LOTREntityBear.BearGroupSpawnData)data;
         if (bgsd.numSpawned >= 1 && this.field_70146_Z.nextBoolean()) {
            this.func_70873_a(-24000);
         }

         ++bgsd.numSpawned;
      }

      if (this.field_70146_Z.nextInt(10000) == 0) {
         this.func_94058_c("Wojtek");
      }

      return (IEntityLivingData)data;
   }

   public boolean func_70650_aV() {
      return true;
   }

   public void func_70636_d() {
      if (!this.field_70170_p.field_72995_K) {
         boolean isChild = this.func_70631_g_();
         if (isChild != this.prevIsChild) {
            if (isChild) {
               this.field_70714_bg.func_85156_a(this.attackAI);
               this.field_70714_bg.func_75776_a(2, this.panicAI);
               this.field_70715_bh.func_85156_a(this.targetNearAI);
            } else {
               this.field_70714_bg.func_85156_a(this.panicAI);
               if (this.hostileTick > 0) {
                  this.field_70714_bg.func_75776_a(1, this.attackAI);
                  this.field_70715_bh.func_75776_a(1, this.targetNearAI);
               } else {
                  this.field_70714_bg.func_85156_a(this.attackAI);
                  this.field_70715_bh.func_85156_a(this.targetNearAI);
               }
            }
         }
      }

      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.func_70638_az() != null) {
         EntityLivingBase entity = this.func_70638_az();
         if (!entity.func_70089_S() || entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
            this.func_70624_b((EntityLivingBase)null);
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.hostileTick > 0 && this.func_70638_az() == null) {
            --this.hostileTick;
         }

         this.setHostile(this.hostileTick > 0);
         if (this.isHostile()) {
            this.func_70875_t();
         }
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      int furs = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      int rugChance;
      for(rugChance = 0; rugChance < furs; ++rugChance) {
         this.func_145779_a(LOTRMod.fur, 1);
      }

      if (flag) {
         rugChance = 30 - i * 5;
         rugChance = Math.max(rugChance, 1);
         if (this.field_70146_Z.nextInt(rugChance) == 0) {
            this.func_70099_a(new ItemStack(LOTRMod.bearRug, 1, this.getBearType().bearID), 0.0F);
         }
      }

   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 2 + this.field_70170_p.field_73012_v.nextInt(3);
   }

   public boolean func_70652_k(Entity entity) {
      float f = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
      return entity.func_70097_a(DamageSource.func_76358_a(this), f);
   }

   public EntityAgeable func_90011_a(EntityAgeable entity) {
      LOTREntityBear mate = (LOTREntityBear)entity;
      LOTREntityBear child = new LOTREntityBear(this.field_70170_p);
      if (this.field_70146_Z.nextBoolean()) {
         child.setBearType(this.getBearType());
      } else {
         child.setBearType(mate.getBearType());
      }

      return child;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag) {
         Entity attacker = damagesource.func_76346_g();
         if (attacker instanceof EntityLivingBase) {
            if (this.func_70631_g_()) {
               double range = 12.0D;
               List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(range, range, range));
               Iterator var8 = list.iterator();

               while(var8.hasNext()) {
                  Object obj = var8.next();
                  Entity entity = (Entity)obj;
                  if (entity instanceof LOTREntityBear) {
                     LOTREntityBear bear = (LOTREntityBear)entity;
                     if (!bear.func_70631_g_()) {
                        bear.becomeAngryAt((EntityLivingBase)attacker);
                     }
                  }
               }
            } else {
               this.becomeAngryAt((EntityLivingBase)attacker);
            }
         }
      }

      return flag;
   }

   private void becomeAngryAt(EntityLivingBase entity) {
      this.func_70624_b(entity);
      this.hostileTick = 200;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("BearType", (byte)this.getBearType().bearID);
      nbt.func_74768_a("Angry", this.hostileTick);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("BearType")) {
         this.setBearType(LOTREntityBear.BearType.forID(nbt.func_74771_c("BearType")));
      }

      this.hostileTick = nbt.func_74762_e("Angry");
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack.func_77973_b() == Items.field_151115_aP;
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      return this.isHostile() ? false : super.func_70085_c(entityplayer);
   }

   public boolean canWorldGenSpawnAt(int i, int j, int k, LOTRBiome biome, LOTRBiomeVariant variant) {
      int trees = biome.decorator.getVariantTreesPerChunk(variant);
      return trees >= 1;
   }

   public boolean func_70601_bi() {
      WorldChunkManager worldChunkMgr = this.field_70170_p.func_72959_q();
      if (!(worldChunkMgr instanceof LOTRWorldChunkManager)) {
         return super.func_70601_bi();
      } else {
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         LOTRBiome biome = (LOTRBiome)this.field_70170_p.func_72807_a(i, k);
         LOTRBiomeVariant variant = ((LOTRWorldChunkManager)worldChunkMgr).getBiomeVariantAt(i, k);
         return super.func_70601_bi() && this.canWorldGenSpawnAt(i, j, k, biome, variant);
      }
   }

   protected String func_70639_aQ() {
      return "lotr:bear.say";
   }

   protected String func_70621_aR() {
      return "lotr:bear.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:bear.death";
   }

   public int func_70627_aG() {
      return 200;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }

   public static enum BearType {
      LIGHT(0),
      DARK(1),
      BLACK(2);

      public final int bearID;

      private BearType(int i) {
         this.bearID = i;
      }

      public String textureName() {
         return this.name().toLowerCase();
      }

      public static LOTREntityBear.BearType forID(int ID) {
         LOTREntityBear.BearType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTREntityBear.BearType t = var1[var3];
            if (t.bearID == ID) {
               return t;
            }
         }

         return LIGHT;
      }

      public static String[] bearTypeNames() {
         String[] names = new String[values().length];

         for(int i = 0; i < names.length; ++i) {
            names[i] = values()[i].textureName();
         }

         return names;
      }
   }

   private static class BearGroupSpawnData implements IEntityLivingData {
      public int numSpawned;

      private BearGroupSpawnData() {
         this.numSpawned = 0;
      }

      // $FF: synthetic method
      BearGroupSpawnData(Object x0) {
         this();
      }
   }
}
