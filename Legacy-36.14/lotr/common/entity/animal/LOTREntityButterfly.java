package lotr.common.entity.animal;

import java.util.Random;
import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockTorch;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.world.biome.LOTRBiomeGenFarHaradJungle;
import lotr.common.world.biome.LOTRBiomeGenLothlorien;
import lotr.common.world.biome.LOTRBiomeGenMirkwood;
import lotr.common.world.biome.LOTRBiomeGenWoodlandRealm;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityButterfly extends EntityLiving implements LOTRAmbientCreature, LOTRRandomSkinEntity {
   private LOTRBlockTorch elfTorchBlock;
   private ChunkCoordinates currentFlightTarget;
   public int flapTime = 0;

   public LOTREntityButterfly(World world) {
      super(world);
      this.func_70105_a(0.5F, 0.5F);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, (byte)0);
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public LOTREntityButterfly.ButterflyType getButterflyType() {
      int i = this.field_70180_af.func_75683_a(16);
      if (i < 0 || i >= LOTREntityButterfly.ButterflyType.values().length) {
         i = 0;
      }

      return LOTREntityButterfly.ButterflyType.values()[i];
   }

   public void setButterflyType(LOTREntityButterfly.ButterflyType type) {
      this.setButterflyType(type.ordinal());
   }

   public void setButterflyType(int i) {
      this.field_70180_af.func_75692_b(16, (byte)i);
   }

   public boolean isButterflyStill() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setButterflyStill(boolean flag) {
      this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(2.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(MathHelper.func_82716_a(this.field_70146_Z, 0.08D, 0.12D));
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (!(biome instanceof LOTRBiomeGenMirkwood) && !(biome instanceof LOTRBiomeGenWoodlandRealm)) {
         if (biome instanceof LOTRBiomeGenLothlorien) {
            this.setButterflyType(LOTREntityButterfly.ButterflyType.LORIEN);
         } else if (biome instanceof LOTRBiomeGenFarHaradJungle) {
            this.setButterflyType(LOTREntityButterfly.ButterflyType.JUNGLE);
         } else {
            this.setButterflyType(LOTREntityButterfly.ButterflyType.COMMON);
         }
      } else {
         this.setButterflyType(LOTREntityButterfly.ButterflyType.MIRKWOOD);
      }

      return data;
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   public boolean func_70104_M() {
      return false;
   }

   protected void func_82167_n(Entity entity) {
   }

   protected void func_85033_bc() {
   }

   protected boolean func_70650_aV() {
      return true;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.isButterflyStill()) {
         this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
         this.field_70163_u = (double)MathHelper.func_76128_c(this.field_70163_u);
         if (this.field_70170_p.field_72995_K) {
            if (this.field_70146_Z.nextInt(200) == 0) {
               this.flapTime = 40;
            }

            if (this.flapTime > 0) {
               --this.flapTime;
            }
         }
      } else {
         this.field_70181_x *= 0.6D;
         if (this.field_70170_p.field_72995_K) {
            this.flapTime = 0;
         }

         if (this.getButterflyType() == LOTREntityButterfly.ButterflyType.LORIEN) {
            double d = this.field_70165_t;
            double d1 = this.field_70163_u;
            double d2 = this.field_70161_v;
            double d3 = this.field_70159_w * -0.2D;
            double d4 = this.field_70181_x * -0.2D;
            double d5 = this.field_70179_y * -0.2D;
            if (this.elfTorchBlock == null) {
               Random torchRand = new Random();
               torchRand.setSeed(this.field_96093_i.getLeastSignificantBits());
               this.elfTorchBlock = (LOTRBlockTorch)LOTRWorldGenElfHouse.getRandomTorch(torchRand);
            }

            LOTRBlockTorch.TorchParticle particle = this.elfTorchBlock.createTorchParticle(this.field_70146_Z);
            if (particle != null) {
               particle.spawn(d, d1, d2);
            }
         }
      }

   }

   protected void func_70619_bc() {
      super.func_70619_bc();
      if (this.isButterflyStill()) {
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = (int)this.field_70163_u - 1;
         int k = MathHelper.func_76128_c(this.field_70161_v);
         if (!this.field_70170_p.func_147439_a(i, j, k).isSideSolid(this.field_70170_p, i, j, k, ForgeDirection.UP)) {
            this.setButterflyStill(false);
         } else if (this.field_70146_Z.nextInt(400) == 0 || this.field_70170_p.func_72890_a(this, 3.0D) != null) {
            this.setButterflyStill(false);
         }
      } else {
         if (this.currentFlightTarget != null && (!this.field_70170_p.func_147437_c(this.currentFlightTarget.field_71574_a, this.currentFlightTarget.field_71572_b, this.currentFlightTarget.field_71573_c) || this.currentFlightTarget.field_71572_b < 1)) {
            this.currentFlightTarget = null;
         }

         if (this.currentFlightTarget == null || this.field_70146_Z.nextInt(30) == 0 || this.currentFlightTarget.func_71569_e((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v) < 4.0F) {
            this.currentFlightTarget = new ChunkCoordinates((int)this.field_70165_t + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7), (int)this.field_70163_u + this.field_70146_Z.nextInt(6) - 2, (int)this.field_70161_v + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7));
         }

         double speed = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
         double d0 = (double)this.currentFlightTarget.field_71574_a + 0.5D - this.field_70165_t;
         double d1 = (double)this.currentFlightTarget.field_71572_b + 0.5D - this.field_70163_u;
         double d2 = (double)this.currentFlightTarget.field_71573_c + 0.5D - this.field_70161_v;
         this.field_70159_w += (Math.signum(d0) * 0.5D - this.field_70159_w) * speed;
         this.field_70181_x += (Math.signum(d1) * 0.7D - this.field_70181_x) * speed;
         this.field_70179_y += (Math.signum(d2) * 0.5D - this.field_70179_y) * speed;
         float f = (float)(Math.atan2(this.field_70179_y, this.field_70159_w) * 180.0D / 3.141592653589793D) - 90.0F;
         float f1 = MathHelper.func_76142_g(f - this.field_70177_z);
         this.field_70701_bs = 0.5F;
         this.field_70177_z += f1;
         if (this.field_70146_Z.nextInt(150) == 0 && this.field_70170_p.func_147439_a(MathHelper.func_76128_c(this.field_70165_t), (int)this.field_70163_u - 1, MathHelper.func_76128_c(this.field_70161_v)).func_149721_r()) {
            this.setButterflyStill(true);
         }
      }

   }

   protected boolean func_70041_e_() {
      return false;
   }

   protected void func_70069_a(float f) {
   }

   protected void func_70064_a(double d, boolean flag) {
   }

   public boolean func_145773_az() {
      return true;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag && !this.field_70170_p.field_72995_K && this.isButterflyStill()) {
         this.setButterflyStill(false);
      }

      return flag;
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setButterflyType(nbt.func_74762_e("ButterflyType"));
      this.setButterflyStill(nbt.func_74767_n("ButterflyStill"));
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("ButterflyType", this.getButterflyType().ordinal());
      nbt.func_74757_a("ButterflyStill", this.isButterflyStill());
   }

   protected boolean func_70692_ba() {
      return true;
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() ? LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.field_151585_k, Material.field_151582_l) : false;
   }

   public boolean func_110164_bC() {
      return false;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }

   public static enum ButterflyType {
      MIRKWOOD("mirkwood"),
      LORIEN("lorien"),
      COMMON("common"),
      JUNGLE("jungle");

      public final String textureDir;

      private ButterflyType(String s) {
         this.textureDir = s;
      }
   }
}
