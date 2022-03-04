package lotr.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;

public class WhiteSmokeParticle extends SmokeParticle {
   protected WhiteSmokeParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float scale, IAnimatedSprite spriteWithAge) {
      super(world, x, y, z, xSpeed, ySpeed, zSpeed, scale, spriteWithAge);
      float grey = MathHelper.func_151240_a(this.field_187136_p, 0.7F, 1.0F);
      this.field_70552_h = this.field_70553_i = this.field_70551_j = grey;
   }

   public static class Factory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new WhiteSmokeParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, this.spriteSet);
      }
   }
}
