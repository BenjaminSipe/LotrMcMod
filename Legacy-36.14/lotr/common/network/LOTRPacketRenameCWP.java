package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketRenameCWP implements IMessage {
   private int wpID;
   private String name;

   public LOTRPacketRenameCWP() {
   }

   public LOTRPacketRenameCWP(LOTRCustomWaypoint wp, String s) {
      this.wpID = wp.getID();
      this.name = s;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.wpID);
      byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
      data.writeShort(nameBytes.length);
      data.writeBytes(nameBytes);
   }

   public void fromBytes(ByteBuf data) {
      this.wpID = data.readInt();
      int length = data.readShort();
      this.name = data.readBytes(length).toString(Charsets.UTF_8);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketRenameCWP packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
         if (cwp != null) {
            String wpName = LOTRCustomWaypoint.validateCustomName(packet.name);
            if (wpName != null) {
               pd.renameCustomWaypoint(cwp, wpName);
            }
         }

         return null;
      }
   }
}
