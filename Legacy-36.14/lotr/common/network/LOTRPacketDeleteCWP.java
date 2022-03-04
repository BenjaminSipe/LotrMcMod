package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketDeleteCWP implements IMessage {
   private int wpID;

   public LOTRPacketDeleteCWP() {
   }

   public LOTRPacketDeleteCWP(LOTRCustomWaypoint wp) {
      this.wpID = wp.getID();
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.wpID);
   }

   public void fromBytes(ByteBuf data) {
      this.wpID = data.readInt();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketDeleteCWP packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
         if (cwp != null) {
            pd.removeCustomWaypoint(cwp);
         }

         return null;
      }
   }
}
