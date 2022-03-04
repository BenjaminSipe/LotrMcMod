package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIGollumAvoidEntity;
import lotr.common.entity.ai.LOTREntityAIGollumFishing;
import lotr.common.entity.ai.LOTREntityAIGollumFollowOwner;
import lotr.common.entity.ai.LOTREntityAIGollumPanic;
import lotr.common.entity.ai.LOTREntityAIGollumRemainStill;
import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTREntityGollum extends LOTREntityNPC implements LOTRCharacter {
   public static int INV_ROWS = 3;
   private int eatingTick;
   public int prevFishTime = 400;
   public boolean isFishing;
   public LOTRInventoryNPC inventory;
   public int prevFishRequired;
   public int fishRequired;

   public LOTREntityGollum(World world) {
      super(world);
      this.inventory = new LOTRInventoryNPC("gollum", this, INV_ROWS * 9);
      this.prevFishRequired = 20;
      this.fishRequired = this.prevFishRequired;
      this.func_70105_a(0.6F, 1.2F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIGollumRemainStill(this));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIGollumPanic(this, 1.4D));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIGollumAvoidEntity(this, LOTREntityOrc.class, 8.0F, 1.2D, 1.4D));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIGollumAvoidEntity(this, LOTREntityElf.class, 8.0F, 1.2D, 1.4D));
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIGollumFishing(this, 1.5D));
      this.field_70714_bg.func_75776_a(5, new LOTREntityAIGollumFollowOwner(this, 1.2D, 6.0F, 4.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 0.1F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, "");
      this.field_70180_af.func_75682_a(18, (byte)0);
      this.field_70180_af.func_75682_a(19, (byte)0);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public String getGollumOwnerUUID() {
      return this.field_70180_af.func_75681_e(17);
   }

   public void setGollumOwnerUUID(String s) {
      this.field_70180_af.func_75692_b(17, s);
   }

   public EntityPlayer getGollumOwner() {
      try {
         UUID uuid = UUID.fromString(this.getGollumOwnerUUID());
         return uuid == null ? null : this.field_70170_p.func_152378_a(uuid);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public boolean isGollumFleeing() {
      return this.field_70180_af.func_75683_a(18) == 1;
   }

   public void setGollumFleeing(boolean flag) {
      this.field_70180_af.func_75692_b(18, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public boolean isGollumSitting() {
      return this.field_70180_af.func_75683_a(19) == 1;
   }

   public void setGollumSitting(boolean flag) {
      this.field_70180_af.func_75692_b(19, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      this.inventory.writeToNBT(nbt);
      nbt.func_74778_a("GollumOwnerUUID", this.getGollumOwnerUUID());
      nbt.func_74757_a("GollumSitting", this.isGollumSitting());
      nbt.func_74768_a("GollumFishTime", this.prevFishTime);
      nbt.func_74768_a("FishReq", this.fishRequired);
      nbt.func_74768_a("FishReqPrev", this.prevFishRequired);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.inventory.readFromNBT(nbt);
      if (nbt.func_74764_b("GollumOwnerUUID")) {
         this.setGollumOwnerUUID(nbt.func_74779_i("GollumOwnerUUID"));
      }

      this.setGollumSitting(nbt.func_74767_n("GollumSitting"));
      this.prevFishTime = nbt.func_74762_e("GollumFishTime");
      if (nbt.func_74764_b("FishReq")) {
         this.fishRequired = nbt.func_74762_e("FishReq");
         this.prevFishRequired = nbt.func_74762_e("FishReqPrev");
      }

   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.field_70146_Z.nextInt(500) == 0) {
         this.func_70691_i(1.0F);
      }

      if (this.eatingTick > 0) {
         if (this.eatingTick % 4 == 0) {
            this.field_70170_p.func_72956_a(this, "random.eat", 0.5F + 0.5F * (float)this.field_70146_Z.nextInt(2), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         }

         --this.eatingTick;
      }

      if (this.prevFishTime > 0) {
         --this.prevFishTime;
      }

      if (this.isGollumSitting() && !this.field_70170_p.field_72995_K && this.field_70122_E) {
         this.func_70683_ar().func_75660_a();
      }

      if (!this.field_70170_p.field_72995_K && this.func_71124_b(0) != null && this.getGollumOwner() != null) {
         double d = this.func_70068_e(this.getGollumOwner());
         if (d < 4.0D) {
            this.func_70671_ap().func_75651_a(this.getGollumOwner(), 100.0F, 100.0F);
            this.func_70671_ap().func_75649_a();
            EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v, this.func_71124_b(0));
            entityitem.field_145804_b = 40;
            float f = 0.3F;
            entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70759_as / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F) * f);
            entityitem.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70759_as / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F) * f);
            entityitem.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.1415927F) * f + 0.1F);
            f = 0.02F;
            float f1 = this.field_70146_Z.nextFloat() * 3.1415927F * 2.0F;
            f *= this.field_70146_Z.nextFloat();
            entityitem.field_70159_w += Math.cos((double)f1) * (double)f;
            entityitem.field_70181_x += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
            entityitem.field_70179_y += Math.sin((double)f1) * (double)f;
            this.field_70170_p.func_72838_d(entityitem);
            this.func_70062_b(0, (ItemStack)null);
         }
      }

      if (!this.field_70170_p.field_72995_K && StringUtils.func_151246_b(this.getGollumOwnerUUID()) && this.field_70146_Z.nextInt(40) == 0) {
         List nearbyPlayers = this.field_70170_p.func_72872_a(EntityPlayer.class, this.field_70121_D.func_72314_b(80.0D, 80.0D, 80.0D));
         Iterator var2 = nearbyPlayers.iterator();

         while(var2.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer)var2.next();
            double d = (double)this.func_70032_d(entityplayer);
            int chance = (int)(d / 8.0D);
            chance = Math.max(2, chance);
            if (this.field_70146_Z.nextInt(chance) == 0) {
               this.field_70170_p.func_72956_a(entityplayer, this.func_70639_aQ(), this.func_70599_aP(), this.func_70647_i());
            }
         }
      }

   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      if (!this.field_70170_p.field_72995_K) {
         ItemStack itemstack;
         if (this.getGollumOwner() != null && entityplayer == this.getGollumOwner()) {
            itemstack = entityplayer.field_71071_by.func_70448_g();
            if (itemstack != null && itemstack.func_77973_b() instanceof ItemFood && this.canGollumEat(itemstack) && this.func_110143_aJ() < this.func_110138_aP()) {
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  --itemstack.field_77994_a;
                  if (itemstack.field_77994_a <= 0) {
                     entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
                  }
               }

               this.func_70691_i((float)((ItemFood)itemstack.func_77973_b()).func_150905_g(itemstack));
               this.eatingTick = 20;
               return true;
            }

            if (entityplayer.func_70093_af()) {
               entityplayer.openGui(LOTRMod.instance, 10, this.field_70170_p, this.func_145782_y(), 0, 0);
               return true;
            }

            this.setGollumSitting(!this.isGollumSitting());
            if (this.isGollumSitting()) {
               LOTRSpeech.sendSpeech(this.getGollumOwner(), this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/stay", this.getGollumOwner()));
            } else {
               LOTRSpeech.sendSpeech(this.getGollumOwner(), this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/follow", this.getGollumOwner()));
            }

            return true;
         }

         itemstack = entityplayer.field_71071_by.func_70448_g();
         if (itemstack != null && itemstack.func_77973_b() == Items.field_151115_aP) {
            boolean tamed = false;
            if (itemstack.field_77994_a >= this.fishRequired) {
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  itemstack.field_77994_a -= this.fishRequired;
                  if (itemstack.field_77994_a <= 0) {
                     entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
                  }
               }

               this.fishRequired = 0;
            } else {
               this.fishRequired -= itemstack.field_77994_a;
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
               }
            }

            this.eatingTick = 20;
            if (this.fishRequired <= 0) {
               this.setGollumOwnerUUID(entityplayer.func_110124_au().toString());
               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tameGollum);
               LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/tame", entityplayer));
               LOTRSpeech.messageAllPlayers(new ChatComponentTranslation("chat.lotr.tameGollum", new Object[]{entityplayer.func_70005_c_(), this.func_70005_c_()}));
               this.spawnHearts();
               this.fishRequired = Math.round((float)this.prevFishRequired * (1.5F + this.field_70146_Z.nextFloat() * 0.25F));
               this.prevFishRequired = this.fishRequired;
            } else {
               LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/tameProgress", entityplayer));
            }

            return true;
         }
      }

      return super.func_70085_c(entityplayer);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return !this.isGollumFleeing() ? "char/gollum/say" : super.getSpeechBank(entityplayer);
   }

   private boolean canGollumEat(ItemStack itemstack) {
      if (itemstack.func_77973_b() != Items.field_151115_aP && itemstack.func_77973_b() != Items.field_151101_aQ) {
         ItemFood food = (ItemFood)itemstack.func_77973_b();
         return food.func_77845_h();
      } else {
         return true;
      }
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      EntityPlayer owner = this.getGollumOwner();
      if (owner != null && damagesource.func_76346_g() == owner) {
         f = 0.0F;
         if (!this.field_70170_p.field_72995_K) {
            LOTRSpeech.sendSpeech(owner, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/hurt", owner));
         }
      }

      if (super.func_70097_a(damagesource, f)) {
         this.setGollumSitting(false);
         return true;
      } else {
         return false;
      }
   }

   public void func_70645_a(DamageSource damagesource) {
      if (!this.field_70170_p.field_72995_K && !StringUtils.func_151246_b(this.getGollumOwnerUUID())) {
         LOTRSpeech.messageAllPlayers(this.func_110142_aN().func_151521_b());
      }

      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K) {
         this.inventory.dropAllItems();
         LOTRLevelData.setGollumSpawned(false);
      }

   }

   public boolean canDropRares() {
      return false;
   }

   public String func_70639_aQ() {
      return "lotr:gollum.say";
   }

   public String func_70621_aR() {
      return "lotr:gollum.hurt";
   }

   public String func_70673_aS() {
      return "lotr:gollum.death";
   }

   public String func_145777_O() {
      return super.func_145777_O();
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 15) {
         for(int i = 0; i < 4; ++i) {
            double d = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            this.field_70170_p.func_72869_a(this.field_70146_Z.nextBoolean() ? "bubble" : "splash", this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 0.5D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d, d1, d2);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      return false;
   }
}
