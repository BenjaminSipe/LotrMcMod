package lotr.common.world.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import lotr.common.LOTRLog;
import lotr.common.block.CustomWaypointMarkerBlock;
import lotr.common.block.LOTRBlockMaterial;
import lotr.common.data.LOTRLevelData;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTRTags;
import lotr.common.tileentity.CustomWaypointMarkerTileEntity;
import lotr.common.util.LOTRUtil;
import lotr.common.util.UsernameHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.Property;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;

public class CustomWaypointStructureHandler {
   public static final CustomWaypointStructureHandler INSTANCE = new CustomWaypointStructureHandler();
   private static final int SPAWN_EXCLUSION_RADIUS = 500;
   private static final int MAP_WAYPOINT_EXCLUSION_RADIUS = 200;
   private Map playersClickedOnBlocksToCreate = new HashMap();
   private Map playersSentProtectionMessageTimes = new HashMap();
   private static final int PROTECTION_MESSAGE_INTERVAL_MILLIS = 3000;

   private CustomWaypointStructureHandler() {
   }

   public boolean isFocalPoint(World world, BlockPos focalPos) {
      return this.isCorrectDimension(world) ? this.isValidCentrepiece(world, focalPos) : false;
   }

   public boolean isFocalPointOfCompletableStructure(ServerWorld world, BlockPos focalPos) {
      return this.isFocalPointOfCompletableStructure(world, focalPos, (text) -> {
      });
   }

   public boolean isFocalPointOfCompletableStructure(ServerWorld world, BlockPos focalPos, Consumer messageCallback) {
      if (this.isTooCloseToExistingCustomWaypoint(world, focalPos, true)) {
         messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.tooClose.otherCustomWaypoint"));
         return false;
      } else if (this.isTooCloseToSpawnOrMapWaypoint(world, focalPos, messageCallback)) {
         return false;
      } else if (this.isFocalPointOfValidStructure(world, focalPos, messageCallback)) {
         return this.hasValidMapOnFocalPoint(world, focalPos, messageCallback);
      } else {
         return false;
      }
   }

   private boolean isFocalPointOfValidStructure(World world, BlockPos focalPos, Consumer messageCallback) {
      if (this.isFocalPoint(world, focalPos)) {
         List boundsToCheckEmpty = (List)this.streamPositionsInBoundingBox(focalPos).collect(Collectors.toList());
         boundsToCheckEmpty.remove(focalPos);
         if (this.testForBlock(world, focalPos.func_177984_a(), boundsToCheckEmpty, this::isValidPillar) && this.testForBlock(world, focalPos.func_177977_b(), boundsToCheckEmpty, this::isValidPillar) && this.testForBlock(world, focalPos.func_177979_c(2), boundsToCheckEmpty, this::isValidPillar)) {
            if (!this.testForBlock(world, focalPos.func_177981_b(2), boundsToCheckEmpty, this::isValidTopLight)) {
               messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.invalid.light"));
               return false;
            } else {
               BlockPos buttressCentre = focalPos.func_177979_c(2);
               if (this.testForBlock(world, buttressCentre.func_177978_c(), boundsToCheckEmpty, this.testValidButtress(Direction.SOUTH)) && this.testForBlock(world, buttressCentre.func_177968_d(), boundsToCheckEmpty, this.testValidButtress(Direction.NORTH)) && this.testForBlock(world, buttressCentre.func_177976_e(), boundsToCheckEmpty, this.testValidButtress(Direction.EAST)) && this.testForBlock(world, buttressCentre.func_177974_f(), boundsToCheckEmpty, this.testValidButtress(Direction.WEST))) {
                  BlockPos crownCentre = focalPos.func_177981_b(1);
                  if (this.testForBlock(world, crownCentre.func_177978_c(), boundsToCheckEmpty, this.testValidCrown(Direction.SOUTH)) && this.testForBlock(world, crownCentre.func_177968_d(), boundsToCheckEmpty, this.testValidCrown(Direction.NORTH)) && this.testForBlock(world, crownCentre.func_177976_e(), boundsToCheckEmpty, this.testValidCrown(Direction.EAST)) && this.testForBlock(world, crownCentre.func_177974_f(), boundsToCheckEmpty, this.testValidCrown(Direction.WEST))) {
                     boolean validBase = this.streamPositionsInSolidBase(focalPos).allMatch((pos) -> {
                        return this.isValidBase(world, pos);
                     });
                     if (!validBase) {
                        messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.invalid.base"));
                        return false;
                     } else {
                        List boundsNotEmpty = (List)boundsToCheckEmpty.stream().filter((pos) -> {
                           return !this.isEmptyBlockForBounds(world, pos);
                        }).collect(Collectors.toList());
                        if (!boundsNotEmpty.isEmpty()) {
                           messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.invalid.notEmpty", new Object[]{boundsNotEmpty.size()}));
                           return false;
                        } else {
                           return true;
                        }
                     }
                  } else {
                     messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.invalid.crown"));
                     return false;
                  }
               } else {
                  messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.invalid.buttress"));
                  return false;
               }
            }
         } else {
            messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.invalid.pillar"));
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isCorrectDimension(World world) {
      return LOTRDimensions.isDimension(world, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY);
   }

   private boolean isTooCloseToSpawnOrMapWaypoint(ServerWorld world, BlockPos focalPos, Consumer messageCallback) {
      BlockPos spawnPoint = LOTRDimensions.getDimensionSpawnPoint(world);
      if (focalPos.func_218141_a(spawnPoint, 500.0D)) {
         messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.tooClose.spawn"));
         return true;
      } else {
         List mapWaypointsTooClose = (List)MapSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedMap().getWaypoints().stream().filter((wp) -> {
            double dx = (double)(wp.getWorldX() - focalPos.func_177958_n());
            double dz = (double)(wp.getWorldZ() - focalPos.func_177952_p());
            double dSq = dx * dx + dz * dz;
            return dSq < 40000.0D;
         }).collect(Collectors.toList());
         if (!mapWaypointsTooClose.isEmpty()) {
            messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.tooClose.mapWaypoint", new Object[]{((MapWaypoint)mapWaypointsTooClose.get(0)).getDisplayName()}));
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean isTooCloseToExistingCustomWaypoint(World world, BlockPos pos, boolean checkPosWithWaypointBounds) {
      MutableBoundingBox thisBb = checkPosWithWaypointBounds ? this.getBoundingBoxForProtection(pos) : new MutableBoundingBox(pos, pos);
      int maxWaypointBoundsRange = 2;
      int chunkX0 = thisBb.field_78897_a - maxWaypointBoundsRange >> 4;
      int chunkX1 = thisBb.field_78893_d + maxWaypointBoundsRange >> 4;
      int chunkZ0 = thisBb.field_78896_c - maxWaypointBoundsRange >> 4;
      int chunkZ1 = thisBb.field_78892_f + maxWaypointBoundsRange >> 4;

      for(int chunkX = chunkX0; chunkX <= chunkX1; ++chunkX) {
         for(int chunkZ = chunkZ0; chunkZ <= chunkZ1; ++chunkZ) {
            Chunk chunk = world.func_212866_a_(chunkX, chunkZ);
            Iterator var13 = chunk.func_177434_r().entrySet().iterator();

            while(var13.hasNext()) {
               Entry entry = (Entry)var13.next();
               BlockPos tePos = (BlockPos)entry.getKey();
               TileEntity te = (TileEntity)entry.getValue();
               if (te instanceof CustomWaypointMarkerTileEntity) {
                  BlockState state = world.func_180495_p(tePos);
                  Direction markerFacing = (Direction)state.func_177229_b(CustomWaypointMarkerBlock.FACING);
                  BlockPos markerFocalPos = tePos.func_177972_a(markerFacing.func_176734_d());
                  MutableBoundingBox existingWaypointBb = this.getBoundingBoxForProtection(markerFocalPos);
                  if (existingWaypointBb.func_78884_a(thisBb)) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean hasValidMapOnFocalPoint(World world, BlockPos focalPos, Consumer messageCallback) {
      return this.getValidMapOnFocalPoint(world, focalPos, messageCallback).isPresent();
   }

   private Optional getValidMapOnFocalPoint(World world, BlockPos focalPos, Consumer messageCallback) {
      List attachedFrames = world.func_175647_a(ItemFrameEntity.class, (new AxisAlignedBB(focalPos)).func_186662_g(1.0D), (frame) -> {
         return getItemFrameSupportPos(frame).equals(focalPos);
      });
      if (attachedFrames.isEmpty()) {
         messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.frame"));
         return Optional.empty();
      } else {
         attachedFrames = (List)attachedFrames.stream().filter((frame) -> {
            return frame.func_82335_i().func_77973_b() == Items.field_151098_aY;
         }).collect(Collectors.toList());
         if (attachedFrames.isEmpty()) {
            messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.map.required"));
            return Optional.empty();
         } else {
            attachedFrames = (List)attachedFrames.stream().filter((frame) -> {
               return this.doesMapIncludePosition(world, this.getMapDataFromFrame(frame), focalPos);
            }).collect(Collectors.toList());
            if (attachedFrames.isEmpty()) {
               messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.map.wrongArea"));
               return Optional.empty();
            } else {
               attachedFrames = (List)attachedFrames.stream().filter((frame) -> {
                  return this.isMapScaleLargeEnough(this.getMapDataFromFrame(frame));
               }).collect(Collectors.toList());
               if (attachedFrames.isEmpty()) {
                  messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.map.tooSmall"));
                  return Optional.empty();
               } else {
                  attachedFrames = (List)attachedFrames.stream().filter((frame) -> {
                     return this.isMapSufficientlyExplored(this.getMapDataFromFrame(frame));
                  }).collect(Collectors.toList());
                  if (attachedFrames.isEmpty()) {
                     messageCallback.accept(new TranslationTextComponent("chat.lotr.cwp.structure.map.notExplored"));
                     return Optional.empty();
                  } else {
                     return Optional.of(attachedFrames.get(0));
                  }
               }
            }
         }
      }
   }

   public static BlockPos getItemFrameSupportPos(ItemFrameEntity frame) {
      return frame.func_174857_n().func_177972_a(frame.func_174811_aO().func_176734_d());
   }

   private Stream streamPositionsInBoundingBox(BlockPos focalPos) {
      return BlockPos.func_229383_a_(this.getMainBoundingBox(focalPos)).map(BlockPos::func_185334_h);
   }

   private Stream streamPositionsInSolidBase(BlockPos focalPos) {
      return BlockPos.func_229383_a_(this.getSolidBaseBoundingBox(focalPos)).map(BlockPos::func_185334_h);
   }

   private MutableBoundingBox getMainBoundingBox(BlockPos focalPos) {
      int x = focalPos.func_177958_n();
      int y = focalPos.func_177956_o();
      int z = focalPos.func_177952_p();
      return new MutableBoundingBox(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2);
   }

   private MutableBoundingBox getSolidBaseBoundingBox(BlockPos focalPos) {
      int baseY = focalPos.func_177956_o() - 3;
      int x = focalPos.func_177958_n();
      int z = focalPos.func_177952_p();
      return new MutableBoundingBox(x - 2, baseY, z - 2, x + 2, baseY, z + 2);
   }

   private MutableBoundingBox getBoundingBoxForProtection(BlockPos focalPos) {
      MutableBoundingBox bb = this.getMainBoundingBox(focalPos);
      bb.func_78888_b(this.getSolidBaseBoundingBox(focalPos));
      ++bb.field_78894_e;
      return bb;
   }

   private boolean testForBlock(World world, BlockPos pos, List boundsToCheckEmpty, BiPredicate tester) {
      if (tester.test(world, pos)) {
         boundsToCheckEmpty.remove(pos);
         return true;
      } else {
         return false;
      }
   }

   private boolean isValidCentrepiece(World world, BlockPos pos) {
      BlockState state = world.func_180495_p(pos);
      return state.func_235714_a_(LOTRTags.Blocks.CUSTOM_WAYPOINT_CENTERPIECES);
   }

   private boolean isValidPillar(World world, BlockPos pos) {
      return this.isValidBase(world, pos);
   }

   private boolean isValidTopLight(World world, BlockPos pos) {
      BlockState state = world.func_180495_p(pos);
      if (state.getLightValue(world, pos) >= 8) {
         return true;
      } else {
         BlockPos abovePos = pos.func_177984_a();
         BlockState aboveState = world.func_180495_p(abovePos);
         return aboveState.func_177230_c() == state.func_177230_c() && aboveState.getLightValue(world, abovePos) >= 8;
      }
   }

   private BiPredicate testValidButtress(Direction facing) {
      return (world, pos) -> {
         BlockState state = world.func_180495_p(pos);
         if (this.isValidStoneOrSimilarMaterial(state) && state.func_177230_c() instanceof StairsBlock) {
            return state.func_177229_b(StairsBlock.field_176309_a) == facing && state.func_177229_b(StairsBlock.field_176308_b) == Half.BOTTOM && state.func_177229_b(StairsBlock.field_176310_M) == StairsShape.STRAIGHT;
         } else {
            return this.testSidewaysStoneSlab(facing).test(world, pos);
         }
      };
   }

   private BiPredicate testSidewaysStoneSlab(Direction facing) {
      return (world, pos) -> {
         BlockState state = world.func_180495_p(pos);
         if (this.isValidStoneOrSimilarMaterial(state) && state.func_177230_c() instanceof SlabBlock) {
            Optional optAxialSlabProperty = state.func_235904_r_().stream().filter((property) -> {
               if (property.func_177699_b() != Axis.class) {
                  return false;
               } else {
                  Collection propertyValues = property.func_177700_c();
                  return propertyValues.contains(Axis.X) && propertyValues.contains(Axis.Z);
               }
            }).findFirst();
            if (optAxialSlabProperty.isPresent()) {
               Property axialSlabProperty = (Property)optAxialSlabProperty.get();
               SlabType expectedSlabType = facing.func_176743_c() == AxisDirection.NEGATIVE ? SlabType.BOTTOM : SlabType.TOP;
               return state.func_177229_b(axialSlabProperty) == facing.func_176740_k() && state.func_177229_b(SlabBlock.field_196505_a) == expectedSlabType;
            }
         }

         return false;
      };
   }

   private BiPredicate testValidCrown(Direction facing) {
      return (world, pos) -> {
         return this.isEmptyBlockForBounds(world, pos) || this.testSidewaysStoneSlab(facing).test(world, pos) || this.testSidewaysHangingTrapdoor(facing).test(world, pos);
      };
   }

   private BiPredicate testSidewaysHangingTrapdoor(Direction facing) {
      return (world, pos) -> {
         BlockState state = world.func_180495_p(pos);
         if (!(state.func_177230_c() instanceof TrapDoorBlock)) {
            return false;
         } else {
            return state.func_177229_b(TrapDoorBlock.field_185512_D) == facing.func_176734_d() && (Boolean)state.func_177229_b(TrapDoorBlock.field_176283_b);
         }
      };
   }

   private boolean isValidBase(World world, BlockPos pos) {
      BlockState state = world.func_180495_p(pos);
      return this.isValidStoneOrSimilarMaterial(state) && state.func_200015_d(world, pos);
   }

   private boolean isValidStoneOrSimilarMaterial(BlockState state) {
      Material material = state.func_185904_a();
      if (material != Material.field_151576_e && material != LOTRBlockMaterial.ICE_BRICK && material != LOTRBlockMaterial.SNOW_BRICK) {
         Block block = state.func_177230_c();
         return block == Blocks.field_189880_di;
      } else {
         return true;
      }
   }

   private boolean isEmptyBlockForBounds(World world, BlockPos pos) {
      BlockState state = world.func_180495_p(pos);
      return state.func_204520_s().func_206886_c() == Fluids.field_204541_a && state.func_196951_e(world, pos).func_197766_b() && !(state.func_177230_c() instanceof FireBlock);
   }

   private MapData getMapDataFromFrame(ItemFrameEntity frame) {
      return FilledMapItem.func_219994_a(frame.func_82335_i(), frame.field_70170_p);
   }

   private boolean doesMapIncludePosition(World world, MapData mapData, BlockPos pos) {
      if (!LOTRDimensions.isDimension(world, mapData.field_76200_c)) {
         return false;
      } else {
         int scaleFactor = 1 << mapData.field_76197_d;
         int halfMapWidth = 64;
         int halfMapWidth = halfMapWidth * scaleFactor;
         return Math.abs(pos.func_177958_n() - mapData.field_76201_a) <= halfMapWidth && Math.abs(pos.func_177952_p() - mapData.field_76199_b) <= halfMapWidth;
      }
   }

   private boolean isMapScaleLargeEnough(MapData mapData) {
      return mapData.field_76197_d >= 1;
   }

   private boolean isMapSufficientlyExplored(MapData mapData) {
      int counted = 0;
      int empty = 0;
      byte[] var4 = mapData.field_76198_e;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         byte c = var4[var6];
         ++counted;
         if (c == 0) {
            ++empty;
         }
      }

      float emptyFraction = (float)empty / (float)counted;
      return emptyFraction < 0.05F;
   }

   public void setPlayerClickedOnBlockToCreate(PlayerEntity player, BlockPos pos) {
      this.playersClickedOnBlocksToCreate.put(player.func_110124_au(), pos.func_185334_h());
   }

   public BlockPos getPlayerClickedOnBlockToCreate(PlayerEntity player) {
      return (BlockPos)this.playersClickedOnBlocksToCreate.get(player.func_110124_au());
   }

   public void clearPlayerClickedOnBlockToCreate(PlayerEntity player) {
      this.playersClickedOnBlocksToCreate.remove(player.func_110124_au());
   }

   public void completeStructureWithCreatedWaypoint(PlayerEntity player, CustomWaypoint waypoint) {
      World world = player.field_70170_p;
      BlockPos waypointPos = waypoint.getPosition();
      Optional frameOpt = this.getValidMapOnFocalPoint(world, waypointPos, (text) -> {
      });
      if (frameOpt.isPresent()) {
         ItemFrameEntity frame = (ItemFrameEntity)frameOpt.get();
         BlockPos offsetPos = frame.func_174857_n();
         Direction frameDir = frame.func_174811_aO();
         world.func_175656_a(offsetPos, (BlockState)((Block)LOTRBlocks.CUSTOM_WAYPOINT_MARKER.get()).func_176223_P().func_206870_a(CustomWaypointMarkerBlock.FACING, frameDir));
         TileEntity te = world.func_175625_s(offsetPos);
         if (te instanceof CustomWaypointMarkerTileEntity) {
            CustomWaypointMarkerTileEntity marker = (CustomWaypointMarkerTileEntity)te;
            marker.absorbItemFrame(frame);
            marker.setWaypointReference(waypoint);
            this.spawnParticles(world, waypointPos);
         } else {
            LOTRLog.error("Player %s created a custom waypoint at (%s) - but somehow the tile entity was not created!", UsernameHelper.getRawUsername(player), waypointPos);
         }
      } else {
         LOTRLog.warn("Player %s created a custom waypoint at (%s) where a valid item frame should exist, but didn't!", UsernameHelper.getRawUsername(player), waypointPos);
      }

   }

   private void spawnParticles(World world, BlockPos waypointPos) {
      if (world instanceof ServerWorld) {
         ServerWorld sWorld = (ServerWorld)world;
         this.streamPositionsInSolidBase(waypointPos).forEach((pos) -> {
            BlockPos abovePos = pos.func_177984_a();
            if (world.func_175623_d(abovePos)) {
               int count = 0;
               double speed = 0.12D;
               sWorld.func_195598_a(ParticleTypes.field_197598_I, (double)abovePos.func_177958_n() + 0.5D, (double)abovePos.func_177956_o() + 0.1D, (double)abovePos.func_177952_p() + 0.5D, count, 0.0D, 1.0D, 0.0D, speed);
            }

         });
      }

   }

   public void updateWaypointStructure(PlayerEntity player, CustomWaypoint waypoint) {
      World world = player.field_70170_p;
      BlockPos waypointPos = waypoint.getPosition();
      CustomWaypointMarkerTileEntity marker = this.getAdjacentWaypointMarker(world, waypointPos, waypoint);
      if (marker != null) {
         marker.updateWaypointReference(waypoint);
         this.spawnParticles(world, waypointPos);
      } else {
         LOTRLog.error("Player %s tried to update a custom waypoint at (%s) - but somehow the tile entity does not exist!", UsernameHelper.getRawUsername(player), waypointPos);
      }

   }

   public void adoptWaypointStructure(PlayerEntity player, CustomWaypoint waypoint) {
      World world = player.field_70170_p;
      BlockPos waypointPos = waypoint.getPosition();
      this.spawnParticles(world, waypointPos);
   }

   public boolean hasAdjacentWaypointMarker(World world, BlockPos focalPos) {
      return this.getAdjacentWaypointMarker(world, focalPos, (AbstractCustomWaypoint)null) != null;
   }

   public CustomWaypointMarkerTileEntity getAdjacentWaypointMarker(World world, BlockPos focalPos, @Nullable AbstractCustomWaypoint waypointToValidate) {
      Iterator var4 = Plane.HORIZONTAL.iterator();

      CustomWaypointMarkerTileEntity marker;
      do {
         TileEntity te;
         do {
            BlockPos offsetPos;
            BlockState state;
            do {
               if (!var4.hasNext()) {
                  return null;
               }

               Direction dir = (Direction)var4.next();
               offsetPos = focalPos.func_177972_a(dir);
               state = world.func_180495_p(offsetPos);
            } while(state.func_177230_c() != LOTRBlocks.CUSTOM_WAYPOINT_MARKER.get());

            te = world.func_175625_s(offsetPos);
         } while(!(te instanceof CustomWaypointMarkerTileEntity));

         marker = (CustomWaypointMarkerTileEntity)te;
      } while(waypointToValidate != null && !marker.matchesWaypointReference(waypointToValidate));

      return marker;
   }

   public boolean isCompletedWaypointStillValidStructure(World world, BlockPos focalPos) {
      return this.isFocalPointOfValidStructure(world, focalPos, (text) -> {
      });
   }

   public boolean isProtectedByWaypointStructure(World world, BlockPos pos, PlayerEntity player) {
      if (!player.field_71075_bZ.field_75098_d && this.isProtectedByWaypointStructure(world, pos)) {
         long currentTime = System.currentTimeMillis();
         long lastMessagedTime = (Long)this.playersSentProtectionMessageTimes.getOrDefault(player.func_110124_au(), 0L);
         if (currentTime - lastMessagedTime >= 3000L) {
            LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.protected"));
            this.playersSentProtectionMessageTimes.put(player.func_110124_au(), currentTime);
         }

         if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
            serverPlayer.func_71120_a(serverPlayer.field_71069_bz);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isProtectedByWaypointStructure(World world, BlockPos pos) {
      return this.isTooCloseToExistingCustomWaypoint(world, pos, false);
   }

   public boolean checkCompletedWaypointHasMarkerAndHandleIfNot(World world, AbstractCustomWaypoint waypoint, PlayerEntity player) {
      BlockPos waypointPos = waypoint.getPosition();
      CustomWaypointMarkerTileEntity marker = this.getAdjacentWaypointMarker(world, waypointPos, waypoint);
      if (marker == null) {
         waypoint.removeFromPlayerData(player);
         LOTRUtil.sendMessage(player, (new TranslationTextComponent("chat.lotr.cwp.missing", new Object[]{waypoint.getDisplayName()})).func_240699_a_(TextFormatting.RED));
         return false;
      } else {
         return true;
      }
   }

   public boolean destroyCustomWaypointMarkerAndRemoveFromPlayerData(World world, CustomWaypoint waypoint, PlayerEntity player, boolean destroyWholeStructure) {
      BlockPos waypointPos = waypoint.getPosition();
      CustomWaypointMarkerTileEntity marker = this.getAdjacentWaypointMarker(world, waypointPos, waypoint);
      if (marker != null) {
         if (LOTRLevelData.sidedInstance((IWorldReader)world).getData(player).getFastTravelData().removeCustomWaypoint(world, waypoint)) {
            world.func_175656_a(marker.func_174877_v(), Blocks.field_150350_a.func_176223_P());
            if (destroyWholeStructure) {
               Stream.concat(this.streamPositionsInBoundingBox(waypointPos).filter((pos) -> {
                  return !world.func_175623_d(pos);
               }), this.streamPositionsInSolidBase(waypointPos).filter((pos) -> {
                  return world.field_73012_v.nextInt(4) == 0;
               })).forEach((pos) -> {
                  this.destroyBlockWithDrops(world, pos, player);
               });
            }

            return true;
         } else {
            return false;
         }
      } else {
         LOTRLog.warn("Tried to destroy a custom waypoint %s for player %s at (%s) but no matching marker block was found", waypoint.getRawName(), UsernameHelper.getRawUsername(player), waypointPos);
         return false;
      }
   }

   private boolean destroyBlockWithDrops(World world, BlockPos pos, PlayerEntity player) {
      BlockState state = world.func_180495_p(pos);
      Block block = state.func_177230_c();
      TileEntity te = world.func_175625_s(pos);
      boolean canHarvest = true;
      boolean removed = state.removedByPlayer(world, pos, player, canHarvest, world.func_204610_c(pos));
      if (removed) {
         state.func_177230_c().func_176206_d(world, pos, state);
         block.func_180657_a(world, player, pos, state, te, ItemStack.field_190927_a);
      }

      return removed;
   }

   @Nullable
   public BlockPos findRandomTravelPositionForCompletedWaypoint(World world, AbstractCustomWaypoint waypoint, PlayerEntity player) {
      BlockPos waypointPos = waypoint.getPosition();
      if (!this.checkCompletedWaypointHasMarkerAndHandleIfNot(world, waypoint, player)) {
         LOTRLog.warn("Player %s tried to travel to a custom waypoint (%s, %s) that isn't a complete structure!", UsernameHelper.getRawUsername(player), waypoint.getRawName(), waypointPos);
         return null;
      } else {
         List safePositions = (List)this.streamPositionsInBoundingBox(waypointPos).filter((pos) -> {
            BlockPos belowPos = pos.func_177977_b();
            return world.func_180495_p(pos.func_177977_b()).func_224755_d(world, belowPos, Direction.UP) && this.isEmptyBlockForBounds(world, pos) && this.isEmptyBlockForBounds(world, pos.func_177984_a());
         }).collect(Collectors.toList());
         if (safePositions.isEmpty()) {
            LOTRLog.warn("Player %s tried to travel to a custom waypoint (%s, %s) but couldn't find any safe positions!", UsernameHelper.getRawUsername(player), waypoint.getRawName(), waypointPos);
            return waypointPos;
         } else {
            return (BlockPos)safePositions.get(world.field_73012_v.nextInt(safePositions.size()));
         }
      }
   }
}
