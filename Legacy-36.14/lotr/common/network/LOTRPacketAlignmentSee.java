package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;

public class LOTRPacketAlignmentSee implements IMessage {
   private String username;
   private Map alignmentMap = new HashMap();

   public LOTRPacketAlignmentSee() {
   }

   public LOTRPacketAlignmentSee(String name, LOTRPlayerData pd) {
      this.username = name;
      Iterator var3 = LOTRFaction.getPlayableAlignmentFactions().iterator();

      while(var3.hasNext()) {
         LOTRFaction f = (LOTRFaction)var3.next();
         float al = pd.getAlignment(f);
         this.alignmentMap.put(f, al);
      }

   }

   public void toBytes(ByteBuf data) {
      byte[] nameBytes = this.username.getBytes(Charsets.UTF_8);
      data.writeByte(nameBytes.length);
      data.writeBytes(nameBytes);
      Iterator var3 = this.alignmentMap.entrySet().iterator();

      while(var3.hasNext()) {
         Entry entry = (Entry)var3.next();
         LOTRFaction f = (LOTRFaction)entry.getKey();
         float alignment = (Float)entry.getValue();
         data.writeByte(f.ordinal());
         data.writeFloat(alignment);
      }

      data.writeByte(-1);
   }

   public void fromBytes(ByteBuf data) {
      int length = data.readByte();
      ByteBuf nameBytes = data.readBytes(length);
      this.username = nameBytes.toString(Charsets.UTF_8);
      boolean var4 = false;

      byte factionID;
      while((factionID = data.readByte()) >= 0) {
         LOTRFaction f = LOTRFaction.forID(factionID);
         float alignment = data.readFloat();
         this.alignmentMap.put(f, alignment);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketAlignmentSee packet, MessageContext context) {
         LOTRMod.proxy.displayAlignmentSee(packet.username, packet.alignmentMap);
         return null;
      }
   }
}
