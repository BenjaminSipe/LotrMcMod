package lotr.common.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.entity.npc.LOTRTraderNPCInfo;
import net.minecraft.block.Block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityTraderRespawn extends Entity {
   private static int MAX_SCALE = 40;
   private int timeUntilSpawn;
   private int prevBobbingTime;
   private int bobbingTime;
   private String traderClassID;
   private boolean traderHasHome;
   private int traderHomeX;
   private int traderHomeY;
   private int traderHomeZ;
   private float traderHomeRadius;
   private String traderLocationName;
   private boolean shouldTraderRespawn;
   private NBTTagCompound traderData;
   public float spawnerSpin;
   public float prevSpawnerSpin;

   public LOTREntityTraderRespawn(World world) {
      super(world);
      this.func_70105_a(0.75F, 0.75F);
      this.spawnerSpin = this.field_70146_Z.nextFloat() * 360.0F;
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(16, 0);
      this.field_70180_af.func_75682_a(17, (byte)0);
      this.field_70180_af.func_75682_a(18, "");
   }

   public int getScale() {
      return this.field_70180_af.func_75679_c(16);
   }

   public void setScale(int i) {
      this.field_70180_af.func_75692_b(16, i);
   }

   public boolean isSpawnImminent() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setSpawnImminent() {
      this.field_70180_af.func_75692_b(17, (byte)1);
   }

   public String getClientTraderString() {
      return this.field_70180_af.func_75681_e(18);
   }

   public void setClientTraderString(String s) {
      this.field_70180_af.func_75692_b(18, s);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      nbt.func_74768_a("Scale", this.getScale());
      nbt.func_74768_a("TimeUntilSpawn", this.timeUntilSpawn);
      nbt.func_74778_a("TraderClassID", this.traderClassID);
      nbt.func_74757_a("TraderHasHome", this.traderHasHome);
      nbt.func_74768_a("TraderHomeX", this.traderHomeX);
      nbt.func_74768_a("TraderHomeY", this.traderHomeY);
      nbt.func_74768_a("TraderHomeZ", this.traderHomeZ);
      nbt.func_74776_a("TraderHomeRadius", this.traderHomeRadius);
      nbt.func_74757_a("TraderShouldRespawn", this.shouldTraderRespawn);
      if (this.traderLocationName != null) {
         nbt.func_74778_a("TraderLocationName", this.traderLocationName);
      }

      if (this.traderData != null) {
         nbt.func_74782_a("TraderData", this.traderData);
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      this.setScale(nbt.func_74762_e("Scale"));
      this.timeUntilSpawn = nbt.func_74762_e("TimeUntilSpawn");
      if (this.timeUntilSpawn <= 1200) {
         this.setSpawnImminent();
      }

      this.traderClassID = nbt.func_74779_i("TraderClassID");
      this.traderHasHome = nbt.func_74767_n("TraderHasHome");
      this.traderHomeX = nbt.func_74762_e("TraderHomeX");
      this.traderHomeY = nbt.func_74762_e("TraderHomeY");
      this.traderHomeZ = nbt.func_74762_e("TraderHomeZ");
      this.traderHomeRadius = nbt.func_74760_g("TraderHomeRadius");
      if (nbt.func_74764_b("TraderShouldRespawn")) {
         this.shouldTraderRespawn = nbt.func_74767_n("TraderShouldRespawn");
      } else {
         this.shouldTraderRespawn = true;
      }

      if (nbt.func_74764_b("TraderLocationName")) {
         this.traderLocationName = nbt.func_74779_i("TraderLocationName");
      }

      if (nbt.func_74764_b("TraderData")) {
         this.traderData = nbt.func_74775_l("TraderData");
      }

   }

   public void copyTraderDataFrom(LOTREntityNPC entity) {
      this.traderClassID = LOTREntities.getStringFromClass(entity.getClass());
      this.traderHasHome = entity.func_110175_bO();
      if (this.traderHasHome) {
         ChunkCoordinates home = entity.func_110172_bL();
         this.traderHomeX = home.field_71574_a;
         this.traderHomeY = home.field_71572_b;
         this.traderHomeZ = home.field_71573_c;
         this.traderHomeRadius = entity.func_110174_bM();
      }

      this.shouldTraderRespawn = entity.shouldTraderRespawn();
      if (entity.getHasSpecificLocationName()) {
         this.traderLocationName = entity.npcLocationName;
      }

      if (entity instanceof LOTRTradeable) {
         LOTRTraderNPCInfo traderInfo = entity.traderNPCInfo;
         this.traderData = new NBTTagCompound();
         traderInfo.writeToNBT(this.traderData);
      }

   }

   public void onSpawn() {
      this.field_70181_x = 0.25D;
      this.timeUntilSpawn = MathHelper.func_76136_a(this.field_70146_Z, 10, 30) * 1200;
   }

   public boolean func_70067_L() {
      return true;
   }

   public void func_70108_f(Entity entity) {
   }

   public boolean func_85031_j(Entity entity) {
      return entity instanceof EntityPlayer ? this.func_70097_a(DamageSource.func_76365_a((EntityPlayer)entity), 0.0F) : false;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      Entity entity = damagesource.func_76346_g();
      if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
         if (!this.field_70170_p.field_72995_K) {
            SoundType sound = Blocks.field_150359_w.field_149762_H;
            this.field_70170_p.func_72956_a(this, sound.func_150495_a(), (sound.func_150497_c() + 1.0F) / 2.0F, sound.func_150494_d() * 0.8F);
            this.field_70170_p.func_72960_a(this, (byte)16);
            this.func_70106_y();
         }

         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 16) {
         for(int l = 0; l < 16; ++l) {
            this.field_70170_p.func_72869_a("iconcrack_" + Item.func_150891_b(LOTRMod.silverCoin), this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.prevSpawnerSpin = this.spawnerSpin;
      if (this.isSpawnImminent()) {
         this.spawnerSpin += 24.0F;
      } else {
         this.spawnerSpin += 6.0F;
      }

      this.prevSpawnerSpin = MathHelper.func_76142_g(this.prevSpawnerSpin);
      this.spawnerSpin = MathHelper.func_76142_g(this.spawnerSpin);
      if (this.getScale() < MAX_SCALE) {
         if (!this.field_70170_p.field_72995_K) {
            this.setScale(this.getScale() + 1);
         }

         this.field_70159_w = 0.0D;
         this.field_70181_x *= 0.9D;
         this.field_70179_y = 0.0D;
      } else {
         this.field_70159_w = 0.0D;
         this.field_70181_x = 0.0D;
         this.field_70179_y = 0.0D;
      }

      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      if (!this.field_70170_p.field_72995_K) {
         this.setClientTraderString(this.traderClassID);
         if (!this.isSpawnImminent() && this.timeUntilSpawn <= 1200) {
            this.setSpawnImminent();
         }

         if (this.timeUntilSpawn > 0) {
            --this.timeUntilSpawn;
         } else {
            boolean flag = false;
            Entity entity = EntityList.func_75620_a(this.traderClassID, this.field_70170_p);
            if (entity != null && entity instanceof LOTREntityNPC) {
               LOTREntityNPC trader = (LOTREntityNPC)entity;
               trader.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
               trader.spawnRidingHorse = false;
               trader.liftSpawnRestrictions = true;
               this.field_70121_D.func_72317_d(0.0D, 100.0D, 0.0D);
               if (trader.func_70601_bi()) {
                  trader.liftSpawnRestrictions = false;
                  trader.func_110161_a((IEntityLivingData)null);
                  if (this.traderHasHome) {
                     trader.func_110171_b(this.traderHomeX, this.traderHomeY, this.traderHomeZ, Math.round(this.traderHomeRadius));
                  }

                  if (this.traderLocationName != null) {
                     trader.setSpecificLocationName(this.traderLocationName);
                  }

                  trader.setShouldTraderRespawn(this.shouldTraderRespawn);
                  flag = this.field_70170_p.func_72838_d(trader);
                  if (trader instanceof LOTRTradeable && this.traderData != null) {
                     trader.traderNPCInfo.readFromNBT(this.traderData);
                  }
               }

               this.field_70121_D.func_72317_d(0.0D, -100.0D, 0.0D);
            }

            if (flag) {
               this.func_85030_a("random.pop", 1.0F, 0.5F + this.field_70146_Z.nextFloat() * 0.5F);
               this.func_70106_y();
            } else {
               this.timeUntilSpawn = 60;
               this.func_70012_b(this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, this.field_70177_z, this.field_70125_A);
            }
         }
      } else if (this.isSpawnImminent()) {
         this.prevBobbingTime = this.bobbingTime++;
      }

   }

   public float getScaleFloat(float tick) {
      float scale = (float)this.getScale();
      if (scale < (float)MAX_SCALE) {
         scale += tick;
      }

      return scale / (float)MAX_SCALE;
   }

   public float getBobbingOffset(float tick) {
      float f = (float)(this.bobbingTime - this.prevBobbingTime);
      f *= tick;
      return MathHelper.func_76126_a(((float)this.prevBobbingTime + f) / 5.0F) * 0.25F;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      int entityID = LOTREntities.getIDFromString(this.getClientTraderString());
      return entityID > 0 ? new ItemStack(LOTRMod.spawnEgg, 1, entityID) : null;
   }
}
