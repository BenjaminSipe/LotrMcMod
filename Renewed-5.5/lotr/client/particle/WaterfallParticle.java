package lotr.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WaterfallParticle extends SpriteTexturedParticle {
   private WaterfallParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(world, x, y, z);
      this.func_70541_f(2.5F);
      this.func_187115_a(0.25F, 0.25F);
      this.field_70547_e = 20 + this.field_187136_p.nextInt(16);
      this.field_187129_i = xSpeed;
      this.field_187130_j = ySpeed;
      this.field_187131_k = zSpeed;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      ++this.field_70546_d;
      if (this.field_70546_d < this.field_70547_e && this.field_82339_as > 0.0F) {
         this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
         this.field_82339_as -= 1.0F / (float)this.field_70547_e;
         this.field_82339_as = Math.max(this.field_82339_as, 0.0F);
      } else {
         this.func_187112_i();
      }

   }

   public IParticleRenderType func_217558_b() {
      return IParticleRenderType.field_217603_c;
   }

   // $FF: synthetic method
   WaterfallParticle(ClientWorld x0, double x1, double x2, double x3, double x4, double x5, double x6, Object x7) {
      this(x0, x1, x2, x3, x4, x5, x6);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         WaterfallParticle particle = new WaterfallParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
         particle.func_82338_g(0.75F);
         particle.func_217568_a(this.spriteSet);
         return particle;
      }
   }
}
