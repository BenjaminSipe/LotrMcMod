package lotr.common.block;

import java.util.List;
import java.util.Random;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRParticles;
import lotr.common.init.LOTRTags;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MorgulFlowerBlock extends LOTRFlowerBlock {
   private static final VoxelShape MORGUL_FLOWER_SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public MorgulFlowerBlock() {
      super(Effects.field_76440_q, 10, createDefaultFlowerProperties().func_200944_c());
      this.flowerShape = MORGUL_FLOWER_SHAPE;
   }

   protected boolean func_200014_a_(BlockState state, IBlockReader world, BlockPos pos) {
      return super.func_200014_a_(state, world, pos) || state.func_235714_a_(LOTRTags.Blocks.MORDOR_PLANT_SURFACES);
   }

   public void func_225542_b_(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      super.func_225542_b_(state, world, pos, random);
      world.func_226691_t_(pos);
      double range = 12.0D;
      AxisAlignedBB aabb = (new AxisAlignedBB(pos)).func_186662_g(range);
      List entities = world.func_175647_a(LivingEntity.class, aabb, this::isEntityVulnerable);
      entities.forEach((e) -> {
         e.func_195064_c(new EffectInstance(Effects.field_76431_k, LOTRUtil.secondsToTicks(10)));
      });
   }

   public void func_196262_a(BlockState state, World world, BlockPos pos, Entity entity) {
      super.func_196262_a(state, world, pos, entity);
      if (entity instanceof LivingEntity && !world.field_72995_K) {
         LivingEntity living = (LivingEntity)entity;
         if (this.isEntityVulnerable(living)) {
            living.func_195064_c(new EffectInstance(Effects.field_76436_u, LOTRUtil.secondsToTicks(5)));
            living.func_195064_c(new EffectInstance(Effects.field_76440_q, LOTRUtil.secondsToTicks(10)));
         }
      }

   }

   private boolean isEntityVulnerable(LivingEntity entity) {
      if (entity instanceof PlayerEntity) {
         PlayerEntity player = (PlayerEntity)entity;
         if (player.field_71075_bZ.field_75098_d) {
            return false;
         } else {
            float alignment = LOTRLevelData.sidedInstance((Entity)player).getData(player).getAlignmentData().getAlignment(FactionPointers.MORDOR);
            float max = 250.0F;
            if (alignment >= max) {
               return false;
            } else if (alignment > 0.0F) {
               float f = alignment / max;
               return entity.func_70681_au().nextFloat() < 1.0F - f;
            } else {
               return true;
            }
         }
      } else {
         return true;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if (rand.nextInt(4) == 0) {
         Vector3d offset = state.func_191059_e(world, pos);
         double x = (double)pos.func_177958_n() + offset.field_72450_a;
         double y = (double)pos.func_177956_o() + offset.field_72448_b;
         double z = (double)pos.func_177952_p() + offset.field_72449_c;
         x += (double)MathHelper.func_151240_a(rand, 0.1F, 0.9F);
         y += (double)MathHelper.func_151240_a(rand, 0.5F, 0.75F);
         z += (double)MathHelper.func_151240_a(rand, 0.1F, 0.9F);
         if (rand.nextBoolean()) {
            world.func_195594_a((IParticleData)LOTRParticles.MORGUL_WATER_EFFECT.get(), x, y, z, 0.0D, 0.0D, 0.0D);
         } else {
            world.func_195594_a((IParticleData)LOTRParticles.WHITE_SMOKE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
         }
      }

   }
}
