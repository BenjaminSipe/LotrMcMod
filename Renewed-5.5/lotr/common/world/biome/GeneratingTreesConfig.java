package lotr.common.world.biome;

import lotr.common.world.gen.feature.LatitudeBasedFeatureConfig;
import lotr.common.world.gen.placement.TreeClustersConfig;

public class GeneratingTreesConfig {
   public final Object[] weightedTrees;
   public final TreeClustersConfig clusterConfig;
   public final LatitudeBasedFeatureConfig.LatitudeConfiguration latitudeConfig;

   private GeneratingTreesConfig(GeneratingTreesConfig.Builder builder) {
      this.weightedTrees = builder.weightedTrees;
      this.clusterConfig = builder.clusterConfig;
      this.latitudeConfig = builder.latitudeConfig;
   }

   public int getCount() {
      return this.clusterConfig.count;
   }

   public float getExtraChance() {
      return this.clusterConfig.extraChance;
   }

   public int getExtraCount() {
      return this.clusterConfig.extraCount;
   }

   public float getTreeCountApproximation() {
      return (float)this.getCount() + this.getExtraChance() * (float)this.getExtraCount();
   }

   public boolean shouldUpdateBiomeTreeAmount() {
      return !this.clusterConfig.hasLayerLimit() || this.clusterConfig.isLayerUpperLimit;
   }

   public int getTreeLayerUpperLimit() {
      return this.clusterConfig.hasLayerLimit() && this.clusterConfig.isLayerUpperLimit ? this.clusterConfig.layerLimit : Integer.MAX_VALUE;
   }

   public TreeClustersConfig makePlacementForLeafBushes() {
      return TreeClustersConfig.builder().count(this.getCount() / 2).extraChance(this.getExtraChance()).extraCount(this.getExtraCount()).layerLimit(this.clusterConfig.layerLimit, this.clusterConfig.isLayerUpperLimit).build();
   }

   public TreeClustersConfig makePlacementForFallenLeaves() {
      return TreeClustersConfig.builder().count(this.getCount() / 2).extraChance(this.getExtraChance()).extraCount(this.getExtraCount()).layerLimit(this.clusterConfig.layerLimit, this.clusterConfig.isLayerUpperLimit).build();
   }

   public static GeneratingTreesConfig.Builder builder() {
      return new GeneratingTreesConfig.Builder();
   }

   // $FF: synthetic method
   GeneratingTreesConfig(GeneratingTreesConfig.Builder x0, Object x1) {
      this(x0);
   }

   public static class Builder {
      private Object[] weightedTrees;
      private TreeClustersConfig clusterConfig;
      private LatitudeBasedFeatureConfig.LatitudeConfiguration latitudeConfig;

      private Builder() {
      }

      public GeneratingTreesConfig.Builder weightedTrees(Object[] weightedTrees) {
         this.weightedTrees = weightedTrees;
         return this;
      }

      public GeneratingTreesConfig.Builder clusterConfig(TreeClustersConfig clusterConfig) {
         this.clusterConfig = clusterConfig;
         return this;
      }

      public GeneratingTreesConfig.Builder latitudeConfig(LatitudeBasedFeatureConfig.LatitudeConfiguration latitudeConfig) {
         this.latitudeConfig = latitudeConfig;
         return this;
      }

      public GeneratingTreesConfig build() {
         return new GeneratingTreesConfig(this);
      }

      // $FF: synthetic method
      Builder(Object x0) {
         this();
      }
   }
}
