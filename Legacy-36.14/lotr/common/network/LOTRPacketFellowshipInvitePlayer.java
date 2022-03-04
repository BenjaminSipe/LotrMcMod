package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRPacketFellowshipInvitePlayer extends LOTRPacketFellowshipDo {
   private String invitedUsername;

   public LOTRPacketFellowshipInvitePlayer() {
   }

   public LOTRPacketFellowshipInvitePlayer(LOTRFellowshipClient fs, String username) {
      super(fs);
      this.invitedUsername = username;
   }

   public void toBytes(ByteBuf data) {
      super.toBytes(data);
      byte[] nameBytes = this.invitedUsername.getBytes(Charsets.UTF_8);
      data.writeByte(nameBytes.length);
      data.writeBytes(nameBytes);
   }

   public void fromBytes(ByteBuf data) {
      super.fromBytes(data);
      int nameLength = data.readByte();
      ByteBuf nameBytes = data.readBytes(nameLength);
      this.invitedUsername = nameBytes.toString(Charsets.UTF_8);
   }

   public static class Handler implements IMessageHandler {
      private UUID findInvitedPlayerUUID(String invitedUsername) {
         GameProfile profile = MinecraftServer.func_71276_C().func_152358_ax().func_152655_a(invitedUsername);
         return profile != null && profile.getId() != null ? profile.getId() : null;
      }

      public IMessage onMessage(LOTRPacketFellowshipInvitePlayer packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRFellowship fellowship = packet.getActiveFellowship();
         if (fellowship != null) {
            int limit = LOTRConfig.getFellowshipMaxSize(entityplayer.field_70170_p);
            if (limit >= 0 && fellowship.getPlayerCount() >= limit) {
               LOTRLog.logger.warn(String.format("Player %s tried to invite a player with username %s to fellowship %s, but fellowship size %d is already >= the maximum of %d", entityplayer.func_70005_c_(), packet.invitedUsername, fellowship.getName(), fellowship.getPlayerCount(), limit));
            } else {
               UUID invitedPlayer = this.findInvitedPlayerUUID(packet.invitedUsername);
               if (invitedPlayer != null) {
                  LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                  playerData.invitePlayerToFellowship(fellowship, invitedPlayer, entityplayer.func_70005_c_());
               } else {
                  LOTRLog.logger.warn(String.format("Player %s tried to invite a player with username %s to fellowship %s, but couldn't find the invited player's UUID", entityplayer.func_70005_c_(), packet.invitedUsername, fellowship.getName()));
               }
            }
         }

         return null;
      }
   }
}
