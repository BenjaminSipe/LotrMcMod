package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketDeleteCWPClient implements IMessage {
   private int cwpID;
   private UUID sharingPlayer;

   public LOTRPacketDeleteCWPClient() {
   }

   public LOTRPacketDeleteCWPClient(int id) {
      this.cwpID = id;
   }

   public LOTRPacketDeleteCWPClient setSharingPlayer(UUID player) {
      this.sharingPlayer = player;
      return this;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.cwpID);
      boolean shared = this.sharingPlayer != null;
      data.writeBoolean(shared);
      if (shared) {
         data.writeLong(this.sharingPlayer.getMostSignificantBits());
         data.writeLong(this.sharingPlayer.getLeastSignificantBits());
      }

   }

   public void fromBytes(ByteBuf data) {
      this.cwpID = data.readInt();
      boolean shared = data.readBoolean();
      if (shared) {
         this.sharingPlayer = new UUID(data.readLong(), data.readLong());
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketDeleteCWPClient packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         LOTRCustomWaypoint cwp;
         if (packet.sharingPlayer != null) {
            if (!LOTRMod.proxy.isSingleplayer()) {
               cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
               if (cwp != null) {
                  pd.removeSharedCustomWaypoint(cwp);
               }
            }
         } else if (!LOTRMod.proxy.isSingleplayer()) {
            cwp = pd.getCustomWaypointByID(packet.cwpID);
            if (cwp != null) {
               pd.removeCustomWaypointClient(cwp);
            }
         }

         return null;
      }
   }
}
