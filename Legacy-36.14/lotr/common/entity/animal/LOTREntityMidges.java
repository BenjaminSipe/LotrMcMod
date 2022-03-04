package lotr.common.entity.animal;

import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.biome.LOTRBiomeGenMidgewater;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityMidges extends EntityLiving implements LOTRAmbientCreature {
   private ChunkCoordinates currentFlightTarget;
   private EntityPlayer playerTarget;
   public LOTREntityMidges.Midge[] midges;

   public LOTREntityMidges(World world) {
      super(world);
      this.func_70105_a(2.0F, 2.0F);
      this.field_70155_l = 0.5D;
      this.midges = new LOTREntityMidges.Midge[3 + this.field_70146_Z.nextInt(6)];

      for(int l = 0; l < this.midges.length; ++l) {
         this.midges[l] = new LOTREntityMidges.Midge();
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(2.0D);
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
      this.field_70181_x *= 0.6D;

      for(int l = 0; l < this.midges.length; ++l) {
         this.midges[l].update();
      }

      if (this.field_70146_Z.nextInt(5) == 0) {
         this.func_85030_a("lotr:midges.swarm", this.func_70599_aP(), this.func_70647_i());
      }

      if (!this.field_70170_p.field_72995_K && this.func_70089_S()) {
         boolean inMidgewater = this.field_70170_p.func_72807_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70161_v)) instanceof LOTRBiomeGenMidgewater;
         int chance = inMidgewater ? 100 : 500;
         if (this.field_70146_Z.nextInt(chance) == 0) {
            double range = inMidgewater ? 16.0D : 24.0D;
            int threshold = inMidgewater ? 6 : 5;
            List list = this.field_70170_p.func_72872_a(LOTREntityMidges.class, this.field_70121_D.func_72314_b(range, range, range));
            if (list.size() < threshold) {
               LOTREntityMidges moreMidges = new LOTREntityMidges(this.field_70170_p);
               moreMidges.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
               moreMidges.func_110161_a((IEntityLivingData)null);
               this.field_70170_p.func_72838_d(moreMidges);
            }
         }
      }

   }

   protected void func_70619_bc() {
      super.func_70619_bc();
      if (this.currentFlightTarget != null && !this.field_70170_p.func_147437_c(this.currentFlightTarget.field_71574_a, this.currentFlightTarget.field_71572_b, this.currentFlightTarget.field_71573_c)) {
         this.currentFlightTarget = null;
      }

      if (this.playerTarget != null && (!this.playerTarget.func_70089_S() || this.func_70068_e(this.playerTarget) > 256.0D)) {
         this.playerTarget = null;
      }

      if (this.playerTarget != null) {
         if (this.field_70146_Z.nextInt(400) == 0) {
            this.playerTarget = null;
         } else {
            this.currentFlightTarget = new ChunkCoordinates((int)this.playerTarget.field_70165_t, (int)this.playerTarget.field_70163_u + 3, (int)this.playerTarget.field_70161_v);
         }
      } else if (this.field_70146_Z.nextInt(100) == 0) {
         EntityPlayer closestPlayer = this.field_70170_p.func_72890_a(this, 12.0D);
         if (closestPlayer != null && this.field_70146_Z.nextInt(7) == 0) {
            this.playerTarget = closestPlayer;
         } else {
            int i = (int)this.field_70165_t + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7);
            int j = (int)this.field_70163_u + this.field_70146_Z.nextInt(4) - this.field_70146_Z.nextInt(3);
            int k = (int)this.field_70161_v + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7);
            if (j < 1) {
               j = 1;
            }

            int height = this.field_70170_p.func_72825_h(i, k);
            if (j > height + 8) {
               j = height + 8;
            }

            this.currentFlightTarget = new ChunkCoordinates(i, j, k);
         }
      }

      if (this.currentFlightTarget != null) {
         double dx = (double)this.currentFlightTarget.field_71574_a + 0.5D - this.field_70165_t;
         double dy = (double)this.currentFlightTarget.field_71572_b + 0.5D - this.field_70163_u;
         double dz = (double)this.currentFlightTarget.field_71573_c + 0.5D - this.field_70161_v;
         this.field_70159_w += (Math.signum(dx) * 0.5D - this.field_70159_w) * 0.1D;
         this.field_70181_x += (Math.signum(dy) * 0.7D - this.field_70181_x) * 0.1D;
         this.field_70179_y += (Math.signum(dz) * 0.5D - this.field_70179_y) * 0.1D;
         this.field_70701_bs = 0.2F;
      } else {
         this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
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

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && damagesource instanceof EntityDamageSourceIndirect) {
         Entity attacker = damagesource.func_76346_g();
         if (attacker instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC)attacker;
            if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
               EntityPlayer entityplayer = npc.hiredNPCInfo.getHiringPlayer();
               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.shootDownMidges);
            }
         }
      }

   }

   protected boolean func_70692_ba() {
      return true;
   }

   public boolean func_70601_bi() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      if (j < 62) {
         return false;
      } else {
         return this.field_70170_p.func_147439_a(i, j - 1, k) == this.field_70170_p.func_72807_a(i, k).field_76752_A && super.func_70601_bi();
      }
   }

   public boolean func_110164_bC() {
      return false;
   }

   protected boolean func_70085_c(EntityPlayer entityplayer) {
      return false;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      int id = LOTREntities.getEntityID(this);
      return id > 0 && LOTREntities.spawnEggs.containsKey(id) ? new ItemStack(LOTRMod.spawnEgg, 1, id) : null;
   }

   public class Midge {
      public float midge_posX;
      public float midge_posY;
      public float midge_posZ;
      public float midge_prevPosX;
      public float midge_prevPosY;
      public float midge_prevPosZ;
      private float midge_initialPosX;
      private float midge_initialPosY;
      private float midge_initialPosZ;
      public float midge_rotation;
      private int midgeTick;
      private int maxMidgeTick = 80;

      public Midge() {
         this.midge_initialPosX = this.midge_posX = -1.0F + LOTREntityMidges.this.field_70146_Z.nextFloat() * 2.0F;
         this.midge_initialPosY = this.midge_posY = LOTREntityMidges.this.field_70146_Z.nextFloat() * 2.0F;
         this.midge_initialPosZ = this.midge_posZ = -1.0F + LOTREntityMidges.this.field_70146_Z.nextFloat() * 2.0F;
         this.midge_rotation = LOTREntityMidges.this.field_70146_Z.nextFloat() * 360.0F;
         this.midgeTick = LOTREntityMidges.this.field_70146_Z.nextInt(this.maxMidgeTick);
      }

      public void update() {
         this.midge_prevPosX = this.midge_posX;
         this.midge_prevPosY = this.midge_posY;
         this.midge_prevPosZ = this.midge_posZ;
         ++this.midgeTick;
         if (this.midgeTick > this.maxMidgeTick) {
            this.midgeTick = 0;
         }

         this.midge_posY = this.midge_initialPosY + 0.5F * MathHelper.func_76126_a((float)this.midgeTick / 6.2831855F);
      }
   }
}
