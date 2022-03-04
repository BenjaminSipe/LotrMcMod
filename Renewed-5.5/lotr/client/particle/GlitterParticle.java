package lotr.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;

public class GlitterParticle extends SpriteTexturedParticle {
   private final IAnimatedSprite spriteSet;

   private GlitterParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, IAnimatedSprite sprites) {
      super(world, x, y, z);
      this.field_70547_e = 20 + this.field_187136_p.nextInt(50);
      this.field_187129_i = xSpeed;
      this.field_187130_j = ySpeed;
      this.field_187131_k = zSpeed;
      this.spriteSet = sprites;
      this.func_217566_b(this.spriteSet);
      this.setParticleAlpha();
   }

   private void setParticleAlpha() {
      float ageF = (float)this.field_70546_d / (float)this.field_70547_e;
      this.field_82339_as = MathHelper.func_76131_a(MathHelper.func_76126_a(ageF * 3.1415927F), 0.02F, 1.0F);
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      ++this.field_70546_d;
      if (this.field_70546_d < this.field_70547_e) {
         this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
         this.setParticleAlpha();
         this.func_217566_b(this.spriteSet);
      } else {
         this.func_187112_i();
      }

   }

   public IParticleRenderType func_217558_b() {
      return IParticleRenderType.field_217603_c;
   }

   // $FF: synthetic method
   GlitterParticle(ClientWorld x0, double x1, double x2, double x3, double x4, double x5, double x6, IAnimatedSprite x7, Object x8) {
      this(x0, x1, x2, x3, x4, x5, x6, x7);
   }

   public static class Factory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         GlitterParticle particle = new GlitterParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
         return particle;
      }
   }
}
