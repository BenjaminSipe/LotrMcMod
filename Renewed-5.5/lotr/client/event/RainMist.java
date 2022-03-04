package lotr.client.event;

import lotr.common.config.LOTRConfig;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.RainType;

public class RainMist {
   private float rainMist;
   private float prevRainMist;

   public void update(World world, Entity viewer) {
      if ((Boolean)LOTRConfig.CLIENT.rainMist.get()) {
         this.prevRainMist = this.rainMist;
         Biome biome = world.func_226691_t_(viewer.func_233580_cy_());
         RainType precip = biome.func_201851_b();
         if (!world.func_72896_J() || precip != RainType.RAIN && precip != RainType.SNOW) {
            if (this.rainMist > 0.0F) {
               this.rainMist -= 0.0016666667F;
               this.rainMist = Math.max(this.rainMist, 0.0F);
            }
         } else if (this.rainMist < 1.0F) {
            this.rainMist += 0.008333334F;
            this.rainMist = Math.min(this.rainMist, 1.0F);
         }
      } else {
         this.reset();
      }

   }

   public void reset() {
      this.prevRainMist = this.rainMist = 0.0F;
   }

   public float getRainMistStrength(float partialTick) {
      return this.prevRainMist + (this.rainMist - this.prevRainMist) * partialTick;
   }
}
