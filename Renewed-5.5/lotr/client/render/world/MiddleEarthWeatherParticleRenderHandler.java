package lotr.client.render.world;

import java.util.Random;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRParticles;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.client.IWeatherParticleRenderHandler;

public class MiddleEarthWeatherParticleRenderHandler implements IWeatherParticleRenderHandler {
   private static final int PARTICLE_RANGE = 10;
   private int rainSoundTime;

   public void render(int ticks, ClientWorld world, Minecraft mc, ActiveRenderInfo activeRenderInfo) {
      float rainStrength = world.func_72867_j(1.0F) / (Minecraft.func_71375_t() ? 1.0F : 2.0F);
      if (rainStrength > 0.0F) {
         Random rand = new Random((long)ticks * 312987231L);
         BlockPos pos = new BlockPos(activeRenderInfo.func_216785_c());
         BlockPos soundPos = null;
         int numParticles = (int)(100.0F * rainStrength * rainStrength) / (mc.field_71474_y.field_74362_aa == ParticleStatus.DECREASED ? 2 : 1);

         for(int i = 0; i < numParticles; ++i) {
            int x = MathHelper.func_76136_a(rand, -10, 10);
            int z = MathHelper.func_76136_a(rand, -10, 10);
            BlockPos surfacePos = world.func_205770_a(Type.MOTION_BLOCKING, pos.func_177982_a(x, 0, z)).func_177977_b();
            Biome biome = world.func_226691_t_(surfacePos);
            LOTRBiomeWrapper biomeWrapper = LOTRBiomes.getWrapperFor(biome, world);
            if (surfacePos.func_177956_o() > 0 && surfacePos.func_177956_o() <= pos.func_177956_o() + 10 && surfacePos.func_177956_o() >= pos.func_177956_o() - 10 && biomeWrapper.getPrecipitationVisually() == RainType.RAIN && !LOTRBiomeBase.isSnowingVisually(biomeWrapper, world, surfacePos)) {
               soundPos = surfacePos;
               if (mc.field_71474_y.field_74362_aa == ParticleStatus.MINIMAL) {
                  break;
               }

               double dx = rand.nextDouble();
               double dz = rand.nextDouble();
               BlockState blockstate = world.func_180495_p(surfacePos);
               FluidState fluidstate = world.func_204610_c(surfacePos);
               VoxelShape voxelshape = blockstate.func_196952_d(world, surfacePos);
               double collisionShapeTop = voxelshape.func_197760_b(Axis.Y, dx, dz);
               double topHeight = (double)fluidstate.func_215679_a(world, surfacePos);
               double dy = Math.max(collisionShapeTop, topHeight);
               IParticleData particle = !fluidstate.func_206884_a(FluidTags.field_206960_b) && !blockstate.func_203425_a(Blocks.field_196814_hQ) && !CampfireBlock.func_226915_i_(blockstate) ? (IParticleData)LOTRParticles.RAIN.get() : ParticleTypes.field_197601_L;
               world.func_195594_a((IParticleData)particle, (double)surfacePos.func_177958_n() + dx, (double)surfacePos.func_177956_o() + dy, (double)surfacePos.func_177952_p() + dz, 0.0D, 0.0D, 0.0D);
            }
         }

         if (soundPos != null && rand.nextInt(3) < this.rainSoundTime++) {
            this.rainSoundTime = 0;
            if (soundPos.func_177956_o() > pos.func_177956_o() + 1 && world.func_205770_a(Type.MOTION_BLOCKING, pos).func_177956_o() > MathHelper.func_76141_d((float)pos.func_177956_o())) {
               mc.field_71441_e.func_184156_a(soundPos, SoundEvents.field_187919_gs, SoundCategory.WEATHER, 0.1F, 0.5F, false);
            } else {
               mc.field_71441_e.func_184156_a(soundPos, LOTRSoundEvents.NEW_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
            }
         }
      }

   }
}
