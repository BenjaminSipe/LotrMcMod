package lotr.common.entity.animal;

import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.ai.LOTREntityAIHiredHorseRemainStill;
import lotr.common.entity.ai.LOTREntityAIHorseFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHorseMoveToRiderTarget;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.item.LOTRItemMountArmor;
import lotr.common.world.biome.LOTRBiomeGenDorEnErnil;
import lotr.common.world.biome.LOTRBiomeGenRohan;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityHorse extends EntityHorse implements LOTRNPCMount {
   private boolean isMoving;
   private ItemStack prevMountArmor;
   private EntityAIBase attackAI;
   private EntityAIBase panicAI;
   private boolean prevIsChild = true;

   public LOTREntityHorse(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(0, new LOTREntityAIHiredHorseRemainStill(this));
      this.field_70714_bg.func_75776_a(0, new LOTREntityAIHorseMoveToRiderTarget(this));
      this.field_70714_bg.func_75776_a(0, new LOTREntityAIHorseFollowHiringPlayer(this));
      EntityAITaskEntry panic = LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
      this.field_70714_bg.func_75776_a(panic.field_75731_b, panic.field_75733_a);
      this.panicAI = panic.field_75733_a;
      this.attackAI = this.createMountAttackAI();
      if (this.isMountHostile()) {
         this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
      }

   }

   protected EntityAIBase createMountAttackAI() {
      return null;
   }

   protected boolean isMountHostile() {
      return false;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(25, (byte)0);
      this.field_70180_af.func_75682_a(26, (byte)1);
      this.field_70180_af.func_75682_a(27, 0);
      this.field_70180_af.func_75682_a(28, (byte)0);
      this.field_70180_af.func_75682_a(29, (byte)0);
      this.field_70180_af.func_75682_a(30, Short.valueOf((short)0));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      if (this.isMountHostile()) {
         this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
      }

   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      if (!this.field_70170_p.field_72995_K) {
         data = super.func_110161_a(data);
         this.onLOTRHorseSpawn();
         this.func_70606_j(this.func_110138_aP());
         return data;
      } else {
         int j = this.field_70146_Z.nextInt(7);
         int k = this.field_70146_Z.nextInt(5);
         int i = j | k << 8;
         this.func_110235_q(i);
         return data;
      }
   }

   protected void onLOTRHorseSpawn() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (this.getClass() == LOTREntityHorse.class) {
         float healthBoost = 0.0F;
         float speedBoost = 0.0F;
         float jumpAdd = 0.0F;
         if (biome instanceof LOTRBiomeGenRohan) {
            healthBoost = 0.5F;
            speedBoost = 0.3F;
            jumpAdd = 0.2F;
         }

         if (biome instanceof LOTRBiomeGenDorEnErnil) {
            healthBoost = 0.3F;
            speedBoost = 0.2F;
            jumpAdd = 0.1F;
         }

         double jumpStrength;
         if (healthBoost > 0.0F) {
            jumpStrength = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
            jumpStrength *= (double)(1.0F + this.field_70146_Z.nextFloat() * healthBoost);
            this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(jumpStrength);
            this.func_70606_j(this.func_110138_aP());
         }

         if (speedBoost > 0.0F) {
            jumpStrength = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
            jumpStrength *= (double)(1.0F + this.field_70146_Z.nextFloat() * speedBoost);
            this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(jumpStrength);
         }

         jumpStrength = this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111126_e();
         double jumpLimit = Math.max(jumpStrength, 1.0D);
         if (jumpAdd > 0.0F) {
            jumpStrength += (double)jumpAdd;
         }

         jumpStrength = Math.min(jumpStrength, jumpLimit);
         this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111128_a(jumpStrength);
      }

   }

   public boolean getBelongsToNPC() {
      return this.field_70180_af.func_75683_a(25) == 1;
   }

   public void setBelongsToNPC(boolean flag) {
      this.field_70180_af.func_75692_b(25, Byte.valueOf((byte)(flag ? 1 : 0)));
      if (flag) {
         this.func_110234_j(true);
         this.func_110251_o(true);
         if (this.func_70874_b() < 0) {
            this.func_70873_a(0);
         }

         if (this.getClass() == LOTREntityHorse.class) {
            this.func_110214_p(0);
         }
      }

   }

   public boolean getMountable() {
      return this.field_70180_af.func_75683_a(26) == 1;
   }

   public void setMountable(boolean flag) {
      this.field_70180_af.func_75692_b(26, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public ItemStack getMountArmor() {
      int ID = this.field_70180_af.func_75679_c(27);
      int meta = this.field_70180_af.func_75683_a(28);
      return new ItemStack(Item.func_150899_d(ID), 1, meta);
   }

   public String getMountArmorTexture() {
      ItemStack armor = this.getMountArmor();
      return armor != null && armor.func_77973_b() instanceof LOTRItemMountArmor ? ((LOTRItemMountArmor)armor.func_77973_b()).getArmorTexture() : null;
   }

   private void setMountArmorWatched(ItemStack itemstack) {
      if (itemstack == null) {
         this.field_70180_af.func_75692_b(27, 0);
         this.field_70180_af.func_75692_b(28, (byte)0);
      } else {
         this.field_70180_af.func_75692_b(27, Item.func_150891_b(itemstack.func_77973_b()));
         this.field_70180_af.func_75692_b(28, (byte)itemstack.func_77960_j());
      }

   }

   public boolean isMountEnraged() {
      return this.field_70180_af.func_75683_a(29) == 1;
   }

   public void setMountEnraged(boolean flag) {
      this.field_70180_af.func_75692_b(29, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public int getTicksSinceFeed() {
      return this.field_70180_af.func_75693_b(30);
   }

   public void setTicksSinceFeed(int ticks) {
      this.field_70180_af.func_75692_b(30, (short)ticks);
   }

   public boolean isMountSaddled() {
      return this.func_110257_ck();
   }

   public boolean func_110257_ck() {
      return (!this.isMoving || !this.getBelongsToNPC()) && super.func_110257_ck();
   }

   public void saddleMountForWorldGen() {
      this.func_70873_a(0);
      LOTRReflection.getHorseInv(this).func_70299_a(0, new ItemStack(Items.field_151141_av));
      LOTRReflection.setupHorseInv(this);
      this.func_110234_j(true);
   }

   public void setChestedForWorldGen() {
      this.func_110207_m(true);
      LOTRReflection.setupHorseInv(this);
   }

   public void setMountArmor(ItemStack itemstack) {
      LOTRReflection.getHorseInv(this).func_70299_a(1, itemstack);
      LOTRReflection.setupHorseInv(this);
      this.setMountArmorWatched(itemstack);
   }

   public boolean isMountArmorValid(ItemStack itemstack) {
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemMountArmor) {
         LOTRItemMountArmor armor = (LOTRItemMountArmor)itemstack.func_77973_b();
         return armor.isValid(this);
      } else {
         return false;
      }
   }

   public int func_70658_aO() {
      ItemStack itemstack = LOTRReflection.getHorseInv(this).func_70301_a(1);
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemMountArmor) {
         LOTRItemMountArmor armor = (LOTRItemMountArmor)itemstack.func_77973_b();
         return armor.getDamageReduceAmount();
      } else {
         return 0;
      }
   }

   public void func_70636_d() {
      if (!this.field_70170_p.field_72995_K) {
         ItemStack armor = LOTRReflection.getHorseInv(this).func_70301_a(1);
         if (this.field_70173_aa > 20 && !ItemStack.func_77989_b(this.prevMountArmor, armor)) {
            this.func_85030_a("mob.horse.armor", 0.5F, 1.0F);
         }

         this.prevMountArmor = armor;
         this.setMountArmorWatched(armor);
      }

      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.getTicksSinceFeed() > 0) {
         this.setTicksSinceFeed(this.getTicksSinceFeed() - 1);
      }

      if (!this.field_70170_p.field_72995_K && this.field_70153_n instanceof EntityPlayer && this.func_70090_H() && this.field_70181_x < 0.0D) {
         AxisAlignedBB swimCheckBox = this.field_70121_D.func_72329_c().func_72321_a(0.0D, -1.0D, 0.0D);
         if (this.field_70170_p.func_147461_a(swimCheckBox).isEmpty() && this.field_70146_Z.nextFloat() < 0.55F) {
            this.field_70181_x += 0.05D;
            this.field_70160_al = true;
         }
      }

      if (!this.field_70170_p.field_72995_K && this.isMountHostile()) {
         boolean isChild = this.func_70631_g_();
         if (isChild != this.prevIsChild) {
            EntityAITaskEntry taskEntry;
            if (isChild) {
               taskEntry = LOTREntityUtils.removeAITask(this, this.attackAI.getClass());
               this.field_70714_bg.func_75776_a(taskEntry.field_75731_b, this.panicAI);
            } else {
               taskEntry = LOTREntityUtils.removeAITask(this, this.panicAI.getClass());
               this.field_70714_bg.func_75776_a(taskEntry.field_75731_b, this.attackAI);
            }
         }

         EntityLivingBase target;
         if (this.func_70638_az() != null) {
            target = this.func_70638_az();
            if (!target.func_70089_S() || target instanceof EntityPlayer && ((EntityPlayer)target).field_71075_bZ.field_75098_d) {
               this.func_70624_b((EntityLivingBase)null);
            }
         }

         if (this.field_70153_n instanceof EntityLiving) {
            target = ((EntityLiving)this.field_70153_n).func_70638_az();
            this.func_70624_b(target);
         } else if (this.field_70153_n instanceof EntityPlayer) {
            this.func_70624_b((EntityLivingBase)null);
         }

         this.setMountEnraged(this.func_70638_az() != null);
      }

      this.prevIsChild = this.func_70631_g_();
   }

   protected boolean func_70610_aX() {
      this.isMoving = true;
      boolean flag = super.func_70610_aX();
      this.isMoving = false;
      return flag;
   }

   public void func_70612_e(float f, float f1) {
      this.isMoving = true;
      super.func_70612_e(f, f1);
      this.isMoving = false;
   }

   public void super_moveEntityWithHeading(float strafe, float forward) {
      super.func_70612_e(strafe, forward);
   }

   public float getStepHeightWhileRiddenByPlayer() {
      return 1.0F;
   }

   public float func_70783_a(int i, int j, int k) {
      return this.getBelongsToNPC() && this.field_70153_n instanceof LOTREntityNPC ? ((LOTREntityNPC)this.field_70153_n).func_70783_a(i, j, k) : super.func_70783_a(i, j, k);
   }

   public double func_70042_X() {
      double d = (double)this.field_70131_O * 0.5D;
      if (this.field_70153_n != null) {
         d += (double)this.field_70153_n.field_70129_M - this.field_70153_n.func_70033_W();
      }

      return d;
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack != null && LOTRMod.isOreNameEqual(itemstack, "apple");
   }

   public EntityAgeable func_90011_a(EntityAgeable otherParent) {
      EntityHorse superChild = (EntityHorse)super.func_90011_a(otherParent);
      LOTREntityHorse child = (LOTREntityHorse)EntityList.func_75620_a(LOTREntities.getStringFromClass(this.getClass()), this.field_70170_p);
      child.func_110214_p(superChild.func_110265_bP());
      child.func_110235_q(superChild.func_110202_bQ());
      double maxHealth = this.getChildAttribute(this, otherParent, SharedMonsterAttributes.field_111267_a, 3.0D);
      maxHealth = this.clampChildHealth(maxHealth);
      child.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(maxHealth);
      child.func_70606_j(child.func_110138_aP());
      double jumpStrength = this.getChildAttribute(this, otherParent, LOTRReflection.getHorseJumpStrength(), 0.1D);
      jumpStrength = this.clampChildJump(jumpStrength);
      child.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111128_a(jumpStrength);
      double moveSpeed = this.getChildAttribute(this, otherParent, SharedMonsterAttributes.field_111263_d, 0.03D);
      moveSpeed = this.clampChildSpeed(moveSpeed);
      child.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(moveSpeed);
      if (this.func_110248_bS() && ((LOTREntityHorse)otherParent).func_110248_bS()) {
         child.func_110234_j(true);
      }

      return child;
   }

   private double getChildAttribute(EntityAgeable parent, EntityAgeable otherParent, IAttribute stat, double variance) {
      double val1 = parent.func_110148_a(stat).func_111125_b();
      double val2 = otherParent.func_110148_a(stat).func_111125_b();
      return val1 <= val2 ? MathHelper.func_82716_a(this.field_70146_Z, val1 - variance, val2 + variance) : MathHelper.func_82716_a(this.field_70146_Z, val2 - variance, val1 + variance);
   }

   protected double clampChildHealth(double health) {
      return MathHelper.func_151237_a(health, 12.0D, 48.0D);
   }

   protected double clampChildJump(double jump) {
      return MathHelper.func_151237_a(jump, 0.3D, 1.0D);
   }

   protected double clampChildSpeed(double speed) {
      return MathHelper.func_151237_a(speed, 0.08D, 0.45D);
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      if (!this.getMountable()) {
         return false;
      } else if (this.isMountEnraged()) {
         return false;
      } else if (this.getBelongsToNPC()) {
         if (this.field_70153_n == null) {
            if (!this.field_70170_p.field_72995_K) {
               entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.mountOwnedByNPC", new Object[0]));
            }

            return true;
         } else {
            return false;
         }
      } else {
         ItemStack itemstack = entityplayer.func_70694_bm();
         if (itemstack != null && this.func_70877_b(itemstack) && this.func_70874_b() == 0 && !this.func_70880_s() && this.func_110248_bS()) {
            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --itemstack.field_77994_a;
               if (itemstack.field_77994_a <= 0) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
               }
            }

            this.func_146082_f(entityplayer);
            return true;
         } else {
            boolean isHorseHealItem = this.isHorseHealItem(itemstack);
            if (isHorseHealItem && this.getTicksSinceFeed() > 0 && !this.func_110256_cu() && this.func_110143_aJ() < this.func_110138_aP()) {
               return false;
            } else {
               boolean prevInLove = this.func_70880_s();
               float prevHealth = this.func_110143_aJ();
               boolean successfulSuperInteract = super.func_70085_c(entityplayer);
               if (this.func_70880_s() && !prevInLove) {
                  this.func_70875_t();
               }

               float newHealth = this.func_110143_aJ();
               if (newHealth > prevHealth) {
                  if (!this.field_70170_p.field_72995_K) {
                     float healthGain = newHealth - prevHealth;
                     this.setTicksSinceFeed((int)(healthGain * 20.0F));
                  }

                  this.field_70170_p.func_72956_a(this, "random.eat", 1.0F, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
               }

               return successfulSuperInteract;
            }
         }
      }
   }

   private boolean isHorseHealItem(ItemStack itemstack) {
      if (itemstack == null) {
         return false;
      } else {
         Item item = itemstack.func_77973_b();
         return item == Items.field_151015_O || item == Items.field_151102_aT || item == Items.field_151025_P || Block.func_149634_a(item) == Blocks.field_150407_cf || item == Items.field_151034_e || item == Items.field_151150_bK || item == Items.field_151153_ao;
      }
   }

   public boolean func_70652_k(Entity entity) {
      float f = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
      boolean flag = entity.func_70097_a(DamageSource.func_76358_a(this), f);
      return flag;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag && this.func_70631_g_() && this.isMountHostile()) {
         Entity attacker = damagesource.func_76346_g();
         if (attacker instanceof EntityLivingBase) {
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(12.0D, 12.0D, 12.0D));

            for(int i = 0; i < list.size(); ++i) {
               Entity entity = (Entity)list.get(i);
               if (entity.getClass() == this.getClass()) {
                  LOTREntityHorse mount = (LOTREntityHorse)entity;
                  if (!mount.func_70631_g_() && !mount.func_110248_bS()) {
                     mount.func_70624_b((EntityLivingBase)attacker);
                  }
               }
            }
         }
      }

      return flag;
   }

   public void func_110199_f(EntityPlayer entityplayer) {
      if (!this.field_70170_p.field_72995_K && (this.field_70153_n == null || this.field_70153_n == entityplayer) && this.func_110248_bS()) {
         AnimalChest animalchest = LOTRReflection.getHorseInv(this);
         animalchest.func_110133_a(this.func_70005_c_());
         entityplayer.openGui(LOTRMod.instance, 29, this.field_70170_p, this.func_145782_y(), animalchest.func_70302_i_(), 0);
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("BelongsNPC", this.getBelongsToNPC());
      nbt.func_74757_a("Mountable", this.getMountable());
      AnimalChest inv = LOTRReflection.getHorseInv(this);
      if (inv.func_70301_a(1) != null) {
         nbt.func_74782_a("LOTRMountArmorItem", inv.func_70301_a(1).func_77955_b(new NBTTagCompound()));
      }

      nbt.func_74777_a("TicksSinceFeed", (short)this.getTicksSinceFeed());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      boolean pre35 = false;
      if (nbt.func_74764_b("BelongsToNPC")) {
         pre35 = true;
         this.setBelongsToNPC(nbt.func_74767_n("BelongsToNPC"));
      } else {
         this.setBelongsToNPC(nbt.func_74767_n("BelongsNPC"));
      }

      if (nbt.func_74764_b("Mountable")) {
         this.setMountable(nbt.func_74767_n("Mountable"));
      }

      AnimalChest inv = LOTRReflection.getHorseInv(this);
      if (nbt.func_74764_b("LOTRMountArmorItem")) {
         ItemStack armor = ItemStack.func_77949_a(nbt.func_74775_l("LOTRMountArmorItem"));
         if (armor != null && this.isMountArmorValid(armor)) {
            inv.func_70299_a(1, armor);
         }
      }

      this.setTicksSinceFeed(nbt.func_74765_d("TicksSinceFeed"));
      if (pre35) {
         double jumpStrength = this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111126_e();
         if (jumpStrength > 1.0D) {
            System.out.println("Reducing horse jump strength from " + jumpStrength);
            jumpStrength = 1.0D;
            this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111128_a(jumpStrength);
            System.out.println("Jump strength now " + this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111126_e());
         }
      }

   }

   public boolean func_70692_ba() {
      return this.getBelongsToNPC() && this.field_70153_n == null;
   }

   public void func_70645_a(DamageSource damagesource) {
      if (this.getBelongsToNPC()) {
         AnimalChest inv = LOTRReflection.getHorseInv(this);
         inv.func_70299_a(0, (ItemStack)null);
         inv.func_70299_a(1, (ItemStack)null);
      }

      super.func_70645_a(damagesource);
   }

   public String func_70005_c_() {
      if (this.getClass() == LOTREntityHorse.class) {
         return super.func_70005_c_();
      } else if (this.func_94056_bM()) {
         return this.func_94057_bL();
      } else {
         String s = EntityList.func_75621_b(this);
         return StatCollector.func_74838_a("entity." + s + ".name");
      }
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }

   public boolean func_110164_bC() {
      return this.getBelongsToNPC() ? false : super.func_110164_bC();
   }

   public boolean shouldDismountInWater(Entity rider) {
      return false;
   }
}
