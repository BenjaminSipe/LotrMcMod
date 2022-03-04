package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class LOTRPacketFastTravel implements IMessage {
   private boolean isCustom;
   private int wpID;
   private UUID sharingPlayer;

   public LOTRPacketFastTravel() {
   }

   public LOTRPacketFastTravel(LOTRAbstractWaypoint wp) {
      this.isCustom = wp instanceof LOTRCustomWaypoint;
      this.wpID = wp.getID();
      if (wp instanceof LOTRCustomWaypoint) {
         this.sharingPlayer = ((LOTRCustomWaypoint)wp).getSharingPlayerID();
      }

   }

   public void toBytes(ByteBuf data) {
      data.writeBoolean(this.isCustom);
      data.writeInt(this.wpID);
      boolean shared = this.sharingPlayer != null;
      data.writeBoolean(shared);
      if (shared) {
         data.writeLong(this.sharingPlayer.getMostSignificantBits());
         data.writeLong(this.sharingPlayer.getLeastSignificantBits());
      }

   }

   public void fromBytes(ByteBuf data) {
      this.isCustom = data.readBoolean();
      this.wpID = data.readInt();
      boolean shared = data.readBoolean();
      if (shared) {
         this.sharingPlayer = new UUID(data.readLong(), data.readLong());
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFastTravel packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         if (!LOTRConfig.enableFastTravel) {
            entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.ftDisabled", new Object[0]));
         } else {
            LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
            boolean isCustom = packet.isCustom;
            int waypointID = packet.wpID;
            LOTRAbstractWaypoint waypoint = null;
            if (!isCustom) {
               if (waypointID >= 0 && waypointID < LOTRWaypoint.values().length) {
                  waypoint = LOTRWaypoint.values()[waypointID];
               }
            } else {
               UUID sharingPlayer = packet.sharingPlayer;
               if (sharingPlayer != null) {
                  waypoint = playerData.getSharedCustomWaypointByID(sharingPlayer, waypointID);
               } else {
                  waypoint = playerData.getCustomWaypointByID(waypointID);
               }
            }

            if (waypoint != null && ((LOTRAbstractWaypoint)waypoint).hasPlayerUnlocked(entityplayer)) {
               if (playerData.getTimeSinceFT() < playerData.getWaypointFTTime((LOTRAbstractWaypoint)waypoint, entityplayer)) {
                  entityplayer.func_71053_j();
                  entityplayer.func_145747_a(new ChatComponentTranslation("lotr.fastTravel.moreTime", new Object[]{((LOTRAbstractWaypoint)waypoint).getDisplayName()}));
               } else {
                  boolean canTravel = playerData.canFastTravel();
                  if (!canTravel) {
                     entityplayer.func_71053_j();
                     entityplayer.func_145747_a(new ChatComponentTranslation("lotr.fastTravel.underAttack", new Object[0]));
                  } else if (entityplayer.func_70608_bn()) {
                     entityplayer.func_71053_j();
                     entityplayer.func_145747_a(new ChatComponentTranslation("lotr.fastTravel.inBed", new Object[0]));
                  } else {
                     playerData.setTargetFTWaypoint((LOTRAbstractWaypoint)waypoint);
                  }
               }
            }
         }

         return null;
      }
   }
}
