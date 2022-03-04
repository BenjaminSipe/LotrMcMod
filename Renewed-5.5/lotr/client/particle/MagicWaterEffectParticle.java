package lotr.client.particle;

import java.awt.Color;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.LOTRBiomeWrapper;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;

public class MagicWaterEffectParticle extends SpellParticle {
   public MagicWaterEffectParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, IAnimatedSprite spriteWithAge) {
      super(world, x, y, z, motionX, motionY, motionZ, spriteWithAge);
      this.field_187129_i = motionX;
      this.field_187130_j = motionY;
      this.field_187131_k = motionZ;
      this.field_70544_f = 0.1F * (0.5F + this.field_187136_p.nextFloat() * 0.5F);
      this.field_70547_e = 20 + this.field_187136_p.nextInt(20);
   }

   private void setColorFromBiomeWater(LOTRBiomeWrapper biome) {
      Color c = new Color(biome.getActualBiome().func_235089_q_().func_235216_b_());
      float[] rgb = c.getColorComponents((float[])null);
      this.field_70552_h = this.getRandomisedColorComponent(rgb[0]);
      this.field_70553_i = this.getRandomisedColorComponent(rgb[1]);
      this.field_70551_j = this.getRandomisedColorComponent(rgb[2]);
   }

   private float getRandomisedColorComponent(float f) {
      float range = 0.2F;
      return MathHelper.func_76131_a(f + MathHelper.func_151240_a(this.field_187136_p, -range, range), 0.0F, 1.0F);
   }

   public void func_189213_a() {
      super.func_189213_a();
      this.field_82339_as = 0.5F + 0.5F * ((float)this.field_70546_d / (float)this.field_70547_e);
   }

   public static class MirkwoodWaterFactory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public MirkwoodWaterFactory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         MagicWaterEffectParticle particle = new MagicWaterEffectParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
         particle.setColorFromBiomeWater(LOTRBiomes.MIRKWOOD.getInitialisedBiomeWrapper());
         return particle;
      }
   }

   public static class MorgulWaterFactory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public MorgulWaterFactory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         MagicWaterEffectParticle particle = new MagicWaterEffectParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
         particle.setColorFromBiomeWater(LOTRBiomes.MORGUL_VALE.getInitialisedBiomeWrapper());
         return particle;
      }
   }
}
