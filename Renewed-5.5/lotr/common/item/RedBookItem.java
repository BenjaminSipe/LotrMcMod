package lotr.common.item;

import java.util.UUID;
import lotr.common.LOTRGameRules;
import lotr.common.LOTRLog;
import lotr.common.block.CustomWaypointMarkerBlock;
import lotr.common.data.FastTravelDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.init.LOTRBlocks;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketOpenAdoptCustomWaypointScreen;
import lotr.common.network.SPacketOpenScreen;
import lotr.common.network.SPacketOpenUpdateCustomWaypointScreen;
import lotr.common.network.SPacketOpenViewAdoptedCustomWaypointScreen;
import lotr.common.tileentity.CustomWaypointMarkerTileEntity;
import lotr.common.util.LOTRUtil;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.AbstractCustomWaypoint;
import lotr.common.world.map.AdoptedCustomWaypoint;
import lotr.common.world.map.AdoptedCustomWaypointKey;
import lotr.common.world.map.CustomWaypoint;
import lotr.common.world.map.CustomWaypointStructureHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Item.Properties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;

public class RedBookItem extends Item {
   public RedBookItem(Properties properties) {
      super(properties);
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      PlayerEntity player = context.func_195999_j();
      if (this.useExistingCustomWaypointStructure(world, pos, player)) {
         return ActionResultType.SUCCESS;
      } else {
         return this.createCustomWaypointStructure(world, pos, player) ? ActionResultType.SUCCESS : ActionResultType.PASS;
      }
   }

   public boolean createCustomWaypointStructure(World world, BlockPos pos, PlayerEntity player) {
      if (!world.field_72995_K) {
         ServerWorld sWorld = (ServerWorld)world;
         boolean canBeCWPStructure = CustomWaypointStructureHandler.INSTANCE.isFocalPoint(world, pos);
         if (canBeCWPStructure) {
            if (!world.func_82736_K().func_223586_b(LOTRGameRules.CUSTOM_WAYPOINT_CREATION)) {
               LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.create.disabled"));
               return false;
            }

            if (CustomWaypointStructureHandler.INSTANCE.hasAdjacentWaypointMarker(world, pos)) {
               LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.alreadyExists"));
               this.playFailedSound(world, pos);
               return true;
            }

            FastTravelDataModule ftData = LOTRLevelData.sidedInstance((IWorldReader)world).getData(player).getFastTravelData();
            if (!ftData.canCreateOrAdoptMoreCustomWaypoints()) {
               LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.limit"));
               this.playFailedSound(world, pos);
               return true;
            }

            boolean isCompleteStructure = CustomWaypointStructureHandler.INSTANCE.isFocalPointOfCompletableStructure(sWorld, pos, (msg) -> {
               LOTRUtil.sendMessage(player, msg);
            });
            if (isCompleteStructure) {
               CustomWaypointStructureHandler.INSTANCE.setPlayerClickedOnBlockToCreate(player, pos);
               LOTRPacketHandler.sendTo(new SPacketOpenScreen(SPacketOpenScreen.Type.CREATE_CUSTOM_WAYPOINT), (ServerPlayerEntity)player);
               this.playOpenScreenSound(world, pos);
            } else {
               this.playFailedSound(world, pos);
            }

            return true;
         }
      }

      return false;
   }

   private boolean useExistingCustomWaypointStructure(World world, BlockPos pos, PlayerEntity player) {
      if (!world.field_72995_K) {
         Pair markerAndPos = this.getClickedOnOrAdjacentMarkerAndFocalPos(world, pos);
         if (markerAndPos != null) {
            CustomWaypointMarkerTileEntity marker = (CustomWaypointMarkerTileEntity)markerAndPos.getLeft();
            BlockPos waypointStructurePos = (BlockPos)markerAndPos.getRight();
            UUID waypointPlayer = marker.getWaypointPlayer();
            if (player.func_110124_au().equals(waypointPlayer)) {
               return this.updateCustomWaypointStructure(world, waypointStructurePos, player, marker);
            }

            return this.useOtherPlayerCustomWaypointStructure(world, waypointStructurePos, player, marker, waypointPlayer);
         }
      }

      return false;
   }

   private Pair getClickedOnOrAdjacentMarkerAndFocalPos(World world, BlockPos pos) {
      BlockState state = world.func_180495_p(pos);
      if (state.func_177230_c() == LOTRBlocks.CUSTOM_WAYPOINT_MARKER.get()) {
         TileEntity te = world.func_175625_s(pos);
         if (te instanceof CustomWaypointMarkerTileEntity) {
            CustomWaypointMarkerTileEntity marker = (CustomWaypointMarkerTileEntity)te;
            BlockPos focalPos = pos.func_177972_a(((Direction)state.func_177229_b(CustomWaypointMarkerBlock.FACING)).func_176734_d());
            return Pair.of(marker, focalPos);
         }
      } else {
         CustomWaypointMarkerTileEntity adjacentMarker = CustomWaypointStructureHandler.INSTANCE.getAdjacentWaypointMarker(world, pos, (AbstractCustomWaypoint)null);
         if (adjacentMarker != null) {
            return Pair.of(adjacentMarker, pos);
         }
      }

      return null;
   }

   private boolean updateCustomWaypointStructure(World world, BlockPos waypointStructurePos, PlayerEntity player, CustomWaypointMarkerTileEntity marker) {
      int waypointId = marker.getWaypointId();
      CustomWaypoint waypoint = LOTRLevelData.sidedInstance((IWorldReader)world).getData(player).getFastTravelData().getCustomWaypointById(waypointId);
      if (waypoint != null) {
         BlockPos waypointSavedPos = waypoint.getPosition();
         if (waypointStructurePos.equals(waypointSavedPos)) {
            LOTRPacketHandler.sendTo(new SPacketOpenUpdateCustomWaypointScreen(waypoint), (ServerPlayerEntity)player);
            this.playOpenScreenSound(world, waypointStructurePos);
            return true;
         } else {
            LOTRLog.error("Player %s tried to update completed custom waypoint structure at (%s), but the waypoint's saved position (%s) didn't match!", UsernameHelper.getRawUsername(player), waypointStructurePos.toString(), waypointSavedPos.toString());
            return false;
         }
      } else {
         LOTRLog.error("Player %s tried to update completed custom waypoint structure at (%s), but no matching waypoint exists in the player data!", UsernameHelper.getRawUsername(player), waypointStructurePos.toString());
         return false;
      }
   }

   private boolean useOtherPlayerCustomWaypointStructure(World world, BlockPos waypointStructurePos, PlayerEntity player, CustomWaypointMarkerTileEntity marker, UUID waypointPlayer) {
      if (marker.isWaypointPublic()) {
         int waypointId = marker.getWaypointId();
         AdoptedCustomWaypointKey adoptKey = AdoptedCustomWaypointKey.of(waypointPlayer, waypointId);
         FastTravelDataModule ftData = LOTRLevelData.sidedInstance((IWorldReader)world).getData(player).getFastTravelData();
         AdoptedCustomWaypoint waypoint = ftData.getAdoptedCustomWaypointByKey(adoptKey);
         if (waypoint != null) {
            String createdPlayerName = UsernameHelper.getLastKnownUsernameOrFallback(waypointPlayer);
            LOTRPacketHandler.sendTo(new SPacketOpenViewAdoptedCustomWaypointScreen(waypoint, createdPlayerName), (ServerPlayerEntity)player);
            this.playOpenScreenSound(world, waypointStructurePos);
            return true;
         } else if (!ftData.canCreateOrAdoptMoreCustomWaypoints()) {
            LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.limit"));
            this.playFailedSound(world, waypointStructurePos);
            return true;
         } else {
            CustomWaypoint originalWaypoint = LOTRLevelData.sidedInstance((IWorldReader)world).getData(world, waypointPlayer).getFastTravelData().getCustomWaypointById(waypointId);
            String createdPlayerName = UsernameHelper.getLastKnownUsernameOrFallback(waypointPlayer);
            LOTRPacketHandler.sendTo(new SPacketOpenAdoptCustomWaypointScreen(originalWaypoint, createdPlayerName), (ServerPlayerEntity)player);
            this.playOpenScreenSound(world, waypointStructurePos);
            return true;
         }
      } else {
         LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.update.otherPlayer"));
         this.playFailedSound(world, waypointStructurePos);
         return true;
      }
   }

   private void playOpenScreenSound(World world, BlockPos pos) {
      world.func_184133_a((PlayerEntity)null, pos, SoundEvents.field_219617_ah, SoundCategory.PLAYERS, 1.0F, 1.0F);
   }

   private void playFailedSound(World world, BlockPos pos) {
      world.func_184133_a((PlayerEntity)null, pos, world.func_180495_p(pos).func_215695_r().func_185846_f(), SoundCategory.PLAYERS, 0.5F, 1.0F);
   }

   public static void playCompleteWaypointActionSound(World world, BlockPos pos) {
      world.func_184133_a((PlayerEntity)null, pos, SoundEvents.field_219618_ai, SoundCategory.PLAYERS, 1.0F, 1.0F);
   }
}
