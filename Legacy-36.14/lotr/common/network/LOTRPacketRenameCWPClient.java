package lotr.common.network;

import com.google.common.base.Charsets;
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

public class LOTRPacketRenameCWPClient implements IMessage {
   private int cwpID;
   private String name;
   private UUID sharingPlayer;

   public LOTRPacketRenameCWPClient() {
   }

   public LOTRPacketRenameCWPClient(int id, String s) {
      this.cwpID = id;
      this.name = s;
   }

   public LOTRPacketRenameCWPClient setSharingPlayer(UUID player) {
      this.sharingPlayer = player;
      return this;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.cwpID);
      byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
      data.writeShort(nameBytes.length);
      data.writeBytes(nameBytes);
      boolean shared = this.sharingPlayer != null;
      data.writeBoolean(shared);
      if (shared) {
         data.writeLong(this.sharingPlayer.getMostSignificantBits());
         data.writeLong(this.sharingPlayer.getLeastSignificantBits());
      }

   }

   public void fromBytes(ByteBuf data) {
      this.cwpID = data.readInt();
      int length = data.readShort();
      this.name = data.readBytes(length).toString(Charsets.UTF_8);
      boolean shared = data.readBoolean();
      if (shared) {
         this.sharingPlayer = new UUID(data.readLong(), data.readLong());
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketRenameCWPClient packet, MessageContext context) {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         LOTRCustomWaypoint cwp;
         if (packet.sharingPlayer != null) {
            if (!LOTRMod.proxy.isSingleplayer()) {
               cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
               if (cwp != null) {
                  pd.renameSharedCustomWaypoint(cwp, packet.name);
               }
            }
         } else if (!LOTRMod.proxy.isSingleplayer()) {
            cwp = pd.getCustomWaypointByID(packet.cwpID);
            if (cwp != null) {
               pd.renameCustomWaypointClient(cwp, packet.name);
            }
         }

         return null;
      }
   }
}
