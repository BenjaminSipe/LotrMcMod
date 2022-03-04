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

public class LOTRPacketCWPSharedUnlockClient implements IMessage {
   private int cwpID;
   private UUID sharingPlayer;

   public LOTRPacketCWPSharedUnlockClient() {
   }

   public LOTRPacketCWPSharedUnlockClient(int id, UUID player) {
      this.cwpID = id;
      this.sharingPlayer = player;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.cwpID);
      data.writeLong(this.sharingPlayer.getMostSignificantBits());
      data.writeLong(this.sharingPlayer.getLeastSignificantBits());
   }

   public void fromBytes(ByteBuf data) {
      this.cwpID = data.readInt();
      this.sharingPlayer = new UUID(data.readLong(), data.readLong());
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketCWPSharedUnlockClient packet, MessageContext context) {
         if (!LOTRMod.proxy.isSingleplayer()) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRCustomWaypoint cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
            if (cwp != null) {
               pd.unlockSharedCustomWaypoint(cwp);
            }
         }

         return null;
      }
   }
}
