package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.util.LOTRLog;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class LOTRPacketCreateCWP implements IMessage {
   private String name;

   public LOTRPacketCreateCWP() {
   }

   public LOTRPacketCreateCWP(String s) {
      this.name = s;
   }

   public void toBytes(ByteBuf data) {
      byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
      data.writeShort(nameBytes.length);
      data.writeBytes(nameBytes);
   }

   public void fromBytes(ByteBuf data) {
      int length = data.readShort();
      this.name = data.readBytes(length).toString(Charsets.UTF_8);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketCreateCWP packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         int minY = LOTRConfig.customWaypointMinY;
         if (minY >= 0 && entityplayer.field_70121_D.field_72338_b < (double)minY) {
            LOTRLog.logger.warn("Player " + entityplayer.func_70005_c_() + " tried to create a custom waypoint below the config file minimum of " + minY + "!");
            return null;
         } else {
            int numWaypoints = pd.getCustomWaypoints().size();
            if (numWaypoints <= pd.getMaxCustomWaypoints()) {
               IChatComponent[] protectionMessage = new IChatComponent[1];
               boolean protection = LOTRBannerProtection.isProtected(world, entityplayer, LOTRBannerProtection.forPlayer_returnMessage(entityplayer, LOTRBannerProtection.Permission.FULL, protectionMessage), true);
               if (!protection) {
                  String wpName = LOTRCustomWaypoint.validateCustomName(packet.name);
                  if (wpName != null) {
                     LOTRCustomWaypoint.createForPlayer(wpName, entityplayer);
                  }
               } else {
                  IChatComponent clientMessage = protectionMessage[0];
                  LOTRPacketCWPProtectionMessage packetMessage = new LOTRPacketCWPProtectionMessage(clientMessage);
                  LOTRPacketHandler.networkWrapper.sendTo(packetMessage, entityplayer);
               }
            }

            return null;
         }
      }
   }
}
