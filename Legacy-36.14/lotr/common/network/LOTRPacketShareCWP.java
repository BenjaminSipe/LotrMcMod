package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketShareCWP implements IMessage {
   private int wpID;
   private String fsName;
   private boolean adding;

   public LOTRPacketShareCWP() {
   }

   public LOTRPacketShareCWP(LOTRCustomWaypoint wp, String s, boolean add) {
      this.wpID = wp.getID();
      this.fsName = s;
      this.adding = add;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.wpID);
      byte[] nameBytes = this.fsName.getBytes(Charsets.UTF_8);
      data.writeShort(nameBytes.length);
      data.writeBytes(nameBytes);
      data.writeBoolean(this.adding);
   }

   public void fromBytes(ByteBuf data) {
      this.wpID = data.readInt();
      int length = data.readShort();
      this.fsName = data.readBytes(length).toString(Charsets.UTF_8);
      this.adding = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketShareCWP packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
         if (cwp != null) {
            LOTRFellowship fellowship = pd.getFellowshipByName(packet.fsName);
            if (fellowship != null) {
               if (packet.adding) {
                  pd.customWaypointAddSharedFellowship(cwp, fellowship);
               } else {
                  pd.customWaypointRemoveSharedFellowship(cwp, fellowship);
               }
            }
         }

         return null;
      }
   }
}
