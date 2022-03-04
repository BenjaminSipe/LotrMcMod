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
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketFactionData implements IMessage {
   private LOTRFaction faction;
   private NBTTagCompound factionNBT;

   public LOTRPacketFactionData() {
   }

   public LOTRPacketFactionData(LOTRFaction f, NBTTagCompound nbt) {
      this.faction = f;
      this.factionNBT = nbt;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.faction.ordinal());

      try {
         (new PacketBuffer(data)).func_150786_a(this.factionNBT);
      } catch (IOException var3) {
         FMLLog.severe("Error writing faction data", new Object[0]);
         var3.printStackTrace();
      }

   }

   public void fromBytes(ByteBuf data) {
      int factionID = data.readByte();
      this.faction = LOTRFaction.forID(factionID);

      try {
         this.factionNBT = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var4) {
         FMLLog.severe("Error reading faction data", new Object[0]);
         var4.printStackTrace();
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFactionData packet, MessageContext context) {
         if (!LOTRMod.proxy.isSingleplayer()) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRFaction faction = packet.faction;
            if (faction != null) {
               LOTRFactionData factionData = pd.getFactionData(faction);
               factionData.load(packet.factionNBT);
            }
         }

         return null;
      }
   }
}
