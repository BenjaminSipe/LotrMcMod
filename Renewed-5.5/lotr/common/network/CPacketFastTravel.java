package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRGameRules;
import lotr.common.LOTRLog;
import lotr.common.data.FastTravelDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.util.LOTRUtil;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.Waypoint;
import lotr.common.world.map.WaypointNetworkType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class CPacketFastTravel extends ByteArrayPacket {
   private CPacketFastTravel(byte[] data) {
      super(data);
   }

   public CPacketFastTravel(Waypoint wp) {
      super((buf) -> {
         WaypointNetworkType.writeIdentification(buf, wp);
      });
   }

   public static void encode(CPacketFastTravel packet, PacketBuffer buf) {
      encodeByteData(packet, buf);
   }

   public static CPacketFastTravel decode(PacketBuffer buf) {
      return (CPacketFastTravel)decodeByteData(buf, CPacketFastTravel::new);
   }

   public static void handle(CPacketFastTravel packet, Supplier context) {
      ServerPlayerEntity player = ((Context)context.get()).getSender();
      ServerWorld world = player.func_71121_q();
      if (!world.func_82736_K().func_223586_b(LOTRGameRules.FAST_TRAVEL)) {
         LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.disabled.gamerule"));
      } else if (!LOTRWorldTypes.hasMapFeatures(world)) {
         LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.disabled.worldtype"));
      } else {
         LOTRPlayerData playerData = LOTRLevelData.serverInstance().getData(player);
         FastTravelDataModule ftData = playerData.getFastTravelData();
         PacketBuffer waypointData = packet.getBufferedByteData();
         Waypoint waypoint = WaypointNetworkType.readFromIdentification(waypointData, playerData);
         if (waypoint != null && waypoint.verifyFastTravellable(world, player)) {
            if (waypoint.hasPlayerUnlocked(player)) {
               if (ftData.getTimeSinceFT() < ftData.getWaypointFTTime(waypoint, player)) {
                  player.func_71053_j();
                  LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.moreTime", new Object[]{waypoint.getDisplayName()}));
               } else {
                  boolean underAttack = ftData.isUnderAttack(player);
                  if (underAttack) {
                     player.func_71053_j();
                     LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.underAttack"));
                  } else if (player.func_70608_bn()) {
                     player.func_71053_j();
                     LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.inBed"));
                  } else {
                     ftData.setTargetWaypoint(waypoint);
                  }
               }
            } else {
               LOTRLog.warn("Player %s tried to FT to a waypoint (%s, %s) that they haven't unlocked", UsernameHelper.getRawUsername(player), waypoint.getClass().getSimpleName(), waypoint.getRawName());
            }
         }
      }

      ((Context)context.get()).setPacketHandled(true);
   }
}
