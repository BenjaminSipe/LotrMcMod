package lotr.common.world.gen.feature.grassblend;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import lotr.common.world.gen.feature.WeightedFeature;
import lotr.common.world.gen.feature.WeightedRandomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public abstract class GrassBlend {
   private final List entries;
   private WeightedRandomFeatureConfig bakedFeatureConfig;

   public GrassBlend(List entries) {
      this.entries = entries;
   }

   protected static GrassBlend of(Function constructor, Object... weightedConfigs) {
      List entries = new ArrayList();

      for(int i = 0; i < weightedConfigs.length; i += 2) {
         Supplier config = (Supplier)weightedConfigs[i];
         int weight = (Integer)weightedConfigs[i + 1];
         entries.add(new GrassBlend.Entry(config, weight));
      }

      return (GrassBlend)constructor.apply(entries);
   }

   private static WeightedRandomFeatureConfig toFeatureConfig(List entries) {
      List weightedGrassTypes = new ArrayList();
      entries.forEach((entry) -> {
         weightedGrassTypes.add(WeightedFeature.make(Feature.field_227248_z_.func_225566_b_((IFeatureConfig)entry.config.get()), entry.weight));
      });
      return new WeightedRandomFeatureConfig(weightedGrassTypes);
   }

   public WeightedRandomFeatureConfig getFeatureConfig() {
      if (this.bakedFeatureConfig == null) {
         this.bakedFeatureConfig = toFeatureConfig(this.entries);
      }

      return this.bakedFeatureConfig;
   }

   public static class Entry {
      private final Supplier config;
      private final int weight;

      public Entry(Supplier config, int weight) {
         this.config = config;
         this.weight = weight;
      }
   }
}
