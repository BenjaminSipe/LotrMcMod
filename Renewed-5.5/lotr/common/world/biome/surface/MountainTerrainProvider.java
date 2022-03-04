package lotr.common.world.biome.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.fml.RegistryObject;

public final class MountainTerrainProvider {
   public static final MountainTerrainProvider NONE = createMountainTerrain();
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(MountainTerrainProvider.MountainLayer.CODEC.listOf().fieldOf("layers").forGetter((mixer) -> {
         return mixer.layers;
      })).apply(instance, MountainTerrainProvider::new);
   });
   private final List layers;

   private MountainTerrainProvider(List layers) {
      this.layers = layers;
   }

   public static MountainTerrainProvider createMountainTerrain(MountainTerrainProvider.MountainLayer... layers) {
      return new MountainTerrainProvider(Arrays.asList(layers));
   }

   public static MountainTerrainProvider createMountainTerrain(MountainTerrainProvider.MountainLayer.MountainLayerBuilder... builders) {
      return new MountainTerrainProvider((List)Stream.of(builders).map(MountainTerrainProvider.MountainLayer.MountainLayerBuilder::build).collect(Collectors.toList()));
   }

   public BlockState getReplacement(int x, int z, int y, BlockState in, BlockState stone, boolean top, int stoneNoiseDepth) {
      Iterator var8 = this.layers.iterator();

      MountainTerrainProvider.MountainLayer layer;
      do {
         if (!var8.hasNext()) {
            return in;
         }

         layer = (MountainTerrainProvider.MountainLayer)var8.next();
      } while(!layer.passes(y, in, stone, top, stoneNoiseDepth));

      return layer.getState(stone);
   }

   public static final class MountainLayer {
      public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
         return instance.group(Codec.INT.fieldOf("above").forGetter((layer) -> {
            return layer.above;
         }), BlockState.field_235877_b_.optionalFieldOf("state").forGetter((layer) -> {
            return layer.state;
         }), Codec.BOOL.fieldOf("use_stone").orElse(false).forGetter((cond) -> {
            return cond.useStone;
         }), Codec.BOOL.fieldOf("replace_stone").orElse(true).forGetter((cond) -> {
            return cond.replaceStone;
         }), Codec.BOOL.fieldOf("top_only").orElse(false).forGetter((cond) -> {
            return cond.topOnly;
         })).apply(instance, MountainTerrainProvider.MountainLayer::new);
      });
      private final int above;
      private final Optional state;
      private final boolean useStone;
      private final boolean replaceStone;
      private final boolean topOnly;

      private MountainLayer(int above, Optional state, boolean useStone, boolean replaceStone, boolean topOnly) {
         this.above = above;
         this.state = state;
         this.useStone = useStone;
         this.replaceStone = replaceStone;
         this.topOnly = topOnly;
      }

      private MountainLayer(MountainTerrainProvider.MountainLayer.MountainLayerBuilder builder) {
         this(builder.above, Optional.ofNullable(builder.state), builder.useStone, builder.replaceStone, builder.topOnly);
      }

      public static MountainTerrainProvider.MountainLayer.MountainLayerBuilder layerBuilder() {
         return new MountainTerrainProvider.MountainLayer.MountainLayerBuilder();
      }

      public boolean passes(int y, BlockState in, BlockState stone, boolean top, int stoneNoiseDepth) {
         if (this.topOnly && !top) {
            return false;
         } else if (!this.replaceStone && in.func_177230_c() == stone.func_177230_c()) {
            return false;
         } else {
            return y >= this.above - stoneNoiseDepth;
         }
      }

      public BlockState getState(BlockState stone) {
         return this.useStone ? stone : (BlockState)this.state.get();
      }

      // $FF: synthetic method
      MountainLayer(MountainTerrainProvider.MountainLayer.MountainLayerBuilder x0, Object x1) {
         this(x0);
      }

      public static class MountainLayerBuilder {
         private int above;
         private BlockState state;
         private boolean useStone;
         private boolean replaceStone;
         private boolean topOnly;

         private MountainLayerBuilder() {
            this.above = -1;
            this.useStone = false;
            this.replaceStone = true;
            this.topOnly = false;
         }

         public MountainTerrainProvider.MountainLayer.MountainLayerBuilder above(int above) {
            this.above = above;
            return this;
         }

         public MountainTerrainProvider.MountainLayer.MountainLayerBuilder state(BlockState state) {
            this.state = state;
            this.useStone = false;
            return this;
         }

         public MountainTerrainProvider.MountainLayer.MountainLayerBuilder state(Block block) {
            return this.state(block.func_176223_P());
         }

         public MountainTerrainProvider.MountainLayer.MountainLayerBuilder state(RegistryObject blockSup) {
            return this.state((Block)blockSup.get());
         }

         public MountainTerrainProvider.MountainLayer.MountainLayerBuilder useStone() {
            this.state = null;
            this.useStone = true;
            this.excludeStone();
            return this;
         }

         public MountainTerrainProvider.MountainLayer.MountainLayerBuilder excludeStone() {
            this.replaceStone = false;
            return this;
         }

         public MountainTerrainProvider.MountainLayer.MountainLayerBuilder topOnly() {
            this.topOnly = true;
            return this;
         }

         public MountainTerrainProvider.MountainLayer build() {
            if (this.above < 0) {
               throw new IllegalArgumentException("above y-value not set or too low");
            } else if (this.state == null && !this.useStone) {
               throw new IllegalArgumentException("block state not set and use_stone is not enabled instead");
            } else {
               return new MountainTerrainProvider.MountainLayer(this);
            }
         }

         // $FF: synthetic method
         MountainLayerBuilder(Object x0) {
            this();
         }
      }
   }
}
