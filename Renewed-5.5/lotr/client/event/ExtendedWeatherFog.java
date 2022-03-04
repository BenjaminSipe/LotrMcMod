package lotr.client.event;

import java.awt.Color;
import lotr.common.config.LOTRConfig;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.ExtendedWeatherType;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;

public abstract class ExtendedWeatherFog {
   private float fog;
   private float prevFog;
   private float[] fogHsbCache = null;
   private float[] fogModifiedRgbCache = null;

   public abstract ExtendedWeatherType getTargetWeather();

   public void update(World world, Entity viewer) {
      if ((Boolean)LOTRConfig.CLIENT.newWeatherRendering.get()) {
         this.prevFog = this.fog;
         Biome biome = world.func_226691_t_(viewer.func_233580_cy_());
         if (world.func_72896_J() && LOTRBiomes.getWrapperFor(biome, world).getExtendedWeatherVisually() == this.getTargetWeather()) {
            if (this.fog < 1.0F) {
               this.fog += 0.008333334F;
               this.fog = Math.min(this.fog, 1.0F);
            }
         } else if (this.fog > 0.0F) {
            this.fog -= 0.005F;
            this.fog = Math.max(this.fog, 0.0F);
         }
      } else {
         this.reset();
      }

   }

   public void reset() {
      this.prevFog = this.fog = 0.0F;
   }

   public float getWeatherFogStrength(float partialTick) {
      return this.prevFog + (this.fog - this.prevFog) * partialTick;
   }

   public void modifyFogColors(FogColors event, float renderPartialTick) {
      float weatherFogStrength = this.getWeatherFogStrength(renderPartialTick);
      if (weatherFogStrength > 0.0F) {
         Color fogColor = new Color(event.getRed(), event.getGreen(), event.getBlue());
         this.fogHsbCache = Color.RGBtoHSB(fogColor.getRed(), fogColor.getGreen(), fogColor.getBlue(), this.fogHsbCache);
         this.fogHsbCache = this.modifyFogHsb(this.fogHsbCache, weatherFogStrength);
         fogColor = Color.getHSBColor(this.fogHsbCache[0], this.fogHsbCache[1], this.fogHsbCache[2]);
         this.fogModifiedRgbCache = fogColor.getColorComponents(this.fogModifiedRgbCache);
         event.setRed(this.fogModifiedRgbCache[0]);
         event.setGreen(this.fogModifiedRgbCache[1]);
         event.setBlue(this.fogModifiedRgbCache[2]);
      }

   }

   protected abstract float[] modifyFogHsb(float[] var1, float var2);
}
