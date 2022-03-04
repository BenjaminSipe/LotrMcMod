package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTREntityAIUntamedPanic;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityWarg extends LOTREntityNPCRideable implements IInvBasic {
   private int eatingTick;
   private AnimalChest wargInventory;

   public LOTREntityWarg(World world) {
      super(world);
      this.func_70105_a(1.5F, 1.7F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, this.getWargAttackAI());
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIUntamedPanic(this, 1.2D));
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      int target = this.addTargetTasks(true);
      if (!(this instanceof LOTREntityWargBombardier)) {
         this.field_70715_bh.func_75776_a(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityRabbit.class, 500, false));
         this.field_70715_bh.func_75776_a(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityDeer.class, 1000, false));
      }

      this.isImmuneToFrost = true;
      this.spawnsInDarkness = true;
      this.setupWargInventory();
   }

   public EntityAIBase getWargAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(18, (byte)0);
      this.field_70180_af.func_75682_a(19, (byte)0);
      this.field_70180_af.func_75682_a(20, 0);
      if (this.field_70146_Z.nextInt(500) == 0) {
         this.setWargType(LOTREntityWarg.WargType.WHITE);
      } else if (this.field_70146_Z.nextInt(20) == 0) {
         this.setWargType(LOTREntityWarg.WargType.BLACK);
      } else if (this.field_70146_Z.nextInt(3) == 0) {
         this.setWargType(LOTREntityWarg.WargType.GREY);
      } else {
         this.setWargType(LOTREntityWarg.WargType.BROWN);
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)MathHelper.func_76136_a(this.field_70146_Z, 20, 32));
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(32.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.22D);
      this.func_110148_a(npcAttackDamage).func_111128_a((double)MathHelper.func_76136_a(this.field_70146_Z, 3, 5));
   }

   public boolean isMountSaddled() {
      return this.field_70180_af.func_75683_a(18) == 1;
   }

   public void setWargSaddled(boolean flag) {
      this.field_70180_af.func_75692_b(18, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public LOTREntityWarg.WargType getWargType() {
      int i = this.field_70180_af.func_75683_a(19);
      return LOTREntityWarg.WargType.forID(i);
   }

   public void setWargType(LOTREntityWarg.WargType w) {
      this.field_70180_af.func_75692_b(19, (byte)w.wargID);
   }

   public ItemStack getWargArmorWatched() {
      int ID = this.field_70180_af.func_75679_c(20);
      return new ItemStack(Item.func_150899_d(ID));
   }

   public String getMountArmorTexture() {
      ItemStack armor = this.getWargArmorWatched();
      return armor != null && armor.func_77973_b() instanceof LOTRItemMountArmor ? ((LOTRItemMountArmor)armor.func_77973_b()).getArmorTexture() : null;
   }

   private void setWargArmorWatched(ItemStack itemstack) {
      if (itemstack == null) {
         this.field_70180_af.func_75692_b(20, 0);
      } else {
         this.field_70180_af.func_75692_b(20, Item.func_150891_b(itemstack.func_77973_b()));
      }

   }

   public IInventory getMountInventory() {
      return this.wargInventory;
   }

   public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
      data = super.func_110161_a(data);
      return data;
   }

   public abstract LOTREntityNPC createWargRider();

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (!this.field_70170_p.field_72995_K && this.canWargBeRidden() && this.field_70146_Z.nextInt(3) == 0) {
         LOTREntityNPC rider = this.createWargRider();
         rider.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
         rider.func_110161_a((IEntityLivingData)null);
         rider.isNPCPersistent = this.isNPCPersistent;
         this.field_70170_p.func_72838_d(rider);
         rider.func_70078_a(this);
      }

      return data;
   }

   public boolean canWargBeRidden() {
      return true;
   }

   public boolean getBelongsToNPC() {
      return false;
   }

   public void setBelongsToNPC(boolean flag) {
   }

   private void setupWargInventory() {
      AnimalChest prevInv = this.wargInventory;
      this.wargInventory = new AnimalChest("WargInv", 2);
      this.wargInventory.func_110133_a(this.func_70005_c_());
      if (prevInv != null) {
         prevInv.func_110132_b(this);
         int invSize = Math.min(prevInv.func_70302_i_(), this.wargInventory.func_70302_i_());

         for(int slot = 0; slot < invSize; ++slot) {
            ItemStack itemstack = prevInv.func_70301_a(slot);
            if (itemstack != null) {
               this.wargInventory.func_70299_a(slot, itemstack.func_77946_l());
            }
         }

         prevInv = null;
      }

      this.wargInventory.func_110134_a(this);
      this.checkWargInventory();
   }

   private void checkWargInventory() {
      if (!this.field_70170_p.field_72995_K) {
         this.setWargSaddled(this.wargInventory.func_70301_a(0) != null);
         this.setWargArmorWatched(this.getWargArmor());
      }

   }

   public void func_76316_a(InventoryBasic inv) {
      boolean prevSaddled = this.isMountSaddled();
      ItemStack prevArmor = this.getWargArmorWatched();
      this.checkWargInventory();
      ItemStack wargArmor = this.getWargArmorWatched();
      if (this.field_70173_aa > 20) {
         if (!prevSaddled && this.isMountSaddled()) {
            this.func_85030_a("mob.horse.leather", 0.5F, 1.0F);
         }

         if (!ItemStack.func_77989_b(prevArmor, wargArmor)) {
            this.func_85030_a("mob.horse.armor", 0.5F, 1.0F);
         }
      }

   }

   public void setWargArmor(ItemStack itemstack) {
      this.wargInventory.func_70299_a(1, itemstack);
      this.setupWargInventory();
      this.setWargArmorWatched(this.getWargArmor());
   }

   public ItemStack getWargArmor() {
      return this.wargInventory.func_70301_a(1);
   }

   public int func_70658_aO() {
      ItemStack itemstack = this.getWargArmor();
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemMountArmor) {
         LOTRItemMountArmor armor = (LOTRItemMountArmor)itemstack.func_77973_b();
         return armor.getDamageReduceAmount();
      } else {
         return 0;
      }
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("WargType", (byte)this.getWargType().wargID);
      if (this.wargInventory.func_70301_a(0) != null) {
         nbt.func_74782_a("WargSaddleItem", this.wargInventory.func_70301_a(0).func_77955_b(new NBTTagCompound()));
      }

      if (this.getWargArmor() != null) {
         nbt.func_74782_a("WargArmorItem", this.getWargArmor().func_77955_b(new NBTTagCompound()));
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setWargType(LOTREntityWarg.WargType.forID(nbt.func_74771_c("WargType")));
      ItemStack wargArmor;
      if (nbt.func_74764_b("WargSaddleItem")) {
         wargArmor = ItemStack.func_77949_a(nbt.func_74775_l("WargSaddleItem"));
         if (wargArmor != null && wargArmor.func_77973_b() == Items.field_151141_av) {
            this.wargInventory.func_70299_a(0, wargArmor);
         }
      } else if (nbt.func_74767_n("Saddled")) {
         this.wargInventory.func_70299_a(0, new ItemStack(Items.field_151141_av));
      }

      if (nbt.func_74764_b("WargArmorItem")) {
         wargArmor = ItemStack.func_77949_a(nbt.func_74775_l("WargArmorItem"));
         if (wargArmor != null && this.isMountArmorValid(wargArmor)) {
            this.wargInventory.func_70299_a(1, wargArmor);
         }
      }

      this.checkWargInventory();
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.field_70153_n instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)this.field_70153_n;
         if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) < 50.0F) {
            entityplayer.func_70078_a((Entity)null);
         } else if (this.isNPCTamed() && this.isMountSaddled()) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideWarg);
         }
      }

      if (this.eatingTick > 0) {
         if (this.eatingTick % 4 == 0) {
            this.field_70170_p.func_72956_a(this, "random.eat", 0.5F + 0.5F * (float)this.field_70146_Z.nextInt(2), 0.4F + this.field_70146_Z.nextFloat() * 0.2F);
         }

         --this.eatingTick;
      }

   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      if (!this.field_70170_p.field_72995_K && !this.hiredNPCInfo.isActive) {
         if (LOTRMountFunctions.interact(this, entityplayer)) {
            return true;
         } else {
            if (this.func_70638_az() != entityplayer) {
               boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0F;
               boolean notifyNotEnoughAlignment = false;
               ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
               if (!notifyNotEnoughAlignment && this.isNPCTamed() && entityplayer.func_70093_af()) {
                  if (hasRequiredAlignment) {
                     this.openGUI(entityplayer);
                     return true;
                  }

                  notifyNotEnoughAlignment = true;
               }

               if (!notifyNotEnoughAlignment && this.isNPCTamed() && itemstack != null && itemstack.func_77973_b() instanceof ItemFood && ((ItemFood)itemstack.func_77973_b()).func_77845_h() && this.func_110143_aJ() < this.func_110138_aP()) {
                  if (hasRequiredAlignment) {
                     if (!entityplayer.field_71075_bZ.field_75098_d) {
                        --itemstack.field_77994_a;
                        if (itemstack.field_77994_a == 0) {
                           entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
                        }
                     }

                     this.func_70691_i((float)((ItemFood)itemstack.func_77973_b()).func_150905_g(itemstack));
                     this.eatingTick = 20;
                     return true;
                  }

                  notifyNotEnoughAlignment = true;
               }

               if (!notifyNotEnoughAlignment && this.isNPCTamed() && !this.isMountSaddled() && this.canWargBeRidden() && this.field_70153_n == null && itemstack != null && itemstack.func_77973_b() == Items.field_151141_av) {
                  if (hasRequiredAlignment) {
                     this.openGUI(entityplayer);
                     return true;
                  }

                  notifyNotEnoughAlignment = true;
               }

               if (!notifyNotEnoughAlignment && !this.func_70631_g_() && this.canWargBeRidden() && this.field_70153_n == null) {
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

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int furs = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      int bones;
      for(bones = 0; bones < furs; ++bones) {
         this.func_145779_a(LOTRMod.fur, 1);
      }

      bones = 2 + this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      int rugChance;
      for(rugChance = 0; rugChance < bones; ++rugChance) {
         this.func_145779_a(LOTRMod.wargBone, 1);
      }

      if (flag) {
         rugChance = 50 - i * 8;
         rugChance = Math.max(rugChance, 1);
         if (this.field_70146_Z.nextInt(rugChance) == 0) {
            this.func_70099_a(new ItemStack(LOTRMod.wargskinRug, 1, this.getWargType().wargID), 0.0F);
         }
      }

   }

   public boolean canDropRares() {
      return false;
   }

   public String func_70639_aQ() {
      return "lotr:warg.say";
   }

   public String func_70621_aR() {
      return "lotr:warg.hurt";
   }

   public String func_70673_aS() {
      return "lotr:warg.death";
   }

   public String getAttackSound() {
      return "lotr:warg.attack";
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K) {
         if (this.getBelongsToNPC()) {
            this.wargInventory.func_70299_a(0, (ItemStack)null);
            this.wargInventory.func_70299_a(1, (ItemStack)null);
         }

         if (this.isNPCTamed()) {
            this.setWargSaddled(false);
            this.func_145779_a(Items.field_151141_av, 1);
            ItemStack wargArmor = this.getWargArmor();
            if (wargArmor != null) {
               this.func_70099_a(wargArmor, 0.0F);
               this.setWargArmor((ItemStack)null);
            }
         }
      }

   }

   public float getTailRotation() {
      float f = (this.func_110138_aP() - this.func_110143_aJ()) / this.func_110138_aP();
      return f * -1.2F;
   }

   public boolean func_110164_bC() {
      return this.isNPCTamed();
   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      return false;
   }

   public static enum WargType {
      BROWN(0),
      GREY(1),
      BLACK(2),
      WHITE(3),
      ICE(4),
      OBSIDIAN(5),
      FIRE(6);

      public final int wargID;

      private WargType(int i) {
         this.wargID = i;
      }

      public String textureName() {
         return this.name().toLowerCase();
      }

      public static LOTREntityWarg.WargType forID(int ID) {
         LOTREntityWarg.WargType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTREntityWarg.WargType w = var1[var3];
            if (w.wargID == ID) {
               return w;
            }
         }

         return BROWN;
      }

      public static String[] wargTypeNames() {
         String[] names = new String[values().length];

         for(int i = 0; i < names.length; ++i) {
            names[i] = values()[i].textureName();
         }

         return names;
      }
   }
}
