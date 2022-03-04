package lotr.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

public class NPCSpeechParticle extends SpriteTexturedParticle {
   private final IAnimatedSprite spriteSet;
   private final float fadeOutThreshold;
   private final int animTime;

   private NPCSpeechParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, IAnimatedSprite sprites) {
      super(world, x, y, z);
      this.fadeOutThreshold = 0.75F;
      this.animTime = 30;
      this.field_70547_e = 40 + this.field_187136_p.nextInt(20);
      this.field_187129_i = xSpeed;
      this.field_187130_j = ySpeed;
      this.field_187131_k = zSpeed;
      this.spriteSet = sprites;
      this.setParticleSpriteAndAlpha();
   }

   private void setParticleSpriteAndAlpha() {
      this.func_217567_a(this.spriteSet.func_217591_a(this.field_70546_d % 30, 30));
      float ageF = (float)this.field_70546_d / (float)this.field_70547_e;
      this.field_82339_as = ageF < 0.75F ? 1.0F : (0.75F - ageF) / 0.25F;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      ++this.field_70546_d;
      if (this.field_70546_d < this.field_70547_e) {
         this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
         this.field_187129_i *= 0.85D;
         this.field_187131_k *= 0.85D;
         this.field_187130_j *= 0.85D;
         this.setParticleSpriteAndAlpha();
      } else {
         this.func_187112_i();
      }

   }

   public IParticleRenderType func_217558_b() {
      return IParticleRenderType.field_217603_c;
   }

   // $FF: synthetic method
   NPCSpeechParticle(ClientWorld x0, double x1, double x2, double x3, double x4, double x5, double x6, IAnimatedSprite x7, Object x8) {
      this(x0, x1, x2, x3, x4, x5, x6, x7);
   }

   public static class Factory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         NPCSpeechParticle particle = new NPCSpeechParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
         return particle;
      }
   }
}
