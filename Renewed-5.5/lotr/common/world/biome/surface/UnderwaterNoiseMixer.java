package lotr.common.world.biome.surface;

import com.mojang.serialization.Codec;
import java.util.Map;
import java.util.Random;
import lotr.common.util.LOTRUtil;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.IStringSerializable;

public enum UnderwaterNoiseMixer implements IStringSerializable {
   NONE("none", (x, z, in, rand) -> {
      return in;
   }),
   SEA_LATITUDE("sea_latitude", (x, z, in, rand) -> {
      double sandProgressF = (double)MapSettingsManager.serverInstance().getCurrentLoadedMap().getWaterLatitudes().getSandCoverageForLatitude(z);
      boolean sandy = false;
      if (sandProgressF <= 0.0D) {
         sandy = false;
      } else if (sandProgressF >= 1.0D) {
         sandy = true;
      } else {
         double noiseAvg = MiddleEarthSurfaceConfig.getNoise1(x, z, 0.1D, 0.03D);
         double noiseNorm = (noiseAvg + 1.0D) / 2.0D;
         sandy = noiseNorm < sandProgressF;
      }

      return sandy ? Blocks.field_150354_m.func_176223_P() : Blocks.field_150351_n.func_176223_P();
   });

   public static final Codec CODEC = IStringSerializable.func_233023_a_(UnderwaterNoiseMixer::values, UnderwaterNoiseMixer::forName);
   private static final Map NAME_LOOKUP = LOTRUtil.createKeyedEnumMap(values(), UnderwaterNoiseMixer::func_176610_l);
   private final String name;
   private final UnderwaterNoiseMixer.UnderwaterBlockReplacer underwaterBlockReplacer;

   private UnderwaterNoiseMixer(String s, UnderwaterNoiseMixer.UnderwaterBlockReplacer replacer) {
      this.name = s;
      this.underwaterBlockReplacer = replacer;
   }

   public String func_176610_l() {
      return this.name;
   }

   public static UnderwaterNoiseMixer forName(String name) {
      return (UnderwaterNoiseMixer)NAME_LOOKUP.get(name);
   }

   public BlockState getReplacement(int x, int z, BlockState in, Random rand) {
      return this.underwaterBlockReplacer.getReplacement(x, z, in, rand);
   }

   @FunctionalInterface
   public interface UnderwaterBlockReplacer {
      BlockState getReplacement(int var1, int var2, BlockState var3, Random var4);
   }
}
