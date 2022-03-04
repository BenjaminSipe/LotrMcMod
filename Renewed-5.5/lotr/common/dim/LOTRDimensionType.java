package lotr.common.dim;

import com.google.common.math.IntMath;
import java.util.OptionalLong;
import lotr.common.time.LOTRTime;
import lotr.common.time.MiddleEarthCalendar;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.thread.EffectiveSide;

public abstract class LOTRDimensionType extends DimensionType {
   protected LOTRDimensionType(OptionalLong fixedTime, boolean hasSkyLight, boolean hasCeiling, boolean ultrawarm, boolean natural, boolean bedWorks, int logicalHeight, ResourceLocation infiniburn, ResourceLocation key, float ambientLight) {
      super(fixedTime, hasSkyLight, hasCeiling, ultrawarm, natural, 1.0D, false, bedWorks, false, false, logicalHeight, infiniburn, key, ambientLight);
   }

   public long getWorldTime(World world) {
      return LOTRTime.getWorldTime(world);
   }

   public float func_236032_b_(long worldTime) {
      long worldTimeME = LOTRTime.getWorldTime(EffectiveSide.get().isClient());
      double d0 = MathHelper.func_181162_h((double)worldTimeME / 48000.0D - 0.25D);
      double d1 = 0.5D - Math.cos(d0 * 3.141592653589793D) / 2.0D;
      return (float)(d0 * 2.0D + d1) / 3.0F;
   }

   public int func_236035_c_(long worldTime) {
      int day = MiddleEarthCalendar.currentDay;
      return IntMath.mod(day, field_235998_b_.length);
   }

   public boolean isLunarEclipse(World world) {
      long worldTimeME = this.getWorldTime(world);
      int day = MiddleEarthCalendar.currentDay;
      return this.func_236035_c_(worldTimeME) == 0 && IntMath.mod(day / field_235998_b_.length, 4) == 3;
   }
}
