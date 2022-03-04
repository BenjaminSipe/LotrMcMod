package lotr.client.render.model.connectedtex;

import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.common.block.GateBlock;
import lotr.common.block.WattleAndDaubBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class ConnectedTexture3DContext implements IModelData {
   private static final Map ALL_RELEVANT_3D_CONTEXTS = (Map)Util.func_200696_a(new HashMap(), (map) -> {
      int maxCombinedBits = (1 << ConnectedTexture3DContext.PositionOfInterest.values().length) - 1;

      for(int combinedBitFlag = 0; combinedBitFlag <= maxCombinedBits; ++combinedBitFlag) {
         ConnectedTexture3DContext ctx = new ConnectedTexture3DContext(combinedBitFlag);
         if (!ctx.hasIrrelevantPositions()) {
            map.put(combinedBitFlag, ctx);
         }
      }

   });
   private static final Map CONTEXT_TO_FACE_2D_CONTEXT_MAP;
   private final int combinedPositionBitFlags;

   private ConnectedTexture3DContext(Set positionsOfInterest) {
      int combined = 0;

      ConnectedTexture3DContext.PositionOfInterest poi;
      for(Iterator var3 = positionsOfInterest.iterator(); var3.hasNext(); combined |= poi.bitFlag) {
         poi = (ConnectedTexture3DContext.PositionOfInterest)var3.next();
      }

      this.combinedPositionBitFlags = combined;
   }

   private ConnectedTexture3DContext(int combinedPositionBitFlags) {
      this.combinedPositionBitFlags = combinedPositionBitFlags;
   }

   public static ConnectedTexture3DContext newEmptyContext() {
      return new ConnectedTexture3DContext(ImmutableSet.of());
   }

   public static ConnectedTexture3DContext newContextFrom(Collection pois) {
      return new ConnectedTexture3DContext(EnumSet.copyOf(pois));
   }

   public static ConnectedTexture3DContext gatherFromWorld(IBlockDisplayReader world, BlockPos pos, BlockState state, TransformationMatrix blockstateRotation, ConnectedTexture3DContext.BlockConnectionType connectionType) {
      Set pois = EnumSet.noneOf(ConnectedTexture3DContext.PositionOfInterest.class);
      Mutable movingPos = new Mutable();
      ConnectedTexture3DContext.PositionOfInterest[] var7 = ConnectedTexture3DContext.PositionOfInterest.values();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         ConnectedTexture3DContext.PositionOfInterest poi = var7[var9];
         movingPos.func_189533_g(pos);
         Stream var10000 = poi.offsets.stream();
         blockstateRotation.getClass();
         List poiOffsets = (List)var10000.map(blockstateRotation::rotateTransform).collect(Collectors.toList());
         poiOffsets.forEach(movingPos::func_189536_c);
         if (connectionType.connects(state, world.func_180495_p(movingPos), poiOffsets)) {
            pois.add(poi);
         }
      }

      pruneIrrelevantPositions(pois);
      return new ConnectedTexture3DContext(pois);
   }

   private static void pruneIrrelevantPositions(Set pois) {
      pois.removeIf((poi) -> {
         boolean var10000;
         if (poi.isCompoundOffset()) {
            pois.getClass();
            if (isIrrelevantCompoundOffsetPosition(poi, pois::contains)) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      });
   }

   private static boolean isIrrelevantCompoundOffsetPosition(ConnectedTexture3DContext.PositionOfInterest poi, Predicate isOtherPoiContained) {
      Stream var10000 = poi.offsets.stream();
      Map var10001 = ConnectedTexture3DContext.PositionOfInterest.SIMPLE_OFFSET_POSITIONS;
      var10001.getClass();
      return var10000.map(var10001::get).noneMatch(isOtherPoiContained);
   }

   public boolean has(ConnectedTexture3DContext.PositionOfInterest poi) {
      return (this.combinedPositionBitFlags & poi.bitFlag) != 0;
   }

   public int getCombinedBitFlags() {
      return this.combinedPositionBitFlags;
   }

   private boolean hasIrrelevantPositions() {
      return ConnectedTexture3DContext.PositionOfInterest.COMPOUND_OFFSET_POSITIONS.stream().filter(this::has).anyMatch((poi) -> {
         return isIrrelevantCompoundOffsetPosition(poi, this::has);
      });
   }

   public ConnectedTexture2DContext getFace2DContext(Direction face) {
      return (ConnectedTexture2DContext)((Map)CONTEXT_TO_FACE_2D_CONTEXT_MAP.get(this.combinedPositionBitFlags)).get(face);
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other != null && other.getClass() == this.getClass()) {
         ConnectedTexture3DContext otherData = (ConnectedTexture3DContext)other;
         return this.combinedPositionBitFlags == otherData.combinedPositionBitFlags;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getCombinedBitFlags();
   }

   public String toString() {
      String s = "ConnectedTexture3DContext[";
      int added = 0;
      ConnectedTexture3DContext.PositionOfInterest[] var3 = ConnectedTexture3DContext.PositionOfInterest.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ConnectedTexture3DContext.PositionOfInterest poi = var3[var5];
         if (this.has(poi)) {
            if (added > 0) {
               s = s + ", ";
            }

            s = s + poi.name();
            ++added;
         }
      }

      s = s + "]";
      return s;
   }

   public boolean hasProperty(ModelProperty prop) {
      return false;
   }

   public Object getData(ModelProperty prop) {
      return null;
   }

   public Object setData(ModelProperty prop, Object data) {
      return null;
   }

   static {
      CONTEXT_TO_FACE_2D_CONTEXT_MAP = (Map)ALL_RELEVANT_3D_CONTEXTS.entrySet().stream().collect(Collectors.toMap(Entry::getKey, (entry) -> {
         ConnectedTexture3DContext ctx3d = (ConnectedTexture3DContext)entry.getValue();
         return (Map)Stream.of(Direction.values()).collect(Collectors.toMap(UnaryOperator.identity(), (dir) -> {
            return ConnectedTextureFaceMapper.get2dFrom3d(ctx3d, dir);
         }));
      }));
   }

   public static enum BlockConnectionType {
      SAME_BLOCK("same_block", (state, otherState, offsets) -> {
         return state.func_177230_c() == otherState.func_177230_c();
      }),
      NO_CONNECTIONS("no_connections", (state, otherState, offsets) -> {
         return false;
      }),
      GATE("gate", GateBlock::doBlocksConnectVisually),
      CONNECTED_WATTLE("connected_wattle", WattleAndDaubBlock::doBlocksConnectVisually);

      private final String name;
      private final ConnectedTexture3DContext.BlockConnectionType.BlockConnectionTest connectionTest;
      private static final Map TYPES_BY_NAME = (Map)Stream.of(values()).collect(Collectors.toMap((type) -> {
         return type.name;
      }, UnaryOperator.identity()));

      private BlockConnectionType(String name, ConnectedTexture3DContext.BlockConnectionType.BlockConnectionTest connectionTest) {
         this.name = name;
         this.connectionTest = connectionTest;
      }

      public boolean connects(BlockState state, BlockState otherState, List offsets) {
         return this.connectionTest.test(state, otherState, offsets);
      }

      public static ConnectedTexture3DContext.BlockConnectionType getByName(String name) {
         return (ConnectedTexture3DContext.BlockConnectionType)TYPES_BY_NAME.get(name);
      }

      @FunctionalInterface
      public interface BlockConnectionTest {
         boolean test(BlockState var1, BlockState var2, List var3);
      }
   }

   public static enum PositionOfInterest {
      DOWN("down", new Direction[]{Direction.DOWN}),
      UP("up", new Direction[]{Direction.UP}),
      NORTH("north", new Direction[]{Direction.NORTH}),
      SOUTH("south", new Direction[]{Direction.SOUTH}),
      WEST("west", new Direction[]{Direction.WEST}),
      EAST("east", new Direction[]{Direction.EAST}),
      DOWN_NORTH("down_north", new Direction[]{Direction.DOWN, Direction.NORTH}),
      DOWN_SOUTH("down_south", new Direction[]{Direction.DOWN, Direction.SOUTH}),
      DOWN_WEST("down_west", new Direction[]{Direction.DOWN, Direction.WEST}),
      DOWN_EAST("down_east", new Direction[]{Direction.DOWN, Direction.EAST}),
      UP_NORTH("up_north", new Direction[]{Direction.UP, Direction.NORTH}),
      UP_SOUTH("up_south", new Direction[]{Direction.UP, Direction.SOUTH}),
      UP_WEST("up_west", new Direction[]{Direction.UP, Direction.WEST}),
      UP_EAST("up_east", new Direction[]{Direction.UP, Direction.EAST}),
      NORTH_WEST("north_west", new Direction[]{Direction.NORTH, Direction.WEST}),
      NORTH_EAST("north_east", new Direction[]{Direction.NORTH, Direction.EAST}),
      SOUTH_WEST("south_west", new Direction[]{Direction.SOUTH, Direction.WEST}),
      SOUTH_EAST("south_east", new Direction[]{Direction.SOUTH, Direction.EAST});

      public final int bitFlag = 1 << this.ordinal();
      public final String nameInJson;
      public final List offsets;
      private static final Map POSITIONS_BY_NAME = (Map)Stream.of(values()).collect(Collectors.toMap((poi) -> {
         return poi.nameInJson;
      }, UnaryOperator.identity()));
      public static final Map SIMPLE_OFFSET_POSITIONS = (Map)Stream.of(values()).filter(ConnectedTexture3DContext.PositionOfInterest::isSimpleOffset).collect(Collectors.toMap((poi) -> {
         return (Direction)poi.offsets.get(0);
      }, UnaryOperator.identity()));
      public static final List COMPOUND_OFFSET_POSITIONS = (List)Stream.of(values()).filter(ConnectedTexture3DContext.PositionOfInterest::isCompoundOffset).collect(Collectors.toList());

      private PositionOfInterest(String s, Direction... offs) {
         this.nameInJson = s;
         this.offsets = Arrays.asList(offs);
         if (this.offsets.isEmpty()) {
            throw new IllegalArgumentException("Connected tex: position of interest '" + this.nameInJson + "' offsets must not be empty");
         }
      }

      public boolean isSimpleOffset() {
         return this.offsets.size() == 1;
      }

      public boolean isCompoundOffset() {
         return this.offsets.size() > 1;
      }

      public static ConnectedTexture3DContext.PositionOfInterest getByJsonName(String name) {
         return (ConnectedTexture3DContext.PositionOfInterest)POSITIONS_BY_NAME.get(name);
      }
   }
}
