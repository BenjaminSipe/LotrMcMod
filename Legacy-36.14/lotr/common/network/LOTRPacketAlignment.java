package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;

public class LOTRPacketAlignment implements IMessage {
   private UUID player;
   private Map alignmentMap = new HashMap();
   private boolean hideAlignment;

   public LOTRPacketAlignment() {
   }

   public LOTRPacketAlignment(UUID uuid) {
      this.player = uuid;
      LOTRPlayerData pd = LOTRLevelData.getData(this.player);
      LOTRFaction[] var3 = LOTRFaction.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LOTRFaction f = var3[var5];
         float al = pd.getAlignment(f);
         this.alignmentMap.put(f, al);
      }

      this.hideAlignment = pd.getHideAlignment();
   }

   public void toBytes(ByteBuf data) {
      data.writeLong(this.player.getMostSignificantBits());
      data.writeLong(this.player.getLeastSignificantBits());
      Iterator var2 = this.alignmentMap.entrySet().iterator();

      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         LOTRFaction f = (LOTRFaction)entry.getKey();
         float alignment = (Float)entry.getValue();
         data.writeByte(f.ordinal());
         data.writeFloat(alignment);
      }

      data.writeByte(-1);
      data.writeBoolean(this.hideAlignment);
   }

   public void fromBytes(ByteBuf data) {
      this.player = new UUID(data.readLong(), data.readLong());
      boolean var2 = false;

      byte factionID;
      while((factionID = data.readByte()) >= 0) {
         LOTRFaction f = LOTRFaction.forID(factionID);
         float alignment = data.readFloat();
         this.alignmentMap.put(f, alignment);
      }

      this.hideAlignment = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketAlignment packet, MessageContext context) {
         if (!LOTRMod.proxy.isSingleplayer()) {
            LOTRPlayerData pd = LOTRLevelData.getData(packet.player);
            Iterator var4 = packet.alignmentMap.entrySet().iterator();

            while(var4.hasNext()) {
               Entry entry = (Entry)var4.next();
               LOTRFaction f = (LOTRFaction)entry.getKey();
               float alignment = (Float)entry.getValue();
               pd.setAlignment(f, alignment);
            }

            pd.setHideAlignment(packet.hideAlignment);
         }

         return null;
      }
   }
}
