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

public class LOTRPacketCWPSharedHideClient implements IMessage {
   private int cwpID;
   private UUID sharingPlayer;
   private boolean hideCWP;

   public LOTRPacketCWPSharedHideClient() {
   }

   public LOTRPacketCWPSharedHideClient(int id, UUID player, boolean hide) {
      this.cwpID = id;
      this.sharingPlayer = player;
      this.hideCWP = hide;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.cwpID);
      data.writeLong(this.sharingPlayer.getMostSignificantBits());
      data.writeLong(this.sharingPlayer.getLeastSignificantBits());
      data.writeBoolean(this.hideCWP);
   }

   public void fromBytes(ByteBuf data) {
      this.cwpID = data.readInt();
      this.sharingPlayer = new UUID(data.readLong(), data.readLong());
      this.hideCWP = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketCWPSharedHideClient packet, MessageContext context) {
         if (!LOTRMod.proxy.isSingleplayer()) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRCustomWaypoint cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
            if (cwp != null) {
               pd.hideOrUnhideSharedCustomWaypoint(cwp, packet.hideCWP);
            }
         }

         return null;
      }
   }
}
