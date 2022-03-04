package lotr.common.world.biome.surface;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.util.WeightedList;
import net.minecraftforge.fml.RegistryObject;

public final class SurfaceNoiseMixer {
   public static final SurfaceNoiseMixer NONE = createNoiseMixer();
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(SurfaceNoiseMixer.Condition.CODEC.listOf().fieldOf("conditions").forGetter((mixer) -> {
         return mixer.conditions;
      })).apply(instance, SurfaceNoiseMixer::new);
   });
   private final List conditions;
   private final Set cachedSurfaceBlocks;

   private SurfaceNoiseMixer(List conditions) {
      this.conditions = conditions;
      this.cachedSurfaceBlocks = (Set)conditions.stream().flatMap((c) -> {
         return c.weightedStates.func_220655_b().map(AbstractBlockState::func_177230_c);
      }).distinct().collect(Collectors.toSet());
   }

   public static SurfaceNoiseMixer createNoiseMixer(SurfaceNoiseMixer.Condition... conditions) {
      return new SurfaceNoiseMixer(Arrays.asList(conditions));
   }

   public static SurfaceNoiseMixer createNoiseMixer(SurfaceNoiseMixer.Condition.ConditionBuilder... builders) {
      return new SurfaceNoiseMixer((List)Stream.of(builders).map(SurfaceNoiseMixer.Condition.ConditionBuilder::build).collect(Collectors.toList()));
   }

   public BlockState getReplacement(int x, int z, BlockState in, boolean top, Random rand) {
      Iterator var6 = this.conditions.iterator();

      SurfaceNoiseMixer.Condition condition;
      do {
         if (!var6.hasNext()) {
            return in;
         }

         condition = (SurfaceNoiseMixer.Condition)var6.next();
      } while(!condition.passes(x, z, top));

      return condition.getState(rand);
   }

   public boolean isSurfaceBlock(BlockState state) {
      return this.cachedSurfaceBlocks.contains(state.func_177230_c());
   }

   public static final class Condition {
      public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
         return instance.group(Codec.intRange(1, 4).fieldOf("noise_index").forGetter((cond) -> {
            return cond.noiseIndex;
         }), Codec.DOUBLE.listOf().fieldOf("scales").forGetter((cond) -> {
            return (List)DoubleStream.of(cond.scales).boxed().collect(Collectors.toList());
         }), Codec.DOUBLE.listOf().fieldOf("x_scales").orElse(ImmutableList.of()).forGetter((cond) -> {
            return (List)DoubleStream.of(cond.xScales).boxed().collect(Collectors.toList());
         }), Codec.DOUBLE.listOf().fieldOf("z_scales").orElse(ImmutableList.of()).forGetter((cond) -> {
            return (List)DoubleStream.of(cond.zScales).boxed().collect(Collectors.toList());
         }), Codec.intRange(1, Integer.MAX_VALUE).listOf().fieldOf("weights").orElse(ImmutableList.of()).forGetter((cond) -> {
            return (List)IntStream.of(cond.weights).boxed().collect(Collectors.toList());
         }), Codec.DOUBLE.fieldOf("threshold").forGetter((cond) -> {
            return cond.threshold;
         }), WeightedList.func_234002_a_(BlockState.field_235877_b_).fieldOf("states").forGetter((cond) -> {
            return cond.weightedStates;
         }), Codec.BOOL.fieldOf("top_only").orElse(false).forGetter((cond) -> {
            return cond.topOnly;
         })).apply(instance, SurfaceNoiseMixer.Condition::new);
      });
      private final int noiseIndex;
      private final double[] scales;
      private final double[] xScales;
      private final double[] zScales;
      private final int[] weights;
      private final double threshold;
      private final WeightedList weightedStates;
      private final boolean topOnly;

      private Condition(int noiseIndex, List scales, List xScales, List zScales, List weights, double threshold, WeightedList weightedStates, boolean topOnly) {
         this(noiseIndex, toArray(scales), toArray(xScales), toArray(zScales), weights.stream().mapToInt(Integer::intValue).toArray(), threshold, weightedStates, topOnly);
      }

      private static double[] toArray(List list) {
         return list.stream().mapToDouble(Double::doubleValue).toArray();
      }

      private Condition(int noiseIndex, double[] scales, double[] xScales, double[] zScales, int[] weights, double threshold, WeightedList weightedStates, boolean topOnly) {
         this.noiseIndex = noiseIndex;
         this.scales = scales;
         this.xScales = xScales != null ? xScales : new double[0];
         this.zScales = zScales != null ? zScales : new double[0];
         this.weights = weights != null ? weights : new int[0];
         this.threshold = threshold;
         this.weightedStates = weightedStates;
         this.topOnly = topOnly;
      }

      private Condition(SurfaceNoiseMixer.Condition.ConditionBuilder builder) {
         this(builder.noiseIndex, builder.scales, builder.xScales, builder.zScales, builder.weights, builder.threshold, builder.weightedStates, builder.topOnly);
      }

      public static SurfaceNoiseMixer.Condition.ConditionBuilder conditionBuilder() {
         return new SurfaceNoiseMixer.Condition.ConditionBuilder();
      }

      public boolean passes(int x, int z, boolean top) {
         if (this.topOnly && !top) {
            return false;
         } else if (this.noiseIndex == 1) {
            return MiddleEarthSurfaceConfig.getNoise1(x, z, this.scales, this.xScales, this.zScales, this.weights) > this.threshold;
         } else if (this.noiseIndex == 2) {
            return MiddleEarthSurfaceConfig.getNoise2(x, z, this.scales, this.xScales, this.zScales, this.weights) > this.threshold;
         } else if (this.noiseIndex == 3) {
            return MiddleEarthSurfaceConfig.getNoise3(x, z, this.scales, this.xScales, this.zScales, this.weights) > this.threshold;
         } else if (this.noiseIndex == 4) {
            return MiddleEarthSurfaceConfig.getNoise4(x, z, this.scales, this.xScales, this.zScales, this.weights) > this.threshold;
         } else {
            throw new IllegalStateException("Noise index " + this.noiseIndex + " does not correspond to a predefined noise generator");
         }
      }

      public BlockState getState(Random rand) {
         return (BlockState)this.weightedStates.func_226318_b_(rand);
      }

      // $FF: synthetic method
      Condition(SurfaceNoiseMixer.Condition.ConditionBuilder x0, Object x1) {
         this(x0);
      }

      public static class ConditionBuilder {
         private int noiseIndex;
         private double[] scales;
         private double[] xScales;
         private double[] zScales;
         private double zScale;
         private int[] weights;
         private double threshold;
         private WeightedList weightedStates;
         private boolean topOnly;

         private ConditionBuilder() {
            this.zScale = 1.0D;
            this.threshold = Double.NEGATIVE_INFINITY;
            this.topOnly = false;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder noiseIndex(int noiseIndex) {
            this.noiseIndex = noiseIndex;
            return this;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder scales(double... scales) {
            this.scales = scales;
            return this;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder xScales(double... xScales) {
            this.xScales = xScales;
            return this;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder zScales(double... zScales) {
            this.zScales = zScales;
            return this;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder weights(int... weights) {
            this.weights = weights;
            return this;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder threshold(double threshold) {
            this.threshold = threshold;
            return this;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder state(BlockState state) {
            this.weightedStates = (new WeightedList()).func_226313_a_(state, 1);
            return this;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder states(Object... entries) {
            this.weightedStates = new WeightedList();

            for(int i = 0; i < entries.length; i += 2) {
               Object obj1 = entries[i];
               BlockState state;
               if (obj1 instanceof BlockState) {
                  state = (BlockState)obj1;
               } else if (obj1 instanceof Block) {
                  state = ((Block)obj1).func_176223_P();
               } else {
                  if (!(obj1 instanceof RegistryObject)) {
                     throw new IllegalArgumentException("Surface noise mixer cannot convert object " + obj1.toString() + " to a weighted blockstate");
                  }

                  state = ((Block)((RegistryObject)obj1).get()).func_176223_P();
               }

               int weight = (Integer)entries[i + 1];
               this.weightedStates.func_226313_a_(state, weight);
            }

            return this;
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder state(Block block) {
            return this.state(block.func_176223_P());
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder state(RegistryObject blockSup) {
            return this.state((Block)blockSup.get());
         }

         public SurfaceNoiseMixer.Condition.ConditionBuilder topOnly() {
            this.topOnly = true;
            return this;
         }

         public SurfaceNoiseMixer.Condition build() {
            if (this.noiseIndex >= 1 && this.noiseIndex <= 4) {
               if (this.scales == null) {
                  throw new IllegalArgumentException("scales not set");
               } else if (this.xScales != null && this.xScales.length != this.scales.length) {
                  throw new IllegalArgumentException("number of custom xScales does not match number of scales");
               } else if (this.zScales != null && this.zScales.length != this.scales.length) {
                  throw new IllegalArgumentException("number of custom zScales does not match number of scales");
               } else if (this.weights != null && this.weights.length != this.scales.length) {
                  throw new IllegalArgumentException("number of custom weights does not match number of scales");
               } else if (this.threshold == Double.NEGATIVE_INFINITY) {
                  throw new IllegalArgumentException("threshold not set");
               } else if (this.weightedStates != null && !this.weightedStates.func_234005_b_()) {
                  return new SurfaceNoiseMixer.Condition(this);
               } else {
                  throw new IllegalArgumentException("block state(s) not set");
               }
            } else {
               throw new IllegalArgumentException("noiseIndex out of supported range");
            }
         }

         // $FF: synthetic method
         ConditionBuilder(Object x0) {
            this();
         }
      }
   }
}
