package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import java.util.Map.Entry;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.util.StatCollector;

public class LOTRPacketAlignmentBonus implements IMessage {
   private LOTRFaction mainFaction;
   private float prevMainAlignment;
   private LOTRAlignmentBonusMap factionBonusMap = new LOTRAlignmentBonusMap();
   private float conquestBonus;
   private double posX;
   private double posY;
   private double posZ;
   private String name;
   private boolean needsTranslation;
   private boolean isKill;
   private boolean isHiredKill;

   public LOTRPacketAlignmentBonus() {
   }

   public LOTRPacketAlignmentBonus(LOTRFaction f, float pre, LOTRAlignmentBonusMap fMap, float conqBonus, double x, double y, double z, LOTRAlignmentValues.AlignmentBonus source) {
      this.mainFaction = f;
      this.prevMainAlignment = pre;
      this.factionBonusMap = fMap;
      this.conquestBonus = conqBonus;
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.name = source.name;
      this.needsTranslation = source.needsTranslation;
      this.isKill = source.isKill;
      this.isHiredKill = source.killByHiredUnit;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.mainFaction.ordinal());
      data.writeFloat(this.prevMainAlignment);
      if (!this.factionBonusMap.isEmpty()) {
         Iterator var2 = this.factionBonusMap.entrySet().iterator();

         while(var2.hasNext()) {
            Entry e = (Entry)var2.next();
            LOTRFaction faction = (LOTRFaction)e.getKey();
            float bonus = (Float)e.getValue();
            data.writeByte(faction.ordinal());
            data.writeFloat(bonus);
         }

         data.writeByte(-1);
      } else {
         data.writeByte(-1);
      }

      data.writeFloat(this.conquestBonus);
      data.writeDouble(this.posX);
      data.writeDouble(this.posY);
      data.writeDouble(this.posZ);
      byte[] nameData = this.name.getBytes(Charsets.UTF_8);
      data.writeShort(nameData.length);
      data.writeBytes(nameData);
      data.writeBoolean(this.needsTranslation);
      data.writeBoolean(this.isKill);
      data.writeBoolean(this.isHiredKill);
   }

   public void fromBytes(ByteBuf data) {
      this.mainFaction = LOTRFaction.forID(data.readByte());
      this.prevMainAlignment = data.readFloat();
      boolean var2 = false;

      byte factionID;
      while((factionID = data.readByte()) >= 0) {
         LOTRFaction faction = LOTRFaction.forID(factionID);
         float bonus = data.readFloat();
         this.factionBonusMap.put(faction, bonus);
      }

      this.conquestBonus = data.readFloat();
      this.posX = data.readDouble();
      this.posY = data.readDouble();
      this.posZ = data.readDouble();
      int length = data.readShort();
      this.name = data.readBytes(length).toString(Charsets.UTF_8);
      this.needsTranslation = data.readBoolean();
      this.isKill = data.readBoolean();
      this.isHiredKill = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketAlignmentBonus packet, MessageContext context) {
         String name = packet.name;
         if (packet.needsTranslation) {
            name = StatCollector.func_74838_a(name);
         }

         LOTRMod.proxy.spawnAlignmentBonus(packet.mainFaction, packet.prevMainAlignment, packet.factionBonusMap, name, packet.isKill, packet.isHiredKill, packet.conquestBonus, packet.posX, packet.posY, packet.posZ);
         return null;
      }
   }
}
