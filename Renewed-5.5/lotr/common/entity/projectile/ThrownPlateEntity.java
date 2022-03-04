package lotr.common.entity.projectile;

import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.item.PlateItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThrownPlateEntity extends ProjectileItemEntity {
   private static final DataParameter THROWN_RETROGRADE;
   private int plateSpin;
   private int prevPlateSpin;

   public ThrownPlateEntity(EntityType type, World w) {
      super(type, w);
   }

   public ThrownPlateEntity(World w, ItemStack stack, LivingEntity e) {
      super((EntityType)LOTREntities.THROWN_PLATE.get(), e, w);
      this.func_213884_b(stack);
   }

   public ThrownPlateEntity(World w, ItemStack stack, double x, double y, double z) {
      super((EntityType)LOTREntities.THROWN_PLATE.get(), x, y, z, w);
      this.func_213884_b(stack);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(THROWN_RETROGRADE, false);
   }

   public boolean getThrownRetrograde() {
      return (Boolean)this.field_70180_af.func_187225_a(THROWN_RETROGRADE);
   }

   public void setThrownRetrograde(boolean flag) {
      this.field_70180_af.func_187227_b(THROWN_RETROGRADE, flag);
   }

   protected Item func_213885_i() {
      return (Item)LOTRItems.FINE_PLATE.get();
   }

   public IPacket func_213297_N() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public BlockState getPlateBlockState() {
      ItemStack plateItemStack = this.func_213882_k();
      if (!plateItemStack.func_190926_b()) {
         Item plateItem = plateItemStack.func_77973_b();
         Block block = plateItem instanceof PlateItem ? ((PlateItem)plateItem).getBlock() : Block.func_149634_a(plateItem);
         return block.func_176223_P();
      } else {
         return ((Block)LOTRBlocks.FINE_PLATE.get()).func_176223_P();
      }
   }

   public void func_213281_b(CompoundNBT nbt) {
      super.func_213281_b(nbt);
      nbt.func_74757_a("ThrownRetrograde", this.getThrownRetrograde());
   }

   public void func_70037_a(CompoundNBT nbt) {
      super.func_70037_a(nbt);
      this.setThrownRetrograde(nbt.func_74767_n("ThrownRetrograde"));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.prevPlateSpin = this.plateSpin++;
      double velX = this.func_213322_ci().func_82615_a();
      double velY = this.func_213322_ci().func_82617_b();
      double velZ = this.func_213322_ci().func_82616_c();
      float xzSpeed = MathHelper.func_76133_a(velX * velX + velZ * velZ);
      if (xzSpeed > 0.1F && velY < 0.0D && this.func_70090_H()) {
         float factor = MathHelper.func_151240_a(this.field_70146_Z, 0.6F, 0.8F);
         float addY = factor * 0.75F;
         velX *= (double)factor;
         velZ *= (double)factor;
         velY += (double)addY;
         this.func_213293_j(velX, velY, velZ);
      }

   }

   public float getPlateSpin(float f) {
      float spinLerp = (float)this.prevPlateSpin + (float)(this.plateSpin - this.prevPlateSpin) * f;
      int spinTicks = 12;
      float deg = spinLerp % (float)spinTicks / (float)spinTicks * 360.0F;
      if (!this.getThrownRetrograde()) {
         deg *= -1.0F;
      }

      return deg;
   }

   protected void func_70227_a(RayTraceResult target) {
      if (target.func_216346_c() == Type.ENTITY) {
         Entity hitEntity = ((EntityRayTraceResult)target).func_216348_a();
         if (hitEntity == this.func_234616_v_()) {
            return;
         }

         hitEntity.func_70097_a(DamageSource.func_76356_a(this, this.func_234616_v_()), 1.0F);
      } else if (target.func_216346_c() == Type.BLOCK) {
         BlockPos pos = ((BlockRayTraceResult)target).func_216350_a();
         if (!this.field_70170_p.field_72995_K && this.breakGlass(pos)) {
            Mutable movingPos = new Mutable();
            int range = 2;

            for(int x = -range; x <= range; ++x) {
               for(int y = -range; y <= range; ++y) {
                  for(int z = -range; z <= range; ++z) {
                     if (this.field_70146_Z.nextInt(4) != 0) {
                        movingPos.func_181079_c(pos.func_177958_n() + x, pos.func_177956_o() + y, pos.func_177952_p() + z);
                        this.breakGlass(movingPos);
                     }
                  }
               }
            }
         }
      }

      BlockState plateState = this.getPlateBlockState();

      for(int i = 0; i < 8; ++i) {
         float range = 0.25F;
         double x = this.func_226277_ct_() + (double)MathHelper.func_151240_a(this.field_70146_Z, -range, range);
         double y = this.func_226278_cu_() + (double)MathHelper.func_151240_a(this.field_70146_Z, -range, range);
         double z = this.func_226281_cx_() + (double)MathHelper.func_151240_a(this.field_70146_Z, -range, range);
         this.field_70170_p.func_195594_a(new BlockParticleData(ParticleTypes.field_197611_d, plateState), x, y, z, 0.0D, 0.0D, 0.0D);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_184185_a(plateState.func_215695_r().func_185845_c(), 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         this.func_70106_y();
      }

   }

   private boolean breakGlass(BlockPos pos) {
      BlockState state = this.field_70170_p.func_180495_p(pos);
      if (state.func_185904_a() == Material.field_151592_s) {
         boolean bannerProtection = false;
         if (!bannerProtection) {
            this.field_70170_p.func_217378_a((PlayerEntity)null, 2001, pos, Block.func_196246_j(state));
            this.field_70170_p.func_175656_a(pos, Blocks.field_150350_a.func_176223_P());
            return true;
         }
      }

      return false;
   }

   protected float func_70185_h() {
      return 0.02F;
   }

   static {
      THROWN_RETROGRADE = EntityDataManager.func_187226_a(ThrownPlateEntity.class, DataSerializers.field_187198_h);
   }
}
