package lotr.common.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRLog;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class WeightedRandomFeatureConfig implements IFeatureConfig {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(WeightedFeature.CODEC.listOf().fieldOf("weighted_features").forGetter((config) -> {
         return ImmutableList.copyOf(config.weightedFeatures);
      })).apply(instance, WeightedRandomFeatureConfig::new);
   });
   public final List weightedFeatures;
   private int totalWeight;

   public WeightedRandomFeatureConfig(List features) {
      this.weightedFeatures = features;
      this.updateTotalWeight();
   }

   public static WeightedRandomFeatureConfig fromEntries(Object... entries) {
      try {
         List tempList = new ArrayList();

         for(int i = 0; i < entries.length; i += 2) {
            ConfiguredFeature feature = (ConfiguredFeature)entries[i];
            int weight = (Integer)entries[i + 1];
            WeightedFeature wf = WeightedFeature.make(feature, weight);
            tempList.add(wf);
         }

         return new WeightedRandomFeatureConfig(ImmutableList.copyOf(tempList));
      } catch (ArrayIndexOutOfBoundsException | ClassCastException var6) {
         throw new IllegalArgumentException("Error adding biome trees! A list of (tree1, weight1), (tree2, weight2)... is required", var6);
      }
   }

   private void updateTotalWeight() {
      this.totalWeight = 0;
      this.weightedFeatures.stream().forEach((wf) -> {
         this.totalWeight += wf.weight;
      });
   }

   private int getTotalWeight() {
      return this.totalWeight;
   }

   public ConfiguredFeature getRandomFeature(Random rand) {
      int totalWeight = this.getTotalWeight();
      int chosenWeight = rand.nextInt(totalWeight);
      WeightedFeature selected = null;

      float featureWeight;
      for(Iterator var5 = this.weightedFeatures.iterator(); var5.hasNext(); chosenWeight = (int)((float)chosenWeight - featureWeight)) {
         WeightedFeature weightedFeature = (WeightedFeature)var5.next();
         featureWeight = (float)weightedFeature.weight;
         if ((float)chosenWeight < featureWeight) {
            selected = weightedFeature;
            break;
         }
      }

      if (selected == null) {
         LOTRLog.error("WeightedRandomFeature error: total weight = %d, chosen weight = %d, but selected feature == null", totalWeight, chosenWeight);
      }

      return selected.feature;
   }
}
