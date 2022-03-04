package lotr.client.particle;

import lotr.common.init.LOTRParticles;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.DripParticle.Dripping;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;

public class TranslucentDrippingParticle extends Dripping {
   private TranslucentDrippingParticle(ClientWorld world, double x, double y, double z, Fluid fl, IParticleData falling) {
      super(world, x, y, z, fl, falling);
   }

   public IParticleRenderType func_217558_b() {
      return IParticleRenderType.field_217603_c;
   }

   // $FF: synthetic method
   TranslucentDrippingParticle(ClientWorld x0, double x1, double x2, double x3, Fluid x4, IParticleData x5, Object x6) {
      this(x0, x1, x2, x3, x4, x5);
   }

   public static class Factory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         TranslucentDrippingParticle particle = new TranslucentDrippingParticle(world, x, y, z, Fluids.field_204546_a, (IParticleData)LOTRParticles.FALLING_WATER.get());
         particle.func_217568_a(this.spriteSet);
         return particle;
      }
   }
}
