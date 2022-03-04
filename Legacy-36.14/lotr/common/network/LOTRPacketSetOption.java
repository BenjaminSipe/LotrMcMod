package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketSetOption implements IMessage {
   private int option;

   public LOTRPacketSetOption() {
   }

   public LOTRPacketSetOption(int i) {
      this.option = i;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.option);
   }

   public void fromBytes(ByteBuf data) {
      this.option = data.readByte();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketSetOption packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
         boolean flag;
         if (packet.option == 0) {
            flag = pd.getFriendlyFire();
            pd.setFriendlyFire(!flag);
         } else if (packet.option == 1) {
            flag = pd.getEnableHiredDeathMessages();
            pd.setEnableHiredDeathMessages(!flag);
         } else if (packet.option == 2) {
            flag = pd.getHideAlignment();
            pd.setHideAlignment(!flag);
         } else if (packet.option == 3) {
            flag = pd.getHideMapLocation();
            pd.setHideMapLocation(!flag);
         } else if (packet.option == 4) {
            flag = pd.getFemRankOverride();
            pd.setFemRankOverride(!flag);
         } else if (packet.option == 5) {
            flag = pd.getEnableConquestKills();
            pd.setEnableConquestKills(!flag);
         }

         return null;
      }
   }
}
