package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRelations;

public class LOTRPacketFactionRelations implements IMessage {
   private LOTRPacketFactionRelations.Type packetType;
   private Map fullMap;
   private LOTRFactionRelations.FactionPair singleKey;
   private LOTRFactionRelations.Relation singleRelation;

   public static LOTRPacketFactionRelations fullMap(Map map) {
      LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
      pkt.packetType = LOTRPacketFactionRelations.Type.FULL_MAP;
      pkt.fullMap = map;
      return pkt;
   }

   public static LOTRPacketFactionRelations reset() {
      LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
      pkt.packetType = LOTRPacketFactionRelations.Type.RESET;
      return pkt;
   }

   public static LOTRPacketFactionRelations oneEntry(LOTRFactionRelations.FactionPair pair, LOTRFactionRelations.Relation rel) {
      LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
      pkt.packetType = LOTRPacketFactionRelations.Type.ONE_ENTRY;
      pkt.singleKey = pair;
      pkt.singleRelation = rel;
      return pkt;
   }

   public void toBytes(ByteBuf data) {
      data.writeByte(this.packetType.ordinal());
      if (this.packetType == LOTRPacketFactionRelations.Type.FULL_MAP) {
         Iterator var2 = this.fullMap.entrySet().iterator();

         while(var2.hasNext()) {
            Entry e = (Entry)var2.next();
            LOTRFactionRelations.FactionPair key = (LOTRFactionRelations.FactionPair)e.getKey();
            LOTRFactionRelations.Relation rel = (LOTRFactionRelations.Relation)e.getValue();
            data.writeByte(key.getLeft().ordinal());
            data.writeByte(key.getRight().ordinal());
            data.writeByte(rel.ordinal());
         }

         data.writeByte(-1);
      } else if (this.packetType != LOTRPacketFactionRelations.Type.RESET && this.packetType == LOTRPacketFactionRelations.Type.ONE_ENTRY) {
         data.writeByte(this.singleKey.getLeft().ordinal());
         data.writeByte(this.singleKey.getRight().ordinal());
         data.writeByte(this.singleRelation.ordinal());
      }

   }

   public void fromBytes(ByteBuf data) {
      int typeID = data.readByte();
      this.packetType = LOTRPacketFactionRelations.Type.forID(typeID);
      byte fac2ID;
      byte relID;
      LOTRFaction f1;
      LOTRFaction f2;
      byte fac1ID;
      if (this.packetType == LOTRPacketFactionRelations.Type.FULL_MAP) {
         this.fullMap = new HashMap();
         boolean var3 = false;

         while((fac1ID = data.readByte()) >= 0) {
            fac2ID = data.readByte();
            relID = data.readByte();
            f1 = LOTRFaction.forID(fac1ID);
            f2 = LOTRFaction.forID(fac2ID);
            LOTRFactionRelations.FactionPair key = new LOTRFactionRelations.FactionPair(f1, f2);
            LOTRFactionRelations.Relation rel = LOTRFactionRelations.Relation.forID(relID);
            this.fullMap.put(key, rel);
         }
      } else if (this.packetType != LOTRPacketFactionRelations.Type.RESET && this.packetType == LOTRPacketFactionRelations.Type.ONE_ENTRY) {
         fac1ID = data.readByte();
         fac2ID = data.readByte();
         relID = data.readByte();
         f1 = LOTRFaction.forID(fac1ID);
         f2 = LOTRFaction.forID(fac2ID);
         this.singleKey = new LOTRFactionRelations.FactionPair(f1, f2);
         this.singleRelation = LOTRFactionRelations.Relation.forID(relID);
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketFactionRelations packet, MessageContext context) {
         if (!LOTRMod.proxy.isSingleplayer()) {
            LOTRPacketFactionRelations.Type t = packet.packetType;
            if (t == LOTRPacketFactionRelations.Type.FULL_MAP) {
               LOTRFactionRelations.resetAllRelations();
               Iterator var4 = packet.fullMap.entrySet().iterator();

               while(var4.hasNext()) {
                  Entry e = (Entry)var4.next();
                  LOTRFactionRelations.FactionPair key = (LOTRFactionRelations.FactionPair)e.getKey();
                  LOTRFactionRelations.Relation rel = (LOTRFactionRelations.Relation)e.getValue();
                  LOTRFactionRelations.overrideRelations(key.getLeft(), key.getRight(), rel);
               }
            } else if (t == LOTRPacketFactionRelations.Type.RESET) {
               LOTRFactionRelations.resetAllRelations();
            } else if (t == LOTRPacketFactionRelations.Type.ONE_ENTRY) {
               LOTRFactionRelations.FactionPair key = packet.singleKey;
               LOTRFactionRelations.Relation rel = packet.singleRelation;
               LOTRFactionRelations.overrideRelations(key.getLeft(), key.getRight(), rel);
            }
         }

         return null;
      }
   }

   public static enum Type {
      FULL_MAP,
      RESET,
      ONE_ENTRY;

      public static LOTRPacketFactionRelations.Type forID(int id) {
         LOTRPacketFactionRelations.Type[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRPacketFactionRelations.Type t = var1[var3];
            if (t.ordinal() == id) {
               return t;
            }
         }

         return null;
      }
   }
}
