package lotr.client.event;

import lotr.common.config.LOTRConfig;
import lotr.common.init.LOTRBiomes;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MistyMountainsMist {
   private int mistTick;
   private int prevMistTick;
   private static final int MIST_TICK_MAX = 80;
   private static final int MIST_MIN_Y = 72;

   public void update(World world, Entity viewer) {
      if ((Boolean)LOTRConfig.CLIENT.mistyMountainsMist.get()) {
         this.prevMistTick = this.mistTick;
         if (this.hasMistAtLocation(world, viewer.func_233580_cy_())) {
            if (this.mistTick < 80) {
               ++this.mistTick;
            }
         } else if (this.mistTick > 0) {
            --this.mistTick;
         }
      } else {
         this.reset();
      }

   }

   private boolean hasMistAtLocation(World world, BlockPos pos) {
      if (pos.func_177956_o() >= 72 && world.func_175710_j(pos) && world.func_226658_a_(LightType.BLOCK, pos) < 7) {
         Biome biome = world.func_226691_t_(pos);
         return LOTRBiomes.getWrapperFor(biome, world).hasMountainsMist();
      } else {
         return false;
      }
   }

   public void reset() {
      this.prevMistTick = this.mistTick = 0;
   }

   public float getCurrentMistFactor(Entity viewer, float partialTick) {
      float mistTickF = (float)this.prevMistTick + (float)(this.mistTick - this.prevMistTick) * partialTick;
      mistTickF /= 80.0F;
      float mistFactorY = (float)viewer.func_226278_cu_() / 256.0F;
      return mistTickF * mistFactorY;
   }
}
