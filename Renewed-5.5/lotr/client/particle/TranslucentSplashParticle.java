package lotr.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

public class TranslucentSplashParticle extends TranslucentRainParticle {
   private TranslucentSplashParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(world, x, y, z);
      this.field_70545_g = 0.04F;
      if (ySpeed == 0.0D && (xSpeed != 0.0D || zSpeed != 0.0D)) {
         this.field_187129_i = xSpeed;
         this.field_187130_j = 0.1D;
         this.field_187131_k = zSpeed;
      }

   }

   public IParticleRenderType func_217558_b() {
      return IParticleRenderType.field_217603_c;
   }

   // $FF: synthetic method
   TranslucentSplashParticle(ClientWorld x0, double x1, double x2, double x3, double x4, double x5, double x6, Object x7) {
      this(x0, x1, x2, x3, x4, x5, x6);
   }

   public static class Factory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         TranslucentSplashParticle particle = new TranslucentSplashParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
         particle.func_217568_a(this.spriteSet);
         return particle;
      }
   }
}
