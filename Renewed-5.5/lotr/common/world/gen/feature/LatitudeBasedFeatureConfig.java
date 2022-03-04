package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.function.IntToDoubleFunction;
import lotr.common.util.LOTRUtil;
import lotr.common.world.map.BothWaterLatitudeSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class LatitudeBasedFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(ConfiguredFeature.field_242763_a.fieldOf("feature").forGetter((config) -> {
         return config.feature;
      }), LatitudeBasedFeatureConfig.LatitudeConfiguration.CODEC.fieldOf("latitude_config").forGetter((config) -> {
         return config.latitudeConfig;
      })).apply(instance, LatitudeBasedFeatureConfig::new);
   });
   public final ConfiguredFeature feature;
   public final LatitudeBasedFeatureConfig.LatitudeConfiguration latitudeConfig;

   public LatitudeBasedFeatureConfig(ConfiguredFeature cf, LatitudeBasedFeatureConfig.LatitudeConfiguration lat) {
      this.feature = cf;
      this.latitudeConfig = lat;
   }

   public static class LatitudeConfiguration {
      public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
         return instance.group(LatitudeBasedFeatureConfig.LatitudeValuesType.CODEC.fieldOf("latitude_type").forGetter((config) -> {
            return config.type;
         }), Codec.BOOL.fieldOf("invert").orElse(false).forGetter((config) -> {
            return config.invert;
         }), Codec.floatRange(0.0F, 1.0F).fieldOf("proportional_min").orElse(0.0F).forGetter((config) -> {
            return config.min;
         }), Codec.floatRange(0.0F, 1.0F).fieldOf("proportional_max").orElse(1.0F).forGetter((config) -> {
            return config.max;
         })).apply(instance, LatitudeBasedFeatureConfig.LatitudeConfiguration::new);
      });
      public final LatitudeBasedFeatureConfig.LatitudeValuesType type;
      public final boolean invert;
      public final float min;
      public final float max;

      private LatitudeConfiguration(LatitudeBasedFeatureConfig.LatitudeValuesType type, boolean invert, float min, float max) {
         this.type = type;
         this.invert = invert;
         this.min = min;
         this.max = max;
      }

      public static LatitudeBasedFeatureConfig.LatitudeConfiguration of(LatitudeBasedFeatureConfig.LatitudeValuesType type, boolean invert) {
         return new LatitudeBasedFeatureConfig.LatitudeConfiguration(type, invert, 0.0F, 1.0F);
      }

      public static LatitudeBasedFeatureConfig.LatitudeConfiguration of(LatitudeBasedFeatureConfig.LatitudeValuesType type) {
         return of(type, false);
      }

      public static LatitudeBasedFeatureConfig.LatitudeConfiguration ofInverted(LatitudeBasedFeatureConfig.LatitudeValuesType type) {
         return of(type, true);
      }

      public LatitudeBasedFeatureConfig.LatitudeConfiguration min(float newMin) {
         return new LatitudeBasedFeatureConfig.LatitudeConfiguration(this.type, this.invert, newMin, this.max);
      }

      public LatitudeBasedFeatureConfig.LatitudeConfiguration max(float newMax) {
         return new LatitudeBasedFeatureConfig.LatitudeConfiguration(this.type, this.invert, this.min, newMax);
      }
   }

   public static enum LatitudeValuesType implements IStringSerializable {
      ICE("ice", (z) -> {
         return (double)waterSettings().getIceCoverageForLatitude(z);
      }),
      SAND("sand", (z) -> {
         return (double)waterSettings().getSandCoverageForLatitude(z);
      }),
      CORAL("coral", (z) -> {
         return (double)waterSettings().getCoralForLatitude(z);
      });

      public static final Codec CODEC = IStringSerializable.func_233023_a_(LatitudeBasedFeatureConfig.LatitudeValuesType::values, LatitudeBasedFeatureConfig.LatitudeValuesType::forName);
      private static final Map NAME_LOOKUP = LOTRUtil.createKeyedEnumMap(values(), LatitudeBasedFeatureConfig.LatitudeValuesType::func_176610_l);
      private final String code;
      private final IntToDoubleFunction zProgressGetter;

      private LatitudeValuesType(String s, IntToDoubleFunction zProgress) {
         this.code = s;
         this.zProgressGetter = zProgress;
      }

      public String func_176610_l() {
         return this.code;
      }

      public static LatitudeBasedFeatureConfig.LatitudeValuesType forName(String name) {
         return (LatitudeBasedFeatureConfig.LatitudeValuesType)NAME_LOOKUP.get(name);
      }

      private static BothWaterLatitudeSettings waterSettings() {
         return MapSettingsManager.serverInstance().getCurrentLoadedMap().getWaterLatitudes();
      }

      public double getLatitudeProgress(int z) {
         return this.zProgressGetter.applyAsDouble(z);
      }
   }
}
