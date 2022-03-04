package lotr.client.render.world;

import lotr.common.dim.LOTRDimensionType;
import lotr.common.init.LOTRParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;

public class MiddleEarthWorldRenderer extends WorldRenderer {
   private final Minecraft theMinecraft;

   public MiddleEarthWorldRenderer(Minecraft minecraft, RenderTypeBuffers buffers) {
      super(minecraft, buffers);
      this.theMinecraft = minecraft;
   }

   private final boolean isMiddleEarthDimension() {
      return this.theMinecraft.field_71441_e.func_230315_m_() instanceof LOTRDimensionType;
   }

   public void func_195462_a(IParticleData particleData, boolean ignoreRange, boolean minimizeLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      if (particleData == ParticleTypes.field_197618_k && this.isMiddleEarthDimension()) {
         particleData = (IParticleData)LOTRParticles.DRIPPING_WATER.get();
      }

      if (particleData == ParticleTypes.field_218425_n && this.isMiddleEarthDimension()) {
         particleData = (IParticleData)LOTRParticles.FALLING_WATER.get();
      }

      if (particleData == ParticleTypes.field_218422_X && this.isMiddleEarthDimension()) {
         particleData = (IParticleData)LOTRParticles.SPLASH.get();
      }

      super.func_195462_a(particleData, ignoreRange, minimizeLevel, x, y, z, xSpeed, ySpeed, zSpeed);
   }
}
