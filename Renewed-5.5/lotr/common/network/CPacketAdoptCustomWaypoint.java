package lotr.common.network;

import java.util.UUID;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import lotr.common.data.FastTravelDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.item.RedBookItem;
import lotr.common.stat.LOTRStats;
import lotr.common.tileentity.CustomWaypointMarkerTileEntity;
import lotr.common.util.LOTRUtil;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.AbstractCustomWaypoint;
import lotr.common.world.map.CustomWaypoint;
import lotr.common.world.map.CustomWaypointStructureHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class CPacketAdoptCustomWaypoint {
   private final UUID waypointPlayer;
   private final int waypointId;

   public CPacketAdoptCustomWaypoint(CustomWaypoint waypoint) {
      this(waypoint.getCreatedPlayer(), waypoint.getCustomId());
   }

   private CPacketAdoptCustomWaypoint(UUID waypointPlayer, int waypointId) {
      this.waypointPlayer = waypointPlayer;
      this.waypointId = waypointId;
   }

   public static void encode(CPacketAdoptCustomWaypoint packet, PacketBuffer buf) {
      buf.func_179252_a(packet.waypointPlayer);
      buf.func_150787_b(packet.waypointId);
   }

   public static CPacketAdoptCustomWaypoint decode(PacketBuffer buf) {
      UUID waypointPlayer = buf.func_179253_g();
      int waypointId = buf.func_150792_a();
      return new CPacketAdoptCustomWaypoint(waypointPlayer, waypointId);
   }

   public static void handle(CPacketAdoptCustomWaypoint packet, Supplier context) {
      ServerPlayerEntity player = ((Context)context.get()).getSender();
      FastTravelDataModule ftData = LOTRLevelData.serverInstance().getData(player).getFastTravelData();
      doAdoptCustomWaypoint(packet, player, ftData);
      ((Context)context.get()).setPacketHandled(true);
   }

   private static void doAdoptCustomWaypoint(CPacketAdoptCustomWaypoint packet, ServerPlayerEntity player, FastTravelDataModule ftData) {
      if (!ftData.canCreateOrAdoptMoreCustomWaypoints()) {
         LOTRLog.warn("Player %s tried to adopt a custom waypoint but has reached their limit", UsernameHelper.getRawUsername(player));
      } else {
         ServerWorld world = player.func_71121_q();
         UUID waypointPlayer = packet.waypointPlayer;
         int waypointId = packet.waypointId;
         FastTravelDataModule waypointOwnerFtData = LOTRLevelData.serverInstance().getData(world, waypointPlayer).getFastTravelData();
         CustomWaypoint waypoint = waypointOwnerFtData.getCustomWaypointById(waypointId);
         if (waypoint == null) {
            LOTRLog.warn("Player %s tried to adopt a nonexistent custom waypoint (creator %s, ID %d)", UsernameHelper.getRawUsername(player), waypointPlayer, waypointId);
         } else if (!waypoint.isPublic()) {
            LOTRLog.warn("Player %s tried to adopt a non-public custom waypoint (creator %s, ID %d)", UsernameHelper.getRawUsername(player), waypointPlayer, waypointId);
         } else {
            BlockPos waypointPos = waypoint.getPosition();
            Vector3d playerPos = player.func_213303_ch();
            CustomWaypointMarkerTileEntity marker = CustomWaypointStructureHandler.INSTANCE.getAdjacentWaypointMarker(world, waypointPos, (AbstractCustomWaypoint)null);
            if (marker == null) {
               LOTRLog.warn("Player %s tried to adopt a custom waypoint where no waypoint structure exists (player pos = %s, clicked pos = %s)", UsernameHelper.getRawUsername(player), playerPos.toString(), waypointPos.toString());
            } else if (!marker.matchesWaypointReference(waypoint)) {
               LOTRLog.warn("Player %s tried to adopt a custom waypoint at a marker which doesn't match the target waypoint (player pos = %s, clicked pos = %s)", UsernameHelper.getRawUsername(player), playerPos.toString(), waypointPos.toString());
            } else if (waypointPos.func_218138_a(playerPos, false) >= 64.0D) {
               LOTRLog.warn("Player %s tried to adopt a custom waypoint on a block too far away (player pos = %s, clicked pos = %s)", UsernameHelper.getRawUsername(player), playerPos.toString(), waypointPos.toString());
            } else {
               if (ftData.adoptCustomWaypoint(world, waypoint)) {
                  CustomWaypointStructureHandler.INSTANCE.adoptWaypointStructure(player, waypoint);
                  LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.cwp.adopt", new Object[]{waypoint.getDisplayName(), ftData.getNumCustomWaypoints(), ftData.getMaxCustomWaypoints()}));
                  RedBookItem.playCompleteWaypointActionSound(world, waypointPos);
                  player.func_195066_a(LOTRStats.ADOPT_CUSTOM_WAYPOINT);
               }

            }
         }
      }
   }
}
