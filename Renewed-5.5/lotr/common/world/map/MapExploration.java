package lotr.common.world.map;

import java.util.BitSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lotr.common.LOTRLog;
import lotr.common.data.DataUtil;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketMapExplorationFull;
import lotr.common.network.SPacketMapExplorationTile;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.math.MathHelper;

public class MapExploration {
   private static final int FOG_TILE_SCALE = 48;
   private static final int FOG_TILE_TEXTURE_SCALE = 64;
   private static final int DISCOVERY_RANGE = 144;
   private static final int INITIAL_DISCOVERY_RANGE = 192;
   private static final int TILES_BEYOND_MAP = 8;
   private static final int BITS_PER_TILE = 1;
   private static final int BIT_EXPLORED = 0;
   private int savedMapWidth;
   private int savedMapHeight;
   private int savedMapOriginX;
   private int savedMapOriginZ;
   private BitSet explorationIndex;

   public CompoundNBT save(CompoundNBT nbt) {
      nbt.func_74768_a("MapWidth", this.savedMapWidth);
      nbt.func_74768_a("MapHeight", this.savedMapHeight);
      nbt.func_74768_a("MapOriginX", this.savedMapOriginX);
      nbt.func_74768_a("MapOriginZ", this.savedMapOriginZ);
      if (this.explorationIndex != null) {
         nbt.func_197644_a("ExplorationIndex", this.explorationIndex.toLongArray());
      }

      return nbt;
   }

   public void load(CompoundNBT nbt, UUID playerUuid) {
      this.savedMapWidth = nbt.func_74762_e("MapWidth");
      this.savedMapHeight = nbt.func_74762_e("MapHeight");
      this.savedMapOriginX = nbt.func_74762_e("MapOriginX");
      this.savedMapOriginZ = nbt.func_74762_e("MapOriginZ");
      if (nbt.func_150297_b("ExplorationIndex", 12)) {
         long[] backingArray = nbt.func_197645_o("ExplorationIndex");
         backingArray = checkBackingArrayNotTooLong(backingArray, this.savedMapWidth, this.savedMapHeight, playerUuid);
         this.explorationIndex = BitSet.valueOf(backingArray);
      } else {
         this.explorationIndex = null;
      }

   }

   private static long[] checkBackingArrayNotTooLong(long[] backingArray, int savedMapWidth, int savedMapHeight, UUID playerUuid) {
      int gridWidth = computeGridSizeForMapDimension(savedMapWidth);
      int gridHeight = computeGridSizeForMapDimension(savedMapHeight);
      int numTiles = gridWidth * gridHeight;
      int numBits = numTiles * 1;
      int maxWordsRequired = MathHelper.func_76143_f((double)numBits / 64.0D);
      if (backingArray.length > maxWordsRequired) {
         long[] truncatedArray = new long[maxWordsRequired];
         System.arraycopy(backingArray, 0, truncatedArray, 0, maxWordsRequired);
         LOTRLog.warn("Map exploration playerdata for %s loaded a backing array which is longer than expected for the saved map dimensions (loaded array length is %d, but map size %dx%d => %d exploration tiles => %d long words required) - so truncated to an array of length %d", playerUuid, backingArray.length, savedMapWidth, savedMapHeight, numTiles, maxWordsRequired, truncatedArray.length);
         return truncatedArray;
      } else {
         return backingArray;
      }
   }

   private static int computeGridSizeForMapDimension(int mapDimension) {
      return MathHelper.func_76143_f((double)mapDimension / 48.0D) + 16;
   }

   public void write(PacketBuffer buf) {
      buf.func_150787_b(this.savedMapWidth);
      buf.func_150787_b(this.savedMapHeight);
      buf.func_150787_b(this.savedMapOriginX);
      buf.func_150787_b(this.savedMapOriginZ);
      long[] backingArray = (long[])Optional.ofNullable(this.explorationIndex).map(BitSet::toLongArray).orElse((Object)null);
      DataUtil.writeNullableToBuffer(buf, backingArray, (BiFunction)(PacketBuffer::func_186865_a));
   }

   public void read(PacketBuffer buf) {
      this.savedMapWidth = buf.func_150792_a();
      this.savedMapHeight = buf.func_150792_a();
      this.savedMapOriginX = buf.func_150792_a();
      this.savedMapOriginZ = buf.func_150792_a();
      long[] backingArray = (long[])DataUtil.readNullableFromBuffer(buf, () -> {
         return buf.func_186873_b((long[])null);
      });
      this.explorationIndex = (BitSet)Optional.ofNullable(backingArray).map(BitSet::valueOf).orElse((Object)null);
   }

   public boolean initialiseIfEmptyOrChanged(ServerPlayerEntity player, MapSettings currentMap) {
      boolean needInit = false;
      if (this.explorationIndex == null) {
         needInit = true;
      } else if (!this.doesSavedMapMatchCurrent(currentMap)) {
         needInit = true;
         LOTRLog.info("Re-initialising %s's saved map exploration grid, because the loaded map has since changed scale or origin!", player.func_200200_C_());
      }

      if (needInit) {
         this.explorationIndex = new BitSet();
         this.saveMapValues(currentMap);
         this.unlockNearbyAreas(player, currentMap, 192, false);
         LOTRPacketHandler.sendTo(new SPacketMapExplorationFull(this), player);
      }

      return needInit;
   }

   private void saveMapValues(MapSettings map) {
      this.savedMapWidth = map.getWidth();
      this.savedMapHeight = map.getHeight();
      this.savedMapOriginX = map.getOriginX();
      this.savedMapOriginZ = map.getOriginZ();
   }

   private boolean doesSavedMapMatchCurrent(MapSettings currentMap) {
      return currentMap.getWidth() == this.savedMapWidth && currentMap.getHeight() == this.savedMapHeight && currentMap.getOriginX() == this.savedMapOriginX && currentMap.getOriginZ() == this.savedMapOriginZ;
   }

   private Optional getBitIndexForTileAtMapCoords(int mapX, int mapZ) {
      int gridX = computeGridCoordinateForMapCoordinate(mapX);
      int gridZ = computeGridCoordinateForMapCoordinate(mapZ);
      return this.getBitIndexForTileAtGridCoords(gridX, gridZ);
   }

   private Optional getBitIndexForTileAtGridCoords(int gridX, int gridZ) {
      int gridWidth = computeGridSizeForMapDimension(this.savedMapWidth);
      int gridHeight = computeGridSizeForMapDimension(this.savedMapHeight);
      if (gridX >= 0 && gridX < gridWidth && gridZ >= 0 && gridZ < gridHeight) {
         int gridIndex = gridZ * gridWidth + gridX;
         return Optional.of(gridIndex * 1);
      } else {
         return Optional.empty();
      }
   }

   private static int computeGridCoordinateForMapCoordinate(int mapCoord) {
      return MathHelper.func_76128_c((double)mapCoord / 48.0D) + 8;
   }

   private static int computeGridCoordinateForFractionalMapCoordinate(double mapCoord) {
      return computeGridCoordinateForMapCoordinate(MathHelper.func_76128_c(mapCoord));
   }

   private static int computeMapCoordinateForGridCoordinate(int gridCoord) {
      return (gridCoord - 8) * 48;
   }

   private Optional getBitForGridTile(int mapX, int mapZ, int bitOffset) {
      return this.explorationIndex == null ? Optional.empty() : this.getBitIndexForTileAtMapCoords(mapX, mapZ).map((bitIndex) -> {
         return this.explorationIndex.get(bitIndex + bitOffset);
      });
   }

   private Optional getAllBitsForGridTile(int mapX, int mapZ) {
      return this.explorationIndex == null ? Optional.empty() : this.getBitIndexForTileAtMapCoords(mapX, mapZ).map((bitIndex) -> {
         return this.explorationIndex.get(bitIndex, bitIndex + 1 - 1);
      });
   }

   private void updateBitForGridTile(int mapX, int mapZ, int bitOffset, boolean value) {
      if (this.explorationIndex == null) {
         throw new IllegalStateException("Tried to update the map exploration grid when the backing bitset was null!");
      } else {
         this.getBitIndexForTileAtMapCoords(mapX, mapZ).ifPresent((bitIndex) -> {
            this.explorationIndex.set(bitIndex + bitOffset, value);
         });
      }
   }

   public boolean isExplored(int mapX, int mapZ) {
      return (Boolean)this.getBitForGridTile(mapX, mapZ, 0).orElse(false);
   }

   private boolean isWithinGridBoundsAndNotExplored(int mapX, int mapZ) {
      return (Boolean)this.getBitForGridTile(mapX, mapZ, 0).map((value) -> {
         return !value;
      }).orElse(false);
   }

   private void setExplored(int mapX, int mapZ, boolean explored) {
      this.updateBitForGridTile(mapX, mapZ, 0, explored);
   }

   private boolean isTileAtGridCoordsExplored(int gridX, int gridZ) {
      return (Boolean)this.getBitIndexForTileAtGridCoords(gridX, gridZ).map((bitIndex) -> {
         return this.explorationIndex.get(bitIndex + 0);
      }).orElse(false);
   }

   public boolean onUpdate(ServerPlayerEntity player, MapSettings currentMap) {
      return this.unlockNearbyAreas(player, currentMap, 144, true);
   }

   private boolean unlockNearbyAreas(ServerPlayerEntity player, MapSettings currentMap, int discoveryRange, boolean sendUpdatePackets) {
      int playerMapX = currentMap.worldToMapX((double)MathHelper.func_76128_c(player.func_226277_ct_()));
      int playerMapZ = currentMap.worldToMapZ((double)MathHelper.func_76128_c(player.func_226281_cx_()));
      boolean updatedAny = false;
      int incr = 48;

      for(int dx = -discoveryRange; dx < discoveryRange; dx += incr) {
         for(int dz = -discoveryRange; dz < discoveryRange; dz += incr) {
            if (dx >= -discoveryRange + incr && dx < discoveryRange - incr || dz >= -discoveryRange + incr && dz < discoveryRange - incr) {
               int mapX = playerMapX + dx;
               int mapZ = playerMapZ + dz;
               if (this.isWithinGridBoundsAndNotExplored(mapX, mapZ)) {
                  this.setExplored(mapX, mapZ, true);
                  if (sendUpdatePackets) {
                     this.sendSingleTileUpdate(player, mapX, mapZ);
                  }

                  updatedAny = true;
               }
            }
         }
      }

      return updatedAny;
   }

   private void sendSingleTileUpdate(ServerPlayerEntity player, int mapX, int mapZ) {
      Optional optBitIndex = this.getBitIndexForTileAtMapCoords(mapX, mapZ);
      if (!optBitIndex.isPresent()) {
         LOTRLog.warn("Tried to send an exploration tile update (map pixel %d, %d) to player %s, but the corresponding bit index was calculated as null! This shouldn't happen", mapX, mapZ, player.func_200200_C_().getString());
      } else {
         int bitIndex = (Integer)optBitIndex.get();
         BitSet tileBits = this.explorationIndex.get(bitIndex, bitIndex + 1);
         LOTRPacketHandler.sendTo(new SPacketMapExplorationTile(mapX, mapZ, tileBits), player);
      }

   }

   public void receiveSingleTileUpdateFromServer(int mapX, int mapZ, BitSet tileBits) {
      if (this.explorationIndex == null) {
         LOTRLog.warn("Received an exploration tile update from the server, but the backing array is null! This shouldn't happen");
      } else {
         Optional bitIndex = this.getBitIndexForTileAtMapCoords(mapX, mapZ);
         if (bitIndex.isPresent()) {
            for(int i = 0; i < 1; ++i) {
               this.explorationIndex.set((Integer)bitIndex.get() + i, tileBits.get(i));
            }
         } else {
            LOTRLog.warn("Received an exploration tile update from the server (at map pixel %d, %d) but the tile location was out of bounds of the backing array! This shouldn't happen", mapX, mapZ);
         }

      }
   }

   public Stream streamTilesForRendering(double mapXMin, double mapXMax, double mapZMin, double mapZMax, IProfiler profiler) {
      if (this.explorationIndex == null) {
         return Stream.empty();
      } else {
         int gridWidth = computeGridSizeForMapDimension(this.savedMapWidth);
         int gridHeight = computeGridSizeForMapDimension(this.savedMapHeight);
         int var10000 = gridWidth * gridHeight;
         int textureEdgeWidth = 8;
         int gridXMin = computeGridCoordinateForFractionalMapCoordinate(mapXMin - (double)textureEdgeWidth);
         int gridXMax = computeGridCoordinateForFractionalMapCoordinate(mapXMax + (double)textureEdgeWidth);
         int gridZMin = computeGridCoordinateForFractionalMapCoordinate(mapZMin - (double)textureEdgeWidth);
         int gridZMax = computeGridCoordinateForFractionalMapCoordinate(mapZMax + (double)textureEdgeWidth);
         return IntStream.rangeClosed(gridXMin, gridXMax).mapToObj((gridX) -> {
            return IntStream.rangeClosed(gridZMin, gridZMax).filter((gridZ) -> {
               return !this.isTileAtGridCoordsExplored(gridX, gridZ);
            }).mapToObj((gridZ) -> {
               int mapX = computeMapCoordinateForGridCoordinate(gridX);
               int mapZ = computeMapCoordinateForGridCoordinate(gridZ);
               profiler.func_76320_a("findDistanceFromExplored");
               int distanceFromExplored = this.findDistanceFromExplored(gridWidth, gridHeight, gridX, gridZ, 2);
               profiler.func_76319_b();
               return new MapExplorationTile(mapX - textureEdgeWidth, mapZ - textureEdgeWidth, 64, distanceFromExplored);
            });
         }).flatMap((stream) -> {
            return stream;
         });
      }
   }

   private int findDistanceFromExplored(int gridWidth, int gridHeight, int gridX, int gridZ, int maxSearchRange) {
      for(int range = 1; range <= maxSearchRange; ++range) {
         for(int dx = -range; dx <= range; ++dx) {
            for(int dz = -range; dz <= range; ++dz) {
               if (Math.abs(dx) == range || Math.abs(dz) == range) {
                  int nearGridX = gridX + dx;
                  int nearGridZ = gridZ + dz;
                  if (nearGridX >= 0 && nearGridX < gridWidth && nearGridZ >= 0 && nearGridZ < gridHeight) {
                     int nearGridIndex = nearGridZ * gridWidth + nearGridX;
                     if (this.explorationIndex.get(nearGridIndex * 1 + 0)) {
                        return range;
                     }
                  }
               }
            }
         }
      }

      return maxSearchRange + 1;
   }
}
