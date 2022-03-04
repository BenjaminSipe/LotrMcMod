package lotr.common.tileentity;

import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class LOTRDwarvenGlowLogic {
   private static final float[] lightValueSqrts = new float[16];
   private static final float minSunBrightness = 0.2F;
   private boolean playersNearby;
   private int glowTick;
   private int prevGlowTick;
   private int maxGlowTick = 120;
   private int playerRange = 8;
   private float fullGlow = 0.7F;

   public LOTRDwarvenGlowLogic setGlowTime(int i) {
      this.maxGlowTick = i;
      return this;
   }

   public LOTRDwarvenGlowLogic setPlayerRange(int i) {
      this.playerRange = i;
      return this;
   }

   public LOTRDwarvenGlowLogic setFullGlow(float f) {
      this.fullGlow = f;
      return this;
   }

   public void update(World world, int i, int j, int k) {
      this.prevGlowTick = this.glowTick;
      if (world.field_72995_K) {
         this.playersNearby = world.func_72977_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, (double)this.playerRange) != null;
         if (this.playersNearby && this.glowTick < this.maxGlowTick) {
            ++this.glowTick;
         } else if (!this.playersNearby && this.glowTick > 0) {
            --this.glowTick;
         }
      }

   }

   public float getGlowBrightness(World world, int i, int j, int k, float tick) {
      float glow = ((float)this.prevGlowTick + (float)(this.glowTick - this.prevGlowTick) * tick) / (float)this.maxGlowTick;
      glow *= this.fullGlow;
      float sun = world.func_72971_b(tick);
      float sunNorml = (sun - 0.2F) / 0.8F;
      float night = 1.0F - sunNorml;
      night -= 0.5F;
      if (night < 0.0F) {
         night = 0.0F;
      }

      night *= 2.0F;
      float skylight = lightValueSqrts[world.func_72925_a(EnumSkyBlock.Sky, i, j, k)];
      return glow * night * skylight;
   }

   public int getGlowTick() {
      return this.glowTick;
   }

   public void setGlowTick(int i) {
      this.glowTick = this.prevGlowTick = i;
   }

   public void resetGlowTick() {
      this.glowTick = this.prevGlowTick = 0;
   }

   static {
      for(int i = 0; i <= 15; ++i) {
         lightValueSqrts[i] = MathHelper.func_76129_c((float)i / 15.0F);
      }

   }
}
