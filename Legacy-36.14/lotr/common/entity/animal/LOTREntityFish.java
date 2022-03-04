package lotr.common.entity.animal;

import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityFish extends EntityWaterMob implements LOTRRandomSkinEntity {
   private ChunkCoordinates currentSwimTarget;
   private int swimTargetTime = 0;
   private static final int swimTargetTimeMax = 200;

   public LOTREntityFish(World world) {
      super(world);
      this.func_70105_a(0.5F, 0.5F);
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, (byte)0);
   }

   public LOTREntityFish.FishType getFishType() {
      int i = this.field_70180_af.func_75683_a(16);
      if (i < 0 || i >= LOTREntityFish.FishType.values().length) {
         i = 0;
      }

      return LOTREntityFish.FishType.values()[i];
   }

   public void setFishType(LOTREntityFish.FishType type) {
      this.setFishType(type.ordinal());
   }

   public void setFishType(int i) {
      this.field_70180_af.func_75692_b(16, (byte)i);
   }

   public String getFishTextureDir() {
      return this.getFishType().textureDir;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(MathHelper.func_82716_a(this.field_70146_Z, 0.04D, 0.08D));
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      this.field_70170_p.func_72807_a(i, k);
      if (this.field_70146_Z.nextInt(30) == 0) {
         this.setFishType(LOTREntityFish.FishType.CLOWNFISH);
      } else if (this.field_70146_Z.nextInt(8) == 0) {
         this.setFishType(LOTREntityFish.FishType.SALMON);
      } else {
         this.setFishType(LOTREntityFish.FishType.COMMON);
      }

      return data;
   }

   protected boolean func_70041_e_() {
      return false;
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setFishType(nbt.func_74762_e("FishType"));
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("FishType", this.getFishType().ordinal());
   }

   protected void func_70628_a(boolean flag, int i) {
      int drops = this.field_70146_Z.nextInt(2 + i);

      for(int l = 0; l < drops; ++l) {
         if (this.getFishType() == LOTREntityFish.FishType.SALMON) {
            this.func_70099_a(new ItemStack(Items.field_151115_aP, 1, net.minecraft.item.ItemFishFood.FishType.SALMON.func_150976_a()), 0.0F);
         } else if (this.getFishType() == LOTREntityFish.FishType.CLOWNFISH) {
            this.func_70099_a(new ItemStack(Items.field_151115_aP, 1, net.minecraft.item.ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 0.0F);
         } else {
            this.func_70099_a(new ItemStack(Items.field_151115_aP, 1, net.minecraft.item.ItemFishFood.FishType.COD.func_150976_a()), 0.0F);
         }
      }

   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.func_70090_H() && !this.field_70170_p.field_72995_K) {
         this.field_70159_w = 0.0D;
         this.field_70181_x -= 0.08D;
         this.field_70181_x *= 0.98D;
         this.field_70179_y = 0.0D;
      }

   }

   public boolean func_70090_H() {
      double d = 0.5D;
      return this.field_70170_p.func_72875_a(this.field_70121_D.func_72314_b(d, d, d), Material.field_151586_h);
   }

   protected void func_70626_be() {
      ++this.field_70708_bq;
      if (this.currentSwimTarget != null && !this.isValidSwimTarget(this.currentSwimTarget.field_71574_a, this.currentSwimTarget.field_71572_b, this.currentSwimTarget.field_71573_c)) {
         this.currentSwimTarget = null;
         this.swimTargetTime = 0;
      }

      if (this.currentSwimTarget == null || this.field_70146_Z.nextInt(200) == 0 || this.getDistanceSqToSwimTarget() < 4.0D) {
         for(int l = 0; l < 16; ++l) {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70163_u);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            i += this.field_70146_Z.nextInt(16) - this.field_70146_Z.nextInt(16);
            k += this.field_70146_Z.nextInt(16) - this.field_70146_Z.nextInt(16);
            j += MathHelper.func_76136_a(this.field_70146_Z, -2, 4);
            if (this.isValidSwimTarget(i, j, k)) {
               this.currentSwimTarget = new ChunkCoordinates(i, j, k);
               this.swimTargetTime = 0;
               break;
            }
         }
      }

      if (this.currentSwimTarget != null) {
         double speed = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
         double d0 = (double)this.currentSwimTarget.field_71574_a + 0.5D - this.field_70165_t;
         double d1 = (double)this.currentSwimTarget.field_71572_b + 0.5D - this.field_70163_u;
         double d2 = (double)this.currentSwimTarget.field_71573_c + 0.5D - this.field_70161_v;
         this.field_70159_w += (Math.signum(d0) * 0.5D - this.field_70159_w) * speed;
         this.field_70181_x += (Math.signum(d1) * 0.5D - this.field_70181_x) * speed;
         this.field_70179_y += (Math.signum(d2) * 0.5D - this.field_70179_y) * speed;
         float f = (float)(Math.atan2(this.field_70179_y, this.field_70159_w) * 180.0D / 3.141592653589793D) - 90.0F;
         float f1 = MathHelper.func_76142_g(f - this.field_70177_z);
         this.field_70701_bs = 0.5F;
         this.field_70177_z += f1;
         ++this.swimTargetTime;
         if (this.swimTargetTime >= 200) {
            this.currentSwimTarget = null;
            this.swimTargetTime = 0;
         }
      }

      this.func_70623_bb();
   }

   private boolean isValidSwimTarget(int i, int j, int k) {
      return this.field_70170_p.func_147439_a(i, j, k).func_149688_o() == Material.field_151586_h;
   }

   private double getDistanceSqToSwimTarget() {
      double d = (double)this.currentSwimTarget.field_71574_a + 0.5D;
      double d1 = (double)this.currentSwimTarget.field_71572_b + 0.5D;
      double d2 = (double)this.currentSwimTarget.field_71573_c + 0.5D;
      return this.func_70092_e(d, d1, d2);
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }

   public static enum FishType {
      COMMON("common"),
      SALMON("salmon"),
      CLOWNFISH("clownfish");

      public final String textureDir;

      private FishType(String s) {
         this.textureDir = s;
      }
   }
}
