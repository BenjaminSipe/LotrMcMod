package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketLoginPlayerData implements IMessage {
   private NBTTagCompound playerData;

   public LOTRPacketLoginPlayerData() {
   }

   public LOTRPacketLoginPlayerData(NBTTagCompound nbt) {
      this.playerData = nbt;
   }

   public void toBytes(ByteBuf data) {
      try {
         (new PacketBuffer(data)).func_150786_a(this.playerData);
      } catch (IOException var3) {
         FMLLog.severe("Error writing LOTR login player data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public void fromBytes(ByteBuf data) {
      try {
         this.playerData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var3) {
         FMLLog.severe("Error reading LOTR login player data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketLoginPlayerData packet, MessageContext context) {
         NBTTagCompound nbt = packet.playerData;
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         if (!LOTRMod.proxy.isSingleplayer()) {
            pd.load(nbt);
         }

         LOTRMod.proxy.setWaypointModes(pd.showWaypoints(), pd.showCustomWaypoints(), pd.showHiddenSharedWaypoints());
         return null;
      }
   }
}
