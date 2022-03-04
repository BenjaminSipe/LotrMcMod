package lotr.client.event;

import lotr.common.world.biome.ExtendedWeatherType;
import net.minecraft.util.math.MathHelper;

public class SandstormFog extends ExtendedWeatherFog {
   private static final float SANDSTORM_FOG_HUE = 0.165F;
   private static final float SANDSTORM_FOG_SAT = 0.1F;
   private static final float SANDSTORM_FOG_BRIGHTNESS_MULTIPLIER = 0.75F;

   public ExtendedWeatherType getTargetWeather() {
      return ExtendedWeatherType.SANDSTORM;
   }

   protected float[] modifyFogHsb(float[] fogHsb, float weatherFogStrength) {
      fogHsb[0] = MathHelper.func_219799_g(weatherFogStrength, fogHsb[0], 0.165F);
      fogHsb[1] = MathHelper.func_219799_g(weatherFogStrength, fogHsb[1], 0.1F);
      fogHsb[2] *= MathHelper.func_219799_g(weatherFogStrength, 1.0F, 0.75F);
      return fogHsb;
   }
}
